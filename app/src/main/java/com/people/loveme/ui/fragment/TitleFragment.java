package com.people.loveme.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.hss01248.dialog.StyledDialog;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.ui.activity.NaviActivity;
import com.people.loveme.utils.KeyboardUtil;
import com.people.loveme.utils.ScreenUtil;
import com.people.loveme.utils.SharePrefUtil;


public abstract class TitleFragment extends Fragment implements EventCenter.EventListener {


    protected final GlobalBeans beans;
    protected NaviActivity act;
    protected int screenWidth;//屏幕宽度

    protected View rootView;
    public OkHttpHelper mOkHttpHelper;
    public Context mContext;
    public String token,userId,cityId,lat,lng;
    public EventCenter eventCenter;
    public ImmersionBar mImmersionBar;
    public TitleFragment() {
        beans = GlobalBeans.getSelf();
        screenWidth = ScreenUtil.getScreenWidth(getContext());
        mOkHttpHelper = OkHttpHelper.getInstance();
        eventCenter = beans.getEventCenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_LOGOUT);
        super.onViewCreated(view, savedInstanceState);
        try {
            mContext = act;
            StyledDialog.init(mContext);
            act.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }catch (Exception e){

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (null == act) {
            act = (NaviActivity) activity;
        }
        mContext = act;
        token =  SharePrefUtil.getString(mContext,AppConsts.TOKEN,"");
        userId = SharePrefUtil.getString(mContext,AppConsts.UID,null);
        cityId = SharePrefUtil.getString(getContext(),AppConsts.CURRENTCITYID,AppConsts.DEFAULTCITYID);
        lat = SharePrefUtil.getString(getContext(), AppConsts.LAT,AppConsts.DEFAULTLAT);
        lng = SharePrefUtil.getString(getContext(), AppConsts.LNG,AppConsts.DEFAULTLNG);
    }

    @Override
    public void onDetach() {
        act = null;
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_LOGOUT);
        super.onDetach();
    }

    public void setActivity(NaviActivity act) {
        this.act = act;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult","onActivityResult");
    }

    public int getTitleId() {
        return 0;
    }

    public String getTitleName() {
        return null;
    }

    public boolean hideLeftArrow() {
        return false;
    }

    protected boolean isAlive() {
        return null != act && !this.isDetached() && !act.isFinishing();
    }

    protected void hideKeyboard() {
        KeyboardUtil.hideKeyboard(act);
    }

    ProgressDialog dialog;

    protected void showProgressDialog(final String title, final String msg) {
        if (null == dialog) {
            dialog = ProgressDialog.show(act, title, msg);
        } else {
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type){
            case EVT_LOGOUT:
                act.finishSelf();
                break;
        }
    }
}
