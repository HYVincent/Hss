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
import com.vincent.hss.presenter.ChatPresenter;
import com.vincent.hss.presenter.controller.ChatController;
import com.vincent.hss.utils.EventUtil;
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
 * description ：
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
    private static List<ChatMsg> dataList = new ArrayList<>();
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
        } else {
            Intent intent = getIntent();
            ask_phone = intent.getStringExtra("ask_phone");
            String chatContent = intent.getStringExtra("chatJson");
            ViseLog.d("chatJson-->"+chatContent);
            chatMsg = JSON.parseObject(chatContent, ChatMsg.class);
            refreshView(chatMsg);
            commonTvTitle2.setText(ask_phone);
        }
        commonTvNoContent.setText("暂无聊天记录，赶快聊起来吧");
        chatEtInput.addTextChangedListener(textWatcher);
        initRecycleView();
        EventUtil.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptChatMsg(ChatMsg chatMsg){
        refreshView(chatMsg);
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
        rlvChatShow.setLayoutManager(layoutManager);
//        rcvFriendsList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rlvChatShow.addItemDecoration(new SpaceItemDecoration(10));
        rlvChatShow.setVisibility(View.GONE);
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
    public void refreshView(ChatMsg data) {
        if(data!=null){
            chatEtInput.setText("");
            //TODO 发送成功就提示一下
            showMsg(1, "已发送");
            dataList.add(data);
            adapter.setChatDatas(dataList);
            rlvChatShow.setAdapter(adapter);
            rlvChatShow.setVisibility(View.VISIBLE);
            rlNoContent.setVisibility(View.GONE);
        }else {
            rlNoContent.setVisibility(View.GONE);
            rlNoContent.setVisibility(View.VISIBLE);
        }
    }
}
