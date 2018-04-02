package com.zhoukp.signer.module.me;

/**
 * @author zhoukp
 * @time 2018/3/18 15:15
 * @email 275557625@qq.com
 * @function
 */

public class UploadHeadIconBean {

    /**
     * status : 200
     * headIconUrl : http://192.168.1.1:8080/1425122042.png
     */

    private int status;
    private String time;
    private String data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
