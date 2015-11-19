package com.example.asus.coursemanagament.SQLite_operation;

/**
 * Created by ASUS on 2015/11/17.
 */
public interface SQLOperate {
    //对教学办纤细的增删改查
    public void add_teachingoffice(Tb_teachingoffice thof);
    public void delete_teachingoffice(int id);
    public void updata_teachingoffice(Tb_teachingoffice thof);
//    public List<Tb_teachingoffice> find();          没用到。。。。。。
    public Tb_teachingoffice findById_teachingoffice(int id);

    //对教师信息的增删改查
    public void add_teacher(Tb_teacher th);
    public void delete_teacher(int id);
    public void updata_teacher(Tb_teacher th);
//    public List<Tb_teacher> find();         没用到。。。。。。
    public Tb_teacher findById_teacher(int id);

    //对课程表的增删改查
    public void add_course(Tb_course cs);
    public void delete_course(int id);
    public void updata_course(Tb_course cs);
//    public List<Tb_teacher> find();        没用到。。。。。。。。。。
    public Tb_course findById_course(int id);
}
