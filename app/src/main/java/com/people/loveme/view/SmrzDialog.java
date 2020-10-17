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
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.activity.SfrzActivity;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/25 0025.
 * 实名认证弹窗
 */

public class SmrzDialog extends Dialog {
    private Context context;
    ImageView ivClose;
    private TextView tvSure;
    private PartColorTextView tvHint;

    public SmrzDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_smrz, null);
        ButterKnife.bind(view);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        tvSure = ((TextView) view.findViewById(R.id.tv_qrz));
        tvHint = ((PartColorTextView) view.findViewById(R.id.tv_hint));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySwitcher.start(context, SfrzActivity.class,new Bundle());
                dismiss();
            }
        });

        HashMap<String, Integer> keysColor = new HashMap<>();//参数之一:key:关键字 Value:颜色  但一定是Color(int)的.如果是#ffffff这样的话建议还是写在color资源包中,个人习惯.
        keysColor.put("实名认证", context.getResources().getColor(R.color.qrz));
        tvHint.setPartText("您尚未完成实名认证，无法进行聊天交友哦~ ", keysColor, context.getResources().getColor(R.color.sure));//多关键词多颜色

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }


}
