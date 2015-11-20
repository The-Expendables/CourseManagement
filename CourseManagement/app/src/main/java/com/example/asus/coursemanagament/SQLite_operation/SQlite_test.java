package com.example.asus.coursemanagament.SQLite_operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.coursemanagament.R;

public class SQlite_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        //对教学办对象增、删、改、查的例子
//        SQLOperateImpl test = new SQLOperateImpl(SQLite.this);    //增
//        Tb_teachingoffice th = new Tb_teachingoffice("12345","12345","18659545514","张三");
//        test.add_teachingoffice(th);
//
//        SQLOperateImpl test1 = new SQLOperateImpl(SQLite.this);   //删
//        test1.delete_teachingoffice(12345);
//
//        SQLOperateImpl test2 = new SQLOperateImpl(SQLite.this);     //改
//        Tb_teachingoffice th2 = new Tb_teachingoffice("12345","12345","18659545514","李四");
//        test.updata_teachingoffice(th2);
//
//        SQLOperateImpl test3 = new SQLOperateImpl(SQlite_test.this);     //查
//        Tb_teachingoffice th3 = test3.findById_teachingoffice("12345");    //通过id（工号）找到整条记录
//        Log.i("info", th3.getName());     //可以通过getName()方法获得查找到的记录的名字，并在日志上打印出来
//                                           //要获取其他相应信息可以调用相应的方法
    }
}
