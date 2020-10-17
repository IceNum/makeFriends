package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class CoinBean {

    /**
     * code : 1
     * data : [{"id":1,"name":"套餐一","num":80,"money":100},{"id":2,"name":"套餐二","num":200,"money":2000}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 套餐一
         * num : 80
         * money : 100
         */

        private int id;
        private String name;
        private int num;
        private String money;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
