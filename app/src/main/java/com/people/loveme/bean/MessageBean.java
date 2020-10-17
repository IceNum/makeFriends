package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/1/15 0015.
 */

public class MessageBean {

    /**
     * data : [{"url":"http://rrl.project.hbsshop.cn/index/index/tongzhi?id=22&uid=100082","title":null,"is_view":1},{"url":"http://rrl.project.hbsshop.cn/index/index/tongzhi?id=23&uid=100082","title":"12312312","is_view":1},{"url":"http://rrl.project.hbsshop.cn/index/index/tongzhi?id=24&uid=100082","title":"测试一下","is_view":1}]
     * num : 0
     */

    private int num;
    private List<DataBean> data;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://rrl.project.hbsshop.cn/index/index/tongzhi?id=22&uid=100082
         * title : null
         * is_view : 1
         */

        private String url;
        private String title;
        private int is_view;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_view() {
            return is_view;
        }

        public void setIs_view(int is_view) {
            this.is_view = is_view;
        }
    }
}
