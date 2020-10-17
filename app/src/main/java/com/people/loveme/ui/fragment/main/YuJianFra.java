package com.people.loveme.ui.fragment.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.people.loveme.R;
import com.people.loveme.bean.UserMeetBean;
import com.people.loveme.bean.ZhaoHuBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.ui.fragment.user.WxhdFra;
import com.people.loveme.utils.FastBlurUtil;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.SendLikeSxDialog;
import com.people.loveme.view.SendLikeSxVipDialog;
import com.people.loveme.view.dragcard.CardsAdapter;
import com.people.loveme.view.dragcard.DragCardsView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class YuJianFra extends CachableFrg implements View.OnClickListener {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    Unbinder unbinder;
    @BindView(R.id.fl_list)
    FrameLayout flList;
    @BindView(R.id.iv_bxh)
    ImageView ivBxh;
    @BindView(R.id.iv_xh)
    ImageView ivXh;
    @BindView(R.id.dragCardsView)
    DragCardsView mDragCardsView;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    private List<UserMeetBean.DataBean> list;
    private boolean hasNo = false;

    private CardsAdapter mCardAdapter;

    private List<String> cardList;

    @Override
    protected int rootLayout() {
        return R.layout.fra_yujian;
    }

    @Override
    protected void initView() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
        Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 5);
        ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivBg.setImageBitmap(blurBitmap);

        cardList = new ArrayList<>();
        list = new ArrayList<>();
        getData();
        ivXh.setOnClickListener(this);
        ivBxh.setOnClickListener(this);
        flList.setOnClickListener(this);

    }

    float mPosX, mPosY, mCurPosX, mCurPosY, slid;
    private int total = 0, currentPositon = 0;


    /**
     * 获取遇见列表
     */
    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.userMeet, params, new SpotsCallBack<UserMeetBean>(getContext()) {

            @Override
            public void onSuccess(Response response, UserMeetBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    list.addAll(bean.getData());
                    total = list.size();
                    cardList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        cardList.add(list.get(i).getHeadimage());
                    }
                    initData();
                    final String head = list.get(currentPositon).getHeadimage();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (!StringUtil.isEmpty(head) && head.startsWith("http")) {
                                    Bitmap bmp = Picasso.with(getContext()).load(head).get();
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("bitmap", bmp);
                                    message.setData(bundle);
                                    mHandler.sendMessage(message);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else
                    hasNo = true;
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void initData() {
        mCardAdapter = new CardsAdapter(getContext(), cardList);
        mDragCardsView.setAdapter(mCardAdapter);
        mCardAdapter.notifyDataSetChanged();
        mDragCardsView.setOnItemClickListener(new DragCardsView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", list.get(currentPositon).getId());
                ActivitySwitcher.startFragment(getActivity(), OtherHomePageFra.class, bundle);
            }
        });
        mDragCardsView.setFlingListener(new DragCardsView.onDragListener() {

            @Override
            public void removeFirstObjectInAdapter(boolean isLeft) {
                // TODO Auto-generated method stub
                browseyujian(currentPositon);
                next();
                if (!isLeft) {
                    sendLove(currentPositon);
                }
                if (cardList.size() > 0) {
                    cardList.remove(0);
                }
                mCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSelectLeft(double distance) {
                // TODO Auto-generated method stub
            }


            @Override
            public void onSelectRight(double distance) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onCardReturn() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onCardMoveDistance(double distance) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // TODO Auto-generated method stub
//                ToastUtil.show("需要补牌了");

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.main_color);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.init();
    }

    /**
     * 喜欢
     */
    private void sendLove(int position) {

//        if (Double.parseDouble(SharePrefUtil.getString(getContext(), AppConsts.SCORE, "0")) < 60) {
//            new CreditDialog(getContext()).show();
//            return;
//        }
        if (hasNo)
            return;
        Map<String, String> params = new HashMap<>();
        params.put("like_uid", userId);
        params.put("be_like_uid", list.get(position).getId() + "");
        OkHttpHelper.getInstance().post(getContext(), Url.sendLove, params, new SpotsCallBack<ZhaoHuBean>(getContext()) {

            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {
                if (null != bean.getData()) {
                    switch (bean.getData().getError_code()) {
                        case -1://普通用户喜欢上限
                            if (null != bean.getData().getNumber())
                                new SendLikeSxDialog(getContext(), bean.getData().getNumber()).show();
                            else
                                new SendLikeSxDialog(getContext(), "15").show();
                            break;
                        case -2:
                            new SendLikeSxVipDialog(getContext()).show();
                            break;
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 遇见
     */
    private void browseyujian(int position) {
        if (hasNo)
            return;
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("yujian_uid", list.get(position).getId() + "");
        OkHttpHelper.getInstance().post(getContext(), Url.browseyujian, params, new SpotsCallBack<ZhaoHuBean>(getContext()) {
            @Override
            public void onSuccess(Response response, ZhaoHuBean bean) {
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = msg.getData().getParcelable("bitmap");
            Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 5);
            ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivBg.setImageBitmap(blurBitmap);
        }
    };

    private void next() {
        if (currentPositon < (total - 1)) {
            currentPositon++;
            final String head = list.get(currentPositon).getHeadimage();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!StringUtil.isEmpty(head) && head.startsWith("http")) {
                            Bitmap bmp = Picasso.with(getContext()).load(head).get();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("bitmap", bmp);
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ToastUtil.show("没有更多推荐数据了");
            hasNo = true;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_xh:
                mDragCardsView.rotationtRight();
                break;
            case R.id.iv_bxh:
                mDragCardsView.rotationLeft();
                break;
            case R.id.fl_list:
                ActivitySwitcher.startFragment(getActivity(), WxhdFra.class);
                break;
        }
    }
}
