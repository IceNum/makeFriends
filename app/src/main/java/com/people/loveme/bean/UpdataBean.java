package com.people.loveme.bean;

/**
 * Created by kxn on 2018/10/30 0030.
 */

public class UpdataBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540860389
     * data : [{"id":1,"version":"1.0","updatefile":"http://rrl.project.hbsshop.cn/uploads/20181018/66e2ff792021985475772681f2004e49.apk"}]
     */

    private int code;
    private String msg;
    private String time;
    private String version;
    private String updatefile;

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





    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdatefile() {
        return updatefile;
    }

    public void setUpdatefile(String updatefile) {
        this.updatefile = updatefile;
    }
}
