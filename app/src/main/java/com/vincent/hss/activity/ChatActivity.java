package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.R;
import com.vincent.hss.adapter.ChatAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Family;
import com.vincent.hss.config.Config;
import com.vincent.hss.presenter.ChatPresenter;
import com.vincent.hss.presenter.controller.ChatController;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.NotificationUtil;
import com.vincent.hss.view.SpaceItemDecoration;
import com.vincent.lwx.netty.msg.ChatMsg;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：；；聊天页面
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 17:48
 *
 * @version 1.0
 */

public class ChatActivity extends BaseActivity implements ChatController.IView {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.chat_tv_send_msg)
    TextView chatTvSendMsg;
    @BindView(R.id.chat_et_input)
    EditText chatEtInput;
    @BindView(R.id.common_iv_no_content)
    ImageView commonIvNoContent;
    @BindView(R.id.common_tv_no_content)
    TextView commonTvNoContent;
    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;
    private Family family;
    @BindView(R.id.rlv_chat_show)
    RecyclerView rlvChatShow;

    private ChatPresenter presenter;
    private String ask_phone;
    private String chatContent;//从通知栏进入的 聊天记录最好是保存在服务器上，从服务器读取聊天记录
    private ChatMsg chatMsg;
    public final List<ChatMsg> dataList = new ArrayList<>();
