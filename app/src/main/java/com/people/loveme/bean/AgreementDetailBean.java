package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class AgreementDetailBean {

    /**
     * data : {"service_agreement_id":"1","title":"条款1","content":"<p>爱迪生阿斯顿撒旦撒旦手动阀电饭锅地方 发的<\/p>","add_time":"1533713461"}
     * message :
     * code : 200
     */

    private DataBean data;
    private String message;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * service_agreement_id : 1
         * title : 条款1
         * content : <p>爱迪生阿斯顿撒旦撒旦手动阀电饭锅地方 发的</p>
         * add_time : 1533713461
         */

        private String service_agreement_id;
        private String title;
        private String content;
        private String add_time;

        public String getService_agreement_id() {
            return service_agreement_id;
        }

        public void setService_agreement_id(String service_agreement_id) {
            this.service_agreement_id = service_agreement_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
