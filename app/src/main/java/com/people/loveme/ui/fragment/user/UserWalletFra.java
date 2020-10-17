package com.people.loveme.ui.fragment.user;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.adapter.CoinAdapter;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.CoinBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.MyGridView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class UserWalletFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.navi_title)
    TextView naviTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tvIncomeDetail)
    TextView tvIncomeDetail;
    @BindView(R.id.tv_ds)
    TextView tvDs;
    @BindView(R.id.tv_tx)
    TextView tvTx;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    CoinAdapter adapter;
    @BindView(R.id.gvCoin)
    MyGridView gvCoin;
    @BindView(R.id.giftView)
    View giftView;
    private List<CoinBean.DataBean> list;
    private double ktxPrice;
    private String money, amount, title;
    private int selectPositon = -1;
    private ProgressDialog loadingDialog;
    private String channel = "alipay";

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
        return HcbApp.self.getString(R.string.wo_qianbao);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_my_wallet, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    private void initView() {

        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setMessage("处理中，请稍候...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(true);
        llWechat.setOnClickListener(this);
        llAlipay.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvIncomeDetail.setOnClickListener(this);
        list = new ArrayList<>();
        ivBack.setOnClickListener(this);

        tvTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktxPrice > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("price", ktxPrice + "");
                    ActivitySwitcher.startFragment(act, TiXianFra.class, bundle);
                } else
                    ToastUtil.show("没有可提现余额！");

            }
        });
        list = new ArrayList<>();
        adapter = new CoinAdapter(mContext, list);
        gvCoin.setAdapter(adapter);
        gvCoin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectPositon = i;
                tvPay.setText("立即支付" + list.get(i).getMoney() + "元");
                title = "充值虚拟币" + list.get(i).getNum() + "个";
                amount = list.get(i).getMoney();
                adapter.setSelectPosition(i);
            }
        });
        getSetmeal();

    }


    /**
     * 获取充值套餐
     */
    private void getSetmeal() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getSetmeal, params, new SpotsCallBack<CoinBean>(getContext()) {
            @Override
            public void onSuccess(Response response, CoinBean coinBean) {
                if (null != coinBean.getData()) {
                    if (null != coinBean.getData()) {
                        list.addAll(coinBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(mContext, Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(mContext) {

            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (null != bean.getData() && null != bean.getData().getMoney()) {
                    SharePrefUtil.saveString(mContext, AppConsts.MONEY, bean.getData().getMoney());
                    money = bean.getData().getMoney();
                    ktxPrice = Double.parseDouble(money);
                    tvDs.setText(money + "");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.txt_main);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();

        if (SharePrefUtil.getBoolean(getContext(), AppConsts.isHaveNewLift, false))
            giftView.setVisibility(View.VISIBLE);
        else
            giftView.setVisibility(View.GONE);
        getUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                act.finishSelf();
                break;
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
                else
                    ToastUtil.show("请选择充值套餐");
                break;
            case R.id.tvIncomeDetail:
                ActivitySwitcher.startFragment(act, IncomeDetailFra.class);
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
        switch (channel) {
            case "wx":
                params.put("pay_type", "2");//支付类型:1=余额,2=微信,3=支付宝
                break;
            case "alipay":
                params.put("pay_type", "3");//支付类型:1=余额,2=微信,3=支付宝
                break;
        }
        params.put("order_type", "6");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用
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
        params.put("desc", title);

        mOkHttpHelper.post(getContext(), Url.alipay, params, new SpotsCallBack<AlipayBean>(getContext()) {
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

    private void goWechatPay(String order) {
        Map<String, String> params = new HashMap<>();
        params.put("body", title);
        params.put("out_trade_no", order);
        params.put("total_fee", (new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()) + "");

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
}
