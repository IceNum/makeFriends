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

import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/10/30 0030.
 */

public class TiXianDialog extends Dialog {


    private Context context;
    ImageView ivClose;
    View.OnClickListener onClickListener;

    public TiXianDialog(Context context,View.OnClickListener onClickListener) {
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
        View view = inflater.inflate(R.layout.dialog_tx_success, null);
        ButterKnife.bind(view);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.tv_confirm).setOnClickListener(onClickListener);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }


}

