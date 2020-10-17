package com.people.loveme.ui.fragment.user;

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
import com.people.loveme.adapter.recyclerview.MyLikeAdapter;
import com.people.loveme.bean.MyLikeBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class WxhdFra extends TitleFragment {

    Unbinder unbinder;
    MyLikeAdapter adapter;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    private List<MyLikeBean.DataBean> list;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_guanzhude);
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
        list = new ArrayList<>();
        adapter = new MyLikeAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getMylike();
            }

            @Override
            public void onLoadMore() {

            }

        });
        adapter.setOnItemClickListener(new MyLikeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", list.get(position).getBe_like_uid());
                ActivitySwitcher.startFragment(act, OtherHomePageFra.class, bundle);
            }
        });
        mRecyclerView.setAdapter(adapter);
        getMylike();
    }

    /**
     * 获取列表
     */
    private void getMylike() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myLike, params, new SpotsCallBack<MyLikeBean>(getContext()) {
            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, MyLikeBean myLikeBean) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
                list.clear();
                adapter.notifyDataSetChanged();
                if (null != myLikeBean.getData())
                    list.addAll(myLikeBean.getData());

                adapter.notifyDataSetChanged();
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