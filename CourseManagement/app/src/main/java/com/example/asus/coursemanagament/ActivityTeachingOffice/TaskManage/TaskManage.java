package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.example.asus.coursemanagament.R;

public class TaskManage extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        tabCreate();
    }
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
                .setIndicator("查看报课")
                .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);//设置默认选项卡，为tab1
    }
    //================================================================
}
