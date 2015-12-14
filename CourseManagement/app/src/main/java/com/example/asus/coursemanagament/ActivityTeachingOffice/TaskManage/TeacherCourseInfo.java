package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacherBaoCourse;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.TeacherCourseListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherCourseInfo extends Activity {
    String tableName = new String ("教师报课信息表");
    String zhuanye =new String();
    private List<Tb_teacherBaoCourse> l = new ArrayList<Tb_teacherBaoCourse>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_teacherBaoCourse>>() {}.getType();
    String gonghao = new String();
    Intent intent = new Intent();


    private EditText search;
    private List<ListTeacherCourse> listInfos= new ArrayList<ListTeacherCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    TeacherCourseListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

         intent = getIntent();
        String t_name = intent.getStringExtra("t_name");
        TextView tc = (TextView)findViewById(R.id.teacherCourse);
        tc.setText(t_name+"报课情况");//title设置名称
        initList();


    }
    //search过滤搜索框事件============================================
    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(search.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    //===========================================================
    //iv_left后退跳转==================================================
    class MyOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();

        }
    }
    //============================================================
//控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());
        iv_left = (ImageView)findViewById(R.id.left);
        iv_left.setOnClickListener(new MyOnClickListner());



        adapter = new TeacherCourseListAdapter(TeacherCourseInfo.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){

        //测试用例
        final  List<Tb_teacherBaoCourse> l2 = new ArrayList<Tb_teacherBaoCourse>();
        Tb_teacherBaoCourse t1 = new Tb_teacherBaoCourse("软件工程专业",
                "软工实践", "2013级", "11322","张东","1-8周","");
        l2.add(t1);
        Tb_teacherBaoCourse t2 = new Tb_teacherBaoCourse("软件工程专业",
                "XML建模", "2013级", "11322","张东","1-8周","");
        l2.add(t2);
        Log.i(gson.toJson(l2), "!!!!!!!");

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type",""+2);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Log.i(,"------response-----"+response);
                            l = gson.fromJson(response, type);
                            //获取数据,获得教师的工号
                             gonghao = intent.getStringExtra("gonghao");
                            //获取数据,获得教师姓名

                            //汇总表和教师信息
                            Log.i(gonghao,"------gonghao-----");
                            Bundle bundle = new queryDB().queryDB(TeacherCourseInfo.this, tableName, l);
                            int rows = bundle.getInt("rows");
                            int cols = bundle.getInt("cols");
                            int i;
                            String tmp;
                            ListTeacherCourse cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                if (bundle.getString(tmp + 5).equals(gonghao)) {
                                    Log.i("info!!!!",bundle.getString(tmp + 5));
                                    cell = new ListTeacherCourse(bundle.getString(tmp + 1),"");
                                    listInfos.add(cell);
                                }
                            }

                            initView();
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

                            Toast.makeText(TeacherCourseInfo.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

//                            l = gson.fromJson(gson.toJson(l2), type);
//                            Intent intent = getIntent();
//                            //获取数据,获得教师的工号
//                            String gonghao = intent.getStringExtra("gonghao");
//                            //汇总表和教师信息
//                            bundle = new queryDB().queryDB(TeacherCourseInfo.this, tableName, l);
//                            int rows = bundle.getInt("rows");
//                            int cols = bundle.getInt("cols");
//                            int i;
//                            String tmp;
//                            ListTeacherCourse cell;
//                            for (i = 0; i < rows; i++) {
//                                tmp = "cell" + i;
//                                if (bundle.getString(tmp + 3).equals(gonghao)) {
//                                    cell = new ListTeacherCourse(bundle.getString(tmp + 1), "");
//                                    listInfos.add(cell);
//                                }
//                            }
//                            initView();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //=========================================================
    }
    //=========================================================
}
