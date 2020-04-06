package com.demo.livegift;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Popwindow工具类
 */
public class PopwindowUtil {
    public static PopupWindow getPopWindow(Context ctx, View targetView, int width, int height,int res) {
        PopupWindow popupWindow = new PopupWindow(targetView, width, height);
        popupWindow.setBackgroundDrawable(ctx.getResources().getDrawable(res));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.update();
        return popupWindow;
    }
}