//    public static List<ChatMsg> sendList = new ArrayList<>();
    private ChatAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        family = JSON.parseObject(getIntent().getStringExtra("family"), Family.class);
        presenter = new ChatPresenter(this);
        adapter = new ChatAdapter(this);
        if (family != null) {
            commonTvTitle2.setText(family.getRemark());
            ask_phone = family.getFamilyPhone();
        } else {
            Intent intent = getIntent();
            ask_phone = intent.getStringExtra("ask_phone");
            String chatContent = intent.getStringExtra("chatJson");
            ViseLog.d("chatJson-->"+chatContent);
            chatMsg = JSON.parseObject(chatContent, ChatMsg.class);
            refreshView(0,chatMsg);
            commonTvTitle2.setText(ask_phone);
        }
        commonTvNoContent.setText("暂无聊天记录，赶快聊起来吧");
        chatEtInput.addTextChangedListener(textWatcher);
        initRecycleView();
        EventUtil.register(this);
        BaseApplication.getShared().putString(Config.MSG.CHATING,ask_phone);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BaseApplication.getShared().putString(Config.MSG.CHATING,ask_phone);
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getShared().putString(Config.MSG.CHATING,"");
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getShared().putString(Config.MSG.CHATING,ask_phone);
        if(dataList!=null){
            if(dataList.size()!=0){
                System.out.println("nnnnnnnn "+ dataList.size());
                adapter.setChatDatas(dataList);
                rlvChatShow.setAdapter(adapter);
                //数据更新自动定位到最顶部的那条
                rlvChatShow.smoothScrollToPosition(dataList.size());
                rlvChatShow.setVisibility(View.VISIBLE);
                rlNoContent.setVisibility(View.GONE);
            }
        }else {
            rlvChatShow.setVisibility(View.GONE);
            rlNoContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptChatMsg(ChatMsg chatMsg){
        System.out.println("******");
        if(!BaseApplication.getShared().getString(Config.MSG.CHATING,"").equals(chatMsg.getPhoneNum())){
            NotificationUtil.sendNotificationChatMsg(ChatActivity.this,chatMsg.getAsk_phone(),JSON.toJSONString(chatMsg));
        }
        refreshView(0,chatMsg);
    }
*/

    /**
     * chatMS  ThreadMode.POSTING 表示和发送线程在同一线程
     * @param chatMsg
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
   public void showAskChatMSg(final ChatMsg chatMsg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!BaseApplication.getShared().getString(Config.MSG.CHATING,"").equals(chatMsg.getPhoneNum())){
                    NotificationUtil.sendNotificationChatMsg(ChatActivity.this,chatMsg.getAsk_phone(),JSON.toJSONString(chatMsg));
                }
                refreshView(0,chatMsg);
            }
        });
   }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>0){
                chatTvSendMsg.setClickable(true);
                chatTvSendMsg.setBackgroundResource(R.drawable.common_shape_background_blue);
            }else {
                chatTvSendMsg.setClickable(false);
                chatTvSendMsg.setBackgroundResource(R.drawable.shape_background_chat_gray);
            }
        }
    };

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //反转布局
//        layoutManager.setReverseLayout(true);
        rlvChatShow.setLayoutManager(layoutManager);
//        rcvFriendsList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rlvChatShow.addItemDecoration(new SpaceItemDecoration(10));
    }


    public static void actionStart(Context context, Family family) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("family", JSON.toJSONString(family));
        context.startActivity(intent);
    }


    @OnClick({R.id.common_rl_return_2, R.id.chat_tv_send_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.chat_tv_send_msg:
                if (family != null) {
                    ask_phone = family.getFamilyPhone();
                    presenter.sendChatMsg(BaseApplication.user.getPhone(), family.getFamilyPhone(), chatEtInput.getText().toString().trim());
                } else {
                    presenter.sendChatMsg(BaseApplication.user.getPhone(), ask_phone, chatEtInput.getText().toString().trim());
                }
                break;
        }
    }


    @Override
    public void msg(int code, String msg) {
        showMsg(code, msg);
    }



    @Override
    public void refreshView(int type,ChatMsg data) {
        if(data == null){
            showMsg(0,"data is null");
            return;
        }
        ViseLog.e("data-->data.phoneNum:"+data.getPhoneNum()+" data.ask_phone:"+data.getAsk_phone()+"data.type-->"+data.getMsgType()+" ask_phone:"+ask_phone);
        if(data!=null){
            ViseLog.d(data.getPhoneNum()+" ask_phone-"+data.getAsk_phone());
            if(type == 1){//表示发出去消息 发出去了就清空键盘
                if(data.getAsk_phone().equals(ask_phone)){
                    chatEtInput.setText("");
                    showMsg(1, "已发送");
                    dataList.add(data);
                    adapter.setChatDatas(dataList);
                    rlvChatShow.setAdapter(adapter);
                    //数据更新自动定位到最顶部的那条
                    rlvChatShow.smoothScrollToPosition(dataList.size());
                    rlvChatShow.setVisibility(View.VISIBLE);
                    rlNoContent.setVisibility(View.GONE);
                }
            }else {
                if(family!=null){
                    //注意这里拿getPhoneNum来比较
                    System.out.println("family is't null");
                    ViseLog.e("family.getFamilyPhone-->"+family.getFamilyPhone());
                    if(data.getPhoneNum().equals(family.getFamilyPhone())){
                        System.out.println("-------------");
                        dataList.add(data);
                        adapter.setChatDatas(dataList);
                        rlvChatShow.setAdapter(adapter);
                        //数据更新自动定位到最顶部的那条
                        rlvChatShow.smoothScrollToPosition(dataList.size());
                        rlvChatShow.setVisibility(View.VISIBLE);
                        rlNoContent.setVisibility(View.GONE);
//            adapter.notifyItemChanged();//局部更新
                    }else {
                        System.out.println("+++++++++++++++");
                    }
                }else if(data.getAsk_phone().equals(ask_phone)){//表示从通知栏进入本Activity的，
                    System.out.println("00000000000");
                    dataList.add(data);
                    adapter.setChatDatas(dataList);
                    rlvChatShow.setAdapter(adapter);
                    //数据更新自动定位到最顶部的那条
                    rlvChatShow.smoothScrollToPosition(dataList.size());
                    rlvChatShow.setVisibility(View.VISIBLE);
                    rlNoContent.setVisibility(View.GONE);
//            adapter.notifyItemChanged();//局部更新
                }
            }
        }else {
            System.out.println("-------------------1");
            rlvChatShow.setVisibility(View.GONE);
            rlNoContent.setVisibility(View.VISIBLE);
        }
    }
}
