package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * Created by kxn on 2019/1/23 0023.
 */
public class ReportFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private String beReportUid;

    @Override
    public String getTitleName() {
        return "举报";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_report, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        beReportUid = getArguments().getString("beReportUid");
    }

    @OnClick(R.id.btn_submit)
    void report() {
        String content = etContent.getText().toString();
        if (StringUtil.isEmpty(content)) {
            ToastUtil.show("请输入举报内容");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("jubao_uid", userId);
        params.put("be_jubao_uid", beReportUid);
        params.put("content", content);
        OkHttpHelper.getInstance().post(getContext(), Url.report, params, new SpotsCallBack<BaseBean>(mContext) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("举报成功！");
                act.finishSelf();
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

