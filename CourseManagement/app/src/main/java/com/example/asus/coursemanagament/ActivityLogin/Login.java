package com.example.asus.coursemanagament.ActivityLogin;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityDepartment.TaskList;
import com.example.asus.coursemanagament.ActivityTeacher.CourseDeclare;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TaskManage;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {


    private Button btn_login;   //登录按钮
    private EditText edtt_userName; //用户名输入框
    private EditText edtt_password; //密码输入框
    private RadioGroup rdgp;    //单选按钮组
    private int number = 1; //判断角色身份
    private TextView tvw_forget; //忘记密码？
    String username = new String();
    String zhuanye = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();

    }

    //====================================================================
    //忘记密码？ 事件监听============================================
    class ForgetClickListener implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            final AlertDialog.Builder Dia = new AlertDialog.Builder(Login.this);

            Dia.setTitle("忘记密码？");
            Dia.setMessage("请联系教学办负责人!" + "\n" + "联系电话:18753621145");
            Dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            });
            Dia.create().show();
        }
    }
//===============================================================================

    //单选按钮组 事件监听=============================================
    class RadioClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rdgp_teacher:
                    number = 1;
                    break;
                case R.id.rdgp_department:
                    number = 2;
                    break;
                case R.id.rdgp_office:
                    number = 3;
                    break;
                default:
                    break;
            }
        }
    }

    //============================================================================
//控件绑定，监听器绑定===================================================
    private void initView() {
        edtt_userName = (EditText) findViewById(R.id.edtt_userName); //绑定 用户名输入
        edtt_password = (EditText) findViewById(R.id.edtt_passWord); //绑定 密码输入

        rdgp = (RadioGroup) findViewById(R.id.rdgp);  //绑定 单选按钮组
        rdgp.setOnCheckedChangeListener(new RadioClickListener()); //为 单选按钮组绑定事件监听器

        btn_login = (Button) findViewById(R.id.btn_login); //绑定 登录按钮
        btn_login.setOnClickListener(new LoginClickListener());  //为 登录按钮绑定事件监听器

        tvw_forget = (TextView) findViewById(R.id.tvw_forget);  //绑定 “忘记密码？”
        tvw_forget.setOnClickListener(new ForgetClickListener());    //为"忘记密码"绑定事件监听器

    }


    //登录按钮 事件监听=================================================
    class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            username = edtt_userName.getText().toString();   //获取账号
            String password = edtt_password.getText().toString();  //获取密码

            GlobalVariables.userId = username;
            GlobalVariables.password = password;

            if (edtt_userName.length() < 1 || edtt_password.length() < 1) {
                Toast.makeText(Login.this, "账号或密码为空，请重新输入", Toast.LENGTH_SHORT).show();
            } else if (edtt_userName.length() < 5) {
                Toast.makeText(Login.this, "账号格式有误，请重新输入", Toast.LENGTH_SHORT).show();
            } else if (edtt_password.length() < 5) {
                Toast.makeText(Login.this, "密码格式有误，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                //连接服务器不能删==================================================================
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "" + number);
                params.put("username", edtt_userName.getText().toString());
                params.put("password", edtt_password.getText().toString());
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/login", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.equals("true")) {

                                        goTo();
                                    } else {
                                        Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {

                            e.printStackTrace();
                            Log.i("Login", "服务器访问失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("info", e.toString());
                }
                //=====================================
            }
        }
    }
// =====================================================================

    private void goTo() {
        Intent intent = new Intent();
        switch (number) {
            case 1:
                intent.putExtra("gonghao", username);
                intent.setClass(Login.this, CourseDeclare.class);
                break;
            case 2:
                intent.putExtra("gonghao", username);
                intent.setClass(Login.this, TaskList.class);
                break;
            case 3:
                intent.setClass(Login.this, TaskManage.class);
                break;
        }
        Login.this.startActivity(intent);
    }

}
