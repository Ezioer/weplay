package com.example.weile.materialdesignexa.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.weile.materialdesignexa.R;


/**
 * Created by architjn on 08/12/15.
 */
public class PermissionChecker {

    public static final int REQUEST_CODE = 7;
    private static final String TAG = "PermissionChecker";
    private OnPermissionResponse response;
    private Context context;
    private Activity activity;
    private View baseView;

    public PermissionChecker(Context context, Activity activity, View baseView) {
        this.context = context;
        this.activity = activity;
        this.baseView = baseView;
    }

    public void check(final String permission, final String customMsg,
                      final OnPermissionResponse response) {
        check(permission, customMsg, response, false);
    }

    public void check(final String permission, final String customMsg,
                      final OnPermissionResponse response, final boolean checkDirectly) {
        this.response = response;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            response.onAccepted();
                        }
                    });
                } else {
                    Log.v(TAG, "Waiting");
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                            permission) && !checkDirectly) {
                        Snackbar.make(baseView, customMsg,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ActivityCompat.requestPermissions((Activity) context,
                                                new String[]{permission},
                                                REQUEST_CODE);
                                    }
                                })
                                .show();
                    } else {
                        ActivityCompat.requestPermissions(((Activity) context),
                                new String[]{permission},
                                REQUEST_CODE);
                    }
                }
            }
        }).start();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, final
    @NonNull int[] grantResults) {
        if (requestCode == PermissionChecker.REQUEST_CODE) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Storage permission has been granted
                        response.onAccepted();
                    } else {
                        //Storage permission has been denied
                        response.onDecline();
                    }
                }
            });
        }
    }

    public interface OnPermissionResponse {
        void onAccepted();

        void onDecline();
    }

}
