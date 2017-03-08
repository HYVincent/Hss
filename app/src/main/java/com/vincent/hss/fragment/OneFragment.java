package com.vincent.hss.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincent.hss.R;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 20:17
 *
 * @version 1.0
 */

public class OneFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_layout_one,null);

        return view;
    }
}
