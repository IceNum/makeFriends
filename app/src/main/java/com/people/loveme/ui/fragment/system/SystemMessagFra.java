package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.adapter.SystemMessageAdapter;
import com.people.loveme.adapter.recyclerview.AgreementsAdapter;
import com.people.loveme.bean.MessageBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.WebFra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2019/1/14 0014.
 */

public class SystemMessagFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    private List<MessageBean.DataBean> list;
    private SystemMessageAdapter adapter;
    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xitongxiaoxi);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_recyclerview_nomargin, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        list = new ArrayList<>();
        adapter = new SystemMessageAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AgreementsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (list.get(position).getIs_view() == 0){
                    list.get(position).setIs_view(1);
                    adapter.notifyDataSetChanged();
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", list.get(position).getTitle());
                bundle.putString("url",list.get(position).getUrl());
                ActivitySwitcher.startFragment(getActivity(), WebFra.class, bundle);
            }
        });

        getQuestions();
    }

    private void getQuestions() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        mOkHttpHelper.post(mContext, Url.getTongzhi, params, new SpotsCallBack<MessageBean>(mContext) {
            @Override
            public void onSuccess(Response response, MessageBean questionBean) {
                if (null != questionBean.getData()) {
                    list.addAll(questionBean.getData());
                    adapter.notifyDataSetChanged();
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

}
