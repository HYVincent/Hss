package com.vincent.hss.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/2/26 23:06
 *
 * @version 1.0
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view)!=0){
            outRect.top = space;
        }

    }
}
