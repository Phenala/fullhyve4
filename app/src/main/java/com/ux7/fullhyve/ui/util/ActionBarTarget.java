package com.ux7.fullhyve.ui.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by hp on 6/3/2018.
 */

public class ActionBarTarget implements Target {

    ActionBar actionBar;
    Activity activity;

    public ActionBarTarget(Activity activity, ActionBar actionBar) {
        this.actionBar = actionBar;
        this.activity = activity;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
    {
        final Drawable d = new BitmapDrawable(activity.getResources(), bitmap);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                actionBar.setIcon(d);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);

            }
        };

        activity.runOnUiThread(runnable);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable)
    {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable)
    {
    }
}
