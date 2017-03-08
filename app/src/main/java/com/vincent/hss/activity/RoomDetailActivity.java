package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.Room;
import com.vincent.hss.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:51
 *
 * @version 1.0
 */

public class RoomDetailActivity extends BaseActivity {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.room_datail_img)
    ImageView roomDatailImg;
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

    private boolean lightStatus = false;//默认灯是关闭的
    private boolean exhaustFanStatus = false;//默认排风扇也是关闭的
    private int airStatus = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        final Intent intent = getIntent();
        commonTvTitle2.setText(intent.getStringExtra("roomname"));
        Glide.with(this).load(intent.getIntExtra("roomBigImg", 0)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = ScreenUtils.getScreenWidth(RoomDetailActivity.this) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = roomDatailImg.getLayoutParams();
                para.height = height;
                roomDatailImg.setLayoutParams(para);
                Glide.with(RoomDetailActivity.this).load(intent.getIntExtra("roomBigImg", 0)).asBitmap().into(roomDatailImg);
            }
        });
    }

    public static void actionStart(Context context, Room room) {
        Intent intent = new Intent(context, RoomDetailActivity.class);
        intent.putExtra("roomname", room.getRoomName());
        intent.putExtra("roomBigImg", room.getRomBigImg());
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.common_rl_return_2,R.id.room_lamplight_status, R.id.room_exhaust_fan_status, R.id.room_air_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.room_lamplight_status:
                if(!lightStatus){
                    //打开灯光
                    Glide.with(RoomDetailActivity.this).load(R.drawable.lamplight_open).into(roomLamplightImg);
                    roomLamplightStatus.setText("Open");
                    roomLamplightStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomLamplightStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_blue));
                    lightStatus = true;
                }else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.lamplight_close).into(roomLamplightImg);
                    roomLamplightStatus.setText("OFF");
                    roomLamplightStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_close));
                    roomLamplightStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_gray_1));
                    lightStatus = false;
                }
                break;
            case R.id.room_exhaust_fan_status:
                if(!exhaustFanStatus){
                    //打开灯光
                    Glide.with(RoomDetailActivity.this).load(R.drawable.exhaust_fan_open).into(roomExhaustFanImg);
                    roomExhaustFanStatus.setText("Open");
                    roomExhaustFanStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomExhaustFanStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_blue));
                    exhaustFanStatus = true;
                }else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.exhaust_fan_close).into(roomExhaustFanImg);
                    roomExhaustFanStatus.setText("OFF");
                    roomExhaustFanStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_close));
                    roomExhaustFanStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_gray_1));
                    exhaustFanStatus = false;
                }
                break;
            case R.id.room_air_status:
                if(airStatus == 1){
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_good).into(roomAirImg);
                    roomAirStatus.setText("正常");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_lamplight_background_open));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_blue));
                    airStatus = 2;
                }else if(airStatus == 2){
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_warning).into(roomAirImg);
                    roomAirStatus.setText("警告");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_background_yello));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_yello));
                    airStatus = 3;
                }else {
                    Glide.with(RoomDetailActivity.this).load(R.drawable.air_bad).into(roomAirImg);
                    roomAirStatus.setText("污染");
                    roomAirStatus.setBackground(getDrawable(R.drawable.shape_background_red));
                    roomAirStatus.setTextColor(ContextCompat.getColor(RoomDetailActivity.this,R.color.color_red));
                    airStatus = 1;
                }
                break;
        }
    }
}
