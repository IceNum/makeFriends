package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
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
 * Created by kxn on 2018/9/10 0010.
 * 手机验证
 */

public class SjyzFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private String code;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_sjyz, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvSendCode.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        String phone = etAccount.getText().toString().trim();
        switch (view.getId()){
            case R.id.tv_send_code:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("手机号不能为空");
                    return;
                }
                code = TimerUtil.getNum();
                sendSMS(phone, code);
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("手机号不能为空");
                    return;
                }
                String inviteCode = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(inviteCode)) {
                    ToastUtil.show("验证码不能为空");
                }
                if (!code.equals(inviteCode)) {
                    ToastUtil.show("验证码不正确，请重新输入");
                    return;
                }
                mobileRz(phone);
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
                        TimerUtil mTimerUtil = new TimerUtil(tvSendCode);
                        mTimerUtil.timers();
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
     * 认证手机号
     * @param userPhone
     * @param
     */
    private void mobileRz(final String userPhone) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("phone", userPhone);
        mOkHttpHelper.post(getContext(), Url.saveUserInfo, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("认证成功！");
                act.finishSelf();
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                eventCenter.sendType(EventCenter.EventType.EVT_RZZT);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
