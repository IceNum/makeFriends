package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.WithdrawBean;
import com.people.loveme.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2019/5/13 0013.
 */
public class WithdrawListAdapter extends RecyclerView.Adapter<WithdrawListAdapter.ViewHolder> {
    private Context context;
    private List<WithdrawBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public WithdrawListAdapter(Context context, List<WithdrawBean.DataBean> list) {
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
        if (null != list.get(position).getAccount())
            holder.tvTitle.setText("提现到账户：" + list.get(position).getAccount());
        if (null != list.get(position).getMoney())
            holder.tvPrice.setText("-" + list.get(position).getMoney());

        holder.tvPrice.setTextColor(context.getResources().getColor(R.color.price));

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
