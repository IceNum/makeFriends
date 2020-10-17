package com.people.loveme.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.people.loveme.R;
import com.people.loveme.view.wheel.ProvinceBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kxn on 2018/10/25 0025.
 */

public class CityChooseDialog extends Dialog {
    TextView tvTitle;
    WheelView wheelViewProvince, wheelViewCity;
    TextView tvCancle;
    TextView tvSure;
    private Context context;
    private List<String> list;
    private String title;
    OnItemClick onItemClick;

    private List<ProvinceBean> cityList = new ArrayList<>();
    private List<ProvinceBean.CityListBean> citiesList = new ArrayList<>();
    private String province, city;
    private List<String> stringProvinceList, stringCityList;
    private boolean isSx = false;

    public CityChooseDialog(Context context, String title, OnItemClick onItemClick, boolean isSx) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.list = list;
        this.title = title;
        this.onItemClick = onItemClick;
        this.isSx = isSx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_city_choose, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        stringCityList = new ArrayList<>();
        stringProvinceList = new ArrayList<>();
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        wheelViewProvince = (WheelView) view.findViewById(R.id.wheelViewProvince);
        wheelViewCity = (WheelView) view.findViewById(R.id.wheelViewCity);
        wheelViewCity.setTextSize(16);
        wheelViewProvince.setTextSize(16);
        wheelViewProvince.setCyclic(false);
        wheelViewCity.setCyclic(false);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        tvSure = (TextView) view.findViewById(R.id.tv_sure);

        tvTitle.setText(title);

        cityList = new Gson().fromJson(getJsons(context),
                new TypeToken<List<ProvinceBean>>() {
                }.getType());

        province = cityList.get(0).province;
        for (int i = 0; i < cityList.size(); i++) {
            stringProvinceList.add(cityList.get(i).province);
        }
        wheelViewProvince.setAdapter(new ArrayWheelAdapter(stringProvinceList));
        wheelViewProvince.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                province = cityList.get(index).province;
                citiesList.clear();
                if (cityList.get(index).cityList.size() > 1 && isSx)
                    citiesList.add(new ProvinceBean().new CityListBean(context.getString(R.string.wo_buxian)));
                citiesList.addAll(cityList.get(index).cityList);
                stringCityList.clear();
                for (int i = 0; i < citiesList.size(); i++) {
                    stringCityList.add(citiesList.get(i).city);
                }

                if (isSx)
                    city = "";
                else
                    city = citiesList.get(0).city;

                wheelViewCity.setAdapter(new ArrayWheelAdapter(stringCityList));
            }
        });

        wheelViewProvince.setCurrentItem(0);

        citiesList.addAll(cityList.get(0).cityList);
        for (int i = 0; i < citiesList.size(); i++) {
            stringCityList.add(citiesList.get(i).city);
        }
        if (isSx)
            city = "";
        else
            city = cityList.get(0).cityList.get(0).city;

        wheelViewCity.setAdapter(new ArrayWheelAdapter(stringCityList));
        wheelViewCity.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (isSx && index != 0)
                    city = citiesList.get(index).city;

                if (!isSx)
                    city = citiesList.get(index).city;
            }
        });


        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(province, city);
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    public static String getJsons(Context context) {
        String text = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.province);
            text = readTextFromSDcard(is);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public interface OnItemClick {
        void onItemClick(String province, String city);
    }


}
