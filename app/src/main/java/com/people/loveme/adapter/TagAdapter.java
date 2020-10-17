package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.TagsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/7 0007.
 */

public class TagAdapter extends BaseAdapter {
    private Context context;
    private List<TagsBean.DataBean> tags;
    private List<String> selects;

    public TagAdapter(Context context, List<TagsBean.DataBean> tags, List<String> select) {
        this.context = context;
        this.tags = tags;
        this.selects = select;
    }


    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int i) {
        return tags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tag, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        if (selects.contains(tags.get(i).getId() ))
            holder.ivGou.setVisibility(View.VISIBLE);
        else
            holder.ivGou.setVisibility(View.INVISIBLE);
        holder.tvTag.setText(tags.get(i).getName());

        return view;
    }

    class ViewHolder {
        @BindView(R.id.iv_gou)
        ImageView ivGou;
        @BindView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
