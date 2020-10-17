package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.QuestionBean;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.view.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class QuestionDetailFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    MyWebView myWebView;

    WebView webView;

    @Override
    public String getTitleName() {
        return "问题详情";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_question_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {


        webView = myWebView.getWebView();
        WebSettings settings = webView.getSettings();
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置支持js
        settings.setJavaScriptEnabled(true);
        // 关闭缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 设置出现缩放工具
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        settings.setUseWideViewPort(true);
        // 自适应屏幕
        settings.setLoadWithOverviewMode(true);

        QuestionBean.DataBean question = ((QuestionBean.DataBean) getArguments().getSerializable("question"));
        if (null != question) {
            if (!StringUtil.isEmpty(question.getName()))
                tvTitle.setText(question.getName());
            if (!StringUtil.isEmpty(question.getUrl()))
                webView.loadUrl(question.getUrl());
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
