package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.people.loveme.R;
import com.people.loveme.bean.MyDtBean;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/15 0015.
 */

public class OtherHomeDongtaiAdapter extends BaseAdapter {
    private Context context;
    private List<MyDtBean.DataBean> list;

    public OtherHomeDongtaiAdapter(Context context, List<MyDtBean.DataBean> list) {
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
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = ((View) LayoutInflater.from(context).inflate(R.layout.item_dongtai_other, null));
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());



        if (!StringUtil.isEmpty(list.get(i).getContent()))
            holder.tvContent.setText(list.get(i).getContent());
        if (!StringUtil.isEmpty(list.get(i).getTime()))
            holder.tvTime.setText( TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(i).getTime()) * 1000)));
        if (!StringUtil.isEmpty(list.get(i).getZan()))
            holder.tvZanNum.setText("ئېسىل  " + list.get(i).getZan());

        if (!StringUtil.isEmpty(list.get(i).getAddress())) {
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvAddress.setText(list.get(i).getAddress());
        } else {
            holder.tvAddress.setVisibility(View.GONE);
            holder.tvAddress.setText("");
        }

        if (!StringUtil.isEmpty(list.get(i).getImages())) {
            String[] images = list.get(i).getImages().split(",");
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int j = 0; j < images.length; j++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(images[j]);
                info.setBigImageUrl(images[j]);
                imageInfo.add(info);
            }
            holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
        }



        return view;
    }



    class ViewHolder {
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_zanNum)
        TextView tvZanNum;
        @BindView(R.id.tv_lyl)
        TextView tvLyl;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
