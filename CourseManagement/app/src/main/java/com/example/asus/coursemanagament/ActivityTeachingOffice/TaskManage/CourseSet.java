package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;

public class CourseSet extends AppCompatActivity {

    private ImageView imgvw_back1;
    private Button btn_update;
    private Button btn_solve;
    private EditText time_teacher;
    private EditText time_department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_set);
        //设置返回的监听事件==============================
        imgvw_back1 = (ImageView) findViewById(R.id.imgvw_back1);
        imgvw_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseSet.this, TaskManage.class);
                startActivity(intent);
            }
        });
        //===================================================

        //设置课表修改监听事件=============================
        time_teacher = (EditText) findViewById(R.id.time_teacher);
        time_department = (EditText) findViewById(R.id.time_department);
        btn_solve = (Button) findViewById(R.id.btn_solve);
        btn_solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time_teacher.length() < 10) {
                    Toast.makeText(CourseSet.this, "教师报课截止时间输入格式有误", Toast.LENGTH_SHORT).show();
                } else if (time_department.length() < 10) {
                    Toast.makeText(CourseSet.this, "系负责人审核截止时间输入格式有误", Toast.LENGTH_SHORT).show();
                }
                //最终需要数据库数据来判断
                else {
                    Intent intent = new Intent(CourseSet.this, TaskManage.class);
                    startActivity(intent);
                }
            }
        });
        //==========================================================


    }
}
