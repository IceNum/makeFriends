package com.people.loveme.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.Md5;
import com.people.loveme.utils.StringUtils;
import com.people.loveme.utils.TimerUtil;
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
 * 找回密码
 */

public class FindBackPasswordFra extends TitleFragment implements View.OnClickListener {


    Unbinder unbinder;
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String code ="";

    @Override
    public String getTitleName() {
        return "忘记密码";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_findback_password, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    public void initView() {
        tvSendCode.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
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
            case R.id.tv_send_code:
                String user_phone_number = etAccount.getText().toString().trim();
                //验证电话号码不能为空
                if (TextUtils.isEmpty(user_phone_number)) {
                    ToastUtil.show("请输入手机号");
                    return;
                }
                //验证手机号是否正确
                if (!StringUtils.isMobile(user_phone_number)) {
                    ToastUtil.show("你输入的手机号格式不正确");
                    return;
                }

                code = TimerUtil.getNum();
                sendSMS(user_phone_number, code);
                TimerUtil mTimerUtil = new TimerUtil(tvSendCode);
                mTimerUtil.timers();
                break;
            case R.id.tv_submit:
                forgetpassword();
                break;
        }
    }

    /**
     * 获取短信验证码
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


    private void forgetpassword() {
        String user_phone_number = etAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String msgCode = etCode.getText().toString().trim();
        //验证电话号码不能为空
        if (TextUtils.isEmpty(user_phone_number)) {
            ToastUtil.show("请输入手机号");
            return;
        }
        //验证手机号是否正确
        if (!StringUtils.isMobile(user_phone_number)) {
            ToastUtil.show("你输入的手机号格式不正确");
            return;
        }

//        if (TextUtils.isEmpty(msgCode)) {
//            ToastUtil.show("验证码不能为空");
//        }
//        //验证验证码是否正确
//        if (!msgCode.equals(code)) {
//            ToastUtil.show("验证码不正确");
//            return;
//        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("密码不能为空");
            return;
        }

        if (!StringUtils.isMatchesPwd6(password)) {
            ToastUtil.show("密码必须大于6位，包含字母");
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("phone", user_phone_number);
        param.put("password",  Md5.encode(password));
        mOkHttpHelper.post(getContext(), Url.forgetpassword, param, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean resultBean) {
                ToastUtil.show(resultBean.getMsg());
                act.finishSelf();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

}

