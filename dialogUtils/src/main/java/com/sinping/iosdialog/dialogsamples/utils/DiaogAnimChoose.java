package com.sinping.iosdialog.dialogsamples.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.sinping.iosdialog.animation.Attention.Flash;
import com.sinping.iosdialog.animation.Attention.RubberBand;
import com.sinping.iosdialog.animation.Attention.ShakeHorizontal;
import com.sinping.iosdialog.animation.Attention.ShakeVertical;
import com.sinping.iosdialog.animation.Attention.Swing;
import com.sinping.iosdialog.animation.Attention.Tada;
import com.sinping.iosdialog.animation.BaseAnimatorSet;
import com.sinping.iosdialog.animation.BounceEnter.BounceBottomEnter;
import com.sinping.iosdialog.animation.BounceEnter.BounceEnter;
import com.sinping.iosdialog.animation.BounceEnter.BounceLeftEnter;
import com.sinping.iosdialog.animation.BounceEnter.BounceRightEnter;
import com.sinping.iosdialog.animation.BounceEnter.BounceTopEnter;
import com.sinping.iosdialog.animation.FadeEnter.FadeEnter;
import com.sinping.iosdialog.animation.FadeExit.FadeExit;
import com.sinping.iosdialog.animation.FallEnter.FallEnter;
import com.sinping.iosdialog.animation.FallEnter.FallRotateEnter;
import com.sinping.iosdialog .animation.FlipEnter.FlipBottomEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipHorizontalEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipHorizontalSwingEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipLeftEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipRightEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipTopEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipVerticalEnter;
import com.sinping.iosdialog.animation.FlipEnter.FlipVerticalSwingEnter;
import com.sinping.iosdialog.animation.FlipExit.FlipHorizontalExit;
import com.sinping.iosdialog.animation.FlipExit.FlipVerticalExit;
import com.sinping.iosdialog.animation.Jelly;
import com.sinping.iosdialog.animation.NewsPaperEnter;
import com.sinping.iosdialog.animation.SlideEnter.SlideBottomEnter;
import com.sinping.iosdialog.animation.SlideEnter.SlideLeftEnter;
import com.sinping.iosdialog.animation.SlideEnter.SlideRightEnter;
import com.sinping.iosdialog.animation.SlideEnter.SlideTopEnter;
import com.sinping.iosdialog.animation.SlideExit.SlideBottomExit;
import com.sinping.iosdialog.animation.SlideExit.SlideLeftExit;
import com.sinping.iosdialog.animation.SlideExit.SlideRightExit;
import com.sinping.iosdialog.animation.SlideExit.SlideTopExit;
import com.sinping.iosdialog.animation.ZoomEnter.ZoomInBottomEnter;
import com.sinping.iosdialog.animation.ZoomEnter.ZoomInEnter;
import com.sinping.iosdialog.animation.ZoomEnter.ZoomInLeftEnter;
import com.sinping.iosdialog.animation.ZoomEnter.ZoomInRightEnter;
import com.sinping.iosdialog.animation.ZoomEnter.ZoomInTopEnter;
import com.sinping.iosdialog.animation.ZoomExit.ZoomInExit;
import com.sinping.iosdialog.animation.ZoomExit.ZoomOutBottomExit;
import com.sinping.iosdialog.animation.ZoomExit.ZoomOutExit;
import com.sinping.iosdialog.animation.ZoomExit.ZoomOutLeftExit;
import com.sinping.iosdialog.animation.ZoomExit.ZoomOutRightExit;
import com.sinping.iosdialog.animation.ZoomExit.ZoomOutTopExit;
import com.sinping.iosdialog.dialog.listener.OnOperItemClickL;
import com.sinping.iosdialog.dialog.widget.ActionSheetDialog;
import com.sinping.iosdialog.dialogsamples.ui.DialogHomeActivity;

import java.util.ArrayList;

public class DiaogAnimChoose {
    public static void showAnim(final Context context) {
        final Class<?> cs[] = {BounceEnter.class,//
                BounceTopEnter.class,//
                BounceBottomEnter.class,//
                BounceLeftEnter.class,//
                BounceRightEnter.class,//
                FlipHorizontalEnter.class,//
                FlipHorizontalSwingEnter.class,//
                FlipVerticalEnter.class,//
                FlipVerticalSwingEnter.class,//
                FlipTopEnter.class,//
                FlipBottomEnter.class,//
                FlipLeftEnter.class,//
                FlipRightEnter.class,//
                FadeEnter.class, //
                FallEnter.class,//
                FallRotateEnter.class,//
                SlideTopEnter.class,//
                SlideBottomEnter.class,//
                SlideLeftEnter.class, //
                SlideRightEnter.class,//
                ZoomInEnter.class,//
                ZoomInTopEnter.class,//
                ZoomInBottomEnter.class,//
                ZoomInLeftEnter.class,//
                ZoomInRightEnter.class,//

                NewsPaperEnter.class,//
                Flash.class,//
                ShakeHorizontal.class,//
                ShakeVertical.class,//
                Jelly.class,//
                RubberBand.class,//
                Swing.class,//
                Tada.class,//
        };

        ArrayList<String> itemList = new ArrayList<String>();
        for (Class<?> c : cs) {
            itemList.add(c.getSimpleName());
        }

        final String[] contents = new String[itemList.size()];
        final ActionSheetDialog dialog = new ActionSheetDialog(context, //
                itemList.toArray(contents), null);
        dialog.title("使用内置show动画设置对话框显示动画\r\n指定对话框将显示效果")//
                .titleTextSize_SP(14.5f)//
                .layoutAnimation(null)//
                .show();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String animType = contents[position];
                    ((DialogHomeActivity) context).setBasIn((BaseAnimatorSet) cs[position].newInstance());
                    com.sinping.iosdialog.dialogsamples.utils.T.showShort(context, animType + "设置成功");
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void dissmissAnim(final Context context) {
        final Class<?> cs[] = {FlipHorizontalExit.class,//
                FlipVerticalExit.class,//
                FadeExit.class,//
                SlideTopExit.class,//
                SlideBottomExit.class,//
                SlideLeftExit.class, //
                SlideRightExit.class,//
                ZoomOutExit.class,//
                ZoomOutTopExit.class,//
                ZoomOutBottomExit.class,//
                ZoomOutLeftExit.class,//
                ZoomOutRightExit.class,//
                ZoomInExit.class,//
        };

        ArrayList<String> itemList = new ArrayList<String>();
        for (Class<?> c : cs) {
            itemList.add(c.getSimpleName());
        }

        final String[] contents = new String[itemList.size()];
        final ActionSheetDialog dialog = new ActionSheetDialog(context, //
                itemList.toArray(contents), null);
        dialog.title("使用内置dismiss动画设置对话框消失动画\r\n指定对话框将消失效果")//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String animType = contents[position];
                    ((DialogHomeActivity) context).setBasOut((BaseAnimatorSet) cs[position].newInstance());
                    com.sinping.iosdialog.dialogsamples.utils.T.showShort(context, animType + "设置成功");
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
