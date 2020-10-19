package com.people.loveme.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.bean.SfRzBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.user.ClrzFra;
import com.people.loveme.ui.fragment.user.FcRzFra;
import com.people.loveme.ui.fragment.user.SfrzFra;
import com.people.loveme.ui.fragment.user.SjyzFra;
import com.people.loveme.ui.fragment.user.XlrzFra;
import com.people.loveme.ui.fragment.user.ZyrzFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

public class RzzxActivity extends BaseFragAct implements View.OnClickListener, EventCenter.EventListener {
    Unbinder unbinder;
    @BindView(R.id.ll_sjyz)
    LinearLayout llSjyz;
    @BindView(R.id.ll_sfrz)
    LinearLayout llSfrz;
    @BindView(R.id.ll_xlrz)
    LinearLayout llXlrz;
    @BindView(R.id.ll_zyrz)
    LinearLayout llZyrz;
    @BindView(R.id.ll_clrz)
    LinearLayout llClrz;
    @BindView(R.id.ll_fcrz)
    LinearLayout llFcrz;
    @BindView(R.id.tv_mobile_status)
    TextView tvMobileStatus;
    @BindView(R.id.tv_card_status)
    TextView tvCardStatus;
    @BindView(R.id.tv_education_status)
    TextView tvEducationStatus;
    @BindView(R.id.tv_profession_status)
    TextView tvProfessionStatus;
    @BindView(R.id.tv_car_status)
    TextView tvCarStatus;
    @BindView(R.id.tv_hous_status)
    TextView tvHousStatus;
    @BindView(R.id.navi_title)
    TextView naviTitle;
    @BindView(R.id.navi_left)
    ImageView naviLeft;

    private Context context;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzzx);
        unbinder = ButterKnife.bind(this);
        context = this;
        initView();
    }

    //    @Override
//    public String getTitleName() {
//        return "认证中心";
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fra_rzzx, container, false);
//        unbinder = ButterKnife.bind(this, rootView);
//        initView();
//        return rootView;
//    }

    private void initView() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            String biz_content = uri.getQueryParameter("biz_content");
            if (null != biz_content) {
                getRzResult(biz_content);
            }
        }
        userId = SharePrefUtil.getString(context, AppConsts.UID, null);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_RZZT);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_TOALYPAY);
        llSjyz.setOnClickListener(this);
        llSfrz.setOnClickListener(this);
        llXlrz.setOnClickListener(this);
        llZyrz.setOnClickListener(this);
        llClrz.setOnClickListener(this);
        llFcrz.setOnClickListener(this);
        naviLeft.setOnClickListener(this);
//        getRzzt();
    }

    @Override
    protected void onResume() {
        getRzzt();
        super.onResume();
    }

    /**
     * 认证状态
     */
    private void getRzzt() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(this, Url.rzzt, params, new SpotsCallBack<RzztBean>(this) {
            @Override
            public void onSuccess(Response response, RzztBean rzztBean) {
                if (null != rzztBean.getData()) {
                    if (null != rzztBean.getData().getPhone())
                        setStatus(rzztBean.getData().getPhone(), tvMobileStatus);
                    if (null != rzztBean.getData().getRelaname())
                        setStatus(rzztBean.getData().getRelaname(), tvCardStatus);
                    if (null != rzztBean.getData().getEdu())
                        setStatus(rzztBean.getData().getEdu(), tvEducationStatus);
                    if (null != rzztBean.getData().getWork())
                        setStatus(rzztBean.getData().getWork(), tvProfessionStatus);
                    if (null != rzztBean.getData().getCar())
                        setStatus(rzztBean.getData().getCar(), tvCarStatus);
                    if (null != rzztBean.getData().getHouse())
                        setStatus(rzztBean.getData().getHouse(), tvHousStatus);

                    if (null != rzztBean.getData().getRelaname())
                        if (rzztBean.getData().getRelaname().equals("1"))
                            SharePrefUtil.saveBoolean(context, AppConsts.REALNAME, true);
                        else
                            SharePrefUtil.saveBoolean(context, AppConsts.REALNAME, false);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void getRzResult(String biz_content) {
        Map<String, String> params = new HashMap<>();
        params.put("biz_content", biz_content);
        OkHttpHelper.getInstance().post(context, Url.isPersonRz, params, new SpotsCallBack<SfRzBean>(context) {
            @Override
            public void onSuccess(Response response, SfRzBean bean) {
                getRzzt();
                ToastUtil.show("认证成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_RZZT);
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void setStatus(String status, TextView tv) {
        switch (status) {  //0审核中   1  已认证  2 审核失败   3未认证
            case "0":
                tv.setText(getString(R.string.wo_shenhezhong));
                tv.setTextColor(context.getResources().getColor(R.color.price));
                break;
            case "1":
                tv.setText(getString(R.string.wo_yirenzheng));
                tv.setTextColor(context.getResources().getColor(R.color.txt_main));
                break;
            case "2":
                tv.setText("审核失败");
                tv.setTextColor(context.getResources().getColor(R.color.txt_main));
                break;
            case "3":
                tv.setText(HcbApp.self.getString(R.string.wo_qurz));
                tv.setTextColor(context.getResources().getColor(R.color.price));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_RZZT);
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_TOALYPAY);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sjyz:
                if (tvMobileStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvMobileStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvMobileStatus);
                    return;
                }
                ActivitySwitcher.startFragment(this, SjyzFra.class);
                break;
            case R.id.ll_sfrz:
                if (tvCardStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvCardStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvCardStatus);
                    return;
                }
//                ActivitySwitcher.start(this, SfrzActivity.class);
                ActivitySwitcher.startFragment(this, SfrzFra.class);
                break;
            case R.id.ll_xlrz:
                if (tvEducationStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvEducationStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvEducationStatus);
                    return;
                }
                ActivitySwitcher.startFragment(this, XlrzFra.class);
                break;
            case R.id.ll_zyrz:
                if (tvProfessionStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvProfessionStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvProfessionStatus);
                    return;
                }
                ActivitySwitcher.startFragment(this, ZyrzFra.class);
                break;
            case R.id.ll_clrz:
                if (tvCarStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvCarStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvCarStatus);
                    return;
                }
                ActivitySwitcher.startFragment(this, ClrzFra.class);
                break;
            case R.id.ll_fcrz:
                if (tvHousStatus.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvHousStatus.getText().toString().equals(getString(R.string.wo_yirenzheng))) {
                    showToast(tvHousStatus);
                    return;
                }
                ActivitySwitcher.startFragment(this, FcRzFra.class);
                break;
            case R.id.navi_left:
                finish();
                break;
        }
    }

    private void showToast(TextView tv) {
        if (tv.getText().toString().equals(getString(R.string.wo_shenhezhong)))
            ToastUtil.show("正在审核中，请耐心等待");
        else if (tv.getText().toString().equals(getString(R.string.wo_yirenzheng)))
            ToastUtil.show("已完成认证，无需重复认证");
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_RZZT:
                getRzzt();
                break;
            case EVT_TOALYPAY:
                finish();
                break;
        }
    }
}
