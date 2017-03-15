package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.adapter.ClassTitleAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.ClassBean;
import com.vincent.hss.config.Config;
import com.vincent.hss.fragment.DeviceFragment;
import com.vincent.hss.fragment.ScentFragment;
import com.vincent.hss.fragment.SensorFragment;
import com.vincent.hss.view.CommonOnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 19:38
 *
 * @version 1.0
 */

public class DeviceManagerActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_rl_title)
    RelativeLayout commonRlTitle;
    @BindView(R.id.rlv_title)
    RecyclerView rlvTitle;
    @BindView(R.id.device_manager_ll_content)
    LinearLayout deviceManagerLlContent;
    @BindView(R.id.manager_tv_device)
    TextView managerTvDevice;
    @BindView(R.id.manager_v_device_line)
    View managerVDeviceLine;
    @BindView(R.id.manager_rl_device)
    RelativeLayout managerRlDevice;
    @BindView(R.id.manager_tv_scene)
    TextView managerTvScene;
    @BindView(R.id.manager_v_scene)
    View managerVScene;
    @BindView(R.id.manager_rl_scene)
    RelativeLayout managerRlScene;
    @BindView(R.id.manager_tv_sensor)
    TextView managerTvSensor;
    @BindView(R.id.manager_v_sensor)
    View managerVSensor;
    @BindView(R.id.mangar_rl_sensor)
    RelativeLayout mangarRlSensor;

    private ClassTitleAdapter adapter;
    private List<ClassBean> data = new ArrayList<>();

    private Fragment deviceFragment,sceneFragment,sensorFragment,currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manager);
        ButterKnife.bind(this);
        commonTvTitle.setText("设备管理器");
        adapter = new ClassTitleAdapter(this);
//        initRecycleView();
        initTopTab();
    }

    /**
     * 初始化顶部table
     */
    private void initTopTab() {
        if (deviceFragment == null) {
            deviceFragment = new DeviceFragment();
        }
        if (!deviceFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.device_manager_ll_content, deviceFragment).commitAllowingStateLoss();

            // 记录当前Fragment
            currentFragment = deviceFragment;
        }
    }

    private void initRecycleView() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvTitle.setLayoutManager(linearLayoutManager);
        data = getData();
        adapter.setData(data);
        rlvTitle.setAdapter(adapter);
        adapter.setOnClick(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                ClassBean classBean = data.get(position);
            }
        });
    }

    /**
     * 初始化数据
     *
     * @return
     */
    private List<ClassBean> getData() {
        ClassBean classBean1 = new ClassBean("设备", R.color.color_reseda);
        ClassBean classBean2 = new ClassBean("场景", R.color.color_gray_1);
        ClassBean classBean3 = new ClassBean("传感器", R.color.color_gray_1);
        data.add(classBean1);
        data.add(classBean2);
        data.add(classBean3);
        return data;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DeviceManagerActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.common_rl_return,R.id.manager_rl_device, R.id.manager_rl_scene, R.id.mangar_rl_sensor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.manager_rl_device:
                todeviceFragment();
                break;
            case R.id.manager_rl_scene:
                toSceneFragment();
                break;
            case R.id.mangar_rl_sensor:
                toSensorFragment();
                break;
        }
    }

    /**
     * 切换到传感器Fragment
     */
    private void toSensorFragment() {
        if (sensorFragment == null) {
            sensorFragment = new SensorFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sensorFragment);

        managerTvDevice.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVDeviceLine.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));

        managerTvScene.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVScene.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));

        managerTvSensor.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_blue));
        managerVSensor.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_blue));
    }

    /**
     * 切换到场景管理Fragment
     */
    private void toSceneFragment() {
        if (sceneFragment == null) {
            sceneFragment = new ScentFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sceneFragment);

        managerTvDevice.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVDeviceLine.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));

        managerTvScene.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_blue));
        managerVScene.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_blue));

        managerTvSensor.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVSensor.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));
    }


    /**
     * 切换到设备Fragment
     */
    private void todeviceFragment(){
        if (deviceFragment == null) {
            deviceFragment = new DeviceFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), deviceFragment);

        managerTvDevice.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_blue));
        managerVDeviceLine.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_blue));

        managerTvSensor.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVSensor.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));

        managerTvScene.setTextColor(ContextCompat.getColor(DeviceManagerActivity.this, R.color.color_gray_1));
        managerVScene.setBackgroundColor(ContextCompat.getColor(DeviceManagerActivity.this,R.color.color_gray_1));
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            /*transaction.hide(currentFragment)//Android 错误：IllegalStateException: Can not perform this action after onSaveInstanceState
                    .add(R.id.ll_main_content, fragment).commit();*/

            transaction.hide(currentFragment)
                    .add(R.id.device_manager_ll_content, fragment).commitAllowingStateLoss();
        } else {
//            transaction.hide(currentFragment).show(fragment).commit();//Android 错误：IllegalStateException: Can not perform this action after onSaveInstanceState
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }
        currentFragment = fragment;
    }
}
