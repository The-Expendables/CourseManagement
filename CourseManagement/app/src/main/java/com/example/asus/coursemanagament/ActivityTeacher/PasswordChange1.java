package com.example.asus.coursemanagament.ActivityTeacher;

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

public class PasswordChange1 extends Activity {
    private SlidingMenu mLeftMenu_teacher;
    private Button btn_cancle1;
    private EditText edtt_old_password1;
    private EditText edtt_new_password3;
    private EditText edtt_new_password4;
    private Button btn_commit1;
    private Button btn_task1;
    private Button btn_password1;
    private Button btn_exit1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change1);
        mLeftMenu_teacher = (SlidingMenu) findViewById(R.id.teacher_menu);

        //设置侧滑菜单任务管理跳转=========================
        btn_task1 = (Button) findViewById(R.id.btn_task1);
        btn_task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange1.this, CourseDeclare.class);
                startActivity(intent);
            }
        });
        //===================================================

        //设置侧滑菜单修改密码跳转==============================
        btn_password1 = (Button) findViewById(R.id.btn_password1);
        btn_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLeftMenu_teacher.toggle();
            }
        });
        //=====================================================

        //设置侧滑菜单退出跳转===============================
        btn_exit1 = (Button) findViewById(R.id.btn_exit1);
        btn_exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChange1.this,Login.class);
                startActivity(intent);
            }
        });
        //===================================================

        //设置取消密码监听事件======================================
        edtt_old_password1 = (EditText) findViewById(R.id.edtt_old_password1);
        edtt_new_password3 = (EditText) findViewById(R.id.edtt_new_password3);
        edtt_new_password4 = (EditText) findViewById(R.id.edtt_new_password4);
        btn_cancle1 = (Button) findViewById(R.id.btn_cancle1);
        btn_cancle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtt_old_password1.setText("");
                edtt_new_password3.setText("");
                edtt_new_password4.setText("");
            }
        });
        //========================================================

        //设置确定的监听事件==================================
        btn_commit1 = (Button) findViewById(R.id.btn_commit1);
        btn_commit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtt_old_password1.length()<5) ) {
                    Toast.makeText(PasswordChange1.this, "原密码输入有误，请重新输入原密码", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    if(edtt_new_password3.length()<6) {
                        Toast.makeText(PasswordChange1.this, "新密码长度过短，请重新输入", Toast
                                .LENGTH_SHORT).show();
                    }
                    else{
                        if(!edtt_new_password4.getText().toString().equals(edtt_new_password3.getText()
                                .toString())) {
                            Toast.makeText(PasswordChange1.this, "两次输入的新密码不同，请重新输入",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final AlertDialog.Builder Dia=new AlertDialog.Builder(PasswordChange1.this);
                            Dia.setTitle("密码修改成功");
                            Dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PasswordChange1.this, PasswordChange1.class);
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
        mLeftMenu_teacher.toggle();
    }
    //=========================================
}
