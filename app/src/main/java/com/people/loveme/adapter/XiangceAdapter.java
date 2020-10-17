package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.people.loveme.R;
import com.people.loveme.utils.DisplayUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/10/31 0031.
 */

public class XiangceAdapter extends BaseAdapter {
    private Context context;
    List<String> list;

    public XiangceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            holder = new ViewHolder(view);
            ViewGroup.LayoutParams layoutParams = holder.iv.getLayoutParams();
            layoutParams.width = (ScreenUtil.getScreenWidth(context) - DisplayUtil.dip2px(context, 50)) / 4;
            layoutParams.height = layoutParams.width;
            holder.iv.setLayoutParams(layoutParams);
            holder.ivChcek.setVisibility(View.GONE);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());


        PicassoUtil.setImag(context, list.get(i), holder.iv);

        return view;
    }


    class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.iv_chcek)
        ImageView ivChcek;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

