package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course;
import com.example.asus.coursemanagament.Tb.Tb_course_mes;
import com.example.asus.coursemanagament.Util.ExcelUtil;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;
import jxl.read.biff.BiffException;

public class CourseAdd extends AppCompatActivity {
    private ImageView imgvw_back1;
    private Button btn_release;
    private String cardnumber;
    private String table_json;
    private String course_mes_json;
    private Gson gson = new Gson();
    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private Button btn_time_teacher;
    private Button btn_time_department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        //设置返回的监听事件==============================
        imgvw_back1 = (ImageView) findViewById(R.id.imgvw_back1);
        imgvw_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseAdd.this, TaskManage.class);
                startActivity(intent);
            }
        });
        //===================================================

        //时间选择器=================================================
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        btn_time_teacher = (Button) findViewById(R.id.btn_time_teacher);
        final TextView edtt_time_teacher = (TextView) findViewById(R.id.edtt_time_teacher);
        btn_time_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtt_time_teacher.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });
        btn_time_department = (Button) findViewById(R.id.btn_time_department);
        final TextView edtt_time_department = (TextView) findViewById(R.id.edtt_time_department);
        btn_time_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtt_time_department.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });
        //=======================================================

        //设置课表发布确认监听事件=============================
        btn_release = (Button) findViewById(R.id.btn_release);
        btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tb_course_mes tb_course_mes = new Tb_course_mes();

                TextView edtt_tablename = (EditText) findViewById(R.id.edtt_tablename);
                tb_course_mes.setTable_name(edtt_tablename.getText().toString());

                Spinner sp_term = (Spinner) findViewById(R.id.sp_term);
                cardnumber = sp_term.getSelectedItem().toString();

                tb_course_mes.setTerm(cardnumber);

                tb_course_mes.setTeacher_time(edtt_time_teacher.getText().toString());


                tb_course_mes.setDepartment_time(edtt_time_department.getText().toString());

                course_mes_json = gson.toJson(tb_course_mes);
                Log.i("info", course_mes_json + "!!!!!!!!!!");
                //数据上传到服务器===============================================================
                Map<String, String> params = new HashMap<String, String>();
                params.put("table_json", table_json);
                params.put("course_mes_json", course_mes_json);

                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/sendCoursetable", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (response.equals("true")) {
                                        Toast.makeText(CourseAdd.this, "添加成功,请查看发布栏.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CourseAdd.this, TaskManage.class);
                                        startActivity(intent);
                                    } else if (response.equals("false"))
                                        Toast.makeText(CourseAdd.this, "服务器内部出错，请联系运维人员.", Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(CourseAdd.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //================================

            }

        });
        //==========================================================
    }

    //添加按钮监听事件================================================
    public void onBtn_filepicker(View view) {
        FilePicker picker = new FilePicker(this);
        picker.setShowHideDir(false);
        picker.setInitPath(Common.getRootPath(this) + "Download/");
        //picker.setAllowExtensions(new String[]{".apk"});
        picker.setMode(FilePicker.Mode.File);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
                //读取excel表格===========================================
                List list = new ArrayList<String[]>();
                try {
                    list = ExcelUtil.readExcel(currentPath);
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                }
                //======================================

                //绑定课程名称到界面========================================
                TextView edtt_tablename = (EditText) findViewById(R.id.edtt_tablename);
                String[] tmp = (String[]) list.get(0);
                edtt_tablename.setText(tmp[0]);
                //===========================================

                //把报课表打包到table_json===========================================
                List<Tb_course> list_tb_courses = new ArrayList<Tb_course>();
                for (int i = 3; i < list.size(); i++) {
                    String[] str = (String[]) list.get(i);
                    Tb_course tb_course =
                            new Tb_course("0", str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7], str[8], str[9], str[10], str[11]);
                    list_tb_courses.add(tb_course);
                }
                table_json = gson.toJson(list_tb_courses);
                //===========================================


            }
        });
        picker.showAtBottom();
    }

}
