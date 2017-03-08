package com.vincent.hss.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.presenter.HomePresenter;
import com.vincent.hss.presenter.controller.HomeController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 21:08
 *
 * @version 1.0
 */

@RuntimePermissions
public class HomeActivity extends BaseActivity implements HomeController.IView {

    @BindView(R.id.main_tv_current_addreee)
    TextView tvCurrentAddress;
    @BindView(R.id.tv_current_weather)
    TextView tvCurrentWeather;
    @BindView(R.id.tv_current_temperature)
    TextView tvCurrentTemperature;//温度
    @BindView(R.id.tv_current_humidity)
    TextView tvCurrentHumidity;//湿度
    @BindView(R.id.tv_weather_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.main_tv_family)
    TextView mainTvFamily;
    @BindView(R.id.main_tv_car)
    TextView mainTvCar;
    @BindView(R.id.main_tv_connection)
    TextView mainTvConnection;
    @BindView(R.id.main_tv_data_analysis)
    TextView mainTvDataAnalysis;
    @BindView(R.id.main_tv_setting)
    TextView mainTvSetting;
    @BindView(R.id.main_tv_add)
    ImageView mainTvAdd;
    private long mExitTime = 0;
    //声明AMapLocation对象
    private AMapLocationClient mLoactionClient = null;
    private HomePresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter = new HomePresenter(this);
        mLoactionClient = new AMapLocationClient(this);
        HomeActivityPermissionsDispatcher.getLocationWithCheck(this);
    }


    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_FINE_LOCATION})
    void getLocation() {
        presenter.getCurrentLocation(mLoactionClient);
    }

    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationaleForCamera(PermissionRequest request) {
        showRationaleDialog("使用定位功能需要系统授权", request);
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_FINE_LOCATION})
    void onCameraDenied() {
        showMsg(0, "你拒绝了权限，将无法定位");
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_FINE_LOCATION})
    void onCameraNeverAskAgain() {
        showMsg(0, "不再允许询问该权限，该功能不可用");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            showMsg(0, "再按一次退出APP");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code, msg);
    }

    @Override
    public void location(final String city) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCurrentAddress.setText(city);
            }
        });
        mLoactionClient.onDestroy();
    }

    @Override
    public void setWeather(String weather, String temperature, String humidity, String updateTime) {
        tvCurrentWeather.setText(weather);
        tvCurrentTemperature.setText(temperature + "℃");
        tvCurrentHumidity.setText(humidity);
        tvUpdateTime.setText("发布时间:" + updateTime);
    }


    @OnClick({R.id.main_tv_add,R.id.main_tv_family, R.id.main_tv_car, R.id.main_tv_connection, R.id.main_tv_data_analysis, R.id.main_tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tv_add:
                showMsg(0,"我也不知道这里做什么");
                break;
            case R.id.main_tv_family:
                FamilyActivity.actionStart(HomeActivity.this);
                break;
            case R.id.main_tv_car:
                CarActivity.actionStart(HomeActivity.this);
                break;
            case R.id.main_tv_connection:
                FamilyAndCarConnectionActivity.actionStart(HomeActivity.this);
                break;
            case R.id.main_tv_data_analysis:
                DataAnalysisActivity.actionStart(HomeActivity.this);
                break;
            case R.id.main_tv_setting:
                SettingActivity.actionStart(HomeActivity.this);
                break;
        }
    }
}
