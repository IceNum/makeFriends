package com.people.loveme.bean;

/**
 * Created by kxn on 2018/11/12 0012.
 */

public class TongZhiConfigBean {

    /**
     * data : {"uid":100007,"zhaohu":1,"zan":0,"like":0,"like_dongtai":0}
     * code : 1
     * msg : 获取成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * uid : 100007
         * zhaohu : 1
         * zan : 0
         * like : 0
         * like_dongtai : 0
         */

        private String uid;
        private String zhaohu;
        private String zan;
        private String like;
        private String like_dongtai;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getZhaohu() {
            return zhaohu;
        }

        public void setZhaohu(String zhaohu) {
            this.zhaohu = zhaohu;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getLike_dongtai() {
            return like_dongtai;
        }

        public void setLike_dongtai(String like_dongtai) {
            this.like_dongtai = like_dongtai;
        }
    }
}
