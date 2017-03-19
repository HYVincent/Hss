package com.vincent.hss.presenter;

import com.vincent.hss.adapter.ChatAdapter;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.ChatController;
import com.vincent.lwx.netty.msg.ChatMsg;
import com.vincent.lwx.netty.msg.MsgType;
import com.vise.log.ViseLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 19:11
 *
 * @version 1.0
 */

public class ChatPresenter implements ChatController.IPresenter {

    private ChatController.IView view;

    public ChatPresenter(ChatController.IView view) {
        this.view = view;
    }

    @Override
    public void sendChatMsg(final String phone, final String ask_phone, final String chatContent) {
        Call<Result> call = RetrofitUtils.getApiService().sendChatMsg(phone,ask_phone,chatContent);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setPhoneNum(phone);
                    chatMsg.setAsk_phone(ask_phone);
                    chatMsg.setType(MsgType.CHAT);
                    chatMsg.setChatContent(chatContent);
                    chatMsg.setMsgType("1");
                    view.refreshView(chatMsg);
                    view.msg(1,"已发送");
                }else{
                    view.msg(0,result.getMsg());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.msg(0,"发送失败");
                ViseLog.d(t.getMessage());
            }
        });
    }
}
