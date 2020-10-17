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
import com.people.loveme.adapter.recyclerview.QuestionAdapter;
import com.people.loveme.bean.QuestionBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/8/18 0018.
 * 常见问题
 */

public class QuestionsFra extends TitleFragment {
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    Unbinder unbinder;
    private List<QuestionBean.DataBean> list;
    private QuestionAdapter questionAdapter;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_changjian);
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
        questionAdapter = new QuestionAdapter(getContext(), list);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setAdapter(questionAdapter);

        questionAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("question",list.get(position));
                ActivitySwitcher.startFragment(act,QuestionDetailFra.class,bundle);
            }
        });

        getQuestions();
    }

    private void getQuestions() {
        Map<String, String> params = new HashMap<>();
        params.put("id","all");
        mOkHttpHelper.post(mContext, Url.question, params, new SpotsCallBack<QuestionBean>(mContext) {
            @Override
            public void onSuccess(Response response, QuestionBean questionBean) {
                if (null != questionBean.getData()){
                    list.addAll(questionBean.getData());
                    questionAdapter.notifyDataSetChanged();
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

