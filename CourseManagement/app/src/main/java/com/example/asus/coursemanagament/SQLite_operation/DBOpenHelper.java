package com.example.asus.coursemanagament.SQLite_operation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2015/11/17.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;//版本
    private static final String DB_NAME = "CourseManagement.db";//数据库名
    //创建教学办数据库表
    public static final String TEACHINGOFFICE = "教学办信息表";//表名
    public static final String ID = "工号";//表中的列名
    public static final String PASSWORD = "密码";//表中的列名
    public static final String PHONE = "联系电话";//表中的列名
    public static final String NAME = "姓名";//表中的列名


    //创建教师数据库表
    public static final String TEACHER = "教师信息表";//表名
    public static final String ID1 = "工号";//表中的列名
    public static final String PASSWORD1 = "密码";//表中的列名
    public static final String DEPARTMENT = "所属系";//表中的列名
    public static final String NAME1 = "姓名";//表中的列名
    public static final String SEX = "性别";//表中的列名
    public static final String BIRTH = "出生日期";//表中的列名
    public static final String EMAIL = "电子邮件";//表中的列名
    public static final String PHONE1 = "联系电话";//表中的列名

    //创建系负责人数据库表
    public static final String DEPARTMENTER = "系负责人信息表";//表名
    public static final String ID3 = "工号";//表中的列名
    public static final String PASSWORD2 = "密码";//表中的列名
    public static final String DEPARTMENT2 = "所属系";//表中的列名
    public static final String PHON2E = "联系电话";//表中的列名
    public static final String NAME2 = "姓名";//表中的列名



    //创建课表的数据库表
    public static final String COURSE = "计算机课程表";//表名
    public static final String ID2 = "序号";//表中的列名
    public static final String GRADE = "年级";//表中的列名
    public static final String MAJOR = "专业";//表中的列名
    public static final String P_CNT = "专业人数";//表中的列名
    public static final String C_NAME = "课程名称";//表中的列名
    public static final String TYPE = "选修类型";//表中的列名
    public static final String CREDIT = "学分";//表中的列名
    public static final String TIMES = "学时";//表中的列名
    public static final String EXP_TIMES = "实验学时";//表中的列名
    public static final String PRA_TIMES = "上机学时";//表中的列名
    public static final String BE_WEEKS = "起讫周序";//表中的列名
    public static final String T_NAME = "任课教师";//表中的列名
    public static final String REMARK = "备注";//表中的列名

    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格
    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    //数据库第一次被创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TEACHER +" (序号 INTEGER PRIMARY KEY autoincrement ,工号 text NOT NULL, 密码 TEXT NOT NULL, 所属系 TEXT NOT NULL, 姓名 TEXT NOT NULL, 性别 TEXT, 出生日期 TEXT, 电子邮件 TEXT, 联系电话 TEXT)");

        db.execSQL("CREATE TABLE " + TEACHINGOFFICE +" (序号 INTEGER PRIMARY KEY autoincrement, 工号 text NOT NULL, 密码 TEXT NOT NULL, 联系电话 TEXT NOT NULL, 姓名 text NOT NULL)");
        db.execSQL("CREATE TABLE 系负责人信息表 (序号 INTEGER PRIMARY KEY,工号 text NOT NULL, 密码 TEXT NOT NULL,所属系 TEXT NOT NULL, 联系电话 TEXT NOT NULL, 姓名 text NOT NULL)");
        db.execSQL("CREATE TABLE 计算机课程表 (序号 integer PRIMARY KEY, 年级 TEXT NOT NULL, 专业 TEXT NOT NULL, 专业人数 TEXT NOT NULL, 课程名称 TEXT NOT NULL, 选修类型 TEXT NOT NULL, 学分 TEXT NOT NULL, 学时 TEXT, 实验学时 TEXT, 上机学时 TEXT, 起讫周序 TEXT, 任课教师 TEXT, 备注 TEXT)");
        db.execSQL("CREATE TABLE 发布表 (序号 integer PRIMARY KEY,表名 integer not null, 学期 TEXT NOT NULL, 教师报课截止时间 TEXT NOT NULL, 系负责人审核截止时间 TEXT NOT NULL)");
        db.execSQL("CREATE TABLE 数学课程表 (序号 integer PRIMARY KEY, 年级 TEXT NOT NULL, 专业 TEXT NOT NULL, 专业人数 TEXT NOT NULL, 课程名称 TEXT NOT NULL, 选修类型 TEXT NOT NULL, 学分 TEXT NOT NULL, 学时 TEXT, 实验学时 TEXT, 上机学时 TEXT, 起讫周序 TEXT, 任课教师 TEXT, 备注 TEXT)");
        db.execSQL("CREATE TABLE 教师报课信息表 (序号 integer PRIMARY KEY,工号 TEXT, 课程名 TEXT, 年级 TEXT, 起讫周序 TEXT, 任课教师 TEXT NOT NULL, 备注 TEXT)");

    }

    //版本升级时被调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
