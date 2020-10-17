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

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.people.loveme.R;

import java.util.List;

/**
 * Created by kxn on 2018/9/26 0026.
 */

public class DoubleChooseDialog extends Dialog {
    TextView tvTitle;
    WheelView wheelView1, wheelView2;
    TextView tvCancle;
    TextView tvSure;
    private Context context;
    private List<String> list;
    private String title;
    private int position1 = 0,position2 = 0;
    OnItemClick onItemClick;

    public DoubleChooseDialog(Context context, String title, List<String> list, OnItemClick onItemClick) {
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
        View view = inflater.inflate(R.layout.dialog_double_choose, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        tvSure = (TextView) view.findViewById(R.id.tv_sure);

        tvTitle.setText(title);
        final ArrayWheelAdapter adapter1 = new ArrayWheelAdapter(list);
        final ArrayWheelAdapter adapter2 = new ArrayWheelAdapter(list);
        wheelView1.setAdapter(adapter1);
        wheelView1.setCurrentItem(0);
        wheelView1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position1 = index;
                if (index < list.size() - 1)
                    wheelView2.setCurrentItem(index + 1);
            }
        });

        wheelView2.setAdapter(adapter2);
        wheelView2.setCurrentItem(0);
        wheelView2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position2 = index;
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(position1,position2);
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    public interface OnItemClick {
        void onItemClick(int position1,int position2);
    }


}