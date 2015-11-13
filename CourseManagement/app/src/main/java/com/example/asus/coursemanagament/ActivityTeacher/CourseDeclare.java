package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.ActivityTeachingOffice.PasswordChange;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

public class CourseDeclare extends TabActivity {

    private SlidingMenu mLeftMenu_teacher;
    private Button btn_task1;
    private Button btn_password1;
    private Button btn_exit1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_declare);
        mLeftMenu_teacher = (SlidingMenu) findViewById(R.id.teacher_menu);
        tabCreate();
        //设置侧滑菜单任务管理跳转=========================
        btn_task1 = (Button) findViewById(R.id.btn_task1);
        btn_task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu_teacher.toggle();
            }
        });
        //===================================================

        //设置侧滑菜单修改密码跳转==============================
        btn_password1 = (Button) findViewById(R.id.btn_password1);
        btn_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDeclare.this, PasswordChange1.class);
                startActivity(intent);
            }
        });
        //=====================================================

        //设置侧滑菜单退出跳转===============================
        btn_exit1 = (Button) findViewById(R.id.btn_exit1);
        btn_exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDeclare.this, Login.class);
                startActivity(intent);
            }
        });
        //===================================================
    }

    //切换侧滑菜单==============================
    public void toggleMenu(View view){
        mLeftMenu_teacher.toggle();
    }
    //============================================

    //选项卡============================================
    private void tabCreate(){
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this,CourseResultList_XXX.class);
        spec = tabHost.newTabSpec("tab1")//新建一个tab
                .setIndicator("开始报课")//设置名称
                .setContent(intent);//设置显示的intent，也可以为R.id.xx
        tabHost.addTab(spec);//添加到tabhost

        intent = new Intent(this,CourseResultInfo.class);
        spec = tabHost.newTabSpec("tab2")
                .setIndicator("查看报课结果")
                .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);//设置默认选项卡，为tab1
    }
    //================================================================
}
