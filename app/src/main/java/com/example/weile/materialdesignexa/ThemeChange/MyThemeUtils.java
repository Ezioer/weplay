package com.example.weile.materialdesignexa.themechange;

import android.app.Activity;
import android.content.Context;

import com.example.weile.materialdesignexa.R;

/**
 * Created by weile on 2017/1/4.
 */
public class MyThemeUtils {

    public static void changTheme(Activity activity, Theme theme) {
        if (activity == null)
            return;
        int style = R.style.greentheme;
        switch (theme) {
            case GREEN:
                style = R.style.greentheme;
                break;
            case BLUE:
                style = R.style.bluetheme;
                break;
            case RED:
                style = R.style.redtheme;
                break;
            case PINK:
                style = R.style.pinktheme;
                break;
            case deep_purple:
                style = R.style.deep_purpletheme;
                break;
            case cyan:
                style = R.style.cyantheme;
                break;
            case black:
                style=R.style.nighttheme;
                break;
            default:
                break;
        }
        activity.setTheme(style);
    }

    public static Theme getCurrentTheme(Context context) {
        int value = PreferenceUtils.getInstance(context)
                .getIntParam("change_theme_key", 0);
        return MyThemeUtils.Theme.mapValueToTheme(value);
    }

    public enum Theme {
        RED(2),
        BLUE(0),
        black(6),
        deep_purple(4),
        cyan(5),
        PINK(3),
        GREEN(1),;

        private int mValue;

        Theme(int value) {
            this.mValue = value;
        }

        public static Theme mapValueToTheme(final int value) {
            for (Theme theme : Theme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return RED;
        }

        static Theme getDefault() {
            return RED;
        }

        public int getIntValue() {
            return mValue;
        }
    }
}