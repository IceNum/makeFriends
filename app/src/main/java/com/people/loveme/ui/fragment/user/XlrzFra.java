package com.people.loveme.ui.fragment.user;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.bean.XlRzBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PicassoUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Response;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kxn on 2018/9/10 0010.
 */

public class XlrzFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.iv_xl)
    ImageView ivXl;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//图片
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private String img_path;
    private String status;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xuelrz);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_xlrz, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        ivXl.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        getInfo();
    }

    /**
     * 获取学历认证信息
     */
    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.educationInfo, params, new SpotsCallBack<XlRzBean>(getContext()) {
            @Override
            public void onSuccess(Response response, XlRzBean bean) {
                if (null != bean.getData()) {
                    PicassoUtil.setImag(mContext,  bean.getData().get_$1image(), ivXl);
                    if (!StringUtil.isEmpty(bean.getData().getStatus()))
                        status = bean.getData().getStatus();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_xl:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.tv_submit:
                if (null != status) {
                    if (status.equals("0")) {
                        ToastUtil.show("正在审核中，无需重复提交！");
                        return;
                    }

                    if (status.equals("1")) {
                        ToastUtil.show("审核已通过，无需重复提交！");
                        return;
                    }
                }
                xlRz();
                break;
        }
    }

    private void checkPmsLocation() {
        MPermissions.requestPermissions(this, AppConsts.PMS_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }


    @PermissionGrant(AppConsts.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        MultiImageSelector selector = MultiImageSelector.create(getContext());
        selector.showCamera(true);
        selector.count(1);
        selector.multi();
        selector.origin(mSelectPath)
                .start(getActivity(), REQUEST_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                Picasso.with(mContext).load(new File(mSelectPath.get(0))).into(ivXl);
                StringBuilder sb = new StringBuilder();
                imagsPath.clear();
                for (String p : mSelectPath) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.setThumbnailPath(p);
                    sb.append(p);
                    sb.append("\n");
                    imagsPath.add(imageItem);
                }
                upLoad();
            }
        }
    }

    /**
     * 上传文件
     */
    private void upLoad() {
        List<String> head = new ArrayList<>();
        List<File> headFile = new ArrayList<>();
        for (int i = 0; i < imagsPath.size(); i++) {
            head.add(imagsPath.get(i).getThumbnailPath());
        }
        try {
            headFile.addAll(Luban.with(getContext()).load(head).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<File>> images = new HashMap<>();
        if (!ListUtil.isEmpty(headFile))
            images.put("file", headFile);
        mOkHttpHelper.post_map_file(mContext, Url.upload, new HashMap<String, Object>(), images, new SpotsCallBack<UpLoadFileBean>(mContext) {
            @Override
            public void onSuccess(Response response, UpLoadFileBean upLoadFileBean) {
                img_path = upLoadFileBean.getData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void xlRz() {
        if (null == img_path) {
            ToastUtil.show("请选择学历信息！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("1image", img_path);
        if (null != status && status.equals("2"))
            params.put("again", "1");
        OkHttpHelper.getInstance().post(getContext(), Url.educationRz, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("提交成功");
                eventCenter.sendType(EventCenter.EventType.EVT_RZZT);
                act.finishSelf();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
