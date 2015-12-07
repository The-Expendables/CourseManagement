package com.example.asus.coursemanagament.SQLite_operation;

/**
 * Created by Administrator on 2015/11/20.
 */
public class Tb_course_mes {
    String table_name;
    String term;
    String teacher_time;
    String department_time;
    public Tb_course_mes(){};
    public Tb_course_mes(String table_name,String term,String teacher_time,String department_time){
        this.table_name=table_name;
        this.term=term;
        this.teacher_time=teacher_time;
        this.department_time=department_time;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTeacher_time() {
        return teacher_time;
    }

    public void setTeacher_time(String teacher_time) {
        this.teacher_time = teacher_time;
    }

    public String getDepartment_time() {
        return department_time;
    }

    public void setDepartment_time(String department_time) {
        this.department_time = department_time;
    }
}
