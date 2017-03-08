package com.vincent.hss.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.jaeger.library.StatusBarUtil;
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

public class FamilyAndCarConnectionActivity extends BaseActivity implements AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener,LocationSource, AMapLocationListener {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;

    private AMap aMap;
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private final int ROUTE_TYPE_DRIVE = 2;
    private DriveRouteResult mDriveRouteResult;
    private RouteSearch mRouteSearch;
    private LatLonPoint mStartPoint =null;
    private LatLonPoint mEndPoint = new LatLonPoint(39.995576,116.481288);//终点，39.995576,116.481288

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        commonTvTitle2.setText("家车互连");
        commonTitleRight.setVisibility(View.VISIBLE);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initMap();

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
                mStartPoint=new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());//起点，39.942295,116.335891
            } else {
                //定位失败
                String errText = "定位失败，" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo();
                ViseLog.e("get location error:" + aMapLocation.getErrorCode() + " info:" + aMapLocation.getErrorInfo());
            }
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
            showMsg(0,"定位中，稍后再试");
            return;
        }
        if (mEndPoint == null) {
            showMsg(0,"终点未设置");
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
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        mapView.onDestroy();
    }

    @OnClick({R.id.common_rl_return_2,R.id.common_title_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.common_title_right:
                //开始搜索规划路线
                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                break;
        }
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
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";

                } else if (result != null && result.getPaths() == null) {
                    showMsg(0,"没有结果");
                }

            } else {
                showMsg(0,"没有结果");
            }
        } else {
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
        new AlertDialog.Builder(FamilyAndCarConnectionActivity.this)
                .setTitle("退出提示")
                .setMessage("是否退出导航模块")
                .setCancelable(false)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("让我在玩会儿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                create()
                .show();
    }
}
