package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

/**
 * Created by wwk on 2015/11/14.
 */
public class ListTeacherCourse {
    private String courseName;
    private String choose;

    public ListTeacherCourse(String courseName, String choose){
        this.courseName = courseName;
        this.choose = choose;
    }
    public String getCourseName(){
        return courseName;
    }
    public String getChoose(){
        return choose;
    }
}
