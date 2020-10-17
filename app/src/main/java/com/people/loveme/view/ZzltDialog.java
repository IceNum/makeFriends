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
import com.people.loveme.utils.DisplayUtil;

/**
 * Created by kxn on 2018/11/5 0005.
 * 终止聊天
 */

public class ZzltDialog extends Dialog {
    ImageView ivClose;
    private Context context;
    private View.OnClickListener onClickListener;

    public ZzltDialog(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_zzlt, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        view.findViewById(R.id.tv_zzlt).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_sure).setOnClickListener(onClickListener);
        ivClose = ((ImageView) view.findViewById(R.id.iv_close));

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
        lp.height = lp.width + DisplayUtil.dip2px(context, 60);
        dialogWindow.setAttributes(lp);
    }


}

