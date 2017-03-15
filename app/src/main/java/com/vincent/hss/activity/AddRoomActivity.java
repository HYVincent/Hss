package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.vincent.hss.R;
import com.vincent.hss.adapter.ImagePickerAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.EventMsg;
import com.vincent.hss.bean.Room;
import com.vincent.hss.presenter.AddRoomPresenter;
import com.vincent.hss.presenter.controller.AddRoomController;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.view.PopupwindowUtils;
import com.vise.log.ViseLog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 19:00
 *
 * @version 1.0
 */

public class AddRoomActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener,AddRoomController.IView {

    @BindView(R.id.lv_1)
    ImageView addImg;
    @BindView(R.id.ll_content_root)
    LinearLayout llContent;
    @BindView(R.id.add_room_tv_class)
    TextView addRoomClass;
    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.rl_common_title_1)
    RelativeLayout rlCommonTitle1;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.add_room_et_name)
    EditText addRoomEtName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    private List<String> selectImagePath = new ArrayList<>();

    private boolean hasSelClass = false;//是否选择了房间类型
    private AddRoomPresenter presenter;

    private String roomType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        ButterKnife.bind(this);
        commonTvTitle2.setText("添加房间");
        commonTitleRight.setText("提交");
        commonTitleRight.setVisibility(View.VISIBLE);
        initAddImg();
        initImageSelect(false,true,9);
        presenter = new AddRoomPresenter(this);
    }

    private void initAddImg() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        //分为4列
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddRoomActivity.class);
        context.startActivity(intent);
    }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.lv_1,R.id.common_rl_return_2, R.id.common_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_1:
                hasSelClass = true;
                addImg.setImageDrawable(getDrawable(R.drawable.common_icon_arrow_buttom_reseda));
                List<String> data = new ArrayList<>();
                data.add("厨房");
                data.add("客厅");
                data.add("卧室");
                data.add("阳台");
                data.add("花园");
                data.add("院子");
                data.add("车库");
                PopupwindowUtils.showPopupwindow2(this, llContent, addRoomClass, data, "取消", addImg);
                break;

            case R.id.common_rl_return_2:
                   finish();
                break;
            case R.id.common_title_right:
                try {
                    if(!hasSelClass){
                        showMsg(0,"没有选择房间，默认为厨房");
                        roomType = "厨房";
                    }
                    String roomName = addRoomEtName.getText().toString().trim();
                    String roomClass = addRoomClass.getText().toString().trim();
                    if(TextUtils.isEmpty(roomName)){
                        showMsg(0,"写个有意义的名字吧");
                        return;
                    }
                    if(selImageList.size()>0&&selImageList != null){
                        for (int i=0;i<selImageList.size();i++){
                            selectImagePath.add(selImageList.get(i).path);
                        }
                    }
                    int roomIcon = 0;
                    if(selectImagePath.size()>0){
                        //保存房间
                        switch (roomClass){
                            case "厨房":
                                roomIcon = R.drawable.common_icon_room_type_kitchen;
                                roomType = "厨房";
                                break;
                            case "客厅":
                                roomIcon = R.drawable.common_icon_room_type_drawing_room;
                                roomType = "客厅";
                                break;
                            case "卧室":
                                roomIcon = R.drawable.common_icon_room_type_bedroom;
                                roomType = "卧室";
                                break;
                            case "阳台":
                                roomIcon = R.drawable.common_icon_room_type_balcony;
                                roomType = "阳台";
                                break;
                            case "花园":
                                roomType = "花园";
                                roomIcon = R.drawable.common_icon_room_type_garden;
                                break;
                            case "院子":
                                roomType = "院子";
                                roomIcon = R.drawable.common_icon_room_type_backyard;
                                break;
                            case "车库":
                                roomType= "车库";
                                roomIcon = R.drawable.common_icon_room_type_garage;
                                break;
                        }
                        Room room = new Room();
                        room.setRoomName(roomName);
                        room.setRoomType(roomType);
                        room.setRoomImg(String.valueOf(roomIcon));
                        String json = JSON.toJSONString(selectImagePath);
                        System.out.println("json-->"+json);
                        room.setRoomBigImg(json);
                        room.setPhone(BaseApplication.user.getPhone());
                       ViseLog.d("--->"+room.toString());
                        //TODO 请求服务器添加
                        presenter.addRoom(room);
                    }else {
                        showMsg(0,"拍个房间照片吧");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    showMsg(0,"房间添加失败");
                }
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //TODO 如果预览会导致空指针崩溃 以后在解决
              /*  //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);*/
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ViseLog.d("path-->"+images.get(0).path);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void addSuccess(final Room room) {
        //TODO 把room保存在本地数据库
        room.save();
        EventUtil.post(new EventMsg("refresh","1"));
        finish();
    }
}
