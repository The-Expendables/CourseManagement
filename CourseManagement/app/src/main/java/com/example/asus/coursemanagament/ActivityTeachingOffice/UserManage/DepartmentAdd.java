package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_department;
import com.example.asus.coursemanagament.Tb.Tb_teacher;
import com.example.asus.coursemanagament.Tb.Tb_teacher_declare;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DepartmentAdd extends AppCompatActivity {
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_add);

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

        EditText edtt_department=(EditText)findViewById(R.id.edtt_department);
        String department=edtt_department.getText().toString();

        EditText edtt_phone=(EditText)findViewById(R.id.edtt_phone);
        String phone=edtt_phone.getText().toString();

        if(id.equals("")||password.equals("")||department.equals("")||name.equals("")||phone.equals("")){
            Toast.makeText(DepartmentAdd.this,"信息不完整",Toast.LENGTH_SHORT).show();
        }
        else {
            Tb_department tb_department = new Tb_department(id, password, department, name, phone);

            String department_json = gson.toJson(tb_department);
//        Log.i("info", "<<>>" + department_json);

            Map<String, String> params = new HashMap<String, String>();
            params.put("table_name", "系负责人信息表");
            params.put("info_json", department_json);
            try {
                HttpUtil.doPost(GlobalVariables.URL + "/Insert_info", params, new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DepartmentAdd.this, response, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DepartmentAdd.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
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
