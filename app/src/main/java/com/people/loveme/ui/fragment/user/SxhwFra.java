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
import com.people.loveme.adapter.recyclerview.SxhwAdapter;
import com.people.loveme.bean.MyLikedBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.utils.ListUtil;

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

public class SxhwFra  extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    SxhwAdapter adapter;
    private List<MyLikedBean.DataBean> list;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_shuiguan);
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
        adapter = new SxhwAdapter(getContext(),list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                page = 1;
//                getMylike();
//                mRecyclerView.setNoMore(false);
            }

            @Override
            public void onLoadMore() {

            }


//            @Override
//            public void onLoadMore() {
//                page++;
//                getMylike();
//            }
        });
        adapter.setOnItemClickListener(new SxhwAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",list.get(position).getLike_uid()+"");
                ActivitySwitcher.startFragment(act, OtherHomePageFra.class,bundle);
            }
        });
        mRecyclerView.setAdapter(adapter);
        getMyliked();
    }

    /**
     * 获取列表
     */
    private void getMyliked() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myLiked, params, new SpotsCallBack<MyLikedBean>(getContext()) {
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
            public void onSuccess(Response response, MyLikedBean myLikedBean) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
                if (!ListUtil.isEmpty(myLikedBean.getData()))
                    list.addAll(myLikedBean.getData());
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




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
