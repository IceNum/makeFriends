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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kxn on 2019/1/21 0021.
 */
public class IncomeSectionDialog extends Dialog {
    WheelView wheelView1, wheelView2;
    TextView tvCancle;
    TextView tvSure;
    private Context context;
    private String title;
    OnItemClick onItemClick;
    private int position1, position2;

    public IncomeSectionDialog(Context context, String title, OnItemClick onItemClick) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
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
        View view = inflater.inflate(R.layout.dialog_age_section, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        final List<String> income = new ArrayList<>();
        income.add(context.getString(R.string.wo_buxian));
        income.addAll(Arrays.asList(context.getResources().getStringArray(R.array.income)));
        final List<String> incomeSecond = new ArrayList<>();

        incomeSecond.add(context.getString(R.string.wo_buxian));
        incomeSecond.addAll(income.subList(1, income.size()));


        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        tvSure = (TextView) view.findViewById(R.id.tv_sure);
        wheelView1.setTextSize(16);
        wheelView2.setTextSize(16);
        wheelView1.setCyclic(false);
        wheelView2.setCyclic(false);

        tvTitle.setText(title);
        final ArrayWheelAdapter adapter1 = new ArrayWheelAdapter(income);
        final ArrayWheelAdapter adapter2 = new ArrayWheelAdapter(incomeSecond);
        wheelView1.setAdapter(adapter1);
        wheelView1.setCurrentItem(0);
        wheelView1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position1 = index;
                incomeSecond.clear();
                incomeSecond.add(context.getString(R.string.wo_buxian));
                incomeSecond.addAll(income.subList(index + 1, income.size()));
                wheelView2.setAdapter(new ArrayWheelAdapter(incomeSecond));
                wheelView2.setCurrentItem(0);
                position2 = 0;
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
                String start = "0",end = "100";
                if (context.getString(R.string.wo_buxian).equals(income.get(position1))){
                    start = "0";
                }else if ("2千".equals(income.get(position1))){
                    start = "2";
                }else if ("4千".equals(income.get(position1))){
                    start = "4";
                }else if ("6千".equals(income.get(position1))){
                    start = "6";
                }else if ("1万".equals(income.get(position1))){
                    start = "10";
                }else if ("2万".equals(income.get(position1))){
                    start = "20";
                }else if ("5万".equals(income.get(position1))){
                    start = "50";
                }
                if (context.getString(R.string.wo_buxian).equals(income.get(position2))){
                    end = "100";
                }else if ("2千".equals(income.get(position2))){
                    end = "2";
                }else if ("4千".equals(income.get(position2))){
                    end = "4";
                }else if ("6千".equals(income.get(position2))){
                    end = "6";
                }else if ("1万".equals(income.get(position2))){
                    end = "10";
                }else if ("2万".equals(income.get(position2))){
                    end = "20";
                }else if ("5万".equals(income.get(position2))){
                    end = "50";
                }
                onItemClick.onItemClick(start + "-" + end);
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
        void onItemClick(String age);
    }


}
