package com.example.asus.coursemanagament.ActivityTeacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListProfessionals;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.CurriculumsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CourseBegin extends AppCompatActivity {

    private EditText search;
    private List<ListCurriculums> listCurriculumses = new ArrayList<ListCurriculums>(); //存放Item
    private ListView listView;
    CurriculumsListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_begin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        initView();

    }
    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(CourseBegin.this,CourseChoose.class);
            TextView info = (TextView)view.findViewById(R.id.ItemName);
            String infoo = info.getText().toString();
            intent.putExtra("zhuanye",infoo);
            startActivity(intent);
        }
    }
    //========================================================
//search过滤搜索框事件============================================
    class MyTextWatcher implements TextWatcher {

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

        adapter = new CurriculumsListAdapter(CourseBegin.this, listCurriculumses);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        Intent intent = getIntent();
        //获取数据
        String gonghao = intent.getStringExtra("gonghao");
        Log.i("info","!!!!!!!!!!!"+gonghao);
        Bundle bundle2 = new queryDB().queryDB(this, "教师信息表");
        int rows2 = bundle2.getInt("rows");
        int i;
        String tmp;
        String zhuanye = new String();
        for (i = 0; i < rows2; i++) {
            tmp = "cell" + i;
           if(bundle2.getString(tmp + 2).equals(gonghao)){
               zhuanye=bundle2.getString(tmp + 3);
               break;
           }
        }

        Log.i("info","!!!!!!!!!!!"+zhuanye);
        Bundle bundle = new queryDB().queryDB(this, "发布表");
        int rows = bundle.getInt("rows");
        ListCurriculums cell;
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            if(bundle.getString(tmp + 1).equals(zhuanye)) {
                cell = new ListCurriculums(bundle.getString(tmp + 1), bundle.getString(tmp + 2),
                        "截止日期", bundle.getString(tmp + 3));
                listCurriculumses.add(cell);
            }
        }
    }
    //=========================================================
}