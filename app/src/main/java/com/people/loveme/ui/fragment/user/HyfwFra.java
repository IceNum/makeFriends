package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.MyExtensionModule;
import com.people.loveme.R;
import com.people.loveme.adapter.VipCardAdapter;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.VipListBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.system.PayFra;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 * 会员服务
 */

public class HyfwFra extends TitleFragment implements EventCenter.EventListener {

    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_yj)
    TextView tvYj;
    @BindView(R.id.tv_now)
    TextView tvNow;
    @BindView(R.id.tv_xj)
    TextView tvXj;
    @BindView(R.id.tv_jd_name)
    TextView tvJdName;
    @BindView(R.id.tv_jd_yj)
    TextView tvJdYj;
    @BindView(R.id.tv_jd_now)
    TextView tvJdNow;
    @BindView(R.id.tv_jd_xj)
    TextView tvJdXj;
    @BindView(R.id.tv_bn_name)
    TextView tvBnName;
    @BindView(R.id.tv_bn_yj)
    TextView tvBnYj;
    @BindView(R.id.tv_bn_now)
    TextView tvBnNow;
    @BindView(R.id.tv_bn_xj)
    TextView tvBnXj;
    @BindView(R.id.tv_yn_name)
    TextView tvYnName;
    @BindView(R.id.tv_yn_yj)
    TextView tvYnYj;
    @BindView(R.id.tv_yn_now)
    TextView tvYnNow;
    @BindView(R.id.tv_yn_xj)
    TextView tvYnXj;
    Unbinder unbinder;
    @BindView(R.id.gridView)
    MyGridView gridView;

    VipCardAdapter adapter;
    List<VipListBean.DataBean> list;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_huiyuanfuwu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_hyfw, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
//        tvTime.setText("会员剩余天数：" + getArguments().getString("viptime"));
        PicassoUtil.setHeadImag(mContext, SharePrefUtil.getString(mContext, AppConsts.HEAD, ""), ivHead);
        list = new ArrayList<>();
        adapter = new VipCardAdapter(mContext, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("amount", list.get(i).getN_price());
                bundle.putString("title", list.get(i).getViptype());
                switch (list.get(i).getId()) {
                    case "1":
                        bundle.putString("opendays", "1");
                        break;
                    case "2":
                        bundle.putString("opendays", "30");
                        break;
                    case "3":
                        bundle.putString("opendays", "90");
                        break;
                    case "4":
                        bundle.putString("opendays", "365");
                        break;
                }
                ActivitySwitcher.startFragment(act, PayFra.class, bundle);
            }
        });
        getVipCardListBean();
        getUserInfo();
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }

            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (null != bean.getData()) {
                    if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1")) {
                        AppConsts.isVip = true;
                        if (!StringUtil.isEmpty(bean.getData().getViptime())) {
                            long time = Long.parseLong(bean.getData().getViptime()) - System.currentTimeMillis() / 1000;
                            int day = (int) (time / 86400) + 1;
                            tvTime.setText("会员剩余天数：" + day + "");
                        }
                        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
                        IExtensionModule defaultModule = null;
                        if (moduleList != null) {
                            for (IExtensionModule module : moduleList) {
                                if (module instanceof DefaultExtensionModule) {
                                    defaultModule = module;
                                    break;
                                }
                            }
                            if (defaultModule != null) {
                                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取会员卡表
     */
    private void getVipCardListBean() {
        Map<String, String> params = new HashMap<>();
        mOkHttpHelper.post(getContext(), Url.vipCardList, params, new SpotsCallBack<VipListBean>(getContext()) {
            @Override
            public void onSuccess(Response response, VipListBean vipListBean) {
                if (null != vipListBean.getData()) {
                    list.addAll(vipListBean.getData());
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_PAYSUCCESS:
                getUserInfo();
                break;
        }
    }
}
