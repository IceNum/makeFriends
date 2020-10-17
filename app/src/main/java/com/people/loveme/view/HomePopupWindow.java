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
import com.people.loveme.bean.HomeTjBean;
import com.people.loveme.utils.ListUtil;

import java.util.List;

/**
 * Created by kxn on 2018/10/25 0025.
 * 首页筛选
 */

public class HomePopupWindow extends PopupWindow {
    private final TextView tvSure;
    private TextView tvClear, tvAge, tvYsr, tvXl, tvJzz, tvSg, tvMz, tvIsCar, tvJg, tvLastTime, tvIsHouse, tvIsRealName, tvXy;
    private LinearLayout llNl, llJzz, llSg, llXl, llYsr, llMz, llIsCar, llJg, llLastTime, llIsHouse, llIsRealName, llXy;
    private View mMenuView;
    private Context context;
    onChooseListener listener;
    private List<String> list;
    private int choosePosition;
    public static int NOCHOOSE = 100;
    private HomeTjBean homeTjBean;


    public interface onChooseListener {
        void onChoose(int position);
    }

    @SuppressLint("InflateParams")
    public HomePopupWindow(final Context context, HomeTjBean homeTjBean, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.homeTjBean = homeTjBean;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_pop_home, null);
        llNl = ((LinearLayout) mMenuView.findViewById(R.id.ll_nl));
        llJzz = ((LinearLayout) mMenuView.findViewById(R.id.ll_jzz));
        llSg = ((LinearLayout) mMenuView.findViewById(R.id.ll_sg));
        llXl = ((LinearLayout) mMenuView.findViewById(R.id.ll_xl));
        llYsr = ((LinearLayout) mMenuView.findViewById(R.id.ll_ysr));

        llMz = ((LinearLayout) mMenuView.findViewById(R.id.ll_mz));
        llIsCar = ((LinearLayout) mMenuView.findViewById(R.id.ll_iscar));
        llJg = ((LinearLayout) mMenuView.findViewById(R.id.ll_jg));
        llLastTime = ((LinearLayout) mMenuView.findViewById(R.id.ll_lastTime));
        llIsHouse = ((LinearLayout) mMenuView.findViewById(R.id.ll_isHouse));
        llIsRealName = ((LinearLayout) mMenuView.findViewById(R.id.ll_isRealName));
        llXy = ((LinearLayout) mMenuView.findViewById(R.id.ll_xy));

        tvMz = ((TextView) mMenuView.findViewById(R.id.tv_mz));
        tvIsCar = ((TextView) mMenuView.findViewById(R.id.tv_iscar));
        tvJg = ((TextView) mMenuView.findViewById(R.id.tv_jg));
        tvLastTime = ((TextView) mMenuView.findViewById(R.id.tv_lastTime));

        tvSure = ((TextView) mMenuView.findViewById(R.id.tv_sure));
        tvClear = ((TextView) mMenuView.findViewById(R.id.tv_clear));

        tvAge = ((TextView) mMenuView.findViewById(R.id.tv_age));
        tvJzz = ((TextView) mMenuView.findViewById(R.id.tv_jzz));
        tvSg = ((TextView) mMenuView.findViewById(R.id.tv_sg));
        tvXl = ((TextView) mMenuView.findViewById(R.id.tv_xl));
        tvYsr = ((TextView) mMenuView.findViewById(R.id.tv_ysr));
        tvIsHouse = ((TextView) mMenuView.findViewById(R.id.tv_isHouse));
        tvIsRealName = ((TextView) mMenuView.findViewById(R.id.tv_isRealName));
        tvXy = ((TextView) mMenuView.findViewById(R.id.tv_xy));

        tvClear.setOnClickListener(onClickListener);
        tvSure.setOnClickListener(onClickListener);
        llNl.setOnClickListener(onClickListener);
        llJzz.setOnClickListener(onClickListener);
        llSg.setOnClickListener(onClickListener);
        llXl.setOnClickListener(onClickListener);
        llYsr.setOnClickListener(onClickListener);
        llMz.setOnClickListener(onClickListener);
        llIsCar.setOnClickListener(onClickListener);
        llJg.setOnClickListener(onClickListener);
        llLastTime.setOnClickListener(onClickListener);
        llIsHouse.setOnClickListener(onClickListener);
        llIsRealName.setOnClickListener(onClickListener);
        llXy.setOnClickListener(onClickListener);

        if (!ListUtil.isEmpty(homeTjBean.getData())) {
            for (int i = 0; i < homeTjBean.getData().size(); i++) {
                if (null != homeTjBean.getData().get(i).getSwitchX() && homeTjBean.getData().get(i).getSwitchX().equals("0")) {
                    switch (homeTjBean.getData().get(i).getName()) {
                        case "city": //居住地
                            llJzz.setVisibility(View.GONE);
                            break;
                        case "height"://身高
                            llSg.setVisibility(View.GONE);
                            break;
                        case "income"://月收入
                            llYsr.setVisibility(View.GONE);
                            break;
                        case "edu"://学历
                            llXl.setVisibility(View.GONE);
                            break;
                        case "nation"://民族
                            llMz.setVisibility(View.GONE);
                            break;
                        case "car"://是否购车
                            llIsCar.setVisibility(View.GONE);
                            break;
                        case "na_place"://籍贯
                            llJg.setVisibility(View.GONE);
                            break;
                        case "logintime"://最近活跃
                            llLastTime.setVisibility(View.GONE);
                            break;
                        case "score"://信用分
                            llXy.setVisibility(View.GONE);
                            break;
                        case "is_relaname"://是否实名
                            llIsRealName.setVisibility(View.GONE);
                            break;
                        case "is_buyhome"://是否有房
                            llIsHouse.setVisibility(View.GONE);
                            break;
                        case "age"://年龄
                            llNl.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }


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


    public void setOnChooseListener(onChooseListener chooseLitener) {
        this.listener = chooseLitener;
    }

    public void setAge(String age) {
        tvAge.setText(age);
    }

    public void setSg(String sg) {
        tvSg.setText(sg);
    }

    public void setJzz(String jzz) {
        tvJzz.setText(jzz);
    }

    public void setXl(String xl) {
        tvXl.setText(xl);
    }

    public void setYsr(String ysr) {
        tvYsr.setText(ysr);
    }

    public void setMz(String mz) {
        tvMz.setText(mz);
    }

    public void setIsCar(String isCar) {
        tvIsCar.setText(isCar);
    }

    public void setJg(String jg) {
        tvJg.setText(jg);
    }

    public void setlastTime(String laseTime) {
        tvLastTime.setText(laseTime);
    }

    public void setIsHouse(String isHouse) {
        tvIsHouse.setText(isHouse);
    }

    public void setIsRealName(String isRealName) {
        tvIsRealName.setText(isRealName);
    }

    public void setXy(String xy) {
        tvXy.setText(xy);
    }


}

