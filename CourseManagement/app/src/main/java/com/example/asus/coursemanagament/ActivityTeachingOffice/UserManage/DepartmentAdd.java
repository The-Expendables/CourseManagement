package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.coursemanagament.R;

public class DepartmentAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_add);

        //返回监听事件===============================
        ImageView ivw_back=(ImageView)findViewById(R.id.ivw_back);
        ivw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //==============================
    }
}
