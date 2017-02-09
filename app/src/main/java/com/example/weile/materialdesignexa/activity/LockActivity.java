package com.example.weile.materialdesignexa.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.async.Action;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.util.ColorCache;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.Tag;
import com.example.weile.materialdesignexa.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;

/**
 * Created by weile on 2016/12/2.
 */
public class LockActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_bigalbumpic)
    ImageView mIvMainAlbumPic;
    @Bind(R.id.tv_bignext)
    TextView mTvMainNext;
    @Bind(R.id.tv_bigpause)
    TextView mTvMainPause;
    @Bind(R.id.tv_bigpre)
    TextView mTvMainPre;
    @Bind(R.id.tv_bigsongname)
    TextView mBigSongName;
    @Bind(R.id.tv_bigartistname)
    TextView mBigArtistName;
    @Bind(R.id.ll_playcontrol)
    LinearLayout mPlayerControl;
    @Bind(R.id.pb_playdur)
    SeekBar mSeekBar;
    private String songname;
    private String artist;
    private Long albumid;
    private int seekbarmax=1000;
    private SongUtils.PlayState mState;
    private static final String TAG = "minibackground";
    private static final long ANIM_DUR = 500;
    private boolean onceAnimated;
    private BroadcastReceiver lockBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Tag.CURRENT_PLAYSONG)) {
                updateLock(intent);
            }
        }
    };

    private void updateLock(final Intent intent) {
        Log.e("ee", "ee");
        mState = SongUtils.PlayState.PLAYING;
        update(intent.getStringExtra("songname"), intent.getStringExtra("artist"), intent
                .getLongExtra("albumid", 0));
    }

    private void update(String songname, String artist, final Long albumid) {
        mBigSongName.setText(songname);
        mBigArtistName.setText(artist);
        String path = SongUtils.getAlbumArt(this, albumid);
        if (path != null) {
            Picasso.with(mContext).load(new File(path)).into(mIvMainAlbumPic);
        } else {
            mIvMainAlbumPic.setImageResource(R.mipmap.g1);
        }
        if (ColorCache.getinstance().getLru().get(albumid) != null) {
            int[] colors = ColorCache.getinstance().getLru().get(albumid);
            mPlayerControl.setBackgroundColor(colors[0]);
            mBigSongName.setTextColor(colors[1]);
            mBigArtistName.setTextColor(colors[2]);
        } else {
            new ArtHandler(path, mPlayerControl.getHeight(), albumid,
                    0, mPlayerControl, mBigSongName, mBigArtistName) {
                @Override
                public void onColorFetched(int[] colors, long albumId) {
                    ColorCache.getinstance().getLru().put(albumid,
                            colors);
                }
            }.execute();
        }
    }

    private void initListener() {
        mTvMainNext.setOnClickListener(this);
        mTvMainPause.setOnClickListener(this);
        mTvMainPre.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if(progress>=seekbarmax/2){
                        finish();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_bignext:
                intent = new Intent();
                intent.setAction(PlayService.ACTION_NEXT_SONG);
                mContext.sendBroadcast(intent);
                intent = null;
                break;
            case R.id.tv_bigpause:
                intent = new Intent();
                intent.setAction(PlayService.ACTION_PAUSE_SONG);
                mContext.sendBroadcast(intent);
                if (mState == SongUtils.PlayState.PLAYING) {
                    mState = SongUtils.PlayState.PAUSE;
                    mTvMainPause.setBackgroundResource(R.mipmap.ic_play_arrow_white_36dp);
                } else {
                    mState = SongUtils.PlayState.PLAYING;
                    mTvMainPause.setBackgroundResource(R.mipmap.ic_pause_white_36dp);
                }
                intent = null;
                break;
            case R.id.tv_bigpre:
                intent = new Intent();
                intent.setAction(PlayService.ACTION_PREV_SONG);
                mContext.sendBroadcast(intent);
                intent = null;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lockBr);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_bigplayer;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        setSwipeEnabled(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        /*final Window win = getWindow();
        win.addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//解除系统锁屏*/
        IntentFilter fli = new IntentFilter();
        fli.addAction(Tag.CURRENT_PLAYSONG);
        registerReceiver(lockBr, fli);
        initListener();
        mSeekBar.setMax(seekbarmax);
        songname = Utils.getTagString(mContext, "songname", "");
        artist = Utils.getTagString(mContext, "artist", "");
        albumid = Utils.getTagLong(mContext, "albumid", 0);
        update(songname, artist, albumid);
    }

    private class ArtHandler extends Action {

        private Bitmap bitmap;
        private String path;
        private int size;
        private long albumId;
        private int position;
        private ValueAnimator colorAnimation, colorAnimation1, colorAnimation2;
        private LinearLayout mMain;
        private TextView view1, view2;

        public ArtHandler(String path,
                          int size, long albumId, int position, LinearLayout group, TextView
                                  view1, TextView view2) {
            this.path = path;
            this.size = size;
            this.albumId = albumId;
            this.position = position;
            this.mMain = group;
            this.view1 = view1;
            this.view2 = view2;
        }

        @NonNull
        @Override
        public String id() {
            return TAG;
        }

        @Nullable
        @Override
        protected Object run() throws InterruptedException {
            if (!onceAnimated)
                Thread.sleep(ANIM_DUR + (100 * position));
            getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    final int[] colors = getAvailableColor(palette);
                    onColorFetched(colors, albumId);
                    mMain.setBackgroundColor(colors[0]);
                    view1.setTextColor(colors[1]);
                    view2.setTextColor(colors[2]);
                    animateViews(colors[0], colors[1], colors[2]);
                }
            });
            return null;
        }

        @Override
        protected void done(@Nullable Object result) {
//            ((ImageView)holder.getView(R.id.iv_ablum)).setImageBitmap(bitmap);
        }

        private void animateViews(int colorBg, int colorName, int colorArtist) {
            colorAnimation = setAnimator(0xffe5e5e5,
                    colorBg);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mMain.setBackgroundColor((Integer) animator
                            .getAnimatedValue());
                }

            });
            colorAnimation.start();
            colorAnimation1 = setAnimator(ContextCompat.getColor(mContext,
                    R.color.album_grid_name_default),
                    colorName);
            colorAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view1.setTextColor((Integer)
                            animator.getAnimatedValue());
                }
            });
            colorAnimation1.start();
            colorAnimation2 = setAnimator(ContextCompat.getColor(mContext,
                    R.color.album_grid_artist_default),
                    colorArtist);
            colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view2.setTextColor((Integer)
                            animator.getAnimatedValue());
                }
            });
            colorAnimation2.start();
        }

        private ValueAnimator setAnimator(int colorFrom, int colorTo) {
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom,
                    colorTo);
            long duration = 800;
            colorAnimation.setDuration(duration);
            return colorAnimation;
        }

        private int[] getAvailableColor(Palette palette) {
            int[] temp = new int[3];
            if (palette.getVibrantSwatch() != null) {
                temp[0] = palette.getVibrantSwatch().getRgb();
                temp[1] = palette.getVibrantSwatch().getBodyTextColor();
                temp[2] = palette.getVibrantSwatch().getTitleTextColor();
            } else if (palette.getDarkVibrantSwatch() != null) {
                temp[0] = palette.getDarkVibrantSwatch().getRgb();
                temp[1] = palette.getDarkVibrantSwatch().getBodyTextColor();
                temp[2] = palette.getDarkVibrantSwatch().getTitleTextColor();
            } else if (palette.getDarkMutedSwatch() != null) {
                temp[0] = palette.getDarkMutedSwatch().getRgb();
                temp[1] = palette.getDarkMutedSwatch().getBodyTextColor();
                temp[2] = palette.getDarkMutedSwatch().getTitleTextColor();
            } else {
                temp[0] = ContextCompat.getColor(mContext, R.color.colorPrimary);
                temp[1] = ContextCompat.getColor(mContext, android.R.color.white);
                temp[2] = 0xffe5e5e5;
            }
            return temp;
        }

        public void getBitmap() {
            if (path == null || !Utils.fileExist(path))
                bitmap = new Utils(mContext)
                        .getBitmapOfVector(R.mipmap.g1, size, size);
            else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                bitmap = BitmapFactory.decodeFile(path);
            }
        }

        //Will be used for overriding
        public void onColorFetched(int[] colors, long albumId) {
        }

    }
}
