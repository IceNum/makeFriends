package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.XyjlAdapter;
import com.people.loveme.bean.CreditScoreBean;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class XyjlFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    private List<CreditScoreBean.DataBean.ListBean> list;
    XyjlAdapter adapter;

    private CreditScoreBean bean;

    @Override
    public String getTitleName() {
        return "信用记录";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_recyclerview_nomargin, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        bean = ((CreditScoreBean) getArguments().getSerializable("bean"));
        list = new ArrayList<>();
        if (null != bean && null != bean.getData() && !ListUtil.isEmpty(bean.getData().getList()))
            list.addAll(bean.getData().getList());
        adapter = new XyjlAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
        adapter.setOnItemClickListener(new XyjlAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
            }
        });
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
