package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_teacher;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class TeacherInfo extends AppCompatActivity {
    private Gson gson=new Gson();
    Bundle bundle;

    private ImageView ivw_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        String t_json=intent.getStringExtra("teacherInfo");

        Log.i("info","<<>>"+t_json);

        Tb_teacher tb_teacher=gson.fromJson(t_json,Tb_teacher.class);


        EditText edtt_id=(EditText)findViewById(R.id.edtt_id);
        edtt_id.setText(tb_teacher.getId());

        EditText edtt_password=(EditText)findViewById(R.id.edtt_password);
        edtt_password.setText(tb_teacher.getPassword());

        EditText edtt_name=(EditText)findViewById(R.id.edtt_name1);
        edtt_name.setText(tb_teacher.getName());

        EditText edtt_sex=(EditText)findViewById(R.id.edtt_sex);
        edtt_sex.setText(tb_teacher.getSex());

        EditText edtt_department=(EditText)findViewById(R.id.edtt_department);
        edtt_department.setText(tb_teacher.getDepartment());

        EditText edtt_birth=(EditText)findViewById(R.id.edtt_birth);
        edtt_birth.setText(tb_teacher.getBirth());

        EditText edtt_phone=(EditText)findViewById(R.id.edtt_phone);
        edtt_phone.setText(tb_teacher.getPhone());

        EditText edtt_email=(EditText)findViewById(R.id.edtt_email);
        edtt_email.setText(tb_teacher.getEmail());

    }
    //返回按钮================================================
    public void onBtn_back(View view){ finish(); }
    //=========================

    //取消按钮================================================
    public void onBtn_cancel(View view){
        finish();
    }
    //=========================

    //添加按钮================================================
    public void onBtn_save(View view){


        EditText edtt_id=(EditText)findViewById(R.id.edtt_id);
        String id=edtt_id.getText().toString();

        EditText edtt_password=(EditText)findViewById(R.id.edtt_password);
        String password=edtt_password.getText().toString();

        EditText edtt_name=(EditText)findViewById(R.id.edtt_name1);
        String name=edtt_name.getText().toString();

        EditText edtt_sex=(EditText)findViewById(R.id.edtt_sex);
        String sex=edtt_sex.getText().toString();

        EditText edtt_department=(EditText)findViewById(R.id.edtt_department);
        String department=edtt_department.getText().toString();

        EditText edtt_birth=(EditText)findViewById(R.id.edtt_birth);
        String birth=edtt_birth.getText().toString();

        EditText edtt_phone=(EditText)findViewById(R.id.edtt_phone);
        String phone=edtt_phone.getText().toString();

        EditText edtt_email=(EditText)findViewById(R.id.edtt_email);
        String email=edtt_email.getText().toString();

        if(id.equals("")||password.equals("")||department.equals("")||name.equals("")){
            Toast.makeText(TeacherInfo.this,"信息不完整",Toast.LENGTH_SHORT).show();
        }
        else {
            Tb_teacher teacher = new Tb_teacher(id, password, department, name, sex, birth, email, phone);

            String teacher_json = gson.toJson(teacher);
//        Log.i("info", "<<>>" + teacher_json);

            Map<String, String> params = new HashMap<String, String>();
            Log.i("info", "<<<>>>" + teacher_json);
            params.put("table_name", "教师信息表");
            params.put("info_json", teacher_json);
            try {
                HttpUtil.doPost(GlobalVariables.URL + "/PortForUpdate", params, new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TeacherInfo.this, response, Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(TeacherInfo.this,TeacherList.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TeacherInfo.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("info", e.toString());
            }
        }
    }


}
