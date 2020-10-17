package com.people.loveme.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.PhotoListBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.other.OtherXiangceFra;
import com.people.loveme.utils.PicassoUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/10/30 0030.
 */

public class UserXcAdapter extends BaseAdapter {
    private Context context;
    private List<PhotoListBean.DataBean> list;
    private String uid;

    public UserXcAdapter(Context context, List<PhotoListBean.DataBean> list, String uid) {
        this.context = context;
        this.list = list;
        this.uid = uid;
    }

    @Override
    public int getCount() {
        return list.size() >= 4 ? 4 : list.size() + 1;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_xc_user_home, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        holder.tvMore.setText("查看更多(" + list.size() + ")");

        if (i == 3)
            holder.tvMore.setVisibility(View.VISIBLE);
        else
            holder.tvMore.setVisibility(View.GONE);

        if (i == list.size()  && list.size() < 4) {
            holder.tvMore.setText("没有更多");
            holder.tvMore.setVisibility(View.VISIBLE);
            holder.iv.setVisibility(View.GONE);
        }else
            holder.iv.setVisibility(View.VISIBLE);

        if (i < list.size())
            PicassoUtil.setImag(context, list.get(i).getImage(), holder.iv);

        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                ActivitySwitcher.startFragment(context, OtherXiangceFra.class, bundle);
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_more)
        TextView tvMore;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
