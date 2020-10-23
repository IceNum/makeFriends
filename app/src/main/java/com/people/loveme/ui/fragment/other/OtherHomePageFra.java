package com.people.loveme.ui.fragment.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.adapter.OtherHomeDongtaiAdapter;
import com.people.loveme.adapter.UserXcAdapter;
import com.people.loveme.bean.AlipayBean;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.CreatOrderBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.GiftBean;
import com.people.loveme.bean.MyDtBean;
import com.people.loveme.bean.PhotoListBean;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.bean.TagsBean;
import com.people.loveme.bean.UserBasicBean;
import com.people.loveme.bean.UserZytjBean;
import com.people.loveme.bean.WeChatPayBean;
import com.people.loveme.bean.ZhaoHuBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.pay.PayResult;
import com.people.loveme.ui.fragment.user.UserDetailFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ShareUtils;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.DaShangOtherDialog;
import com.people.loveme.view.DaShangSuccessDialog;
import com.people.loveme.view.FhtjDialog;
import com.people.loveme.view.GiftPop;
import com.people.loveme.view.MyListView;
import com.people.loveme.view.PartColorTextView;
import com.people.loveme.view.ReLianDialog;
import com.people.loveme.view.ReasonDialog;
import com.people.loveme.view.SendLikeSxDialog;
import com.people.loveme.view.SendLikeSxVipDialog;
import com.people.loveme.view.SmrzDialog;
import com.people.loveme.view.VipDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/15 0015.
 * 他人主页
 */

