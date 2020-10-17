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
import com.people.loveme.adapter.recyclerview.HmdAdapter;
import com.people.loveme.bean.HmdListBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 */

public class HmdFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    HmdAdapter adapter;
    private List<HmdListBean.DataBean> list;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_heimingdan);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_recyclerview, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        list = new ArrayList<>();
        adapter = new HmdAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                mRecyclerView.refreshComplete();
//            }
//
//            @Override
//            public void onLoadMore() {
//                mRecyclerView.loadMoreComplete();
//            }
//        });
        mRecyclerView.setAdapter(adapter);
        getBlackList();
    }


    /**
     * 获取黑名单列表
     */
    private void getBlackList() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.blacklist, params, new SpotsCallBack<HmdListBean>(mContext) {

            @Override
            public void onSuccess(Response response, HmdListBean hmdListBean) {
                if (!ListUtil.isEmpty(hmdListBean.getData())){
                    list.addAll(hmdListBean.getData());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
