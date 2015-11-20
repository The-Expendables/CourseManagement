package com.example.asus.coursemanagament.SQLite_operation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wwk on 2015/11/20.
 */
public class queryDB {
    private String[] departmentInfo = {"序号","工号", "密码", "所属系", "联系电话", "姓名"};
    private String[] teacherCurriculums = {"序号","工号","课程名","年级","起讫周序","任课教师","备注"};
    private String[] teacherInfo = {"序号","工号","密码","所属系","姓名","性别","出生日期","电子邮件",
            "联系电话"};
    private String[] release = {"序号","表名","学期","教师报课截止时间","系负责人审核截止时间"};
    private String[] course = {"序号","年级","专业","专业人数","课程名称","选修类型",
            "学分","学时","实验学时","上机学时","起讫周序","任课教师","备注"};

    private String[] x;
    int i, j = 0;
    String id;
    Bundle bundle = new Bundle();

    public Bundle queryDB(Context context,String tableName) {
        DBOpenHelper dbHelper = new DBOpenHelper(context, "CourseManagement.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
        switch (tableName){
            case "计算机专业课程表":x = course;break;
            case "数学专业课程表":x = course;break;
            case "发布表":x = release;break;
            case "教师信息表":x = teacherInfo;break;
            case "教师报课信息表" :x = teacherCurriculums;break;
            case "系负责人信息表":x = departmentInfo;break;
        }

        //查询的语法，参数1为表名；参数2为表中的列名；参数3为要查询的列名；参数4为对应列的值；该函数返回的是一个游标
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            for (i = 0; i < x.length; i++) {
                id = "cell" + j + i;
                bundle.putString(id, cursor.getString(cursor.getColumnIndex(x[i])));
            }
            j++;
        }
        bundle.putInt("rows", j);
        bundle.putInt("cols", i);
        cursor.close();

        return bundle;
    }
}