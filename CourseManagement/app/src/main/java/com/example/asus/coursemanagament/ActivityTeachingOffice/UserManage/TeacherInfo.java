package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.asus.coursemanagament.R;

public class TeacherInfo extends AppCompatActivity {

    private ImageView ivw_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    //控件绑定==============================
    private void initView(){
        ivw_back = (ImageView)findViewById(R.id.ivw_back);
        ivw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherInfo.this, TeacherList.class);
                startActivity(intent);
            }
        });
    }

}
