package com.people.loveme.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.adapter.GiftListAdapter;
import com.people.loveme.bean.GiftBean;
import com.people.loveme.utils.PicassoUtil;

import java.util.List;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class GiftPop extends PopupWindow
{
    private Button sendBtn;
    private GridView gvGift;
    private ImageView ivSelect;
    private TextView tvCoinNum;
    private View mMenuView;
    GiftListAdapter adapter;

    @SuppressLint("InflateParams")
    public GiftPop(final Context context, final List<GiftBean.DataBean> listList, final OnSelectListener onSelectListener,View.OnClickListener onClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_pop_gift, null);
        sendBtn = (Button) mMenuView.findViewById(R.id.btnSend);
        gvGift = (GridView) mMenuView.findViewById(R.id.gvGift);
        tvCoinNum = (TextView) mMenuView.findViewById(R.id.tvCoinNum);
        ivSelect = (ImageView) mMenuView.findViewById(R.id.ivSelect);
        adapter = new GiftListAdapter(context,listList);
        gvGift.setAdapter(adapter);

        gvGift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onSelectListener.onSelect(i);
                PicassoUtil.setImag(context,listList.get(i).getImg_src(),ivSelect);
                tvCoinNum.setText(listList.get(i).getMoney());
            }
        });

        sendBtn.setOnClickListener(onClickListener);

        // 设置CartoonTagPopupWindow的View
        this.setContentView(mMenuView);
        // 设置CartoonTagPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置CartoonTagPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置CartoonTagPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置CartoonTagPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        // 设置CartoonTagPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

//        // 设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
////		this.setAnimationStyle(R.style.PopupWindowAnimation);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x80000000);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public interface OnSelectListener{
        void onSelect(int position);
    }

}