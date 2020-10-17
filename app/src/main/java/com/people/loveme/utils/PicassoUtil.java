package com.people.loveme.utils;

import android.content.Context;
import android.widget.ImageView;

import com.people.loveme.R;
import com.squareup.picasso.Picasso;

/**
 * Created by kxn on 2018/7/17 0017.
 */
public class PicassoUtil {
    public static void setImag(Context context, String url, ImageView iv) {
        if (!StringUtil.isEmpty(url))
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_defaut).error(R.mipmap.ic_defaut).into(iv);
        else
            Picasso.with(context).load(R.mipmap.ic_defaut).into(iv);
    }
    public static void setImag(Context context, int res, ImageView iv) {
            Picasso.with(context).load(res).placeholder(R.mipmap.ic_defaut).error(R.mipmap.ic_defaut).into(iv);
    }

    public static void setHeadImag(Context context, String url, ImageView iv) {
        if (!StringUtil.isEmpty(url))
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_head).error(R.mipmap.ic_head).into(iv);
        else
            Picasso.with(context).load(R.mipmap.ic_head).into(iv);
    }

    public static void setHeadImag(Context context, int res, ImageView iv) {
        Picasso.with(context).load(res).placeholder(R.mipmap.ic_head).error(R.mipmap.ic_head).into(iv);
    }
}
