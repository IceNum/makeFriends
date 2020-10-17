package com.people.loveme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleDialog;
import com.people.loveme.AppConsts;
import com.people.loveme.GlobalBeans;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.AppStartBean;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.MessageBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.login.LoginFra;
import com.people.loveme.ui.fragment.main.GuangChangFra;
import com.people.loveme.ui.fragment.main.HomeFrg;
import com.people.loveme.ui.fragment.main.MineFra;
import com.people.loveme.ui.fragment.main.XiaoxiFra;
import com.people.loveme.ui.fragment.main.YuJianFra;
import com.people.loveme.ui.fragment.user.IncomeDetailFra;
import com.people.loveme.ui.fragment.user.SxhwFra;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseFragAct
        implements TabHost.OnTabChangeListener, EventCenter.EventListener, RongIM.UserInfoProvider {
    @BindView(R.id.tabhost)
    FragmentTabHost mTabHost;

    private TextView tvNum;
    private int curTab = 0, tabIdx = 0;

    private int messageNum; //聊天未读消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (GlobalBeans.getSelf() == null) {
            GlobalBeans.initForMainUI(getApplication());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null != getIntent().getStringExtra("tag")){
            if (getIntent().getStringExtra("tag").equals("gift"))
                ActivitySwitcher.startFragment(this, IncomeDetailFra.class);
            else
                ActivitySwitcher.startFragment(this, SxhwFra.class);
        }


        ButterKnife.bind(this);
        initTabHost();
        setTabSelected(curTab, true);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_TOHOME);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_TOSHOPCAR);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_LOGOUT);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_CHANGENUM);

        String token = SharePrefUtil.getString(this, AppConsts.RONGTOKEN, null);
        if (null != token)
            connect(token);

        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                MainActivity.this.getUserInfo(userId);
                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
        RongIM.setUserInfoProvider(this, true);
        final String nickName = SharePrefUtil.getString(MainActivity.this, AppConsts.NAME, "");
        final String userHead = SharePrefUtil.getString(MainActivity.this, AppConsts.HEAD, "");
        final String uid = SharePrefUtil.getString(MainActivity.this, AppConsts.UID, "");
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(uid, nickName, Uri.parse(userHead)));
//        boolean isguide = SharePrefUtil.getBoolean(this, AppConsts.ISGUIDE, false);
//        if (!isguide) {
//            List<Integer> imags = new ArrayList<>();
//            imags.add(R.mipmap.noviceguide1);
//            imags.add(R.mipmap.noviceguide2);
//            imags.add(R.mipmap.noviceguide3);
//            new GuideDialog(MainActivity.this, imags).show();
//            SharePrefUtil.saveBoolean(MainActivity.this, AppConsts.ISGUIDE, true);
//        }
        setJpushId();

        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                messageNum = i;
                 i += SharePrefUtil.getInt(MainActivity.this, AppConsts.MESSAGENUM, 0);
                if (i < 1)
                    tvNum.setVisibility(View.GONE);
                else
                    tvNum.setVisibility(View.VISIBLE);

                if (i > 99)
                    tvNum.setText("99+");
                else
                    tvNum.setText(i + "");
            }
        }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.SYSTEM);

        getUserInfo(SharePrefUtil.getString(getApplicationContext(),AppConsts.UID,""));
