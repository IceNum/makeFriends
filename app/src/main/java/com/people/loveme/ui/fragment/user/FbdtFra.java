package com.people.loveme.ui.fragment.user;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.adapter.GridImgAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.TopicBean;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.FeedBackGridView;
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
 * Created by kxn on 2018/9/7 0007.
 */

public class FbdtFra extends TitleFragment implements NaviRightListener {
    Unbinder unbinder;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.addImg)
    FeedBackGridView addImg;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;

    private GridImgAdapter addImgAdapter;
    private ArrayList<ArrayList<ImageItem>> images = new ArrayList<>();
    private ArrayList<GridImgAdapter> adapters = new ArrayList<>();
    private ArrayList<ImageItem> imagsPath = new ArrayList<>();
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private int currentImage = -2;
    private static final int REQUEST_IMAGE = 2;
    private String img_path, topic_id, address;
    private StringBuffer imgs = new StringBuffer();
    private List<TopicBean.DataBean> topics;
    private boolean isCheck = true;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_fabudongtai);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_fbdt, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        address = SharePrefUtil.getString(mContext, AppConsts.ADDRESS, "中国");
        tvAddress.setText(address);
        addImgAdapter = new GridImgAdapter(getActivity(), imagsPath, -1);
        addImg.setAdapter(addImgAdapter);
        addImgAdapter.setMaxSize(3);
        addImgAdapter.setAddImageListencer(new GridImgAdapter.AddImageListencer() {
            @Override
            public void addImageClicked(GridImgAdapter adapter) {
                pickImage();
            }
        });
        addImgAdapter.setDelImageListencer(new GridImgAdapter.DelImageListencer() {
            @Override
            public void delImageAtPostion(int postion, GridImgAdapter adapter) {
                currentImage = adapter.getNumber();
                if (currentImage != -1) {
                    images.get(currentImage).remove(postion);
                    adapters.get(currentImage).notifyDataSetChanged();
                } else {
                    imagsPath.remove(postion);
                    mSelectPath.remove(postion);
                    addImgAdapter.notifyDataSetChanged();
                }
            }
        });
        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck = b;
            }
        });
        getTopics();
    }

    private void setData(List<String> types) {
        final ArrayAdapter<String> myAdapter = new ArrayAdapter(getContext(), R.layout.category_spinner, types);
        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                topic_id = topics.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    /**
     * 获取话题列表
     */
    private void getTopics() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getTopic, params, new SpotsCallBack<TopicBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TopicBean topicBean) {
                if (null != topicBean.getData()) {
                    topics = topicBean.getData();
                    List<String> types = new ArrayList<>();
                    if (topics.size() > 0) {
                        for (int i = 0; i < topics.size(); i++) {
                            types.add(topics.get(i).getName());
                        }
                        topic_id = topicBean.getData().get(0).getId();
                        setData(types);
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPmsLocation();
        } else {
            pmsLocationSuccess();
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
        selector.count(3);
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
                StringBuilder sb = new StringBuilder();
                imagsPath.clear();
                for (String p : mSelectPath) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.setThumbnailPath(p);
                    sb.append(p);
                    sb.append("\n");
                    imagsPath.add(imageItem);
                }
                for (int i = 0; i < imagsPath.size(); i++) {
                    upLoad(imagsPath.get(i).getThumbnailPath());
                }
                addImgAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 上传文件
     */
    private void upLoad(String path) {
        List<String> head = new ArrayList<>();
        List<File> headFile = new ArrayList<>();
        head.add(path);
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
                imgs.append(upLoadFileBean.getData() + ",");
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    /**
     * 发布
     */
    private void push() {
        if (imgs.length() <= 0) {
            ToastUtil.show("请选择图片信息！");
            return;
        }

        img_path = imgs.substring(0, imgs.length() - 1);

        String content = etContent.getText().toString();
        if (StringUtil.isEmpty(content)) {
            ToastUtil.show("请输入动态内容！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != imagsPath)
            params.put("images", img_path);
        params.put("cateid", topic_id);
        if (isCheck)
            params.put("address", address);
        params.put("content", content);
        params.put("longitude", lng);
        params.put("latitude", lat);

        OkHttpHelper.getInstance().post(getContext(), Url.addDynamic, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("发布成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                eventCenter.sendType(EventCenter.EventType.EVT_PUSH);
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

    @Override
    public int rightText() {
        return R.string.update;
    }

    @Override
    public void onRightClicked(View v) {
        push();
    }
}
