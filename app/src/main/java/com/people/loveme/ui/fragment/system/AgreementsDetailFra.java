package com.people.loveme.ui.fragment.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.loveme.R;
import com.people.loveme.ui.fragment.TitleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class AgreementsDetailFra extends TitleFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_conten)
    TextView tvConten;

    @Override
    public String getTitleName() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_agreement_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        String title = getArguments().getString("title");
        String content = getArguments().getString("content");
        if (null != title)
            act.titleTv.setText(title);

        if (null != content)
            tvConten.setText(content);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
