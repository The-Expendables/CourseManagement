package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.coursemanagament.R;

public class DepartmentList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window_FEATURE_NO_TITLE);
        setContentView(R.layout.activity_department_list);

        //返回监听事件============================================
        ImageView ivw_1=(ImageView)findViewById(R.id.ivw_1);
        ivw_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //============================================

        //添加教师信息按钮监听事件=================================
        ImageView ivw_2=(ImageView)findViewById(R.id.ivw_2);
        ivw_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(DepartmentList.this,DepartmentAdd.class);
                startActivity(intent);
            }
        });
        //=================================

        //listview====================================

        //================================

    }
}
