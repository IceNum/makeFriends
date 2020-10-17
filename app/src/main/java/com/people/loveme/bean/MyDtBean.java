package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class MyDtBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540804102
     * data : [{"id":6,"uid":99887,"cateid":3,"content":"动态测试","images":"http://rrl.project.hbsshop.cn/uploads/20181029/611edd13db50b36f9cd78a939e418799.jpg","time":null,"latitude":"34.749394","longitude":"113.734195","zan":0,"address":"河南省郑州市金水区宏昌街29号靠近居然之家(郑州店)","time_text":""}]
     */

    private int code;
    private String msg;
    private String time;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * uid : 99887
         * cateid : 3
         * content : 动态测试
         * images : http://rrl.project.hbsshop.cn/uploads/20181029/611edd13db50b36f9cd78a939e418799.jpg
         * time : null
         * latitude : 34.749394
         * longitude : 113.734195
         * zan : 0
         * address : 河南省郑州市金水区宏昌街29号靠近居然之家(郑州店)
         * time_text :
         */

        private String id;
        private int uid;
        private int cateid;
        private String content;
        private String images;
        private String time;
        private String latitude;
        private String longitude;
        private String zan;
        private String address;
        private String time_text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getCateid() {
            return cateid;
        }

        public void setCateid(int cateid) {
            this.cateid = cateid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime_text() {
            return time_text;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }
    }
}
