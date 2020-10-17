package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.adapter.StringAdapter;

import java.util.List;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class ReasonDialog extends Dialog {

    private Context context;
    private List<String> list;
    private String title;
    OnItemClick onItemClick;

    public ReasonDialog(Context context, String title, List<String> list, OnItemClick onItemClick) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.list = list;
        this.title = title;
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
        View view = inflater.inflate(R.layout.dialog_reason, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ListView lv = (ListView) view.findViewById(R.id.lv);

        tvTitle.setText(title);
        lv.setAdapter(new StringAdapter(context, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != onItemClick)
                    onItemClick.onItemClick(i);
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

    }

    public interface OnItemClick {
        void onItemClick(int position);
    }


}

