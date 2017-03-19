package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.EventMsg;
import com.vincent.hss.presenter.controller.EditUserInfoController;
import com.vincent.hss.presenter.EditUserInfoPresenter;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.view.WindowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 18:14
 *
 * @version 1.0
 */

public class EditUserInfoActivity extends BaseActivity implements EditUserInfoController.IView{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.user_info_et_user_nickname)
    TextView userInfoEtUserNickname;
    @BindView(R.id.user_info_et_user_sex)
    TextView userInfoEtUserSex;
    @BindView(R.id.user_info_et_user_birthday)
    TextView userInfoEtUserBirthday;
    @BindView(R.id.user_info_et_user_live_status)
    TextView userInfoEtUserLiveStatus;
    @BindView(R.id.user_info_submit_alter)
    Button userInfoSubmitAlter;
    @BindView(R.id.et_userinfo_nickname)
    RelativeLayout etUserinfoNickname;
    @BindView(R.id.et_userinfo_gender)
    RelativeLayout etUserinfoGender;
    @BindView(R.id.et_userinfo_birthday)
    RelativeLayout etUserinfoBirthday;
    @BindView(R.id.et_userinfo_status)
    RelativeLayout etUserinfoStatus;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private String nickname;

    //是否有修改个人资料
    private boolean isAlter = false;
    private EditUserInfoPresenter presenter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_user_info);
        ButterKnife.bind(this);
        commonTvTitle.setText("编辑个人资料");
        userInfoEtUserBirthday.setText(BaseApplication.user.getBirthday());
        userInfoEtUserLiveStatus.setText(BaseApplication.user.getLive_status());
        userInfoEtUserNickname.setText(BaseApplication.user.getNickname());
        userInfoEtUserSex.setText(BaseApplication.user.getSex());
        presenter = new EditUserInfoPresenter(this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.common_rl_return, R.id.user_info_submit_alter, R.id.et_userinfo_nickname,
            R.id.et_userinfo_gender, R.id.et_userinfo_birthday, R.id.et_userinfo_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.user_info_submit_alter:
                if(isAlter){
                    //存在修改，提交
                    presenter.submitAlterUserInfo(
                            BaseApplication.user.getPhone(),
                            userInfoEtUserNickname.getText().toString().trim(),
                            userInfoEtUserSex.getText().toString().trim(),
                            userInfoEtUserBirthday.getText().toString().trim(),
                            userInfoEtUserLiveStatus.getText().toString().trim());
                }else {
                    //没有修改，返回
                    showMsg(0,"当前并无修改");
                }
                break;
            case R.id.et_userinfo_nickname:
                alterNickname();
                break;
            case R.id.et_userinfo_gender:
                List<String> data = new ArrayList<>();
                data.add("男");
                data.add("女");
                WindowUtils.selectSingleText(this, llRoot, data, new WindowUtils.SelectTextListener() {
                    @Override
                    public void getContent(String contentStr) {
                        userInfoEtUserSex.setText(contentStr);
                        if(!TextUtils.equals(contentStr,BaseApplication.user.getSex())){
                            isAlter = true;
                        }
                    }
                });
                break;
            case R.id.et_userinfo_birthday:
                alterBirthday();
                break;
            case R.id.et_userinfo_status:
                WindowUtils.editContentDialog(this,"编辑", new WindowUtils.GetContentListener() {
                    @Override
                    public void content(String inputStr) {
                        if(!TextUtils.isEmpty(inputStr)){
                            userInfoEtUserLiveStatus.setText(inputStr);
                            if(!TextUtils.equals(inputStr,BaseApplication.user.getLive_status())){
                                isAlter = true;
                            }
                        }
                    }
                });
                break;
        }
    }


    private void alterBirthday() {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(EditUserInfoActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                Toast.makeText(EditUserInfoActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                userInfoEtUserBirthday.setText(dateDesc);
                if(!TextUtils.equals(dateDesc,BaseApplication.user.getBirthday())){
                    isAlter = true;
                }
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1990) //min year in loop
                .maxYear(2550) // max year in loop
                .dateChose(BaseApplication.user.getBirthday()) // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(EditUserInfoActivity.this);
    }

    private void alterNickname() {
        WindowUtils.editContentDialog(this,"修改昵称", new WindowUtils.GetContentListener() {
            @Override
            public void content(String inputStr) {
                if(!TextUtils.isEmpty(inputStr)){
                    userInfoEtUserNickname.setText(inputStr);
                    if(!TextUtils.equals(inputStr,BaseApplication.user.getNickname())){
                        isAlter = true;
                    }
                }
            }
        });
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void alterUserInfoSuccess() {
        //修改成功之后要更新个人资料
        EventUtil.post(new EventMsg("refresh",""));
        finish();
    }
}
