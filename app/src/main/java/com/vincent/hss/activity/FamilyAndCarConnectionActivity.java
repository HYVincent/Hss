package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
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
import com.sinping.iosdialog.dialog.widget.MaterialDialog;
import com.sinping.iosdialog.dialog.widget.NormalDialog;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.overlay.DrivingRouteOverlay;
import com.vincent.hss.utils.AMapUtil;
import com.vise.log.ViseLog;

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

public class FamilyAndCarConnectionActivity extends BaseActivity implements AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener, LocationSource, AMapLocationListener {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.connection_input_location)
    EditText connectionInputLocation;
    @BindView(R.id.rl_common_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_setting_home_location)
    TextView tvSettingHomeLocation;
    @BindView(R.id.rl_setting_home_location)
    RelativeLayout rlSettingHomeLocation;
    @BindView(R.id.fab_start)
    FloatingActionButton fabStart;

    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private final int ROUTE_TYPE_DRIVE = 2;
    private DriveRouteResult mDriveRouteResult;
    private RouteSearch mRouteSearch;
    private LatLonPoint mStartPoint = null;
    //    private LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，39.995576,116.481288
    private LatLonPoint mEndPoint = null;

    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private GeocodeSearch geocoderSearch;
    private Marker geoMarker;

    private  MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        initMap();
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    private void initMap() {
        aMap = mapView.getMap();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMapClickListener(FamilyAndCarConnectionActivity.this);
        aMap.setOnMarkerClickListener(FamilyAndCarConnectionActivity.this);
        aMap.setOnInfoWindowClickListener(FamilyAndCarConnectionActivity.this);
        aMap.setInfoWindowAdapter(FamilyAndCarConnectionActivity.this);
    }


    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        if(dialog!=null){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FamilyAndCarConnectionActivity.class);
        context.startActivity(intent);
    }

    /**
     * 设置定位初始化及启动定位
     *
     * @param locationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener locationChangedListener) {
        onLocationChangedListener = locationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    /**
     * deactivate()中写停止定位的相关调用。
     */
    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位回调方法：onLocationChanged(AMapLocation amapLocation)。
     * 在回调方法中调用“mListener.onLocationChanged(amapLocation);”可以在地图上显示系统小蓝点
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && mLocationOption != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                //显示位置蓝点
                onLocationChangedListener.onLocationChanged(aMapLocation);
//                aMapLocation.getLongitude();//经度
//                aMapLocation.getLatitude();//纬度
                mStartPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());//起点，39.942295,116.335891
            } else {
                //定位失败
                String errText = "定位失败，" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo();
                ViseLog.e("get location error:" + aMapLocation.getErrorCode() + " info:" + aMapLocation.getErrorInfo());
            }
        }
    }


    @OnClick({R.id.rl_common_return, R.id.tv_setting_home_location, R.id.fab_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_common_return:
                finish();
                break;
            case R.id.tv_setting_home_location:
                String location = connectionInputLocation.getText().toString().trim();
                GeocodeQuery query = new GeocodeQuery(location, "010");
                geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
                break;
            case R.id.fab_start:
                break;
        }
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
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            showMsg(0, "定位中，稍后再试");
            return;
        }
        if (mEndPoint == null) {
            showMsg(0, "终点未设置");
        }
        showDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }



    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        mapView.onDestroy();
        if(dialog!=null){
            dialog.cancel();
            dialog = null;
        }
        super.onDestroy();
    }


    /**
     * 开始搜索规划路线
     */
    private void setLocation() {
        dialog = new MaterialDialog(FamilyAndCarConnectionActivity.this);
        View view = LayoutInflater.from(FamilyAndCarConnectionActivity.this).inflate(R.layout.dlg_input_location, null);
        EditText editText = (EditText) view.findViewById(R.id.dlg_input_location);
        String input = editText.getText().toString().trim();
        dialog.setContentView(view);
        dialog.btnText("取消", "确定")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }
        );
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
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
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";

                } else if (result != null && result.getPaths() == null) {
                    showMsg(0, "没有结果");
                }

            } else {
                showMsg(0, "没有结果");
            }
        } else {
            showMsg(0, String.valueOf(errorCode));
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
        final NormalDialog dialog = new NormalDialog(FamilyAndCarConnectionActivity.this);
        dialog.content("退出吗，不再看看？")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        //删除
                        dialog.dismiss();
                        finish();
                    }
                });

    }



    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
                geoMarker.setPosition(AMapUtil.convertToLatLng(address
                        .getLatLonPoint()));
                //搜索到地址了
                /*addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();
                ToastUtil.show(GeocoderActivity.this, addressName);*/
            } else {
                showMsg(0,"没有数据");
            }
        } else {
            ViseLog.e("code-->"+rCode);
        }
    }


}
