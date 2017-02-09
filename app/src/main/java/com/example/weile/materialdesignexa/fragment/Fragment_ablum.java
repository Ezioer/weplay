package com.example.weile.materialdesignexa.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.async.Action;
import com.example.weile.materialdesignexa.activity.AlbumDetailActivity;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.bean.Album;
import com.example.weile.materialdesignexa.util.ColorCache;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/15.
 */
public class Fragment_ablum extends BaseFragment {
    private static final String TAG = "AlbumListAdapter-TAG";
    private static final long ANIM_DUR = 500;
    public static boolean onceAnimated;
    private CommonRecAdapter<Album> mAdapter;
    private ArrayList<Album> mAlbumList;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.iv_nosong)
    ImageView mNoSongs;

    public static Fragment_ablum newInstance(){
        Bundle args=new Bundle();
        Fragment_ablum fragment_ablum=new Fragment_ablum();
        fragment_ablum.setArguments(args);
        return fragment_ablum;
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_ablum;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        mAlbumList = SongUtils.getAlbumList(mContext);
        if(mAlbumList.size()<=0){
            mNoSongs.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < mAlbumList.size(); i++) {
            if (mAlbumList.get(i).getAlbumArtist().equals("<unknown>")) {
                mAlbumList.remove(i);
                i--;
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new CommonRecAdapter<Album>(mContext, R.layout.item_ablum, mAlbumList) {
            @Override
            public void convert(final RecycleViewHolder holder, final Album album,int posi) {
                holder.setText(R.id.tv_ablumname, album.getAlbumTitle());
                holder.setText(R.id.tv_ablumer, album.getAlbumArtist());
                holder.setText(R.id.num, String.valueOf(album.getSongNumber()) + "首歌");
                ImageView imageviewalbum = holder.getView(R.id.iv_ablum);
                String path = album.getAlbumArtPath();
                if (path != null) {
                    Picasso.with(mContext).load(new File(path)).into(imageviewalbum);
                } else {
                    imageviewalbum.setImageResource(R.mipmap.g1);
                }
                if (ColorCache.getinstance().getLru().get(album.getAlbumId()) != null) {
                    int[] colors = ColorCache.getinstance().getLru().get(album.getAlbumId());
                    holder.getView(R.id.ll_abluminfo).setBackgroundColor(colors[0]);
                    TextView albumtext = holder.getView(R.id.tv_ablumname);
                    albumtext.setTextColor(colors[1]);
                    TextView singertext = holder.getView(R.id.tv_ablumer);
                    singertext.setTextColor(colors[2]);
                    TextView numtext = holder.getView(R.id.num);
                    numtext.setTextColor(colors[2]);
                } else {
                    new ArtHandler(path, holder, (new Utils(mContext).getWindowWidth() - new
                            Utils(mContext).dpToPx(1)) / 2, album.getAlbumId(), 1) {
                        @Override
                        public void onColorFetched(int[] colors, long albumId) {
                            ColorCache.getinstance().getLru().put(album.getAlbumId(), colors);
                        }
                    }.execute();
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent,View view, RecyclerView.ViewHolder holder, int position) {

                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("albumname", mAlbumList.get(position).getAlbumTitle());
                bundle.putLong("albumid", mAlbumList.get(position).getAlbumId());
                intent.putExtras(bundle);

                ImageView imageView = (ImageView) view.findViewById(R.id.iv_ablum);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), imageView, "transition_animation_news_photos");
                    startActivity(intent, options.toBundle());
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                }
            }
        });
    }

    private class ArtHandler extends Action {

        private Bitmap bitmap;
        private String path;
        private RecycleViewHolder holder;
        private int size;
        private long albumId;
        private int position;
        private ValueAnimator colorAnimation, colorAnimation1, colorAnimation2;

        public ArtHandler(String path, RecycleViewHolder holder,
                          int size, long albumId, int position) {
            this.path = path;
            this.holder = holder;
            this.size = size;
            this.albumId = albumId;
            this.position = position;
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
                    holder.getView(R.id.ll_abluminfo).setBackgroundColor(colors[0]);
                    ((TextView) (holder.getView(R.id.tv_ablumname))).setTextColor(colors[1]);
                    ((TextView) (holder.getView(R.id.tv_ablumer))).setTextColor(colors[2]);
                    ((TextView) (holder.getView(R.id.num))).setTextColor(colors[2]);
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
                    holder.getView(R.id.ll_abluminfo).setBackgroundColor((Integer) animator
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
                    ((TextView) (holder.getView(R.id.tv_ablumname))).setTextColor((Integer)
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
                    ((TextView) (holder.getView(R.id.tv_ablumer))).setTextColor((Integer)
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
