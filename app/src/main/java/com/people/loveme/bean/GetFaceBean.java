package com.people.loveme.bean;

/**
 * Created by kxn on 2018/11/14 0014.
 */

public class GetFaceBean {

    /**
     * code : 1
     * msg : 链接获取成功
     * data : {"url":"https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-php-20180705&amp;app_id=2018080160823914&amp;biz_content=%7B%22biz_no%22%3A%22ZM201811133000000696900412613425%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=zhima.customer.certification.certify&amp;return_url=http%3A%2F%2Frrl.project.hbsshop.cn%2Fapi%2Fother%2Frelaname&amp;sign=DAaRBM%2BBlQaTnGWKSdzN5SqBG4nJ5hYzIhOPpRez3qmlhed0vsB0Li1H6enBH5a3ZxV5cZUEsDipgqQIL2S05IqT9wsnJf3OL1Mrv2sI3enA9orAP6ZPGSmuc4qYgSO77KGsP%2FBOk69qhCm3Cwp0jQcv4EWxOWjkHgyrHoiEfC6Km9NaQbD3B7MrfTonByigfUBShd4BL3CPUt4Hpn%2F67SvJO8R53LhuWrBFnAWzq4AGqDB1QEyRjOaTY2G3AxNXbPXgIf%2BbszOvJuTFFBHdkLgEMLJF5qj2YvZ7K6ZScXyJYBXMXRLyfUTEzykLnAbRFsPDpT7xqu%2FMR9z%2BWkdtQQ%3D%3D&amp;sign_type=RSA2×tamp=2018-11-13+21%3A55%3A33&amp;version=1.0","zm_id":"ZM201811133000000696900412613425"}
     */

    private int code;
    private String msg;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-php-20180705&amp;app_id=2018080160823914&amp;biz_content=%7B%22biz_no%22%3A%22ZM201811133000000696900412613425%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=zhima.customer.certification.certify&amp;return_url=http%3A%2F%2Frrl.project.hbsshop.cn%2Fapi%2Fother%2Frelaname&amp;sign=DAaRBM%2BBlQaTnGWKSdzN5SqBG4nJ5hYzIhOPpRez3qmlhed0vsB0Li1H6enBH5a3ZxV5cZUEsDipgqQIL2S05IqT9wsnJf3OL1Mrv2sI3enA9orAP6ZPGSmuc4qYgSO77KGsP%2FBOk69qhCm3Cwp0jQcv4EWxOWjkHgyrHoiEfC6Km9NaQbD3B7MrfTonByigfUBShd4BL3CPUt4Hpn%2F67SvJO8R53LhuWrBFnAWzq4AGqDB1QEyRjOaTY2G3AxNXbPXgIf%2BbszOvJuTFFBHdkLgEMLJF5qj2YvZ7K6ZScXyJYBXMXRLyfUTEzykLnAbRFsPDpT7xqu%2FMR9z%2BWkdtQQ%3D%3D&amp;sign_type=RSA2×tamp=2018-11-13+21%3A55%3A33&amp;version=1.0
         * zm_id : ZM201811133000000696900412613425
         */

        private String url;
        private String zm_id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getZm_id() {
            return zm_id;
        }

        public void setZm_id(String zm_id) {
            this.zm_id = zm_id;
        }
    }
}
