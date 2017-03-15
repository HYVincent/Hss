package com.vincent.hss.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.analytics.MobclickAgent;
import com.vincent.hss.R;
import com.vincent.hss.utils.ScreenUtils;
import com.vincent.hss.view.GlideImageLoader;
import com.vincent.hss.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

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

    private LoadingDialog dialog;
    private static List<Activity> activityList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(BaseActivity.this, ContextCompat.getColor(BaseActivity.this, R.color.color_blue));
        activityList.add(this);
        dialog =new LoadingDialog(this);
    }


    /**
     * 设置参数
     * @param isCrop 是否裁剪
     * @param countImg 设置选择图片张书
     *  @param selectModel 选择模式 true多选  FALSE 单选
     */
    public void initImageSelect(boolean isCrop,boolean selectModel,int countImg) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(countImg);    //选中数量限制
        imagePicker.setMultiMode(selectModel);
        imagePicker.setCrop(isCrop);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(ScreenUtils.getScreenWidth(this)/8*5);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(ScreenUtils.getScreenWidth(this)/8*5);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
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
        activityList.remove(this);
    }

    /**
     * 杀死其它的Activity，
     * @param activity
     */
    public void finshOther(Activity activity){
        for (int i=0;i<activityList.size();i++){
            if(!activityList.get(i).equals(activity)){
                activityList.get(i).finish();
            }
        }
    }

    /**
     * 结束APP
     */
    public void finishAllActivity(){
        for (Activity activity:activityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
