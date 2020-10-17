package com.people.loveme.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.HmdListBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 */

public class HmdAdapter extends RecyclerView.Adapter<HmdAdapter.ViewHolder> {


    private Context context;
    private List<HmdListBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public HmdAdapter(Context context, List<HmdListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hmd, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!StringUtil.isEmpty(list.get(position).getBe_black_user().getNickname()))
            holder.tvName.setText(list.get(position).getBe_black_user().getNickname());
        PicassoUtil.setHeadImag(context, list.get(position).getBe_black_user().getHeadimage(), holder.ivHead);
        holder.tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delBlackList(position);
            }
        });
    }

    /**
     * 移出黑名单列表
     */
    private void delBlackList(final int position) {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context,AppConsts.UID,""));
        params.put("be_black_uid", list.get(position).getBe_black_uid());

        OkHttpHelper.getInstance().post(context, Url.delBlacklist, params, new SpotsCallBack<BaseBean>(context) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("移出成功！");
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

        RongIM.getInstance().removeFromBlacklist(list.get(position).getBe_black_uid(), new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.show("移出黑名单成功！");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
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
        @BindView(R.id.tv_out)
        TextView tvOut;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
