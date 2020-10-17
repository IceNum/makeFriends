package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/20 0020.
 */

public class PhotoListBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540791630
     * data : [{"id":3,"uid":99887,"image":"http://rrl.project.hbsshop.cn/uploads/20181029/04779f4e1ed5c3dbd7a13f1136288464.jpg"}]
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
         * id : 3
         * uid : 99887
         * image : http://rrl.project.hbsshop.cn/uploads/20181029/04779f4e1ed5c3dbd7a13f1136288464.jpg
         */

        private int id;
        private int uid;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
