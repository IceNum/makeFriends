package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.NearUserBean;
import com.people.loveme.utils.FormatUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class NearAdapter extends RecyclerView.Adapter<NearAdapter.ViewHolder> {

    private Context context;
    private List<NearUserBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public NearAdapter(Context context, List<NearUserBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_near, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PicassoUtil.setHeadImag(context, list.get(position).getHeadimage(), holder.ivHead);
        if (null != list.get(position).getNickname())
            holder.tvName.setText(list.get(position).getNickname());
        if (null != list.get(position).getScore() && !list.get(position).getScore().equals("null"))
            holder.tvJf.setText(list.get(position).getScore() + "分");
        else
            holder.tvJf.setText(0 + "分");
        if (!StringUtil.isEmpty(list.get(position).getAge()))
            holder.tvAge.setText(list.get(position).getAge() + "岁");
        if (null != list.get(position).getJb() && !StringUtil.isEmpty(list.get(position).getJb().getHeight()))
            holder.tvShengao.setText(list.get(position).getJb().getHeight() + "cm");
        try {
            if (!StringUtil.isEmpty(list.get(position).getDistance())) {
                if ((int) (Double.parseDouble(list.get(position).getDistance()) * 1000) < 10) {
                    holder.tvJuli.setText("附近");
                } else
                    holder.tvJuli.setText(FormatUtil.formatDistance((int) (Double.parseDouble(list.get(position).getDistance()) * 1000)));
            } else
                holder.tvJuli.setText("附近");
        } catch (Exception e) {
            holder.tvJuli.setText("附近");
        }

//        if (!StringUtil.isEmpty(list.get(position).getOnlineStatus()))
//            holder.tvOnline.setText(list.get(position).getOnlineStatus());
        if (!StringUtil.isEmpty(list.get(position).getDubai()))
            holder.tvSignature.setText(list.get(position).getDubai());

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });
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
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_shengao)
        TextView tvShengao;
        @BindView(R.id.tv_juli)
        TextView tvJuli;
        @BindView(R.id.tv_online)
        TextView tvOnline;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_signature)
        TextView tvSignature;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
