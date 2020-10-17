package com.people.loveme.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class XlRzBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540783295
     * data : {"id":4,"uid":99887,"1image":"20181029/5d32179dbaeb5f0cc8a3d7d47574794b.jpg","2image":"","time":1540783282,"status":"0","time_text":"2018-10-29 11:21:22","status_text":"Status 0"}
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

    public static class DataBean {
        /**
         * id : 4
         * uid : 99887
         * 1image : 20181029/5d32179dbaeb5f0cc8a3d7d47574794b.jpg
         * 2image :
         * time : 1540783282
         * status : 0
         * time_text : 2018-10-29 11:21:22
         * status_text : Status 0
         */

        private int id;
        private int uid;
        @SerializedName("1image")
        private String _$1image;
        @SerializedName("2image")
        private String _$2image;
        private int time;
        private String status;
        private String time_text;
        private String status_text;

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

        public String get_$1image() {
            return _$1image;
        }

        public void set_$1image(String _$1image) {
            this._$1image = _$1image;
        }

        public String get_$2image() {
            return _$2image;
        }

        public void set_$2image(String _$2image) {
            this._$2image = _$2image;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime_text() {
            return time_text;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }
    }
}
