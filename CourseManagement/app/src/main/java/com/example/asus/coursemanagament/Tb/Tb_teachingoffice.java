package com.example.asus.coursemanagament.Tb;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Tb_teachingoffice {
    private String id;          //用户名，主键
    private String password;    //密码
    private String phone;       //手机号码
    private String name;        //姓名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tb_teachingoffice() {
        super();
    }

    public Tb_teachingoffice(String id,String password,String phone,String name){
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }
}
