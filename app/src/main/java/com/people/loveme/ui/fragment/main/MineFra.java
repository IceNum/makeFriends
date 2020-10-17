package com.people.loveme.ui.fragment.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.AppConsts;
import com.people.loveme.MyExtensionModule;
import com.people.loveme.R;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.AppStartBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.EwmBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.HeadShBean;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.bean.UserInfoBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.bean.ZhaoHuBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.activity.RzzxActivity;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.ui.fragment.system.SettingFra;
import com.people.loveme.ui.fragment.user.ClrzFra;
import com.people.loveme.ui.fragment.user.FbdtFra;
import com.people.loveme.ui.fragment.user.FcRzFra;
import com.people.loveme.ui.fragment.user.HyfwFra;
import com.people.loveme.ui.fragment.user.SfrzFra;
import com.people.loveme.ui.fragment.user.SjyzFra;
import com.people.loveme.ui.fragment.user.SkgwFra;
import com.people.loveme.ui.fragment.user.SxhwFra;
import com.people.loveme.ui.fragment.user.UserHomeFra;
import com.people.loveme.ui.fragment.user.UserInfoFra;
import com.people.loveme.ui.fragment.user.UserWalletFra;
import com.people.loveme.ui.fragment.user.WddtFra;
import com.people.loveme.ui.fragment.user.WxhdFra;
import com.people.loveme.ui.fragment.user.XiangceFra;
import com.people.loveme.ui.fragment.user.XlrzFra;
import com.people.loveme.ui.fragment.user.XyzxFra;
import com.people.loveme.ui.fragment.user.YqhyFra;
import com.people.loveme.ui.fragment.user.ZyrzFra;
import com.people.loveme.utils.FastBlurUtil;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.QRCode;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.DaShangSuccessDialog;
import com.people.loveme.view.EwmDialog;
import com.people.loveme.view.GuideDialog;
import com.people.loveme.view.LazdDialog;
import com.people.loveme.view.PeopleDoDialog;
import com.people.loveme.view.SingleChooseDialog;
import com.people.loveme.view.UserDialog;
import com.people.loveme.view.ZdyzrDialog;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/7/3 0003.
 * 我的
 */

