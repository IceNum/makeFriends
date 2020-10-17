package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.MyLikeBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class MyLikeAdapter extends RecyclerView.Adapter<MyLikeAdapter.ViewHolder> {



    private Context context;
    private List<MyLikeBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public MyLikeAdapter(Context context, List<MyLikeBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_like, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (null != list.get(position).getBelikeer()){
            if (!StringUtil.isEmpty(list.get(position).getBelikeer().getAge()))
                holder.tvAge.setText(list.get(position).getBelikeer().getAge() + "岁");
            else
                holder.tvAge.setText("未完善");

            if (!StringUtil.isEmpty(list.get(position).getBelikeer().getScore()))
                holder.tvJf.setText(list.get(position).getBelikeer().getScore() + "分");
            else
                holder.tvJf.setText("0分");
            if (!StringUtil.isEmpty(list.get(position).getBelikeer().getNickname()))
                holder.tvName.setText(list.get(position).getBelikeer().getNickname());
            else
                holder.tvName.setText("未完善");

            if (!StringUtil.isEmpty(list.get(position).getBelikeer().getDubai()))
                holder.tvSignature.setText(list.get(position).getBelikeer().getDubai());
            if (!StringUtil.isEmpty(list.get(position).getBelikeer().getHeadimage()))
                PicassoUtil.setHeadImag(context, list.get(position).getBelikeer().getHeadimage(), holder.ivHead);
            if (null != list.get(position).getBe_like_jb() && !StringUtil.isEmpty(list.get(position).getBe_like_jb().getHeight()))
                holder.tvShengao.setText(list.get(position).getBe_like_jb().getHeight() + "cm");
            else
                holder.tvShengao.setText("未完善");
        }

        if (null != list.get(position).getBe_like_jb() && !StringUtil.isEmpty(list.get(position).getBe_like_jb().getIncome())) {
            switch (list.get(position).getBe_like_jb().getIncome()) {
                case "0":
                    holder.tvIncome.setText("2k");
                    break;
                case "1":
                    holder.tvIncome.setText("2k-4k");
                    break;
                case "2":
                    holder.tvIncome.setText("4k-6k");
                    break;
                case "3":
                    holder.tvIncome.setText("6k-10k");
                    break;
                case "4":
                    holder.tvIncome.setText("10k-15k");
                    break;
                case "5":
                    holder.tvIncome.setText("15k-20k");
                    break;
                case "6":
                    holder.tvIncome.setText("20k-50k");
                    break;
                case "7":
                    holder.tvIncome.setText("50k");
                    break;
                default:
                    holder.tvIncome.setText(list.get(position).getBe_like_jb().getIncome());
                    break;
            }
        } else
            holder.tvIncome.setText("未完善");

        if (null != list.get(position).getBe_like_jb() && !StringUtil.isEmpty(list.get(position).getBe_like_jb().getEdu()))
            holder.tvXueli.setText(list.get(position).getBe_like_jb().getEdu());
        else
            holder.tvXueli.setText("未完善");

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delLike(position);
            }
        });

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });
    }

    /**
     * 取消喜欢
     */
    private void delLike(final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("like_uid", list.get(position).getLike_uid());
        params.put("be_like_uid", list.get(position).getBe_like_uid() + "");
        OkHttpHelper.getInstance().post(context, Url.delLike, params, new SpotsCallBack<BaseBean>(context) {
            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                list.remove(position);
                notifyDataSetChanged();
                ToastUtil.show("取消成功！");
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jf)
        TextView tvJf;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_shengao)
        TextView tvShengao;
        @BindView(R.id.tv_xueli)
        TextView tvXueli;
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.tv_signature)
        TextView tvSignature;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}