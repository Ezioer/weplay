package com.example.weile.materialdesignexa.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.service.PlayService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by weile on 2016/11/15.
 */
public class NotificationUtil {
    private boolean mNotificationisactiv = false;
    private int notificationid = 11211;
    private Context mContext;
    private PlayService mServices;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    public NotificationUtil(Context context, PlayService service) {
        this.mContext = context;
        this.mServices = service;
    }

    private Notification.Builder createNotificationbuilder(boolean iscanremove) {
        Intent notificationintent = new Intent();
        notificationintent.setAction(PlayService.ACTION_NOTI_CLICK);
        PendingIntent contentpending = PendingIntent.getBroadcast(mContext, 0,
                notificationintent, 0);
        Intent deleteintent = new Intent();
        deleteintent.setAction(PlayService.ACTION_NOTI_REMOVE);
        PendingIntent deletepending = PendingIntent.getBroadcast(mContext, 0, deleteintent, 0);
        if (iscanremove) {
            return new Notification.Builder(mContext)
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_audiotrack_white_24dp)
                    .setDeleteIntent(deletepending)
                    .setContentIntent(contentpending);
        } else {
            return new Notification.Builder(mContext)
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_audiotrack_white_24dp)
                    .setDeleteIntent(deletepending)
                    .setContentIntent(contentpending);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void CreateNotification(boolean iscanremove) {
        mNotification = createNotificationbuilder(iscanremove).build();
        RemoteViews bigRemoteView = new RemoteViews(mContext.getPackageName(), R.layout
                .notification_layout);
        RemoteViews smallRemoteView = new RemoteViews(mContext.getPackageName(), R.layout
                .notification_small);
        mNotification.bigContentView = bigRemoteView;
        mNotification.contentView = smallRemoteView;
        mNotification.priority = Notification.PRIORITY_MAX;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context
                .NOTIFICATION_SERVICE);
        if (!iscanremove) {
            mServices.startForeground(notificationid, mNotification);
        }
        mNotificationManager.notify(notificationid, mNotification);
        mNotificationisactiv = true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void UpdateNotificationState(String songname, String singer, long ablumid, boolean
            isplaying) {
        mNotification.bigContentView.setTextViewText(R.id.noti_name, songname);
        mNotification.bigContentView.setTextViewText(R.id.noti_artist, singer);
        mNotification.contentView.setTextViewText(R.id.noti_name, songname);
        mNotification.contentView.setTextViewText(R.id.noti_artist, singer);
        Intent playintent = new Intent();
        playintent.setAction(PlayService.ACTION_PAUSE_SONG);
        PendingIntent playingPendingIntent = PendingIntent.getBroadcast(mContext, 100,
                playintent, 0);
        mNotification.bigContentView.setOnClickPendingIntent(R.id.noti_play_button,
                playingPendingIntent);
        mNotification.contentView.setOnClickPendingIntent(R.id.noti_play_button,
                playingPendingIntent);
        Intent nextsongintent = new Intent();
        nextsongintent.setAction(PlayService.ACTION_NEXT_SONG);
        PendingIntent nextpendingintent=PendingIntent.getBroadcast(mContext,101,nextsongintent,0);
        mNotification.bigContentView.setOnClickPendingIntent(R.id.noti_next_button,nextpendingintent);
        mNotification.contentView.setOnClickPendingIntent(R.id.noti_next_button,nextpendingintent);
        Intent presongintent=new Intent();
        presongintent.setAction(PlayService.ACTION_PREV_SONG);
        PendingIntent prependingintent=PendingIntent.getBroadcast(mContext,102,presongintent,0);
        mNotification.bigContentView.setOnClickPendingIntent(R.id.noti_prev_button,prependingintent);
        mNotification.contentView.setOnClickPendingIntent(R.id.noti_prev_button,prependingintent);
        String path=SongUtils.getAlbumArt(mContext,ablumid);
        int playstate;
        if(isplaying){
            playstate=R.mipmap.ic_pause_white_48dp;
        }else {
            playstate=R.mipmap.ic_play_arrow_white_48dp;
        }
        mNotification.bigContentView.setImageViewResource(R.id.noti_play_button,playstate);
        mNotification.contentView.setImageViewResource(R.id.noti_play_button,playstate);
        if(!TextUtils.isEmpty(path)){
            Picasso.with(mContext).load(new File(path)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mNotification.bigContentView.setImageViewBitmap(R.id.noti_album_art,bitmap);
                    mNotification.contentView.setImageViewBitmap(R.id.noti_album_art,bitmap);
                    mNotificationManager.notify(notificationid,mNotification);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
    }
    public void updateNotification(){
        mNotificationManager.notify(notificationid,mNotification);
    }
    public boolean ismNotificationisactiv() {
        return mNotificationisactiv;
    }
    public void setmNotificationisactiv(boolean mNotificationisactiv) {
        this.mNotificationisactiv = mNotificationisactiv;
    }

}
