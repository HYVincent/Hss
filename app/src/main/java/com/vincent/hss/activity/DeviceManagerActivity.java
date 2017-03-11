package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.adapter.ClassTitleAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.ClassBean;
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
    private ClassTitleAdapter adapter;
    private List<ClassBean> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manager);
        ButterKnife.bind(this);
        commonTvTitle.setText("设备管理器");
        adapter = new ClassTitleAdapter(this);
        initRecycleView();
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
     * @return
     */
    private List<ClassBean> getData(){
        ClassBean classBean1 = new ClassBean("设备",R.color.color_reseda);
        ClassBean classBean2 = new ClassBean("场景",R.color.color_gray_1);
        ClassBean classBean3 = new ClassBean("传感器",R.color.color_gray_1);
        data.add(classBean1);
        data.add(classBean2);
        data.add(classBean3);
        return data;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DeviceManagerActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
    }



}
