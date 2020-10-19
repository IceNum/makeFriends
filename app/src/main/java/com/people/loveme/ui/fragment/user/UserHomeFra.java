package com.people.loveme.ui.fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.adapter.OtherHomeDongtaiAdapter;
import com.people.loveme.adapter.UserXcAdapter;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.MyDtBean;
import com.people.loveme.bean.PhotoListBean;
import com.people.loveme.bean.RzztBean;
import com.people.loveme.bean.TagsBean;
import com.people.loveme.bean.UserBasicBean;
import com.people.loveme.bean.UserZytjBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.MyListView;
import com.people.loveme.view.PartColorTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/10/31 0031.
 * 个人主页
 */

public class UserHomeFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.iv_isVip)
    ImageView ivIsVip;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.ll_userInfo)
    LinearLayout llUserInfo;
    @BindView(R.id.iv_isMobile)
    ImageView ivIsMobile;
    @BindView(R.id.iv_isCard)
    ImageView ivIsCard;
    @BindView(R.id.iv_isEducation)
    ImageView ivIsEducation;
    @BindView(R.id.iv_isProfession)
    ImageView ivIsProfession;
    @BindView(R.id.iv_isCar)
    ImageView ivIsCar;
    @BindView(R.id.iv_isHous)
    ImageView ivIsHous;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.tv_nxdb)
    TextView tvNxdb;
    @BindView(R.id.tv_xzdt)
    PartColorTextView tvXzdt;
    @BindView(R.id.lv_dt)
    MyListView lvDt;
    @BindView(R.id.tv_xzdt_show)
    TextView tvXzdtShow;

    @BindView(R.id.fl_tags)
    TagFlowLayout flTags;


    private String nickName, head, biaoqian;
    OtherHomeDongtaiAdapter adapter;
    private List list;
    private boolean isLove = false;
    private List<PhotoListBean.DataBean> xcList;
    UserXcAdapter xcAdapter;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    private List<String> tags;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_user_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        act.hindNaviBar();
        initView();
        return rootView;
    }

    public void initView() {
        xcList = new ArrayList<>();
        tags = new ArrayList<>();
        xcAdapter = new UserXcAdapter(mContext, xcList, userId);
        gv.setAdapter(xcAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, i);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        if (null != userId) {
            getUserInfo();
            getRzzt();
            getPhotos();
            getUserZyInfo();
            getDynamic();
            getBasicInfo();
        }


//        LinearLayout.LayoutParams layoutParams = ((LinearLayout.LayoutParams) gv.getLayoutParams());
//        layoutParams.height = (ScreenUtil.getScreenWidth(mContext) - FormatUtil.pixOfDip(55)) / 4;
//        gv.setLayoutParams(layoutParams);


        ivShare.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);

        list = new ArrayList<>();
        adapter = new OtherHomeDongtaiAdapter(mContext, list);
        lvDt.setAdapter(adapter);

