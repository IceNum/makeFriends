package com.people.loveme.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.people.loveme.R;
import com.people.loveme.utils.PicassoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by kxn on 2018/12/24 0024.
 */

public class GuideFra extends TitleFragment {
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    Unbinder unbinder;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_guide, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    private void initView() {
        bannerGuideContent.setAdapter(bannerAdapter);
        bannerGuideContent.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                if (position == 2)
                    act.finishSelf();
            }
        });
        List<Integer> guides = new ArrayList<>();
        guides.add(R.mipmap.noviceguide1);
        guides.add(R.mipmap.noviceguide2);
        guides.add(R.mipmap.noviceguide3);
        bannerGuideContent.setData(guides, null);
    }


    private BGABanner.Adapter bannerAdapter = new BGABanner.Adapter() {
        @Override
        public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
            ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
            PicassoUtil.setImag(mContext, (int) model, (ImageView) view);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
