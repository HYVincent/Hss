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

import com.vincent.hss.R;
import com.vincent.hss.adapter.SearchUserListAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.User;
import com.vincent.hss.presenter.SearchFamilyPresenter;
import com.vincent.hss.presenter.controller.SearchFamilyController;
import com.vincent.hss.view.CommonOnClickListener;
import com.vincent.hss.view.SpaceItemDecoration;
import com.vise.log.ViseLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/18 22:37
 *
 * @version 1.0
 */

public class SearchFamilyActivity extends BaseActivity implements SearchFamilyController.IView{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.search_family_input)
    EditText searchFamilyInput;
    @BindView(R.id.searchO_family_start)
    ImageView searchOFamilyStart;
    @BindView(R.id.search_user_result_list)
    RecyclerView searchUserResultList;

    private List<User> users;
    private SearchUserListAdapter adapter;
    private SearchFamilyPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_family);
        ButterKnife.bind(this);
        commonTvTitle.setText("搜索家人");
        adapter = new SearchUserListAdapter(this);
        presenter = new SearchFamilyPresenter(this);
        searchFamilyInput.addTextChangedListener(textWatcher);
        adapter.setOnClick(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                User user = users.get(position);
                SearchResultDetailActivity.actionStart(SearchFamilyActivity.this,user);
            }
        });
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchUserResultList.setLayoutManager(layoutManager);
        searchUserResultList.addItemDecoration(new SpaceItemDecoration(10));
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchFamilyActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_rl_return, R.id.searchO_family_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.searchO_family_start:
                if(searchFamilyInput.getText().toString().length()<6){
                    showMsg(0,"号码必须要6位以上");
                    return;
                }
                presenter.search(searchFamilyInput.getText().toString().trim());
                break;
        }
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
            ViseLog.d("输入的内容为："+s.toString());
            if(s.length()>5){
                presenter.search(s.toString());
            }
        }
    };

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void refreshView(List<User> list) {
        if(list!=null){
            users = list;
            adapter.setListData(list);
            searchUserResultList.setAdapter(adapter);
        }else{
            showMsg(0,"没有数据");
        }
    }
}
