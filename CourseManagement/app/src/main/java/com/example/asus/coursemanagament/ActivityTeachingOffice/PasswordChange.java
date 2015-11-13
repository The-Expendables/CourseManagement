package com.example.asus.coursemanagament.ActivityTeachingOffice;

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
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TaskManage;
import com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage.UserManage;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

public class PasswordChange extends Activity {
    private SlidingMenu mLeftMenu_teachingoffice;
    private Button btn_cancle;
    private EditText edtt_old_password;
    private EditText edtt_new_password1;
    private EditText edtt_new_password2;
    private Button btn_commit;
    private Button btn_task;
    private Button btn_user;
    private Button btn_password;
    private Button btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        mLeftMenu_teachingoffice = (SlidingMenu) findViewById(R.id.teachingoffice_menu);

        //设置取消密码监听事件======================================
        edtt_old_password = (EditText) findViewById(R.id.edtt_old_password);
        edtt_new_password1 = (EditText) findViewById(R.id.edtt_new_password1);
        edtt_new_password2 = (EditText) findViewById(R.id.edtt_new_password2);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtt_old_password.setText("");
                edtt_new_password1.setText("");
                edtt_new_password2.setText("");
            }
        });
        //========================================================

        //设置确定的监听事件==================================
        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先假设初始密码为12345
                if ((edtt_old_password.length()<5) || !edtt_old_password.getText().toString().equals("12345")) {
                    Toast.makeText(PasswordChange.this, "原密码输入有误，请重新输入原密码", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    if(edtt_new_password1.length()<6) {
                        Toast.makeText(PasswordChange.this, "新密码长度过短，请重新输入", Toast
                                .LENGTH_SHORT).show();
                    }
                    else{
                        if(!edtt_new_password2.getText().toString().equals(edtt_new_password1.getText()
                                .toString())) {
                            Toast.makeText(PasswordChange.this, "两次输入的新密码不同，请重新输入",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final AlertDialog.Builder Dia=new AlertDialog.Builder(PasswordChange.this);
                            Dia.setTitle("密码修改成功");
                            Dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PasswordChange.this, PasswordChange.class);
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

        //设置侧滑菜单任务管理跳转=================================
        btn_task = (Button) findViewById(R.id.btn_task);
        btn_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange.this, TaskManage.class);
                startActivity(intent);
            }
        });
        //==========================================================

        //设置侧滑菜单用户管理跳转====================================
        btn_user = (Button) findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange.this, UserManage.class);
                startActivity(intent);
            }
        });
        //=============================================================

        //设置侧滑菜单修改密码跳转======================================
        btn_password = (Button) findViewById(R.id.btn_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu_teachingoffice.toggle();
            }
        });
        //============================================

        //设置侧滑菜单退出跳转=======================================
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange.this, Login.class);
                startActivity(intent);
            }
        });
        //===========================================================
    }
    //侧滑菜单===============================
    public void toggleMenu(View view){
        mLeftMenu_teachingoffice.toggle();
    }
    //=========================================
}
