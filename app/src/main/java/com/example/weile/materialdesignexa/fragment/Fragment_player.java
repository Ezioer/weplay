package com.example.weile.materialdesignexa.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.async.Action;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.util.ColorCache;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.ThreadTask;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.widget.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/15.
 */
public class Fragment_player extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.ll_miniPlayer)
    LinearLayout mMiniPlayer;
    @Bind(R.id.iv_miniPlayerImg)
    ImageView mMiniPic;
    @Bind(R.id.tv_miniSongname)
    TextView mMiniSongName;
    @Bind(R.id.tv_miniSinger)
    TextView mMiniSinger;
    @Bind(R.id.tv_miniNext)
    TextView mMiniNext;
    @Bind(R.id.tv_miniPause)
    TextView mMiniPause;
    @Bind(R.id.ll_dragview)
    LinearLayout mDragView;
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
    @Bind(R.id.tv_currentdur)
    TextView mCurrentDur;
    @Bind(R.id.tv_totaldur)
    TextView mTotalDur;
    @Bind(R.id.pb_playdur)
    SeekBar mPbDur;
    private static final String TAG = "minibackground";
    private static final long ANIM_DUR = 500;
    private boolean onceAnimated;
    private ImageView mAvatar;
    private TextView mSongname, mSinger;
    private SlidingUpPanelLayout mSlidingUpPaneLayout;
    private String path;
    private SongUtils.PlayState mState;
    public static final String CURRENT_PLAYSONG = "CURRENT_PLAYSONG";
    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CURRENT_PLAYSONG)) {
                mState = SongUtils.PlayState.PLAYING;
                updatePlay(intent);
                if (mSlidingUpPaneLayout.isPanelHidden()) {
                    mSlidingUpPaneLayout.showPanel();
                }
            }
        }
    };

    public static Fragment_player newInstance() {
        Bundle bundle = new Bundle();
        Fragment_player player = new Fragment_player();
        player.setArguments(bundle);
        return player;
    }

    private void updatePlay(Intent intent) {
        updateNavigationHeader(intent);
        updateMiniPlayer(intent);
        updateMainPlayer(intent);
    }

    private int seek = 0;

    //更新主界面
    private void updateMainPlayer(final Intent intent) {
        String dur = intent.getStringExtra("dur");
        seek = intent.getIntExtra("seek", 0);
        mTotalDur.setText(dur);
        mPbDur.setMax((int) intent.getLongExtra("totaldur", 0));
        mPbDur.setProgress(seek);
        updateSeekBar();

        mBigSongName.setText(intent.getStringExtra("songname"));
        mBigArtistName.setText(intent.getStringExtra("artist"));
        if (path != null) {
            Picasso.with(mContext).load(new File(path)).into(mIvMainAlbumPic);
        } else {
            mIvMainAlbumPic.setImageResource(R.mipmap.g1);
        }
        if (ColorCache.getinstance().getLru().get(intent.getLongExtra("albumid", 0)) != null) {
            int[] colors = ColorCache.getinstance().getLru().get(intent.getLongExtra("albumid", 0));
            mPlayerControl.setBackgroundColor(colors[0]);
            mBigSongName.setTextColor(colors[1]);
            mBigArtistName.setTextColor(colors[2]);
        } else {
            new ArtHandler(path, mPlayerControl.getHeight(), intent.getLongExtra("albumid", 0),
                    0, mPlayerControl, mBigSongName, mBigArtistName) {
                @Override
                public void onColorFetched(int[] colors, long albumId) {
                    ColorCache.getinstance().getLru().put(intent.getLongExtra("albumid", 0),
                            colors);
                }
            }.execute();
        }
    }

    private Timer timer = null;

    private void updateSeekBar() {
        if (timer == null) {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mState == SongUtils.PlayState.PLAYING) {
                                seek++;
                                mPbDur.setProgress(seek);
                                mCurrentDur.setText(Utils.getTimeFormat(seek));
                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    //更新mini播放控制
    private void updateMiniPlayer(final Intent intent) {
        mMiniSongName.setText(intent.getStringExtra("songname"));
        mMiniSinger.setText(intent.getStringExtra("artist"));
        path = SongUtils.getAlbumArt(mContext, intent.getLongExtra("albumid", 0));
        if (path != null) {
            Picasso.with(mContext).load(new File(path)).into(mMiniPic);
        } else {
            mMiniPic.setImageResource(R.mipmap.g1);
        }
        if (ColorCache.getinstance().getLru().get(intent.getLongExtra("albumid", 0)) != null) {
            int[] colors = ColorCache.getinstance().getLru().get(intent.getLongExtra("albumid", 0));
            mMiniPlayer.setBackgroundColor(colors[0]);
            mMiniSongName.setTextColor(colors[1]);
            mMiniSinger.setTextColor(colors[2]);
        } else {
            new ArtHandler(path, Utils.dpToPx(56), intent.getLongExtra("albumid",
                    0), 0, mMiniPlayer, mMiniSongName, mMiniSinger) {
                @Override
                public void onColorFetched(int[] colors, long albumId) {
                    ColorCache.getinstance().getLru().put(intent.getLongExtra("albumid", 0),
                            colors);
                }
            }.execute();
        }
    }

    private void updateNavigationHeader(Intent intent) {
        if (mAvatar.getDrawable() == null || intent == null) {
            mAvatar.setImageResource(R.mipmap.bidiu);
            mSongname.setText(null);
            mSinger.setText(null);
        } else {
            String path = SongUtils.getAlbumArt(mContext, intent.getLongExtra("albumid", 0));
            if (path != null) {
                Picasso.with(mContext).load(new File(path)).into(mAvatar);
            }
            mSongname.setText(intent.getStringExtra("songname"));
            mSinger.setText(intent.getStringExtra("albumname"));
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_player;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        mState = SongUtils.PlayState.PAUSE;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CURRENT_PLAYSONG);
        getActivity().registerReceiver(br, intentFilter);
        setListener();
    }

    private void doViewBig(float slideOffset, View panel) {
        LinearLayout relativeLayout = (LinearLayout) panel.findViewById(R.id.ll_miniPlayer);
        ObjectAnimator animator = ObjectAnimator.ofFloat(relativeLayout, "alpha", 1f, (1 -
                slideOffset));
        animator.setDuration(0);
        animator.start();
    }

    private void setListener() {
        mTvMainNext.setOnClickListener(this);
        mTvMainPause.setOnClickListener(this);
        mTvMainPre.setOnClickListener(this);
        mMiniNext.setOnClickListener(this);
        mMiniPause.setOnClickListener(this);
        mPbDur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPbDur.setProgress(seekBar.getProgress());
                    seek = seekBar.getProgress();
                    Intent intent = new Intent();
                    intent.setAction(PlayService.ACTION_SEEK_SONG);
                    intent.putExtra("currentposi", seekBar.getProgress());
                    mContext.sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSlidingUpPaneLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                doViewBig(slideOffset, panel);
            }

            @Override
            public void onPanelCollapsed(View panel) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
    }

    //设置侧边栏
    public void setNavigationHeader(View navigationheader) {
        mAvatar = (ImageView) navigationheader.findViewById(R.id.iv_headerpic);
        mSongname = (TextView) navigationheader.findViewById(R.id.tv_headersongname);
        mSinger = (TextView) navigationheader.findViewById(R.id.tv_headersinger);
    }

    //设置小播放器
    public void setSlidingPaneLayout(SlidingUpPanelLayout slidingPaneLayout) {
        mSlidingUpPaneLayout = slidingPaneLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext.unregisterReceiver(br);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_miniNext:
            case R.id.tv_bignext:
                intent = new Intent();
                intent.setAction(PlayService.ACTION_NEXT_SONG);
                mContext.sendBroadcast(intent);
                intent = null;
                break;
            case R.id.tv_miniPause:
            case R.id.tv_bigpause:
                intent = new Intent();
                intent.setAction(PlayService.ACTION_PAUSE_SONG);
                mContext.sendBroadcast(intent);
                if (mState == SongUtils.PlayState.PLAYING) {
                    mState = SongUtils.PlayState.PAUSE;
                    mTvMainPause.setBackgroundResource(R.mipmap.ic_play_arrow_white_36dp);
                    mMiniPause.setBackgroundResource(R.mipmap.ic_play_arrow_white_36dp);
                } else {
                    mState = SongUtils.PlayState.PLAYING;
                    mTvMainPause.setBackgroundResource(R.mipmap.ic_pause_white_36dp);
                    mMiniPause.setBackgroundResource(R.mipmap.ic_pause_white_36dp);
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
                bitmap = Utils
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
