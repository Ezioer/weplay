package com.example.weile.materialdesignexa.handle;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.bean.Song;
import com.example.weile.materialdesignexa.util.NotificationUtil;
import com.example.weile.materialdesignexa.util.SongUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by weile on 2016/11/14.
 */
public class PlayHandler {
    private Context mContext;
    private PlayService mService;
    private MediaPlayer mMediaPlayer;
    private int currentsongpos = -1;
    private ArrayList<Song> mCurrentPlaySong;

    public PlayHandler(Context context, PlayService service) {
        this.mContext = context;
        this.mService = service;
        mMediaPlayer = new MediaPlayer();
        mCurrentPlaySong = new ArrayList<>();
    }

    public void playAllSong(long songid) {
        setCurrentPlaySong(SongUtils.getSongList(mContext));
        final int seekToCurPos = findCurrentPos(songid);
        updatePlayState(seekToCurPos);
    }

    private void setCurrentPlaySong(ArrayList<Song> songList) {
        this.mCurrentPlaySong.clear();
        this.mCurrentPlaySong = songList;
    }

    public Song getCurrentPlaySong() {
        return mCurrentPlaySong.get(currentsongpos);
    }

    private void updatePlayState(final int pos) {
        try {
            setPlayPos(pos);
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mCurrentPlaySong.get(pos).getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNextSong(getNextSongPos(pos));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playNextSong(int nextSongPos) {
        if (nextSongPos < mCurrentPlaySong.size()) {
            updatePlayState(nextSongPos);
        } else if (nextSongPos >= mCurrentPlaySong.size()) {
            updatePlayState(0);
        }
        mService.updatePlayer();
    }

    public void playPreSong(int preSongPos) {
        if (preSongPos == -1) {
            preSongPos = mCurrentPlaySong.size() - 1;
        }
        updatePlayState(preSongPos);
        mService.updatePlayer();
    }

    public void playSeekPosi(int posi){
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.seekTo(posi*1000);
        }
    }
    public void playOrPause(NotificationUtil mNotificationutil) {
        boolean playstate;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playstate = false;
            mNotificationutil.CreateNotification(true);
        } else {
            mMediaPlayer.start();
            playstate = true;
            mNotificationutil.CreateNotification(false);
        }
        mNotificationutil.updateNotification();
        mNotificationutil.UpdateNotificationState(getCurrentPlaySong().getName(),
                getCurrentPlaySong().getArtist(), getCurrentPlaySong().getAlbumId(),playstate);
    }

    private int getNextSongPos(int pos) {
        //如果播放的是最后一首，则播放第一首
        if (pos == mCurrentPlaySong.size() - 1) {
            return 0;
        }
        return pos + 1;
    }

    //获得当前播放歌曲在播放列表中的位置
    private void setPlayPos(int pos) {
        this.currentsongpos = pos;
    }

    public int getPlayPos() {
        return currentsongpos;
    }

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    //获得当前播放歌曲的id
    public long getCurrentPlaySongId() {
        if (currentsongpos == -1 || currentsongpos > mCurrentPlaySong.size()) {
            return -1;
        } else {
            return mCurrentPlaySong.get(currentsongpos).getSongId();
        }
    }

    public String getCurrentPlaySongName() {
        return mCurrentPlaySong.get(currentsongpos).getName();
    }
    public String getCurrentPlaySongArtist(){
        return mCurrentPlaySong.get(currentsongpos).getArtist();
    }
    public long getCurrentPlaySongAblumid() {
        return mCurrentPlaySong.get(currentsongpos).getAlbumId();
    }

    public String getCurrentPlaySongAblumName() {
        return mCurrentPlaySong.get(currentsongpos).getAlbumName();
    }

    private int findCurrentPos(long songid) {
        for (int i = 0; i < mCurrentPlaySong.size(); i++) {
            if (mCurrentPlaySong.get(i).getSongId() == songid) {
                return i;
            }
        }
        return -1;
    }
}
