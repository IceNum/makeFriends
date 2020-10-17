package com.people.loveme.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.people.loveme.bean.GcListBean;
import com.people.loveme.bean.ZhaoHuBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
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
import io.rong.imlib.model.UserInfo;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/11 0011.
 */

public class GcAdapter extends RecyclerView.Adapter<GcAdapter.ViewHolder> {


    private Context context;
    private List<GcListBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    private PeopleClickListener peopleClickListener;

    public GcAdapter(Context context, List<GcListBean.DataBean> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_gc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (null != list.get(position).getContent())
            holder.tvContent.setText(list.get(position).getContent());
        else
            holder.tvContent.setText("");


        if (!StringUtil.isEmpty(list.get(position).getAddress())) {
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvAddress.setText(list.get(position).getAddress());
        } else {
            holder.tvAddress.setVisibility(View.GONE);
        }


        if (null != list.get(position).getTime_text())
            holder.tvTime.setText(list.get(position).getTime_text());


        PicassoUtil.setImag(context, list.get(position).getImages(), holder.ivPic);
        if (null != list.get(position).getMember()) {
            PicassoUtil.setHeadImag(context, list.get(position).getMember().getHeadimage(), holder.ivHead);
            if (null != list.get(position).getMember().getNickname())
                holder.tvName.setText(list.get(position).getMember().getNickname());
            else
                holder.tvName.setText("未命名");

            if (!StringUtil.isEmpty(list.get(position).getMember().getScore()))
                holder.tvJf.setText(new BigDecimal(list.get(position).getMember().getScore()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            else
                holder.tvJf.setText("0");
            if (null != list.get(position).getMember().getIsvip() && list.get(position).getMember().getIsvip().equals("1"))
                holder.ivVip.setVisibility(View.VISIBLE);
            else
                holder.ivVip.setVisibility(View.GONE);
            if (!StringUtil.isEmpty(list.get(position).getMember().getAge())) {
                holder.tvAge.setText(list.get(position).getMember().getAge() + "岁");
                holder.tvAge.setVisibility(View.VISIBLE);
            } else
                holder.tvAge.setVisibility(View.GONE);
        }

        if (null != list.get(position).getZiliao()) {
            if (null != list.get(position).getZiliao() && !StringUtil.isEmpty(list.get(position).getZiliao().getHeight())) {
                holder.tvSg.setText(list.get(position).getZiliao().getHeight() + "cm");
                holder.tvSg.setVisibility(View.VISIBLE);
            } else
                holder.tvSg.setVisibility(View.GONE);

            if (null != list.get(position).getZiliao() && !StringUtil.isEmpty(list.get(position).getZiliao().getEdu())) {
                holder.tvXl.setVisibility(View.VISIBLE);
                holder.tvXl.setText(list.get(position).getZiliao().getEdu());
            } else
                holder.tvXl.setVisibility(View.GONE);

            if (null != list.get(position).getZiliao() && !StringUtil.isEmpty(list.get(position).getZiliao().getIncome())) {
                switch (list.get(position).getZiliao().getIncome()) {
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

        } else {
            holder.tvSr.setVisibility(View.GONE);
            holder.tvSg.setVisibility(View.GONE);
            holder.tvXl.setVisibility(View.GONE);
        }


        if (null != list.get(position).getIszan() && list.get(position).getIszan().equals("1")) {
            holder.ivZan.setImageResource(R.mipmap.zans);
        } else
            holder.ivZan.setImageResource(R.mipmap.zan);

        holder.tvZanNum.setText("赞 " + list.get(position).getZan());

        holder.llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != list.get(position).getIszan() && list.get(position).getIszan().equals("1"))
                    cancleZan(position);
                else
                    zan(position);
            }
        });

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != peopleClickListener)
                    peopleClickListener.OnItemClick(position);
            }
        });

        holder.tvLyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Double.parseDouble(SharePrefUtil.getString(context, AppConsts.SCORE, "0")) < 60) {
//                    new CreditDialog(context).show();
//                    return;
//                }


                if (!StringUtil.isEmpty(list.get(position).getUid()))
                    isCanZhaoHu(list.get(position));

            }
        });

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

        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                ImageInfo imageInfo1 = new ImageInfo();
                imageInfo1.setBigImageUrl(list.get(position).getImages());
                imageInfo1.setThumbnailUrl(list.get(position).getImages());
                imageInfo.add(imageInfo1);
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, imageInfo);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", list.get(position).getUid());
                ActivitySwitcher.startFragment(context, OtherHomePageFra.class, bundle);
            }
        });
    }


    /**
     * 判断是否可以打招呼
     */
    private void isCanZhaoHu(final GcListBean.DataBean dataBean) {
        if (SharePrefUtil.getString(context, AppConsts.UID, "").equals( dataBean.getUid())){
            ToastUtil.show("自己不可以和自己打招呼");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("say_uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("be_say_uid", dataBean.getUid());
        OkHttpHelper.getInstance().post(context, Url.Zhaohu, params, new SpotsCallBack<ZhaoHuBean>(context) {
            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {
                if (null != bean.getData()) {
                    switch (bean.getData().getError_code()) {
                        case -1: //不接受任何人
                            ToastUtil.show("该用户不接受任何人聊天");
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
                            if (!StringUtil.isEmpty(dataBean.getUid())) {
                                String nickName = SharePrefUtil.getString(context, AppConsts.NAME, null);
                                String userHead = SharePrefUtil.getString(context, AppConsts.HEAD, null);
                                String uid = SharePrefUtil.getString(context, AppConsts.UID, null);
                                if (null != uid && null != nickName && null != userHead)
                                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(uid, nickName, Uri.parse(userHead)));
                                RongIM.getInstance().setMessageAttachedUserInfo(true);
                                String name = "聊天";
                                if (null != dataBean.getMember().getNickname())
                                    name = dataBean.getMember().getNickname();
                                RongIM.getInstance().startPrivateChat(context, dataBean.getUid(), name);
                            }
                            break;
                    }
                } else {
                    if (!StringUtil.isEmpty(dataBean.getUid())) {
                        String nickName = SharePrefUtil.getString(context, AppConsts.NAME, null);
                        String userHead = SharePrefUtil.getString(context, AppConsts.HEAD, null);
                        String uid = SharePrefUtil.getString(context, AppConsts.UID, null);
                        if (null != uid && null != nickName && null != userHead)
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(uid, nickName, Uri.parse(userHead)));
                        RongIM.getInstance().setMessageAttachedUserInfo(true);
                        String name = "聊天";
                        if (null != dataBean.getMember() && null != dataBean.getMember().getNickname())
                            name = dataBean.getMember().getNickname();
                        RongIM.getInstance().startPrivateChat(context, dataBean.getUid(), name);
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 点赞
     *
     * @param positon
     */
    private void zan(final int positon) {
        list.get(positon).setZan((list.get(positon).getZan() + 1));
        list.get(positon).setIszan("1");
        notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("dtid", list.get(positon).getId());
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
//                list.get(positon).setZan((list.get(positon).getZan() + 1));
//                list.get(positon).setIszan("1");
//                notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void cancleZan(final int positon) {
        list.get(positon).setIszan("0");
        list.get(positon).setZan((list.get(positon).getZan() - 1));
        notifyDataSetChanged();

        Map<String, String> params = new HashMap<>();
        params.put("uid", SharePrefUtil.getString(context, AppConsts.UID, ""));
        params.put("dtid", list.get(positon).getId());
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
//                list.get(positon).setIszan("0");
//                list.get(positon).setZan((list.get(positon).getZan() - 1));
//                notifyDataSetChanged();
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
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.tv_zanNum)
        TextView tvZanNum;
        @BindView(R.id.ll_zan)
        LinearLayout llZan;
        @BindView(R.id.tv_lyl)
        TextView tvLyl;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

