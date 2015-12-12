package com.tb;

public class Tb_teacher {
	private String id;          //用户名，主键
    private String password;    //用户密码
    private String department;  //所属系
    private String name;        //姓名
    private String sex;         //性别
    private String birth;       //出生年月
    private String email;       //邮箱
    private String phone;       //手机号码

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Tb_teacher() {
        super();
    }

    public Tb_teacher(String id,String password,String department,String name,String sex,String birth,String email,String phone){
        this.id = id;
        this.password = password;
        this.department = department;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
    }
}
