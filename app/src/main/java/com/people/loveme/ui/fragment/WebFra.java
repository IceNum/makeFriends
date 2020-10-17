package com.people.loveme.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.people.loveme.R;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.ui.activity.NaviActivity;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.view.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/7/5 0005.
 * webView
 */

public class WebFra extends TitleFragment {
    @BindView(R.id.webView)
    MyWebView myWebView;
    Unbinder unbinder;
    private String url, title;

    WebView webView;
    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_web, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        url = getArguments().getString("url");
        title = getArguments().getString("title");
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
        if (!StringUtil.isEmpty(url))
            webView.loadUrl(url);
        if (!StringUtil.isEmpty(title))
            ((NaviActivity) getActivity()).setTitle(title);

        eventCenter.sendType(EventCenter.EventType.EVT_MESSAGEREAD);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
