package com.example.weile.materialdesignexa.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weile on 2016/11/17.
 */
public class Utils {
    private Context mContext;
    public Utils(Context mContext){
        this.mContext=mContext;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Bitmap getBitmapOfVector(@DrawableRes int id, int height, int width) {
        Drawable vectorDrawable = mContext.getDrawable(id);
        if (vectorDrawable != null)
            vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        if (vectorDrawable != null)
            vectorDrawable.draw(canvas);
        return bm;
    }
    public int getWindowWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public static void setTagInt(Context context, String tag, int tagvalue) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(tag, tagvalue);
        editor.commit();
    }

    public static int getTagInt(Context context, String tag) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);

        int tagvalue = preferences.getInt(tag, 0);
        return tagvalue;
    }

    public static int getTagInt(Context context, String tag, int def) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        int tagvalue = preferences.getInt(tag, def);
        return tagvalue;
    }

    public static void setTagString(Context context, String tag, String value) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public static String getTagString(Context context, String tag) {
        if (context == null) {
            return getDate();
        }
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        String tagvalue = preferences.getString(tag, getDate());
        return tagvalue;
    }

    public static String getTagString(Context context, String tag, String defstr) {
        if (context == null) {
            return defstr;
        }
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        String tagvalue = preferences.getString(tag, defstr);
        return tagvalue;
    }


    public static long getTagLong(Context context, String tag, long def) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        long tagvalue = preferences.getLong(tag, def);
        return tagvalue;
    }

    public static void setTagLong(Context context, String tag, long value) {
        SharedPreferences preferences = context.getSharedPreferences("MOVIE",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(tag, value);
        editor.commit();
    }
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
    public static String getDayDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
    public static String getHisDate(int i) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        return df.format(new Date().getTime()-24*60*60*1000*i);// new Date()为获取当前系统时间
    }
    public static String getDay(Long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        return df.format(time);
    }
    public static String getTimeFormat(int dur){
        try {
            int time = dur;
            long minutes = time / 60;
            int seconds = time % 60;

            if (seconds < 10) {
                return String.valueOf(minutes) + ":0" + String.valueOf(seconds);
            } else {
                return String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return String.valueOf(0);
        }
    }
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    public static int dp2px(Context mContext, int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public static void settranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id
                    .content)).getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
        }
    }
    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }
    /**
     * 得到手机的缓存目录
     *
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }

        File cacheDir = context.getCacheDir();
        return cacheDir;
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {

        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
