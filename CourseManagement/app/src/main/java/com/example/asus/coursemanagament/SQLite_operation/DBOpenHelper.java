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
    public static final String TEACHINGOFFICE = "TeachingOffice";//表名
    public static final String ID = "_id";//表中的列名
    public static final String PASSWORD = "password";//表中的列名
    public static final String PHONE = "phone";//表中的列名
    public static final String NAME = "name";//表中的列名

    //创建教师数据库表
    public static final String TEACHER = "Teacher";//表名
    public static final String ID1 = "_id";//表中的列名
    public static final String PASSWORD1 = "password";//表中的列名
    public static final String DEPARTMENT = "department";//表中的列名
    public static final String NAME1 = "name";//表中的列名
    public static final String SEX = "sex";//表中的列名
    public static final String BIRTH = "birth";//表中的列名
    public static final String EMAIL = "email";//表中的列名
    public static final String PHONE1 = "phone";//表中的列名

    //创建课表的数据库表
    public static final String COURSE = "Course";//表名
    public static final String ID2 = "_id";//表中的列名
    public static final String GRADE = "grade";//表中的列名
    public static final String MAJOR = "major";//表中的列名
    public static final String P_CNT = "p_cnt";//表中的列名
    public static final String C_NAME = "c_name";//表中的列名
    public static final String TYPE = "type";//表中的列名
    public static final String CREDIT = "credit";//表中的列名
    public static final String TIMES = "times";//表中的列名
    public static final String EXP_TIMES = "exp_times";//表中的列名
    public static final String PRA_TIMES = "pra_times";//表中的列名
    public static final String BE_WEEKS = "be_weeks";//表中的列名
    public static final String T_NAME = "t_name";//表中的列名
    public static final String REMARK = "remark";//表中的列名

    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格
    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    //数据库第一次被创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TeachingOffice (_id Integer PRIMARY KEY AUTOINCREMENT, password TEXT NOT NULL, phone TEXT NOT NULL, name text NOT NULL)");
        db.execSQL("CREATE TABLE Teacher (_id INTEGER PRIMARY KEY, password TEXT NOT NULL, department TEXT NOT NULL, name TEXT NOT NULL, sex TEXT, birth TEXT, email TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE course (_id INTEGER PRIMARY KEY, grade TEXT NOT NULL, major TEXT NOT NULL, p_cnt TEXT NOT NULL, c_name TEXT NOT NULL, type TEXT NOT NULL, credit TEXT NOT NULL, times TEXT, exp_times TEXT, pra_times TEXT, be_weeks TEXT, t_name TEXT, remark TEXT)");
    }

    //版本升级时被调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