public class OtherHomePageFra extends TitleFragment implements View.OnClickListener, DaShangOtherDialog.Pay, EventCenter.EventListener {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.iv_isVip)
    ImageView ivIsVip;
    @BindView(R.id.tv_recentLogin)
    TextView tvRecentLogin;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.ll_userInfo)
    LinearLayout llUserInfo;
    @BindView(R.id.tv_nxdb)
    TextView tvNxdb;
    @BindView(R.id.tv_xzdt)
    PartColorTextView tvXzdt;
    @BindView(R.id.lv_dt)
    MyListView lvDt;
    @BindView(R.id.tv_dzh)
    TextView tvDzh;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.tv_ds)
    TextView tvDs;
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
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.iv_love)
    ImageView ivLove;
    @BindView(R.id.fl_tags)
    TagFlowLayout flTags;
    @BindView(R.id.tv_xzdt_show)
    TextView tvXzdtShow;


    private String uid, name, head, biaoqian;
    OtherHomeDongtaiAdapter adapter;
    private List list;
    private boolean isLove = false;
    private List<PhotoListBean.DataBean> xcList;
    UserXcAdapter xcAdapter;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    private String loveState = "0", giftId;

    private List<String> tags;

    ArrayList<ImageInfo> headInfo = new ArrayList<>();
    GiftPop giftPop;
    List<GiftBean.DataBean> giftList = new ArrayList<>();
    private int userMoney = 0, giftMoney = 0;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_other_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    public void initView() {
        tags = new ArrayList<>();
        uid = getArguments().getString("uid");
        userMoney = Integer.parseInt(SharePrefUtil.getString(mContext, AppConsts.MONEY, "0"));
        eventCenter.registEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
        xcList = new ArrayList<>();
        xcAdapter = new UserXcAdapter(mContext, xcList, uid);
        gv.setAdapter(xcAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, i);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        if (null != uid) {
            getUserInfo();
            getRzzt();
            getPhotos();
            getUserZyInfo();
            getDynamic();
            getBasicInfo();
        }


        ivShare.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);
        tvDzh.setOnClickListener(this);
        llLike.setOnClickListener(this);
        tvDs.setOnClickListener(this);
        ivHead.setOnClickListener(this);

        list = new ArrayList<>();
        adapter = new OtherHomeDongtaiAdapter(mContext, list);
        lvDt.setAdapter(adapter);
        addViewer();
        isLike();
        getGiftList();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                new ShareUtils(act).share(Url.shareUrl, getString(R.string.app_name), getString(R.string.share_des));
                break;
            case R.id.iv_more:
                doPeople();
                break;
            case R.id.iv_back:
                act.finishSelf();
                break;
            case R.id.ll_userInfo:
                Bundle bundle = new Bundle();
                bundle.putString("id", uid);
                ActivitySwitcher.startFragment(act, UserDetailFra.class, bundle);
                break;
            case R.id.tv_dzh:
                Log.e("loveState = " , "====loveState====" + loveState);
                if (!loveState.equals("0")) {
                    new ReLianDialog(getContext()).show();
                    return;
                }
                if (!StringUtil.isEmpty(uid)) {
                    isCanZhaoHu(uid);
                }
                break;
            case R.id.ll_like:
                if (isLove)
                    delLove();
                else
                    sendLove();
                break;
            case R.id.tv_ds:
                giftPop = new GiftPop(mContext, giftList, new GiftPop.OnSelectListener() {
                    @Override
                    public void onSelect(int position) {
                        giftId = giftList.get(position).getId();
                        if (null != giftList.get(position).getMoney())
                            giftMoney = Integer.parseInt(giftList.get(position).getMoney());
                    }
                }, this);
                giftPop.setClippingEnabled(false);
                giftPop.showAtLocation(ivBack,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.iv_head:
                if (headInfo.size() == 0) {
                    ToastUtil.show("暂无头像！");
                    return;
                }
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) headInfo);
                bundle1.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                intent.putExtras(bundle1);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
                break;
            case R.id.btnSend:
                if (userId.equals(uid)){
                    ToastUtil.show("自己不能给自己送礼物");
                    return;
                }
                if (StringUtil.isEmpty(giftId)) {
                    ToastUtil.show("请选择要赠送的礼物");
                    return;
                }
                if (userMoney >= giftMoney)
                    giftGiving(giftId);
                else
                    ToastUtil.show("账号余额不足，请先去充值！");
                break;
        }
    }

    private void setTag() {
        final TagAdapter<String> tagAdapter = new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv_tag,
                        flTags, false);
                switch (position) {
                    case 0:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_age_50dp);
                        break;
                    case 1:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_address_50dp);
                        break;
                    case 2:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_shengao_50dp);
                        break;
                    case 3:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_xueli_50dp);
                        break;
                    case 4:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_shouru_50dp);
                        break;

                }

                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
            }
        };

        flTags.setAdapter(tagAdapter);
        flTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
    }

    private void isLike() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("be_like_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.checkLike, params, new BaseCallback<BaseBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                if (bean.getMsg().equals("已喜欢")) {
                    isLove = true;
                } else
                    isLove = false;
                changLove();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                isLove = false;
                changLove();
            }
        });
    }

    /**
     * 获取礼物列表
     */
    private void getGiftList() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getGiftList, params, new BaseCallback<GiftBean>() {
            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, GiftBean bean) {
                Log.e("11111","response ==== " + response.body().toString());
                if (!ListUtil.isEmpty(bean.getData()))
                    giftList.addAll(bean.getData());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 赠送礼物
     */
    private void giftGiving(String giftId) {
        Map<String, String> params = new HashMap<>();
        params.put("give_uid", userId);
        params.put("get_uid", uid);
        params.put("gift_id", giftId);
        OkHttpHelper.getInstance().post(getContext(), Url.giftGiving, params, new SpotsCallBack<BaseBean>(mContext) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                if (baseBean.getCode() == 1) {
                    new DaShangSuccessDialog(mContext).show();
                    userMoney -= giftMoney;
                } else
                    ToastUtil.show(baseBean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 判断是否可以打招呼
     */
    private void isCanZhaoHu(final String uid) {
        if (SharePrefUtil.getString(getContext(), AppConsts.UID, "").equals(uid)) {
            ToastUtil.show("自己不可以和自己打招呼");
            return;
        }
        eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
        Map<String, String> params = new HashMap<>();
        params.put("say_uid", SharePrefUtil.getString(getContext(), AppConsts.UID, ""));
        params.put("be_say_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.Zhaohu, params, new SpotsCallBack<ZhaoHuBean>(getContext()) {
            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {
                if (null != bean.getData()) {
                    switch (bean.getData().getError_code()) {
                        case -1: //不接受任何人
                            ToastUtil.show("该用户不接受任何人聊天");
                            break;
                        case -2://只接受符合条件用户
                            new FhtjDialog(getContext()).show();
                            break;
                        case -3://被拉黑
                            ToastUtil.show("被拉黑无法发起聊天");
                            break;
                        case -4://超时未打招呼
                            ToastUtil.show("超时未打招呼");
                            break;
                        case -5://会员打招呼次数上限
                            ToastUtil.show("今天打招呼已达上限");
                            break;
                        case -6://普通用户打招呼次数上限
                            new VipDialog(getContext()).show();
                            break;
                        case -7:
                            new SmrzDialog(getContext()).show();
                            break;
                        default:
                            if (!StringUtil.isEmpty(uid)) {
                                String nickName = SharePrefUtil.getString(getContext(), AppConsts.NAME, null);
                                String userHead = SharePrefUtil.getString(getContext(), AppConsts.HEAD, null);
                                if (null != userId && null != nickName && null != userHead)
                                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, nickName, Uri.parse(userHead)));
                                RongIM.getInstance().setMessageAttachedUserInfo(true);
                                RongIM.getInstance().startPrivateChat(getContext(), uid, name);
                            }
                            break;
                    }
                } else {
                    String nickName = SharePrefUtil.getString(getContext(), AppConsts.NAME, null);
                    String userHead = SharePrefUtil.getString(getContext(), AppConsts.HEAD, null);
                    if (null != userId && null != nickName && null != userHead)
                        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, nickName, Uri.parse(userHead)));
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    RongIM.getInstance().startPrivateChat(getContext(), uid, name);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    private void doPeople() {
        String[] list = getResources().getStringArray(R.array.dopeople);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(),  "选择操作", reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if (position == 1)
                    showReport();
                else
                    addBlack();

            }
        });
        reasonDialog.show();
    }

    private void showReport() {
        String[] list = getResources().getStringArray(R.array.jblx);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(getContext(), getString(R.string.wo_select_jubao_type), reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                report(reason.get(position));
            }
        });
        reasonDialog.show();
    }

    /**
     * 添加查看信息
     */
    private void addViewer() {
        Map<String, String> params = new HashMap<>();
        params.put("view_uid", userId);
        params.put("be_view_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.addViewer, params, new BaseCallback<BaseBean>() {

            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, BaseBean bean) {
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 举报
     */
    private void report(String type) {
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", userId);
        params.put("be_jubao_uid", uid);
        params.put("content", type);
        OkHttpHelper.getInstance().post(getContext(), Url.report, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 喜欢
     */
    private void sendLove() {
        if (userId.equals(uid)) {
            ToastUtil.show("不能关注自己");
            return;
        }
        eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
        Map<String, String> params = new HashMap<>();
        params.put("like_uid", userId);
        params.put("be_like_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.sendLove, params, new SpotsCallBack<ZhaoHuBean>(getContext()) {

            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {

                if (null != bean.getData()) {
                    switch (bean.getData().getError_code()) {
                        case -1://普通用户喜欢上限
                            if (null != bean.getData().getNumber())
                                new SendLikeSxDialog(getContext(), bean.getData().getNumber()).show();
                            else
                                new SendLikeSxDialog(getContext(), "15").show();
                            break;
                        case -2:
                            new SendLikeSxVipDialog(getContext()).show();
                            break;
                    }
                }
                isLove = !isLove;
                changLove();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void changLove() {
        if (isLove)
            ivLove.setImageResource(R.mipmap.xihuan_main);
        else
            ivLove.setImageResource(R.mipmap.xihuan_white);
    }

    /**
     * 取消喜欢
     */
    private void delLove() {
        Map<String, String> params = new HashMap<>();
        params.put("like_uid", userId);
        params.put("be_like_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.delLove, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("取消成功！");
                isLove = !isLove;
                changLove();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 拉黑
     */
    private void addBlack() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("be_black_uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.addBlack, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });


        RongIM.getInstance().addToBlacklist(uid, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.show("加入黑名单成功！");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (bean.getCode() == 0) {
                    ToastUtil.show("用户不存在！");
                    act.finishSelf();
                }

                if (null != bean.getData()) {

                    if (!StringUtil.isEmpty(bean.getData().getHeadimage())) {
                        PicassoUtil.setHeadImag(getContext(), bean.getData().getHeadimage(), ivHead);
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(bean.getData().getHeadimage());
                        info.setBigImageUrl(bean.getData().getHeadimage());
                        headInfo.add(info);
                    } else
                        PicassoUtil.setHeadImag(getContext(), R.mipmap.ic_head, ivHead);
                    if (!StringUtil.isEmpty(bean.getData().getNickname())) {
                        name = bean.getData().getNickname();
                        tvName.setText(bean.getData().getNickname());
                        tvTitle.setText(bean.getData().getNickname());
                    } else {
                        name = "未命名";
                        tvName.setText(name);
                        tvTitle.setText(name);
                    }
                    if (!StringUtil.isEmpty(bean.getData().getHeadimage())) {
                        head = bean.getData().getHeadimage();
                        PicassoUtil.setHeadImag(getContext(), head, ivHead);
                    }
                    if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1"))
                        ivIsVip.setImageResource(R.mipmap.vip_item);
                    else
                        ivIsVip.setImageResource(R.mipmap.vip_item_no);
                    if (!StringUtil.isEmpty(bean.getData().getLove_status())) {
                        loveState = bean.getData().getLove_status();
                        switch (loveState) {
                            case "0":

                                break;
                            case "1":
                            case "2":
                                ReLianDialog reLianDialog = new ReLianDialog(mContext);
                                reLianDialog.show();
                                break;
                        }
                    }
                    if (null != bean.getData().getSex() && bean.getData().getSex().equals("1")) {
                        ivSex.setImageResource(R.mipmap.nan_center);
                        tvXzdtShow.setText("心中的她");
                    } else {
                        ivSex.setImageResource(R.mipmap.nv_center);
                        tvXzdtShow.setText("心中的他");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getScore()))
                        tvJf.setText(new BigDecimal(bean.getData().getScore()).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "分");
                    else
                        tvJf.setText("0分");

                    if (!StringUtil.isEmpty(bean.getData().getDubai()))
                        tvNxdb.setText(bean.getData().getDubai());

//                    if (!StringUtil.isEmpty(bean.getData().getCity())){
//                        tags.add(bean.getData().getCity());
//                    }

                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        tags.add(bean.getData().getAge() + "岁");
                    }
                    if (!StringUtil.isEmpty(bean.getData().getLastlogintime())) {
                        Log.e("time", bean.getData().getLastlogintime() + "");
                        Log.e("time", System.currentTimeMillis() + "");
                        if ((System.currentTimeMillis() / 1000 - Double.parseDouble(bean.getData().getLastlogintime())) < 604800)
                            tvRecentLogin.setText("最近登录时间：" + TimeUtil.recentTime(System.currentTimeMillis() - Long.parseLong(bean.getData().getLastlogintime()) * 1000));
                        else
                            tvRecentLogin.setText("最近登录时间：7天前");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getBiaoqian())) {
                        biaoqian = bean.getData().getBiaoqian();
                        getTags();
                    } else {
                        tvTag1.setVisibility(View.GONE);
                        tvTag2.setVisibility(View.GONE);
                        tvTag3.setVisibility(View.GONE);
                    }
                }
                setTag();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 获取用户基本资料
     */
    private void getBasicInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.userInfo, params, new SpotsCallBack<UserBasicBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserBasicBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        tags.add(bean.getData().getHeight() + "cm");
                    }
                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "1":
                                tags.add("2千以下");
                                break;
                            case "2":
                                tags.add("2-4千");
                                break;
                            case "4":
                                tags.add("4-6千");
                                break;
                            case "6":
                                tags.add("6-1万");
                                break;
                            case "10":
                                tags.add("1-1.5万");
                                break;
                            case "15":
                                tags.add("1.5-2万");
                                break;
                            case "20":
                                tags.add("2-5万");
                                break;
                            case "50":
                                tags.add("5万以上");
                                break;
                            default:
                                tags.add("2千以上");
                                break;
                        }
                    }
                    if (!StringUtil.isEmpty(bean.getData().getEdu())) {
                        tags.add(bean.getData().getEdu());
                    }
                }

                setTag();
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
        params.put("uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.rzzt, params, new SpotsCallBack<RzztBean>(getContext()) {
            @Override
            public void onSuccess(Response response, RzztBean rzztBean) {
                if (null != rzztBean.getData()) {
                    if (rzztBean.getData().getPhone().equals("1")) {
                        ivIsMobile.setImageResource(R.mipmap.sjrenzheng);
                        ivIsMobile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成手机认证");
                            }
                        });
                    }

                    if (rzztBean.getData().getRelaname().equals("1")) {
                        ivIsCard.setImageResource(R.mipmap.sfrenzheng);
                        ivIsCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成身份认证");
                            }
                        });
                    }

                    if (rzztBean.getData().getCar().equals("1")) {
                        ivIsCar.setImageResource(R.mipmap.clrenzheng);
                        ivIsCar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成车辆认证");
                            }
                        });
                    }

                    if (rzztBean.getData().getEdu().equals("1")) {
                        ivIsEducation.setImageResource(R.mipmap.xlrenzheng);
                        ivIsEducation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成学历认证");
                            }
                        });
                    }

                    if (rzztBean.getData().getWork().equals("1")) {
                        ivIsProfession.setImageResource(R.mipmap.zyrenzheng);
                        ivIsProfession.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成职业认证");
                            }
                        });
                    }

                    if (rzztBean.getData().getHouse().equals("1")) {
                        ivIsHous.setImageResource(R.mipmap.fcrenzheng);
                        ivIsHous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.show("此用户已完成房产认证");
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 用户相册
     */
    private void getPhotos() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        mOkHttpHelper.post(mContext, Url.photoList, params, new SpotsCallBack<PhotoListBean>(mContext) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }

            @Override
            public void onSuccess(Response response, PhotoListBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    xcList.addAll(bean.getData());
                    xcAdapter.notifyDataSetChanged();
                    imageInfo.clear();
                    for (int i = 0; i < xcList.size(); i++) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(xcList.get(i).getImage());
                        info.setBigImageUrl(xcList.get(i).getImage());
                        imageInfo.add(info);
                    }
                    xcAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private String city, age, height, income, edu;

    /**
     * 获取用户征友条件
     */
    private void getUserZyInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.getFindTiaoJian, params, new SpotsCallBack<UserZytjBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserZytjBean bean) {
                StringBuffer xzdt = new StringBuffer();
                xzdt.append("想找一个");
                if (null != bean.getData()) {

                    if (!StringUtil.isEmpty(bean.getData().getCity())) {
                        city = bean.getData().getCity();
                        xzdt.append(city + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        age = bean.getData().getAge();
                        xzdt.append(getString(R.string.wo_buxian) + bean.getData().getAge() + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        height = bean.getData().getHeight();
                        xzdt.append(mContext.getString(R.string.height) + height + "cm,");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "0":
                                income = "2千以下";
                                break;
                            case "1":
                                income = "2-4千元";
                                break;
                            case "2":
                                income = "4-6千元";
                                break;
                            case "3":
                                income = "6千-1万元";
                                break;
                            case "4":
                                income = "1-1.5万元";
                                break;
                            case "5":
                                income = "1.5-2万元";
                                break;
                            case "6":
                                income = "2-5万元";
                                break;
                            case "7":
                                income = "5万元以上";
                                break;
                            default:
                                income = bean.getData().getIncome();
                                break;
                        }

                        xzdt.append(mContext.getString(R.string.wo_yueshou) + income + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getEdu())) {
                        edu = bean.getData().getEdu();
                        xzdt.append(bean.getData().getEdu() + "学历的");
                    }

                    xzdt.append("有缘人，快来联系我吧");
                }

                HashMap<String, Integer> keysColor = new HashMap<>();//参数之一:key:关键字 Value:颜色  但一定是Color(int)的.如果是#ffffff这样的话建议还是写在color资源包中,个人习惯.
                if (null != city)
                    keysColor.put(city, getResources().getColor(R.color.xzdt));
                if (null != age)
                    keysColor.put(age, getResources().getColor(R.color.xzdt));
                if (null != income)
                    keysColor.put(income, getResources().getColor(R.color.xzdt));
                if (null != edu)
                    keysColor.put(edu, getResources().getColor(R.color.xzdt));
                if (null != height)
                    keysColor.put(height, getResources().getColor(R.color.xzdt));
                tvXzdt.setPartText(xzdt.toString(), keysColor, getResources().getColor(R.color.txt_lv7));
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    /**
     * 获取用户动态
     */
    private void getDynamic() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        OkHttpHelper.getInstance().post(getContext(), Url.myDynamic, params, new BaseCallback<MyDtBean>() {
            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, MyDtBean myDtBean) {
                if (!ListUtil.isEmpty(myDtBean.getData()))
                    list.addAll(myDtBean.getData());
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 用户标签
     */
    private void getTags() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getTags, params, new SpotsCallBack<TagsBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TagsBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    String[] tags = biaoqian.split(",");
                    for (int i = 0; i < tags.length; i++) {
                        for (int j = 0; j < bean.getData().size(); j++) {
                            if (tags[i].equals(bean.getData().get(j).getId())) {
                                switch (i) {
                                    case 0:
                                        tvTag1.setText(bean.getData().get(j).getName());
                                        tvTag2.setVisibility(View.GONE);
                                        tvTag3.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        tvTag2.setText(bean.getData().get(j).getName());
                                        tvTag2.setVisibility(View.VISIBLE);
                                        tvTag3.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        tvTag3.setText(bean.getData().get(j).getName());
                                        tvTag3.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        tvTag1.setVisibility(View.GONE);
                                        tvTag2.setVisibility(View.GONE);
                                        tvTag3.setVisibility(View.GONE);
                                        break;
                                }
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


    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.txt_main);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 创建订单
     */
    private void createOrder(final String amount, final String pay_type) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        params.put("pay_type", pay_type);//,2=微信,3=支付宝
        params.put("order_type", "2");//1=用户充值,2=打赏用户,3=打赏平台,4=恋爱指导费,5=账号激活,6=实名制费用
        params.put("be_dashang_uid", uid);
        params.put("detail", "打赏用户");


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
        params.put("total_fee", (new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()) + "");

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
                new DaShangSuccessDialog(mContext).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_PAYSUCCESS);
    }
}
