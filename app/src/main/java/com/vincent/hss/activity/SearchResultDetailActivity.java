package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.User;
import com.vincent.hss.presenter.SearchResultDetailPresenter;
import com.vincent.hss.presenter.controller.SearchResultDetailController;
import com.vincent.hss.view.WindowUtils;
import com.vise.log.ViseLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 7:54
 *
 * @version 1.0
 */

public class SearchResultDetailActivity extends BaseActivity implements SearchResultDetailController.IView{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.user_info_et_user_phone)
    TextView userInfoEtUserPhone;
    @BindView(R.id.user_info_et_user_nickname)
    TextView userInfoEtUserNickname;
    @BindView(R.id.user_info_et_user_sex)
    TextView userInfoEtUserSex;
    @BindView(R.id.user_info_et_user_birthday)
    TextView userInfoEtUserBirthday;
    @BindView(R.id.user_info_et_user_live_status)
    TextView userInfoEtUserLiveStatus;
    @BindView(R.id.search_result_add_family_tv)
    TextView searchResultAddFamilyTv;

    private SearchResultDetailPresenter presenter;
    private User user;
    private boolean isSend= false;//是否已发送添加请求

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resutl_detail);
        ButterKnife.bind(this);
        user = JSON.parseObject(getIntent().getStringExtra("userInfo"),User.class);
        initView(user);
        presenter  = new SearchResultDetailPresenter(this);
        presenter.hasFamily(BaseApplication.user.getPhone(),user.getPhone());
    }

    private void initView(User user) {
        commonTvTitle.setText("用户详情");
        userInfoEtUserBirthday.setText(user.getBirthday());
        userInfoEtUserLiveStatus.setText(user.getLive_status());
        userInfoEtUserNickname.setText(user.getNickname());
        userInfoEtUserPhone.setText(user.getPhone());
        userInfoEtUserSex.setText(user.getSex());
    }

    @OnClick({R.id.common_rl_return, R.id.search_result_add_family_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.search_result_add_family_tv:
                if(!isSend){
                    WindowUtils.editContentDialog(SearchResultDetailActivity.this, "向对方说点什么吧", new WindowUtils.GetContentListener() {
                        @Override
                        public void content(String inputStr) {
                            presenter.sendAddFamilyRequest(BaseApplication.user.getPhone(),user.getPhone(),inputStr);
                            ViseLog.d("正在发送..........");
                        }
                    });
                }else {
                    showMsg(0,"点我也没有用，我都发了，你还点");
                }
                break;
        }
    }

    public static void actionStart(Context context, User user){
        Intent intent = new Intent(context,SearchResultDetailActivity.class);
        intent.putExtra("userInfo", JSON.toJSONString(user));
        context.startActivity(intent);
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void sendSuccess() {
//        finish();
        searchResultAddFamilyTv.setText("已发送");
        searchResultAddFamilyTv.setBackgroundResource(R.drawable.shape_pop_background_gray);
        isSend = true;
    }

    @Override
    public void hasFamily(boolean hasFamily) {
        if(hasFamily){
            searchResultAddFamilyTv.setBackgroundResource(R.drawable.shape_pop_background_gray);
            searchResultAddFamilyTv.setClickable(false);
            searchResultAddFamilyTv.setText("已添加");
        }
    }
}
