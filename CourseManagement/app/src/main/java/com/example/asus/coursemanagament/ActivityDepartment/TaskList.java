package com.example.asus.coursemanagament.ActivityDepartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.ActivityTeachingOffice.PasswordChange;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TaskManage;
import com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage.UserManage;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

public class TaskList extends AppCompatActivity {

    private SlidingMenu mLeftMenu_department;
    private Button btn_task2;
    private Button btn_password2;
    private Button btn_exit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        mLeftMenu_department = (SlidingMenu) findViewById(R.id.department_menu);

        //设置侧滑菜单任务管理跳转=================================
        btn_task2 = (Button) findViewById(R.id.btn_task2);
        btn_task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu_department.toggle();
            }
        });
        //==========================================================

        //设置侧滑菜单修改密码跳转======================================
        btn_password2 = (Button) findViewById(R.id.btn_password2);
        btn_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskList.this, PasswordChange2.class);
                startActivity(intent);
            }
        });
        //============================================

        //设置侧滑菜单退出跳转=======================================
        btn_exit2 = (Button) findViewById(R.id.btn_exit2);
        btn_exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskList.this, Login.class);
                startActivity(intent);
            }
        });
        //===========================================================

    }

    //侧滑菜单===============================
    public void toggleMenu(View view){
        mLeftMenu_department.toggle();
    }
    //=========================================
}
