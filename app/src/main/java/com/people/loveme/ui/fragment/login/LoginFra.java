package com.people.loveme.ui.fragment.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.LoginBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.activity.MainActivity;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.ZhjhDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;


/**
 * Created by kxn on 2018/7/5 0005.
 */

public class LoginFra extends TitleFragment implements View.OnClickListener, EventCenter.EventListener {

    Unbinder unbinder;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_wb)
    TextView tvWb;


    private String type = null, token, thirdUid, nickName, userIcon, age;
    private Dialog progressDlg;
    private UMShareAPI mShareAPI;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_login, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        mShareAPI = UMShareAPI.get(mContext);
        initView();
        return rootView;
    }

    public void initView() {
        tvWechat.setOnClickListener(this);
        tvQq.setOnClickListener(this);
        tvWb.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        eventCenter.registEvent(this, EventCenter.EventType.EVT_RGIST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                ActivitySwitcher.startFragment(getActivity(), FindBackPasswordFra.class);
                break;
            case R.id.tv_login:
                String phone = etAccount.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                if (StringUtil.isEmpty(phone)) {
                    ToastUtil.show("手机号不能为空");
                    return;
                }
                if (StringUtil.isEmpty(pass)) {
                    ToastUtil.show("密码不能为空");
                    return;
                }
                userLogin(phone, pass);
                break;
            case R.id.tv_register:
                ActivitySwitcher.startFragment(getActivity(), RegistFra.class);
                break;
            case R.id.tv_qq:
                type = "1";
                ToastUtil.show("正在跳转QQ登录,请稍后...");
                UMShareAPI.get(mContext).doOauthVerify(getActivity(), SHARE_MEDIA.QQ, umOauthListener);
                break;
            case R.id.tv_wechat:
                type = "0";
                if (!isWeixinAvilible(mContext)) {
                    ToastUtil.show("请安装微信客户端");
                    return;
                }
                ToastUtil.show("正在跳转微信登录,请稍后...");
                UMShareAPI.get(mContext).doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN, umOauthListener);
                break;
            case R.id.tv_wb:
                ToastUtil.show("正在跳转微博登录,请稍后...");
                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, umOauthListener);
                break;
        }
    }


    /**
     * 用户登录
     *
     * @param phone
     * @param password
     */
    private void userLogin(final String phone, final String password) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        mOkHttpHelper.post(mContext,Url.login, params, new SpotsCallBack<LoginBean>(mContext) {
            @Override
            public void onSuccess(Response response, LoginBean loginBean) {
                if (loginBean.getCode().equals("-1")){
                    new ZhjhDialog(getActivity(),0).show();
                    return;
                }
                if (loginBean.getCode().equals("0")){
                    ToastUtil.show(loginBean.getMsg());
                    return;
                }

                act.finishSelf();
                SharePrefUtil.saveString(mContext, AppConsts.PHONE, phone);
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getRong_token()))
                    SharePrefUtil.saveString(mContext, AppConsts.RONGTOKEN, loginBean.getData().getRong_token());
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getId()))
                    SharePrefUtil.saveString(mContext, AppConsts.UID, loginBean.getData().getId() + "");

                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getHeadimage()))
                    SharePrefUtil.saveString(mContext, AppConsts.HEAD, loginBean.getData().getHeadimage());
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getNickname()))
                    SharePrefUtil.saveString(mContext, AppConsts.NAME, loginBean.getData().getNickname());

                if (!StringUtil.isEmpty(loginBean.getData().getIs_wanshan()) && !loginBean.getData().getIs_wanshan().equals("1")){
                    ActivitySwitcher.startFragment(act,CompleteInformationFra.class);
                    SharePrefUtil.saveBoolean(act,AppConsts.ISCOMPLETE,false);
                } else{
                    ActivitySwitcher.start(act, MainActivity.class);
                    SharePrefUtil.saveBoolean(act,AppConsts.ISCOMPLETE,true);
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show("账号信息不正确！");
            }
        });
    }


    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 授权监听
     */
    private UMAuthListener umOauthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i("onStart", "onStart: ");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            if (SHARE_MEDIA.QQ.equals(share_media))
                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
            else if (SHARE_MEDIA.WEIXIN.equals(share_media))
                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, umAuthListener);
            else if (SHARE_MEDIA.SINA.equals(share_media))
                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, umAuthListener);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i("onError", "onError: " + "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i("onCancel", "onCancel: " + "授权取消");
        }
    };

    /**
     * 登陆监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i("onStart", "onStart: ");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String login_type = "1", sex;
            if (SHARE_MEDIA.QQ.equals(share_media))
                login_type = "2";
            else if (SHARE_MEDIA.WEIXIN.equals(share_media))
                login_type = "1";
            else if (SHARE_MEDIA.SINA.equals(share_media))
                login_type = "3";
            nickName = map.get("name");//昵称
            userIcon = map.get("iconurl");//头像
            thirdUid = map.get("uid");//第三方平台id
            String gender = map.get("gender");
            if (gender.equals("男"))
                sex = "1";
            else
                sex = "2";
            thirdLogin(thirdUid, nickName, userIcon, login_type, sex);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i("onError", "onError: " + "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i("onCancel", "onCancel: " + "授权取消");
        }
    };

    /**
     * 第三方登录
     *
     * @param thirdUid
     * @param nickName
     * @param userIcon
     */
    private void thirdLogin(final String thirdUid, final String nickName, final String userIcon, String login_type, String sex) {
        Map<String, String> params = new HashMap<>();
        params.put("openid", thirdUid);
        params.put("type", login_type);
        params.put("headimage", userIcon);
        params.put("sex", sex);
        params.put("nickname", nickName);
        mOkHttpHelper.post(mContext, Url.thirdLogin, params, new SpotsCallBack<LoginBean>(mContext) {
            @Override
            public void onSuccess(Response response, LoginBean loginBean) {
                act.finishSelf();
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getRong_token()))
                    SharePrefUtil.saveString(mContext, AppConsts.RONGTOKEN, loginBean.getData().getRong_token());
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getId()))
                    SharePrefUtil.saveString(mContext, AppConsts.UID, loginBean.getData().getId() + "");
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getHeadimage()))
                    SharePrefUtil.saveString(mContext, AppConsts.HEAD, loginBean.getData().getHeadimage());
                if (null != loginBean.getData() && !StringUtil.isEmpty(loginBean.getData().getNickname()))
                    SharePrefUtil.saveString(mContext, AppConsts.NAME, loginBean.getData().getNickname());

                if (!StringUtil.isEmpty(loginBean.getData().getIs_wanshan()) && !loginBean.getData().getIs_wanshan().equals("1")){
                    ActivitySwitcher.startFragment(act,CompleteInformationFra.class);
                    SharePrefUtil.saveBoolean(act,AppConsts.ISCOMPLETE,false);
                } else{
                    ActivitySwitcher.start(act, MainActivity.class);
                    SharePrefUtil.saveBoolean(act,AppConsts.ISCOMPLETE,true);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onEvent(EventCenter.HcbEvent e) {
        switch (e.type){
            case EVT_RGIST:
                break;
        }
    }
}
