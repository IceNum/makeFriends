package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.MFragmentStatePagerAdapter;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class IncomeDetailFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.tab)
    XTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //送礼物 充值 提现
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public String getTitleName() {
        return "收支明细";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_incomedetail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        String[] titles = new String[3];
        titles[0] = "送礼物";
        titles[1] = "充值";
        titles[2] = "提现";
        GiftListFra giftFra = new GiftListFra();
        RechargeListFra rechargeFra = new RechargeListFra();
        WithdrawListFra withdrawFra = new WithdrawListFra();
        fragments.add(giftFra);
        fragments.add(rechargeFra);
        fragments.add(withdrawFra);
        tab.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(new MFragmentStatePagerAdapter(getChildFragmentManager(), fragments, titles));
        tab.setupWithViewPager(viewPager);


        SharePrefUtil.saveBoolean(getContext(), AppConsts.isHaveNewLift, false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
