package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.GiftBean;
import com.people.loveme.utils.PicassoUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class GiftListAdapter extends BaseAdapter {
    Context context;
    List<GiftBean.DataBean> list;

    public GiftListAdapter(Context context, List<GiftBean.DataBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_gift, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        holder.tvGiftName.setText(list.get(i).getName());
        holder.tvGiftMoney.setText(list.get(i).getMoney()+"虚拟币");
        PicassoUtil.setImag(context, list.get(i).getImg_src(), holder.ivGift);

        return view;
    }




    class ViewHolder {
        @BindView(R.id.ivGift)
        ImageView ivGift;
        @BindView(R.id.tvGiftName)
        TextView tvGiftName;
        @BindView(R.id.tvGiftMoney)
        TextView tvGiftMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
