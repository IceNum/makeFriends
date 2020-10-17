package com.people.loveme.ui.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.NearAdapter;
import com.people.loveme.bean.NearUserBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.LazyFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/5 0005.
 * 首页附近
 */

public class HomeNearFra extends LazyFragment {
    @BindView(R.id.xRecyclerView)
    XRecyclerView mRecyclerView;
    Unbinder unbinder;

    NearAdapter adapter;
    private List<NearUserBean.DataBean> list;
    private int page = 1;
    private boolean hasMore = true;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_near, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new NearAdapter(getContext(), list);
        adapter.setOnItemClickListener(new NearAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",list.get(position).getId()+"");
                ActivitySwitcher.startFragment(getActivity(), OtherHomePageFra.class,bundle);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
                mRecyclerView.setNoMore(false);
            }

            @Override
            public void onLoadMore() {
                if (!hasMore) {
                    mRecyclerView.setNoMore(true);
                    return;
                }
                page++;
                getData();
            }
        });
        mRecyclerView.setAdapter(adapter);
        getData();
    }


    /**
     * 获取推荐列表
     */
    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", SharePrefUtil.getString(getContext(), AppConsts.LAT, ""));
        params.put("longitude", SharePrefUtil.getString(getContext(), AppConsts.LNG, ""));
        OkHttpHelper.getInstance().post(getContext(), Url.nearbyUser, params, new SpotsCallBack<NearUserBean>(getContext()) {

            @Override
            public void onSuccess(Response response, NearUserBean bean) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                if (!ListUtil.isEmpty(bean.getData())) {
                    list.clear();
                    list.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }
}
