package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class HomeBannerBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540880578
     * data : [{"id":1,"image":"http://rrl.project.hbsshop.cn/uploads/20181019/26154e70bb7307cfefab3f58f68dd56d.gif","url":"http://baidu.com"}]
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
         * id : 1
         * image : http://rrl.project.hbsshop.cn/uploads/20181019/26154e70bb7307cfefab3f58f68dd56d.gif
         * url : http://baidu.com
         */

        private int id;
        private String image;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
