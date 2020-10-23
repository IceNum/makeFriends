package com.people.loveme.adapter.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by kxn on 2018/9/10 0010.
 */

public class DongTaiAdapter extends RecyclerView.Adapter<DongTaiAdapter.ViewHolder> {


    private Context context;
    private List<MyDtBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public DongTaiAdapter(Context context, List<MyDtBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dt_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (!StringUtil.isEmpty(list.get(position).getContent()))
            holder.tvContent.setText(list.get(position).getContent());
        if (!StringUtil.isEmpty(list.get(position).getTime()))
            holder.tvTime.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(position).getTime()) * 1000)));
        if (!StringUtil.isEmpty(list.get(position).getZan()))
            holder.tvZanNum.setText("ئېسىل  " + list.get(position).getZan());


        if (!StringUtil.isEmpty(list.get(position).getImages())) {
            String[] images = list.get(position).getImages().split(",");
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(images[i]);
                info.setBigImageUrl(images[i]);
                imageInfo.add(info);
            }
            holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
        }

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });
        if (!StringUtil.isEmpty(list.get(position).getAddress())) {
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvAddress.setText(list.get(position).getAddress());
        } else {
            holder.tvAddress.setVisibility(View.GONE);
            holder.tvAddress.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_zanNum)
        TextView tvZanNum;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

