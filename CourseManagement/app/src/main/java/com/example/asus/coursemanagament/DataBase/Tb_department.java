package com.example.asus.coursemanagament.DataBase;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Tb_department {
    private String id;          //用户名,主键
    private String password;    //用户密码
    private String phone;       //手机密码
    private String department;  //所属系
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
