package com.people.loveme.ui.fragment.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.utils.ScreenUtil;
import com.people.loveme.utils.SharePrefUtil;

import butterknife.ButterKnife;

public abstract class CachableFrg extends Fragment {

    protected GlobalBeans beans;
    protected EventCenter eventCenter;
    protected View rootView;
    protected int screenWidth;//屏幕宽度
    public OkHttpHelper mOkHttpHelper;
    public String userId,cityId,lat,lng,token;
    ImmersionBar mImmersionBar;
    protected abstract int rootLayout();

    protected abstract void initView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beans = GlobalBeans.getSelf();
        eventCenter = beans.getEventCenter();
        mOkHttpHelper = OkHttpHelper.getInstance();
        userId = SharePrefUtil.getString(getContext(),AppConsts.UID,null);
        cityId = SharePrefUtil.getString(getContext(),AppConsts.CURRENTCITYID,AppConsts.DEFAULTCITYID);
        token = SharePrefUtil.getString(getContext(),AppConsts.TOKEN,"");
        lat = SharePrefUtil.getString(getContext(), AppConsts.LAT,AppConsts.DEFAULTLAT);
        lng = SharePrefUtil.getString(getContext(), AppConsts.LNG,AppConsts.DEFAULTLNG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(rootLayout(), container, false);
            ButterKnife.bind(this, rootView);
            initView();
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        screenWidth = ScreenUtil.getScreenWidth(getContext());

        return rootView;
    }

    ProgressDialog dialog;

    protected void showProgressDialog(final int title, final int msg) {
        showProgressDialog(getString(title), getString(msg));
    }

    protected void showProgressDialog(final String title, final String msg) {
        if (null == dialog) {
            dialog = ProgressDialog.show(getActivity(), title, msg);
        } else {
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    protected void dismissDialog() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }

}
