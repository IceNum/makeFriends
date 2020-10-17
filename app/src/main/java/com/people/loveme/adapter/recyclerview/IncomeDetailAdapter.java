package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.IncomeDetailBean;
import com.people.loveme.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class IncomeDetailAdapter extends RecyclerView.Adapter<IncomeDetailAdapter.ViewHolder> {

    private Context context;
    private List<IncomeDetailBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    private String userId;

    public IncomeDetailAdapter(Context context, List<IncomeDetailBean.DataBean> list, String userId) {
        this.context = context;
        this.list = list;
        this.userId = userId;
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
            if (userId.equals(list.get(position).getGiveuid().getId())) { //送出
                holder.tvPrice.setText("-" + list.get(position).getGift().getMoney());
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.price));
                if (null != list.get(position).getGift().getName()) {
                    if (null != list.get(position).getGetuid() && null != list.get(position).getGift())
                        holder.tvTitle.setText("送给" + list.get(position).getGetuid().getNickname() + list.get(position).getGift().getName());
                }
            } else {//收到
                holder.tvPrice.setText("+" + list.get(position).getGift().getMoney());
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.txt_main));
                if (null != list.get(position).getGift().getName())
                    holder.tvTitle.setText("收到" + list.get(position).getGiveuid().getNickname() + "送的" + list.get(position).getGift().getName());
            }
        } else
            holder.tvPrice.setText("");

        if (null != list.get(position).getTime())
            holder.tvTime.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(position).getTime()) * 1000)));

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