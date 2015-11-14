package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

/**
 * Created by wwk on 2015/11/13.
 */
public class ListProfessionals {
    private String teacherName;
    private String job;
    private String jobNumber;

    public ListProfessionals(String teacherName, String job, String jobNumber){
        this.teacherName = teacherName;
        this.job = job;
        this.jobNumber = jobNumber;
    }

    public String getTeacherName(){
        return teacherName;
    }
    public String getJob(){
        return job;
    }
    public String getJobNumber(){
        return jobNumber;
    }
}
