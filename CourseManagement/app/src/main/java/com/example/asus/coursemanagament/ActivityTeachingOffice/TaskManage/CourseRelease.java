package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asus.coursemanagament.ListAdapter;
import com.example.asus.coursemanagament.R;

import java.util.ArrayList;
import java.util.List;

public class CourseRelease extends Activity {

    private EditText search;
    private List<ListInfo> listInfos= new ArrayList<ListInfo>(); //存放Item
    private ListView listView;
    ListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_release);

        initList();
        initView();

    }
//search过滤搜索框事件============================================
    class MyTextWatcher implements TextWatcher{

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(search.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
    //===========================================================
//控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());

        adapter = new ListAdapter(CourseRelease.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        String d = "截止日期:";
        ListInfo cell;
        for(int i = 0;i < 3; i++){
            cell = new ListInfo("计算机","201502",d,"2015.03.01");
            listInfos.add(cell);
        }
        for(int i = 0; i < 3; i++){
            cell = new ListInfo("数学","201501",d,"2014.03.01");
            listInfos.add(cell);
        }
    }
    //=========================================================
}
