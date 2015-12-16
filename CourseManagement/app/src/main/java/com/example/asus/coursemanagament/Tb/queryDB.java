package com.example.asus.coursemanagament.Tb;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wwk on 2015/11/20.
 */
public class queryDB {
    //系负责人
    private String[] departmentInfo = {"工号", "密码", "姓名", "手机号码", "所属系"};
    //教师开课表
    private String[] teacherCurriculums = {"课程表名", "课程名称", "年级", "工号", "任课教师",
            "起讫周序", "备注"};
    //教师
    private String[] teacherInfo = {"工号", "密码", "姓名", "所属系", "性别", "出生日期", "邮箱",
            "手机号码"};
    //开课时间表
    private String[] release = {"课程表名", "学期", "教师报课截止时间", "系负责人审核截止时间"};
    private String[] course = {"年级", "专业", "专业人数", "课程名称", "选修类型",
            "学分", "学时", "实验学时", "上机学时", "起讫周序", "任课教师", "备注"};
    private String[] x;
    private int i, j;
    private String id;
    private Bundle bundle = new Bundle();

    private int k;
    private Type type;//gson 中参数类型
    private Gson gson = new Gson();
    private String s = new String();

    public Bundle queryDB(Context context, String tableName,List<?> l) {

        if(     tableName.equals("计算机科学与技术（实验班）专业开课表")||
                tableName.equals("计算机科学与技术（卓越班）专业开课表")||
                tableName.equals("计算机科学与技术专业开课表")||
                tableName.equals("软件工程专业开课表")||
                tableName.equals("数学类（实验班）专业开课表")||
                tableName.equals("数学类专业开课表")||
                tableName.equals("网络工程专业开课表")||
                tableName.equals("信息安全专业开课表")){

            x = course;
            k=1;
        }

        switch (tableName) {

            case "发布表":
                x = release;
                k = 2;
                break;
            case "教师信息表":
                x = teacherInfo;
                k=3;
                break;
            case "教师报课信息表":
                x = teacherCurriculums;
                k=4;
                break;
            case "系负责人信息表":
                x = departmentInfo;
                k=5;
                break;
        }



        //将 l 中的数据放入bundle中，按照不同的表定义不同类型

        Iterator it = l.iterator();
        while (it.hasNext()) {
            switch (k) {
                case 1:
                    for (i = 0; i < l.size(); i++) {
                        Tb_course t = (Tb_course) l.get(i);  //各类专业课表
                        for (j = 0; j < x.length; j++) {
                            s = t.getX(j);
                            id = "cell" + i + j;
                            bundle.putString(id, s);
                        }
                    }
                    break;
                case 2:
                    for (i = 0; i < l.size(); i++) {
                        Tb_course_mes t = (Tb_course_mes) l.get(i);  //课表的各项截止时间
                        for (j = 0; j < x.length; j++) {
                            s = t.getX(j);
                            id = "cell" + i + j;
                            bundle.putString(id, s);
                        }
                    }
                    break;
                case 3:
                    for (i = 0; i < l.size(); i++) {
                        Tb_teacher t = (Tb_teacher) l.get(i);  //教师信息
                        for (j = 0; j < x.length; j++) {
                            s = t.getX(j);
                            id = "cell" + i + j;
                            bundle.putString(id, s);
                        }
                    }
                    break;
                case 4:
                    for (i = 0; i < l.size(); i++) {
                        Tb_teacherBaoCourse t = (Tb_teacherBaoCourse) l.get(i);  //教师报课信息
                        for (j = 0; j < x.length; j++) {
                            s = t.getX(j);
                            id = "cell" + i + j;
                            bundle.putString(id, s);
                        }
                    }
                    break;
                case 5:
                    for (i = 0; i < l.size(); i++) {
                        Tb_department t = (Tb_department) l.get(i);  //系负责人
                        for (j = 0; j < x.length; j++) {
                            s = t.getX(j);
                            id = "cell" + i + j;
                            bundle.putString(id, s);
                        }
                    }
                    break;
            }
            it.next();
        }
        bundle.putInt("rows", i);
        bundle.putInt("cols", j);
        return bundle;
    }
}