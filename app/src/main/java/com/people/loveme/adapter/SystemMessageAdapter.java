package com.people.loveme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.adapter.recyclerview.AgreementsAdapter;
import com.people.loveme.bean.MessageBean;
import com.people.loveme.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2019/1/14 0014.
 */

public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder> {


    private Context context;
    private List<MessageBean.DataBean> list;
    private AgreementsAdapter.OnItemClickListener onItemClickListener;

    public SystemMessageAdapter(Context context, List<MessageBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(AgreementsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_system, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!StringUtil.isEmpty(list.get(position).getTitle()))
            holder.tvTitle.setText(list.get(position).getTitle());
        else
            holder.tvTitle.setText(context.getString(R.string.wo_xitongxiaoxi));
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        if (list.get(position).getIs_view() == 0) {
            holder.noReadView.setVisibility(View.VISIBLE);
        }else
            holder.noReadView.setVisibility(View.GONE);
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
        @BindView(R.id.noReadView)
        View noReadView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

