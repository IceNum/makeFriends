package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleDialog;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.CashierInputFilter;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.TiXianDialog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/8/15 0015.
 */

public class TiXianFra extends TitleFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.et_alipay)
    EditText etAlipay;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.et_realName)
    EditText etRealName;
    @BindView(R.id.tvMoney)
    TextView tvMoney;

    private double ktx;
    private String ktxPrice;
    TiXianDialog tiXianDialog;

    @Override
    public String getTitleName() {
        return "提现";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_tixian, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        ktxPrice = getArguments().getString("price");
        if (null != ktxPrice) {
            etAmount.setHint("账户余额：" + ktxPrice);
            ktx = Double.parseDouble(ktxPrice);
        }

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etAmount.getText().toString().length() > 0)
                    tvMoney.setText(AppConsts.RMB + new BigDecimal(etAmount.getText().toString()).divide(new BigDecimal("10"), 1).multiply(new BigDecimal("0.85")));
                else
                    tvMoney.setText("0");
            }
        });
        InputFilter[] filters = {new CashierInputFilter()};
        etAmount.setFilters(filters);
    }

    @OnClick(R.id.btn_pay)
    void userCash() {
        String amount = etAmount.getText().toString();
        String realName = etRealName.getText().toString();
        String account = etAlipay.getText().toString();

        if (StringUtil.isEmpty(amount)) {
            ToastUtil.show("请输入提现金额");
            return;
        }
        if (StringUtil.isEmpty(account)) {
            ToastUtil.show("请输入支付宝账号");
            return;
        }
        if (StringUtil.isEmpty(realName)) {
            ToastUtil.show("请输入真实姓名");
            return;
        }

        try {
            double money = Double.parseDouble(amount);
            if (money < 1000) {
                new CircleDialog.Builder(act)
                        .setTitle("提示")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setText("虚拟币数量不能小于1000!")//内容
                        .setPositive(mContext.getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();

                return;
            }
            if (money > Double.parseDouble(ktxPrice)) {
                ToastUtil.show("提现虚拟币数量不能大于可用虚拟币数量");
                return;
            }

        } catch (Exception e) {
            ToastUtil.show("提现金额不正确！");
            return;
        }

        final Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("money", amount);
        params.put("account_name", realName);
        params.put("account", account);
        mOkHttpHelper.post(getContext(), Url.sqtx, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean resultBean) {
                if (null == tiXianDialog)
                    tiXianDialog = new TiXianDialog(mContext, TiXianFra.this);
                tiXianDialog.show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
            case R.id.iv_close:
                tiXianDialog.dismiss();
                act.finishSelf();
                break;

        }
    }
}
