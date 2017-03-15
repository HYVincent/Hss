package com.vincent.hss.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.vincent.hss.R;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/14 20:00
 *
 * @version 1.0
 */

public class LoadingDialog extends Dialog {

    private SpinKitView spinKitView;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面View
        initView();
    }

    private void initView() {
        spinKitView = (SpinKitView)findViewById(R.id.spin_kit);
        //效果设置：https://github.com/ybq/Android-SpinKit
        Style style = Style.values()[7];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
    }
}
