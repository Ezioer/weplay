package com.example.weile.materialdesignexa.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.activity.AlbumDetailActivity;
import com.example.weile.materialdesignexa.activity.SingerDetailActivity;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.db.DBHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by weile on 2016/12/26.
 */
public class ViewUtil {
    private static ViewUtil instance=null;
    private ViewUtil(){

    }
    public  static synchronized ViewUtil getInstance(){
         if(instance==null){
             instance=new ViewUtil();
         }
        return instance;
    }
    //弹出退出对话框
    public  void popExit(final Activity mContext) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage("你确定退出吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mContext.finish();
                    }
                }).setNegativeButton("再看看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    //弹出歌曲操作模块
    private PopupWindow mDialog = null;
    private TextView mSingerDetail, mAlbumdetail, mAddLove, mDeleteSong, mSongsInfo;
    private boolean isShowing = false;

    public void popSongOpera(final Context mContext, View v, final Song song, int mScreenHeight,
                             final ArrayList<Song> mSongList, final CommonRecAdapter<Song> mAdapter, final RecyclerView mView) {
        if (mDialog == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout
                    .dialog_operasong, null);
            mDialog = new PopupWindow(view, WindowManager.LayoutParams
                    .WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mDialog.setOutsideTouchable(true);
            mDialog.setTouchable(true);
            mSingerDetail = (TextView) view.findViewById(R.id.tv_watch_singer);
            mAlbumdetail = (TextView) view.findViewById(R.id.tv_watch_album);
            mAddLove = (TextView) view.findViewById(R.id.tv_add_love);
            mDeleteSong = (TextView) view.findViewById(R.id.tv_delete_song);
            mSongsInfo = (TextView) view.findViewById(R.id.tv_song_detail);
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            isShowing = false;
            return;
        }
        mSongsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.getInstance().popSongInfo(mContext, song);
                mDialog.dismiss();
            }
        });
        mDeleteSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(song.getPath());
                if (file.delete()) {
                    Snackbar.make(mView, "删除成功", Snackbar.LENGTH_SHORT).show();
                }
                //从本地数据库中删除歌曲文件
                mContext.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.MediaColumns._ID + "=" + song.getSongId(), null);
                //移除文件并更新
                for (int i = 0; i < mSongList.size(); i++) {
                    if (song.getSongId() == mSongList.get(i).getSongId()) {
                        mSongList.remove(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                mDialog.dismiss();
            }
        });
        mAddLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = "该歌曲已在我喜欢的列表中";
                if (!DBHelper.getInstance(mContext).checkSongIslove(String.valueOf(song.getSongId
                        ()))) {
                    mess = "成功添加至我喜欢的";
                    DBHelper.getInstance(mContext).insertMyLove(song);
                }
                mDialog.dismiss();
                Snackbar.make(mView, mess, Snackbar.LENGTH_SHORT).show();
            }
        });
        mSingerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SingerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("artistname", song.getArtist());
                bundle.putLong("artistid", song.getArtistid());
                intent.putExtras(bundle);
                mContext.startActivity(intent, bundle);
                mDialog.dismiss();
            }
        });
        mAlbumdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("albumname", song.getAlbumName());
                bundle.putLong("albumid", song.getAlbumId());
                intent.putExtras(bundle);
                mContext.startActivity(intent, bundle);
                mDialog.dismiss();
            }
        });
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int style = 0;
        if (location[1] < mScreenHeight / 2) {
            style = 0;
            mDialog.showAsDropDown(v);
            isShowing = true;
        } else {
            style = R.style.operaani;
            mDialog.showAtLocation(v, Gravity.NO_GRAVITY, location[0], (location[1] - mDialog
                    .getContentView().getMeasuredHeight()));
            isShowing = true;
        }
        mDialog.setAnimationStyle(style);
    }

    //弹出歌曲详细信息对话框
    public void popSongInfo(Context mContext, Song song) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_songinfo, null);
        TextView songname = (TextView) view.findViewById(R.id.songname);
        TextView singer = (TextView) view.findViewById(R.id.singer);
        TextView album = (TextView) view.findViewById(R.id.album);
        TextView adddate = (TextView) view.findViewById(R.id.adddate);
        TextView durtion = (TextView) view.findViewById(R.id.durtion);
        TextView kbps = (TextView) view.findViewById(R.id.kbps);
        TextView hz = (TextView) view.findViewById(R.id.hz);
        TextView size = (TextView) view.findViewById(R.id.size);
        TextView path = (TextView) view.findViewById(R.id.tv_songpath);
        TextView type = (TextView) view.findViewById(R.id.type);
        songname.setText(song.getName());
        singer.setText(song.getArtist());
        album.setText(song.getAlbumName());
        adddate.setText(String.valueOf(song.getDateAdded()));
        durtion.setText(song.getDuration());
        path.setText(song.getPath());
        size.setText(song.getSize() + "M");
        kbps.setText(song.getKbps());
        hz.setText(song.getHz());
        type.setText(song.getType());
        final AlertDialog.Builder mDialogInfo = new AlertDialog.Builder(mContext);
        mDialogInfo.setView(view)
                .setMessage("歌曲详情")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }
}
