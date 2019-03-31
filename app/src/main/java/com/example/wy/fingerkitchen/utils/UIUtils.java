package com.example.wy.fingerkitchen.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author LiLang
 * @date 2019/3/23
 */
public class UIUtils {

    public static int dpTopx(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setWindowStatusBarColor(Activity activity, int colorInt) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorInt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
