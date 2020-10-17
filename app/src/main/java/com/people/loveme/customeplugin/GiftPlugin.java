package com.people.loveme.customeplugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.biz.EventCenter;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * Created by kxn on 2019/5/8 0008.
 */
public class GiftPlugin implements IPluginModule {

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.ic_gift_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.sendgift);
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        GlobalBeans.getSelf().getEventCenter().sendType(EventCenter.EventType.EVT_SENDGIFT);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

}
