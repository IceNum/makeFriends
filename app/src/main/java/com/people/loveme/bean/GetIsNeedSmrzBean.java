package com.people.loveme.bean;

/**
 * Created by kxn on 2018/12/7 0007.
 */

public class GetIsNeedSmrzBean {

    /**
     * code : 1
     * msg : 获取成功
     * isneedpay : 1
     * money : 10
     */

    private int code;
    private String msg;
    private String isneedpay;
    private String money;

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

    public String getIsneedpay() {
        return isneedpay;
    }

    public void setIsneedpay(String isneedpay) {
        this.isneedpay = isneedpay;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
