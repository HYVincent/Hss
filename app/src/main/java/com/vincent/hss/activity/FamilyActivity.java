package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.adapter.RoomListAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.Room;
import com.vincent.hss.utils.GlideImageLoader;
import com.vincent.hss.view.RoomListItemOnClickListener;
import com.vincent.hss.view.SpaceItemDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 10:23
 *
 * @version 1.0
 */

public class FamilyActivity extends BaseActivity {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.familey_top_banner)
    Banner banner;
    @BindView(R.id.room_list)
    RecyclerView rlvRoomList;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;

    private List<Room> data = new ArrayList<>();
    private RoomListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        commonTvTitle2.setText("房间");
        commonTitleRight.setText("添加");
        commonTitleRight.setVisibility(View.VISIBLE);
        //资源文件
//        Integer[] images={R.drawable.family_icon_couples_room,R.drawable.family_icon_couples_room};
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.family_icon_couples_room);
        images.add(R.drawable.family_icon_couples_room);
        images.add(R.drawable.family_icon_couples_room);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images).start();
        //banner设置方法全部调用完毕时最后调用
//        banner.start();
        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new RoomListAdapter(FamilyActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvRoomList.setLayoutManager(layoutManager);
//        rcvFriendsList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rlvRoomList.addItemDecoration(new SpaceItemDecoration(10));
        if (data != null) {
            data.clear();
        }
        //添加数据
        Room room1 = new Room("厨房", R.drawable.family_icon_room_kitchen, R.drawable.room_icon_datail_kitchen);
        Room room2 = new Room("厨房", R.drawable.family_icon_room_kitchen, R.drawable.room_icon_datail_kitchen);
        Room room3 = new Room("厨房", R.drawable.family_icon_room_kitchen, R.drawable.room_icon_datail_kitchen);
        Room room4 = new Room("厨房", R.drawable.family_icon_room_kitchen, R.drawable.room_icon_datail_kitchen);
        Room room5 = new Room("厨房", R.drawable.family_icon_room_kitchen, R.drawable.room_icon_datail_kitchen);
        data.add(room1);
        data.add(room2);
        data.add(room3);
        data.add(room4);
        data.add(room5);

        adapter.setItemOnClickListener(new RoomListItemOnClickListener() {
            @Override
            public void onClick(View view, int postion) {
                Room room = data.get(postion);
                RoomDetailActivity.actionStart(FamilyActivity.this, room);
            }
        });
        refreshdata(data);
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    private void refreshdata(List<Room> data) {
        if (data != null && data.size() > 0) {
            rlvRoomList.setAdapter(adapter);
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FamilyActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_title_right,R.id.common_rl_return_2, R.id.familey_top_banner})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.familey_top_banner:
                break;
            case R.id.common_title_right:
                showMsg(0,"正在开发中...");
                break;
        }
    }

}
