package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/8/18 0018.
 */

public class ShopCollectedAdapter extends RecyclerView.Adapter<ShopCollectedAdapter.ViewHolder> {

    private Context context;
    private List list;
    private OnItemClickListener onItemClickListener;

    public ShopCollectedAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_collected, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_shopName)
        TextView tvShopName;
        @BindView(R.id.tv_shopDes)
        TextView tvShopDes;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


