package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.LookedBean;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;
import com.people.loveme.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class SkgwAdapter extends RecyclerView.Adapter<SkgwAdapter.ViewHolder> {

    private Context context;
    private List<LookedBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public SkgwAdapter(Context context, List<LookedBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_skgw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });
        if (null != list.get(position).getViewer()){
            if (!StringUtil.isEmpty(list.get(position).getViewer().getAge()))
                holder.tvAge.setText(list.get(position).getViewer().getAge() + "岁");
        }

        else
            holder.tvAge.setText("未完善");
        if (null != list.get(position).getJibeninfo() && !StringUtil.isEmpty(list.get(position).getJibeninfo().getIncome())) {
            switch (list.get(position).getJibeninfo().getIncome()) {
                case "0":
                    holder.tvIncome.setText("2k");
                    break;
                case "1":
                    holder.tvIncome.setText("2k-4k");
                    break;
                case "2":
                    holder.tvIncome.setText("4k-6k");
                    break;
                case "3":
                    holder.tvIncome.setText("6k-10k");
                    break;
                case "4":
                    holder.tvIncome.setText("10k-15k");
                    break;
                case "5":
                    holder.tvIncome.setText("15k-20k");
                    break;
                case "6":
                    holder.tvIncome.setText("20k-50k");
                    break;
                case "7":
                    holder.tvIncome.setText("50k");
                    break;
                default:
                    holder.tvIncome.setText(list.get(position).getJibeninfo().getIncome());
                    break;
            }
        } else
            holder.tvIncome.setText("未完善");
        if (null != list.get(position).getViewer() && !StringUtil.isEmpty(list.get(position).getViewer().getScore()))
            holder.tvJf.setText(list.get(position).getViewer().getScore() + "分");
        if (null != list.get(position).getViewer() && !StringUtil.isEmpty(list.get(position).getViewer().getNickname()))
            holder.tvName.setText(list.get(position).getViewer().getNickname());
        else
            holder.tvName.setText("未完善");
        if (null != list.get(position).getViewer() && !StringUtil.isEmpty(list.get(position).getViewer().getDubai()))
            holder.tvSignature.setText(list.get(position).getViewer().getDubai());
        else
            holder.tvSignature.setText("未完善");
        if (null != list.get(position).getViewer() && !StringUtil.isEmpty(list.get(position).getViewer().getHeadimage()))
            PicassoUtil.setHeadImag(context, list.get(position).getViewer().getHeadimage(), holder.ivHead);
        if (null != list.get(position).getJibeninfo() && !StringUtil.isEmpty(list.get(position).getJibeninfo().getHeight()))
            holder.tvShengao.setText(list.get(position).getJibeninfo().getHeight() + "cm");
        else
            holder.tvShengao.setText("未完善");
        if (null != list.get(position).getJibeninfo() && !StringUtil.isEmpty(list.get(position).getJibeninfo().getEdu()))
            holder.tvXueli.setText(list.get(position).getJibeninfo().getEdu());
        else
            holder.tvXueli.setText("未完善");
        if (null != list.get(position).getTime())
            holder.tvTime.setText(TimeUtil.recentTime(System.currentTimeMillis() / 1000 - Long.parseLong(list.get(position).getTime())));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jf)
        TextView tvJf;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_shengao)
        TextView tvShengao;
        @BindView(R.id.tv_xueli)
        TextView tvXueli;
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.tv_signature)
        TextView tvSignature;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

