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
import com.people.loveme.bean.FcRzBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.UpLoadFileBean;
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

public class FcRzFra extends TitleFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.iv_zhu)
    ImageView ivZhu;
    @BindView(R.id.iv_fu)
    ImageView ivFu;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//图片
    private ArrayList<String> mSelectPathZhu = new ArrayList<>();
    private ArrayList<String> mSelectPathFu = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private String hous_zheng_path, hous_fu_path;
    private int selectTag;
    private String status;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_fangrz);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_fcrz, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        ivZhu.setOnClickListener(this);
        ivFu.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        getInfo();
    }

    /**
     * 获取房产认证信息
     */
    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.hoursRzInfo, params, new SpotsCallBack<FcRzBean>(getContext()) {
            @Override
            public void onSuccess(Response response, FcRzBean bean) {
                if (null != bean.getData()) {
                        PicassoUtil.setImag(mContext,  bean.getData().get_$1image(), ivZhu);
                        PicassoUtil.setImag(mContext, bean.getData().get_$2image(), ivFu);
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
            case R.id.iv_zhu:
                selectTag = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.iv_fu:
                selectTag = 1;
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
                houseRz();
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
        switch (selectTag) {
            case 0:
                selector.origin(mSelectPathZhu)
                        .start(getActivity(), REQUEST_IMAGE);
                break;
            case 1:
                selector.origin(mSelectPathFu)
                        .start(getActivity(), REQUEST_IMAGE);
                break;
        }

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
                switch (selectTag) {
                    case 0:
                        mSelectPathZhu = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        Picasso.with(mContext).load(new File(mSelectPathZhu.get(0))).into(ivZhu);
                        StringBuilder sb0 = new StringBuilder();
                        imagsPath.clear();
                        for (String p : mSelectPathZhu) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setThumbnailPath(p);
                            sb0.append(p);
                            sb0.append("\n");
                            imagsPath.add(imageItem);
                        }
                        break;
                    case 1:
                        mSelectPathFu = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        Picasso.with(mContext).load(new File(mSelectPathFu.get(0))).into(ivFu);
                        StringBuilder sb1 = new StringBuilder();
                        imagsPath.clear();
                        for (String p : mSelectPathFu) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setThumbnailPath(p);
                            sb1.append(p);
                            sb1.append("\n");
                            imagsPath.add(imageItem);
                        }
                        break;
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
                switch (selectTag) {
                    case 0:
                        hous_zheng_path = upLoadFileBean.getData();
                        break;
                    case 1:
                        hous_fu_path = upLoadFileBean.getData();
                        break;
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void houseRz() {

        if (null == hous_zheng_path) {
            ToastUtil.show("请选择房产主页照！");
            return;
        }
        if (null == hous_fu_path) {
            ToastUtil.show("请选择房产副页照！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("1image", hous_zheng_path);
        params.put("2image", hous_fu_path);
        if (null != status && status.equals("2"))
            params.put("again", "1");
        OkHttpHelper.getInstance().post(getContext(), Url.hoursRz, params, new SpotsCallBack<BaseBean>(getContext()) {
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