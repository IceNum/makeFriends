package com.people.loveme.ui.fragment.login;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.bean.ImageItem;
import com.people.loveme.bean.UpLoadFileBean;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.http.OkHttpHelper;
import com.people.loveme.http.SpotsCallBack;
import com.people.loveme.http.Url;
import com.people.loveme.ui.activity.MainActivity;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.WebFra;
import com.people.loveme.utils.DateUtil;
import com.people.loveme.utils.ListUtil;
import com.people.loveme.utils.PhotoUtils;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.people.loveme.view.CircleImageView;
import com.people.loveme.view.CityChooseDialog;
import com.people.loveme.view.SingleChooseDialog;
import com.squareup.picasso.Picasso;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
 * Created by kxn on 2018/10/26 0026.
 * 注册后完善个人资料
 */

public class CompleteInformationFra extends TitleFragment implements View.OnClickListener {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_sg)
    TextView tvSg;
    @BindView(R.id.ll_sg)
    LinearLayout llSg;
    @BindView(R.id.tv_ysr)
    TextView tvYsr;
    @BindView(R.id.ll_ysr)
    LinearLayout llYsr;
    @BindView(R.id.tv_xl)
    TextView tvXl;
    @BindView(R.id.ll_xl)
    LinearLayout llXl;
    @BindView(R.id.et_inviteId)
    EditText etInviteId;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    Unbinder unbinder;

    private ArrayList<ImageItem> imagsPath = new ArrayList<>();//头像
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;

    private String nickname, sex, birthday, city, headimage, height, income, edu;

    private static final int REQUEST_CROP = 5;
    Uri imageUri, cropImageUri;
    File fileUri;
    private String headPath;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_completainfo, container, false);
        act.hindNaviBar();
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        headPath = Environment.getExternalStorageDirectory().getPath() + "/ "+ System.currentTimeMillis()+".jpg";
        fileUri = new File(headPath);
        imageUri = Uri.fromFile(fileUri);
        ivHead.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llBirthday.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llSg.setOnClickListener(this);
        llYsr.setOnClickListener(this);
        llXl.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvXieyi.setOnClickListener(this);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StyledDialog.init(mContext);
                StyledDialog.buildIosAlert("提示", "不完善资料无法使用程序，是否退出？", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                    }

                    @Override
                    public void onSecond() {
                        act.finishSelf();
                    }
                }).setBtnColor(R.color.txt_lv2, R.color.txt_lv5, 0).setBtnText("再想想", "退出").show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        ActivitySwitcher.start(act,MainActivity.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPmsLocation();
                } else {
                    pmsLocationSuccess();
                }
                break;
            case R.id.ll_sex:
                final List<String> sexs = new ArrayList<>();
                sexs.add(mContext.getString(R.string.wo_man));
                sexs.add(mContext.getString(R.string.wo_wom));
                StyledDialog.buildBottomItemDialog(sexs, mContext.getString(R.string.wo_quxiao), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        tvSex.setText(sexs.get(position));
                        sex = (position + 1) + "";
                    }

                    @Override
                    public void onBottomBtnClick() {
                    }
                })
                        .show();
                break;
            case R.id.ll_birthday:
                TimePickerView startTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String birth = DateUtil.formatDate(date.getTime(), "yyyy-MM-dd");
                        tvBirthday.setText(birth);
                        birthday = birth;
                    }
                })
                        .setCancelColor(R.color.txt_lv1)//取消按钮文字颜色
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build();
                startTime.show();
                break;
            case R.id.ll_address:
                CityChooseDialog cityChooseDialog = new CityChooseDialog(mContext, mContext.getString(R.string.wo_buxian), new CityChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(String province, String City) {
                        tvAddress.setText(province + " " + City);
                        city = province + City;
                    }
                },false);
                cityChooseDialog.show();
                break;
            case R.id.ll_sg:
                final List<String> sg = Arrays.asList(getResources().getStringArray(R.array.shengao));
                SingleChooseDialog sgChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_shengao), sg, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvSg.setText(sg.get(position) + "cm");
                        height = sg.get(position);
                    }
                });
                sgChooseDialog.show();
                break;
            case R.id.ll_ysr:
                final List<String> sr = Arrays.asList(getResources().getStringArray(R.array.sr));
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
                        tvYsr.setText(sr.get(position));
                    }
                });
                srChooseDialog.show();
                break;
            case R.id.ll_xl:
                final List<String> xl = Arrays.asList(getResources().getStringArray(R.array.xl));
                SingleChooseDialog slChooseDialog = new SingleChooseDialog(mContext, mContext.getString(R.string.wo_xueli), xl, new SingleChooseDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        tvXl.setText(xl.get(position));
                        edu = xl.get(position);
                    }
                });
                slChooseDialog.show();
                break;
            case R.id.tv_register:
                save();
//                if (null != headimage)
//                    saveImage(headimage);
                break;
            case R.id.tv_xieyi:
                Bundle bundle = new Bundle();
                bundle.putString("url", Url.REGISTER);
                bundle.putString("title", "注册协议");
                ActivitySwitcher.startFragment(act, WebFra.class, bundle);
                break;

        }
    }

    /**
     * 保存信息
     */
    private void save() {
        nickname = etName.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("uid", userId);
        if (null != headimage)
            params.put("headimage", headimage);//头像图片
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (!StringUtil.isEmpty(nickname))
            params.put("nickname", nickname);//昵称
        else {
            ToastUtil.show(getString(R.string.wo_nichengbuweikong));
            return;
        }
        if (null != sex)
            params.put("sex", sex);//性别 1 男 2女
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (null != birthday)
            params.put("birthday", birthday);//生日
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }

        if (null != city)
            params.put("city", city);//城市
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (null != height)
            params.put("height", height);//身高
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (null != income)
            params.put("income", income);//收入
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (null != edu)
            params.put("edu", edu);//学历
        else {
            ToastUtil.show(getString(R.string.wo_qingxuanze));
            return;
        }
        if (!StringUtil.isEmpty(etInviteId.getText().toString()))
        params.put("puid",etInviteId.getText().toString());

        OkHttpHelper.getInstance().post(getContext(), Url.ziliao, params, new SpotsCallBack<BaseBean>(getContext()) {
            @Override
            public void onSuccess(Response response, BaseBean bean) {
                act.finishSelf();
                SharePrefUtil.saveBoolean(act, AppConsts.ISCOMPLETE, true);
                SharePrefUtil.saveString(act, AppConsts.NAME,nickname);
                SharePrefUtil.saveString(act, AppConsts.HEAD,headimage);
                ActivitySwitcher.start(act, MainActivity.class);
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
                headimage = upLoadFileBean.getData();
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

}
