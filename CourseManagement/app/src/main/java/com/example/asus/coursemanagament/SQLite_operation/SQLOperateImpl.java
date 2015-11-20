package com.example.asus.coursemanagament.SQLite_operation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 2015/11/17.
 */
public class SQLOperateImpl implements SQLOperate{

    private DBOpenHelper dbOpenHelper;

    public SQLOperateImpl(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 增，用insert向数据库  教学办    表中插入数据
     */
    public void add_teachingoffice(Tb_teachingoffice thof) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID, thof.getId());
        values.put(DBOpenHelper.PASSWORD, thof.getPassword());
        values.put(DBOpenHelper.PHONE,thof.getPhone());
        values.put(DBOpenHelper.NAME,thof.getName());
        db.insert(DBOpenHelper.TEACHINGOFFICE, null, values);
    }

    /**
     * 增，用insert向数据库  教师   表中插入数据
     */
    public void add_teacher(Tb_teacher th) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID1, th.getId());
        values.put(DBOpenHelper.PASSWORD1, th.getPassword());
        values.put(DBOpenHelper.DEPARTMENT,th.getDepartment());
        values.put(DBOpenHelper.NAME1,th.getName());
        values.put(DBOpenHelper.SEX, th.getSex());
        values.put(DBOpenHelper.BIRTH, th.getBirth());
        values.put(DBOpenHelper.EMAIL, th.getEmail());
        values.put(DBOpenHelper.PHONE1, th.getPhone());
        db.insert(DBOpenHelper.TEACHER, null, values);
    }

    /**
     * 增，用insert向数据库  系负责人   表中插入数据
     */
    public void add_department(Tb_department dp) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID3, dp.getId());
        values.put(DBOpenHelper.PASSWORD2, dp.getPassword());
        values.put(DBOpenHelper.DEPARTMENT2,dp.getDepartment());
        values.put(DBOpenHelper.PHON2E,dp.getPhone());
        values.put(DBOpenHelper.NAME2, dp.getName());
        db.insert(DBOpenHelper.DEPARTMENTER, null, values);
    }

    /**
     * 增，用insert向数据库  课程   表中插入数据
     */
    public void add_course(Tb_course cs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID2, cs.getId());
        values.put(DBOpenHelper.GRADE, cs.getGrade());
        values.put(DBOpenHelper.MAJOR,cs.getMajor());
        values.put(DBOpenHelper.P_CNT,cs.getP_cnt());
        values.put(DBOpenHelper.C_NAME, cs.getC_name());
        values.put(DBOpenHelper.TYPE, cs.getType());
        values.put(DBOpenHelper.CREDIT, cs.getCredit());
        values.put(DBOpenHelper.TIMES, cs.getTimes());
        values.put(DBOpenHelper.EXP_TIMES, cs.getExp_times());
        values.put(DBOpenHelper.PRA_TIMES, cs.getPra_times());
        values.put(DBOpenHelper.BE_WEEKS, cs.getBe_weeks());
        values.put(DBOpenHelper.T_NAME, cs.getT_name());
        values.put(DBOpenHelper.REMARK, cs.getRemark());
        db.insert(DBOpenHelper.COURSE, null, values);
    }

    /**
     * 删，通过id删除 教学办 表数据
     */
    public void delete_teachingoffice(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.TEACHINGOFFICE, DBOpenHelper.ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * 删，通过id删除 教师  表数据
     */
    public void delete_teacher(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.TEACHER, DBOpenHelper.ID1 + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * 删，通过id删除 系负责人  表数据
     */
    public void delete_department(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.DEPARTMENTER, DBOpenHelper.ID3 + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * 删，通过id删除 课程  表数据
     */
    public void delete_course(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.COURSE, DBOpenHelper.ID2 + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * 改，修改 教学办   表中指定id的数据
     */
    public void updata_teachingoffice(Tb_teachingoffice thof) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID, thof.getId());
        values.put(DBOpenHelper.PASSWORD, thof.getPassword());
        values.put(DBOpenHelper.PHONE,thof.getPhone());
        values.put(DBOpenHelper.NAME,thof.getName());
        db.update(DBOpenHelper.TEACHINGOFFICE, values, DBOpenHelper.ID + "=?", new String[]{String.valueOf(thof.getId())});
    }

    /**
     * 改，修改 教师  表中指定id的数据
     */
    public void updata_teacher(Tb_teacher th) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID1, th.getId());
        values.put(DBOpenHelper.PASSWORD1, th.getPassword());
        values.put(DBOpenHelper.DEPARTMENT,th.getDepartment());
        values.put(DBOpenHelper.NAME1,th.getName());
        values.put(DBOpenHelper.SEX, th.getSex());
        values.put(DBOpenHelper.BIRTH, th.getBirth());
        values.put(DBOpenHelper.EMAIL, th.getEmail());
        values.put(DBOpenHelper.PHONE1, th.getPhone());
        db.update(DBOpenHelper.TEACHER, values, DBOpenHelper.ID1 + "=?", new String[]{String.valueOf(th.getId())});
    }


    /**
     * 改，修改 系负责人  表中指定id的数据
     */
    public void updata_department(Tb_department th) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID3, th.getId());
        values.put(DBOpenHelper.PASSWORD2, th.getPassword());
        values.put(DBOpenHelper.DEPARTMENT2,th.getDepartment());
        values.put(DBOpenHelper.PHON2E,th.getPhone());
        values.put(DBOpenHelper.NAME2, th.getName());
        db.update(DBOpenHelper.DEPARTMENTER, values, DBOpenHelper.ID3 + "=?", new String[]{String.valueOf(th.getId())});
    }


    /**
     * 改，修改 课程  表中指定id的数据
     */
    public void updata_course(Tb_course cs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ID2, cs.getId());
        values.put(DBOpenHelper.GRADE, cs.getGrade());
        values.put(DBOpenHelper.MAJOR,cs.getMajor());
        values.put(DBOpenHelper.P_CNT,cs.getP_cnt());
        values.put(DBOpenHelper.C_NAME, cs.getC_name());
        values.put(DBOpenHelper.TYPE, cs.getType());
        values.put(DBOpenHelper.CREDIT, cs.getCredit());
        values.put(DBOpenHelper.TIMES, cs.getTimes());
        values.put(DBOpenHelper.EXP_TIMES, cs.getExp_times());
        values.put(DBOpenHelper.PRA_TIMES, cs.getPra_times());
        values.put(DBOpenHelper.BE_WEEKS, cs.getBe_weeks());
        values.put(DBOpenHelper.T_NAME, cs.getT_name());
        values.put(DBOpenHelper.REMARK, cs.getRemark());
        db.update(DBOpenHelper.COURSE, values, DBOpenHelper.ID2 + "=?", new String[]{String.valueOf(cs.getId())});
    }

    /**
     * 查询   教学办  表中指定id的数据
     */
    public Tb_teachingoffice findById_teachingoffice(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.TEACHINGOFFICE, null, DBOpenHelper.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Tb_teachingoffice person = null;
        if(cursor != null && cursor.moveToFirst()){
            person = new Tb_teachingoffice();
            String id1 = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ID));
            String password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PHONE));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME));
            person.setId(id1);
            person.setPassword(password);
            person.setPhone(phone);
            person.setName(name);
        }
        return person;
    }

    /**
     * 查询   教师  表中指定id的数据
     */
    public Tb_teacher findById_teacher(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.TEACHER, null, DBOpenHelper.ID1 + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Tb_teacher person = null;
        if(cursor != null && cursor.moveToFirst()){
            person = new Tb_teacher();
            String id1 = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ID1));
            String password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PASSWORD1));
            String department = cursor.getString(cursor.getColumnIndex(DBOpenHelper.DEPARTMENT));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME1));
            String sex = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SEX));
            String birth = cursor.getString(cursor.getColumnIndex(DBOpenHelper.BIRTH));
            String email = cursor.getString(cursor.getColumnIndex(DBOpenHelper.EMAIL));
            String phone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PHONE1));
            person.setId(id1);
            person.setPassword(password);
            person.setDepartment(department);
            person.setName(name);
            person.setSex(sex);
            person.setBirth(birth);
            person.setEmail(email);
            person.setPhone(phone);

        }
        return person;
    }

    /**
     * 查询   教师  表中指定id的数据
     */
    public Tb_department findById_department(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.DEPARTMENTER, null, DBOpenHelper.ID3 + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Tb_department person = null;
        if(cursor != null && cursor.moveToFirst()){
            person = new Tb_department();
            String id1 = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ID3));
            String password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PASSWORD2));
            String department = cursor.getString(cursor.getColumnIndex(DBOpenHelper.DEPARTMENT2));
            String phone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PHON2E));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME2));
            person.setId(id1);
            person.setPassword(password);
            person.setDepartment(department);
            person.setName(name);
            person.setPhone(phone);
        }
        return person;
    }

    /**
     * 查询   课表  表中指定id的数据
     */
    public Tb_course findById_course(String id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.COURSE, null, DBOpenHelper.ID2 + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Tb_course person = null;
        if(cursor != null && cursor.moveToFirst()){
            person = new Tb_course();
            String id1 = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ID2));
            String grade = cursor.getString(cursor.getColumnIndex(DBOpenHelper.GRADE));
            String major = cursor.getString(cursor.getColumnIndex(DBOpenHelper.MAJOR));
            String p_cnt = cursor.getString(cursor.getColumnIndex(DBOpenHelper.P_CNT));
            String c_name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.C_NAME));
            String type = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TYPE));
            String credit = cursor.getString(cursor.getColumnIndex(DBOpenHelper.CREDIT));
            String times = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TIMES));
            String exp_times = cursor.getString(cursor.getColumnIndex(DBOpenHelper.EXP_TIMES));
            String pra_times = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PRA_TIMES));
            String be_weeks = cursor.getString(cursor.getColumnIndex(DBOpenHelper.BE_WEEKS));
            String t_name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.T_NAME));
            String remark = cursor.getString(cursor.getColumnIndex(DBOpenHelper.REMARK));
            person.setId(id1);
            person.setGrade(grade);
            person.setMajor(major);
            person.setP_cnt(p_cnt);
            person.setC_name(c_name);
            person.setType(type);
            person.setCredit(credit);
            person.setTimes(times);
            person.setExp_times(exp_times);
            person.setPra_times(pra_times);
            person.setBe_weeks(be_weeks);
            person.setT_name(t_name);
            person.setRemark(remark);
        }
        return person;
    }

//    /**
//     * 查，查询表中所有的数据    (暂时没用！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//     */
//    public List<Tb_teachingoffice> find() {
//        List<Tb_teachingoffice> persons = null;
//        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//        Cursor cursor = db.query(DBOpenHelper.TEACHINGOFFICE, null, null, null, null, null, null);
//        if(cursor != null){
//            persons = new ArrayList<Tb_teachingoffice>();
//            while(cursor.moveToNext()){
//                Tb_teachingoffice person = new Tb_teachingoffice();
//                String id = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ID));
//                String password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PASSWORD));
//                String phone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.PHONE));
//                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME));
//                person.setId(id);
//                person.setId(password);
//                person.setPhone(phone);
//                person.setName(name);
//                persons.add(person);
//            }
//        }
//        return persons;
//    }
}