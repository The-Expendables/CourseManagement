package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.ActivityTeachingOffice.PasswordChange;
import com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage.UserManage;

import android.widget.TabWidget;
import android.widget.TextView;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Util.SlidingMenu;

public class TaskManage extends TabActivity {

    private SlidingMenu mLeftMenu_teachingoffice;
    private ImageView imgvw_add_task;
    private Button btn_task;
    private Button btn_user;
    private Button btn_password;
    private Button btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        mLeftMenu_teachingoffice = (SlidingMenu) findViewById(R.id.teachingoffice_menu);
        tabCreate();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //添加新的报课表======================================================
        imgvw_add_task = (ImageView) findViewById(R.id.imgvw_add_task);
        imgvw_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, CourseAdd.class);
                startActivity(intent);
            }
        });
        //======================================================================

        //设置侧滑菜单任务管理跳转=================================
        btn_task = (Button) findViewById(R.id.btn_task);
        btn_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu_teachingoffice.toggle();
            }
        });
        //==========================================================

        //设置侧滑菜单用户管理跳转====================================
        btn_user = (Button) findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, UserManage.class);
                startActivity(intent);
            }
        });
        //=============================================================

        //设置侧滑菜单修改密码跳转======================================
        btn_password = (Button) findViewById(R.id.btn_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, PasswordChange.class);
                startActivity(intent);
            }
        });
        //============================================

        //设置侧滑菜单退出跳转=======================================
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this,Login.class);
                startActivity(intent);
            }
        });
        //===========================================================
    }

    //侧滑菜单===============================
    public void toggleMenu(View view){
        mLeftMenu_teachingoffice.toggle();
    }
    //=========================================

    //选项卡============================================
    private void tabCreate(){
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this,CourseRelease.class);
        spec = tabHost.newTabSpec("tab1")//新建一个tab
                .setIndicator("报课表发布")//设置名称
                .setContent(intent);//设置显示的intent，也可以为R.id.xx
        tabHost.addTab(spec);//添加到tabhost

        intent = new Intent(this,CourseCheck.class);
        spec = tabHost.newTabSpec("tab2")
                .setIndicator("查看报课") //Indicator指示器
                .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);//设置默认选项卡，为tab1

        TabWidget tabWidget=tabHost.getTabWidget();//获取TabHost的头部

        //getChildCount 返回选项卡数量
        //getChildTabViewAt 返回位于指定索引位置的选项卡标识符
        for(int i = 0;i < tabWidget.getChildCount();i++){
            //循环每个tabView
            View view = tabWidget.getChildTabViewAt(i);
            //获取tabView项
            TextView tv = (TextView)view.findViewById(android.R.id.title);
            tv.setTextSize(18);//改变字体
        }
    }

    //================================================================
}

