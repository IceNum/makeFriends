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
import com.people.loveme.ui.fragment.user.HyfwFra;

import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/11/1 0001.
 * 普通发送喜欢上限
 */

public class SendLikeSxDialog extends Dialog {
    private Context context;
    ImageView ivClose;
    TextView tvTime;
    private String time;

    public SendLikeSxDialog(Context context, String time) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_sendlike_sx, null);
        ButterKnife.bind(view);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        tvTime = ((TextView) view.findViewById(R.id.time));
        if (null != time)
            tvTime.setText(time + "次");
        view.findViewById(R.id.tv_wzxx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_qkt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySwitcher.startFragment(context, HyfwFra.class, new Bundle());
                dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }


}
