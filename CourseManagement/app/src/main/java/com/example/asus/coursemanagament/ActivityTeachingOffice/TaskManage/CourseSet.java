package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course_mes;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CourseSet extends AppCompatActivity {

    private ImageView imgvw_back1;
    private Button btn_solve;
    private String cardnumber;
    private String course_mes_json;
    private Gson gson=new Gson();
    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private Button btn_time_teacher;
    private Button btn_time_department;
    private String table_name;
    private String info_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_set);
        Intent intent = getIntent();
        table_name = intent.getStringExtra("table_name");
//        Toast.makeText(CourseSet.this,table_name,Toast.LENGTH_SHORT).show();
        TextView textView =(TextView)findViewById(R.id.title1);
        textView.setText(table_name);
        //设置返回的监听事件==============================
        imgvw_back1 = (ImageView) findViewById(R.id.imgvw_back1);
        imgvw_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseSet.this, TaskManage.class);
                startActivity(intent);
            }
        });
        //===================================================

        //时间选择器=================================================
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        btn_time_teacher = (Button)findViewById(R.id.btn_time_teacher1);
        final TextView edtt_time_teacher = (TextView) findViewById(R.id.tvw_time_teacher);
        btn_time_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseSet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtt_time_teacher.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });
        btn_time_department = (Button) findViewById(R.id.btn_time_department1);
        final TextView edtt_time_department=(TextView) findViewById(R.id.tvw_time_department);
        btn_time_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseSet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtt_time_department.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });
        //=======================================================

        //设置课表修改监听事件=============================
        btn_solve = (Button) findViewById(R.id.btn_solve);
        btn_solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tb_course_mes tb_course_mes=new Tb_course_mes();
                tb_course_mes.setTable_name(table_name);
                Spinner sp_term = (Spinner) findViewById(R.id.spinner4);
                cardnumber = sp_term.getSelectedItem().toString();
//                sp_term.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                        cardnumber = CourseSet.this.getResources().getStringArray(R.array.term)[arg2];
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                        // TODO Auto-generated method stub
//                    }
//                });
                tb_course_mes.setTerm(cardnumber);
                tb_course_mes.setTeacher_time(edtt_time_teacher.getText().toString());
                tb_course_mes.setDepartment_time(edtt_time_department.getText().toString());
                info_json=gson.toJson(tb_course_mes);

                Log.i("info","success"+info_json);
                //数据上传到服务器===============================================================
                Map<String, String> params = new HashMap<String, String>();
                params.put("table_name", "发布表");
                params.put("info_json",info_json);
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/PortForUpdate", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourseSet.this, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            Log.i("CourseAdd", "服务器访问失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourseSet.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("info", e.toString()+"跑了吗跑了吗！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                }
                //================================


                Intent intent = new Intent(CourseSet.this, TaskManage.class);
                startActivity(intent);
            }
        });
        //==========================================================


    }
}
