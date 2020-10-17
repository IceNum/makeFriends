package com.people.loveme.ui.activity;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.login.LoginFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseFragAct {

    private final static int SECOND = 1000;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private int downCount = 3;

    private GlobalBeans beans;
    private Handler uiHandler;

    private Context context;
    private String tag;

    AMapLocationClient mLocationClient = null;
    AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        context = this;
        GlobalBeans.initForMainUI(getApplication());
        beans = GlobalBeans.getSelf();
        uiHandler = beans.getHandler();

        tag = getIntent().getStringExtra("tag");

        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            );
        } else {
            pmsLocationSuccess();
//            uiHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    new Timer().scheduleAtFixedRate(timerTask, 0, SECOND);
//                }
//            }, 400);
        }

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerTask.cancel();
                enterApp();
            }
        });
    }

    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        mLocationClient.startLocation();
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Timer().scheduleAtFixedRate(timerTask, 0, SECOND);
            }
        }, 400);
    }

    @PermissionDenied(AppConsts.PMS_LOCATION)
    public void pmsLocationError() {
        ToastUtil.show("权限被拒绝，无法使用该功能！");
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Timer().scheduleAtFixedRate(timerTask, 0, SECOND);
            }
        }, 400);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 定位监听
     */
    //声明定位回调监听器
    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    SharePrefUtil.saveString(context, AppConsts.LAT, amapLocation.getLatitude() + "");
                    SharePrefUtil.saveString(context, AppConsts.LNG, amapLocation.getLongitude() + "");
                    SharePrefUtil.saveString(context, AppConsts.DISTRICT, amapLocation.getDistrict());
                    StringBuffer add = new StringBuffer();
                    add.append(amapLocation.getProvince());
                    add.append(amapLocation.getCity());
                    if (null != amapLocation.getDistrict())
                        add.append(amapLocation.getDistrict());
                    if (null != amapLocation.getStreet())
                        add.append(amapLocation.getStreet());
                    SharePrefUtil.saveString(context, AppConsts.ADDRESS, add.toString());
                } else {
                    if (amapLocation.getErrorCode() == 12 || amapLocation.getErrorCode() == 13) {
                        ToastUtil.show("定位失败！");
                    }
                }
            }
        }
    };


    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (downCount > 0) {
                downCount--;
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("(" + (downCount + 1) + "s)" + "跳过");
                    }
                });

            } else {
                enterApp();
                this.cancel();
            }
        }
    };


    private void enterApp() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!SharePrefUtil.getBoolean(context, AppConsts.ISWELCOME, false))
                    ActivitySwitcher.start(WelcomeActivity.this, GuideActivity.class);
                else {
                    if (null != SharePrefUtil.getString(context, AppConsts.UID, null)) {
                        if (!SharePrefUtil.getBoolean(context, AppConsts.ISCOMPLETE, false))
                            ActivitySwitcher.startFragment(WelcomeActivity.this, LoginFra.class);
                        else {
                            Bundle bundle = new Bundle();
                            if (null != tag)
                                bundle.putString("tag", tag);

                            ActivitySwitcher.start(WelcomeActivity.this, MainActivity.class, bundle);
                        }

                    } else
                        ActivitySwitcher.startFragment(WelcomeActivity.this, LoginFra.class);
                }
                finish();
            }
        });
    }


    private long backPressTime = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLocationClient.stop();
    }

    @Override
    public void onBackPressed() {
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - backPressTime > 2 * SECOND) {
            backPressTime = uptimeMillis;
            ToastUtil.show(getString(R.string.press_again_to_leave));
        } else {
            onExit();
        }
    }

    private void onExit() {
        timerTask.cancel();
        finish();
        if (null != beans) {
            beans.onTerminate();
        }
    }
}