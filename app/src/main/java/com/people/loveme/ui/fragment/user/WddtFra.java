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
import com.people.loveme.adapter.recyclerview.DongTaiAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.MyDtBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
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

public class WddtFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    DongTaiAdapter adapter;

    private List<MyDtBean.DataBean> list;


    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_dongtai);
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
        adapter = new DongTaiAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDynamic();
                mRecyclerView.setNoMore(false);
            }

            @Override
            public void onLoadMore() {

            }


        });
        adapter.setOnItemClickListener(new DongTaiAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (null == list.get(position).getId()) {
                    ToastUtil.show("暂时无法删除");
                    return;
                }
                delDynamic(position);
            }
        });
        mRecyclerView.setAdapter(adapter);
        getDynamic();
    }

    /**
     * 获取我的动态
     */
    private void getDynamic() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myDynamic, params, new BaseCallback<MyDtBean>() {
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
            public void onSuccess(Response response, MyDtBean myDtBean) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();

                list.clear();
                adapter.notifyDataSetChanged();

                if (!ListUtil.isEmpty(myDtBean.getData()))
                    list.addAll(myDtBean.getData());
                else
                    mRecyclerView.setNoMore(true);

                Collections.reverse(list);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
            }
        });
    }


    /**
     * 删除动态
     */
    private void delDynamic(final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("id", list.get(position).getId() + "");
        OkHttpHelper.getInstance().post(getContext(), Url.delDynamic, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                list.remove(position);
                adapter.notifyDataSetChanged();
                ToastUtil.show("删除成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
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

