package com.people.loveme.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GiftBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.MyConversationFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.ui.fragment.system.ReportFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.DaShangSuccessDialog;
import com.people.loveme.view.GiftPop;
import com.people.loveme.view.ReasonDialog;
import com.people.loveme.view.ZzltDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import okhttp3.Request;
import okhttp3.Response;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener, EventCenter.EventListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    PopupWindow mPopupWindow;
    ZzltDialog zzltDialog;
    private String userId,giftId;
    private Context context;
    private boolean isOther = false;

    GiftPop giftPop;
    List<GiftBean.DataBean> giftList = new ArrayList<>();
    private int userMoney = 0, giftMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        context = this;
        ivBack.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        Uri uri = getIntent().getData();
        if (uri != null) {
            String param = uri.getQueryParameter("title");
            if (null != param)
                tvTitle.setText(param);
            userId = uri.getQueryParameter("targetId");
        }

        RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", s);
                ActivitySwitcher.startFragment(ConversationActivity.this, OtherHomePageFra.class, bundle);
                return true;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }
        });


        RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                if (userInfo.getUserId().equals(userId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", s);
                    ActivitySwitcher.startFragment(ConversationActivity.this, OtherHomePageFra.class, bundle);
                }
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });


        List<Message> list = RongIM.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, userId, -1, 1);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSenderUserId().equals(userId))
                isOther = true;
        }

        GlobalBeans.getSelf().getEventCenter().registEvent(this, EventCenter.EventType.EVT_SENDGIFT);
        userMoney = Integer.parseInt(SharePrefUtil.getString(this, AppConsts.MONEY, "0"));
        getGiftList();


        RongExtension mRongExtension = (RongExtension) findViewById(io.rong.imkit.R.id.rc_extension);

        EditText et = mRongExtension.getInputEditText();
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("text",editable.toString());
            }
        });

        MyConversationFragment fragment = (MyConversationFragment)getSupportFragmentManager().findFragmentById(R.id.conversation);
        fragment.setmTargetId(userId);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                isLikeEachOther(userId);

                break;
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.tv_qkjl:
                ToastUtil.show("清空成功！");
                RongIMClient.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, userId, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        finish();
                        RongIMClient.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, userId);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
                mPopupWindow.dismiss();
                break;
            case R.id.ll_zzlt:
                mPopupWindow.dismiss();
                endLT(userId);
                finish();
                break;
            case R.id.tv_jb:
                Bundle bundle = new Bundle();
                bundle.putString("beReportUid", userId);
                ActivitySwitcher.startFragment(this, ReportFra.class, bundle);
