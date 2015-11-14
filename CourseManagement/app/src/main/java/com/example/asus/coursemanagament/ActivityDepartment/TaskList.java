package com.example.asus.coursemanagament.ActivityDepartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asus.coursemanagament.ActivityLogin.Login;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.CurriculumsListAdapter;
import com.example.asus.coursemanagament.UiCustomViews.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends AppCompatActivity {

    private SlidingMenu mLeftMenu_department;
    private Button btn_task2;
    private Button btn_password2;
    private Button btn_exit2;
    private EditText search;
    private List<ListCurriculums> listCurriculumses = new ArrayList<ListCurriculums>(); //存放Item
    private ListView listView;
    CurriculumsListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        initView();
        mLeftMenu_department = (SlidingMenu) findViewById(R.id.department_menu);

        //设置侧滑菜单任务管理跳转=================================
        btn_task2 = (Button) findViewById(R.id.btn_task2);
        btn_task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu_department.toggle();
            }
        });
        //==========================================================

        //设置侧滑菜单修改密码跳转======================================
        btn_password2 = (Button) findViewById(R.id.btn_password2);
        btn_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskList.this, PasswordChange2.class);
                startActivity(intent);
            }
        });
        //============================================

        //设置侧滑菜单退出跳转=======================================
        btn_exit2 = (Button) findViewById(R.id.btn_exit2);
        btn_exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskList.this,Login.class);
                startActivity(intent);
            }
        });
        //===========================================================

    }

    //侧滑菜单===============================
    public void toggleMenu(View view){
        mLeftMenu_department.toggle();
    }
    //=========================================
    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(TaskList.this,SummaryTable.class);
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

        adapter = new CurriculumsListAdapter(TaskList.this, listCurriculumses);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        String d = "截止日期:";
        ListCurriculums cell;
        for(int i = 0;i < 3; i++){
            cell = new ListCurriculums("计算机","201502",d,"2015.03.01");
            listCurriculumses.add(cell);
        }
        for(int i = 0; i < 3; i++){
            cell = new ListCurriculums("数学","201501",d,"2014.03.01");
            listCurriculumses.add(cell);
        }
    }
    //=========================================================
}
