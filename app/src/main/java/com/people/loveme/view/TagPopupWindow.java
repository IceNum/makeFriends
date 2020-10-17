package com.people.loveme.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.people.loveme.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kxn on 2017/12/12.
 */
public class TagPopupWindow extends PopupWindow {
    private View mMenuView;
    private Context context;
    onChooseListener listener;
    private List<String> list;
    private int choosePosition;
    public static int NOCHOOSE = 100;
    TagFlowLayout flTags;


    public interface onChooseListener {
        void onChoose(int position);
    }

    @SuppressLint("InflateParams")
    public TagPopupWindow(final Context context) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_selecttag, null);
        flTags = ((TagFlowLayout) mMenuView.findViewById(R.id.fl_tags));


        list = new ArrayList<>();

        // 设置CartoonTagPopupWindow的View
        this.setContentView(mMenuView);
        // 设置CartoonTagPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置CartoonTagPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
                int height = mMenuView.findViewById(R.id.fl_tags).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                        listener.onChoose(NOCHOOSE);
                    }
                }
                return true;
            }
        });
    }

    private void setTag() {
        final TagAdapter<String> tagAdapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv_topic,
                        flTags, false);
                if (position == choosePosition)
                    tv.setSelected(true);
                else
                    tv.setSelected(false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
            }
        };

        flTags.setAdapter(tagAdapter);
        tagAdapter.setSelectedList(choosePosition);
        flTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                listener.onChoose(position);
                dismiss();
                return true;
            }
        });
    }

    public void setData(final List<String> list, final int choosePosition) {
        this.list.clear();
        this.list.addAll(list);
        this.choosePosition = choosePosition;

        setTag();
    }

    public void setOnChooseListener(onChooseListener chooseLitener) {
        this.listener = chooseLitener;
    }


}
