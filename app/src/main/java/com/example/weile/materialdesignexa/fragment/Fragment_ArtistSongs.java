package com.example.weile.materialdesignexa.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.base.BaseFragment;
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
 * Created by weile on 2016/11/22.
 */
public class Fragment_ArtistSongs extends BaseFragment {
    private CommonDiyRecAdapter<Song> mAdapter;
    private ArrayList<Song> mList;
    private String artistname;
    @Bind(R.id.rv_songlist)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_songlist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        artistname=args.getString("artistname");
    }

    private void init() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mList= SongUtils.getSongListOfArtist(mContext,artistname);
        mAdapter=new CommonDiyRecAdapter<Song>(mContext,R.layout.item_songs,mList) {
            @Override
            public void convert(RecycleViewHolder holder, final Song song,int posi) {
                holder.setText(R.id.tv_songname,song.getName());
                holder.setText(R.id.tv_singer,song.getArtist());
                holder.setText(R.id.tv_ablumname,song.getAlbumName());
                ImageView songopera=holder.getView(R.id.iv_moreopera);
                songopera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.getInstance().popSongOpera(mContext,v,song,mScreenHeight,mList,mAdapter,mRecyclerView);
                    }
                });
                String path=SongUtils.getAlbumArt(mContext,song.getAlbumId());
                if(path!=null){
                    ImageView mImageview=holder.getView(R.id.iv_songpic);
                    Picasso.with(mContext).load(new File(path)).resize(Utils.dp2px(mContext,50),
                            Utils.dp2px(mContext,50)).centerCrop().into(mImageview);
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonDiyRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=new Intent();
                intent.setAction(PlayService.ACTION_PLAY_ALL_SONGS);
                intent.putExtra("songid",mList.get(position).getSongId());
                intent.putExtra("pos",position);
                mContext.sendBroadcast(intent);
            }
        });
    }
}
