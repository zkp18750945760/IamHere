package com.zhoukp.signer.module.login;

/**
 * @author zhoukp
 * @time 2018/3/18 13:20
 * @email 275557625@qq.com
 * @function
 */

public class LoginBean {

    /**
     * time : 2018-03-18 13:23:51
     * user : {"userPassword":"bsZsfaFzPXMh8nPVCBWT4A==","userClass":"2班","userGrade":"2014级","userDuty":"学生","userMajor":"软件工程","userName":"何凌轲","userId":"1425122012"}
     * status : 200
     */

    private String time;
    /**
     * userPassword : bsZsfaFzPXMh8nPVCBWT4A==
     * userClass : 2班
     * userGrade : 2014级
     * userDuty : 学生
     * userMajor : 软件工程
     * userName : 何凌轲
     * userId : 1425122012
     */

    private UserBean data;
    private int status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class UserBean {
        private String userClass;
        private String userCollege;
        private String userDuty;
        private String userName;
        private Integer userId;
        private Integer userNo;
        private Integer userGrade;
        private String userHead;

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getUserClass() {
            return userClass;
        }

        public void setUserClass(String userClass) {
            this.userClass = userClass;
        }

        public String getUserCollege() {
            return userCollege;
        }

        public void setUserCollege(String userCollege) {
            this.userCollege = userCollege;
        }

        public String getUserDuty() {
            return userDuty;
        }

        public void setUserDuty(String userDuty) {
            this.userDuty = userDuty;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getUserNo() {
            return userNo;
        }

        public void setUserNo(Integer userNo) {
            this.userNo = userNo;
        }

        public Integer getUserGrade() {
            return userGrade;
        }

        public void setUserGrade(Integer userGrade) {
            this.userGrade = userGrade;
        }
    }
}
