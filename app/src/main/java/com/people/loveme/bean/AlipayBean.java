package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/19 0019.
 */

public class AlipayBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : alipay_sdk=alipay-sdk-php-20180705&amp;app_id=2018080260801980&amp;biz_content=%7B%22body%22%3A%22%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A%22201811050857424746%22%2C%22timeout_express%22%3A%221541396084m%22%2C%22total_amount%22%3A%2210%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2Frrl.project.hbsshop.cn%2Fapi%2Fpay%2Falipayok&amp;sign_type=RSA2&amp;timestamp=2018-11-05+13%3A34%3A44&amp;version=1.0&amp;sign=IbmtgrJk8UUSG4A0cJ9J5eBFzFt%2F6lavMiUSEVKklVK6eskoleB8ls5amtcyLEdvmvG7MrdDM1r%2FjtyiYmUsuK%2BtULf5Rd2M3KbqEcCo21sZqk0afUiBLDiznIWBnZVWRG9Qf4FYoPh7fkXn2Y4Pqat5%2FF5i%2F1E1xa8DVLUwAX6W4sjzf3WVRjtFoo65MQ%2FpcyTqs6%2BUXEuJLLlmFAQx5ZOBaT6Cu7VObyQ7Nbi2vbmcI7laS%2FRUucTLmG%2FqpN%2FfOuE%2FSaL15cDGXQyFj81sVXkOsrCogl1Bf6jFnMIaupeVkIGbwmaQAGXOMgQIvB%2FapypTyRQhYhxJK9LK6YfSWg%3D%3D
     */

    private int code;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
