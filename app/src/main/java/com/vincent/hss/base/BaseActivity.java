package com.vincent.hss.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;
import com.vincent.hss.R;
import com.vincent.hss.view.MyLoadingView;

import es.dmoral.toasty.Toasty;

/**
 * description ：
 * project name：CoolApp
 * author : Vincent
 * creation date: 2017/3/4 21:19
 *
 * @version 1.0
 */

public abstract class BaseActivity extends AppCompatActivity{

    private MyLoadingView dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(BaseActivity.this, ContextCompat.getColor(BaseActivity.this, R.color.color_blue));
        dialog = new MyLoadingView(this);
//        dialog.setTitle("提示");
        dialog.setMessage("操作中..");
        dialog.setCancelable(false);
    }

    public void showDialog(){
        if(dialog!=null){
            dialog.show();
        }
    }

    public void closeDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 消息提醒
     * @param code
     * @param msg
     */
    public void showMsg(int code,String msg){
        if(code == 0){
            //错误消息
           Toasty.error(this,msg, Toast.LENGTH_LONG).show();
        }else{
            //正确消息
            Toasty.success(BaseApplication.getApplication(),msg,Toast.LENGTH_LONG).show();
        }
    }

    public void getSubscriber(){
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BaseActivity");
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BaseActivity");
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
