package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.UserZytjBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.AgeSectionDialog;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.IncomeSectionDialog;
import com.people.loveme.view.SgSectionDialog;
import com.people.loveme.view.SingleChooseDialog;

import java.util.ArrayList;
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

public class UserZytjFra extends TitleFragment implements View.OnClickListener, NaviRightListener {
    Unbinder unbinder;
    @BindView(R.id.tv_nl)
    TextView tvNl;
    @BindView(R.id.tv_jzd)
    TextView tvJzd;
    @BindView(R.id.tv_sg)
    TextView tvSg;
    @BindView(R.id.tv_ysr)
    TextView tvYsr;
    @BindView(R.id.tv_yl)
    TextView tvYl;
    @BindView(R.id.tv_fc)
    TextView tvFc;
    @BindView(R.id.tv_qc)
    TextView tvQc;

    private String personalsAge, personalsCity, personnalsSg, personnalsYsr, personnalsXl, personnalsFc, personnalsQc;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_zhengyou);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_zytj, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvNl.setOnClickListener(this);
        tvJzd.setOnClickListener(this);
        tvSg.setOnClickListener(this);
        tvYsr.setOnClickListener(this);
        tvFc.setOnClickListener(this);
        tvQc.setOnClickListener(this);
        tvYl.setOnClickListener(this);
        getUserZyInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nl:
                AgeSectionDialog ageSectionDialog = new AgeSectionDialog(mContext, getString(R.string.wo_age), new AgeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String age) {
                        if (age.startsWith(getString(R.string.wo_buxian)) && age.endsWith(getString(R.string.wo_buxian))) {
                            personalsAge = null;
                            tvNl.setText(getString(R.string.wo_buxian));
                        } else {
                            if (age.startsWith(getString(R.string.wo_buxian))) {
                                personalsAge = age.replace(getString(R.string.wo_buxian), "0");
                                tvNl.setText(age.split("-")[1] + "以下");
                            } else if (age.endsWith(getString(R.string.wo_buxian)) || age.endsWith("60")) {
                                personalsAge = age.replace(getString(R.string.wo_buxian), "60");
                                tvNl.setText(age.split("-")[0] + "以上");
                            } else {
                                personalsAge = age;
                                tvNl.setText(age);
                            }

                        }
                    }
                });
                ageSectionDialog.show();
                break;
            case R.id.tv_jzd:
                CityChooseDialog cityChooseDialog = new CityChooseDialog(mContext, mContext.getString(R.string.wo_buxian), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        personalsCity = province + city;
                        tvJzd.setText(personalsCity);
                    }
                }, true);
                cityChooseDialog.show();
                break;
            case R.id.tv_sg:
                SgSectionDialog sgSectionDialog = new SgSectionDialog(mContext, mContext.getString(R.string.height), new AgeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String sg) {
                        tvSg.setText(sg);
                        if (sg.startsWith(getString(R.string.wo_buxian)) && sg.endsWith(getString(R.string.wo_buxian))) {
                            tvSg.setText(getString(R.string.wo_buxian));
                            personnalsSg = null;
                        } else {
                            if (sg.startsWith(getString(R.string.wo_buxian))) {
                                personnalsSg = sg.replace(getString(R.string.wo_buxian), "150");
                                tvSg.setText(sg.split("-")[1] + "以下");
                            } else if (sg.endsWith(getString(R.string.wo_buxian))) {
                                personnalsSg = sg.replace(getString(R.string.wo_buxian), "210");
                                tvSg.setText(sg.split("-")[0] + "以上");
                            } else
                                personnalsSg = sg;
                        }
                    }
                });
                sgSectionDialog.show();
                break;
            case R.id.tv_ysr:
                IncomeSectionDialog incomeSectionDialog = new IncomeSectionDialog(mContext, mContext.getString(R.string.wo_yueshou), new IncomeSectionDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String income) {
                        personnalsYsr = income;
                        if (income.startsWith("0") && income.endsWith("100")) {
                            tvYsr.setText(getString(R.string.wo_buxian));
                        } else if (income.endsWith("100")) {
                            if (Integer.parseInt(income.split("-")[0]) >= 10)
                                tvYsr.setText(Integer.parseInt(income.split("-")[0]) / 10 + "万以上");
                            else
                                tvYsr.setText(income.split("-")[0] + "千以上");
                        } else if (income.startsWith("0")) {
                            if (Integer.parseInt(income.split("-")[1]) >= 10)
                                tvYsr.setText(Integer.parseInt(income.split("-")[1]) / 10 + "万以下");
                            else
                                tvYsr.setText(income.split("-")[1] + "千以下");
                        } else {
                            String start = "2千", end = "5万";
                            if (Integer.parseInt(income.split("-")[0]) >= 10)
                                start = Integer.parseInt(income.split("-")[0]) / 10 + "万";
                            else
                                start = income.split("-")[0] + "千";

                            if (Integer.parseInt(income.split("-")[1]) >= 10)
                                end = Integer.parseInt(income.split("-")[1]) / 10 + "万";
                            else
                                end = income.split("-")[1] + "千";

                            tvYsr.setText(start + "-" + end);
                        }
                    }
                });
                incomeSectionDialog.show();

                break;
            case R.id.tv_yl:
                final List<String> xl = Arrays.asList(getResources().getStringArray(R.array.xl));
                final List<String> xls = Arrays.asList(getResources().getStringArray(R.array.xls));
                SingleChooseDialog xlDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_xueli), xls, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        personnalsXl = xl.get(position);
                        tvYl.setText(xls.get(position));
                    }
                });
                xlDialog.show();
                break;
            case R.id.tv_fc:
                final List<String> fc = new ArrayList<>();
                fc.add(getString(R.string.wo_buxian));
                fc.add(getString(R.string.wo_youfang));
                SingleChooseDialog fcChooseDialog = new SingleChooseDialog(mContext, getString(R.string.wo_fangchan), fc, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvFc.setText(fc.get(position));
                        personnalsFc = position + "";
                    }
                });
                fcChooseDialog.show();
                break;
            case R.id.tv_qc:
                final List<String> qc = new ArrayList<>();
                qc.add(getString(R.string.wo_buxian));
                qc.add("有车");
                SingleChooseDialog qcChooseDialog = new SingleChooseDialog(mContext, "汽车", qc, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        personnalsQc = position + "";
                        tvQc.setText(qc.get(position));
                    }
                });
                qcChooseDialog.show();
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
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != personalsAge)
            params.put("age", personalsAge);//年龄
        if (null != personnalsSg)
            params.put("height", personnalsSg);//身高
        if (null != personnalsYsr)
            params.put("income", personnalsYsr);//收入
        if (null != personnalsXl)
            params.put("edu", personnalsXl);//学历
        if (null != personalsCity)
            params.put("city", personalsCity);//城市
        if (null != personnalsFc)
            params.put("house", personnalsFc);//是否有房
        if (null != personnalsQc)
            params.put("car", personnalsQc);//是否有车

        OkHttpHelper.getInstance().post(getContext(), Url.findTiaoJian, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("保存成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEZYTJ);
                act.finishSelf();
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
                    if (!StringUtil.isEmpty(bean.getData().getAge())) {
                        String showAge = "";
                        if (!StringUtil.isEmpty(bean.getData().getAge())) {
                            if (bean.getData().getAge().startsWith("0"))
                                showAge = bean.getData().getAge().split("-")[1] + "以下";
                            else if (bean.getData().getAge().endsWith("60"))
                                showAge = bean.getData().getAge().split("-")[0] + "以上";
                            else
                                showAge = bean.getData().getAge();
                        }
                        tvNl.setText(showAge);
                    }

                    if (!StringUtil.isEmpty(bean.getData().getHeight())) {
                        if (bean.getData().getHeight().startsWith(getString(R.string.wo_buxian)) && bean.getData().getHeight().endsWith(getString(R.string.wo_buxian))) {
                            tvSg.setText(getString(R.string.wo_buxian));
                        } else {
                            if (bean.getData().getHeight().endsWith("210"))
                                tvSg.setText(bean.getData().getHeight().split("-")[0] + "以上");
                            else if (bean.getData().getHeight().startsWith("150"))
                                tvSg.setText(bean.getData().getHeight().split("-")[1] + "以下");
                            else
                                tvSg.setText(bean.getData().getHeight());
                        }
                    }

                    if (!StringUtil.isEmpty(bean.getData().getIncome())) {
                        if (bean.getData().getIncome().startsWith(getString(R.string.wo_buxian)) && bean.getData().getIncome().endsWith(getString(R.string.wo_buxian))) {
                            tvYsr.setText(getString(R.string.wo_buxian));
                        } else {
                            if (bean.getData().getIncome().endsWith("100")) {
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[0]) >= 10)
                                    tvYsr.setText(Integer.parseInt(bean.getData().getIncome().split("-")[0]) / 10 + "万以上");
                                else
                                    tvYsr.setText(bean.getData().getIncome().split("-")[0] + "千以上");
                            } else if (bean.getData().getIncome().startsWith("0")) {
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[1]) >= 10)
                                    tvYsr.setText(Integer.parseInt(bean.getData().getIncome().split("-")[1]) / 10 + "万以下");
                                else
                                    tvYsr.setText(bean.getData().getIncome().split("-")[1] + "千以下");
                            } else {
                                String start = "2千", end = "5万";
                                if (Integer.parseInt(bean.getData().getIncome().split("-")[0]) >= 10)
                                    start = Integer.parseInt(bean.getData().getIncome().split("-")[0]) / 10 + "万";
                                else
                                    start = bean.getData().getIncome().split("-")[0] + "千";

                                if (Integer.parseInt(bean.getData().getIncome().split("-")[1]) >= 10)
                                    end = Integer.parseInt(bean.getData().getIncome().split("-")[1]) / 10 + "万";
                                else
                                    end = bean.getData().getIncome().split("-")[1] + "千";

                                tvYsr.setText(start + "-" + end);
                            }

                        }
                    }

                    if (!StringUtil.isEmpty(bean.getData().getEdu()))
                        tvYl.setText(bean.getData().getEdu() + "及以上");
                    if (!StringUtil.isEmpty(bean.getData().getCity()))
                        tvJzd.setText(bean.getData().getCity());
                    if (!StringUtil.isEmpty(bean.getData().getHouse()) && bean.getData().getHouse().equals("1"))
                        tvFc.setText(getString(R.string.wo_youfang));
                    else
                        tvFc.setText(getString(R.string.wo_buxian));
                    if (!StringUtil.isEmpty(bean.getData().getCar()) && bean.getData().getHouse().equals("1"))
                        tvQc.setText(getString(R.string.wo_youche));
                    else
                        tvQc.setText(getString(R.string.wo_buxian));
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }
}

