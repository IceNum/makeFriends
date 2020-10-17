package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.people.loveme.R;
import com.people.loveme.utils.PicassoUtil;

import java.util.List;

/**
 * Created by kxn on 2018/11/10 0010.
 */

public class GuideDialog extends Dialog {


    private Context context;
    private ViewPager mViewPager;
    private List<Integer> imagPath;

//    public GuideDialog(Context context, List<String> imags) {
//        super(context, R.style.MyDialogStyle);
//        this.context = context;
//        this.imagPaths = imags;
//    }

    public GuideDialog(Context context, List<Integer> imags) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.imagPath = imags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_guide, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        mViewPager = ((ViewPager) view.findViewById(R.id.viewpager));
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

//        //设置Page间间距
//        mViewPager.setPageMargin(30);
//        //设置缓存的页面数量
//        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter( new MyPagerAdapter(context,imagPath));
//        mViewPager.setPageTransformer(true, new
//                RotateYTransformer());



        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int)(d.widthPixels * 0.8); // 宽带度设置为屏幕的0.9
        lp.height = (int)(d.heightPixels * 0.7) ;
        dialogWindow.setAttributes(lp);
    }


    public class MyPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<String> imagPaths;
        private List<Integer> imagPath;

        public MyPagerAdapter(Context context ,List<Integer> list) {
            mContext = context;
            imagPath = list;
        }

        @Override
        public int getCount() {
            return imagPath.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_guide,null);
            ImageView iv = (ImageView) view.findViewById(R.id.image);
            PicassoUtil.setImag(context,imagPath.get(position),iv);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container,position,object); 这一句要删除，否则报错
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
