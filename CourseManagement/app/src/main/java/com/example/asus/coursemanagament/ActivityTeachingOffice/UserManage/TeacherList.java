package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.coursemanagament.R;

import org.w3c.dom.Text;

public class TeacherList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list2);

        //返回监听事件============================================
        ImageView ivw_back=(ImageView)findViewById(R.id.ivw_back);
        ivw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //============================================

        //跳转到单个导入============================================
        TextView tvw_single=(TextView)findViewById(R.id.tvw_single);
        tvw_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherList.this,SingleImport.class);
                startActivity(intent);
            }
        });
        //============================================

        //跳转到批量导入============================================
        TextView tvw_batch=(TextView)findViewById(R.id.tvw_batch);
        tvw_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherList.this,BatchImport.class);
                startActivity(intent);
            }
        });
        //============================================
    }
}
