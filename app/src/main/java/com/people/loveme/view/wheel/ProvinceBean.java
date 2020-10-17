package com.people.loveme.view.wheel;

import java.util.List;

/**
 * Created by Slingge on 2017/9/6 0006.
 */

public class ProvinceBean {

    public String province;
    public String provinceId;
    public List<CityListBean> cityList;

    public class CityListBean {
        public String city;
        public String cityId;

        public CityListBean(String city) {
            this.city = city;
        }

        public List<DistrictListBean> districtList;
    }

    public class DistrictListBean {
        public String district;
        public String districtId;
    }
}
