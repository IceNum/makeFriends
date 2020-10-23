package com.people.loveme.ui.fragment.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.people.loveme.AppConsts;
import com.people.loveme.HcbApp;
import com.people.loveme.R;
import com.people.loveme.actlink.NaviRightListener;
import com.people.loveme.adapter.TagAdapter;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.GetUserInfoBean;
import com.people.loveme.bean.HeadShBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.TagsBean;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.biz.EventCenter;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PhotoUtils;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.MyGridView;
import com.people.loveme.view.SingleChooseDialog;
import com.people.loveme.view.wheel.ProvincePopWindow;
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
import okhttp3.Request;
import okhttp3.Response;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kxn on 2018/7/10 0010.
 */

public class UserInfoFra extends TitleFragment implements View.OnClickListener, NaviRightListener, ProvincePopWindow.PopInterface, PopupWindow.OnDismissListener {
    Unbinder unbinder;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.gv_tag)
    MyGridView gvTag;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_jbzl)
    LinearLayout llJbzl;
    @BindView(R.id.ll_xxzl)
    LinearLayout llXxzl;
    @BindView(R.id.ll_zytj)
    LinearLayout llZytj;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_signature)
    TextView etSignature;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.ll_age)
    LinearLayout llAge;
    @BindView(R.id.shadowView)
    View shadowView;
    @BindView(R.id.tv_shz)
    TextView tvShz;

    private List<TagsBean.DataBean> tags;
    private List<String> select;
    private TagAdapter tagAdapter;

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//头像
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private static final int REQUEST_CROP = 5;
    private String face, nickname, sex, userCity, signature, user_tag = "", age;
    private List<TagsBean.DataBean> tagLists;
    ProvincePopWindow cityPopWindow;
    Uri imageUri, cropImageUri;
    File fileUri;
    private String headPath;
    private String bq;//用户标签
    private boolean isSh = false;

    @Override
    public String getTitleName() {
        return HcbApp.self.getString(R.string.wo_gerenxinxi);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_userinfo, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        tags = new ArrayList<>();
        select = new ArrayList<>();
        tagLists = new ArrayList<>();
        tagAdapter = new TagAdapter(mContext, tags, select);
        gvTag.setAdapter(tagAdapter);
        gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (select.contains(tags.get(i).getId())) {
                    select.remove(tags.get(i).getId());
                    tagAdapter.notifyDataSetChanged();
                    return;
                }
                if (select.size() < 3)
                    select.add(tags.get(i).getId());
                else
                    ToastUtil.show("标签最多只能选三个");
                tagAdapter.notifyDataSetChanged();
            }
        });
        llHead.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llJbzl.setOnClickListener(this);
        llXxzl.setOnClickListener(this);
        llZytj.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llAge.setOnClickListener(this);
        llName.setOnClickListener(this);


       isShenhe();
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
                    if (!StringUtil.isEmpty(bean.getData().getHeadimage()) && !isSh)
                        Picasso.with(getContext()).load(bean.getData().getHeadimage()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);
                    else if (!isSh)
                        Picasso.with(getContext()).load(R.mipmap.ic_logo).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

                    if (!StringUtil.isEmpty(bean.getData().getAge()) && !bean.getData().getAge().equals("null"))
                        tvAge.setText(bean.getData().getAge() + "岁");
                    if (!StringUtil.isEmpty(bean.getData().getNickname()))
                        etName.setText(bean.getData().getNickname());
                    if (!StringUtil.isEmpty(bean.getData().getSex())) {
                        if (bean.getData().getSex().equals("1"))
                            tvSex.setText(getString(R.string.male));
                        else
                            tvSex.setText(getString(R.string.female));
                    }
                    if (!StringUtil.isEmpty(bean.getData().getCity()))
                        tvCity.setText(bean.getData().getCity());
                    if (!StringUtil.isEmpty(bean.getData().getDubai()))
                        etSignature.setText(bean.getData().getDubai());

                    if (!StringUtil.isEmpty(bean.getData().getBiaoqian())) {
                        bq = bean.getData().getBiaoqian();

                    }

                    getTags();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     * 是否有审核中的头像
     */
    private void isShenhe() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        OkHttpHelper.getInstance().post(getContext(), Url.isShenhe, params, new SpotsCallBack<HeadShBean>(getContext()) {
            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                getInfo();
            }

            @Override
            public void onSuccess(Response response, final HeadShBean shBean) {
                if (shBean.getCode() == 0 && null != shBean.getSrc()) {
                    isSh = true;
                    shadowView.setVisibility(View.VISIBLE);
                    tvShz.setVisibility(View.VISIBLE);
                    tvShz.setText(getString(R.string.wo_shenhezhong));
                    Picasso.with(getContext()).load(shBean.getSrc()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);
                    if (!StringUtil.isEmpty(shBean.getSrc()))
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, shBean.getSrc());
                } else if (shBean.getCode() == 2 && null != shBean.getSrc()) {
                    isSh = true;
                    shadowView.setVisibility(View.VISIBLE);
                    tvShz.setVisibility(View.VISIBLE);
                    tvShz.setText("被拒绝");
                    if (!StringUtil.isEmpty(shBean.getSrc()))
                        SharePrefUtil.saveString(getContext(), AppConsts.HEAD, shBean.getSrc());
                    Picasso.with(getContext()).load(shBean.getSrc()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

                } else {
                    shadowView.setVisibility(View.GONE);
                    tvShz.setVisibility(View.GONE);
                }

                getInfo();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show("获取审核失败");

                getInfo();
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
            case R.id.ll_name:
                new CircleDialog.Builder(act)
                        //添加标题，参考普通对话框
                        .setTitle(getString(R.string.wo_tianxiedubai))
                        .setInputText(etSignature.getText().toString())//输入框默认文本
                        .setInputHint(getString(R.string.wo_tianxiedubai))//提示
                        .setInputCounterColor(getResources().getColor(R.color.txt_lv4))//最大字符数文字的颜色值
                        .autoInputShowKeyboard()//自动弹出键盘
                        .setPositiveInput(mContext.getString(R.string.confirm), new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {
                                etSignature.setText(text);
                            }
                        })
                        //添加取消按钮，参考普通对话框
                        .show();
                break;
            case R.id.ll_jbzl:
                ActivitySwitcher.startFragment(act, UserJbzlFra.class);
                break;
            case R.id.ll_head:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.ll_sex:
                final List<String> sexs = new ArrayList<>();
                sexs.add(mContext.getString(R.string.male));
                sexs.add(mContext.getString(R.string.female));
                StyledDialog.buildBottomItemDialog(sexs, mContext.getString(R.string.cancel), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        tvSex.setText(sexs.get(position));
                        sex = (position + 1) + "";
                    }

                    @Override
                    public void onBottomBtnClick() {
                    }
                })
                        //.setTitle("人生若只如初见")
                        .show();
                break;
            case R.id.ll_xxzl:
                ActivitySwitcher.startFragment(act, UserXxzlFra.class);
                break;
            case R.id.ll_zytj:
                ActivitySwitcher.startFragment(act, UserZytjFra.class);
                break;
            case R.id.ll_address:
                CityChooseDialog cityChooseDialog = new CityChooseDialog(mContext, mContext.getString(R.string.wo_buxian), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String city) {
                        userCity = province + city;
                        tvCity.setText(city);
                    }
                }, false);
                cityChooseDialog.show();
