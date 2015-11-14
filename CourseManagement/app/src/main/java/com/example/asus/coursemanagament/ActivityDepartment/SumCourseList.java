package com.example.asus.coursemanagament.ActivityDepartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TeachingOfficeSummaryTable;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SumCourseList extends AppCompatActivity {

    private EditText search;
    private List<ListTotalCourse> listInfos= new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Button btn_export;
    TotalCoureseListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_course_list__xxx);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        initView();

    }
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
    //iv_left后退跳转==================================================
    class MyOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SumCourseList.this,SummaryTable.class);
            startActivity(intent);

        }
    }
    //============================================================
//控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());
        iv_left = (ImageView)findViewById(R.id.left);
        iv_left.setOnClickListener(new MyOnClickListner());

        btn_export = (Button)findViewById(R.id.btn_export);
        //导出事件


        adapter = new TotalCoureseListAdapter(SumCourseList.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        ListTotalCourse cell;

        for(int i = 0;i < 3; i++){
            cell = new ListTotalCourse("图形设计");
            listInfos.add(cell);
        }
        for(int i = 0; i < 3; i++){
            cell = new ListTotalCourse("计算机导论");
            listInfos.add(cell);
        }
    }
    //=========================================================
}
