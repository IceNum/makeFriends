package com.people.loveme.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.people.loveme.R;
import com.lzy.ninegrid.NineGridView;
import com.people.loveme.utils.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by kxn on 2018/8/2 0002.
 */

public class PicassoImageLoader implements NineGridView.ImageLoader {

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        if (!StringUtil.isEmpty(url)) {
            Picasso.with(context).load(url)//
                    .placeholder(R.mipmap.ic_defaut)//
                    .error(R.mipmap.ic_defaut)//
                    .into(imageView);
        }
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
