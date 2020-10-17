package com.people.loveme.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kxn on 2018/9/26 0026.
 */

public class CreditScoreBean implements Serializable {


    /**
     * code : 1
     * msg : 查询成功
     * time : 1540862801
     * data : {"uid":"99887","score":45,"list":[]}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * uid : 99887
         * score : 45
         * list : []
         */

        private String uid;
        private String score;
        private List<ListBean> list;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public class ListBean implements Serializable{
            private String id;
            private String uid;
            private String time;
            private String detail;
            private String add_score;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getAdd_score() {
                return add_score;
            }

            public void setAdd_score(String add_score) {
                this.add_score = add_score;
            }
        }
    }
}
