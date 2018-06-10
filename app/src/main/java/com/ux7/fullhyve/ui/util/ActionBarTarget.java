package com.ux7.fullhyve.ui.util;

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
    Resources resources;

    public ActionBarTarget(Resources resources, ActionBar actionBar) {
        this.actionBar = actionBar;
        this.resources = resources;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
    {
        Drawable d = new BitmapDrawable(resources, bitmap);
        actionBar.setIcon(d);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
