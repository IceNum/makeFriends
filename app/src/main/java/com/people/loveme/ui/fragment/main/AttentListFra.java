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
import com.people.loveme.adapter.recyclerview.GcAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GcListBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.LazyFragment;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.ReasonDialog;
import com.people.loveme.view.ReportDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 * 广场关注列表
 */

public class AttentListFra extends LazyFragment {
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    Unbinder unbinder;

    GcAdapter adapter;
    private List<GcListBean.DataBean> list;
    private int page = 1, doPeoplePosition;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_recyclerview_nomargin, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new GcAdapter(getContext(), list);
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
            }

            @Override
            public void onLoadMore() {
            }
        });
        mRecyclerView.setAdapter(adapter);
        adapter.setPeopleClickListener(new GcAdapter.PeopleClickListener() {
            @Override
            public void OnItemClick(int position) {
                ReportDialog reportDialog = new ReportDialog(getContext(), new ReportDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        doPeoplePosition = position;
                        showReport(list.get(position).getUid());
                    }
                });
                reportDialog.show();
            }
        });
        getData();
    }

    private void showReport(final String uid) {
        String[] list = getResources().getStringArray(R.array.jblx);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(), "选择举报类型", reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                report(reason.get(position), uid);
            }
        });
        reasonDialog.show();
    }

    /**
     * 举报
     */
    private void report(String type, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", SharePrefUtil.getString(getContext(), AppConsts.UID, ""));
        params.put("be_jubao_uid", uid);
        params.put("content", type);
        OkHttpHelper.getInstance().post(getContext(), Url.report, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取关注列表
     */
    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(getContext(), AppConsts.UID, ""));
        OkHttpHelper.getInstance().post(getContext(), Url.myAttention, params, new SpotsCallBack<GcListBean>(getContext()) {

            @Override
            public void onSuccess(Response response, GcListBean bean) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                if (null != bean.getData()) {
                    list.clear();
                    list.addAll(bean.getData());
                    Collections.reverse(list);
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

