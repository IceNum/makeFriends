package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.CreditScoreBean;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class XyjlAdapter extends RecyclerView.Adapter<XyjlAdapter.ViewHolder> {


    private Context context;
    private List<CreditScoreBean.DataBean.ListBean> list;
    private OnItemClickListener onItemClickListener;

    public XyjlAdapter(Context context, List<CreditScoreBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xyjl, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!StringUtil.isEmpty(list.get(position).getAdd_score())) {
            if (list.get(position).getAdd_score().startsWith("-")){
                holder.tvScore.setTextColor(context.getResources().getColor(R.color.price));
                holder.tvScore.setText(list.get(position).getAdd_score());
            }else{
                holder.tvScore.setText("+" + list.get(position).getAdd_score());
                holder.tvScore.setTextColor(context.getResources().getColor(R.color.txt_main));
            }

//            if (list.get(position).getMoeny().contains("+"))
//                holder.tvScore.setTextColor(context.getResources().getColor(R.color.txt_main));
//            else
//                holder.tvScore.setTextColor(context.getResources().getColor(R.color.price));
        }
        if (null != list.get(position).getTime())
            holder.tvTime.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(position).getTime() + "000"))));
        if (!StringUtil.isEmpty(list.get(position).getDetail()))
            holder.tvTitle.setText(list.get(position).getDetail());
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
        @BindView(R.id.tv_score)
        TextView tvScore;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
