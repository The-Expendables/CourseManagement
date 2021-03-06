package com.tb;

/**
 * Created by ASUS on 2015/10/30.
 */
public class Tb_course {
    private String id;    //编号；主键
    private String grade;        //年级
    private String major;       //专业
    private String p_cnt;        //人数
    private String c_name;      //课程名称
    private String type;        //选修类型
    private String credit;       //学分
    private String times;        //学时
    private String exp_times;    //实验学时
    private String pra_times;   //上机学时
    private String be_weeks;    //起讫周序
    private String t_name;      //任课教师
    private String remark;      //备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getP_cnt() {
        return p_cnt;
    }

    public void setP_cnt(String p_cnt) {
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

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getExp_times() {
        return exp_times;
    }

    public void setExp_times(String exp_times) {
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

    public Tb_course() {
        super();
    }

    public Tb_course(String id,String grade,String major,String p_cnt,String c_name,String type,
                     String credit,String times,String exp_times,String pra_times,String be_weeks,
                     String t_name,String remark){
        this.id = id;
        this.grade = grade;
        this.major = major;
        this.p_cnt = p_cnt;
        this.c_name = c_name;
        this.type = type;
        this.credit = credit;
        this.times = times;
        this.exp_times = exp_times;
        this.pra_times = pra_times;
        this.be_weeks = be_weeks;
        this.t_name = t_name;
        this.remark = remark;
    }
    
    //用于合并开课表里教师填报的信息
    public void setThree(String be_weeks,String t_name,String remark){
    	if(this.be_weeks.length()>0) this.be_weeks+=";";
    	this.be_weeks+=be_weeks;
    	
    	if(this.t_name.length()>0) this.t_name+=";";
    	this.t_name+=t_name;
    	
    	if(this.remark.length()>0) this.remark+=";";
    	this.remark+=remark;
    }
}
