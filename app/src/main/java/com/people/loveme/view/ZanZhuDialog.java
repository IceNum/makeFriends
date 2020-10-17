package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;

import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/11/23 0023.
 */

public class ZanZhuDialog extends Dialog implements View.OnClickListener {


    private Context context;
    ImageView ivWechatPay, ivAlipayPay, ivClose;
    TextView tvMoney;
    Pay pay;
    private String money;
    private String payType;

    public ZanZhuDialog(Context context, String money, Pay pay) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.pay = pay;
        this.money = money;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_zanzhu, null);
        ButterKnife.bind(view);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        tvMoney = ((TextView) view.findViewById(R.id.tv_money));
        ivWechatPay = ((ImageView) view.findViewById(R.id.iv_wechat_pay));
        ivAlipayPay = ((ImageView) view.findViewById(R.id.iv_alipay_pay));
        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ivAlipayPay.setOnClickListener(this);
        ivWechatPay.setOnClickListener(this);
        tvMoney.setText("¥ " + money);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_alipay_pay:
                payType = "3";
                dismiss();
                if (null != pay)
                    pay.pay(payType, money + "");
                break;
            case R.id.iv_wechat_pay:
                payType = "2";
                dismiss();
                if (null != pay)
                    pay.pay(payType, money + "");
                break;
        }
    }

    public interface Pay {
        void pay(String payType, String money);
    }


}


