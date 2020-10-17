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
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/11/7 0007.
 */

public class MessageSettingFra extends TitleFragment implements NaviRightListener, View.OnClickListener {

    @BindView(R.id.iv_all)
    ImageView ivAll;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.iv_fhtj)
    ImageView ivFhtj;
    @BindView(R.id.ll_fhtj)
    LinearLayout llFhtj;
    Unbinder unbinder;

    private boolean isReceiveAll = true, isReceiveFhtj = true;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xiaoxishezhi);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_message_setting, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        llAll.setOnClickListener(this);
        llFhtj.setOnClickListener(this);
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {

            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (null != bean.getData() && bean.getData().getMessage_all().equals("0"))
                    isReceiveAll = false;
                else
                    isReceiveAll = true;

                if (null != bean.getData() && bean.getData().getMessage_tiaojian().equals("0"))
                    isReceiveFhtj = false;
                else
                    isReceiveFhtj = true;
                changeShow();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 保存信息
     */
    private void save() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (isReceiveFhtj)
            params.put("message_tiaojian", "1");
        else
            params.put("message_tiaojian", "0");

        if (isReceiveAll)
            params.put("message_all", "1");
        else
            params.put("message_all", "0");
        mOkHttpHelper.post(getContext(), Url.saveUserInfo, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("保存成功！");
                act.finishSelf();
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

    @Override
    public int rightText() {
        return R.string.save;
    }

    @Override
    public void onRightClicked(View v) {
        save();
    }

    private void changeShow() {
        if (isReceiveAll)
            ivAll.setImageResource(R.mipmap.on);
        else
            ivAll.setImageResource(R.mipmap.off);

        if (isReceiveFhtj)
            ivFhtj.setImageResource(R.mipmap.on);
        else
            ivFhtj.setImageResource(R.mipmap.off);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_all:
                isReceiveAll = !isReceiveAll;

                if (isReceiveAll)
                    isReceiveFhtj = false;
                else
                    isReceiveFhtj = true;

                changeShow();
                break;
            case R.id.ll_fhtj:
                isReceiveFhtj = !isReceiveFhtj;
                if (isReceiveFhtj)
                    isReceiveAll = false;
                else
                    isReceiveAll = true;
                changeShow();
                break;
        }
    }
}
