package com.example.asus.coursemanagament.ActivityTeacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

public class CourseDeclare extends AppCompatActivity {
    private SlidingMenu mLeftMenu_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_declare);
        mLeftMenu_teacher = (SlidingMenu) findViewById(R.id.teacher_menu);
    }
    public void toggleMenu(View view){
        mLeftMenu_teacher.toggle();
    }
}
