package com.people.loveme.ui.fragment.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.adapter.ImageAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.PhotoListBean;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.ToastUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Request;
import okhttp3.Response;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kxn on 2018/9/20 0020.
 */

public class XiangceFra extends TitleFragment implements NaviRightListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.bgRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private boolean isMore = true;
    private List<PhotoListBean.DataBean> list;
    private List<String> images;
    private ImageAdapter adapter;

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    boolean isEdit = false;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_xiangce);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_xiangce, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mRefreshLayout.setPullDownRefreshEnable(true);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        list = new ArrayList<>();
        images = new ArrayList<>();
        adapter = new ImageAdapter(mContext, images);
        adapter.setDeletePhotoListener(new ImageAdapter.DeletePhoto() {
            @Override
            public void deletePhoto(int positon) {
                deletImage(positon);
            }
        });
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) { //上传图片
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPmsLocation();
                    } else {
                        pmsLocationSuccess();
                    }
                } else {//查看大图
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, i - 1);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }
            }
        });
        getPhotos();
    }

    private void getPhotos() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        mOkHttpHelper.post(mContext, Url.photoList, params, new SpotsCallBack<PhotoListBean>(mContext) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onSuccess(Response response, PhotoListBean bean) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                list.clear();
                if (!ListUtil.isEmpty(bean.getData())) {
                    list.addAll(bean.getData());
                    images.clear();
                    imageInfo.clear();
                    for (int i = 0; i < list.size(); i++) {
                        images.add(list.get(i).getImage());
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(list.get(i).getImage());
                        info.setBigImageUrl(list.get(i).getImage());
                        imageInfo.add(info);
                    }
                    adapter.notifyDataSetChanged();
                } else
                    isMore = false;
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }
        });
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
        mSelectPath.clear();
        //权限授权成功
        MultiImageSelector selector = MultiImageSelector.create(getContext());
        selector.showCamera(true);
        selector.count(9);
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
                if (null != upLoadFileBean.getData())
                    saveImage(upLoadFileBean.getData());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    /**
     * 保存相册
     *
     * @param img_path
     */
    private void saveImage(String img_path) {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("image", img_path);
        mOkHttpHelper.post(mContext, Url.addPhoto, params, new SpotsCallBack<BaseBean>(mContext) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("上传成功！");
                getPhotos();
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void deletImage(final int positon) {
        final Map<String, String> params = new HashMap<>();
        params.put("ids", list.get(positon - 1).getId() + "");
        mOkHttpHelper.post(mContext, Url.delPhoto, params, new SpotsCallBack<BaseBean>(mContext) {

            @Override
            public void onSuccess(Response response, BaseBean baseBean) {
                ToastUtil.show("删除成功！");
                images.remove(positon-1);
                list.remove(positon-1);
                adapter.notifyDataSetChanged();
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public int rightText() {
        return R.string.edit;
    }

    @Override
    public void onRightClicked(View v) {
        isEdit = !isEdit;
        if (isEdit)
            act.right.setText("完成");
        else
            act.right.setText("编辑");
        adapter.setEdit(isEdit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getPhotos();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
