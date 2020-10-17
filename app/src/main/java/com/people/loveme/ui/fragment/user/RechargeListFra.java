package com.people.loveme.ui.fragment.user;

import android.support.v7.widget.GridLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.RechargeListAdapter;
import com.people.loveme.bean.RechargeBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.main.CachableFrg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2019/5/13 0013.
 */
public class RechargeListFra extends CachableFrg {

    @BindView(R.id.xRecyclerView)
    XRecyclerView mRecyclerView;
    Unbinder unbinder;
    List<RechargeBean.DataBean> list;
    RechargeListAdapter adapter;

    @Override
    protected int rootLayout() {
        return R.layout.layout_xrecyclerview;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        adapter = new RechargeListAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getMyChargeOrder();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.refresh();
    }

    /**
     * 获取我的充值明细
     */
    private void getMyChargeOrder() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myChargeOrder, params, new SpotsCallBack<RechargeBean>(getContext()) {
            @Override
            public void onSuccess(Response response, RechargeBean rechargeBean) {
                mRecyclerView.refreshComplete();
                if (null != rechargeBean.getData()) {
                    if (null != rechargeBean.getData()) {
                        list.clear();
                        list.addAll(rechargeBean.getData());
                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onError(Response response, int code, Exception e) {
                mRecyclerView.refreshComplete();
            }
        });
    }
}
