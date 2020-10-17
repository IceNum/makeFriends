package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 */

public class ChangePasswordFra extends TitleFragment {
    @BindView(R.id.et_password_now)
    EditText etPasswordNow;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    Unbinder unbinder;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_change_password, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassWord();
            }
        });
    }

    /**
     * 修改密码
     */
    private void changePassWord() {
        String password = etPassword.getText().toString();
        String passwordNow = etPasswordNow.getText().toString();
        String repassword = etConfirmPassword.getText().toString();

        if (StringUtil.isEmpty(passwordNow)) {
            ToastUtil.show("请输入原密码");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            ToastUtil.show("请输入密码");
            return;
        }
        if (StringUtil.isEmpty(repassword)) {
            ToastUtil.show("请输入确认密码");
            return;
        }

        if (!repassword.equals(password)) {
            ToastUtil.show("密码和确认密码不一致！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("password", passwordNow);
        params.put("phone", SharePrefUtil.getString(mContext, AppConsts.PHONE,""));
        OkHttpHelper.getInstance().post(getContext(), Url.changePassword, params, new SpotsCallBack<BaseBean>(mContext) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("密码修改成功！");
                act.finishSelf();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