//        SmrzDialog smrzDialog = new SmrzDialog(mContext);
//        smrzDialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                ToastUtil.show("分享");
                break;
            case R.id.iv_back:
                act.finishSelf();
                break;
            case R.id.ll_userInfo:
                Bundle bundle = new Bundle();
                bundle.putString("id", userId);
                ActivitySwitcher.startFragment(getActivity(), UserDetailFra.class, bundle);
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (null != bean.getData()) {
                    PicassoUtil.setHeadImag(mContext, SharePrefUtil.getString(mContext,AppConsts.HEAD,""), ivHead);

                    if (!StringUtil.isEmpty(bean.getData().getNickname())) {
                        tvName.setText(bean.getData().getNickname());
                        tvTitle.setText(bean.getData().getNickname());
                    }
                    if (null != bean.getData().getIsvip() && bean.getData().getIsvip().equals("1"))
                        ivIsVip.setImageResource(R.mipmap.vip_item);
                    else
                        ivIsVip.setImageResource(R.mipmap.vip_item_no);
                    if (null != bean.getData().getSex() && bean.getData().getSex().equals("1")) {
                        tvXzdtShow.setText("心中的她");
                        ivSex.setImageResource(R.mipmap.nan_center);
                    } else {
                        ivSex.setImageResource(R.mipmap.nv_center);
                        tvXzdtShow.setText("心中的他");
                    }

                    tvJf.setText(new BigDecimal(bean.getData().getScore()).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "分");
                    if (!StringUtil.isEmpty(bean.getData().getDubai()))
                        tvNxdb.setText(bean.getData().getDubai());

                    if (!StringUtil.isEmpty(bean.getData().getBiaoqian())) {
                        biaoqian = bean.getData().getBiaoqian();
                        getTags();
                    } else {
                        tvTag1.setVisibility(View.GONE);
                        tvTag2.setVisibility(View.GONE);
                        tvTag3.setVisibility(View.GONE);
                    }


                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        tags.add(bean.getData().getAge() + "岁");
                    }
                }

                setTag();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void setTag() {
        final TagAdapter<String> tagAdapter = new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv_tag,
                        flTags, false);
                switch (position) {
                    case 0:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_age_50dp);
                        break;
                    case 1:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_address_50dp);
                        break;
                    case 2:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_shengao_50dp);
                        break;
                    case 3:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_xueli_50dp);
                        break;
                    case 4:
                        tv.setBackgroundResource(R.drawable.bg_rect_tag_shouru_50dp);
                        break;

                }

                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
            }
        };

        flTags.setAdapter(tagAdapter);
        flTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
    }


    /**
     * 获取用户基本资料
     */
    private void getBasicInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.userInfo, params, new SpotsCallBack<UserBasicBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserBasicBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        tags.add(bean.getData().getHeight() + "cm");
                    }
                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "1":
                                tags.add("2千以下");
                                break;
                            case "2":
                                tags.add("2-4千");
                                break;
                            case "4":
                                tags.add("4-6千");
                                break;
                            case "6":
                                tags.add("6-1万");
                                break;
                            case "10":
                                tags.add("1-1.5万");
                                break;
                            case "15":
                                tags.add("1.5-2万");
                                break;
                            case "20":
                                tags.add("2-5万");
                                break;
                            case "50":
                                tags.add("5万以上");
                                break;
                            default:
                                tags.add("2千以上");
                                break;
                        }
                    }
                    if (!StringUtil.isEmpty(bean.getData().getEdu())) {
                        tags.add(bean.getData().getEdu());
                    }
                    setTag();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 认证状态
     */
    private void getRzzt() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.rzzt, params, new SpotsCallBack<RzztBean>(getContext()) {
            @Override
            public void onSuccess(Response response, RzztBean rzztBean) {
                if (null != rzztBean.getData()) {
                    if (rzztBean.getData().getPhone().equals("1"))
                        ivIsMobile.setImageResource(R.mipmap.sjrenzheng);
                    if (rzztBean.getData().getRelaname().equals("1"))
                        ivIsCard.setImageResource(R.mipmap.sfrenzheng);
                    if (rzztBean.getData().getCar().equals("1"))
                        ivIsCar.setImageResource(R.mipmap.clrenzheng);
                    if (rzztBean.getData().getEdu().equals("1"))
                        ivIsEducation.setImageResource(R.mipmap.xlrenzheng);
                    if (rzztBean.getData().getWork().equals("1"))
                        ivIsProfession.setImageResource(R.mipmap.zyrenzheng);
                    if (rzztBean.getData().getHouse().equals("1"))
                        ivIsHous.setImageResource(R.mipmap.fcrenzheng);


                    if (null != rzztBean.getData().getRelaname())
                        if (rzztBean.getData().getRelaname().equals("1"))
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, true);
                        else
                            SharePrefUtil.saveBoolean(getContext(), AppConsts.REALNAME, false);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 用户相册
     */
    private void getPhotos() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        mOkHttpHelper.post(mContext, Url.photoList, params, new SpotsCallBack<PhotoListBean>(mContext) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }

            @Override
            public void onSuccess(Response response, PhotoListBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    xcList.addAll(bean.getData());
                    xcAdapter.notifyDataSetChanged();
                    imageInfo.clear();
                    for (int i = 0; i < xcList.size(); i++) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(xcList.get(i).getImage());
                        info.setBigImageUrl(xcList.get(i).getImage());
                        imageInfo.add(info);
                    }
                    xcAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private String city, age, height, income, edu;

    /**
     * 获取用户征友条件
     */
    private void getUserZyInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getFindTiaoJian, params, new SpotsCallBack<UserZytjBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserZytjBean bean) {
                StringBuffer xzdt = new StringBuffer();
                xzdt.append("想找一个");
                if (null != bean.getData()) {

                    if (!StringUtil.isEmpty(bean.getData().getCity())) {
                        city = bean.getData().getCity();
                        xzdt.append(city + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        age = bean.getData().getAge();
                        xzdt.append(getString(R.string.wo_buxian) + bean.getData().getAge() + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        height = bean.getData().getHeight();
                        xzdt.append(mContext.getString(R.string.height) + height + "cm,");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "0":
                                income = "2千以下";
                                break;
                            case "1":
                                income = "2-4千元";
                                break;
                            case "2":
                                income = "4-6千元";
                                break;
                            case "3":
                                income = "6千-1万元";
                                break;
                            case "4":
                                income = "1-1.5万元";
                                break;
                            case "5":
                                income = "1.5-2万元";
                                break;
                            case "6":
                                income = "2-5万元";
                                break;
                            case "7":
                                income = "5万元以上";
                                break;
                        }

                        xzdt.append(mContext.getString(R.string.wo_yueshou) + income + ",");
                    }

                    if (!StringUtil.isEmpty(bean.getData().getEdu())) {
                        edu = bean.getData().getEdu();
                        xzdt.append(bean.getData().getEdu() + "学历的");
                    }

                    xzdt.append("有缘人，快来联系我吧");
                }

                HashMap<String, Integer> keysColor = new HashMap<>();//参数之一:key:关键字 Value:颜色  但一定是Color(int)的.如果是#ffffff这样的话建议还是写在color资源包中,个人习惯.
                if (null != city)
                    keysColor.put(city, getResources().getColor(R.color.xzdt));
                if (null != age)
                    keysColor.put(age, getResources().getColor(R.color.xzdt));
                if (null != income)
                    keysColor.put(income, getResources().getColor(R.color.xzdt));
                if (null != edu)
                    keysColor.put(edu, getResources().getColor(R.color.xzdt));
                if (null != height)
                    keysColor.put(height, getResources().getColor(R.color.xzdt));
                tvXzdt.setPartText(xzdt.toString(), keysColor, getResources().getColor(R.color.txt_lv7));
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }

    /**
     * 获取用户动态
     */
    private void getDynamic() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.myDynamic, params, new BaseCallback<MyDtBean>() {
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
            public void onSuccess(Response response, MyDtBean myDtBean) {
                if (!ListUtil.isEmpty(myDtBean.getData()))
                    list.addAll(myDtBean.getData());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 用户标签
     */
    private void getTags() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getTags, params, new SpotsCallBack<TagsBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TagsBean bean) {
                if (!ListUtil.isEmpty(bean.getData())) {
                    String[] tags = biaoqian.split(",");
                    for (int i = 0; i < tags.length; i++) {
                        for (int j = 0; j < bean.getData().size(); j++) {
                            if (tags[i].equals(bean.getData().get(j).getId())) {
                                switch (i) {
                                    case 0:
                                        tvTag1.setText(bean.getData().get(j).getName());
                                        tvTag2.setVisibility(View.GONE);
                                        tvTag3.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        tvTag2.setText(bean.getData().get(j).getName());
                                        tvTag2.setVisibility(View.VISIBLE);
                                        tvTag3.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        tvTag3.setText(bean.getData().get(j).getName());
                                        tvTag3.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.txt_main);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