//                if (cityPopWindow == null) {
//                    cityPopWindow = new ProvincePopWindow(getContext());
//                }
//                cityPopWindow.setOnCycleListener(this);
//                cityPopWindow.setOnDismissListener(this);
//                if (!cityPopWindow.isShowing()) {
//                    cityPopWindow.showAtLocation(llAddress, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
//                }
                break;
            case R.id.ll_age:
                final List<String> ages = new ArrayList<>();
                for (int i = 18; i < 61; i++) {
                    ages.add(i + "");
                }
                SingleChooseDialog hyChooseDialog = new SingleChooseDialog(mContext, getString(R.string.wo_buxian), ages, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        age = ages.get(position);
                        tvAge.setText(age);
                    }
                });
                hyChooseDialog.show();
                break;
        }
    }

    /**
     * 保存信息
     */
    private void save() {
        user_tag = "";
        nickname = etName.getText().toString();
        signature = etSignature.getText().toString();
        for (int i = 0; i < select.size(); i++) {
            if (i < select.size() - 1)
                user_tag += select.get(i) + ",";
            else
                user_tag += select.get(i);
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != face)
            params.put("headimage", face);//头像图片
        if (null != nickname)
            params.put("nickname", nickname);//昵称
        if (null != sex)
            params.put("sex", sex);//性别 1 男 2女
        if (null != age)
            params.put("age", age);
        if (null != userCity)
            params.put("city", userCity);//城市id
        if (null != signature)
            params.put("dubai", signature);//签名
        if (null != user_tag)
            params.put("biaoqian", user_tag);//用户标签 , 分割

        OkHttpHelper.getInstance().post(getContext(), Url.saveUserInfo, params, new SpotsCallBack<BaseBean>(getContext()) {

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
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    /**
     * 发布
     */
    private void push(String img_path) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        params.put("images", img_path);
        params.put("content", "更新了头像。");
        params.put("longitude", lng);
        params.put("latitude", lat);

        OkHttpHelper.getInstance().post(getContext(), Url.addDynamic, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {
                ToastUtil.show("发布成功！");
                eventCenter.sendType(EventCenter.EventType.EVT_EDITEINFO);
                act.finishSelf();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
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
        headPath = Environment.getExternalStorageDirectory().getPath() + "/ " + System.currentTimeMillis() + ".jpg";
        fileUri = new File(headPath);
        imageUri = Uri.fromFile(fileUri);
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
                Picasso.with(mContext).load(new File(mSelectPath.get(0))).into(ivHead);
                StringBuilder sb = new StringBuilder();
                imagsPath.clear();
                for (String p : mSelectPath) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.setThumbnailPath(p);
                    sb.append(p);
                    sb.append("\n");
                    imagsPath.add(imageItem);
                }


                int output_X = 480, output_Y = 480;
                cropImageUri = Uri.fromFile(new File(mSelectPath.get(0)));
                PhotoUtils.cropImageUri(getActivity(), cropImageUri, imageUri, 1, 1, output_X, output_Y, REQUEST_CROP);
            }
        }

        if (requestCode == REQUEST_CROP) {
            Bitmap bitmap = PhotoUtils.getBitmapFromUri(imageUri, mContext);
            if (bitmap != null) {
                ivHead.setImageBitmap(bitmap);
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
        head.add(headPath);
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
                face = upLoadFileBean.getData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    /**
     * 用户标签
     */
    private void getTags() {
        Map<String, String> params = new HashMap<>();
        OkHttpHelper.getInstance().post(getContext(), Url.getTags, params, new SpotsCallBack<TagsBean>(getContext()) {
            @Override
            public void onSuccess(Response response, TagsBean bean) {
                tagLists.addAll(bean.getData());
                for (int i = 0; i < tagLists.size(); i++) {
                    tags.add(tagLists.get(i));
                }
                tagAdapter.notifyDataSetChanged();

                if (null != bq) {
                    String[] userTags = bq.split(",");
                    for (int i = 0; i < userTags.length; i++) {
                        for (int j = 0; j < tags.size(); j++) {
                            if (userTags[i].equals(tags.get(j).getId()))
                                select.add(userTags[i]);
                        }
                    }
                    tagAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    @Override
    public int rightText() {
        return R.string.save;
    }

    @Override
    public void onRightClicked(View v) {
        if (null != face) {
            new CircleDialog.Builder(act)
                    .setTitle("提示")
                    .setText("头像需审核通过后，方可使用！")//内容
                    .setPositive(mContext.getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            save();
                        }
                    })
                    .show();
        } else
            save();
    }

    @Override
    public void saveVycle(String province, String city, String county, String districtId) {
        city = province + city;
        tvCity.setText(province + city);
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
}

