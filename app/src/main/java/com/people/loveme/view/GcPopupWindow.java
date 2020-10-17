package com.people.loveme.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.people.loveme.R;

import java.util.List;

/**
 * Created by kxn on 2018/12/13 0013.
 */

public class GcPopupWindow extends PopupWindow {
    private final TextView tvSure;
    private TextView tvClear, tvAge, tvSex, tvXy, tvJzz;
    private LinearLayout llNl, llJzz, llSex, llXy;
    private View mMenuView;
    private Context context;
    HomePopupWindow.onChooseListener listener;
    private List<String> list;
    private int choosePosition;
    public static int NOCHOOSE = 100;


    public interface onChooseListener {
        void onChoose(int position);
    }

    @SuppressLint("InflateParams")
    public GcPopupWindow(final Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_pop_gc, null);
        llNl = ((LinearLayout) mMenuView.findViewById(R.id.ll_nl));
        llJzz = ((LinearLayout) mMenuView.findViewById(R.id.ll_jzz));
        llSex = ((LinearLayout) mMenuView.findViewById(R.id.ll_sex));
        llXy = ((LinearLayout) mMenuView.findViewById(R.id.ll_xy));


        tvSure = ((TextView) mMenuView.findViewById(R.id.tv_sure));
        tvClear = ((TextView) mMenuView.findViewById(R.id.tv_clear));

        tvAge = ((TextView) mMenuView.findViewById(R.id.tv_age));
        tvJzz = ((TextView) mMenuView.findViewById(R.id.tv_jzz));
        tvSex = ((TextView) mMenuView.findViewById(R.id.tv_sex));
        tvXy = ((TextView) mMenuView.findViewById(R.id.tv_xy));

        tvClear.setOnClickListener(onClickListener);
        tvSure.setOnClickListener(onClickListener);
        llNl.setOnClickListener(onClickListener);
        llJzz.setOnClickListener(onClickListener);
        llSex.setOnClickListener(onClickListener);
        llXy.setOnClickListener(onClickListener);

        // 设置CartoonTagPopupWindow的View
        this.setContentView(mMenuView);
        // 设置CartoonTagPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置CartoonTagPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置CartoonTagPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置CartoonTagPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        // 设置CartoonTagPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.ll_container).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    public void setOnChooseListener(HomePopupWindow.onChooseListener chooseLitener) {
        this.listener = chooseLitener;
    }

    public void setAge(String age) {
        tvAge.setText(age);
    }

    public void setSex(String sex) {
        tvSex.setText(sex);
    }

    public void setJzz(String jzz) {
        tvJzz.setText(jzz);
    }

    public void setXy(String xy) {
        tvXy.setText(xy);
    }




}


