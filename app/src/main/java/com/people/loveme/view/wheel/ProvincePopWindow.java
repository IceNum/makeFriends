package com.people.loveme.view.wheel;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.people.loveme.R;
import com.people.loveme.view.wheel.adapter.ListWheelAdapter;
import com.people.loveme.view.wheel.adapter.ListWheelAdapter2;
import com.people.loveme.view.wheel.adapter.ListWheelAdapter3;
import com.people.loveme.view.wheel.wheelview.OnWheelChangedListener;
import com.people.loveme.view.wheel.wheelview.WheelView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 轮子选择器
 * Created by Administrator on 2016/11/9 0009.
 */

public class ProvincePopWindow extends PopupWindow implements OnWheelChangedListener {

    private Context context;
    private WheelView wheel1;//省
    private WheelView wheel2;//市
    private WheelView wheel3;//区

    private String province, city, county;
    private String districtId;

    private List<ProvinceBean> cityList = new ArrayList<>();
    private List<ProvinceBean.CityListBean> citiesList = new ArrayList<>();
    private List<ProvinceBean.DistrictListBean> counties = new ArrayList<>();

    public ProvincePopWindow(Context context) {
        // TODO Auto-generated constructor stub
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.wheel_region, null);

        this.context = context;

        this.setContentView(v);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(android.R.style.Animation_InputMethod);
        this.setFocusable(true);
//		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cityList = new Gson().fromJson(getJsons(context),
                new TypeToken<List<ProvinceBean>>() {
                }.getType());

        initPop(v, cityList);
    }


    //初始化Pop
    private void initPop(View viewGroup, List<ProvinceBean> cityList) {
        wheel1 = (WheelView) viewGroup.findViewById(R.id.wheel1);
        wheel2 = (WheelView) viewGroup.findViewById(R.id.wheel2);
        wheel3 = (WheelView) viewGroup.findViewById(R.id.wheel3);

        TextView tv_enter = (TextView) viewGroup.findViewById(R.id.tv_enter);
        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                pcw.saveVycle(province, city, county, districtId);
            }
        });
        TextView tv_cancel = (TextView) viewGroup.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        province=cityList.get(0).province;
        wheel1.setViewAdapter(new ListWheelAdapter(context, cityList));
        wheel1.setCurrentItem(0);
        wheel1.setCyclic(false);// 可循环滚动
        wheel1.addChangingListener(this);

        citiesList = cityList.get(0).cityList;
        city=citiesList.get(0).city;
        wheel2.setViewAdapter(new ListWheelAdapter2(context, citiesList));
        wheel2.setCurrentItem(0);
        wheel2.setCyclic(false);// 可循环滚动
        wheel2.addChangingListener(this);

        counties = cityList.get(0).cityList.get(0).districtList;
        county=counties.get(0).district;
        districtId = counties.get(0).districtId;
        wheel3.setViewAdapter(new ListWheelAdapter3(context, counties));
        wheel3.setCurrentItem(0);
        wheel3.setCyclic(false);// 可循环滚动
        wheel3.addChangingListener(this);
    }

    private int proviceIndex,cityIndex,countyIndex;


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == wheel1) {
            proviceIndex=wheel.getCurrentItem();
            cityIndex=0;
            countyIndex=0;
            citiesList = cityList.get(proviceIndex).cityList;
            wheel2.setViewAdapter(new ListWheelAdapter2(context, citiesList));
            counties = citiesList.get(0).districtList;
            wheel3.setViewAdapter(new ListWheelAdapter3(context, counties));

            wheel2.setCurrentItem(0);
            wheel3.setCurrentItem(0);

            province = cityList.get(proviceIndex).province;
            city = citiesList.get(0).city;
            county = counties.get(0).district;
            districtId = counties.get(0).districtId;
        } else if (wheel == wheel2) {
            cityIndex=wheel.getCurrentItem();
            countyIndex=0;
            counties = citiesList.get(cityIndex).districtList;
            province = cityList.get(proviceIndex).province;
            city = citiesList.get(cityIndex).city;
            county=counties.get(0).district;
            districtId = counties.get(0).districtId;
            wheel3.setViewAdapter(new ListWheelAdapter3(context, counties));
            wheel3.setCurrentItem(0);

        } else if (wheel == wheel3) {
            countyIndex=wheel.getCurrentItem();
            province = cityList.get(proviceIndex).province;
            city = citiesList.get(cityIndex).city;
            county=counties.get(countyIndex).district;
            districtId = counties.get(countyIndex).districtId;
        }
    }

    private PopInterface pcw;

    public void setOnCycleListener(PopInterface pcw) {
        this.pcw = pcw;
    }

    public interface PopInterface {
        void saveVycle(String province, String city, String county, String districtId);
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


}
