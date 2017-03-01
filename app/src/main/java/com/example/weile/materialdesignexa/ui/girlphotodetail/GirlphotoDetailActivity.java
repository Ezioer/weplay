package com.example.weile.materialdesignexa.ui.girlphotodetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.util.ThreadTask;
import com.example.weile.materialdesignexa.widget.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;

/**
 * Created by weile on 2017/2/23.
 */
public class GirlphotoDetailActivity extends BaseActivity {
    @Bind(R.id.iv_girlphoto)
    PhotoView mPic;
    @Bind(R.id.tv_down)
    TextView mDown;
    String url;
    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_girlphotodetail;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        url=getIntent().getExtras().getString("picurl");
        Glide.with(this).load(url).animate(R.anim.ani_fade).diskCacheStrategy(DiskCacheStrategy.ALL).into(mPic);
        mPic.enable();
        mDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downPic();
            }
        });
    }

    private void downPic() {
        final BitmapFactory.Options options=new BitmapFactory.Options();
        ThreadTask.getInstance().executorOtherThread(new Runnable() {
            @Override
            public void run() {
                final String imageurl=getImageUrl(url);
                GirlphotoDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(imageurl!=null){
                            Bitmap bitmap = BitmapFactory.decodeFile(imageurl,options);
                            if(bitmap!=null){
                                saveImage(bitmap);
                                Snackbar.make(mPic,"图片已保存到"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/保存照片",2000).show();
                            }
                        }
                    }
                });
            }
        },ThreadTask.ThreadPeriod.PERIOD_MIDDLE);
    }

    //保存图片到本地
    private void saveImage(Bitmap bitmap) {
        File dir=new File(Environment.getExternalStorageDirectory(),"保存图片");
        if(!dir.exists()){
            dir.mkdir();
        }
        String filename= System.currentTimeMillis()+".jpg";
        File file=new File(dir,filename);
        try {
            FileOutputStream outputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        //通知图库更新
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
    }

    //获得网络图片的url
    private String getImageUrl(String url) {
        String path=null;
        FutureTarget<File> futureTarget=Glide.with(mContext).load(url).downloadOnly(500,500);
        try {
            File cache=futureTarget.get();
            path=cache.getAbsolutePath();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        return path;
    }
}
