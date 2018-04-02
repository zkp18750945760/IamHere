package com.zhoukp.signer.module.activity;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/19 15:16
 * @email 275557625@qq.com
 * @function
 */

public class ActivityBean {


    /**
     * data : [{"address":"灯光篮球场","content":"篮球比赛","time":"2018-03-26 15:00:00-2018-03-31 09:00:00","type":2,"week":null}]
     * time : 2018-03-31T14:37:42
     * message : 成功获取日程表！
     * status : 100
     */

    private String time;
    private String message;
    private int status;
    /**
     * address : 灯光篮球场
     * content : 篮球比赛
     * time : 2018-03-26 15:00:00-2018-03-31 09:00:00
     * type : 2
     * week : null
     */

    private List<DataBean> data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private Integer id;
        private String address;
        private String name;
        private String time;
        private Boolean enroll;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Boolean getEnroll() {
            return enroll;
        }

        public void setEnroll(Boolean enroll) {
            this.enroll = enroll;
        }
    }
}
