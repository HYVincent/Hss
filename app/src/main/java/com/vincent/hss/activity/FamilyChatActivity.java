package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.adapter.MyFamilyAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Family;
import com.vincent.hss.presenter.FamilyChatPresenter;
import com.vincent.hss.presenter.controller.FamilyChatController;
import com.vincent.hss.view.CommonOnClickListener;
import com.vincent.hss.view.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/14 18:41
 *
 * @version 1.0
 */

public class FamilyChatActivity extends BaseActivity implements FamilyChatController.IView{


    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.rl_common_title_1)
    RelativeLayout rlCommonTitle1;
    @BindView(R.id.rlv_family_list)
    RecyclerView rlvFamilyList;
    @BindView(R.id.common_iv_no_content)
    ImageView commonIvNoContent;
    @BindView(R.id.common_tv_no_content)
    TextView commonTvNoContent;
    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;

    private MyFamilyAdapter adapter;
    private List<Family> listData;
    private FamilyChatPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_chat);
        ButterKnife.bind(this);
        commonTvTitle2.setText("家人互通");
        commonTitleRight.setVisibility(View.VISIBLE);
        commonTvNoContent.setText("没有数据，去添加查找家人吧");
        commonTitleRight.setText("添加");
        presenter = new FamilyChatPresenter(this);
        adapter = new MyFamilyAdapter(this);
        initRecycleView();
        adapter.setOnClick(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Family family = listData.get(position);//当前被点击的Family
                ChatActivity.actionStart(FamilyChatActivity.this,family);
            }
        });
        //TODO 服务器获取家人列表
        presenter.getFamilyList(BaseApplication.user.getPhone());
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvFamilyList.setLayoutManager(layoutManager);
        rlvFamilyList.addItemDecoration(new SpaceItemDecoration(10));
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FamilyChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getFamilyList(BaseApplication.user.getPhone());
    }

    @OnClick({R.id.common_rl_return_2, R.id.common_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.common_title_right:
                SearchFamilyActivity.actionStart(FamilyChatActivity.this);
                break;
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }


    @Override
    public void refresh(List<Family> familyList) {
        if(familyList != null&&familyList.size()>0){
            listData = familyList;
            adapter.setListData(familyList);
            rlvFamilyList.setAdapter(adapter);
            rlvFamilyList.setVisibility(View.VISIBLE);
            rlNoContent.setVisibility(View.GONE);
        }else {
            rlvFamilyList.setVisibility(View.GONE);
            rlNoContent.setVisibility(View.VISIBLE);
        }
    }
}
