package com.example.weile.materialdesignexa.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StyleableRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.weile.materialdesignexa.activity.AlbumDetailActivity;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.db.DBHelper;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.activity.SingerDetailActivity;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.PermissionChecker;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/14.
 */
public class Fragment_SongList extends BaseFragment {
    private ArrayList<Song> mSongList;
    private CommonRecAdapter<Song> mAdapter;
    @Bind(R.id.rv_songlist)
    RecyclerView mRecyclerView;
    @Bind(R.id.iv_nosong)
    ImageView mNoSongs;

    public static Fragment_SongList newInstance() {
        Bundle args = new Bundle();
        Fragment_SongList fragment_songlist = new Fragment_SongList();
        fragment_songlist.setArguments(args);
        return fragment_songlist;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_songlist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        initSongList();
    }

    private void initSongList() {
        mSongList = SongUtils.getSongList(mContext);
        if(mSongList.size()<=0){
            mNoSongs.setVisibility(View.VISIBLE);
            return;
        }
        //过滤掉未知音频
        for (int i = 0; i < mSongList.size(); i++) {
            if (mSongList.get(i).getArtist().equals("<unknown>")) {
                mSongList.remove(i);
                i--;
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommonRecAdapter<Song>(mContext, R.layout.item_songs, mSongList) {
            @Override
            public void convert(RecycleViewHolder holder, final Song song,int posi) {
                holder.setText(R.id.tv_songname, song.getName());
                holder.setText(R.id.tv_singer, song.getArtist());
                holder.setText(R.id.tv_ablumname, song.getAlbumName());
                ImageView mOperasong = holder.getView(R.id.iv_moreopera);
                mOperasong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.getInstance().popSongOpera(mContext, v, song, mScreenHeight,
                                mSongList,mAdapter,mRecyclerView);
                    }
                });
                String path = SongUtils.getAlbumArt(mContext, song.getAlbumId());
                ImageView mImageview = holder.getView(R.id.iv_songpic);
                if (path != null) {
                    Picasso.with(mContext).load(new File(path)).resize(Utils.dp2px(mContext, 50),
                            Utils.dp2px(mContext, 50)).centerCrop().into(mImageview);
                }else{
                    Picasso.with(mContext).load(R.mipmap.ic_album_grey600_48dp).resize(Utils.dp2px(mContext, 50),
                            Utils.dp2px(mContext, 50)).centerCrop().into(mImageview);
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder,
                                    int position) {
                Intent intent = new Intent();
                intent.setAction(PlayService.ACTION_PLAY_ALL_SONGS);
                intent.putExtra("songid", mSongList.get(position).getSongId());
                intent.putExtra("pos", position);
                mContext.sendBroadcast(intent);
            }
        });
    }
}
