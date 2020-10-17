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

import com.people.loveme.R;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.user.HyfwFra;

/**
 * Created by kxn on 2018/10/26 0026.
 * vip 弹框
 */

public class VipDialog extends Dialog implements View.OnClickListener {
    ImageView ivClose;
    private Context context;

    public VipDialog(Context context) {
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
        View view = inflater.inflate(R.layout.dialog_vip, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        view.findViewById(R.id.tv_zxx).setOnClickListener(this);
        view.findViewById(R.id.tv_qkt).setOnClickListener(this);

        ivClose.setOnClickListener(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_zxx:
                dismiss();
                break;
            case R.id.tv_qkt:
                ActivitySwitcher.startFragment(context, HyfwFra.class,new Bundle());
                break;
        }
    }
}
