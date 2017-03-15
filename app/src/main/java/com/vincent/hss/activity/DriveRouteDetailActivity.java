package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.vincent.hss.R;
import com.vincent.hss.adapter.DriveSegmentListAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.utils.AMapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/12 21:08
 *
 * @version 1.0
 */

public class DriveRouteDetailActivity extends BaseActivity {

    @BindView(R.id.title_back)
    LinearLayout titleBack;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.map)
    TextView map;
    @BindView(R.id.title_map)
    LinearLayout titleMap;
    @BindView(R.id.route_title)
    RelativeLayout routeTitle;
    @BindView(R.id.firstline)
    TextView firstline;
    @BindView(R.id.secondline)
    TextView secondline;
    @BindView(R.id.bus_segment_list)
    ListView busSegmentList;
    @BindView(R.id.bus_path)
    LinearLayout busPath;
    @BindView(R.id.route_map)
    MapView routeMap;
    private DrivePath mDrivePath;
    private DriveRouteResult mDriveRouteResult;
    private DriveSegmentListAdapter mDriveSegmentListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        ButterKnife.bind(this);
        getIntentData();
        titleCenter.setText("驾车路线详情");
        String dur = AMapUtil.getFriendlyTime((int) mDrivePath.getDuration());
        String dis = AMapUtil.getFriendlyLength((int) mDrivePath
                .getDistance());
        firstline.setText(dur + "(" + dis + ")");
        int taxiCost = (int) mDriveRouteResult.getTaxiCost();
        secondline.setText("打车约"+taxiCost+"元");
        secondline.setVisibility(View.VISIBLE);
        configureListView();
    }


    public static void actionStart(Context context){
        Intent intent = new Intent(context,DriveRouteDetailActivity.class);
        context.startActivity(intent);

    }

    private void configureListView() {
        busSegmentList = (ListView) findViewById(R.id.bus_segment_list);
        mDriveSegmentListAdapter = new DriveSegmentListAdapter(
                this.getApplicationContext(), mDrivePath.getSteps());
        busSegmentList.setAdapter(mDriveSegmentListAdapter);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mDrivePath = intent.getParcelableExtra("drive_path");
        mDriveRouteResult = intent.getParcelableExtra("drive_result");
        for (int i = 0; i < mDrivePath.getSteps().size(); i++) {
            DriveStep step = mDrivePath.getSteps().get(i);
            List<TMC> tmclist = step.getTMCs();
            for (int j = 0; j < tmclist.size(); j++) {
                String s = ""+tmclist.get(j).getPolyline().size();
                Log.i("MY", s+tmclist.get(j).getStatus()
                        +tmclist.get(j).getDistance()
                        +tmclist.get(j).getPolyline().toString());
            }
        }
    }

    @OnClick(R.id.title_back)
    public void onClick() {
        finish();
    }
}
