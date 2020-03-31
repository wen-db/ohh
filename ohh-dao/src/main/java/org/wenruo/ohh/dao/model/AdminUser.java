package org.wenruo.ohh.dao.model;

public class AdminUser {
    private Long id;

    private String name1;

    private String msg;

    public AdminUser(Long id, String name1, String msg) {
        this.id = id;
        this.name1 = name1;
        this.msg = msg;
    }

    public AdminUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1 == null ? null : name1.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}