package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

/**
 * Created by wwk on 2015/11/13.
 */
public class ListInfo {
    private String ItemName;
    private String semester;
    private String deadlineview;
    private String deadline;

    public ListInfo(String ItemName, String semester, String deadlineview,String deadline) {
        this.ItemName = ItemName;
        this.semester = semester;
        this.deadlineview = deadlineview;
        this.deadline = deadline;
    }

    public String getItemName(){
        return ItemName;
    }
    public String getSemester(){
        return semester;
    }
    public String getDeadlineview(){
        return  deadlineview;
    }
    public String getDeadline(){
        return deadline;
    }
}
