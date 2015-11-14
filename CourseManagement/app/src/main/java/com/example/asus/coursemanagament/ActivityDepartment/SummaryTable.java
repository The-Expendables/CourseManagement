package com.example.asus.coursemanagament.ActivityDepartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListProfessionals;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TeacherCourseInfo;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.ProfessionalsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SummaryTable extends AppCompatActivity {

    private EditText search;
    private List<ListProfessionals> listInfos= new ArrayList<ListProfessionals>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private TextView tv_isCourse;
    ProfessionalsListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_table);
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
    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //总汇总表
            if(position == 0) {
                Intent intent = new Intent(SummaryTable.this, SumCourseList.class);
                //引用其他目录的class
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(SummaryTable.this,DepartTeacherList.class);
                //引用其他目录的class
                startActivity(intent);
            }
        }
    }
    //========================================================
    //iv_left后退跳转==================================================
    class MyOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SummaryTable.this,TaskList.class);
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

        tv_isCourse = (TextView)findViewById(R.id.isCourse);
        //用Intent传入信息改变isCourse的名称


        adapter = new ProfessionalsListAdapter(SummaryTable.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        String d = "工号:";
        ListProfessionals cell;
        cell = new ListProfessionals("汇","总","表");
        listInfos.add(cell);
        for(int i = 0;i < 3; i++){
            cell = new ListProfessionals("张三",d,"1234");
            listInfos.add(cell);
        }
        for(int i = 0; i < 3; i++){
            cell = new ListProfessionals("李四",d,"0987");
            listInfos.add(cell);
        }
    }
    //=========================================================
}
