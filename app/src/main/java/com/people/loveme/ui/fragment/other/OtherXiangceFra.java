package com.people.loveme.ui.fragment.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.adapter.XiangceAdapter;
import com.people.loveme.bean.PhotoListBean;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/10/31 0031.
 */

public class OtherXiangceFra extends TitleFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.bgRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    private List<PhotoListBean.DataBean> list;
    private List<String> images;
    private XiangceAdapter adapter;

    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    private String uid;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xiangce);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_xiangce, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        uid = getArguments().getString("uid");
        mRefreshLayout.setPullDownRefreshEnable(true);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        list = new ArrayList<>();
        images = new ArrayList<>();
        adapter = new XiangceAdapter(mContext, images);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, i);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        getPhotos();
    }

    private void getPhotos() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        mOkHttpHelper.post(mContext, Url.photoList, params, new SpotsCallBack<PhotoListBean>(mContext) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onSuccess(Response response, PhotoListBean bean) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                list.clear();
                if (!ListUtil.isEmpty(bean.getData())) {
                    list.addAll(bean.getData());
                    images.clear();
                    imageInfo.clear();
                    for (int i = 0; i < list.size(); i++) {
                        images.add(list.get(i).getImage());
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(list.get(i).getImage());
                        info.setBigImageUrl(list.get(i).getImage());
                        imageInfo.add(info);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }
        });
    }










    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getPhotos();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}

