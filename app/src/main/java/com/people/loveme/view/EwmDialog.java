package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.people.loveme.AppConsts;
import com.people.loveme.R;
import com.people.loveme.http.Url;
import com.people.loveme.utils.DisplayUtil;
import com.people.loveme.utils.QRCode;
import com.people.loveme.utils.SharePrefUtil;
import com.people.loveme.utils.StringUtil;
import com.people.loveme.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


/**
 * Created by kxn on 2018/9/26 0026.
 * 二维码弹窗
 */

public class EwmDialog extends Dialog {
    CircleImageView ivHead;
    TextView tvName;
    TextView tvJf;
    ImageView ivEwm;
    ImageView ivClose;
    private Context context;
    String ewm, score, name, head;//用户二维码和用户信用分 昵称和头像

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ivEwm.setImageBitmap(QRCode.createQRCodeWithLogo5(ewm, DisplayUtil.dip2px(context, 180), ((Bitmap) msg.getData().getParcelable("bitmap"))));
        }
    };

    public EwmDialog(Context context, String ewm, String score, String name, String head) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.ewm = Url.THE_SERVER_URL + "/html/zhuanti3.html?uid=" + ewm;
        this.score = score;
        this.name = name;
        this.head = SharePrefUtil.getString(context,AppConsts.HEAD,"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_ewm, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(view);
                return false;
            }
        });


        ivHead = ((CircleImageView) view.findViewById(R.id.iv_head));
        ivClose = ((ImageView) view.findViewById(R.id.iv_close));
        tvJf = ((TextView) view.findViewById(R.id.tv_jf));
        ivEwm = ((ImageView) view.findViewById(R.id.iv_ewm));
        tvName = ((TextView) view.findViewById(R.id.tv_name));

        if (!StringUtil.isEmpty(head)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = Picasso.with(context).load(head).get();
                        Message message = new Message();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bitmap", bitmap);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_logo);
            Message message = new Message();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putParcelable("bitmap", bitmap);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }

        tvName.setText(name);
        tvJf.setText(score + "分");
        if (!StringUtil.isEmpty(head))
            Picasso.with(getContext()).load(head).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);
        else
            Picasso.with(getContext()).load(R.mipmap.ic_logo).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽带度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }

    private void showDialog(final View view) {
        ActionDialog callDialog = new ActionDialog(context, "是否要保存图片到本地？", context.getString(R.string.wo_quxiao), context.getString(R.string.wo_queding));
        callDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                viewSaveToImage(view);
            }
        });
        callDialog.show();
    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);
        saveBitmap(cachebmp, Calendar.getInstance().getTimeInMillis() + ".png");
        view.destroyDrawingCache();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    /*
     * 保存文件，文件名为当前日期
     */
    public void saveBitmap(Bitmap bitmap, String bitName) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
// 插入图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);
                ToastUtil.show("保存成功！");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));

    }
}


