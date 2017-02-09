package com.example.weile.materialdesignexa.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.weile.materialdesignexa.activity.MainActivity;
import com.example.weile.materialdesignexa.handle.PlayHandler;
import com.example.weile.materialdesignexa.util.NotificationUtil;
import com.example.weile.materialdesignexa.util.Tag;
import com.example.weile.materialdesignexa.util.Utils;

/**
 * Created by weile on 2016/11/14.
 */
public class PlayService extends Service {
    public static final String ACTION_PLAY_SINGLE = "ACTION_PLAY_SINGLE";
    public static final String ACTION_PLAY_ALL_SONGS = "ACTION_PLAY_ALL_SONGS";
    public static final String ACTION_PLAY_ALBUM = "ACTION_PLAY_ALBUM";
    public static final String ACTION_PLAY_PLAYLIST = "ACTION_PLAY_PLAYLIST";
    public static final String ACTION_PLAY_ARTIST = "ACTION_PLAY_ARTIST";
    public static final String ACTION_GET_SONG = "ACTION_GET_SONG";
    public static final String ACTION_NOTI_CLICK = "ACTION_NOTI_CLICK";
    public static final String ACTION_NOTI_REMOVE = "ACTION_NOTI_REMOVE";
    public static final String ACTION_CHANGE_SONG = "ACTION_CHANGE_SONG";
    public static final String ACTION_SEEK_SONG = "ACTION_SEEK_SONG";
    public static final String ACTION_NEXT_SONG = "ACTION_NEXT_SONG";
    public static final String ACTION_PREV_SONG = "ACTION_PREV_SONG";
    public static final String ACTION_PAUSE_SONG = "ACTION_PAUSE_SONG";
    public static final String ACTION_ADD_QUEUE = "ACTION_ADD_QUEUE";
    private Context mContext;
    private PlayHandler mMusicPlayHandler;
    private NotificationUtil mNotificationUtil;
    private BroadcastReceiver playReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                handleBroadcastReceiver(context, intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "暂时无法播放", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void handleBroadcastReceiver(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PLAY_ALL_SONGS:
                playAllSongs(intent);
                break;
            case ACTION_SEEK_SONG:
                mMusicPlayHandler.playSeekPosi(intent.getIntExtra("currentposi",0));
                break;
            case ACTION_NEXT_SONG:
                mMusicPlayHandler.playNextSong(mMusicPlayHandler.getPlayPos() + 1);
                break;
            case ACTION_PREV_SONG:
                mMusicPlayHandler.playPreSong(mMusicPlayHandler.getPlayPos()-1);
                break;
            case ACTION_PAUSE_SONG:
                mMusicPlayHandler.playOrPause(mNotificationUtil);
                break;
            case ACTION_NOTI_CLICK:
                final Intent intent1 = new Intent();
                if(MainActivity.MainActivityIsRunning){
                     intent1.setClass(mContext,MainActivity.class);
                     startActivity(intent1);
                }else {
                    intent1.setClass(mContext,MainActivity.class);
                    startActivity(intent1);
                }
                break;
            case ACTION_NOTI_REMOVE:
                mNotificationUtil.setmNotificationisactiv(false);
                mMusicPlayHandler.getmMediaPlayer().stop();
                break;

        }
    }

    private void playAllSongs(Intent intent) {
        mMusicPlayHandler.playAllSong(intent.getLongExtra("songid", 0));
        updatePlayer();
    }

    public void updatePlayer() {
        Intent intent = new Intent();
        intent.setAction(Tag.CURRENT_PLAYSONG);
        intent.putExtra("isplaying", mMusicPlayHandler.getmMediaPlayer().isPlaying());
        intent.putExtra("songid", mMusicPlayHandler.getCurrentPlaySongId());
        intent.putExtra("songname", mMusicPlayHandler.getCurrentPlaySongName());
        intent.putExtra("albumid", mMusicPlayHandler.getCurrentPlaySongAblumid());
        intent.putExtra("albumname", mMusicPlayHandler.getCurrentPlaySongAblumName());
        intent.putExtra("seek", mMusicPlayHandler.getmMediaPlayer().getCurrentPosition());
        intent.putExtra("pos", mMusicPlayHandler.getPlayPos());
        intent.putExtra("dur",mMusicPlayHandler.getCurrentPlaySong().getDuration());
        intent.putExtra("duration",mMusicPlayHandler.getCurrentPlaySong().duration());
        intent.putExtra("totaldur",mMusicPlayHandler.getCurrentPlaySong().getSeconds());
        intent.putExtra("artist",mMusicPlayHandler.getCurrentPlaySongArtist());
        sendBroadcast(intent);
        updateNotification();
        Utils.setTagString(mContext,"songname", mMusicPlayHandler.getCurrentPlaySongName());
        Utils.setTagString(mContext,"artist", mMusicPlayHandler.getCurrentPlaySongArtist());
        Utils.setTagLong(mContext,"albumid",  mMusicPlayHandler.getCurrentPlaySongAblumid());
    }

    private void updateNotification() {
        if (!mNotificationUtil.ismNotificationisactiv())
            mNotificationUtil.CreateNotification(false);
        mNotificationUtil.UpdateNotificationState(mMusicPlayHandler.getCurrentPlaySong().getName
                (), mMusicPlayHandler.getCurrentPlaySong().getArtist(), mMusicPlayHandler
                .getCurrentPlaySong().getAlbumId(), mMusicPlayHandler.getmMediaPlayer().isPlaying
                ());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        if (mMusicPlayHandler == null) {
            mMusicPlayHandler = new PlayHandler(mContext, this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_PLAY_ALL_SONGS);
        intentFilter.addAction(ACTION_PREV_SONG);
        intentFilter.addAction(ACTION_PAUSE_SONG);
        intentFilter.addAction(ACTION_NEXT_SONG);
        intentFilter.addAction(ACTION_NOTI_CLICK);
        intentFilter.addAction(ACTION_NOTI_REMOVE);
        intentFilter.addAction(ACTION_SEEK_SONG);
        registerReceiver(playReceiver, intentFilter);
        mNotificationUtil = new NotificationUtil(mContext, this);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(playReceiver);
        if(mMusicPlayHandler.getmMediaPlayer()!=null){
            mMusicPlayHandler.getmMediaPlayer().stop();
            mMusicPlayHandler.getmMediaPlayer().release();
        }
    }
}
