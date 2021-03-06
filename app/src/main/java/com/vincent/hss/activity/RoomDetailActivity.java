package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.sinping.iosdialog.animation.BaseAnimatorSet;
import com.sinping.iosdialog.animation.BounceEnter.BounceTopEnter;
import com.sinping.iosdialog.animation.SlideExit.SlideBottomExit;
import com.sinping.iosdialog.dialog.listener.OnBtnClickL;
import com.sinping.iosdialog.dialog.widget.NormalDialog;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.EventMsg;
import com.vincent.hss.bean.Room;
import com.vincent.hss.presenter.RoomDetailPresenter;
import com.vincent.hss.presenter.controller.RoomDetailController;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.GlideImageLoader;
import com.vise.log.ViseLog;
import com.youth.banner.Banner;


import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//import io.realm.Realm;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:51
 *
 * @version 1.0
 */

public class RoomDetailActivity extends BaseActivity implements RoomDetailController.IView{

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.room_detail_banner)
    Banner roomDatailBanner;
    @BindView(R.id.room_lamplight_img)
    ImageView roomLamplightImg;
    @BindView(R.id.room_lamplight_status)
    TextView roomLamplightStatus;
    @BindView(R.id.room_exhaust_fan_img)
    ImageView roomExhaustFanImg;
    @BindView(R.id.room_exhaust_fan_status)
    TextView roomExhaustFanStatus;
    @BindView(R.id.room_air_img)
    ImageView roomAirImg;
    @BindView(R.id.room_air_status)
    TextView roomAirStatus;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;

    private boolean lightStatus = false;//默认灯是关闭的
    private boolean exhaustFanStatus = false;//默认排风扇也是关闭的
    private int airStatus = 1;

    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private  NormalDialog normalDialog = null;

    private RoomDetailPresenter presenter;
    private Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        room = JSON.parseObject(intent.getStringExtra("room"),Room.class);
        commonTvTitle2.setText(room.getRoomName());
        commonTitleRight.setText("删除");
        commonTitleRight.setVisibility(View.VISIBLE);
        List<String> listImgPath = JSON.parseArray(room.getRoomBigImg(), String.class);
        initBannerData(listImgPath);

        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();

        presenter = new RoomDetailPresenter(this);

    }

    /**
     * 设置轮播图的数据
     *
     * @param listImgPath
     */
    private void initBannerData(List<String> listImgPath) {
        ViseLog.e("listImagePath:" + listImgPath);
        //设置图片加载器
        roomDatailBanner.setImageLoader(new GlideImageLoader());
        roomDatailBanner.setImages(listImgPath).start();
//        GlideUtils.loadingImgMax(this,roomDatailBanner,listImgPath.get(0));
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        roomDatailBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        roomDatailBanner.stopAutoPlay();
    }

    public static void actionStart(Context context, Room room) {
        Intent intent = new Intent(context, RoomDetailActivity.class);
//        intent.putExtra("roomname", room.getRoomName());
//        intent.putExtra("roomBigImg", room.getRoomBigImg());
//        intent.putExtra("roomId",room.getId());
        intent.putExtra("room",JSON.toJSONString(room));
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.common_title_right,R.id.common_rl_return_2, R.id.room_lamplight_status,
            R.id.room_exhaust_fan_status, R.id.room_air_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.room_lamplight_status:
                if (!lightStatus) {
                    //打开灯光
                    Glide.with(RoomDetailActivity.this).load(R.drawable.lamplight_open).into(roomLamplightImg);
                    roomLamplightStatus.setText("Open");
                    roomLamplightStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomLamplightStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_blue));
                    lightStatus = true;
                } else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.lamplight_close).into(roomLamplightImg);
                    roomLamplightStatus.setText("OFF");
                    roomLamplightStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_close));
                    roomLamplightStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_gray_1));
                    lightStatus = false;
                }
                break;
            case R.id.room_exhaust_fan_status:
                if (!exhaustFanStatus) {
                    //打开灯光
                    Glide.with(RoomDetailActivity.this).load(R.drawable.exhaust_fan_open).into(roomExhaustFanImg);
                    roomExhaustFanStatus.setText("Open");
                    roomExhaustFanStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomExhaustFanStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_blue));
                    exhaustFanStatus = true;
                } else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.exhaust_fan_close).into(roomExhaustFanImg);
                    roomExhaustFanStatus.setText("OFF");
                    roomExhaustFanStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_close));
                    roomExhaustFanStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_gray_1));
                    exhaustFanStatus = false;
                }
                break;
            case R.id.room_air_status:
                if (airStatus == 1) {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_good).into(roomAirImg);
                    roomAirStatus.setText("正常");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_blue));
                    airStatus = 2;
                } else if (airStatus == 2) {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_warning).into(roomAirImg);
                    roomAirStatus.setText("警告");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_background_yello));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_yello));
                    airStatus = 3;
                } else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_bad).into(roomAirImg);
                    roomAirStatus.setText("污染");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_background_red));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this, R.color.color_red));
                    airStatus = 1;
                }
                break;
            case R.id.common_title_right:
                showDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    public void showDialog(){
        normalDialog = new NormalDialog(RoomDetailActivity.this);
        normalDialog.content("删除不可恢复，这个房间被拆了吗？")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        normalDialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        normalDialog.dismiss();
                    }
                },new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        //删除
                        presenter.deleteRoom(BaseApplication.user.getPhone(),room.getRoomName());
                        normalDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if(normalDialog!=null&&normalDialog.isShowing()){
            normalDialog.cancel();
            normalDialog = null;
        }
        super.onDestroy();
    }


    @Override
    public void deleteRoomSuccess() {
        //TODO 删除本地数据库数据
        Room ro = DataSupport.find(Room.class,room.getId());
        if(ro != null){
            ro.delete();
        }
        EventUtil.post(new EventMsg("refresh","1"));
        try {
            Thread.sleep(200);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
