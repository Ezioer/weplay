package com.example.weile.materialdesignexa.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.weile.materialdesignexa.themechange.MyThemeUtils;
import com.example.weile.materialdesignexa.themechange.PreferenceUtils;
import com.example.weile.materialdesignexa.handle.ActivityCollector;
import com.example.weile.materialdesignexa.util.Utils;

import butterknife.ButterKnife;

/**
 * Created by weile on 2016/12/16.
 */
public abstract class BaseActivity extends SwipeActivity {
    protected Context mContext=null;
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected PreferenceUtils preferenceUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferenceUtils=PreferenceUtils.getInstance(this);
        initTheme();
        super.onCreate(savedInstanceState);
        mContext=this;
        if(getContentLayoutId()!=0){
            setContentView(getContentLayoutId());
        }else{
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initViewAndEvent(savedInstanceState);
        ActivityCollector.getInstance().addActivity(this);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight=displayMetrics.heightPixels;
        mScreenWidth=displayMetrics.widthPixels;
    }

    private void initTheme() {
        MyThemeUtils.Theme theme=MyThemeUtils.getCurrentTheme(this);
        MyThemeUtils.changTheme(this,theme);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if(isneedfullscreen()){
            Utils.settranslucent(this);
        }
    }

    protected abstract boolean isneedfullscreen();

    @Override
    protected void onDestroy() {
        ActivityCollector.getInstance().removeActivity(this);
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    /**
     * 判断是否拥有权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    /**
     * 请求权限
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
        Toast.makeText(mContext,"如果拒绝授权,会导致应用无法正常使用",Toast.LENGTH_LONG).show();
    }
    /**
     * 请求权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 这里写你需要的业务逻辑
                    initData();
                } else {
                    Toast.makeText(mContext,"如果拒绝授权,会导致应用无法正常使用",Toast.LENGTH_LONG).show();
                }
                break;
            case 12:
                //另一个权限的回调
                break;
        }
    }
    protected void initData(){
    }
    protected void goActivity(Class<?> t){
        Intent intent=new Intent(this,t);
        startActivity(intent);

    }
    protected abstract int getContentLayoutId();
    protected abstract void initViewAndEvent(Bundle savedInstanceState);
}
