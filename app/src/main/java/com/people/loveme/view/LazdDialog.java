package com.people.loveme.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by kxn on 2018/10/26 0026.
 * 恋爱指导弹框
 */

public class LazdDialog extends Dialog implements View.OnClickListener {
    ImageView ivClose;
    private Activity context;

    public LazdDialog(Activity context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_lazd, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ivClose = ((ImageView) view.findViewById(R.id.iv_close));

        view.findViewById(R.id.iv_wechat).setOnClickListener(this);
        view.findViewById(R.id.iv_alipay).setOnClickListener(this);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }

    private String channel;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wechat:
                channel = "wx";
                createOrder();
                dismiss();
                break;
            case R.id.iv_alipay:
                channel = "alipay";
                createOrder();
                dismiss();
                break;
        }
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context, AppConsts.UID,""));
        params.put("money", "99");
        switch (channel) {
            case "wx":
                params.put("pay_type", "2");//支付类型:1=余额,2=微信,3=支付宝
                break;
            case "alipay":
                params.put("pay_type", "3");//支付类型:1=余额,2=微信,3=支付宝
                break;
        }
        params.put("order_type", "4");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用
        params.put("detail", "恋爱指导费");


        OkHttpHelper.getInstance().post(getContext(), Url.createOrder, params, new SpotsCallBack<CreatOrderBean>(getContext()) {
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
                        dismiss();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show("支付失败");
                    }
                    break;
                }
            }
        }
    };

    private void goAlipay(String orderId) {
        Map<String, String> params = new HashMap<>();
        params.put("orderid", orderId);
        params.put("title", "恋爱指导费");
        params.put("money", "99");
        params.put("desc", "恋爱指导费");

        OkHttpHelper.getInstance().post(getContext(), Url.alipay, params, new SpotsCallBack<AlipayBean>(getContext()) {
            @Override
            public void onSuccess(Response response, AlipayBean residueBean) {
                final String orderInfo = residueBean.getData();   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(context);
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
        params.put("body", "恋爱指导费");
        params.put("out_trade_no", order);
        params.put("total_fee", "9900");
        OkHttpHelper.getInstance().post(getContext(), Url.wechatPay, params, new SpotsCallBack<WeChatPayBean>(getContext()) {
            @Override
            public void onSuccess(Response response, WeChatPayBean weChatPayBean) {
                if (isWxAppInstalledAndSupported(weChatPayBean.getData().getAppid())) {
                    //1.调用API前，需要先向微信注册您的APPID
                    final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
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
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, null);
        wxApi.registerApp(appId);

        boolean bIsWXAppInstalledAndSupported = wxApi.isWXAppInstalled() && wxApi.isWXAppSupportAPI();

        return bIsWXAppInstalledAndSupported;
    }
}
