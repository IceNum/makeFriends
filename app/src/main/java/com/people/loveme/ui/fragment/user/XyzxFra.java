package com.people.loveme.ui.fragment.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.CreditScoreBean;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.activity.SfrzActivity;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.WebFra;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.DaShangSuccessDialog;
import com.people.loveme.view.ZanZhuDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class XyzxFra extends TitleFragment implements View.OnClickListener, EventCenter.EventListener, ZanZhuDialog.Pay, BGARefreshLayout.BGARefreshLayoutDelegate {

    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_xyjl)
    TextView tvXyjl;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.tv_xmrz)
    TextView tvXmrz;
    @BindView(R.id.tv_xlrz)
    TextView tvXlrz;
    @BindView(R.id.tv_zyrz)
    TextView tvZyrz;
    @BindView(R.id.tv_clrz)
    TextView tvClrz;
    @BindView(R.id.tv_fcrz)
    TextView tvFcrz;
    @BindView(R.id.tv_zz5)
    TextView tvZz5;
    @BindView(R.id.tv_zz10)
    TextView tvZz10;
    @BindView(R.id.tv_zz20)
    TextView tvZz20;
    @BindView(R.id.tv_xygz)
    TextView tvXygz;
    @BindView(R.id.ll_jczl)
    LinearLayout llJczl;
    @BindView(R.id.ll_sczp)
    LinearLayout llSczp;
    @BindView(R.id.ll_smrz)
    LinearLayout llSmrz;
    @BindView(R.id.ll_xlrz)
    LinearLayout llXlrz;
    @BindView(R.id.ll_zyrz)
    LinearLayout llZyrz;
    @BindView(R.id.ll_clrz)
    LinearLayout llClrz;
    @BindView(R.id.ll_fcrz)
    LinearLayout llFcrz;
    @BindView(R.id.ll_zz5)
    LinearLayout llZz5;
    @BindView(R.id.ll_zz10)
    LinearLayout llZz10;
    @BindView(R.id.ll_zz20)
    LinearLayout llZz20;
    @BindView(R.id.tv_jczl)
    TextView tvJczl;
    @BindView(R.id.tv_sctp)
    TextView tvSctp;
    @BindView(R.id.bgRefreshLayout)
    BGARefreshLayout mRefreshLayout;

    private String ruleUrl;

    private CreditScoreBean bean;
    private String titel, content;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_xyzx, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    private void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_EDITEINFO);
        mRefreshLayout.setPullDownRefreshEnable(true);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        ivBack.setOnClickListener(this);
        tvXyjl.setOnClickListener(this);
        tvXygz.setOnClickListener(this);
        llJczl.setOnClickListener(this);
        llSczp.setOnClickListener(this);
        llSmrz.setOnClickListener(this);
        llXlrz.setOnClickListener(this);
        llZyrz.setOnClickListener(this);
        llClrz.setOnClickListener(this);
        llFcrz.setOnClickListener(this);
        llZz5.setOnClickListener(this);
        llZz10.setOnClickListener(this);
        llZz20.setOnClickListener(this);
        getCreditScore();
        getRzzt();
    }

    /**
     * 获取信用积分
     */
    private void getCreditScore() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.creditScore, params, new SpotsCallBack<CreditScoreBean>(getContext()) {
            @Override
            public void onSuccess(Response response, CreditScoreBean creditScoreBean) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                bean = creditScoreBean;
                if (null != creditScoreBean.getData().getScore()) {
                    tvJf.setText(creditScoreBean.getData().getScore() + "分");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }
        });
    }


    /**
     * 认证状态
     */
    private void getRzzt() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.rzzt, params, new SpotsCallBack<RzztBean>(getContext()) {
            @Override
            public void onSuccess(Response response, RzztBean rzztBean) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                if (null != rzztBean.getData()) {

                    if (!StringUtil.isEmpty(rzztBean.getData().getBasic_info()) && rzztBean.getData().getBasic_info().equals("1")){
                        tvJczl.setText("已完成");
                        tvJczl.setTextColor(mContext.getResources().getColor(R.color.txt_main));
                    }



                    if (!StringUtil.isEmpty(rzztBean.getData().getCar()))
                        setStatus(rzztBean.getData().getCar(), tvClrz);
                    if (!StringUtil.isEmpty(rzztBean.getData().getEdu()))
                        setStatus(rzztBean.getData().getEdu(), tvXlrz);
                    if (!StringUtil.isEmpty(rzztBean.getData().getWork()))
                        setStatus(rzztBean.getData().getWork(), tvZyrz);
                    if (!StringUtil.isEmpty(rzztBean.getData().getRelaname()))
                        setStatus(rzztBean.getData().getRelaname(), tvXmrz);
                    if (!StringUtil.isEmpty(rzztBean.getData().getHouse()))
                        setStatus(rzztBean.getData().getHouse(), tvFcrz);


                    if (rzztBean.getData().getFive_photo().equals("1")) {
                        tvSctp.setText("已完成");
                        tvSctp.setTextColor(getResources().getColor(R.color.txt_main));
                    }
                    if (rzztBean.getData().getZanzhu_5().equals("1")) {
                        tvZz5.setText("已完成");
                        tvZz5.setTextColor(getResources().getColor(R.color.txt_main));
                    }
                    if (rzztBean.getData().getZanzhu_10().equals("1")) {
                        tvZz10.setText("已完成");
                        tvZz10.setTextColor(getResources().getColor(R.color.txt_main));
                    }
                    if (rzztBean.getData().getZanzhu_20().equals("1")) {
                        tvZz20.setText("已完成");
                        tvZz20.setTextColor(getResources().getColor(R.color.txt_main));
                    }

                    if (null != rzztBean.getData().getRelaname())
                        if (rzztBean.getData().getRelaname().equals("1"))
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, true);
                        else
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, false);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }
        });
    }


    private void setStatus(String status, TextView tv) {
        switch (status) {  //0审核中   1  已认证  2 审核失败   3未认证
            case "0":
                tv.setText(getString(R.string.wo_shenhezhong));
                tv.setTextColor(mContext.getResources().getColor(R.color.price));
                break;
            case "1":
                tv.setText(getString(R.string.wo_yirenzheng));
                tv.setTextColor(mContext.getResources().getColor(R.color.txt_main));
                break;
            case "2":
                tv.setText("未完成");
                tv.setTextColor(mContext.getResources().getColor(R.color.price));
                break;
            case "3":
                tv.setText("未完成");
                tv.setTextColor(mContext.getResources().getColor(R.color.price));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCreditScore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        eventCenter.unregistEvent(this,EventCenter.EventType.EVT_EDITEINFO);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                act.finishSelf();
                break;
            case R.id.tv_xyjl:
                Bundle bundleJl = new Bundle();
                bundleJl.putSerializable("bean", bean);
                ActivitySwitcher.startFragment(act, XyjlFra.class, bundleJl);
                break;
            case R.id.tv_xygz:
                Bundle bundle = new Bundle();
                bundle.putString("title", "信用规则");
                bundle.putString("url", Url.Creditrules);
                ActivitySwitcher.startFragment(act, WebFra.class, bundle);
                break;
            case R.id.ll_jczl:
                ActivitySwitcher.startFragment(act, UserInfoFra.class);
                break;
            case R.id.ll_sczp:
                ActivitySwitcher.startFragment(getActivity(), XiangceFra.class);
                break;
            case R.id.ll_smrz:
                if (tvXmrz.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvXmrz.getText().toString().equals(getString(R.string.wo_yirenzheng))){
                    showToast(tvXmrz);
                    return;
                }
                ActivitySwitcher.start(getActivity(), SfrzActivity.class);
                break;
            case R.id.ll_xlrz:
                if (tvXlrz.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvXlrz.getText().toString().equals(getString(R.string.wo_yirenzheng))){
                    showToast(tvXlrz);
                    return;
                }
                ActivitySwitcher.startFragment(getActivity(), XlrzFra.class);
                break;
            case R.id.ll_zyrz:
                if (tvZyrz.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvZyrz.getText().toString().equals(getString(R.string.wo_yirenzheng))){
                    showToast(tvZyrz);
                    return;
                }
                ActivitySwitcher.startFragment(getActivity(), ZyrzFra.class);
                break;
            case R.id.ll_clrz:
                if (tvClrz.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvClrz.getText().toString().equals(getString(R.string.wo_yirenzheng))){
                    showToast(tvClrz);
                    return;
                }
                ActivitySwitcher.startFragment(getActivity(), ClrzFra.class);
                break;
            case R.id.ll_fcrz:

                if (tvFcrz.getText().toString().equals(getString(R.string.wo_shenhezhong)) || tvFcrz.getText().toString().equals(getString(R.string.wo_yirenzheng))){
                    showToast(tvFcrz);
                    return;
                }
                ActivitySwitcher.startFragment(getActivity(), FcRzFra.class);
                break;
            case R.id.ll_zz5:
                ZanZhuDialog zanZhuDialog5 = new ZanZhuDialog(mContext, "5", this);
                zanZhuDialog5.show();
//                DaShangOtherDialog daShangDialog5 = new DaShangOtherDialog(mContext, this);
//                daShangDialog5.show();
                break;
            case R.id.ll_zz10:
                ZanZhuDialog zanZhuDialog10 = new ZanZhuDialog(mContext, "10", this);
                zanZhuDialog10.show();
                break;
            case R.id.ll_zz20:
                ZanZhuDialog zanZhuDialog20 = new ZanZhuDialog(mContext, "20", this);
                zanZhuDialog20.show();
                break;
        }
    }

    private void showToast(TextView tv){
        if (tv.getText().toString().equals(getString(R.string.wo_shenhezhong)))
            ToastUtil.show("正在审核中，请耐心等待");
        else if (tv.getText().toString().equals(getString(R.string.wo_yirenzheng)))
            ToastUtil.show("已完成认证，无需重复认证");
    }

    /**
     * 创建订单
     */
    private void createOrder(final String amount, final String pay_type) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        params.put("pay_type", pay_type);//,2=微信,3=支付宝
        params.put("order_type", "3");//1=用户充值,2=打赏用户.3=打赏平台
        params.put("detail", "打赏平台");


        OkHttpHelper.getInstance().post(getContext(), Url.createOrder, params, new SpotsCallBack<CreatOrderBean>(getContext()) {
            @Override
            public void onSuccess(Response response, CreatOrderBean creatOrderBean) {
                switch (pay_type) {
                    case "2":
                        goWechatPay(creatOrderBean.getData().getId(), amount);
                        break;
                    case "3":
                        goAlipay(creatOrderBean.getData().getId(), amount);
                        break;
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.show("支付成功");
                        GlobalBeans.getSelf().getEventCenter().sendType(EventCenter.EventType.EVT_PAYSUCCESS);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show("支付失败");
                    }
                    break;
                }
            }
        }
    };

    private void goAlipay(String orderId, String amount) {
        Map<String, String> params = new HashMap<>();
        params.put("orderid", orderId);
        params.put("title", "打赏");
        params.put("money", amount);
        params.put("desc", "打赏");
        OkHttpHelper.getInstance().post(getContext(), Url.alipay, params, new SpotsCallBack<AlipayBean>(getContext()) {
            @Override
            public void onSuccess(Response response, AlipayBean residueBean) {
                final String orderInfo = residueBean.getData();   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(act);
                        Map<String, String> result = alipay.payV2(orderInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void goWechatPay(String order, String amount) {

        Map<String, String> params = new HashMap<>();
        params.put("body", "打赏");
        params.put("out_trade_no", order);
        params.put("total_fee", new BigDecimal(amount).multiply(new BigDecimal("100")).intValue() + "");

        OkHttpHelper.getInstance().post(getContext(), Url.wechatPay, params, new SpotsCallBack<WeChatPayBean>(getContext()) {
            @Override
            public void onSuccess(Response response, WeChatPayBean weChatPayBean) {
                if (isWxAppInstalledAndSupported(weChatPayBean.getData().getAppid())) {
                    //1.调用API前，需要先向微信注册您的APPID
                    final IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, null);
                    // 将该app注册到微信
                    msgApi.registerApp(weChatPayBean.getData().getAppid());
                    PayReq request = new PayReq();
                    request.appId = weChatPayBean.getData().getAppid();
                    request.partnerId = weChatPayBean.getData().getPartnerid();
                    request.prepayId = weChatPayBean.getData().getPrepayid();
                    request.packageValue = weChatPayBean.getData().getPackageX();
                    request.nonceStr = weChatPayBean.getData().getNoncestr();
                    request.timeStamp = weChatPayBean.getData().getTimestamp();
                    request.sign = weChatPayBean.getData().getSign();
                    msgApi.sendReq(request);
                } else {
                    ToastUtil.show("未安装微信，需要安装微信客户端才能使用此功能！");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    private boolean isWxAppInstalledAndSupported(String appId) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(mContext, null);
        wxApi.registerApp(appId);
        boolean bIsWXAppInstalledAndSupported = wxApi.isWXAppInstalled() && wxApi.isWXAppSupportAPI();
        return bIsWXAppInstalledAndSupported;
    }

    @Override
    public void pay(String payType, String money) {
        createOrder(money, payType);
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_PAYSUCCESS:
                getRzzt();
                getCreditScore();
                new DaShangSuccessDialog(mContext).show();
                break;
            case EVT_EDITEINFO:
                getRzzt();
                getCreditScore();
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getCreditScore();
        getRzzt();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
