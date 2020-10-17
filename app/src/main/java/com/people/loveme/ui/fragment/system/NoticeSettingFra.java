package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.TongZhiConfigBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 */

public class NoticeSettingFra extends TitleFragment implements View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.iv_jsxx)
    ImageView ivJsxx;
    @BindView(R.id.ll_jsxx)
    LinearLayout llJsxx;
    @BindView(R.id.iv_dzh)
    ImageView ivDzh;
    @BindView(R.id.ll_dzh)
    LinearLayout llDzh;
    @BindView(R.id.iv_bz)
    ImageView ivBz;
    @BindView(R.id.ll_bz)
    LinearLayout llBz;
    @BindView(R.id.iv_bxh)
    ImageView ivBxh;
    @BindView(R.id.ll_bxh)
    LinearLayout llBxh;
    @BindView(R.id.iv_dttx)
    ImageView ivDttx;
    @BindView(R.id.ll_dttx)
    LinearLayout llDttx;

    private boolean jsxx = true, dzh = true, bz = true, bxh = true, dttx = true;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_tongzhishezhi);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_notice_setting, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        llJsxx.setOnClickListener(this);
        llDzh.setOnClickListener(this);
        llBz.setOnClickListener(this);
        llBxh.setOnClickListener(this);
        llDttx.setOnClickListener(this);
        getSetting();
    }

    /**
     * 获取通知设置
     */
    private void getSetting() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.tongzhiConf, params, new SpotsCallBack<TongZhiConfigBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TongZhiConfigBean configBean) {
                if (null != configBean.getData()) {
                    if (!StringUtil.isEmpty(configBean.getData().getZhaohu()) && configBean.getData().getZhaohu().equals("0"))
                        dzh = false;
                    else
                        dzh = true;
                    if (!StringUtil.isEmpty(configBean.getData().getZan()) && configBean.getData().getZan().equals("0"))
                        bz = false;
                    else
                        bz = true;
                    if (!StringUtil.isEmpty(configBean.getData().getLike()) && configBean.getData().getLike().equals("0"))
                        bxh = false;
                    else
                        bxh = true;
                    if (!StringUtil.isEmpty(configBean.getData().getLike_dongtai()) && configBean.getData().getLike_dongtai().equals("0"))
                        dttx = false;
                    else
                        dttx = true;
                    show();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 修改通知设置
     */
    private void setTongZhiConfig() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (dzh)
            params.put("zhaohu", "1");
        else
            params.put("zhaohu", "0");

        if (bz)
            params.put("zan", "1");
        else
            params.put("zan", "0");

        if (bxh)
            params.put("like", "1");
        else
            params.put("like", "0");

        if (dttx)
            params.put("like_dongtai", "1");
        else
            params.put("like_dongtai", "0");

        OkHttpHelper.getInstance().post(getContext(), Url.editTongzhiConf, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean configBean) {
                ToastUtil.show("设置成功");
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void show() {
        if (!dzh && !bz && !bxh && !dttx)
            jsxx = false;

        if (jsxx)
            ivJsxx.setImageResource(R.mipmap.on);
        else
            ivJsxx.setImageResource(R.mipmap.off);

        if (dzh)
            ivDzh.setImageResource(R.mipmap.on);
        else
            ivDzh.setImageResource(R.mipmap.off);

        if (bz)
            ivBz.setImageResource(R.mipmap.on);
        else
            ivBz.setImageResource(R.mipmap.off);

        if (bxh)
            ivBxh.setImageResource(R.mipmap.on);
        else
            ivBxh.setImageResource(R.mipmap.off);

        if (dttx)
            ivDttx.setImageResource(R.mipmap.on);
        else
            ivDttx.setImageResource(R.mipmap.off);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_jsxx:
                jsxx = !jsxx;
                if (!jsxx) {
                    dzh = false;
                    bz = false;
                    bxh = false;
                    dttx = false;
                }else {
                    dzh = true;
                    bz = true;
                    bxh = true;
                    dttx = true;
                }
                show();
                setTongZhiConfig();
                break;
            case R.id.ll_dzh:
                dzh = !dzh;
                if (dzh)
                    jsxx = true;
                show();
                setTongZhiConfig();
                break;
            case R.id.ll_bz:
                bz = !bz;
                if (bz)
                    jsxx = true;
                show();
                setTongZhiConfig();
                break;
            case R.id.ll_bxh:
                bxh = !bxh;
                if (bxh)
                    jsxx = true;
                show();
                setTongZhiConfig();
                break;
            case R.id.ll_dttx:
                dttx = !dttx;
                if (dttx)
                    jsxx = true;
                show();
                setTongZhiConfig();
                break;
        }
    }
}
