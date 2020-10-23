package com.people.loveme.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.WebFra;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.StringUtils;
import com.people.loveme.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/7/5 0005.
 * 注册
 */

public class RegistFra extends TitleFragment implements View.OnClickListener {
    private Unbinder unbinder;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_yqm)
    EditText etYqm;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private String code = "";
    private boolean isCheck = true;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_registe, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    public void initView() {
        tvAgree.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvSendCode.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck = b;
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
            case R.id.iv_close:
                act.finishSelf();
                break;
            case R.id.tv_next:
                String phone = etAccount.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("手机号不能为空");
                    return;
                }
                if (!StringUtils.isMobile(phone)) {
                    ToastUtil.show("你输入的手机号格式不正确");
                    return;
                }
//                String inviteCode = etCode.getText().toString().trim();
//                if (TextUtils.isEmpty(inviteCode)) {
//                    ToastUtil.show("验证码不能为空");
//                    return;
//                }


                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.show("密码不能为空");
                    return;
                }

                if (!StringUtils.isMatchesPwd6(password)) {
                    ToastUtil.show("密码必须大于6位，包含字母");
                    return;
                }

//                if (!code.equals(inviteCode)) {
//                    ToastUtil.show("验证码不正确，请重新输入");
//                    return;
//                }
//
//                if (!code.equals(inviteCode)) {
//                    ToastUtil.show("验证码不正确，请重新输入");
//                    return;
//                }
                isHave(phone);

                break;
            case R.id.tv_agree:
                Bundle bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", Url.REGISTER);
                ActivitySwitcher.startFragment(getActivity(), WebFra.class, bundle);
                break;
            case R.id.tv_send_code:
                String user_phone_number = etAccount.getText().toString().trim();
                //验证电话号码不能为空
                if (TextUtils.isEmpty(user_phone_number)) {
                    ToastUtil.show(getString(R.string.phonenum));
                    return;
                }
                //验证手机号是否正确
                if (!StringUtils.isMobile(user_phone_number)) {
                    ToastUtil.show("你输入的手机号格式不正确");
                    return;
                }
                isHave(user_phone_number);
                break;

        }
    }

    /**
     *校验是否注册过
     * @param phone
     */
    public void isHave(final String phone) {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(mContext, Url.isHave + phone, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                if (baseBean.getCode() == 1) {
                    ToastUtil.show("该账号已注册");
                } else {
                    userRegister(phone, etPassword.getText().toString().trim());
//                    code = TimerUtil.getNum();
//                    sendSMS(phone, code);
//                    TimerUtil mTimerUtil = new TimerUtil(tvSendCode);
//                    mTimerUtil.timers();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 获取短信验证码
     *
     * @param phone
     */
    public void sendSMS(String phone, String CODE) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("tpl_id", "87719");
        params.put("tpl_value", "%23code%23%3d" + CODE);
        params.put("key", "ef4245ba5b10050771db3bb2a97cad29");
        OkHttpHelper.getInstance().post(mContext, "https://v.juhe.cn/sms/send?", params, new SpotsCallBack<String>(getContext()) {

            @Override
            public void onSuccess(Response response, String string) {
                try {
                    JSONObject obj = new JSONObject(string);
                    if (obj.getString("error_code").equals("0")) {
                        ToastUtil.show("短信已发送，请注意查收");
                    } else {
                        ToastUtil.show(obj.getString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 用户注册
     *
     * @param userPhone
     * @param password
     * @param
     */
    private void userRegister(final String userPhone, final String password) {
        String code = etYqm.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("phone", userPhone);
        params.put("password", password);
        params.put("user_longitude", lng);
        params.put("user_latitude", lat);
        if (!StringUtil.isEmpty(code))
            params.put("code", code);

        mOkHttpHelper.post(getContext(), Url.register, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                Log.e("11111","loginBean ==== " + response.body().toString());
                if (baseBean.getCode() == 1) {
                    ToastUtil.show("تىزىملىتىش مۇۋەپپىقىيەتلىك！");
                    SharePrefUtil.saveBoolean(mContext, AppConsts.ISGUIDE, false);
                    eventCenter.sendType(EventCenter.EventType.EVT_RGIST);
                    act.finishSelf();
                } else
                    ToastUtil.show("注册失败，" + baseBean.getMsg());

            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
