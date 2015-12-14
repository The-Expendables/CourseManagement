package com.example.asus.coursemanagament.ActivityDepartment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Util.SlidingMenu;

public class PasswordChange2 extends Activity {
    private SlidingMenu mLeftMenu_department;
    private Button btn_cancle2;
    private EditText edtt_old_password2;
    private EditText edtt_new_password5;
    private EditText edtt_new_password6;
    private Button btn_commit2;

    private Button btn_task2;
    private Button btn_password2;
    private Button btn_exit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change2);
        mLeftMenu_department = (SlidingMenu) findViewById(R.id.department_menu);

        //设置侧滑菜单任务管理跳转=========================
        btn_task2 = (Button) findViewById(R.id.btn_task2);
        btn_task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange2.this, TaskList.class);
                startActivity(intent);
            }
        });
        //===================================================

        //设置侧滑菜单修改密码跳转==============================
        btn_password2 = (Button) findViewById(R.id.btn_password2);
        btn_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLeftMenu_department.toggle();
            }
        });
        //=====================================================

        //设置侧滑菜单退出跳转===============================
        btn_exit2 = (Button) findViewById(R.id.btn_exit2);
        btn_exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange2.this,Login.class);
                startActivity(intent);
            }
        });
        //===================================================

        //设置取消密码监听事件======================================
        edtt_old_password2 = (EditText) findViewById(R.id.edtt_old_password2);
        edtt_new_password5 = (EditText) findViewById(R.id.edtt_new_password5);
        edtt_new_password6 = (EditText) findViewById(R.id.edtt_new_password6);
        btn_cancle2 = (Button) findViewById(R.id.btn_cancle2);
        btn_cancle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtt_old_password2.setText("");
                edtt_new_password5.setText("");
                edtt_new_password6.setText("");
            }
        });
        //========================================================

        //设置确定的监听事件==================================
        btn_commit2 = (Button) findViewById(R.id.btn_commit2);
        btn_commit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先假设初始密码为12345
                if ((edtt_old_password2.length()<5) || !edtt_old_password2.getText().toString().equals("12345")) {
                    Toast.makeText(PasswordChange2.this, "原密码输入有误，请重新输入原密码", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    if(edtt_new_password5.length()<6) {
                        Toast.makeText(PasswordChange2.this, "新密码长度过短，请重新输入", Toast
                                .LENGTH_SHORT).show();
                    }
                    else{
                        if(!edtt_new_password6.getText().toString().equals(edtt_new_password5.getText()
                                .toString())) {
                            Toast.makeText(PasswordChange2.this, "两次输入的新密码不同，请重新输入",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final AlertDialog.Builder Dia=new AlertDialog.Builder(PasswordChange2.this);
                            Dia.setTitle("密码修改成功");
                            Dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PasswordChange2.this, PasswordChange2.class);
                                    startActivity(intent);
                                }
                            });
                            Dia.create().show();
                        }
                    }
                }
            }
        });
        //===================================================================

    }
    //侧滑菜单===============================
    public void toggleMenu(View view){
        mLeftMenu_department.toggle();
    }
    //=========================================
}
