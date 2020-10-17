package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.LxwmBean;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/8/18 0018.
 * 联系我们
 */

public class LxwmFra extends TitleFragment {
    @BindView(R.id.tv_kf)
    TextView tvKf;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_gzh)
    TextView tvGzh;
    @BindView(R.id.tv_wb)
    TextView tvWb;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.yx)
    TextView yx;
    @BindView(R.id.tv_gw)
    TextView tvGw;
    Unbinder unbinder;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_lianxiwomen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_lxwm, container, false);
        initView();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void initView() {
        getInfo();
    }

    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        mOkHttpHelper.post(mContext, Url.coutactUs, params, new SpotsCallBack<LxwmBean>(mContext) {
            @Override
            public void onSuccess(Response response, LxwmBean lxwmBean) {
                    if (!StringUtil.isEmpty(lxwmBean.getKefu()))
                        tvKf.setText(lxwmBean.getKefu());
                    if (!StringUtil.isEmpty(lxwmBean.getWechatmp()))
                        tvGzh.setText(lxwmBean.getWechatmp());
                    if (!StringUtil.isEmpty(lxwmBean.getWeibo()))
                        tvWb.setText(lxwmBean.getWeibo());
                    if (!StringUtil.isEmpty(lxwmBean.getPhone()))
                        tvPhone.setText(lxwmBean.getPhone());
                    if (!StringUtil.isEmpty(lxwmBean.getEmail()))
                        yx.setText(lxwmBean.getEmail());
                    if (!StringUtil.isEmpty(lxwmBean.getUrl()))
                        tvGw.setText(lxwmBean.getUrl());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
