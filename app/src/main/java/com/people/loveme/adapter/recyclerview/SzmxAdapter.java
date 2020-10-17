package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.MyOrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class SzmxAdapter extends RecyclerView.Adapter<SzmxAdapter.ViewHolder> {


    private Context context;
    private List<MyOrderBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public SzmxAdapter(Context context, List<MyOrderBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_szmx, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (null != list.get(position).getType()) {
            switch (list.get(position).getType()) {
                case "1"://收入
                    holder.tvPrice.setText("+" + list.get(position).getMoney());
                    holder.tvPrice.setTextColor(context.getResources().getColor(R.color.txt_main));
                    break;
                case "0": //支出
                    holder.tvPrice.setText("-" + list.get(position).getMoney());
                    holder.tvPrice.setTextColor(context.getResources().getColor(R.color.price));
                    break;
            }
        } else
            holder.tvPrice.setText("");


        if (null != list.get(position).getDetail())
            holder.tvTitle.setText(list.get(position).getDetail());
        if (null != list.get(position).getTime_text())
            holder.tvTime.setText(list.get(position).getTime_text());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}