package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SlidingMenu;

public class TaskManage extends TabActivity {

    private SlidingMenu mLeftMenu_teachingoffice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        mLeftMenu_teachingoffice = (SlidingMenu) findViewById(R.id.teachingoffice_menu);
        tabCreate();
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

