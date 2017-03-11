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
import com.vincent.hss.bean.dao.DaoUtils;
import com.vincent.hss.bean.dao.RoomDao;
import com.vincent.hss.presenter.FamilyPresenter;
import com.vincent.hss.presenter.controller.FamilyController;
import com.vincent.hss.utils.GlideImageLoader;
import com.vincent.hss.view.RoomListItemOnClickListener;
import com.vincent.hss.view.SpaceItemDecoration;
import com.vise.log.ViseLog;
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

public class FamilyActivity extends BaseActivity implements FamilyController.IView{

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
    @BindView(R.id.common_tv_no_content)
    TextView commonTvNoContent;
    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;

    private RoomListAdapter adapter;

    private List<Room> data = new ArrayList<>();
    private RoomDao roomDao;

    private FamilyPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        ButterKnife.bind(this);
        commonTvTitle2.setText("房间");
        commonTitleRight.setText("添加");
        commonTitleRight.setVisibility(View.VISIBLE);
        presenter = new FamilyPresenter(this);
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
        /////////////////////////////////////////////
        roomDao = DaoUtils.getmDaoSession().getRoomDao();
        presenter.queryRoom(roomDao);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ViseLog.d("onRestart");
        presenter.queryRoom(roomDao);
    }

    private void initRecycleView() {
        adapter = new RoomListAdapter(FamilyActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvRoomList.setLayoutManager(layoutManager);
//        rcvFriendsList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rlvRoomList.addItemDecoration(new SpaceItemDecoration(10));
        adapter.setItemOnClickListener(new RoomListItemOnClickListener() {
            @Override
            public void onClick(View view, int postion) {
                ViseLog.d("position-->"+postion +"  data.size-->"+data.size());
                Room room = data.get(postion);
                RoomDetailActivity.actionStart(FamilyActivity.this, room);
            }
        });

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

    @OnClick({R.id.common_title_right, R.id.common_rl_return_2, R.id.familey_top_banner})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.familey_top_banner:
                break;
            case R.id.common_title_right:
                AddRoomActivity.actionStart(FamilyActivity.this);
                break;
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void refreshRoom(List<Room> listData) {
        ViseLog.d("listData:"+listData.size());
        if (listData != null && listData.size() > 0) {
            data = listData;
            adapter.setData(data);
            rlvRoomList.setAdapter(adapter);
            rlNoContent.setVisibility(View.GONE);
            rlvRoomList.setVisibility(View.VISIBLE);
        }else {
            rlNoContent.setVisibility(View.VISIBLE);
            commonTvNoContent.setText("没有房间，赶快去添加吧");
            rlvRoomList.setVisibility(View.GONE);
        }
    }
}
