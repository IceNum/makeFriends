package com.people.loveme.ui.fragment.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.SfRzBean;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.PayDialog;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Response;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class SfrzFra extends TitleFragment implements View.OnClickListener, PayDialog.Pay, EventCenter.EventListener {
    Unbinder unbinder;
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

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//图片
    private ArrayList<String> mSelectPathZheng = new ArrayList<>();
    private ArrayList<String> mSelectPathFan = new ArrayList<>();
    private ArrayList<String> mSelectPathSc = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private String shouchi, zheng, fan, name, card, again = "0";
    private int selectTag;

    private String status;// 0=未审核,1审核ok,2审核失败
    private int time = 0;

    @Override
    public String getTitleName() {
        return "身份认证";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_sfrz, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        ivIdZ.setOnClickListener(this);
        ivIdF.setOnClickListener(this);
        ivIdS.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
//        getInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
        eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
        time++;
    }

    /**
     * 获取身份认证信息
     */
    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.personRzInfo, params, new SpotsCallBack<SfRzBean>(getContext()) {
            @Override
            public void onSuccess(Response response, SfRzBean bean) {
                if (null != bean.getData()) {
                    again = "1";
                    PicassoUtil.setImag(mContext, bean.getData().getTopimage(), ivIdZ);
                    PicassoUtil.setImag(mContext, bean.getData().getBottomimage(), ivIdF);
                    PicassoUtil.setImag(mContext, bean.getData().getHandimage(), ivIdS);
                    if (!StringUtil.isEmpty(bean.getData().getRelaname()))
                        etName.setText(bean.getData().getRelaname());
                    if (!StringUtil.isEmpty(bean.getData().getIdcard()))
                        etIdcardNum.setText(bean.getData().getIdcard());
                    if (!StringUtil.isEmpty(bean.getData().getStatus()))
                        status = bean.getData().getStatus();

                    if (null != status && status.equals("1") && time > 1) {
                        eventCenter.sendType(EventCenter.EventType.EVT_RZZT);
                        eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                        act.finishSelf();
                    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_idZ:
                selectTag = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.iv_idF:
                selectTag = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.iv_idS:
                selectTag = 2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
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
                if (StringUtil.isEmpty(zheng)) {
                    ToastUtil.show("请选择身份证正面照");
                    return;
                }
                if (StringUtil.isEmpty(fan)) {
                    ToastUtil.show("请选择身份证反面照");
                    return;
                }
                if (StringUtil.isEmpty(shouchi)) {
                    ToastUtil.show("请选择身份证手持照");
                    return;
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

                if (card.length() != 18) {
                    ToastUtil.show("身份证号码长度有误，请检查");
                    return;
                }
//                isNeedPay();
                submitInfo();
                break;
        }
    }

    /**
     * 提交身份信息
     */
    private void submitInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("idcard", card);
        params.put("relaname", name);
        params.put("topimage", zheng);
        params.put("bottomimage", fan);
        params.put("handimage", shouchi);
        params.put("again", again);//是否再次认证(1=再次提交
        OkHttpHelper.getInstance().post(getContext(), Url.personRz, params, new SpotsCallBack<GetIsNeedSmrzBean>(getContext()) {
            @Override
            public void onSuccess(Response response, GetIsNeedSmrzBean bean) {
                act.finishSelf();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show("提交失败！");
            }
        });
    }

    /**
     * 是否需要支付
     */
    private void isNeedPay() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.needPay, params, new SpotsCallBack<GetIsNeedSmrzBean>(getContext()) {
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
        PayDialog payDialog = new PayDialog(mContext, money, this);
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
        params.put("title", "实名认证");
        params.put("money", amount);
        params.put("desc", "实名认证");
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
        params.put("body", "实名认证");
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
        OkHttpHelper.getInstance().get(Url.getFace, params, new SpotsCallBack<GetFaceBean>(getContext()) {
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

    private void checkPmsLocation() {
        MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }


    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        MultiImageSelector selector = MultiImageSelector.create(getContext());
        selector.showCamera(true);
        selector.count(1);
        selector.multi();
        switch (selectTag) {
            case 0:
                selector.origin(mSelectPathZheng)
                        .start(getActivity(), REQUEST_IMAGE);
                break;
            case 1:
                selector.origin(mSelectPathFan)
                        .start(getActivity(), REQUEST_IMAGE);
                break;
            case 2:
                selector.origin(mSelectPathSc)
                        .start(getActivity(), REQUEST_IMAGE);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                switch (selectTag) {
                    case 0:
                        mSelectPathZheng = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        Picasso.with(mContext).load(new File(mSelectPathZheng.get(0))).into(ivIdZ);
                        StringBuilder sb0 = new StringBuilder();
                        imagsPath.clear();
                        for (String p : mSelectPathZheng) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setThumbnailPath(p);
                            sb0.append(p);
                            sb0.append("\n");
                            imagsPath.add(imageItem);
                        }
                        break;
                    case 1:
                        mSelectPathFan = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        Picasso.with(mContext).load(new File(mSelectPathFan.get(0))).into(ivIdF);
                        StringBuilder sb1 = new StringBuilder();
                        imagsPath.clear();
                        for (String p : mSelectPathFan) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setThumbnailPath(p);
                            sb1.append(p);
                            sb1.append("\n");
                            imagsPath.add(imageItem);
                        }
                        break;
                    case 2:
                        mSelectPathSc = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        Picasso.with(mContext).load(new File(mSelectPathSc.get(0))).into(ivIdS);
                        StringBuilder sb2 = new StringBuilder();
                        imagsPath.clear();
                        for (String p : mSelectPathSc) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setThumbnailPath(p);
                            sb2.append(p);
                            sb2.append("\n");
                            imagsPath.add(imageItem);
                        }
                        break;
                }
                upLoad();
            }
        }
    }

    /**
     * 上传文件
     */
    private void upLoad() {
        List<String> head = new ArrayList<>();
        List<File> headFile = new ArrayList<>();
        for (int i = 0; i < imagsPath.size(); i++) {
            head.add(imagsPath.get(i).getThumbnailPath());
        }
        try {
            headFile.addAll(Luban.with(getContext()).load(head).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<File>> images = new HashMap<>();
        if (!ListUtil.isEmpty(headFile))
            images.put("file", headFile);
        mOkHttpHelper.post_map_file(mContext, Url.upload, new HashMap<String, Object>(), images, new SpotsCallBack<UpLoadFileBean>(mContext) {
            @Override
            public void onSuccess(Response response, UpLoadFileBean upLoadFileBean) {
                switch (selectTag) {
                    case 0:
                        zheng = upLoadFileBean.getData();
                        break;
                    case 1:
                        fan = upLoadFileBean.getData();
                        break;
                    case 2:
                        shouchi = upLoadFileBean.getData();
                        break;
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void pay(String payType, String money) {
        createOrder(money, payType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_PAYSUCCESS:
                getMakeUrl();
                break;
        }
    }
}
