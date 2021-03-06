package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.people.loveme.R;

import java.util.List;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class ReportDialog extends Dialog {

    private Context context;
    private List<String> list;
    private String title;
    ReportDialog.OnItemClick onItemClick;

    public ReportDialog(Context context,ReportDialog.OnItemClick onItemClick) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_report, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        TextView tvJb = (TextView) view.findViewById(R.id.tv_jb);
        tvJb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onItemClick.onItemClick(1);
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }


}
