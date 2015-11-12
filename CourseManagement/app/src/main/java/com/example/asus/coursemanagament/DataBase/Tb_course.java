package com.example.asus.coursemanagament.DataBase;

/**
 * Created by ASUS on 2015/10/30.
 */
public class Tb_course {
    private float grade;        //年级，主键
    private String major;       //专业
    private float p_cnt;        //人数
    private String c_name;      //课程名称，主键
    private String type;        //选修类型
    private float credit;       //学分
    private float times;        //学时
    private float exp_times;    //实验学时
    private String pra_times;   //上机学时
    private String be_weeks;    //起讫周序
    private String t_name;      //任课教师
    private String remark;      //备注

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public float getP_cnt() {
        return p_cnt;
    }

    public void setP_cnt(float p_cnt) {
        this.p_cnt = p_cnt;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getTimes() {
        return times;
    }

    public void setTimes(float times) {
        this.times = times;
    }

    public float getExp_times() {
        return exp_times;
    }

    public void setExp_times(float exp_times) {
        this.exp_times = exp_times;
    }

    public String getPra_times() {
        return pra_times;
    }

    public void setPra_times(String pra_times) {
        this.pra_times = pra_times;
    }

    public String getBe_weeks() {
        return be_weeks;
    }

    public void setBe_weeks(String be_weeks) {
        this.be_weeks = be_weeks;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
