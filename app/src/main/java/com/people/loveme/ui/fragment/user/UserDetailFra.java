package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.UserBasicBean;
import com.people.loveme.bean.UserXxInfoBean;
import com.people.loveme.bean.UserZytjBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/12/4 0004.
 * 用户详细信息
 */

public class UserDetailFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.tvID)
    TextView tvID;
    @BindView(R.id.tv_sg)
    TextView tvSg;
    @BindView(R.id.tv_sr)
    TextView tvSr;
    @BindView(R.id.tv_xl)
    TextView tvXl;
    @BindView(R.id.et_byyx)
    TextView etByyx;
    @BindView(R.id.tv_hy)
    TextView tvHy;
    @BindView(R.id.tv_hyzk)
    TextView tvHyzk;
    @BindView(R.id.tv_wzxz)
    TextView tvWzxz;
    @BindView(R.id.tv_cgqk)
    TextView tvCgqk;
    @BindView(R.id.et_tz)
    TextView etTz;
    @BindView(R.id.tv_mz)
    TextView tvMz;
    @BindView(R.id.tv_jg)
    TextView tvJg;
    @BindView(R.id.tv_ywzn)
    TextView tvYwzn;
    @BindView(R.id.tv_zjxy)
    TextView tvZjxy;
    @BindView(R.id.tv_xz)
    TextView tvXz;
    @BindView(R.id.tv_sx)
    TextView tvSx;
    @BindView(R.id.tv_nl)
    TextView tvNl;
    @BindView(R.id.tv_jzd)
    TextView tvJzd;
    @BindView(R.id.tv_sg_tj)
    TextView tvSgTj;
    @BindView(R.id.tv_ysr)
    TextView tvYsr;
    @BindView(R.id.tv_yl)
    TextView tvYl;
    @BindView(R.id.tv_fc)
    TextView tvFc;
    @BindView(R.id.tv_qc)
    TextView tvQc;
    @BindView(R.id.tv_db)
    TextView tvDb;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xiangxiziliao);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_user_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        userId = getArguments().getString("id");
        getInfo();
        getBasicInfo();
        getUserDetailInfo();
        getUserZyInfo();
    }

    /**
     * 获取用户信息
     */
    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getUserInfo, params, new SpotsCallBack<GetUserInfoBean>(getContext()) {
            @Override
            public void onSuccess(Response response, GetUserInfoBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getDubai()))
                        tvDb.setText(bean.getData().getDubai());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
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
                    if (!StringUtil.isEmpty(bean.getData().getUid()))
                        tvID.setText(bean.getData().getUid());
                    if (!StringUtil.isEmpty(bean.getData().getHeight()))
                        tvSg.setText(bean.getData().getHeight() + "cm");
                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "0":
                                tvSr.setText("2千以下");
                                break;
                            case "1":
                                tvSr.setText("2-4千元");
                                break;
                            case "2":
                                tvSr.setText("4-6千元");
                                break;
                            case "3":
                                tvSr.setText("6-1万元");
                                break;
                            case "4":
                                tvSr.setText("1-1.5万元");
                                break;
                            case "5":
                                tvSr.setText("1.5-2万元");
                                break;
                            case "6":
                                tvSr.setText("2-5万元");
                                break;
                            case "7":
                                tvSr.setText("5万元以上");
                                break;
                        }
                    }
                    if (!StringUtil.isEmpty(bean.getData().getEdu()))
                        tvXl.setText(bean.getData().getEdu());
                    if (!StringUtil.isEmpty(bean.getData().getAcademy()))
                        etByyx.setText(bean.getData().getAcademy());
                    if (!StringUtil.isEmpty(bean.getData().getProfession()))
                        tvHy.setText(bean.getData().getProfession());
                    if (!StringUtil.isEmpty(bean.getData().getMarital_status())) {
                        String[] items = getResources().getStringArray(R.array.hyzk);
                        switch (bean.getData().getMarital_status()) {
                            case "0":
                                tvHyzk.setText(items[0]);
                                break;
                            case "1":
                                tvHyzk.setText(items[1]);
                                break;
                            case "2":
                                tvHyzk.setText(items[2]);
                                break;
                        }

                    }
                    if (!StringUtil.isEmpty(bean.getData().getSeek()))
                        tvWzxz.setText(bean.getData().getSeek());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 获取用户详细资料
     */
    private void getUserDetailInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.userDetailInfo, params, new SpotsCallBack<UserXxInfoBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserXxInfoBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getWeight()))
                        etTz.setText(bean.getData().getWeight() + "kg");
                    if (!StringUtil.isEmpty(bean.getData().getNation()))
                        tvMz.setText(bean.getData().getNation());
                    if (!StringUtil.isEmpty(bean.getData().getNa_place()))
                        tvJg.setText(bean.getData().getNa_place());
                    if (!StringUtil.isEmpty(bean.getData().getHave_son())) {
                        if (bean.getData().getHave_son().equals("1"))
                            tvYwzn.setText("有子女");
                        else
                            tvYwzn.setText("无子女");
                    }
                    if (!StringUtil.isEmpty(bean.getData().getReligion()))
                        tvZjxy.setText(bean.getData().getReligion());
                    if (!StringUtil.isEmpty(bean.getData().getConstellation()))
                        tvXz.setText(bean.getData().getConstellation());
                    if (!StringUtil.isEmpty(bean.getData().getShengxiao()))
                        tvSx.setText(bean.getData().getShengxiao());
                    if (!StringUtil.isEmpty(bean.getData().getCar())) {
                        if (bean.getData().getCar().equals("1"))
                            tvCgqk.setText("已购车");
                        else
                            tvCgqk.setText("未购车");
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }

    /**
     * 获取用户征友条件
     */
    private void getUserZyInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.getFindTiaoJian, params, new SpotsCallBack<UserZytjBean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserZytjBean bean) {
                if (null != bean.getData()) {
                    if (!StringUtil.isEmpty(bean.getData().getAge()))
                        tvNl.setText(bean.getData().getAge());
                    if (!StringUtil.isEmpty(bean.getData().getHeight()))
                        tvSgTj.setText(bean.getData().getHeight());
                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        switch (bean.getData().getIncome()) {
                            case "0":
                                tvYsr.setText("2千以下");
                                break;
                            case "1":
                                tvYsr.setText("2-4千元");
                                break;
                            case "2":
                                tvYsr.setText("4-6千元");
                                break;
                            case "3":
                                tvYsr.setText("6千-1万元");
                                break;
                            case "4":
                                tvYsr.setText("1-1.5万元");
                                break;
                            case "5":
                                tvYsr.setText("1.5-2万元");
                                break;
                            case "6":
                                tvYsr.setText("2-5万元");
                                break;
                            case "7":
                                tvYsr.setText("5万元以上");
                                break;

                        }
                    }

                    if (!StringUtil.isEmpty(bean.getData().getEdu()))
                        tvYl.setText(bean.getData().getEdu());
                    if (!StringUtil.isEmpty(bean.getData().getCity()))
                        tvJzd.setText(bean.getData().getCity());
                    if (!StringUtil.isEmpty(bean.getData().getHouse()) && bean.getData().getHouse().equals("1"))
                        tvFc.setText(getString(R.string.wo_youfang));
                    else
                        tvFc.setText(getString(R.string.wo_wufang));
                    if (!StringUtil.isEmpty(bean.getData().getCar()) && bean.getData().getHouse().equals("1"))
                        tvQc.setText(getString(R.string.wo_youche));
                    else
                        tvQc.setText(getString(R.string.wo_wuche));
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
