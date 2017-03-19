package com.vincent.hss.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinping.iosdialog.dialogsamples.utils.T;
import com.vincent.hss.R;
import com.vincent.hss.adapter.CommonSingleTextAdapter;
import com.vincent.hss.adapter.RoomClassListAdapter;
import com.vincent.hss.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/10 1:41
 *
 * @version 1.0
 */

public class WindowUtils {


    /**
     * 弹出窗口 选择文字 并设置到tv上
     *
     * @param activity
     * @param view
     * @param tv
     * @param data
     */
    public static void showPopupwindow2(final Activity activity, View view, final TextView tv, final List<String> data, String hintText, final ImageView lv) {
        View contentView = LayoutInflater.from(activity).inflate(R.layout.item_layout_room_class, null);
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
        TextView tvHint = (TextView) contentView.findViewById(R.id.item_popupwindow_text);
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
//        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        pop.showAsDropDown(view,0,15,Gravity.CENTER);
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

    /**
     * 弹出window
     *
     * @param activity   上下文对象
     * @param title      title
     * @param content    内容
     * @param cancelText 取消
     * @param okText     确定
     */
    public static void showAlertDialog(Activity activity, String title, String content, String cancelText, String okText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.item_msg_detail, null);
        builder.setView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.item_pop_title);
        TextView tvContent = (TextView) view.findViewById(R.id.item_pop_content);
        TextView tvCancel = (TextView) view.findViewById(R.id.item_pop_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.item_pop_ok);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvOk.setText(okText);
        tvCancel.setText(cancelText);
        final Dialog dialog = builder.create();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 公共的输入框，填写内容
     * @param activity
     * @param listener
     */
    public static void editContentDialog(final Activity activity, String hintText, final GetContentListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.dialog_style);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewDialog = inflater.inflate(R.layout.layout_edit_content, null);
        TextView textView = (TextView)viewDialog.findViewById(R.id.common_tv_title);
        final EditText content = (EditText)viewDialog.findViewById(R.id.et_edit_content);
        textView.setText(hintText);
        RelativeLayout finish = (RelativeLayout)viewDialog.findViewById(R.id.common_rl_return);
        RelativeLayout save = (RelativeLayout)viewDialog.findViewById(R.id.rl_content_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  contentStr = content.getText().toString().trim();
                if(TextUtils.isEmpty(contentStr)){
                    Toast.makeText(activity,"尚未输入", Toast.LENGTH_LONG).show();
                }else {
                    listener.content(contentStr);
                    dialog.dismiss();
                }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        int width = ScreenUtils.getScreenWidth(activity);
        int height = ScreenUtils.getScreenHeight(activity);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);
        dialog.show();
    }

    public interface GetContentListener{
        void content(String inputStr);
    }


    /**
     * 弹出菜单，选择单个文字
     * @param activity
     * @param listData
     */
    public static void selectSingleText(final Activity activity, View showAsView, final List<String> listData, final SelectTextListener listener){
        final PopupWindow popupWindow = new PopupWindow(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_popup_select,null);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//显示大小);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rlv_item);
        final TextView tvCancel = (TextView)view.findViewById(R.id.layout_calcel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        CommonSingleTextAdapter adapter = new CommonSingleTextAdapter(activity);
        recyclerView.setAdapter(adapter);
        adapter.setData(listData);
        adapter.setClickListener(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                listener.getContent(listData.get(position));
                popupWindow.dismiss();
            }
        });
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
    }
    public interface SelectTextListener{
        void getContent(String contentStr);
    }

    /**
     * 显示app更新
     */
    public static void checkNewestVersion(Activity activity, String updateDesc, final CommonOnClickListener listener){
        final Dialog dialog = new Dialog(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewDialog = inflater.inflate(R.layout.dlg_show_update, null);
        TextView tvShowUpdateDesc = (TextView)viewDialog.findViewById(R.id.tv_show_update_desc);
        TextView tvCancelUpdate = (TextView)viewDialog.findViewById(R.id.tv_cancel_down);
        TextView  tvDownUpdate = (TextView)viewDialog.findViewById(R.id.tv_down_update);
        tvShowUpdateDesc.setText(updateDesc);
        tvCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvDownUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,0);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        int width = ScreenUtils.getScreenWidth(activity)/6*5;
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(viewDialog, layoutParams);
        dialog.show();
    }

    /**
     * 弹出分享窗口
     * @param activity
     * @param view
     * @param qqFriendListener
     * @param qzoneListener
     */
    public static void showAppShared(final Activity activity, View view,final CommonOnClickListener qqFriendListener, final CommonOnClickListener qzoneListener){
        final PopupWindow popupWindow = new PopupWindow(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewDialog = inflater.inflate(R.layout.pop_layout_shared, null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//显示大小);
        popupWindow.setContentView(viewDialog);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        RelativeLayout rlQQFriends = (RelativeLayout)viewDialog.findViewById(R.id.rl_qq_friend);
        RelativeLayout rlQzone = (RelativeLayout)viewDialog.findViewById(R.id.rl_qqzone);
        rlQQFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqFriendListener.onClick(v,0);
                popupWindow.dismiss();
            }
        });
        rlQzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qzoneListener.onClick(v,0);
                popupWindow.dismiss();
            }
        });
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

        popupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失
        popupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        popupWindow.showAsDropDown(view,0,30,Gravity.CENTER);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
    }


}
