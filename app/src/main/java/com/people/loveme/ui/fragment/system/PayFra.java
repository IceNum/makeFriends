package com.people.loveme.ui.fragment.system;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.people.loveme.R;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

import static com.people.loveme.biz.EventCenter.EventType.EVT_PAYSUCCESS;

/**
 * Created by kxn on 2018/8/13 0013.
 */

public class PayFra extends TitleFragment implements View.OnClickListener, EventCenter.EventListener {
    Unbinder unbinder;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private String play_tour_uid, type; //被打赏人的ID
    private ProgressDialog loadingDialog;
    private String toastMsg;
    private String amount, title;


    private String channel = "alipay";
    private String opendays;


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
                        eventCenter.sendType(EVT_PAYSUCCESS);
                        act.finishSelf();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show("支付失败");
                    }
                    break;
                }
            }
        }
    };

    @Override
    public String getTitleName() {
        return "支付";
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_pay, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        opendays = getArguments().getString("opendays");
        amount = getArguments().getString("amount");
        title = getArguments().getString("title");
        if (null != title)
            tvTitle.setText(title);
        if (null != amount)
            tvPrice.setText(AppConsts.RMB + amount);
        initView();
        return rootView;
    }

    private void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setMessage("处理中，请稍候...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(true);

        llWechat.setOnClickListener(this);
        llAlipay.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat:
                channel = "wx";
                ivAlipay.setImageResource(R.mipmap.ic_weixuan);
                ivWechat.setImageResource(R.mipmap.ic_xuanze);
                break;
            case R.id.ll_alipay:
                channel = "alipay";
                ivAlipay.setImageResource(R.mipmap.ic_xuanze);
                ivWechat.setImageResource(R.mipmap.ic_weixuan);
                break;
            case R.id.tv_pay:
                if (null != amount)
                    pay(channel);
                break;
        }
    }

    /**
     * 支付
     */
    private void pay(String channel) {
        switch (channel) {
            case "wx": //微信
                createOrder();
                break;
            case "alipay": //支付宝支付
                createOrder();
                break;
        }
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        switch (channel){
            case "wx":
                params.put("pay_type", "2");//支付类型:1=余额,2=微信,3=支付宝
                break;
            case "alipay":
                params.put("pay_type", "3");//支付类型:1=余额,2=微信,3=支付宝
                break;
        }
        params.put("vip_opentime",opendays);
        params.put("order_type","1");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用

        params.put("detail", title);

        mOkHttpHelper.post(getContext(), Url.createOrder, params, new SpotsCallBack<CreatOrderBean>(getContext()) {
            @Override
            public void onSuccess(Response response, CreatOrderBean creatOrderBean) {
                switch (channel) {
                    case "wx":
                        goWechatPay(creatOrderBean.getData().getId());
                        break;
                    case "alipay":
                        goAlipay(creatOrderBean.getData().getId());
                        break;
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }




    private void goAlipay(String orderId) {
        Map<String, String> params = new HashMap<>();
        params.put("orderid", orderId);
        params.put("title", title);
        params.put("money", amount);
        params.put("desc",title);

        mOkHttpHelper.post(getContext(), Url.alipay, params, new SpotsCallBack<AlipayBean>(getContext()) {
            @Override
            public void onSuccess(Response response, AlipayBean residueBean) {
                final String orderInfo = residueBean.getData();   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {

                        PayTask alipay = new PayTask(act);
                        Map<String, String> result = alipay.payV2(orderInfo,true);

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

    private void goWechatPay(String order) {
        Map<String, String> params = new HashMap<>();
        params.put("body", title);
        params.put("out_trade_no", order);
        params.put("total_fee", (new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()) +"");

        mOkHttpHelper.post(getContext(), Url.wechatPay, params, new SpotsCallBack<WeChatPayBean>(getContext()) {
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
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type){
            case EVT_PAYSUCCESS:
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                ToastUtil.show("支付成功！");
                act.finishSelf();
                break;
        }
    }
}
