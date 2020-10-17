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
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.adapter.recyclerview.SkgwAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.LookedBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.ToastUtil;

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

public class SkgwFra extends TitleFragment implements NaviRightListener {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    SkgwAdapter adapter;

    private List<LookedBean.DataBean> list;
    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_shuikan);
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
        adapter = new SkgwAdapter(getContext(),list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        adapter.setOnItemClickListener(new SkgwAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        getLooked();

        adapter.setOnItemClickListener(new SkgwAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (null != list.get(position).getJibeninfo() && null != list.get(position).getJibeninfo().getUid()){
                    Bundle bundle = new Bundle();
                    bundle.putString("uid",list.get(position).getJibeninfo().getUid());
                    ActivitySwitcher.startFragment(act, OtherHomePageFra.class,bundle);
                }
            }
        });
    }

    /**
     * 获取列表
     */
    private void getLooked() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myLooked, params, new SpotsCallBack<LookedBean>(getContext()) {

            @Override
            public void onSuccess(Response response, LookedBean lookedBean) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
                if (!ListUtil.isEmpty(lookedBean.getData()))
                    list.addAll(lookedBean.getData());
                else
                    mRecyclerView.setNoMore(true);

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
     * 获取列表
     */
    private void delAll() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.cleanSeeMe, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("清空成功！");
                list.clear();
                adapter.notifyDataSetChanged();
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

    @Override
    public int rightText() {
        return R.string.clear;
    }

    @Override
    public void onRightClicked(View v) {
        delAll();
    }
}
