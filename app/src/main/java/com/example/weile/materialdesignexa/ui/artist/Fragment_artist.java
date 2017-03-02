package com.example.weile.materialdesignexa.ui.artist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.bean.Artist;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.SongUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/15.
 */
public class Fragment_artist extends BaseFragment {
    private CommonRecAdapter<Artist> mAdapter;
    private ArrayList<Artist> mArtistList;
    @Bind(R.id.iv_nosong)
    ImageView mNoSongs;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    public static Fragment_artist newInstance(){
        Bundle args=new Bundle();
        Fragment_artist fragment_artist=new Fragment_artist();
        fragment_artist.setArguments(args);
        return fragment_artist;
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_artist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        init();
    }

    @Override
    protected boolean isneedani() {
        return false;
    }

    private void init() {
        initData();
    }
    private void initData(){
        mArtistList=SongUtils.getArtistList(mContext);
        if(mArtistList.size()<=0){
            mNoSongs.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < mArtistList.size(); i++) {
            if(mArtistList.get(i).getArtistName().equals("<unknown>")){
                mArtistList.remove(i);
                i--;
            }
        }
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter=new CommonRecAdapter<Artist>(mContext,R.layout.item_artist,mArtistList) {
            @Override
            public void convert(RecycleViewHolder holder, Artist artist,int posi) {
                holder.setText(R.id.tv_ablumname,artist.getArtistName());
                holder.setText(R.id.tv_ablumer,String.valueOf(artist.getNumberOfSongs())+"首歌");
                holder.setText(R.id.num,String.valueOf(artist.getNumberOfAlbums())+"张专辑");
                ImageView imageviewartist=holder.getView(R.id.iv_artist);
                imageviewartist.setImageResource(R.mipmap.g1);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("artistname",mArtistList.get(position).getArtistName());
                bundle.putLong("artistid",mArtistList.get(position).getArtistId());
                ImageView artistpic = (ImageView) view.findViewById(R.id.iv_artist);
                goIntentActivity(artistpic,bundle,SingerDetailActivity.class);
            }
        });
    }
}
