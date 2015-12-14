package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_teacher;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SingleImport extends AppCompatActivity {

    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_import);

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
    public void onBtn_add(View view){


        EditText edtt_id=(EditText)findViewById(R.id.edtt_id);
        String id=edtt_id.getText().toString();

        EditText edtt_password=(EditText)findViewById(R.id.edtt_password);
        String password=edtt_password.getText().toString();

        EditText edtt_name=(EditText)findViewById(R.id.edtt_name);
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

        Tb_teacher teacher=new Tb_teacher(id,password,department,name,sex,birth,email,phone);

        String teacher_json=gson.toJson(teacher);
        Log.i("info","<<>>"+teacher_json);

        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", "教师信息表");
        params.put("info_json", teacher_json);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/Insert_info", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           Toast.makeText(SingleImport.this,response,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SingleImport.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("info", e.toString());
        }
    }
    //=========================

}
