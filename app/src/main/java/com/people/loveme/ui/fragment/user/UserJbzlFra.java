package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.UserBasicBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.SingleChooseDialog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2018/9/7 0007.
 */

public class UserJbzlFra extends TitleFragment implements View.OnClickListener, NaviRightListener {
    Unbinder unbinder;
    @BindView(R.id.tv_sg)
    TextView tvSg;
    @BindView(R.id.tv_sr)
    TextView tvSr;
    @BindView(R.id.tv_xl)
    TextView tvXl;
    @BindView(R.id.tv_hy)
    TextView tvHy;
    @BindView(R.id.tv_hyzk)
    TextView tvHyzk;
    @BindView(R.id.tv_wzxz)
    TextView tvWzxz;
    @BindView(R.id.et_byyx)
    EditText etByyx;
    @BindView(R.id.tvID)
    TextView tvID;

    private String stature, income, education, academy, profession, marital_status, seek;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_jibenziliao);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_jbzl, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvSg.setOnClickListener(this);
        tvSr.setOnClickListener(this);
        tvXl.setOnClickListener(this);
        tvHy.setOnClickListener(this);
        tvHyzk.setOnClickListener(this);
        tvWzxz.setOnClickListener(this);
        getBasicInfo();
    }

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
                            case "1":
                                tvSr.setText("2千以下");
                                break;
                            case "2":
                                tvSr.setText("2-4千元");
                                break;
                            case "4":
                                tvSr.setText("4-6千元");
                                break;
                            case "6":
                                tvSr.setText("6-1万元");
                                break;
                            case "10":
                                tvSr.setText("1-1.5万元");
                                break;
                            case "15":
                                tvSr.setText("1.5-2万元");
                                break;
                            case "20":
                                tvSr.setText("2-5万元");
                                break;
                            case "50":
                                tvSr.setText("5万元以上");
                                break;
                            default:
                                tvSr.setText("2千以上");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    List<String> sg, sr, xl, hy, hyzk, xz;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sg:
                sg = Arrays.asList(getResources().getStringArray(R.array.shengao));
                SingleChooseDialog sgChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_shengao), sg, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        stature = sg.get(position);
                        tvSg.setText(stature + "cm");
                    }
                });
                sgChooseDialog.show();
                break;
            case R.id.tv_sr:
                sr = Arrays.asList(getResources().getStringArray(R.array.sr));
                SingleChooseDialog srChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_yueshou), sr, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 0:
                                income = "1";
                                break;
                            case 1:
                                income = "2";
                                break;
                            case 2:
                                income = "4";
                                break;
                            case 3:
                                income = "6";
                                break;
                            case 4:
                                income = "10";
                                break;
                            case 5:
                                income = "15";
                                break;
                            case 6:
                                income = "20";
                                break;
                            case 7:
                                income = "50";
                                break;

                        }
                        tvSr.setText(sr.get(position));
                    }
                });
                srChooseDialog.show();
                break;
            case R.id.tv_xl:
                xl = Arrays.asList(getResources().getStringArray(R.array.xl));
                SingleChooseDialog slChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_xueli), xl, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        education = xl.get(position);
                        tvXl.setText(education);
                    }
                });
                slChooseDialog.show();
                break;
            case R.id.tv_hy:
                hy = Arrays.asList(getResources().getStringArray(R.array.hy));
                SingleChooseDialog hyChooseDialog = new SingleChooseDialog(mContext, "行业", hy, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        profession = hy.get(position);
                        tvHy.setText(profession);
                    }
                });
                hyChooseDialog.show();
                break;
            case R.id.tv_hyzk:
                hyzk = Arrays.asList(getResources().getStringArray(R.array.hyzk));
                SingleChooseDialog hyzkChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_hunyinzhuang), hyzk, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        marital_status = position + "";
                        tvHyzk.setText(hyzk.get(position));
                    }
                });
                hyzkChooseDialog.show();
                break;
            case R.id.tv_wzxz:
                xz = Arrays.asList(getResources().getStringArray(R.array.xz));
                SingleChooseDialog xzChooseDialog = new SingleChooseDialog(mContext, "寻找", xz, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        seek = xz.get(position);
                        tvWzxz.setText(seek);
                    }
                });
                xzChooseDialog.show();
                break;
        }
    }

    @Override
    public int rightText() {
        return R.string.save;
    }

    @Override
    public void onRightClicked(View v) {
        save();
    }

    /**
     * 保存信息
     */
    private void save() {
        academy = etByyx.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != income)
            params.put("income", income);//收入
        if (null != stature)
            params.put("height", stature);//身高
        if (null != education)
            params.put("edu", education);//学历
        if (null != academy)
            params.put("academy", academy);//院校
        if (null != profession)
            params.put("profession", profession);//职业
        if (null != marital_status)
            params.put("marital_status", marital_status);//婚姻状态 0 未婚 1 已婚 2 离异
        if (null != seek)
            params.put("seek", seek);//寻找

        OkHttpHelper.getInstance().post(getContext(), Url.editBasicInfo, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("保存成功！");
                act.finishSelf();
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
