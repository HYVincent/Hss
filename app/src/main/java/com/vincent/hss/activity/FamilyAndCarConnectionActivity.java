package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.sinping.iosdialog.animation.BaseAnimatorSet;
import com.sinping.iosdialog.animation.BounceEnter.BounceTopEnter;
import com.sinping.iosdialog.animation.SlideExit.SlideBottomExit;
import com.sinping.iosdialog.dialog.listener.OnBtnClickL;
import com.sinping.iosdialog.dialog.widget.NormalDialog;
import com.sinping.iosdialog.dialogsamples.utils.T;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.overlay.DrivingRouteOverlay;
import com.vincent.hss.overlay.PoiOverlay;
import com.vincent.hss.utils.AMapUtil;
import com.vincent.hss.utils.ScreenUtils;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：高德地图
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 10:24
 *
 * @version 1.0
 */

public class FamilyAndCarConnectionActivity extends BaseActivity implements AMapLocationListener,RadioGroup.OnCheckedChangeListener,LocationSource,PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerClickListener,AMap.InfoWindowAdapter,RouteSearch.OnRouteSearchListener {

    @BindView(R.id.connection_rl_finish)
    RelativeLayout connectionRlFinish;
    @BindView(R.id.connection_iv_menu)
    ImageView connectionIvMenu;
    @BindView(R.id.rl_map_type_menu)
    RelativeLayout rlMapTypeMenu;
    @BindView(R.id.connection_input_location)
    EditText connectionInputLocation;
    @BindView(R.id.location_start_search)
    ImageView locationStartSearch;
    @BindView(R.id.rl_title_center)
    RelativeLayout rlTitleCenter;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.firstline)
    TextView mRotueTimeDes;
    @BindView(R.id.secondline)
    TextView mRouteDetailDes;



    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    //定位客户端
    private AMapLocationClient mlocationClient;
    //定位参数
    private AMapLocationClientOption mLocationOption;
    //界面元素显示相关，比如说logo位置等
    private UiSettings uiSettings;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    // Poi查询条件类
    private PoiSearch.Query query;
    //Poi返回的结果
    private PoiResult poiResult;
    // 当前页面，从0开始计数
    private int currentPage = 0;
    //Poi搜索
    private PoiSearch poiSearch;
    /*导航路径规划*/
    private RouteSearch mRouteSearch;

    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private NormalDialog dialog;

    //规划路径
    private final int ROUTE_TYPE_DRIVE = 2;
    //规划路径的起点
//    private LatLonPoint mStartPoint = new LatLonPoint(39.942295,116.335891);//起点，39.942295,116.335891\
    private LatLonPoint mStartPoint = null;
    //路径规划的终点
//    private LatLonPoint mEndPoint = new LatLonPoint(39.995576,116.481288);//终点，39.995576,116.481288
    private LatLonPoint mEndPoint = null;
    //定位到的当前城市
    private String currentCity;
    /*当前城市代码*/
    private String cityCode;
    /*路径规划的结果*/
    private DriveRouteResult mDriveRouteResult;

    private boolean isNavigation = false;//是否正在导航

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();
        initMap();
        mapView.onCreate(savedInstanceState);// 此方法必须重写

    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        showDialog();
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked));
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        // 设置定位的类型为根据地图面向方向旋转 会导致搜索结果移动位置无效一直保持定位姿势
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomPosition(14);//缩放比例
        uiSettings.setScaleControlsEnabled(true);//显示比例尺
//        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//底部
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);//中部
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
        if(dialog!=null){
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        //停止定位
        deactivate();

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FamilyAndCarConnectionActivity.class);
        context.startActivity(intent);
    }


    private void showPopupwindow() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_type, null);
        popupWindow.setContentView(view);
        //矢量地图
        TextView normal = (TextView) view.findViewById(R.id.map_type_normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                popupWindow.dismiss();
            }
        });
        //卫星地图
        TextView satellite = (TextView) view.findViewById(R.id.map_type_satellite);
        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                popupWindow.dismiss();
            }
        });
        //夜景地图
        TextView night = (TextView) view.findViewById(R.id.map_type_night);
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
                popupWindow.dismiss();
            }
        });
        //导航地图
        TextView navi = (TextView) view.findViewById(R.id.map_type_navi);
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图模式
                popupWindow.dismiss();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(ScreenUtils.getScreenWidth(this) / 2);
        popupWindow.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(rlMapTypeMenu, 0, 0, Gravity.BOTTOM);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.connection_map_type, menu);
        //true 表示允许菜单被创建出来
        return true;
    }
