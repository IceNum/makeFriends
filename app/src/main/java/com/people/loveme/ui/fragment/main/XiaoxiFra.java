package com.people.loveme.ui.fragment.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.ConversationListAdapterEx;
import com.people.loveme.adapter.MyPagerAdapter;
import com.people.loveme.bean.MessageBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.system.MessageSettingFra;
import com.people.loveme.ui.fragment.system.SystemMessagFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.model.Conversation;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class XiaoxiFra extends CachableFrg implements EventCenter.EventListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    Unbinder unbinder1;
    @BindView(R.id.rc_left)
    AsyncImageView rcLeft;
    @BindView(R.id.tv_message_num)
    TextView tvMessageNum;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_time_message)
    TextView tvTimeMessage;
    @BindView(R.id.tv_message_content)
    TextView tvMessageContent;
    @BindView(R.id.rl_message)
    RelativeLayout rlMessage;
    private ConversationListFragment mConversationListFragment = null;
    private ArrayList<Fragment> fragments;
    private MyPagerAdapter adapter;

    @Override
    protected int rootLayout() {
        return R.layout.fra_xiaoxi;
    }

    @Override
    protected void initView() {
        eventCenter.registEvent(this, EventCenter.EventType.EVT_MESSAGEREAD);
        fragments = new ArrayList<>();
        Fragment conversationList = initConversationList();
        fragments.add(conversationList);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(adapter);
        }
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySwitcher.startFragment(getActivity(), MessageSettingFra.class);
            }
        });

        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySwitcher.startFragment(getActivity(), SystemMessagFra.class);
            }
        });
//        getTongzhi();
    }

    /**
     * 获取未读消息
     */
    private void getTongzhi() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getTongzhi, params, new SpotsCallBack<MessageBean>(getContext()) {
            @Override
            public void onSuccess(Response response, MessageBean messageBean) {
                if (messageBean.getNum() > 0) {
                    tvMessageNum.setText(messageBean.getNum() + "");
                    tvMessageNum.setVisibility(View.VISIBLE);
                } else
                    tvMessageNum.setVisibility(View.GONE);
                SharePrefUtil.saveInt(getContext(), AppConsts.MESSAGENUM, messageBean.getNum());
                eventCenter.sendType(EventCenter.EventType.EVT_CHANGENUM);
                if (null != messageBean.getTime())
                    tvTimeMessage.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(messageBean.getTime()) * 1000)));
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
        mImmersionBar.statusBarColor(R.color.main_color);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.init();
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("/conversationlist")
                    .scheme("rong")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .build();
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
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

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_MESSAGEREAD:
                getTongzhi();
                break;
        }
    }
}
