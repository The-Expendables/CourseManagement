package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_course_mes;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacher_declare;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.CurriculumsListAdapter;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseBegin extends AppCompatActivity {

    private String tableName = new String("发布表");
    private List<Tb_course_mes> l = new ArrayList<Tb_course_mes>();
    private Gson g = new Gson();
    private Type type = new TypeToken<List<Tb_course_mes>>() {}.getType();
    private Bundle bundle;

    private EditText search;
    private List<ListCurriculums> listCurriculumses = new ArrayList<ListCurriculums>(); //存放Item
    private ListView listView;
    private Tb_teacher_declare tb_teacher_declare;
    private Gson gson=new Gson();
    private String tb_teacher_declare_json;
    CurriculumsListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_begin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();


    }
    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(CourseBegin.this,CourseChoose.class);
            TextView info = (TextView)view.findViewById(R.id.ItemName);
            //传送给下一个UI 专业名称（开课表表名）
            String infoo = info.getText().toString();
            intent.putExtra("courseTB",infoo);

            startActivity(intent);

        }
    }
    //========================================================
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
//控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());

        adapter = new CurriculumsListAdapter(CourseBegin.this, listCurriculumses);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){


        //测试用例
        final  List<Tb_course_mes> l2 = new ArrayList<Tb_course_mes>();
        Tb_course_mes t1 = new Tb_course_mes("软件工程专业", "209902", "2100.01.20", "2100.01.21");
        l2.add(t1);
        Tb_course_mes t2 = new Tb_course_mes("数学类专业", "209902", "2100.01.20", "2100.01.21");
        l2.add(t2);
        Log.i(gson.toJson(l2),"!!!!!!!");

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type",""+5);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            l = gson.fromJson(gson.toJson(l2), type);
                            l = g.fromJson(response, type);
                            Intent intent = getIntent();
                            //获取工号，判定教师 所属系即专业
                            String courseTB = intent.getStringExtra("courseTB");
                            bundle = new queryDB().queryDB(CourseBegin.this, tableName, l);
                            int i;
                            String tmp;
                            int rows = bundle.getInt("rows");
                            ListCurriculums cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                if ((bundle.getString(tmp + 0)).equals(courseTB)) {
                                    cell = new ListCurriculums(bundle.getString(tmp + 0), bundle.getString(tmp + 1),
                                            "截止日期:", bundle.getString(tmp + 2));
                                    listCurriculumses.add(cell);
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
                            Toast.makeText(CourseBegin.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

//                            l = gson.fromJson(gson.toJson(l2), type);
                            Intent intent = getIntent();
                            //获取工号，判定教师 所属系即专业
                            String courseTB = intent.getStringExtra("courseTB");
                            bundle = new queryDB().queryDB(CourseBegin.this, tableName, l);
                            int i;
                            String tmp;
                            int rows = bundle.getInt("rows");
                            ListCurriculums cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                if ((bundle.getString(tmp + 0)).equals(courseTB)) {
                                    cell = new ListCurriculums(bundle.getString(tmp + 0), bundle.getString(tmp + 1),
                                            "截止日期:", bundle.getString(tmp + 2));
                                    listCurriculumses.add(cell);
                                }
                            }
                            initView();

                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //=========================================================

    //================选课弹出框============================================
    private void showDialog(){   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        View view  = inflater.inflate(R.layout.teacher_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //需要改成服务器传来的数据=====================================

        TextView grade = (TextView) view.findViewById(R.id.grade);
        grade.setText("2012");
        TextView number = (TextView) view.findViewById(R.id.number);
        number.setText("180");
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText("专业必修");
        TextView credit = (TextView) view.findViewById(R.id.credit);
        credit.setText("4");
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText("40");
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time);
        exp_time.setText(" ");
        TextView com_time = (TextView) view.findViewById(R.id.com_time);
        com_time.setText(" ");

        builder.setTitle("课程1");
        //===========================================================

        builder.setView(view);
        builder.setPositiveButton("选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialog1();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
    private void showDialog1(){   //显示课程信息填写对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        final View view1  = inflater.inflate(R.layout.teacher_dialog1,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("课程1");    //需要改成服务器传来的数据==============================

        builder.setView(view1);
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //将填写的信息获取============================
                Spinner spinner1 = (Spinner)view1.findViewById(R.id.spinner1);
                String week_begin = spinner1.getSelectedItem().toString();
                Spinner spinner2 = (Spinner)view1.findViewById(R.id.spinner2);
                String week_end = spinner2.getSelectedItem().toString();
                String be_weeks=week_begin+"-"+week_end;
                EditText name_teacher = (EditText) view1.findViewById(R.id.name);
                String t_name = name_teacher.getText().toString();
                EditText note1 = (EditText) view1.findViewById(R.id.note);
                String remark = note1.getText().toString();

                tb_teacher_declare.setBe_weeks(be_weeks);
                tb_teacher_declare.setT_name(t_name);
                tb_teacher_declare.setRemark(remark);
                //剩下的待续。。。！！！！

                tb_teacher_declare_json=gson.toJson(tb_teacher_declare);

                //=======================================================
                showDialog2();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
    private void showDialog2(){      //显示是否确认选课提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意！！！！！");
        builder.setMessage("课程一旦选了就不能修改，您是否确认要选课？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tb_teacher_declare_json",tb_teacher_declare_json);
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/sendCourseDeclare", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourseBegin.this, "报课成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
//                            Toast.makeText(Login.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            Log.i("Login", "服务器访问失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourseBegin.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("info", e.toString());
                }
                //将填写的信息发送到服务器==========================================
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
    //===============================================================
}