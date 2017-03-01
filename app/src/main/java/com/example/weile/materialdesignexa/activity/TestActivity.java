package com.example.weile.materialdesignexa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.widget.CacheManager;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import butterknife.Bind;

/**
 * Created by weile on 2017/2/23.
 */
public class TestActivity  extends BaseActivity{
    public static final int REQUEST_CODE_AVATAR = 100;
    public static final String AVATAR_FILE_NAME = "avatar.png";
    @Bind(R.id.btn_getpic)
    Button mGetPic;
    @Bind(R.id.iv_pic)
    ImageView mPic;
    @Override
    protected boolean isneedfullscreen() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activitytest;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        mGetPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SImagePicker
                        .from(TestActivity.this)
                        .pickMode(SImagePicker.MODE_AVATAR)
                        .cropFilePath(
                                CacheManager.getInstance().getImageInnerCache()
                                        .getAbsolutePath(AVATAR_FILE_NAME))
                        .forResult(REQUEST_CODE_AVATAR);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
           if(requestCode==REQUEST_CODE_AVATAR){
               Glide.with(mContext).load(CacheManager.getInstance().getImageInnerCache()
                       .getAbsolutePath(AVATAR_FILE_NAME)).into(mPic);
           }
        }
    }
}
