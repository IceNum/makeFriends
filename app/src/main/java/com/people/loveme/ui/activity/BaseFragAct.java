package com.people.loveme.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.biz.ActivityWatcher;
import com.people.loveme.biz.EventCenter;


public class BaseFragAct extends FragmentActivity {
    public GlobalBeans beans;
    public EventCenter eventCenter;
    protected ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle arg0) {
        if (null == GlobalBeans.getSelf()) {
            GlobalBeans.initForMainUI(getApplication());
        }
        beans = GlobalBeans.getSelf();
        eventCenter = beans.getEventCenter();
        super.onCreate(arg0);
        initImmersionBar();
    }


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();
    }



    @Override
    protected void onResume() {
        super.onResume();
        ActivityWatcher.setCurAct(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityWatcher.setCurAct(null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
