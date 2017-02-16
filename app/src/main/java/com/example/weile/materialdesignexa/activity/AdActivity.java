package com.example.weile.materialdesignexa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * Created by weile on 2017/2/14.
 */
public class AdActivity extends BaseActivity {
    @Bind(R.id.tv_jump)
    TextView mTvjump;
    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activityad;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        setSwipeEnabled(false);
        h.sendEmptyMessageDelayed(0,0);
        mTvjump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAd();
            }
        });
    }
    private int count=3;
    private Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(count==0){
                hideAd();
            }else {
                mTvjump.setText("跳过"+count+"秒");
                h.sendEmptyMessageDelayed(0,1000);
                count--;
            }
        }
    };
    private void hideAd(){
        Intent intent=new Intent(AdActivity.this,MainActivity.class);
        overridePendingTransition(R.anim.enter_alpha,R.anim.exit_alpha);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        h.removeCallbacksAndMessages(null);
    }
}
