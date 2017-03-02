package com.example.weile.materialdesignexa.ui.SongList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.adapter.AdAdapter;
import com.example.weile.materialdesignexa.basemvp.BaseView;
import com.example.weile.materialdesignexa.basemvp.rx.RxManager;
import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.AdBean;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.util.CommonDiyRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.RetrofitUtil;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.util.ViewUtil;
import com.example.weile.materialdesignexa.widget.AutoScrollViewPager;
import com.example.weile.materialdesignexa.widget.CirclePageIndicator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weile on 2016/11/14.
 */
public class Fragment_SongList extends BaseFragment implements BannerView{
    private ArrayList<Song> mSongList=new ArrayList<>();
    private CommonDiyRecAdapter<Song> mAdapter;
    @Bind(R.id.rv_songlist)
    RecyclerView mRecyclerView;
    @Bind(R.id.iv_nosong)
    ImageView mNoSongs;
    private BannerPresenter mPresenter;

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

    @Override
    protected boolean isneedani() {
        return true;
    }

    private void init() {
        initdata();
    }
    private void initdata() {
        RequestStart();
        if(mPresenter==null){
            mPresenter=new BannerPresenter(this);
        }
        mPresenter.getBannerList();
    }

    private View mHeader;
    private AutoScrollViewPager mAdPager;
    private CirclePageIndicator mIndicator;
    private AdAdapter mAdAdapter;
    @Override
    public void refreshDada(AdBean adBean) {
        initSongList();
        if(mHeader==null){
            mHeader= LayoutInflater.from(mContext).inflate(R.layout.mainpage_header,null);
            mAdapter.addHeadView(mHeader);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdPager= (AutoScrollViewPager) mHeader.findViewById(R.id.autoviewpager);
        mAdPager.setCycle(true);
        if(mAdAdapter==null){
            mAdAdapter=new AdAdapter(adBean.banner_list,mContext);
            mAdPager.setAdapter(mAdAdapter);
        }else {
            mAdAdapter.notifyDataSetChanged();
        }
        mIndicator= (CirclePageIndicator) mHeader.findViewById(R.id.pageindicator);
        mIndicator.setViewPager(mAdPager);
        mIndicator.setFillColor(Color.parseColor("#00aaff"));
        mIndicator.setStrokeColor(Color.parseColor("#000000"));
        mIndicator.setStrokeWidth(3);
        if(!mAdPager.isScroll()){
            mAdPager.startAutoScroll();
        }
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
        mAdapter = new CommonDiyRecAdapter<Song>(mContext, R.layout.item_songs, mSongList) {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new CommonDiyRecAdapter.OnItemClickListener() {
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
