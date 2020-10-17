package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.activity.SfrzActivity;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class RzzxFra extends TitleFragment implements View.OnClickListener, EventCenter.EventListener {
    Unbinder unbinder;
    @BindView(R.id.ll_sjyz)
    LinearLayout llSjyz;
    @BindView(R.id.ll_sfrz)
    LinearLayout llSfrz;
    @BindView(R.id.ll_xlrz)
    LinearLayout llXlrz;
    @BindView(R.id.ll_zyrz)
    LinearLayout llZyrz;
    @BindView(R.id.ll_clrz)
    LinearLayout llClrz;
    @BindView(R.id.ll_fcrz)
    LinearLayout llFcrz;
    @BindView(R.id.tv_mobile_status)
    TextView tvMobileStatus;
    @BindView(R.id.tv_card_status)
    TextView tvCardStatus;
    @BindView(R.id.tv_education_status)
    TextView tvEducationStatus;
    @BindView(R.id.tv_profession_status)
    TextView tvProfessionStatus;
    @BindView(R.id.tv_car_status)
    TextView tvCarStatus;
    @BindView(R.id.tv_hous_status)
    TextView tvHousStatus;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_renzhengzhongxin);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_rzzx, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_RZZT);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_TOALYPAY);
        llSjyz.setOnClickListener(this);
        llSfrz.setOnClickListener(this);
        llXlrz.setOnClickListener(this);
        llZyrz.setOnClickListener(this);
        llClrz.setOnClickListener(this);
        llFcrz.setOnClickListener(this);
        getRzzt();
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
                    if (null != rzztBean.getData().getPhone())
                        setStatus(rzztBean.getData().getPhone(), tvMobileStatus);
                    if (null != rzztBean.getData().getRelaname())
                        setStatus(rzztBean.getData().getRelaname(), tvCardStatus);
                    if (null != rzztBean.getData().getEdu())
                        setStatus(rzztBean.getData().getEdu(), tvEducationStatus);
                    if (null != rzztBean.getData().getWork())
                        setStatus(rzztBean.getData().getWork(), tvProfessionStatus);
                    if (null != rzztBean.getData().getCar())
                        setStatus(rzztBean.getData().getCar(), tvCarStatus);
                    if (null != rzztBean.getData().getHouse())
                        setStatus(rzztBean.getData().getHouse(), tvHousStatus);

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

    private void setStatus(String status, TextView tv) {
        switch (status) {  //0审核中   1  已认证  2 审核失败   3未认证
            case "0":
                tv.setText(getString(R.string.wo_shenhezhong));
                tv.setTextColor(mContext.getResources().getColor(R.color.price));
                break;
            case "1":
                tv.setText(getString(R.string.wo_yirenzheng));
                tv.setTextColor(mContext.getResources().getColor(R.color.txt_main));
                break;
            case "2":
                tv.setText(getString(R.string.wo_shenhefil));
                tv.setTextColor(mContext.getResources().getColor(R.color.txt_main));
                break;
            case "3":
                tv.setText(getString(R.string.wo_qurenzhegn));
                tv.setTextColor(mContext.getResources().getColor(R.color.price));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_RZZT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sjyz:
                if (tvMobileStatus.getText().toString().equals("审核中") || tvMobileStatus.getText().toString().equals("已认证")){
                    showToast(tvMobileStatus);
                    return;
                }
                ActivitySwitcher.startFragment(act, SjyzFra.class);
                break;
            case R.id.ll_sfrz:
                if (tvCardStatus.getText().toString().equals("审核中") || tvCardStatus.getText().toString().equals("已认证")){
                    showToast(tvCardStatus);
                    return;
                }
                ActivitySwitcher.start(getActivity(), SfrzActivity.class);
                break;
            case R.id.ll_xlrz:
                if (tvEducationStatus.getText().toString().equals("审核中") || tvEducationStatus.getText().toString().equals("已认证")){
                    showToast(tvEducationStatus);
                    return;
                }
                ActivitySwitcher.startFragment(act, XlrzFra.class);
                break;
            case R.id.ll_zyrz:
                if (tvProfessionStatus.getText().toString().equals("审核中") || tvProfessionStatus.getText().toString().equals("已认证")){
                    showToast(tvProfessionStatus);
                    return;
                }
                ActivitySwitcher.startFragment(act, ZyrzFra.class);
                break;
            case R.id.ll_clrz:
                if (tvCarStatus.getText().toString().equals("审核中") || tvCarStatus.getText().toString().equals("已认证")){
                    showToast(tvCarStatus);
                    return;
                }
                ActivitySwitcher.startFragment(act, ClrzFra.class);
                break;
            case R.id.ll_fcrz:
                if (tvHousStatus.getText().toString().equals("审核中") || tvHousStatus.getText().toString().equals("已认证")){
                    showToast(tvHousStatus);
                    return;
                }
                ActivitySwitcher.startFragment(act, FcRzFra.class);
                break;
        }
    }

    private void showToast(TextView tv){
        if (tv.getText().toString().equals("审核中"))
            ToastUtil.show("正在审核中，请耐心等待");
        else if (tv.getText().toString().equals("已认证"))
            ToastUtil.show("已完成认证，无需重复认证");
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_RZZT:
                getRzzt();
                break;

        }
    }
}
