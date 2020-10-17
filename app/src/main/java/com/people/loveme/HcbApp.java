package com.people.loveme;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.hss01248.dialog.StyledDialog;
import com.people.loveme.biz.CrashHandler;
import com.people.loveme.imageloader.PicassoImageLoader;
import com.lzy.ninegrid.NineGridView;
import com.people.loveme.utils.SharePrefUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;

public class HcbApp extends MultiDexApplication implements RongIM.LocationProvider {

    public static Application self;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        AppConsts.isVip = SharePrefUtil.getBoolean(this,AppConsts.ISVIP,false);
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5cc18dbb570df3bc700002f6", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "");

        PlatformConfig.setWeixin("wx68930dc29af24397", "347b83153d7b42f058aa97c39e38da76");
        PlatformConfig.setQQZone("1108379626", "j7yQDV8ZNtCjxVZB");
        PlatformConfig.setSinaWeibo("1299511545", "12da9ac9b3df9f1fb18a98af30fc3495", "https://api.weibo.com/oauth2/default.html");
//
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        RongIM.init(this,"25wehl3u2g0kw");
        RongIM.setLocationProvider(this);
//
//        //百度地图初始化
//        SDKInitializer.initialize(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        StyledDialog.init(this);

        CrashHandler.getInstance().init(this);

        //九宫格图片初始化
        NineGridView.setImageLoader(new PicassoImageLoader());

        setInputProvider(); //融云聊天界面扩展自定义

        closeAndroidPDialog();
    }

    private void setInputProvider() {

        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
    }

    /**
     * 获得当前进程的名字
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {

    }
}
