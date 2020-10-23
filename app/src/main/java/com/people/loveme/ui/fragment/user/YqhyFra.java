package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.adapter.MyFriendAdapter;
import com.people.loveme.bean.MyInviteBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.WebFra;
import com.people.loveme.utils.ShareUtils;
import com.people.loveme.view.MyListView;
import com.people.loveme.view.SpacingTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class YqhyFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.spacingTv_Yqm)
    SpacingTextView spacingTvYqm;
    @BindView(R.id.lv)
    MyListView lv;
    @BindView(R.id.tv_yqgz)
    TextView tvYqgz;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private List<MyInviteBean.DataBean> list;
    MyFriendAdapter adapter;

    private String titel, content;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_yaoqing);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_yqhy, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        spacingTvYqm.setText(userId);
        list = new ArrayList<>();
        adapter = new MyFriendAdapter(mContext, list);
        lv.setAdapter(adapter);
        tvYqgz.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        getMyInvite();
    }


    /**
     * 我邀请的人
     */
    private void getMyInvite() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        mOkHttpHelper.post(mContext, Url.myInvite, params, new SpotsCallBack<MyInviteBean>(mContext) {
            @Override
            public void onSuccess(Response response, MyInviteBean myInviteBean) {
                if (null != myInviteBean.getData()) {
                    if (null != myInviteBean.getData()) {
                        list.addAll(myInviteBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yqgz:
                Bundle bundle = new Bundle();
                bundle.putString("title", "邀请规则");
                bundle.putString("url", Url.Inviterules);
                ActivitySwitcher.startFragment(act, WebFra.class, bundle);
                break;
            case R.id.tv_share:
                new ShareUtils(act).share(Url.shareUrl, getString(R.string.app_name), getString(R.string.share_des));
                break;
        }
    }
}
