package com.people.loveme.ui.fragment.main;


import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.MyPagerAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.scan.ScanFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.ZhjhDialog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 首页
 */
public class HomeFrg extends CachableFrg implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.tv_recomment)
    TextView tvRecomment;
    @BindView(R.id.view_recomment)
    View viewRecomment;
    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.view_near)
    View viewNear;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int rootLayout() {
        return R.layout.fra_home;
    }


    @Override
    protected void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeRecommendFra());
//        fragments.add(new HomeNearFra());
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvRecomment.setTextColor(getResources().getColor(R.color.txt_lv7));
                    tvNear.setTextColor(getResources().getColor(R.color.txt_home_light));
                    viewNear.setVisibility(View.INVISIBLE);
                    viewRecomment.setVisibility(View.VISIBLE);
                } else {
                    tvRecomment.setTextColor(getResources().getColor(R.color.txt_home_light));
                    tvNear.setTextColor(getResources().getColor(R.color.txt_lv7));
                    viewNear.setVisibility(View.VISIBLE);
                    viewRecomment.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvNear.setOnClickListener(this);
        tvRecomment.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        getUserInfo();
        getRzzt();
        setLastlogin();

    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.main_color);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recomment:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_near:
                viewPager.setCurrentItem(1);
                break;
            case R.id.iv_scan:
                checkPmsLocation();
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }

            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                SharePrefUtil.saveString(getContext(), AppConsts.SCORE, bean.getData().getScore());
                SharePrefUtil.saveString(getContext(), AppConsts.MONEY, bean.getData().getMoney());

                if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1"))
                    SharePrefUtil.saveBoolean(getContext(),AppConsts.ISVIP,true);
                else
                    SharePrefUtil.saveBoolean(getContext(),AppConsts.ISVIP,false);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 认证状态
     */
    private void getRzzt() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.rzzt, params, new SpotsCallBack<RzztBean>(getContext()) {
            @Override
            public void onSuccess(Response response, RzztBean rzztBean) {
                if (null != rzztBean.getData()) {
                    if (null != rzztBean.getData().getRelaname())
                        if (rzztBean.getData().getRelaname().equals("1"))
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, true);
                        else
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, false);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void setLastlogin() {

        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        mOkHttpHelper.post(getContext(), Url.lastlogin, params, new BaseCallback<BaseBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, BaseBean o) {
                if (o.getCode() == -1){
                    new ZhjhDialog(getActivity(),1).show();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                new ZhjhDialog(getActivity(),1).show();
            }
        });
    }


    private void checkPmsLocation() {
        MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                Manifest.permission.CAMERA
        );
    }

    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        ActivitySwitcher.startFragment(getActivity(), ScanFra.class);
    }

    @PermissionDenied(AppConsts.PMS_LOCATION)
    public void pmsLocationError() {
        ToastUtil.show("权限被拒绝，无法使用该功能");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
