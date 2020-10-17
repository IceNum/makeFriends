package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/4/20 0020.
 */
public class GiftBean {

    /**
     * code : 1
     * data : [{"id":1,"image":"/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg","name":"穿云箭","money":"100","img_src":"http://39.98.246.254/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg"}]
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
         * image : /uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg
         * name : 穿云箭
         * money : 100
         * img_src : http://39.98.246.254/uploads/20190401/05d0f167f1707fd953c37a6af9fe516d.jpg
         */

        private String id;
        private String image;
        private String name;
        private String money;
        private String img_src;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getImg_src() {
            return img_src;
        }

        public void setImg_src(String img_src) {
            this.img_src = img_src;
        }
    }
}
