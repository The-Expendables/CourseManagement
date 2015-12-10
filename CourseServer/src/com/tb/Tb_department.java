package com.tb;

/**
 * Created by ASUS on 2015/11/20.
 */
public class Tb_department {
    private String id;          //用户名
    private String password;    //密码
    private String department;  //所属系
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public Tb_department() {
        super();
    }

    public Tb_department(String id,String password,String department,String phone,String name){
        this.id = id;
        this.password = password;
        this.department=department;
        this.phone = phone;
        this.name = name;
    }
}
