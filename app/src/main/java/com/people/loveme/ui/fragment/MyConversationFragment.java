package com.people.loveme.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.mylhyl.circledialog.CircleDialog;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.user.HyfwFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.mention.RongMentionManager;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

import static io.rong.imlib.model.Conversation.ConversationType.PRIVATE;

/**
 * Created by kxn on 2019/5/9 0009.
 */
public class MyConversationFragment extends ConversationFragment {
    String mTargetId;
    ImageView mVoiceToggle;


    @Override
    public void onResume() {
        super.onResume();
        mVoiceToggle = getView().findViewById(R.id.rc_voice_toggle);
        if (AppConsts.isVip) {
            mVoiceToggle.setVisibility(View.VISIBLE);
        } else {
            mVoiceToggle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSendToggleClick(View v, String text) {
        int times = SharePrefUtil.getInt(getContext(), AppConsts.SENDTIMES, 0);
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(text.trim())) {
            if (!SharePrefUtil.getBoolean(getContext(), AppConsts.ISVIP, false)) {
                if (times > 9) {
                    if (times >= 10) {
                        new CircleDialog.Builder(getActivity())
                                .setTitle("提示")
                                .setCancelable(true)
                                .setCanceledOnTouchOutside(true)
                                .setText("充值会员更好沟通！")//内容
                                .setPositive(getContext().getString(R.string.wo_queding), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("viptime", "0");
                                        ActivitySwitcher.startFragment(getActivity(), HyfwFra.class, bundle);
                                    }
                                })
                                .show();
                    }
                    if (text.length() > 2) {
                        text = text.substring(0, 2) + "* * *";
                    }
                }
                times++;
                SharePrefUtil.saveInt(getContext(), AppConsts.SENDTIMES, times);
            }
            TextMessage textMessage = TextMessage.obtain(text);
            MentionedInfo mentionedInfo = RongMentionManager.getInstance().onSendButtonClick();
            if (mentionedInfo != null) {
                textMessage.setMentionedInfo(mentionedInfo);
            }
            Message message = Message.obtain(this.mTargetId, PRIVATE, textMessage);
            RongIM.getInstance().sendMessage(message, (String) null, (String) null, (IRongCallback.ISendMessageCallback) null);
        }
    }

    @Override
    public void onPluginClicked(IPluginModule pluginModule, int position) {


        if (pluginModule.obtainTitle(getContext()).equals("图片")) {
            ToastUtil.show("选择图片");
        } else
            super.onPluginClicked(pluginModule, position);
    }

    public void setmTargetId(String mTargetId) {
        this.mTargetId = mTargetId;
    }
}
