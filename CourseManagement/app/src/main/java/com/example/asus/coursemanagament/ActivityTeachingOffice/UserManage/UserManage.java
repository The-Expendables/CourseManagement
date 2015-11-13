package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.coursemanagament.ActivityLogin.Login;
import com.example.asus.coursemanagament.ActivityTeachingOffice.PasswordChange;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TaskManage;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

public class UserManage extends Activity {

    private SlidingMenu mLeftMenu_teachingoffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        //左侧按钮监听事件========================
        mLeftMenu_teachingoffice = (SlidingMenu) findViewById(R.id.teachingoffice_menu);
        //==========================

        //侧滑栏按钮监听事件========================

        //跳转到任务管理
        Button btn_task = (Button)findViewById(R.id.btn_task);
        btn_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManage.this,TaskManage.class);
                startActivity(intent);
            }
        });


        //跳转到修改密码
        Button btn_password=(Button)findViewById(R.id.btn_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManage.this,PasswordChange.class);
                startActivity(intent);
            }
        });

        //退出
        Button btn_exit=(Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManage.this,Login.class);
                startActivity(intent);
            }
        });

        //==========================

        //页面跳转===============================
        final ListView listvw=(ListView)findViewById(R.id.listvw);
        String[] ctype=new String[]{"系负责人","教师"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_activated_1,ctype);
        listvw.setAdapter(adapter);//关联listView
        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result=parent.getItemAtPosition(position).toString();
                if(result=="系负责人"){
                    Intent intent=new Intent(UserManage.this,DepartmentList.class);
                    intent.putExtra("type",result);//把关键字传递给下一个活动
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(UserManage.this,TeacherList.class);
                    intent.putExtra("type",result);//把关键字传递给下一个活动
                    startActivity(intent);
                }
            }
        });
        //==========================
    }
    //左侧按钮监听事件==========================
    public void toggleMenu(View view){
        mLeftMenu_teachingoffice.toggle();
    }
    //==========================
}
