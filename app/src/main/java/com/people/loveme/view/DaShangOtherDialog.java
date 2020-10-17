package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/25 0025.
 * 打赏他人弹窗
 */

public class DaShangOtherDialog extends Dialog implements View.OnClickListener {


    private Context context;
    private TextView tv1,tv2,tv5,tv10;
    private EditText etMoney;
    ImageView ivWechatPay, ivAlipayPay, ivClose;
    Pay pay;
    private double money = 2;
    private String payType;

    public DaShangOtherDialog(Context context,Pay pay) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.pay = pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_ds_other, null);
        ButterKnife.bind(view);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        tv1 = ((TextView) view.findViewById(R.id.tv1));
        tv2 = ((TextView) view.findViewById(R.id.tv2));
        tv5 = ((TextView) view.findViewById(R.id.tv5));
        tv10 = ((TextView) view.findViewById(R.id.tv10));
        ivWechatPay = ((ImageView) view.findViewById(R.id.iv_wechat_pay));
        ivAlipayPay = ((ImageView) view.findViewById(R.id.iv_alipay_pay));
        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        etMoney = ((EditText) view.findViewById(R.id.et_money));
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv1.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv2.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv5.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv10.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv1.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv2.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv5.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv10.setTextColor(context.getResources().getColor(R.color.txt_lv7));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().startsWith("0"))
                    return;
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv10.setOnClickListener(this);
        ivAlipayPay.setOnClickListener(this);
        ivWechatPay.setOnClickListener(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv1:
                money = 1;
                tv1.setBackgroundResource(R.drawable.bg_money_select_2dp);
                tv2.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv5.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv10.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv1.setTextColor(context.getResources().getColor(R.color.white));
                tv2.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv5.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv10.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                break;
            case R.id.tv2:
                money = 2;
                tv2.setBackgroundResource(R.drawable.bg_money_select_2dp);
                tv1.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv5.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv10.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv2.setTextColor(context.getResources().getColor(R.color.white));
                tv1.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv5.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv10.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                break;
            case R.id.tv5:
                money = 5;
                tv5.setBackgroundResource(R.drawable.bg_money_select_2dp);
                tv2.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv1.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv10.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv5.setTextColor(context.getResources().getColor(R.color.white));
                tv2.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv1.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv10.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                break;
            case R.id.tv10:
                money = 10;
                tv10.setBackgroundResource(R.drawable.bg_money_select_2dp);
                tv2.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv5.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv1.setBackgroundResource(R.drawable.bg_border_money_2dp);
                tv10.setTextColor(context.getResources().getColor(R.color.white));
                tv2.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv5.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                tv1.setTextColor(context.getResources().getColor(R.color.txt_lv7));
                break;
            case R.id.iv_alipay_pay:
                if (etMoney.getText().toString().length() > 0){
                    try {
                        money = Double.parseDouble(etMoney.getText().toString());
                        if (money < 0.01){
                            ToastUtil.show("金额不能小于0.01！");
                            return;
                        }
                    }catch (Exception e){
                        ToastUtil.show("金额格式错误，请重新输入！");
                        return;
                    }
                }
                payType = "3";
                dismiss();
                if (null != pay)
                    pay.pay(payType,money+"");
                break;
            case R.id.iv_wechat_pay:
                if (etMoney.getText().toString().length() > 0){
                    try {
                        money = Double.parseDouble(etMoney.getText().toString());
                        if (money < 0.01){
                            ToastUtil.show("金额不能小于0.01！");
                            return;
                        }
                    }catch (Exception e){
                        ToastUtil.show("金额格式错误，请重新输入！");
                        return;
                    }
                }
                payType = "2";
                dismiss();
                if (null != pay)
                    pay.pay(payType,money+"");
                break;
        }
    }

    public interface Pay{
        void pay(String payType,String money);
    }


}