public class MineFra extends CachableFrg implements View.OnClickListener, EventCenter.EventListener, BGARefreshLayout.BGARefreshLayoutDelegate, ZdyzrDialog.Pay {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    Unbinder unbinder;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.tv_lazt)
    TextView tvLazt;
    @BindView(R.id.ll_userInfo)
    LinearLayout llUserInfo;
    @BindView(R.id.tv_fbdt)
    TextView tvFbdt;
    @BindView(R.id.tv_hyfw)
    TextView tvHyfw;
    @BindView(R.id.tv_wxhd)
    TextView tvWxhd;
    @BindView(R.id.tv_wdqb)
    TextView tvWdqb;
    @BindView(R.id.ll_wdxc)
    LinearLayout llWdxc;
    @BindView(R.id.ll_rzzx)
    LinearLayout llRzzx;
    @BindView(R.id.ll_wdxy)
    LinearLayout llWdxy;
    @BindView(R.id.ll_wddt)
    LinearLayout llWddt;
    @BindView(R.id.ll_sxhw)
    LinearLayout llSxhw;
    @BindView(R.id.ll_skgw)
    LinearLayout llSkgw;
    @BindView(R.id.ll_yqhy)
    LinearLayout llYqhy;
    @BindView(R.id.ll_xsjc)
    LinearLayout llXsjc;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.iv_ewm)
    ImageView ivEwm;
    @BindView(R.id.iv_isMobile)
    ImageView ivIsMobile;
    @BindView(R.id.iv_isCard)
    ImageView ivIsCard;
    @BindView(R.id.iv_isEducation)
    ImageView ivIsEducation;
    @BindView(R.id.iv_isProfession)
    ImageView ivIsProfession;
    @BindView(R.id.iv_isCar)
    ImageView ivIsCar;
    @BindView(R.id.iv_isHous)
    ImageView ivIsHous;
    @BindView(R.id.tv_dynamicCount)
    TextView tvDynamicCount;
    @BindView(R.id.tv_loveCount)
    TextView tvLoveCount;
    @BindView(R.id.tv_showCount)
    TextView tvShowCount;
    @BindView(R.id.tv_zlwcl)
    TextView tvZlwcl;
    @BindView(R.id.bgRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.shadowView)
    View shadowView;
    @BindView(R.id.tv_shz)
    TextView tvShz;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.likeView)
    View likeView;
    @BindView(R.id.lookView)
    View lookView;
    Unbinder unbinder1;
    @BindView(R.id.giftView)
    View giftView;

    private String ewm, score, name, head;//用户二维码和用户信用分 昵称和头像

    private UserInfoBean userInfo;
    private RzztBean rzzT;

    private String status;//用户状态 1 正常， 0冻结
    private String money = "0";//余额
    private String viptime = "0";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = msg.getData().getParcelable("bitmap");
            Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 5);
            ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivBg.setImageBitmap(blurBitmap);
        }
    };

    private boolean isSh = false;

    @Override
    protected int rootLayout() {
        return R.layout.fra_mine;
    }

    @Override
    protected void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_EDITEINFO);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_RZZT);
        mRefreshLayout.setPullDownRefreshEnable(true);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        ivHead.setOnClickListener(this);
        tvLazt.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);
        tvFbdt.setOnClickListener(this);
        tvHyfw.setOnClickListener(this);
        tvWxhd.setOnClickListener(this);
        tvWdqb.setOnClickListener(this);
        llWdxc.setOnClickListener(this);
        llRzzx.setOnClickListener(this);
        llWdxy.setOnClickListener(this);
        llWddt.setOnClickListener(this);
        llSxhw.setOnClickListener(this);
        llSkgw.setOnClickListener(this);
        llYqhy.setOnClickListener(this);
        llXsjc.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        ivEwm.setOnClickListener(this);

        ivIsMobile.setOnClickListener(this);
        ivIsCard.setOnClickListener(this);
        ivIsEducation.setOnClickListener(this);
        ivIsProfession.setOnClickListener(this);
        ivIsCar.setOnClickListener(this);
        ivIsHous.setOnClickListener(this);
        ivVip.setOnClickListener(this);


        getInfo();
        isShenhe();

        getRzzt();
        isJlgx();
        ivEwm.setImageBitmap(QRCode.createQRCode(userId));
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.main_color);
        mImmersionBar.transparentStatusBar();
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();

        if (SharePrefUtil.getBoolean(getContext(),AppConsts.isHaveNewLift,false))
            giftView.setVisibility(View.VISIBLE);
        else
            giftView.setVisibility(View.GONE);

        getUserInfo();
    }

    List<String> lazt;

    private boolean isCanEnter(String status) {
        switch (status) {  //0审核中   1  已认证  2 审核失败   3未认证
            case "0":
                ToastUtil.show("审核中");
                return false;
            case "1":
                ToastUtil.show(getString(R.string.wo_yirenzheng));
                return false;
            default:
                return true;
        }
    }

    @Override
    public void onClick(View view) {
        final Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_isMobile:
                if (isCanEnter(rzzT.getData().getPhone()))
                    ActivitySwitcher.startFragment(getActivity(), SjyzFra.class);
                break;
            case R.id.iv_isCard:
                if (isCanEnter(rzzT.getData().getRelaname()))
                    ActivitySwitcher.startFragment(getActivity(), SfrzFra.class);
                break;
            case R.id.iv_isEducation:
                if (isCanEnter(rzzT.getData().getEdu()))
                    ActivitySwitcher.startFragment(getActivity(), XlrzFra.class);
                break;
            case R.id.iv_isProfession:
                if (isCanEnter(rzzT.getData().getWork()))
                    ActivitySwitcher.startFragment(getActivity(), ZyrzFra.class);
                break;
            case R.id.iv_isCar:
                if (isCanEnter(rzzT.getData().getCar()))
                    ActivitySwitcher.startFragment(getActivity(), ClrzFra.class);
                break;
            case R.id.iv_isHous:
                if (isCanEnter(rzzT.getData().getHouse()))
                    ActivitySwitcher.startFragment(getActivity(), FcRzFra.class);
                break;
            case R.id.ll_userInfo:
                ActivitySwitcher.startFragment(getActivity(), UserInfoFra.class, bundle);
                break;
            case R.id.iv_head:
                UserDialog userDialog = new UserDialog(getContext(), new PeopleDoDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
//                            bundle.putString("id", userId);
//                            ActivitySwitcher.startFragment(getActivity(), UserDetailFra.class, bundle);
                            ActivitySwitcher.startFragment(getActivity(), UserHomeFra.class, bundle);
                        } else
                            ActivitySwitcher.startFragment(getActivity(), UserInfoFra.class, bundle);
                    }
                });
                userDialog.show();
                break;
            case R.id.tv_lazt:
                lazt = Arrays.asList(getResources().getStringArray(R.array.lazt));
                SingleChooseDialog laChooseDialog = new SingleChooseDialog(getContext(), getString(R.string.wo_lianaizhuangtai), lazt, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        changeLoveStatus(position);
                    }
                });
                laChooseDialog.show();
                break;
            case R.id.tv_fbdt:
                ActivitySwitcher.startFragment(getActivity(), FbdtFra.class, bundle);
                break;
            case R.id.iv_vip:
            case R.id.tv_hyfw:
                bundle.putString("viptime", viptime);
                ActivitySwitcher.startFragment(getActivity(), HyfwFra.class, bundle);
                break;
            case R.id.tv_wxhd:
                ActivitySwitcher.startFragment(getActivity(), WxhdFra.class, bundle);
                break;
            case R.id.tv_wdqb:
                bundle.putString("money", money);
                ActivitySwitcher.startFragment(getActivity(), UserWalletFra.class, bundle);
                break;
            case R.id.ll_wdxc:
                ActivitySwitcher.startFragment(getActivity(), XiangceFra.class);
                break;
            case R.id.ll_rzzx:
