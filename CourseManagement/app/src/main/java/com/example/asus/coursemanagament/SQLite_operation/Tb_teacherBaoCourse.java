package com.example.asus.coursemanagament.SQLite_operation;

/**
 * Created by wwk on 2015/12/10.
 */
public class Tb_teacherBaoCourse {
    private String table_name;  //课程表名
    private String course_name;      //课程名称
    private String grade;        //年级
    private String id;          //用户名,工号
    private String t_name;      //任课教师
    private String be_weeks;    //起讫周序
    private String remark;      //备注


   public String getTable_name(){
       return table_name;
   }
    public String getC_name() {
        return course_name;
    }
    public String getGrade() {
        return grade;
    }
    public String getId() {
        return id;
    }

    public String getT_name() {
        return t_name;
    }
    public String getBe_weeks() {
        return be_weeks;
    }

    public String getRemark() {
        return remark;
    }

    public String getX(int i) {
        String s = new String();
        switch (i) {
            case 0:
                s = getTable_name();break;
            case 1:
                s = getC_name();break;
            case 2:
                s = getGrade();break;
            case 3:
                s = getBe_weeks();break;
            case 4:
                s = getT_name();break;
            case 5:
                s = getId();break;
            case 6:
                s = getRemark();break;
        }
        return s;
    }
    public Tb_teacherBaoCourse(String table_name,String c_name,String grade,String be_weeks,String id,
                               String t_name , String remark){
        this.table_name = table_name;
        this.id = id;
        this.grade = grade;
        this.course_name = c_name;
        this.be_weeks = be_weeks;
        this.t_name = t_name;
        this.remark = remark;
    }
}
