package com.vincent.hss.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.adapter.RoomClassListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/10 1:41
 *
 * @version 1.0
 */

public class PopupwindowUtils {

    private static PopupWindow pop;

    public static String currentSelectText=null;


    public static PopupWindow showPopupwindow(final Activity activity,View view,TextView tv, final List<String> data){
        View contentView;
        LayoutInflater mLayoutInflater = LayoutInflater.from(activity);
        contentView = mLayoutInflater.inflate(R.layout.item_layout_room_class,
                null);
        pop = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, (int) activity.getResources().getDimension(R.dimen.size_120));
        RecyclerView rlvRoomClass = (RecyclerView)contentView.findViewById(R.id.rlv_room_class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RoomClassListAdapter adapter = new RoomClassListAdapter(activity);
        rlvRoomClass.setLayoutManager(layoutManager);
        adapter.setData(data);
        adapter.setClickListener(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                currentSelectText = data.get(position);
            }
        });
        rlvRoomClass.setAdapter(adapter);
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
//        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        pop.update();
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
        return pop;
    }

    public static String getCurrentSelectText() {
        return currentSelectText;
    }

    /**
     * 弹出窗口 选择文字 并设置到tv上
     * @param activity
     * @param view
     * @param tv
     * @param data
     */
    public static void showPopupwindow2(final Activity activity, View view, final TextView tv, final List<String> data, String hintText, final ImageView lv) {
        View contentView = LayoutInflater.from(activity).inflate(R.layout.item_layout_room_class,null);
        final PopupWindow pop = new PopupWindow(activity);
        pop.setContentView(contentView);
       /* pop.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ScreenUtils.getScreenWidth(activity));*/
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//显示大小);
        RecyclerView rlvRoomClass = (RecyclerView) contentView.findViewById(R.id.rlv_room_class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvRoomClass.addItemDecoration(new SpaceItemDecoration(5));
        RoomClassListAdapter adapter = new RoomClassListAdapter(activity);
        rlvRoomClass.setLayoutManager(layoutManager);
        adapter.setData(data);
        TextView tvHint = (TextView)contentView.findViewById(R.id.item_popupwindow_text);
        tvHint.setText(hintText);
        tvHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        adapter.setClickListener(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                String text = data.get(position);
                tv.setText(text);
                pop.dismiss();
            }
        });
        rlvRoomClass.setAdapter(adapter);
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.anim_menu_bottombar);
        pop.showAtLocation(view,Gravity.BOTTOM,0,0);
        pop.update();
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
                lv.setImageDrawable(activity.getDrawable(R.drawable.common_icon_arrow_right_resda));
            }
        });
    }
}
