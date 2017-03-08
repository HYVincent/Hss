package com.vincent.hss.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.config.Config;
import com.vincent.hss.fragment.FourFragment;
import com.vincent.hss.fragment.OneFragment;
import com.vincent.hss.fragment.ThreeFragment;
import com.vincent.hss.fragment.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.rl_tab_one)
    RelativeLayout rlTabOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.rl_tab_two)
    RelativeLayout rlTabTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.rl_tab_three)
    RelativeLayout rlTabThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.rl_tab_four)
    RelativeLayout rlTabFour;
    @BindView(R.id.rl_buttom_tab)
    LinearLayout rlButtomTab;
    @BindView(R.id.ll_main_content)
    LinearLayout llMainContent;
    private Fragment oneFragment, twoFragment, threeFragment, fourFragment, currentFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_content);
        ButterKnife.bind(this);
        setupWindowAnimations();
        initTab();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }


        /**
         * 初始化
         */
    private void initTab() {
        if (oneFragment == null) {
            oneFragment = new OneFragment();
        }
        if (!oneFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ll_main_content, oneFragment).commitAllowingStateLoss();

            // 记录当前Fragment
            currentFragment = oneFragment;
            // 设置图片文本的变化
            // 设置图片文本的变化
            ivOne.setImageResource(R.drawable.main_table_setting_blue);
            tvOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_blue));
            tvOne.setText(Config.APP_MAIN_TAB_TEXT_ONE);

            ivTwo.setImageResource(R.drawable.main_table_setting_gray);
            tvTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvTwo.setText(Config.APP_MAIN_TAB_TEXT_TWO);

            ivThree.setImageResource(R.drawable.main_table_setting_gray);
            tvThree.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvThree.setText(Config.APP_MAIN_TAB_TEXT_THREE);

            ivFour.setImageResource(R.drawable.main_table_setting_gray);
            tvFour.setText(Config.APP_MAIN_TAB_TEXT_FOUR);
            tvFour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.color_gray_1));
        }
    }

    private void toOneFragment(){
        if (oneFragment == null) {
            oneFragment = new OneFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), oneFragment);

        // 设置图片文本的变化
        ivOne.setImageResource(R.drawable.main_table_setting_blue);
        tvOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_blue));
        tvOne.setText(Config.APP_MAIN_TAB_TEXT_ONE);

        ivTwo.setImageResource(R.drawable.main_table_setting_gray);
        tvTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
        tvTwo.setText(Config.APP_MAIN_TAB_TEXT_TWO);

        ivThree.setImageResource(R.drawable.main_table_setting_gray);
        tvThree.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
        tvThree.setText(Config.APP_MAIN_TAB_TEXT_THREE);

        ivFour.setImageResource(R.drawable.main_table_setting_gray);
        tvFour.setText(Config.APP_MAIN_TAB_TEXT_FOUR);
        tvFour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.color_gray_1));
    }

    private void   toTwoFragment(){
        if (twoFragment == null) {
            twoFragment = new TwoFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), twoFragment);
            // 设置图片文本的变化
            ivOne.setImageResource(R.drawable.main_table_setting_gray);
            tvOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvOne.setText(Config.APP_MAIN_TAB_TEXT_ONE);

            ivTwo.setImageResource(R.drawable.main_table_setting_blue);
            tvTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_blue));
            tvTwo.setText(Config.APP_MAIN_TAB_TEXT_TWO);

            ivThree.setImageResource(R.drawable.main_table_setting_gray);
            tvThree.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvThree.setText(Config.APP_MAIN_TAB_TEXT_THREE);

            ivFour.setImageResource(R.drawable.main_table_setting_gray);
            tvFour.setText(Config.APP_MAIN_TAB_TEXT_FOUR);
            tvFour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.color_gray_1));
    }

    private void   toThreeFragment(){
        if (threeFragment == null) {
            threeFragment = new ThreeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), threeFragment);
            // 设置图片文本的变化
            ivOne.setImageResource(R.drawable.main_table_setting_gray);
            tvOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvOne.setText(Config.APP_MAIN_TAB_TEXT_ONE);

            ivTwo.setImageResource(R.drawable.main_table_setting_gray);
            tvTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvTwo.setText(Config.APP_MAIN_TAB_TEXT_TWO);

            ivThree.setImageResource(R.drawable.main_table_setting_blue);
            tvThree.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_blue));
            tvThree.setText(Config.APP_MAIN_TAB_TEXT_THREE);

            ivFour.setImageResource(R.drawable.main_table_setting_gray);
            tvFour.setText(Config.APP_MAIN_TAB_TEXT_FOUR);
            tvFour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.color_gray_1));
    }

    private void toFourFragment(){
        if (fourFragment == null) {
            fourFragment = new FourFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fourFragment);
            // 设置图片文本的变化
            ivOne.setImageResource(R.drawable.main_table_setting_gray);
            tvOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvOne.setText(Config.APP_MAIN_TAB_TEXT_ONE);

            ivTwo.setImageResource(R.drawable.main_table_setting_gray);
            tvTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvTwo.setText(Config.APP_MAIN_TAB_TEXT_TWO);

            ivThree.setImageResource(R.drawable.main_table_setting_gray);
            tvThree.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_gray_1));
            tvThree.setText(Config.APP_MAIN_TAB_TEXT_THREE);

            ivFour.setImageResource(R.drawable.main_table_setting_blue);
            tvFour.setText(Config.APP_MAIN_TAB_TEXT_FOUR);
            tvFour.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.color_blue));
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            /*transaction.hide(currentFragment)//Android 错误：IllegalStateException: Can not perform this action after onSaveInstanceState
                    .add(R.id.ll_main_content, fragment).commit();*/

            transaction.hide(currentFragment)
                    .add(R.id.ll_main_content, fragment).commitAllowingStateLoss();

        } else {
//            transaction.hide(currentFragment).show(fragment).commit();//Android 错误：IllegalStateException: Can not perform this action after onSaveInstanceState
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }
        currentFragment = fragment;
    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @OnClick({R.id.rl_tab_one, R.id.rl_tab_two, R.id.rl_tab_three, R.id.rl_tab_four})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_one:
                toOneFragment();
                break;
            case R.id.rl_tab_two:
                toTwoFragment();
                break;
            case R.id.rl_tab_three:
                toThreeFragment();
                break;
            case R.id.rl_tab_four:
                toFourFragment();
                break;
        }
    }
}
