package com.people.loveme.ui.fragment.scan;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.people.loveme.R;
import com.people.loveme.biz.ActivitySwitcher;
import com.people.loveme.ui.fragment.TitleFragment;
import com.people.loveme.ui.fragment.other.OtherHomePageFra;
import com.people.loveme.utils.ToastUtil;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/8/18 0018.
 * 扫一扫
 */

public class ScanFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_scan, container, false);
        act.hindNaviBar();
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mScannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                act.finishSelf();
                try {
                    Integer.parseInt(rawResult.getText().split("uid=")[1]);
                    Bundle bundle = new Bundle();
                    bundle.putString("uid",rawResult.getText().split("uid=")[1]);
                    ActivitySwitcher.startFragment(act, OtherHomePageFra.class,bundle);
                }catch (Exception e){
                    ToastUtil.show("该二维码与本软件无关，请重新扫描");
                }


            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.finishSelf();
            }
        });
    }

    @Override
    public void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
