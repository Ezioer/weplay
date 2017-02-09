package com.example.weile.materialdesignexa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.activity.AlbumDetailActivity;
import com.example.weile.materialdesignexa.activity.SingerDetailActivity;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.base.BaseFragment;
import com.example.weile.materialdesignexa.bean.LoveSong;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.db.DBHelper;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.SongUtils;
import com.example.weile.materialdesignexa.util.ThemeUtils;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2016/12/23.
 */
public class Fragment_mylove extends BaseFragment {
    @Bind(R.id.rv_lovesong)
    RecyclerView mRvLoveSong;
    private ArrayList<LoveSong> mlist;
    private CommonRecAdapter<LoveSong> mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mylove;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
         initData();
    }

    private void initData() {
        mlist=DBHelper.getInstance(mContext).selectLove();
        mRvLoveSong.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new CommonRecAdapter<LoveSong>(mContext,R.layout.item_songs,mlist) {
            @Override
            public void convert(RecycleViewHolder holder,final LoveSong loveSong,int posi) {
                holder.setText(R.id.tv_songname, loveSong.getName());
                holder.setText(R.id.tv_singer, loveSong.getArtist());
                holder.setText(R.id.tv_ablumname, loveSong.getAlbumName());
                ImageView mOperasong = holder.getView(R.id.iv_moreopera);
                mOperasong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popSongOpera(v,loveSong);
                    }
                });
                String path = SongUtils.getAlbumArt(mContext, loveSong.getAlbumId());
                if (path != null) {
                    ImageView mImageview = holder.getView(R.id.iv_songpic);
                    Picasso.with(mContext).load(new File(path)).resize(Utils.dp2px(mContext, 50),
                            Utils.dp2px(mContext, 50)).centerCrop().into(mImageview);
                }
            }
        };
        mAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.setAction(PlayService.ACTION_PLAY_ALL_SONGS);
                intent.putExtra("songid", mlist.get(position).getSongId());
                intent.putExtra("pos", position);
                mContext.sendBroadcast(intent);
            }
        });
        mRvLoveSong.setAdapter(mAdapter);
    }
    private PopupWindow mDialog = null;
    private TextView mSingerDetail,mAlbumdetail,mAddLove,mSongsInfo,mDeleteSong;
    private boolean isShowing=false;
    private void popSongOpera(View v,final LoveSong song) {
        if (mDialog == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout
                    .dialog_operasong, null);
            mDialog = new PopupWindow(view, WindowManager.LayoutParams
                    .WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mDialog.setOutsideTouchable(true);
            mDialog.setTouchable(true);
            mSingerDetail= (TextView) view.findViewById(R.id.tv_watch_singer);
            mAlbumdetail= (TextView) view.findViewById(R.id.tv_watch_album);
            mAddLove= (TextView) view.findViewById(R.id.tv_add_love);
            mSongsInfo= (TextView) view.findViewById(R.id.tv_song_detail);
            mDeleteSong= (TextView) view.findViewById(R.id.tv_delete_song);
            mAddLove.setText("从列表中移除");
        }
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            isShowing=false;
            return;
        }
        mAddLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DBHelper.getInstance(mContext).deletelovesong(String.valueOf(song.getSongId()));
                for (int i = 0; i < mlist.size(); i++) {
                     if(mlist.get(i).getSongId()==song.getSongId()){
                         mlist.remove(i);
                         mAdapter.notifyDataSetChanged();
                     }
                }
                 Snackbar.make(mRvLoveSong,"移除成功",Snackbar.LENGTH_SHORT).show();
            }
        });
        mSingerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SingerDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("artistname",song.getArtist());
                bundle.putLong("artistid",song.getArtistId());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
                mDialog.dismiss();
            }
        });
        mAlbumdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("albumname",song.getAlbumName());
                bundle.putLong("albumid", song.getAlbumId());
                intent.putExtras(bundle);
                startActivity(intent, bundle);
                mDialog.dismiss();
            }
        });
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int style=0;
        if (null != mDialog &&isShowing) {
            mDialog.dismiss();
            isShowing=false;
        } else {
            if(location[1]<mScreenHeight/2){
                style=0;
                mDialog.showAsDropDown(v);
                isShowing=true;
            }else {
                style=R.style.operaani;
                mDialog.showAtLocation(v, Gravity.NO_GRAVITY,location[0],(location[1]-mDialog.getContentView().getMeasuredHeight()));
                isShowing=true;
            }
        }
        mDialog.setAnimationStyle(style);
    }
}