//                ActivitySwitcher.startFragment(getActivity(), RzzxFra.class);
                ActivitySwitcher.start(getActivity(), RzzxActivity.class);
                break;
            case R.id.ll_wdxy:
                ActivitySwitcher.startFragment(getActivity(), XyzxFra.class);
                break;
            case R.id.ll_wddt:
                ActivitySwitcher.startFragment(getActivity(), WddtFra.class);
                break;
            case R.id.ll_sxhw:
                ActivitySwitcher.startFragment(getActivity(), SxhwFra.class);
                break;
            case R.id.ll_skgw:
                ActivitySwitcher.startFragment(getActivity(), SkgwFra.class);
                break;
            case R.id.ll_xsjc:
//                getStartImage();

                List<Integer> imags = new ArrayList<>();
                imags.add(R.mipmap.noviceguide1);
                imags.add(R.mipmap.noviceguide2);
                imags.add(R.mipmap.noviceguide3);
                new GuideDialog(getContext(), imags).show();
                break;
            case R.id.ll_yqhy:
                ActivitySwitcher.startFragment(getActivity(), YqhyFra.class);
                break;
            case R.id.ll_setting:
                ActivitySwitcher.startFragment(getActivity(), SettingFra.class);
                break;
            case R.id.iv_ewm:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;

        }
    }

    private void checkPmsLocation() {
        MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
    }


    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        EwmDialog ewmDialog = new EwmDialog(getContext(), userId, score, name, head);
        ewmDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getStartImage() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.appStartImage, params, new SpotsCallBack<AppStartBean>(getContext()) {
            @Override
            public void onSuccess(Response response, AppStartBean baseBean) {
                if (!ListUtil.isEmpty(baseBean.getData()))
//                    new GuideDialog(getContext(), baseBean.getData()).show();

                    SharePrefUtil.saveBoolean(getContext(), AppConsts.ISGUIDE, true);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.uerIndex, params, new SpotsCallBack<UserInfoBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserInfoBean userInfoBean) {
                if (null != userInfoBean.getData()) {
                    tvDynamicCount.setText("(" + userInfoBean.getData().getDongtai_num() + ")");
                    tvLoveCount.setText("(" + userInfoBean.getData().getLikeme_num() + ")");
                    tvShowCount.setText("(" + userInfoBean.getData().getViewme_num() + ")");
                    tvZlwcl.setText(userInfoBean.getData().getRate() + "%");
                    progressBar.setProgress(userInfoBean.getData().getRate());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 是否有审核中的头像
     */
    private void isShenhe() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.isShenhe, params, new SpotsCallBack<HeadShBean>(getContext()) {
            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                getUserInfo();
            }

            @Override
            public void onSuccess(Response response, final HeadShBean shBean) {
                if (shBean.getCode() == 0 && null != shBean.getSrc()) {
                    isSh = true;
                    shadowView.setVisibility(View.VISIBLE);
                    tvShz.setVisibility(View.VISIBLE);
                    tvShz.setText("审核中");
                    Picasso.with(getContext()).load(shBean.getSrc()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);
                    if (!StringUtil.isEmpty(shBean.getSrc()))
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, shBean.getSrc());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (shBean.getSrc().startsWith("http")) {
                                    Bitmap bmp = Picasso.with(getContext()).load(shBean.getSrc()).get();
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("bitmap", bmp);
                                    message.setData(bundle);
                                    mHandler.sendMessage(message);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else if (shBean.getCode() == 2 && null != shBean.getSrc()) {
                    isSh = true;
                    shadowView.setVisibility(View.VISIBLE);
                    tvShz.setVisibility(View.VISIBLE);
                    tvShz.setText("被拒绝");
                    if (!StringUtil.isEmpty(shBean.getSrc()))
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, shBean.getSrc());
                    Picasso.with(getContext()).load(shBean.getSrc()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (shBean.getSrc().startsWith("http")) {
                                    Bitmap bmp = Picasso.with(getContext()).load(shBean.getSrc()).get();
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("bitmap", bmp);
                                    message.setData(bundle);
                                    mHandler.sendMessage(message);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    shadowView.setVisibility(View.GONE);
                    tvShz.setVisibility(View.GONE);
                }

                getUserInfo();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show("获取审核失败");

                getUserInfo();
            }
        });
    }

    /**
     * 是否满60未建立恋爱关系
     */
    private void isJlgx() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().get(Url.notfound, params, new SpotsCallBack<ZhaoHuBean>(getContext()) {
            @Override
            public void onSuccess(Response response, ZhaoHuBean zhaoHuBean) {
                if (null != zhaoHuBean.getData()) {
                    if (zhaoHuBean.getData().getError_code() == 0)
                        new LazdDialog(getActivity()).show();
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
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();

                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getLikeme_num()) && !bean.getData().getLikeme_num().equals("0")) {
                        likeView.setVisibility(View.VISIBLE);
                    } else
                        likeView.setVisibility(View.GONE);

                    if (!StringUtil.isEmpty(bean.getData().getViewme_num()) && !bean.getData().getViewme_num().equals("0")) {
                        lookView.setVisibility(View.VISIBLE);
                    } else
                        lookView.setVisibility(View.GONE);

                    if (!StringUtil.isEmpty(bean.getData().getHeadimage()) && !isSh)
                        Picasso.with(getContext()).load(bean.getData().getHeadimage()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);
                    else if (!isSh)
                        Picasso.with(getContext()).load(R.mipmap.ic_logo).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

                    if (!StringUtil.isEmpty(bean.getData().getNickname())) {
                        name = bean.getData().getNickname();
                        tvName.setText(name);
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeadimage()) && !isSh)
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, bean.getData().getHeadimage());
                    if (!StringUtil.isEmpty(bean.getData().getNickname()))
                        SharePrefUtil.saveString(getContext(), AppConsts.NAME, bean.getData().getNickname());


                    if (!StringUtil.isEmpty(bean.getData().getViptime())) {
                        long time = Long.parseLong(bean.getData().getViptime()) - System.currentTimeMillis() / 1000;
                        int day = (int) (time / 86400);
                        viptime = day + "";
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeadimage()) && !isSh) {
                        head = bean.getData().getHeadimage();
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, head);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (!StringUtil.isEmpty(head) && head.startsWith("http")) {
                                        Bitmap bmp = Picasso.with(getContext()).load(head).get();
                                        Message message = new Message();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("bitmap", bmp);
                                        message.setData(bundle);
                                        mHandler.sendMessage(message);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                    if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1")) {
                        SharePrefUtil.saveBoolean(getContext(), AppConsts.ISVIP, true);
                        ivVip.setImageResource(R.mipmap.vip_item);
                        AppConsts.isVip = true;
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
                    } else {
                        SharePrefUtil.saveBoolean(getContext(), AppConsts.ISVIP, false);
                        ivVip.setImageResource(R.mipmap.vip_item_no);
                    }

                    if (!StringUtil.isEmpty(bean.getData().getLove_status())) {
                        switch (bean.getData().getLove_status()) {
                            case "0":
                                tvLazt.setText(getString(R.string.wo_zhengyouzhong));
                                break;
                            case "1":
                                tvLazt.setText(getString(R.string.wo_zhengzaiyuehui));
                                break;
                            case "2":
                                tvLazt.setText(getString(R.string.wo_zhaodaoyizhong));
                                break;
                        }
                    }
                    if (null != bean.getData().getSex() && bean.getData().getSex().equals("1"))
                        ivSex.setImageResource(R.mipmap.nan_center);
                    else
                        ivSex.setImageResource(R.mipmap.nv_center);
                    if (!StringUtil.isEmpty(bean.getData().getScore()))
                        score = new BigDecimal(bean.getData().getScore()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                    else
                        score = "0";

                    SharePrefUtil.saveString(getContext(), AppConsts.SCORE, score);
                    tvJf.setText(score + "分");

                    status = bean.getData().getStatus();//用户状态 1=正常 0=冻结
                    if (!StringUtil.isEmpty(bean.getData().getMoney())) {
                        money = bean.getData().getMoney();
                        SharePrefUtil.saveString(getContext(), AppConsts.MONEY, bean.getData().getMoney());
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }
        });
    }


    boolean isZdyzr = false;

    /**
     * 修改恋爱状态
     */
    private void changeLoveStatus(final int love_status) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("love_status", love_status + "");
        OkHttpHelper.getInstance().post(getContext(), Url.changeLoveStatus, params, new SpotsCallBack<EwmBean>(getContext()) {
            @Override
            public void onSuccess(Response response, EwmBean ewmBean) {
                tvLazt.setText(lazt.get(love_status));
                if (love_status == 2) {
                    new ZdyzrDialog(getContext(), MineFra.this).show();
                    isZdyzr = true;
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
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
                rzzT = rzztBean;
                if (null != rzztBean.getData()) {
                    if (rzztBean.getData().getPhone().equals("1"))
                        ivIsMobile.setImageResource(R.mipmap.sjrenzheng);
                    if (rzztBean.getData().getRelaname().equals("1"))
                        ivIsCard.setImageResource(R.mipmap.sfrenzheng);
                    if (rzztBean.getData().getCar().equals("1"))
                        ivIsCar.setImageResource(R.mipmap.clrenzheng);
                    if (rzztBean.getData().getEdu().equals("1"))
                        ivIsEducation.setImageResource(R.mipmap.xlrenzheng);
                    if (rzztBean.getData().getWork().equals("1"))
                        ivIsProfession.setImageResource(R.mipmap.zyrenzheng);
                    if (rzztBean.getData().getHouse().equals("1"))
                        ivIsHous.setImageResource(R.mipmap.fcrenzheng);


                    if (null != rzztBean.getData().getRelaname())
                        if (rzztBean.getData().getRelaname().equals("1"))
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, true);
                        else
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, false);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_EDITEINFO);
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_RZZT);
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_EDITEINFO:
                getInfo();
                isShenhe();
                break;
            case EVT_PAYSUCCESS:
                if (isZdyzr) {
                    new DaShangSuccessDialog(getContext()).show();
                    isZdyzr = false;
                }
                getInfo();
                isShenhe();
                break;
            case EVT_RZZT:
                getRzzt();
                break;
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getInfo();
        getRzzt();
        isShenhe();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }


    /**
     * 创建订单
     */
    private void createOrder(final String amount, final String pay_type) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        params.put("pay_type", pay_type);//,2=微信,3=支付宝
        params.put("order_type", "3");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用
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
    private Handler handler = new Handler() {
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
                        PayTask alipay = new PayTask(getActivity());
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
                    final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), null);
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
        IWXAPI wxApi = WXAPIFactory.createWXAPI(getContext(), null);
        wxApi.registerApp(appId);
        boolean bIsWXAppInstalledAndSupported = wxApi.isWXAppInstalled() && wxApi.isWXAppSupportAPI();
        return bIsWXAppInstalledAndSupported;
    }

    @Override
    public void pay(String payType, String money) {
        createOrder(money, payType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}

