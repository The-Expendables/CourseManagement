package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacher_declare;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wwk on 2015/11/14.
 */
public class CourseChoose  extends Activity {

    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Bundle bundle;
    TotalCoureseListAdapter adapter;
    private String title;

    private Gson gson=new Gson();
    private String tb_teacher_declare_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_choose);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        initView();
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

        adapter = new TotalCoureseListAdapter(CourseChoose.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        Intent intent = getIntent();
        //获取数据
        String zhuanye = intent.getStringExtra("zhuanye");
        Bundle bundle = new queryDB().queryDB(this, zhuanye+"课程表");
        int rows = bundle.getInt("rows");
        int cols = bundle.getInt("cols");
        int i;
        String tmp;
        ListTotalCourse cell;
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            cell = new ListTotalCourse(bundle.getString(tmp + 4));
            listInfos.add(cell);
        }
    }
    //=========================================================

    //================选课弹出框============================================
    private void showDialog2(int i){   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        View view  = inflater.inflate(R.layout.teacher_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int j=0;
        TextView grade = (TextView) view.findViewById(R.id.grade1);
        grade.setText(bundle.getString("cell"+i+j)); j++;
        TextView major = (TextView) view.findViewById(R.id.major1);
        major.setText(bundle.getString("cell" + i + j)); j++;
        TextView number = (TextView) view.findViewById(R.id.number1);
        number.setText(bundle.getString("cell"+i+j)); j++;
        TextView type = (TextView) view.findViewById(R.id.type1);
        type.setText(bundle.getString("cell" + i + j)); j++;
        builder.setTitle(bundle.getString("cell" + i + j));
        title = bundle.getString("cell" + i + j); j++;//弹出框标题为课程名称
        TextView credit = (TextView) view.findViewById(R.id.credit1);
        credit.setText(bundle.getString("cell" + i + j)); j++;
        TextView time = (TextView) view.findViewById(R.id.time1);
        time.setText(bundle.getString("cell" + i + j)); j++;
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time1);
        exp_time.setText(bundle.getString("cell" + i + j)); j++;
        TextView com_time = (TextView) view.findViewById(R.id.com_time1);
        com_time.setText(bundle.getString("cell" + i + j)); j++;

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
        builder.setTitle(title);
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
                EditText note1 = (EditText) view1.findViewById(R.id.remark);
                String remark = note1.getText().toString();
                Tb_teacher_declare tb_teacher_declare = new Tb_teacher_declare();
                tb_teacher_declare.setBe_weeks(be_weeks);
                tb_teacher_declare.setT_name(t_name);
                tb_teacher_declare.setRemark(remark);

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
                                    Toast.makeText(CourseChoose.this, "报课成功", Toast.LENGTH_SHORT).show();
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

