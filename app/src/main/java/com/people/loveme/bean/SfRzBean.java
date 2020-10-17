package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class SfRzBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540781402
     * data : {"id":3,"relaname":"酷酷","idcard":"10086","topimage":"20181029/47cd477974cfa3bad804dcb43628465f.jpg","bottomimage":"20181029/8640f0a1939e70f666410936713a9483.jpg","handimage":"20181029/8d3fae1ad9f14c92d4cf701cbe41ab85.jpg","time":1540781389,"status":"0","uid":99887,"time_text":"2018-10-29 10:49:49","status_text":"Status 0"}
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
         * id : 3
         * relaname : 酷酷
         * idcard : 10086
         * topimage : 20181029/47cd477974cfa3bad804dcb43628465f.jpg
         * bottomimage : 20181029/8640f0a1939e70f666410936713a9483.jpg
         * handimage : 20181029/8d3fae1ad9f14c92d4cf701cbe41ab85.jpg
         * time : 1540781389
         * status : 0
         * uid : 99887
         * time_text : 2018-10-29 10:49:49
         * status_text : Status 0
         */

        private int id;
        private String relaname;
        private String idcard;
        private String topimage;
        private String bottomimage;
        private String handimage;
        private int time;
        private String status;
        private int uid;
        private String time_text;
        private String status_text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRelaname() {
            return relaname;
        }

        public void setRelaname(String relaname) {
            this.relaname = relaname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getTopimage() {
            return topimage;
        }

        public void setTopimage(String topimage) {
            this.topimage = topimage;
        }

        public String getBottomimage() {
            return bottomimage;
        }

        public void setBottomimage(String bottomimage) {
            this.bottomimage = bottomimage;
        }

        public String getHandimage() {
            return handimage;
        }

        public void setHandimage(String handimage) {
            this.handimage = handimage;
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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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
