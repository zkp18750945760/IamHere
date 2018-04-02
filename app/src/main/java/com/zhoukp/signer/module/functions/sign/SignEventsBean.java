package com.zhoukp.signer.module.functions.sign;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/23 15:36
 * @email 275557625@qq.com
 * @function
 */

public class SignEventsBean {

    /**
     * data : [{"date":24,"week":"星期六","address":"C1-401","timeLag":561000,"time":"9-10节","type":1,"content":"#高等数学C(一)"}]
     * time : 2018-03-24 21:13:39
     * status : 200
     */

    private String time;
    private int status;
    /**
     * date : 24
     * week : 星期六
     * address : C1-401
     * timeLag : 561000
     * time : 9-10节
     * type : 1
     * content : #高等数学C(一)
     */

    private List<DataBean> data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        //当前日程星期
        private Integer week;
        //当前日程类型
        private Integer type;
        //当前日程地址
        private String address;
        //当前日程时间
        private String time;
        //当前日程内容
        private String content;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getWeek() {
            return week;
        }

        public void setWeek(Integer week) {
            this.week = week;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