//        getTongzhi();
    }


    /**
     * 获取未读消息
     */
    private void getTongzhi() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(this,AppConsts.UID,""));
        OkHttpHelper.getInstance().post(this, Url.getTongzhi, params, new SpotsCallBack<MessageBean>(this) {
            @Override
            public void onSuccess(Response response, MessageBean messageBean) {
                int num = 0;
                try {
                    num = messageNum;
                } catch (Exception e1) {

                }
                num += messageBean.getNum();
                if (num > 0){
                    tvNum.setText(num + "");
                    tvNum.setVisibility(View.VISIBLE);
                }else
                    tvNum.setVisibility(View.GONE);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void setUserInfo(final String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(MainActivity.this, Url.getUserInfo, params, new BaseCallback<GetUserInfoBean>() {
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
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (userId.equals(SharePrefUtil.getString(getApplicationContext(),AppConsts.UID,""))){
                    if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1"))
                        SharePrefUtil.saveBoolean(MainActivity.this,AppConsts.ISVIP,true);
                    else
                        SharePrefUtil.saveBoolean(MainActivity.this,AppConsts.ISVIP,false);

                    SharePrefUtil.saveString(getApplicationContext(),AppConsts.MONEY,bean.getData().getMoney());
                }
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, bean.getData().getNickname(), Uri.parse(bean.getData().getHeadimage())));
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void getStartImage() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(this, Url.appStartImage, params, new SpotsCallBack<AppStartBean>(this) {
            @Override
            public void onSuccess(Response response, AppStartBean baseBean) {
                if (!ListUtil.isEmpty(baseBean.getData()))
//                    new GuideDialog(MainActivity.this, baseBean.getData()).show();

                    SharePrefUtil.saveBoolean(MainActivity.this, AppConsts.ISGUIDE, true);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 设置推送ID
     */
    private void setJpushId() {
        String registrationId = JPushInterface.getRegistrationID(this);
        Map<String, String> params = new HashMap<>();
        params.put("jpush_id", registrationId);
        params.put("uid", SharePrefUtil.getString(this, AppConsts.UID, ""));
        params.put("user_longitude", SharePrefUtil.getString(this, AppConsts.LNG, AppConsts.DEFAULTLNG));
        params.put("user_latitude", SharePrefUtil.getString(this, AppConsts.LAT, AppConsts.DEFAULTLAT));
        OkHttpHelper.getInstance().post(this, Url.saveUserInfo, params, new BaseCallback<BaseBean>() {
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
            public void onSuccess(Response response, BaseBean baseBean) {
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTabHost.setCurrentTab(tabIdx);
    }

    private void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < tabs.size(); i++) {
            final TabDesc td = tabs.get(i);
            final View vTab = makeTabView();
            ((TextView) vTab.findViewById(R.id.tab_label)).setText(td.name);

            if (i == 3) {
                tvNum = ((TextView) vTab.findViewById(R.id.tv_message_num));
                tvNum.setVisibility(View.VISIBLE);
            }
            refreshTab(vTab, td, false);
            mTabHost.addTab(mTabHost.newTabSpec(td.tag).setIndicator(vTab), td.frgClass, null);
        }
        mTabHost.setBackgroundResource(R.color.white);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(this);
    }

    private void setTabSelected(int tabIdx, boolean selected) {
        refreshTab(mTabHost.getTabWidget().getChildAt(tabIdx),
                tabs.get(tabIdx), selected);
    }

    private View makeTabView() {
        return this.getLayoutInflater().inflate(
                R.layout.maintab, mTabHost.getTabWidget(), false);
    }

    private void refreshTab(View vTab, TabDesc td, boolean selected) {
        final ImageView iv = (ImageView) vTab.findViewById(R.id.tab_image);
        iv.setImageResource(selected ? td.icSelect : td.icNormal);
    }

    private final static List<TabDesc> tabs = new ArrayList<TabDesc>() {
        {
            add(TabDesc.make("home", R.string.homepage,
                    R.mipmap.home, R.mipmap.home_choose, HomeFrg.class));
            add(TabDesc.make("yujian", R.string.yujian,
                    R.mipmap.yujian, R.mipmap.yujian_choose, YuJianFra.class));
            add(TabDesc.make("guangchang", R.string.guangchang,
                    R.mipmap.guangchang, R.mipmap.guangchang_choose, GuangChangFra.class));
            add(TabDesc.make("xiaoxi", R.string.xiaoxi,
                    R.mipmap.xiaoxi, R.mipmap.xiaoxi_choose, XiaoxiFra.class));
            add(TabDesc.make("mine", R.string.minepage,
                    R.mipmap.wode, R.mipmap.wode_choose, MineFra.class));
        }
    };

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (this.getApplicationInfo().packageName.equals(HcbApp.getCurProcessName(MainActivity.this))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.e("RongIM", "onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.e("RongIM", "onSuccess" + userid);
                    SharePrefUtil.saveString(MainActivity.this, AppConsts.RONGID, userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("RongIM", "onError" + errorCode);
                    if (errorCode == RongIMClient.ErrorCode.RC_DISCONN_KICK) {

                    }
                }
            });

            RongIM.getInstance().setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(ConnectionStatus connectionStatus) {
                    switch (connectionStatus) {

                        case CONNECTED://连接成功。

                            break;
                        case DISCONNECTED://断开连接。

                            break;
                        case CONNECTING://连接中。

                            break;
                        case NETWORK_UNAVAILABLE://网络不可用。

                            break;
                        case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                            new CircleDialog.Builder(MainActivity.this)
                                    .setTitle("提示")
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(false)
                                    .setText("该账号已在别处登录！")//内容
                                    .setPositive(getString(R.string.wo_queding), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ActivitySwitcher.startFragment(MainActivity.this, LoginFra.class);
                                            eventCenter.sendType(EventCenter.EventType.EVT_LOGOUT);
                                            SharePrefUtil.saveString(MainActivity.this, AppConsts.UID, null);
                                            finish();
                                            RongIM.getInstance().logout();
                                        }
                                    })
                                    .show();

                            break;
                    }
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventCenter.unregistEvent(this, EventCenter.EventType.EVT_TOHOME);
    }

    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type) {
            case EVT_TOHOME:
                tabIdx = 1;
                break;
            case EVT_TOSHOPCAR:
                tabIdx = 3;
                break;
            case EVT_LOGOUT:
                onExit();
                break;
            case EVT_CHANGENUM:
                int num = 0;
                try {
                    num = messageNum;
                } catch (Exception e1) {

                }
                num += SharePrefUtil.getInt(this, AppConsts.MESSAGENUM, 0);
                if (num > 0){
                    tvNum.setText(num + "");
                    tvNum.setVisibility(View.VISIBLE);
                }else
                    tvNum.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        setUserInfo(s);
        return null;
    }


    private static class TabDesc {
        String tag;
        int name;
        int icNormal;
        int icSelect;
        Class<? extends Fragment> frgClass;

        static TabDesc make(String tag, int name, int icNormal, int icSelect,
                            Class<? extends Fragment> frgClass) {
            TabDesc td = new TabDesc();
            td.tag = tag;
            td.name = name;
            td.icNormal = icNormal;
            td.icSelect = icSelect;
            td.frgClass = frgClass;
            return td;
        }

    }


    @Override
    public void onTabChanged(String s) {
        tabIdx = mTabHost.getCurrentTab();
        if (tabIdx == curTab) {
            return;
        }
        setTabSelected(curTab, false);
        curTab = tabIdx;
        setTabSelected(curTab, true);
    }


    private long backPressTime = 0;
    private static final int SECOND = 1000;

    @Override
    public void onBackPressed() {
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - backPressTime > 2 * SECOND) {
            backPressTime = uptimeMillis;
            ToastUtil.show(getString(R.string.press_again_to_leave));
        } else {
            ToastUtil.cancel();
            onExit();
        }
    }

    private void onExit() {
        finish();
        if (null != beans) {
            beans.onTerminate();
        }
    }


}