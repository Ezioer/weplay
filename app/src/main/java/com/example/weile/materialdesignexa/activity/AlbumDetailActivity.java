package com.example.weile.materialdesignexa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.util.CommonDiyRecAdapter;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/23.
 */
public class AlbumDetailActivity extends BaseActivity {
    @Bind(R.id.album_collapslayout)
    CollapsingToolbarLayout mCollLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.iv_albumpic)
    ImageView mImageview;
    @Bind(R.id.rv_albumsongs)
    RecyclerView mRecyclerView;
    private CommonDiyRecAdapter<Song> mAdapter;
    private ArrayList<Song> mList;
    private String albumname;
    private long albumid;

    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_albumdetail;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        albumname = getIntent().getExtras().getString("albumname");
        albumid = getIntent().getExtras().getLong("albumid");
        initToolBar();
        init();
    }

    private void init() {
        String path = SongUtils.getAlbumArt(mContext, albumid);
        if (path != null) {
            Picasso.with(mContext).load(new File(path)).centerCrop().resize(Utils.getScreenWidth
                    (AlbumDetailActivity.this), Utils.dp2px(mContext, 300)).into(mImageview);
        }
        mList = SongUtils.getAlbumSongByAlbumid(mContext, albumid);
        mAdapter = new CommonDiyRecAdapter<Song>(mContext, R.layout.item_songs, mList) {
            @Override
            public void convert(RecycleViewHolder holder,final Song song,int posi) {
                holder.setText(R.id.tv_songname, song.getName());
                holder.setText(R.id.tv_singer, song.getArtist());
                holder.setText(R.id.tv_ablumname, song.getAlbumName());
                ImageView songopera=holder.getView(R.id.iv_moreopera);
                songopera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.getInstance().popSongOpera(mContext,v,song,mScreenHeight,mList,mAdapter,mRecyclerView);
                    }
                });
                String path = SongUtils.getAlbumArt(mContext, song.getAlbumId());
                if (path != null) {
                    ImageView mImageview = holder.getView(R.id.iv_songpic);
                    Picasso.with(mContext).load(new File(path)).resize(Utils.dp2px(mContext,
                            50),
                            Utils.dp2px(mContext, 50)).centerCrop().into(mImageview);
                }
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonDiyRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent,View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.setAction(PlayService.ACTION_PLAY_ALL_SONGS);
                intent.putExtra("songid", mList.get(position).getSongId());
                intent.putExtra("pos", position);
                mContext.sendBroadcast(intent);
            }
        });
    }

    private void initToolBar() {
        mCollLayout.setTitle(albumname);
        mCollLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolBar);
        mToolBar.setPopupTheme(R.style.poptheme);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.more:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(AlbumDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return true;
    }
}
