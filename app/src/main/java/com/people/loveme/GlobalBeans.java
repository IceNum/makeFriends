package com.people.loveme;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;


import com.people.loveme.biz.EventCenter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GlobalBeans {

    private GlobalBeans(final Context ctx) {
        this.ctx = ctx;
    }

    public static void initForMainUI(final Context ctx) {
        self = new GlobalBeans(ctx);
        self.uiHandler = new Handler(ctx.getMainLooper());
        self.initBizObjects();
    }

    public static GlobalBeans getSelf() {
        return self;
    }


    private void initBizObjects() {
        exeService = new ScheduledThreadPoolExecutor(3);
        eventCenter = new EventCenter(uiHandler);
    }


    /**
     * 程序终止、退出 做一些释放操作
     */
    public void onTerminate() {
        try {
            exeService.shutdown();
//            locator.destory();
        } catch (Exception e) {
        }
        self = null;
    }

    private int checkPermission(final String permission) {
        return ContextCompat.checkSelfPermission(ctx, permission);
    }



    private static GlobalBeans self;

    private Context ctx;
    private ScheduledExecutorService exeService;

    private Handler uiHandler;
    private EventCenter eventCenter;

    private Activity curActivity;//当前Activity

    public Activity getCurActivity() {
        return curActivity;
    }

    public void setCurActivity(Activity curActivity) {
        this.curActivity = curActivity;
    }



    public Context getApp() {
        return ctx;
    }


    public Handler getHandler() {
        return uiHandler;
    }

    public EventCenter getEventCenter() {
        return eventCenter;
    }


    public ScheduledExecutorService getExecutorService() {
        return exeService;
    }

}
