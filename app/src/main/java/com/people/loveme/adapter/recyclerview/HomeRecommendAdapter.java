package com.people.loveme.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.HomeRecommendBean;
import com.people.loveme.bean.ZhaoHuBean;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.FhtjDialog;
import com.people.loveme.view.SmrzDialog;
import com.people.loveme.view.VipDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.ViewHolder> {


    private Context context;
    private List<HomeRecommendBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    private PeopleClickListener peopleClickListener;

    public HomeRecommendAdapter(Context context, List<HomeRecommendBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPeopleClickListener(PeopleClickListener peopleClickListener) {
        this.peopleClickListener = peopleClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!ListUtil.isEmpty(list.get(position).getDongtai()))
            PicassoUtil.setImag(context, list.get(position).getDongtai().get(0).getImages(), holder.ivPic);
        PicassoUtil.setHeadImag(context, list.get(position).getHeadimage(), holder.ivHead);
        if (null != list.get(position).getNickname())
            holder.tvName.setText(list.get(position).getNickname());
        else
            holder.tvName.setText("未命名");
        if (!StringUtil.isEmpty(list.get(position).getScore()))
            holder.tvJf.setText(new BigDecimal(list.get(position).getScore()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        else
            holder.tvJf.setText("0");

        if (null != list.get(position).getIsvip() && list.get(position).getIsvip().equals("1"))
            holder.ivVip.setVisibility(View.VISIBLE);
        else
            holder.ivVip.setVisibility(View.GONE);
        if (!StringUtil.isEmpty(list.get(position).getAge()) && !list.get(position).getAge().equals("null")) {
            holder.tvAge.setText(list.get(position).getAge() + "岁");
            holder.tvAge.setVisibility(View.VISIBLE);
        } else
            holder.tvAge.setVisibility(View.GONE);
        if (null != list.get(position).getJb() && !StringUtil.isEmpty(list.get(position).getJb().getHeight())) {
            holder.tvSg.setText(list.get(position).getJb().getHeight() + "cm");
            holder.tvSg.setVisibility(View.VISIBLE);
        } else
            holder.tvSg.setVisibility(View.GONE);
        if (null != list.get(position).getJb() && !StringUtil.isEmpty(list.get(position).getJb().getEdu())) {
            holder.tvXl.setVisibility(View.VISIBLE);
            holder.tvXl.setText(list.get(position).getJb().getEdu());
        } else
            holder.tvXl.setVisibility(View.GONE);

        if (null != list.get(position).getJb() && !StringUtil.isEmpty(list.get(position).getJb().getIncome())) {
            switch (list.get(position).getJb().getIncome()) {
                case "1":
                    holder.tvSr.setText("2千以下");
                    break;
                case "2":
                    holder.tvSr.setText("2-4千元");
                    break;
                case "4":
                    holder.tvSr.setText("4-6千元");
                    break;
                case "6":
                    holder.tvSr.setText("6-1万元");
                    break;
                case "10":
                    holder.tvSr.setText("1-1.5万元");
                    break;
                case "15":
                    holder.tvSr.setText("1.5-2万元");
                    break;
                case "20":
                    holder.tvSr.setText("2-5万元");
                    break;
                case "50":
                    holder.tvSr.setText("5万元以上");
                    break;
                default:
                    holder.tvSr.setText("2千以上");
                    break;
            }
        } else
            holder.tvSr.setVisibility(View.GONE);
        if (!ListUtil.isEmpty(list.get(position).getDongtai())) {
            if (null != list.get(position).getDongtai().get(0).getIszan() && list.get(position).getDongtai().get(0).getIszan().equals("1")) {
                holder.ivZan.setImageResource(R.mipmap.zans);
            } else
                holder.ivZan.setImageResource(R.mipmap.zan);
        }

        if (!ListUtil.isEmpty(list.get(position).getDongtai()))
            holder.tvZanNum.setText("赞 " + list.get(position).getDongtai().get(0).getZan());

        holder.llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ListUtil.isEmpty(list.get(position).getDongtai())) {
                    if (null != list.get(position).getDongtai().get(0).getIszan() && list.get(position).getDongtai().get(0).getIszan().equals("1"))
                        cancleZan(position);
                    else
                        zan(position);
                }
            }
        });


        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != peopleClickListener)
                    peopleClickListener.OnItemClick(position);
            }
        });

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        if (!ListUtil.isEmpty(list.get(position).getDongtai())) {
            if (!StringUtil.isEmpty(list.get(position).getDongtai().get(0).getImages())) {
                String[] images = list.get(position).getDongtai().get(0).getImages().split(",");
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                for (int i = 0; i < images.length; i++) {
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(images[i]);
                    info.setBigImageUrl(images[i]);
                    imageInfo.add(info);
                }
                holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
            }
        } else {
            if (!StringUtil.isEmpty(list.get(position).getHeadimage())) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(list.get(position).getHeadimage());
                info.setBigImageUrl(list.get(position).getHeadimage());
                imageInfo.add(info);
                holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
            }
        }

        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ListUtil.isEmpty(list.get(position).getDongtai())) {
                    if (!StringUtil.isEmpty(list.get(position).getDongtai().get(0).getImages())) {
                        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                        ImageInfo imageInfo1 = new ImageInfo();
                        imageInfo1.setBigImageUrl(list.get(position).getDongtai().get(0).getImages());
                        imageInfo1.setThumbnailUrl(list.get(position).getDongtai().get(0).getImages());
                        imageInfo.add(imageInfo1);
                        Intent intent = new Intent(context, ImagePreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, imageInfo);
                        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                    }
                }
            }
        });

        holder.tvLyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Double.parseDouble(SharePrefUtil.getString(context, AppConsts.SCORE, "0")) < 60) {
//                    new CreditDialog(context).show();
//                    return;
//                }


                if (!StringUtil.isEmpty(list.get(position).getId()))
                    isCanZhaoHu(list.get(position));

            }
        });
    }


    /**
     * 点赞
     *
     * @param positon
     */
    private void zan(final int positon) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("dtid", list.get(positon).getDongtai().get(0).getId() + "");
        OkHttpHelper.getInstance().post(context, Url.zan, params, new BaseCallback<BaseBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                list.get(positon).getDongtai().get(0).setZan((list.get(positon).getDongtai().get(0).getZan() + 1));
                list.get(positon).getDongtai().get(0).setIszan("1");
                notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void cancleZan(final int positon) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("dtid", list.get(positon).getDongtai().get(0).getId() + "");
        OkHttpHelper.getInstance().post(context, Url.cancleZan, params, new BaseCallback<BaseBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                list.get(positon).getDongtai().get(0).setIszan("0");
                list.get(positon).getDongtai().get(0).setZan((list.get(positon).getDongtai().get(0).getZan() - 1));
                notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 判断是否可以打招呼
     */
    private void isCanZhaoHu(final HomeRecommendBean.DataBean dataBean) {
        if (SharePrefUtil.getString(context, AppConsts.UID, "").equals( dataBean.getId())){
            ToastUtil.show("自己不可以和自己打招呼");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("say_uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("be_say_uid", dataBean.getId());
        OkHttpHelper.getInstance().post(context, Url.Zhaohu, params, new SpotsCallBack<ZhaoHuBean>(context) {
            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {
                if (null != bean.getData()) {
                    switch (bean.getData().getError_code()) {
                        case -1: //不接受任何人

                            break;
                        case -2://只接受符合条件用户
                            new FhtjDialog(context).show();
                            break;
                        case -3://被拉黑
                            ToastUtil.show("被拉黑无法发起聊天");
                            break;
                        case -4://超时未打招呼
                            ToastUtil.show("超时未打招呼");
                            break;
                        case -5://会员打招呼次数上限
                            ToastUtil.show("今天打招呼已达上限");
                            break;
                        case -6://普通用户打招呼次数上限
                            new VipDialog(context).show();
                            break;
                        case -7:
                            new SmrzDialog(context).show();
                            break;
                        default:
                            if (!StringUtil.isEmpty(dataBean.getId())) {
                                String name = "聊天";
                                if (null != dataBean.getNickname())
                                    name = dataBean.getNickname();
                                RongIM.getInstance().startPrivateChat(context, dataBean.getId(), name);
                            }
                            break;

                    }
                } else {
                    if (!StringUtil.isEmpty(dataBean.getId())) {
                        String name = "聊天";
                        if (null != dataBean.getNickname())
                            name = dataBean.getNickname();
                        RongIM.getInstance().startPrivateChat(context, dataBean.getId(), name);
                    }
                }
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

    public interface PeopleClickListener {
        void OnItemClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_jf)
        TextView tvJf;
        @BindView(R.id.iv_vip)
        ImageView ivVip;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_sg)
        TextView tvSg;
        @BindView(R.id.tv_xl)
        TextView tvXl;
        @BindView(R.id.tv_sr)
        TextView tvSr;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_reason)
        TextView tvReason;
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.tv_zanNum)
        TextView tvZanNum;
        @BindView(R.id.ll_zan)
        LinearLayout llZan;
        @BindView(R.id.tv_lyl)
        TextView tvLyl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}



