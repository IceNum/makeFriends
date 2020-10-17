package com.people.loveme.view.dragcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.people.loveme.R;
import com.people.loveme.utils.PicassoUtil;

import java.util.List;

/**
 * Created by kxn on 2018/11/10 0010.
 */

public class CardsAdapter extends BaseAdapter {
    private Context context;
    private List<String> cardList;

    public CardsAdapter(Context context, List<String> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View cardView = LayoutInflater.from(context).inflate(
                R.layout.layout_card, parent, false);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), cardList.get(position));
        MyImageView iv_image = (MyImageView) cardView
                .findViewById(R.id.iv_image);

        PicassoUtil.setImag(context,cardList.get(position),iv_image);
//        iv_image.setImageDrawable(new HalfRoundedDrawable(bitmap, 15, 0));
        return cardView;
    }

}
