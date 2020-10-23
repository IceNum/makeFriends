package com.people.loveme.ui.fragment.system;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.UpdataBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.BaseCallback;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.login.LoginFra;
import com.people.loveme.utils.AppUtils;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.ToastUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kxn on 2018/8/18 0018.
 */

public class SettingFra extends TitleFragment implements View.OnClickListener {
    @BindView(R.id.tv_tzsz)
    TextView tvTzsz;
    @BindView(R.id.tv_hmd)
    TextView tvHmd;
    @BindView(R.id.tv_xgmm)
    TextView tvXgmm;
    @BindView(R.id.tv_yjfk)
    TextView tvYjfk;
    @BindView(R.id.tv_fwxy)
    TextView tvFwxy;
    @BindView(R.id.tv_lxwm)
    TextView tvLxwm;
    @BindView(R.id.tv_cjwt)
    TextView tvCjwt;
    @BindView(R.id.tv_bbgx)
    TextView tvBbgx;
    @BindView(R.id.tv_qchc)
    TextView tvQchc;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    Unbinder unbinder;
    @BindView(R.id.updateView)
    View updateView;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.settings);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_setting, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        tvTzsz.setOnClickListener(this);
        tvHmd.setOnClickListener(this);
        tvXgmm.setOnClickListener(this);
        tvYjfk.setOnClickListener(this);
        tvFwxy.setOnClickListener(this);
        tvLxwm.setOnClickListener(this);
        tvCjwt.setOnClickListener(this);
        tvBbgx.setOnClickListener(this);
        tvQchc.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        getVersion();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String updateAddress;

    void getVersion() {
        Map<String, String> params = new HashMap<>();
        mOkHttpHelper.post(getContext(), Url.appUpdate, params, new BaseCallback<UpdataBean>() {
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
            public void onSuccess(Response response, UpdataBean updataBean) {
                try {
                    Log.e("======","AppUtils.getVersionCode(getContext()) = " + AppUtils.getVersionCode(getContext())
                            + "  Integer.parseInt(updataBean.getVersion()) = " + Integer.parseInt(updataBean.getVersion()));
                    if (AppUtils.getVersionCode(getContext()) < Integer.parseInt(updataBean.getVersion())) {
                        updateAddress = updataBean.getUpdatefile();
                        updateView.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tzsz:
                ActivitySwitcher.startFragment(act, NoticeSettingFra.class);
                break;
            case R.id.tv_hmd:
                ActivitySwitcher.startFragment(act, HmdFra.class);
                break;
            case R.id.tv_xgmm:
                ActivitySwitcher.startFragment(act, ChangePasswordFra.class);
                break;
            case R.id.tv_yjfk:
                ActivitySwitcher.startFragment(act, FeedbackFra.class);
                break;
            case R.id.tv_fwxy:
                ActivitySwitcher.startFragment(act, FwxyFra.class);
                break;
            case R.id.tv_lxwm:
                ActivitySwitcher.startFragment(act, LxwmFra.class);
                break;
            case R.id.tv_cjwt:
                ActivitySwitcher.startFragment(act, QuestionsFra.class);
                break;
            case R.id.tv_bbgx:
                if (null != updateAddress) {
                    checkPmsLocation();
                } else {
                    ToastUtil.show("当前已是最新版本");
                }
                break;
            case R.id.tv_qchc:
                ToastUtil.show("清理成功！");
                break;
            case R.id.btn_logout:
                ActivitySwitcher.startFragment(act, LoginFra.class);
                eventCenter.sendType(EventCenter.EventType.EVT_LOGOUT);
                SharePrefUtil.saveString(mContext,AppConsts.UID,null);
                act.finishSelf();
                break;
        }
    }

    private void checkPmsLocation() {
        MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功

        AllenVersionChecker
                .getInstance()
                .downloadOnly(
                        UIData.create().setDownloadUrl(updateAddress).setTitle("提示").setContent("是否立即更新")
                ).executeMission(getContext());
    }

    @PermissionDenied(AppConsts.PMS_LOCATION)
    public void pmsLocationError() {
        ToastUtil.show("权限被拒绝，无法使用改功能！");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
