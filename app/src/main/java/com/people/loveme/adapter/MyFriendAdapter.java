package com.people.loveme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.MyInviteBean;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.TimeUtil;
import com.people.loveme.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class MyFriendAdapter extends BaseAdapter {
    private Context context;
    private List<MyInviteBean.DataBean> list;

    public MyFriendAdapter(Context context, List<MyInviteBean.DataBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_haoyou, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = ((ViewHolder) view.getTag());

        holder.tvNum.setText((i + 1) + ". ");

        if (!StringUtil.isEmpty(list.get(i).getNickname()))
            holder.tvName.setText(list.get(i).getNickname());
        if (!StringUtil.isEmpty(list.get(i).getRegtime()))
            holder.tvTime.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(i).getRegtime()) * 1000)));

        double money = 0;
        for (int j = 0; j < list.get(i).getOrder().size(); j++) {
            if (null != list.get(i).getOrder().get(j).getMoney()) {
                try {
                    money += Double.parseDouble(list.get(i).getOrder().get(j).getMoney());
                } catch (Exception e) {

                }
            }
        }
        if (money > 0)
            holder.tvMoney.setText(money + "");
        if (!StringUtil.isEmpty(list.get(i).getRegtime()))
            holder.tvTime.setText(TimeUtil.friendlyPassTime(TimeUtil.String2Data(Long.parseLong(list.get(i).getRegtime()) * 1000)));
        PicassoUtil.setHeadImag(context, list.get(i).getHeadimage(), holder.ivHead);

        return view;
    }


    class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