//                mPopupWindow.dismiss();
//                if (null != userId)
//                    showReport(userId);
                break;
            case R.id.tv_addHmd:
                if (null != userId)
                    addBlack();
                mPopupWindow.dismiss();
                break;
            case R.id.tv_zzlt:
                endLT(userId);
                zzltDialog.dismiss();
                finish();
                break;
            case R.id.tv_sure:
                zzltDialog.dismiss();
                finish();
                break;
            case R.id.btnSend:
                if (userId.equals(SharePrefUtil.getString(this, AppConsts.UID, ""))){
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

    /**
     * 判断是否相互喜欢
     */
    private void isLikeEachOther(String otherId) {
        Map<String, String> params = new HashMap<>();
        params.put("uid1", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("uid2", otherId);
        OkHttpHelper.getInstance().post(context, Url.xianghulike, params, new SpotsCallBack<BaseBean>(context) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {
                if (bean.getMsg().equals("非相互喜欢")) {
                    if (isOther) //未回复
                        showZzltDialog();
                    else
                        finish();
                } else
                    finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    /**
     * 终止聊天
     */
    private void endLT(String otherId) {
        Map<String, String> params = new HashMap<>();
        params.put("say_uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("be_say_uid", otherId);
        OkHttpHelper.getInstance().post(context, Url.zhongzhi, params, new SpotsCallBack<BaseBean>(context) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showReport(final String uid) {
        String[] list = getResources().getStringArray(R.array.jblx);
        final List<String> reason = Arrays.asList(list);
        ReasonDialog reasonDialog = new ReasonDialog(this,  getString(R.string.wo_select_jubao_type), reason, new ReasonDialog.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                report(reason.get(position), uid);
            }
        });
        reasonDialog.show();
    }

    /**
     * 举报
     */
    private void report(String type, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", SharePrefUtil.getString(this, AppConsts.UID, ""));
        params.put("be_jubao_uid", uid);
        params.put("content", type);
        OkHttpHelper.getInstance().post(this, Url.report, params, new SpotsCallBack<BaseBean>(this) {

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
     * 拉黑
     */
    private void addBlack() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(this, AppConsts.UID, ""));
        params.put("be_black_uid", userId);
        OkHttpHelper.getInstance().post(this, Url.addBlack, params, new SpotsCallBack<BaseBean>(this) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show(bean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });


        RongIM.getInstance().addToBlacklist(userId, new RongIMClient.OperationCallback() {
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
     * 获取礼物列表
     */
    private void getGiftList() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(this, Url.getGiftList, params, new BaseCallback<GiftBean>() {
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
        params.put("give_uid", SharePrefUtil.getString(this, AppConsts.UID, ""));
        params.put("get_uid", userId);
        params.put("gift_id", giftId);
        OkHttpHelper.getInstance().post(this, Url.giftGiving, params, new SpotsCallBack<BaseBean>(this) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                giftPop.dismiss();
                if (baseBean.getCode() == 1) {
                    new DaShangSuccessDialog(ConversationActivity.this).show();
                    userMoney -= giftMoney;
                } else
                    ToastUtil.show(baseBean.getMsg());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                giftPop.dismiss();
            }
        });
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        if (e.type.equals(EventCenter.EventType.EVT_SENDGIFT)){
            giftPop = new GiftPop(this, giftList, new GiftPop.OnSelectListener() {
                @Override
                public void onSelect(int position) {
                    giftId = giftList.get(position).getId();
                    if (null != giftList.get(position).getMoney())
                        giftMoney = Integer.parseInt(giftList.get(position).getMoney());
                }
            }, this);
            giftPop.showAtLocation(tvTitle,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }


    //    RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
//
    private class MySendMessageListener implements RongIM.OnSendMessageListener {


        /**
         * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
         *
         * @param message              消息实例。
         * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
         * @return true 表示走自己的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

            if (message.getSentStatus() == Message.SentStatus.FAILED) {
                if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
                    //不在聊天室
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
                    //不在讨论组
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
                    //不在群组
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
                    //你在他的黑名单中
                    ToastUtil.show("你在他的黑名单中");
                }
            } else
                isOther = false;

            MessageContent messageContent = message.getContent();

            if (messageContent instanceof TextMessage) {//文本消息
                TextMessage textMessage = (TextMessage) messageContent;
                Log.d("Message", "onSent-TextMessage:" + textMessage.getContent());
            } else if (messageContent instanceof ImageMessage) {//图片消息
                ImageMessage imageMessage = (ImageMessage) messageContent;
                Log.d("Message", "onSent-ImageMessage:" + imageMessage.getRemoteUri());
            } else if (messageContent instanceof VoiceMessage) {//语音消息

                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                Log.d("Message", "onSent-voiceMessage:" + voiceMessage.getUri().toString());
                return true;
            } else if (messageContent instanceof RichContentMessage) {//图文消息
                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                Log.d("Message", "onSent-RichContentMessage:" + richContentMessage.getContent());
            } else {
                Log.d("Message", "onSent-其他消息，自己来判断处理");
            }



            return false;
        }

        /**
         * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
         *
         * @param message 发送的消息实例。
         * @return 处理后的消息实例。
         */
        @Override
        public Message onSend(Message message) {
            return message;
        }
    }

    @Override
    public void onBackPressed() {
        isLikeEachOther(userId);
    }

    private void showZzltDialog() {
        if (null == zzltDialog)
            zzltDialog = new ZzltDialog(this, this);
        zzltDialog.show();
    }

    private void showPopupWindow() {
        int layoutId = R.layout.popup_conversation;   // 布局ID
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        contentView.findViewById(R.id.tv_qkjl).setOnClickListener(this);
        contentView.findViewById(R.id.ll_zzlt).setOnClickListener(this);
        contentView.findViewById(R.id.tv_jb).setOnClickListener(this);
        contentView.findViewById(R.id.tv_addHmd).setOnClickListener(this);
        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应(能够根据剩余空间自动选中向上向下弹出)
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        if (null == mPopupWindow)
            mPopupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x60000000);
        // 设置CartoonTagPopupWindow弹出窗体的背景
//        mPopupWindow.setBackgroundDrawable(dw);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        // popupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.showAsDropDown(ivMore);
    }
}
