package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTeacherCourse;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacherBaoCourse;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDeclare extends TabActivity {
    String tableName = new String("教师报课信息表");
    private List<Tb_teacherBaoCourse> l = new ArrayList<Tb_teacherBaoCourse>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_teacherBaoCourse>>() {}.getType();
    private String courseTB = new String();

    private SlidingMenu mLeftMenu_teacher;
    private Button btn_task1;
    private Button btn_password1;
    private Button btn_exit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_declare);
        mLeftMenu_teacher = (SlidingMenu) findViewById(R.id.teacher_menu);
//        tabCreate();
        initHttp();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
    public void toggleMenu(View view) {
        mLeftMenu_teacher.toggle();
    }
    //============================================

    //选项卡============================================
    private void tabCreate() {

//        Intent intent2 = getIntent();
//        //获取工号，以判定教师所属系
//        String gonghao = intent2.getStringExtra("gonghao");

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent(this, CourseBegin.class);
        intent.putExtra("courseTB",courseTB );
        spec = tabHost.newTabSpec("tab1")//新建一个tab
                .setIndicator("开始报课")//设置名称
                .setContent(intent);//设置显示的intent，也可以为R.id.xx
        tabHost.addTab(spec);//添加到tabhost

        intent = new Intent(this, CourseResultInfo.class);
        intent.putExtra("courseTB", courseTB);
        spec = tabHost.newTabSpec("tab2")
                .setIndicator("查看报课结果")
                .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);//设置默认选项卡，为tab1


        TabWidget tabWidget = tabHost.getTabWidget();//获取TabHost的头部
        //getChildCount 返回选项卡数量
        //getChildTabViewAt 返回位于指定索引位置的选项卡标识符
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            //循环每个tabView
            View view = tabWidget.getChildTabViewAt(i);
            //获取tabView项
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextSize(18);//改变字体
        }
    }

    //================================================================
    private void initHttp() {//连接服务器，用用户的工号查询对应专业的 开课表
        //测试用例
//        final List<Tb_teacherBaoCourse> l2 = new ArrayList<Tb_teacherBaoCourse>();
//        Tb_teacherBaoCourse t1 = new Tb_teacherBaoCourse("软件工程专业",
//                "软工实践", "2013级", "11322", "张东", "1-8周", "");
//        l2.add(t1);
//        Tb_teacherBaoCourse t2 = new Tb_teacherBaoCourse("数学类专业",
//                "XML建模", "2013级", "11322", "张里", "1-8周", "");
//        l2.add(t2);
//        Log.i(gson.toJson(l2), "!!!!!!!");

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("tableName", "" + tableName);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/login", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            l = gson.fromJson(gson.toJson(l2), type);
                            l = gson.fromJson(response, type);
                            Intent intent = getIntent();
                            //获取工号，判定教师 所属系即专业
                            String gonghao = intent.getStringExtra("gonghao");
                            Log.i("info", "!!!!!!!!!!!" + gonghao);
                            Bundle bundle2 = new queryDB().queryDB(CourseDeclare.this, tableName, l);
                            int rows2 = bundle2.getInt("rows");
                            int i;
                            String tmp;
                            courseTB = new String();
//                            for (i = 0; i < rows2; i++) {
//                                tmp = "cell" + i;
//                                if (bundle2.getString(tmp + 3).equals(gonghao)) {
//                                    courseTB = bundle2.getString(tmp + 0);
//                                    break;
//                                }
//                            }
                            courseTB = "软件工程专业";//测试使用，之后删除，将前面的代码解注释
                            tabCreate();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.i("in onError", "!!!!!!");
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(CourseDeclare.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

//                            l = gson.fromJson(gson.toJson(l2), type);
//
//                            Intent intent = getIntent();
//                            //获取工号，判定教师 所属系即专业
//                            String gonghao = intent.getStringExtra("gonghao");
//                            Log.i("info", "!!!!!!!!!!!" + gonghao);
//                            Bundle bundle2 = new queryDB().queryDB(CourseDeclare.this, tableName, l);
//                            int rows2 = bundle2.getInt("rows");
//                            int i;
//                            String tmp;
//                            courseTB = new String();
//                            for (i = 0; i < rows2; i++) {
//                                tmp = "cell" + i;
//                                if (bundle2.getString(tmp + 3).equals(gonghao)) {
//                                    courseTB = bundle2.getString(tmp + 0);
//                                    break;
//                                }
//                            }
//                            courseTB = "软件工程专业";//测试使用，之后删除，将前面的代码解注释
//                            tabCreate();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //=========================================================

    }
    //==================================================================
}
