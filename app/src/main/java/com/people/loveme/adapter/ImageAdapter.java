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
 * Created by kxn on 2018/9/20 0020.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    List<String> list;
    private boolean isEdit = false;
    DeletePhoto deletePhoto;

    public ImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setDeletePhotoListener(DeletePhoto deletePhotoListener) {
        this.deletePhoto = deletePhotoListener;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size() + 1;
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
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        if (isEdit && i != 0)
            holder.ivChcek.setVisibility(View.VISIBLE);
        else
            holder.ivChcek.setVisibility(View.GONE);

        holder.ivChcek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != deletePhoto)
                    deletePhoto.deletePhoto(i);
            }
        });


        if (i == 0)
            PicassoUtil.setImag(context, R.mipmap.ic_add_imag, holder.iv);
        else
            PicassoUtil.setImag(context, list.get(i - 1), holder.iv);

        return view;
    }

    public interface DeletePhoto {
        void deletePhoto(int positon);
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
