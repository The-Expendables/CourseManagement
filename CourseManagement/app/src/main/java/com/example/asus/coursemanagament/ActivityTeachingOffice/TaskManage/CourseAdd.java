package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.DBOpenHelper;
import com.example.asus.coursemanagament.SQLite_operation.Tb_course;
import com.example.asus.coursemanagament.SQLite_operation.Tb_course_mes;
import com.example.asus.coursemanagament.UiCustomViews.ExcelUtil;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
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
    private EditText time_teacher;
    private EditText time_department;
    private String cardnumber;
    private DBOpenHelper dbOpenHelper=new DBOpenHelper(CourseAdd.this);
    private String table_json;
    private String course_mes_json;
    private Gson gson=new Gson();
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
        btn_time_teacher = (Button)findViewById(R.id.btn_time_teacher);
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
        final TextView edtt_time_department=(TextView) findViewById(R.id.edtt_time_department);
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
        time_teacher = (EditText) findViewById(R.id.time_teacher);
        time_department = (EditText) findViewById(R.id.time_department);
        btn_release = (Button) findViewById(R.id.btn_release);
        btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Tb_course_mes tb_course_mes=new Tb_course_mes();

                    TextView edtt_tablename = (EditText) findViewById(R.id.edtt_tablename);
                    tb_course_mes.setTable_name(edtt_tablename.getText().toString());

                    Spinner sp_term = (Spinner) findViewById(R.id.sp_term);
                    sp_term.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            cardnumber = CourseAdd.this.getResources().getStringArray(R.array.term)[arg2];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                    tb_course_mes.setTerm(cardnumber);

//                    TextView edtt_time_teacher = (EditText) findViewById(R.id.edtt_time_teacher);
                    tb_course_mes.setTeacher_time(edtt_time_teacher.getText().toString());

//                    TextView edtt_time_department=(EditText) findViewById(R.id.edtt_time_department);
                    tb_course_mes.setDepartment_time(edtt_time_department.getText().toString());

                    Toast.makeText(CourseAdd.this,"添加成功,请查看发布栏.",Toast.LENGTH_SHORT).show();

                    course_mes_json=gson.toJson(tb_course_mes);

                    //数据上传到服务器===============================================================
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("table_json", table_json);
                    params.put("course_mes_json",course_mes_json);
                    try {
                        HttpUtil.doPost(GlobalVariables.URL + "/sendCoursetable", params, new HttpCallbackListener() {
                            @Override
                            public void onFinish(final String response) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CourseAdd.this,"报课成功",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e) {
//                            Toast.makeText(Login.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                Log.i("CourseAdd", "服务器访问失败");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CourseAdd.this,"服务器访问失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("info", e.toString()+"跑了吗跑了吗！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                    }
                    //================================

                    //把数据导入本地数据库============================================================
                    /*
                    SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("表名",tb_course_mes.getTable_name());
                    values.put("学期",tb_course_mes.getTerm());
                    values.put("教师报课截止时间",tb_course_mes.getTeacher_time());
                    values.put("系负责人审核截止日期",tb_course_mes.getDepartment_time());
                    db.insert("发布表",null,values);
                    */
                    //=================================

                    Intent intent = new Intent(CourseAdd.this, TaskManage.class);
                    startActivity(intent);
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

                String tablename = "";
                switch (tmp[0]) {
                    case "2015学年下学期计算机科学与技术专业 开课计划书":
                        tablename = "计算机专业课程表";
                        break;
                    case "2015学年下学期 数学类（实验班）专业 开课计划书":
                        tablename = "数学类（实验班）专业课程表";
                        break;
                    case "2015学年下学期数学类 开课计划书":
                        tablename = "数学专业课程表";
                        break;
                    default:
                        Log.i("info", "tablename not found");
                        break;
                }
                //===========================================
                Log.i("info", "tablenme:" + tablename);

                //把报课表打包到table_json===========================================
                List<Tb_course> list_tb_courses=new ArrayList<Tb_course>();
                for (int i = 3; i < list.size(); i++) {
                    String[] str = (String[]) list.get(i);
                    Tb_course tb_course =
                            new Tb_course("0",str[0],str[1],str[2],str[3],str[4],str[5],str[6],str[7],str[8],str[9],str[10],str[11]);
                    list_tb_courses.add(tb_course);
                }
                table_json=gson.toJson(list_tb_courses);
                //===========================================

                //数据导入数据库===========================================
                // list.get(i);list.size(); String[] str=(String[])list.get(i);
                /*
                int id = 0;
                for (int i = 3; i < list.size(); i++) {
                    String[] str = (String[]) list.get(i);
                    SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                    ContentValues values = new ContentValues();

                    values.put("年级", str[0]);
                    values.put("专业", str[1]);
                    values.put("专业人数", str[2]);
                    values.put("课程名称", str[3]);
                    values.put("选修类型", str[4]);
                    values.put("学分", str[5]);
                    values.put("学时", str[6]);
                    values.put("实验学时", str[7]);
                    values.put("上机学时", str[8]);
                    values.put("起讫周序", str[9]);
                    values.put("任课教师", str[10]);
                    values.put("备注", str[11]);
                    db.insert(tablename, null, values);
//                      Tb_course tb_course=new Tb_course("1",str[0],str[1],str[2],str[3],str[4],str[5],str[6],str[7],str[8],str[9],str[10],str[11]);
//                        Log.i("info",str[0]);
//                        SQLOperateImpl test = new SQLOperateImpl(CourseAdd.this);
//                        test.add_course(tb_course);

                }
                Toast.makeText(CourseAdd.this, "导入成功！", Toast.LENGTH_SHORT).show();
                */
                //===========================================
            }
        });
        picker.showAtBottom();
    }

    //下拉栏监听事件===========================================
    //================================================
}
