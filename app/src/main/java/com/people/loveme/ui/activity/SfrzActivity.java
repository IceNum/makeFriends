package com.people.loveme.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.GetFaceBean;
import com.people.loveme.bean.GetIsNeedSmrzBean;
import com.people.loveme.bean.SfRzBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.PayDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class SfrzActivity extends BaseFragAct implements View.OnClickListener, PayDialog.Pay, EventCenter.EventListener {

    @BindView(R.id.iv_idZ)
    ImageView ivIdZ;
    @BindView(R.id.iv_idF)
    ImageView ivIdF;
    @BindView(R.id.iv_idS)
    ImageView ivIdS;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idcardNum)
    EditText etIdcardNum;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.navi_title)
    TextView naviTitle;
    @BindView(R.id.navi_left)
    ImageView naviLeft;

    private String status;// 0=未审核,1审核ok,2审核失败
    private String name, card, userId;
    private Context mContext;
    PayDialog payDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfrz);
        ButterKnife.bind(this);
        tvSubmit.setOnClickListener(this);
        naviLeft.setOnClickListener(this);
        mContext = this;
        userId = SharePrefUtil.getString(mContext, AppConsts.UID, "");

        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            String biz_content = uri.getQueryParameter("biz_content");
            if (null != biz_content) {
                getRzResult(biz_content);
            }
        }

        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取身份认证信息
     */
    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(mContext, Url.personRzInfo, params, new SpotsCallBack<SfRzBean>(mContext) {
            @Override
            public void onSuccess(Response response, SfRzBean bean) {
                if (null != bean.getData()) {
                    PicassoUtil.setImag(mContext, bean.getData().getTopimage(), ivIdZ);
                    PicassoUtil.setImag(mContext, bean.getData().getBottomimage(), ivIdF);
                    PicassoUtil.setImag(mContext, bean.getData().getHandimage(), ivIdS);
                    if (!StringUtil.isEmpty(bean.getData().getRelaname()))
                        etName.setText(bean.getData().getRelaname());
                    if (!StringUtil.isEmpty(bean.getData().getIdcard()))
                        etIdcardNum.setText(bean.getData().getIdcard());
                    if (!StringUtil.isEmpty(bean.getData().getStatus()))
                        status = bean.getData().getStatus();
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
        OkHttpHelper.getInstance().get(Url.personRz, params, new SpotsCallBack<SfRzBean>(mContext) {
            @Override
            public void onSuccess(Response response, SfRzBean bean) {
                getInfo();
                ToastUtil.show("认证成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_RZZT);
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (null != status) {
                    if (status.equals("0")) {
                        ToastUtil.show("正在审核中，无需重复提交！");
                        return;
                    }

                    if (status.equals("1")) {
                        ToastUtil.show("审核已通过，无需重复提交！");
                        return;
                    }
                }

                name = etName.getText().toString();
                card = etIdcardNum.getText().toString();
                if (StringUtil.isEmpty(name)) {
                    ToastUtil.show("请填写姓名！");
                    return;
                }
                if (StringUtil.isEmpty(card)) {
                    ToastUtil.show("请填写身份证号！");
                    return;
                }

                if (card.length() != 18){
                    ToastUtil.show("身份证号码长度有误，请检查");
                    return;
                }
                isNeedPay();
                break;
            case R.id.navi_left:
                finish();
                break;
        }
    }

    /**
     * 是否需要支付
     */
    private void isNeedPay() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(mContext, Url.needPay, params, new SpotsCallBack<GetIsNeedSmrzBean>(mContext) {
            @Override
            public void onSuccess(Response response, GetIsNeedSmrzBean bean) {
                if (null != bean.getIsneedpay() && bean.getIsneedpay().equals("1")) {
                    try {
                        if (!StringUtil.isEmpty(bean.getMoney()) && Double.parseDouble(bean.getMoney()) > 0) {
                            showPayDialog(bean.getMoney());
                        } else {
                            getMakeUrl();
                        }
                    } catch (Exception e) {
                        getMakeUrl();
                    }
                } else
                    getMakeUrl();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void showPayDialog(String money) {
        payDialog = new PayDialog(mContext, money, this);
        payDialog.show();
    }


    /**
     * 创建订单
     */
    private void createOrder(final String amount, final String pay_type) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        params.put("pay_type", pay_type);//,2=微信,3=支付宝
        params.put("order_type", "6");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用
        params.put("detail", "实名认证支付");


        OkHttpHelper.getInstance().post(mContext, Url.createOrder, params, new SpotsCallBack<CreatOrderBean>(mContext) {
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
                        isNeedPay();
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
        params.put("title", "实名认证支付");
        params.put("money", amount);
        params.put("desc", "实名认证支付");
        OkHttpHelper.getInstance().post(mContext, Url.alipay, params, new SpotsCallBack<AlipayBean>(mContext) {
            @Override
            public void onSuccess(Response response, AlipayBean residueBean) {
                final String orderInfo = residueBean.getData();   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(SfrzActivity.this);
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
        params.put("body", "实名认证支付");
        params.put("out_trade_no", order);
        params.put("total_fee", new BigDecimal(amount).multiply(new BigDecimal("100")).intValue() + "");

        OkHttpHelper.getInstance().post(mContext, Url.wechatPay, params, new SpotsCallBack<WeChatPayBean>(mContext) {
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


    /**
     * 获取实名认证链接
     */
    private void getMakeUrl() {
        name = etName.getText().toString();
        card = etIdcardNum.getText().toString();
        if (StringUtil.isEmpty(name)) {
            ToastUtil.show("请填写姓名！");
            return;
        }
        if (StringUtil.isEmpty(card)) {
            ToastUtil.show("请填写身份证号！");
            return;
        }

        if (card.length() != 18) {
            ToastUtil.show("身份证号码长度有误，请检查");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("relaname", name);
        params.put("idcard", card);
        params.put("method", "make_url");
        OkHttpHelper.getInstance().get(Url.getFace, params, new SpotsCallBack<GetFaceBean>(mContext) {
            @Override
            public void onSuccess(Response response, GetFaceBean bean) {
                doVerify(bean.getData().getUrl());

            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 启动支付宝进行认证
     *
     * @param url 开放平台返回的URL
     */
    private void doVerify(String url) {
        if (hasApplication()) {
            Intent action = new Intent(Intent.ACTION_VIEW);
            StringBuilder builder = new StringBuilder();
            builder.append("alipays://platformapi/startapp?appId=20000067&url=");
            builder.append(URLEncoder.encode(url));
            action.setData(Uri.parse(builder.toString()));
            startActivity(action);
            eventCenter.sendType(EventCenter.EventType.EVT_TOALYPAY);
            finish();
        } else {
            //处理没有安装支付宝的情况
            new AlertDialog.Builder(mContext)
                    .setMessage("是否下载并安装支付宝完成认证?")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent action = new Intent(Intent.ACTION_VIEW);
                            action.setData(Uri.parse("https://m.alipay.com"));
                            startActivity(action);
                        }
                    }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 判断是否安装了支付宝
     *
     * @return true 为已经安装
     */
    private boolean hasApplication() {
        PackageManager manager = mContext.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List<ResolveInfo> list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    @Override
    public void pay(String payType, String money) {
        payDialog.dismiss();
        createOrder(money, payType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type){
            case EVT_PAYSUCCESS:
                getMakeUrl();
                break;
        }
    }
}
