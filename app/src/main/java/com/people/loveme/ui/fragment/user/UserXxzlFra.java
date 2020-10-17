package com.people.loveme.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.UserXxInfoBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.SingleChooseDialog;
import com.people.loveme.view.wheel.ProvincePopWindow;

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
 * 详细资料
 */

public class UserXxzlFra extends TitleFragment implements View.OnClickListener, ProvincePopWindow.PopInterface, PopupWindow.OnDismissListener, NaviRightListener {
    Unbinder unbinder;
    @BindView(R.id.tv_cgqk)
    TextView tvCgqk;
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

    ProvincePopWindow cityPopWindow;
    @BindView(R.id.et_tz)
    EditText etTz;
    @BindView(R.id.tv_gfqk)
    TextView tvGfqk;
    private String car,house, weight, nation, native_place, is_children, religion, constellation, shengxiao;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xiangxiziliao);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_xxzl, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvCgqk.setOnClickListener(this);
        tvGfqk.setOnClickListener(this);
        tvMz.setOnClickListener(this);
        tvJg.setOnClickListener(this);
        tvYwzn.setOnClickListener(this);
        tvZjxy.setOnClickListener(this);
        tvXz.setOnClickListener(this);
        tvSx.setOnClickListener(this);
        getUserDetailInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    List<String> gcqk,gfqk, mz, jg, ywzn, zjxy, xz, sx;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cgqk:
                gcqk = new ArrayList<>();
                gcqk.add("未购车");
                gcqk.add("已购车");
                SingleChooseDialog gcqkChooseDialog = new SingleChooseDialog(mContext, "购车情况", gcqk, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvCgqk.setText(gcqk.get(position));
                        car = position + "";
                    }
                });
                gcqkChooseDialog.show();
                break;
            case R.id.tv_gfqk:
                gfqk = new ArrayList<>();
                gfqk.add("未购房");
                gfqk.add("已购房");
                SingleChooseDialog gfqkChooseDialog = new SingleChooseDialog(mContext, "购房情况", gfqk, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvGfqk.setText(gfqk.get(position));
                        house = position + "";
                    }
                });
                gfqkChooseDialog.show();
                break;
            case R.id.tv_mz:
                mz = Arrays.asList(getResources().getStringArray(R.array.mz));
                SingleChooseDialog mzChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_minzu), mz, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        nation = mz.get(position);
                        tvMz.setText(nation);
                    }
                });
                mzChooseDialog.show();
                break;
            case R.id.tv_jg:

                CityChooseDialog cityChooseDialog = new CityChooseDialog(mContext, mContext.getString(R.string.wo_jiguan), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        native_place = province + city;
                        tvJg.setText(native_place);
                    }
                },false);
                cityChooseDialog.show();
                break;
            case R.id.tv_ywzn:
                ywzn = Arrays.asList(getResources().getStringArray(R.array.ywzn));
                SingleChooseDialog ywznChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_youwuzinv), ywzn, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvYwzn.setText(ywzn.get(position));
                        is_children = position + "";
                    }
                });
                ywznChooseDialog.show();
                break;
            case R.id.tv_zjxy:
                zjxy = Arrays.asList(getResources().getStringArray(R.array.zjxy));
                SingleChooseDialog zjxyChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_zongjiao), zjxy, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        religion = zjxy.get(position);
                        tvZjxy.setText(zjxy.get(position));
                    }
                });
                zjxyChooseDialog.show();
                break;
            case R.id.tv_xz:
                xz = Arrays.asList(getResources().getStringArray(R.array.xingzuo));
                SingleChooseDialog xzChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_xingzuo), xz, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        constellation = xz.get(position);
                        tvXz.setText(constellation);
                    }
                });
                xzChooseDialog.show();
                break;
            case R.id.tv_sx:
                sx = Arrays.asList(getResources().getStringArray(R.array.shuxiang));
                SingleChooseDialog sxChooseDialog = new SingleChooseDialog(mContext, "属性", sx, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        shengxiao = sx.get(position);
                        tvSx.setText(shengxiao);
                    }
                });
                sxChooseDialog.show();
                break;
        }
    }

    @Override
    public void saveVycle(String province, String city, String county, String districtId) {
        native_place = province + city;
        tvJg.setText(native_place);
    }


    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
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
        weight = etTz.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != car)
            params.put("car", car);//是否有车 1 有 0 没
        if (null != house)
            params.put("house", house);//是否有房 1 有 0 没
        if (null != weight)
            params.put("weight", weight);//体重
        if (null != nation)
            params.put("nation", nation);//民族
        if (null != native_place)
            params.put("na_place", native_place);//籍贯
        if (null != is_children)
            params.put("have_son", is_children);//是否有子女 1 有 0 没有
        if (null != religion)
            params.put("religion", religion);//宗教
        if (null != constellation)
            params.put("constellation", constellation);//星座
        if (null != shengxiao)
            params.put("shengxiao", shengxiao);//生肖

        OkHttpHelper.getInstance().post(getContext(), Url.editUerDetailInfo, params, new SpotsCallBack<BaseBean>(getContext()) {

            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("保存成功！");
                act.finishSelf();
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show("保存失败！");
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
                        etTz.setText(bean.getData().getWeight() + "");
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
                    if (!StringUtil.isEmpty(bean.getData().getHouse())) {
                        if (bean.getData().getHouse().equals("1"))
                            tvGfqk.setText("已购房");
                        else
                            tvGfqk.setText("未购房");
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }
}
