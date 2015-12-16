package com.example.asus.coursemanagament.Tb;

/**
 * Created by ASUS on 2015/12/9.
 */
public class Tb_teacher_declare {
    String table_name;
    String course_name;
    String grade;
    String be_weeks;
    String id;
    String t_name;
    String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getT_name() {

        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBe_weeks() {

        return be_weeks;
    }

    public void setBe_weeks(String be_weeks) {
        this.be_weeks = be_weeks;
    }

    public String getGrade() {

        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourse_name() {

        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTable_name() {

        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Tb_teacher_declare() {
        super();
    }

    public Tb_teacher_declare(String table_name,String course_name,String grade,String be_weeks,
                              String id,String t_name,String remark){
        this.table_name = table_name;
        this.course_name = course_name;
        this.grade = grade;
        this.be_weeks = be_weeks;
        this.id = id;
        this.t_name = t_name;
        this.remark = remark;
    }

}
