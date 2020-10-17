package com.people.loveme.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.login.LoginFra;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

public class GuideActivity extends Activity {

    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    Context context;
    @BindView(R.id.btn_guide_enter)
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        context = this;
        bannerGuideContent.setAdapter(bannerAdapter);
        bannerGuideContent.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                if (position == 4) {
                    btnSkip.setVisibility(View.VISIBLE);
                    SharePrefUtil.saveBoolean(context, AppConsts.ISWELCOME, true);
                    ActivitySwitcher.startFragment(GuideActivity.this, LoginFra.class);
                    finish();
                }
            }
        });

        bannerGuideContent.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter,0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                    btnSkip.setVisibility(View.VISIBLE);
                    SharePrefUtil.saveBoolean(context, AppConsts.ISWELCOME, true);
                    ActivitySwitcher.startFragment(GuideActivity.this, LoginFra.class);
                    finish();
            }
        });


        List<Integer> guides = new ArrayList<>();
        guides.add(R.mipmap.guide1);
        guides.add(R.mipmap.guide2);
        guides.add(R.mipmap.guide3);
        guides.add(R.mipmap.guide4);
        bannerGuideContent.setData(guides, null);
    }

    private BGABanner.Adapter bannerAdapter = new BGABanner.Adapter() {
        @Override
        public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
            ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
            PicassoUtil.setImag(context, (int) model, (ImageView) view);
        }
    };
}
