package com.example.asus.coursemanagament.Tb;

/**
 * Created by Administrator on 2015/11/20.
 */
public class Tb_course_mes {
    String table_name;  //表名
    String term;    //学期
    String teacher_time;    //教师报课截止时间
    String department_time;     //系负责人审核截止时间
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

    public String getX(int i) {
        String s = new String();
        switch (i) {
            case 0:
                s = getTable_name();break;
            case 1:
                s = getTerm();break;
            case 2:
                s = getTeacher_time();break;
            case 3:
                s = getDepartment_time();break;
        }
        return s;
    }
    public void setDepartment_time(String department_time) {
        this.department_time = department_time;
    }
}
