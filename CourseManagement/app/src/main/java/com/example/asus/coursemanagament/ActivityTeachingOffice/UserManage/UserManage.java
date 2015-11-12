package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.coursemanagament.R;

public class UserManage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        //页面跳转
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
    }
}