*/

    /**
     * 输入提示结果的回调 这个不要了，免得费流量
     *
     * @param tipList g
     * @param rCode   g
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            final List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < tipList.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
            }
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

            final PopupWindow popupWindow = new PopupWindow(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_search_result_list, null);
            ListView listView = (ListView) view.findViewById(R.id.item_search_result_list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ViseLog.d("---------------------" + listString.get(position));
                    String str = listString.get(position).get("name");
                    popupWindow.dismiss();
                }
            });
            listView.setAdapter(aAdapter);
            popupWindow.setContentView(view);
            aAdapter.notifyDataSetChanged();
            popupWindow.setOutsideTouchable(true);
            popupWindow.setWidth(ScreenUtils.getScreenWidth(this));
            popupWindow.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            popupWindow.showAsDropDown(rlTitleCenter, 0, 0, Gravity.BOTTOM);
        } else {
            showMsg(0, String.valueOf(rCode));
        }
    }

    @OnClick({R.id.connection_rl_finish, R.id.rl_map_type_menu, R.id.location_start_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connection_rl_finish:
                if(isNavigation){
                    //正在导航，退出提示
                    exitDialog();
                }else {
                    //否则退出
                    finish();
                }
                break;
            case R.id.rl_map_type_menu:
                showPopupwindow();
                break;
            case R.id.location_start_search:
                String userInputAddress = connectionInputLocation.getText().toString().trim();
                if(TextUtils.isEmpty(userInputAddress)){
                    showMsg(0,"未输入");
                }else {
                    showDialog();
                    doSearchQuery(userInputAddress,"","");
                }
                break;
        }
    }

    /**
     * 开始Poi搜索
     * @param userInputAddressStr 搜索字符串
     * @param searchType 搜索类型
     * @param searchArea 搜索地区
     */
    protected void doSearchQuery(String userInputAddressStr,String searchType,String searchArea) {
        currentPage = 0;
        query = new PoiSearch.Query(userInputAddressStr, searchType, searchArea);
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
    }

    /**
     * 定位成功回调
     * @param amapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        closeDialog();
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                //经纬度
                mStartPoint = new LatLonPoint(amapLocation.getLatitude(),amapLocation.getLongitude());
                currentCity = amapLocation.getCity();
                cityCode = amapLocation.getCityCode();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
                showMsg(0,errText);
            }
        }
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }




    /**
     * 激活定位
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(true);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     *  poi信息查询结果回调
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        closeDialog();// 隐藏对话框
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        ViseLog.d("poiItems->"+poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        //推荐按城市信息
                        showSuggestCity(suggestionCities);
                    } else {
                        showMsg(0,"抱歉，没有相关信息");
                    }
                }
            } else {
                showMsg(0,"抱歉，没有相关信息");
            }
        } else {
            showMsg(0,"错误码："+String.valueOf(rCode));
        }
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        showMsg(1,infomation);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        ViseLog.d("+++++++"+poiItem);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        ViseLog.d("........"+marker.getPosition());
        return false;
    }

    /**
     * 搜索结果点击目的地
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        // 调起高德地图app
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把这里设置为终点开始导航
                setDestination(marker);
            }
        });

        return view;
    }

    /**
     * 弹出设置目的地的对话框
     * @param marker
     */
    private void setDestination(final Marker marker) {
        dialog = new NormalDialog(FamilyAndCarConnectionActivity.this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.content("是否设置该地点为目的地开始导航?")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
//                        startAMapNavi(marker);
                        //自己写导航
                        startAmapNavi2(marker);
                        dialog.dismiss();
                    }
                });
    }
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
//            ToastUtil.show(mContext, "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            showMsg(0,"终点未设置");
        }
        showDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
            isNavigation = true;
        }
    }
    /**
     * 规划路径
     * @param marker
     */
    private void startAmapNavi2(Marker marker) {
        //初始化路径规划的终点。。
//        mEndPoint = new LatLonPoint(39.995576,116.481288);//终点，39.995576,116.481288
//        mEndPoint = new LatLonPoint(marker.get)
        MarkerOptions markerOptions = marker.getOptions();
        LatLng latLng = markerOptions.getPosition();
        ViseLog.d("latlng"+latLng+"  latLng.longitude="+latLng.longitude+"  latLng.latitude"+latLng.latitude);
        mEndPoint = new LatLonPoint(latLng.latitude,latLng.longitude);
        ViseLog.d("marker-->"+markerOptions.getPosition());
        //开车导航
        searchRouteResult(ROUTE_TYPE_DRIVE,RouteSearch.DrivingDefault);
    }

    /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    public void startAMapNavi(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi(naviPara, getApplicationContext());
        } catch (com.amap.api.maps.AMapException e) {

            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getApplicationContext());

        }

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        closeDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            FamilyAndCarConnectionActivity.this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);//用来显示打车多少钱的View
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(FamilyAndCarConnectionActivity.this,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            //数据过大，导致 TransactionTooLargeException: data parcel size 1624324 bytes
                            //TODO 注意 这里超长距离导航数据过大会导致页面崩溃，可存入数据库，然后读取的方式
                            intent.putExtra("drive_result", mDriveRouteResult);
                            startActivity(intent);
                        }
                    });

                } else if (result != null && result.getPaths() == null) {
//                    ToastUtil.show(mContext, R.string.no_result);
                    showMsg(0,"结果为空");
                }

            } else {
//                ToastUtil.show(mContext, R.string.no_result);
                showMsg(0,"结果为空");
            }
        } else {
//            ToastUtil.showerror(this.getApplicationContext(), errorCode);
            showMsg(0,String.valueOf(errorCode));
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onBackPressed() {
        if(isNavigation){
            //正在导航，退出提示
            exitDialog();
        }else {
            //否则退出
            super.onBackPressed();
        }
    }


    private void exitDialog(){
        dialog = new NormalDialog(FamilyAndCarConnectionActivity.this);
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#383838"))//
                .cornerRadius(5)//
                .content("检测到正在导航，是否退出导航?")//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#ffffff"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                .btnPressColor(Color.parseColor("#2B2B2B"))//
                .widthScale(0.85f)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        finishActivity();
                    }
                });
    }

    /**
     * 延迟200ms关闭，避免内存泄漏
     */
    private void finishActivity(){
        try {
            Thread.sleep(200);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
