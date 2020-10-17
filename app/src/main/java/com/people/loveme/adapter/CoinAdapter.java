package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.CoinBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class CoinAdapter extends BaseAdapter {
    Context context;
    List<CoinBean.DataBean> list;
    private int selectPosition = -1;

    public CoinAdapter(Context context, List<CoinBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setSelectPosition(int position){
        this.selectPosition = position;
        notifyDataSetChanged();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coin, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        holder.tvTitle.setText(list.get(i).getNum()+"虚拟币");

        if (i == selectPosition){
            holder.tvTitle.setBackgroundResource(R.drawable.bg_coin_select);
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.txt_main_deep));
        }else{
            holder.tvTitle.setBackgroundResource(R.drawable.bg_coin_normal);
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.txt_lv4));
        }


        return view;
    }



    class ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
