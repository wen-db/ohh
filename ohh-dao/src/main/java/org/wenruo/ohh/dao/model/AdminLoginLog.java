package org.wenruo.ohh.dao.model;

import java.util.Date;

public class AdminLoginLog {
    private Integer id;

    private String ip;

    private Date loginDate;

    private String msg;

    public AdminLoginLog(Integer id, String ip, Date loginDate, String msg) {
        this.id = id;
        this.ip = ip;
        this.loginDate = loginDate;
        this.msg = msg;
    }

    public AdminLoginLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}