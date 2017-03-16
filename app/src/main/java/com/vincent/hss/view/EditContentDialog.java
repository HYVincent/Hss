package com.vincent.hss.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 23:25
 *
 * @version 1.0
 */

public class EditContentDialog extends Dialog {

    private RelativeLayout finish,save;
    private TextView title;
    private EditText etContent;
    private ContentListener contentListener;

    public EditContentDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
    }

    public void setContentListener(ContentListener contentListener) {
        this.contentListener = contentListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_content);
        title = (TextView)findViewById(R.id.common_tv_title);
        etContent = (EditText)findViewById(R.id.et_edit_content);
        finish = (RelativeLayout)findViewById(R.id.common_rl_return);
        save = (RelativeLayout)findViewById(R.id.rl_content_save);
        title.setText("编辑");
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentListener.getContent(etContent.getText().toString().trim());
                dismiss();
            }
        });
    }

    /*设置title*/
    public void setTitle(String titleStr) {
        title.setText(titleStr);
    }

    public interface ContentListener{

        void getContent(String content);

    }

}
