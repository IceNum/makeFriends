package com.people.loveme.ui.fragment.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.R;
import com.people.loveme.adapter.MyPagerAdapter;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.user.FbdtFra;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class GuangChangFra extends CachableFrg implements View.OnClickListener {
    @BindView(R.id.tv_gc)
    TextView tvGc;
    @BindView(R.id.view_gc)
    View viewGc;
    @BindView(R.id.tv_gz)
    TextView tvGz;
    @BindView(R.id.view_gz)
    View viewGz;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.iv_fb)
    ImageView ivFb;

    @Override
    protected int rootLayout() {
        return R.layout.fra_guangchang;
    }

    @Override
    protected void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GcListFra());
        fragments.add(new AttentListFra());
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvGc.setTextColor(getResources().getColor(R.color.txt_lv7));
                    tvGz.setTextColor(getResources().getColor(R.color.txt_home_light));
                    viewGc.setVisibility(View.VISIBLE);
                    viewGz.setVisibility(View.INVISIBLE);
                } else {
                    tvGc.setTextColor(getResources().getColor(R.color.txt_home_light));
                    tvGz.setTextColor(getResources().getColor(R.color.txt_lv7));
                    viewGc.setVisibility(View.INVISIBLE);
                    viewGz.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvGc.setOnClickListener(this);
        tvGz.setOnClickListener(this);
        ivFb.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.main_color);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gc:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_gz:
                viewPager.setCurrentItem(1);
                break;
            case R.id.iv_fb:
                ActivitySwitcher.startFragment(getActivity(), FbdtFra.class);
                break;
        }
    }

}
