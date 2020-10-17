package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.VipListBean;
import com.people.loveme.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/19 0019.
 */

public class VipCardAdapter extends BaseAdapter {
    private Context context;
    private List<VipListBean.DataBean> list;

    public VipCardAdapter(Context context, List<VipListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_vip_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        if (!StringUtil.isEmpty(list.get(i).getViptype()))
            holder.tvName.setText(list.get(i).getViptype());
        if (!StringUtil.isEmpty(list.get(i).get_$0_price()))
            holder.tvYj.setText("原价：¥" + list.get(i).get_$0_price());
        if (!StringUtil.isEmpty(list.get(i).getN_price()))
            holder.tvXj.setText(list.get(i).getN_price());

        if (!StringUtil.isEmpty(list.get(i).getId())){
            switch (list.get(i).getId()) {
                case "1":
                    holder.ivDay.setImageResource(R.mipmap.vip01);
                    break;
                case "2":
                    holder.ivDay.setImageResource(R.mipmap.vip30);
                    break;
                case "3":
                    holder.ivDay.setImageResource(R.mipmap.vip90);
                    break;
                case "4":
                    holder.ivDay.setImageResource(R.mipmap.vip365);
                    break;
            }
        }
        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_yj)
        TextView tvYj;
        @BindView(R.id.tv_now)
        TextView tvNow;
        @BindView(R.id.tv_xj)
        TextView tvXj;
        @BindView(R.id.iv_day)
        ImageView ivDay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
