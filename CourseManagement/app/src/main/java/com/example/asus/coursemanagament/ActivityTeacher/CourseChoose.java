package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course;
import com.example.asus.coursemanagament.Tb.Tb_teacher_declare;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.example.asus.coursemanagament.Util.TotalCoureseListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wwk on 2015/11/14.
 */
public class CourseChoose extends Activity {
    String tableName = new String();
    String courseTB = new String();
    String gonghao = new String();
    private List<Tb_course> l = new ArrayList<Tb_course>();

    private Type type = new TypeToken<List<Tb_course>>() {
    }.getType();
    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Bundle bundle;
    TotalCoureseListAdapter adapter;
    private String title;
    private String Grade;
    private String be_weeks;
    private String t_name;
    private String remark;
    private Gson gson = new Gson();
    private String tb_teacher_declare_json;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //获取数据，eg：网络工程专业：。。。。
        courseTB = intent.getStringExtra("courseTB");
        gonghao = intent.getStringExtra("gonghao");
        tableName = courseTB;
        Log.i(gonghao, "!!!!!!gonghao");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_choose);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        TextView teacherCourse = (TextView) findViewById(R.id.teacherCourse);
        teacherCourse.setText(tableName);
    }

    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            showDialog2(position);
        }
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
    class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    //============================================================
//控件绑定，事件监听===========================================
    private void initView() {
        search = (EditText) findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());
        iv_left = (ImageView) findViewById(R.id.left);
        iv_left.setOnClickListener(new MyOnClickListner());
        adapter = new TotalCoureseListAdapter(CourseChoose.this, listInfos);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {

        progress = new ProgressDialog(CourseChoose.this);
        progress.setMessage("加载中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);
        progress.show();

        //连接服务器================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type", "" + 1);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            l = gson.fromJson(response, type);
                            progress.cancel();
                            bundle = new queryDB().queryDB(CourseChoose.this, tableName, l);
                            int rows = bundle.getInt("rows");
                            int cols = bundle.getInt("cols");
                            int i;
                            String tmp;
                            ListTotalCourse cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                cell = new ListTotalCourse(bundle.getString(tmp + 3));
                                listInfos.add(cell);

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
                            progress.cancel();
                            Toast.makeText(CourseChoose.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //=========================================================


    //选过一次就不能再选的逻辑判断，添加在listviewOnItemclick里面===============================
//    if(listview.color = "black"){
//        showDialog2(i);
//    }
//    else{
//        showDialog_end();
//    }
    //报课成功一次后显示对话框======================================
    private void showDialog_end() {   //显示确认对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您已经报过该课程，若要修改，请联系教务办。");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }


    //================选课弹出框============================================
    private void showDialog2(int i) {   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        View view = inflater.inflate(R.layout.teacher_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int j = 0;
        TextView grade = (TextView) view.findViewById(R.id.grade1);
        grade.setText(bundle.getString("cell" + i + j));
        Grade = bundle.getString("cell" + i + j);
        j++;
        TextView major = (TextView) view.findViewById(R.id.major1);
        major.setText(bundle.getString("cell" + i + j));
        j++;
        TextView number = (TextView) view.findViewById(R.id.number1);
        number.setText(bundle.getString("cell" + i + j));
        j++;
        builder.setTitle(bundle.getString("cell" + i + j));
        title = bundle.getString("cell" + i + j);
        j++;//弹出框标题为课程名称
        TextView type = (TextView) view.findViewById(R.id.type1);
        type.setText(bundle.getString("cell" + i + j));
        j++;
        TextView credit = (TextView) view.findViewById(R.id.credit1);
        credit.setText(bundle.getString("cell" + i + j));
        j++;
        TextView time = (TextView) view.findViewById(R.id.time1);
        time.setText(bundle.getString("cell" + i + j));
        j++;
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time1);
        exp_time.setText(bundle.getString("cell" + i + j));
        j++;
        TextView com_time = (TextView) view.findViewById(R.id.com_time1);
        com_time.setText(bundle.getString("cell" + i + j));
        j++;

        builder.setView(view);
        builder.setPositiveButton("选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialog11();
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

    private void showDialog11() {   //显示课程信息填写对话框
        LayoutInflater inflater = LayoutInflater.from(CourseChoose.this);    //引入自定义布局
        final View view1 = inflater.inflate(R.layout.teacher_dialog3, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseChoose.this);
        builder.setTitle("申报该课程，请填写：");
        builder.setView(view1);
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //将填写的信息获取============================
                Spinner spinner1 = (Spinner) view1.findViewById(R.id.spinner6);
                String week_begin = spinner1.getSelectedItem().toString();
                Spinner spinner2 = (Spinner) view1.findViewById(R.id.spinner7);
                String week_end = spinner2.getSelectedItem().toString();
                be_weeks = week_begin + "-" + week_end;
                EditText name_teacher = (EditText) view1.findViewById(R.id.name);
                t_name = name_teacher.getText().toString();
                EditText note1 = (EditText) view1.findViewById(R.id.remark);
                remark = note1.getText().toString();
                Tb_teacher_declare tb_teacher_declare = new Tb_teacher_declare();
                tb_teacher_declare.setTable_name(tableName);
                tb_teacher_declare.setCourse_name(title);
                tb_teacher_declare.setGrade(Grade);
                tb_teacher_declare.setBe_weeks(be_weeks);
                tb_teacher_declare.setId(GlobalVariables.userId);
                tb_teacher_declare.setT_name(t_name);
                tb_teacher_declare.setRemark(remark);
                tb_teacher_declare_json = gson.toJson(tb_teacher_declare);
                Log.i("info", "=========" + tb_teacher_declare_json);
                //=======================================================
                showDialog22();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }

    private void showDialog22() {      //显示是否确认选课提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意！！！！！");
        builder.setMessage("课程一旦选了就不能修改，您是否确认要选课？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tb_teacher_declare_json", tb_teacher_declare_json);
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/Send_teacher_declare", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourseChoose.this, "报课成功", Toast.LENGTH_SHORT).show();
                                    //=========================把listview的字体颜色改成红色的
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
                                    Toast.makeText(CourseChoose.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
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

