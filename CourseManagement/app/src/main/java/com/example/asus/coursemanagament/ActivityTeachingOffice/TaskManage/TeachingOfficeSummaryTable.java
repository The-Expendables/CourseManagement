package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.ProfessionalsListAdapter;
import com.example.asus.coursemanagament.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwk on 2015/11/13.
 */
public class TeachingOfficeSummaryTable extends Activity {

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
                Intent intent2 = getIntent();
                //获取数据
                String zhuanye = intent2.getStringExtra("zhuanye");
                Intent intent = new Intent(TeachingOfficeSummaryTable.this, TotalCourseInfo.class);
                intent.putExtra("zhuanye",zhuanye);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(TeachingOfficeSummaryTable.this,TeacherCourseInfo.class);
                TextView info = (TextView)view.findViewById(R.id.jobNumber);
                String infoo = info.getText().toString();
                intent.putExtra("gonghao",infoo);
                startActivity(intent);
            }
        }
    }
    //========================================================
    //iv_left后退跳转==================================================
    class MyOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();

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


        adapter = new ProfessionalsListAdapter(TeachingOfficeSummaryTable.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        Intent intent = getIntent();
        //获取数据
        String zhuanye = intent.getStringExtra("zhuanye");
        //汇总表和教师信息
        Bundle bundle = new queryDB().queryDB(this, "教师信息表");
        int rows = bundle.getInt("rows");
        int cols = bundle.getInt("cols");
        int i;
        String tmp;
        ListProfessionals cell;
        cell = new ListProfessionals("汇","总","表");
        listInfos.add(cell);
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            if(bundle.getString(tmp + 3).equals(zhuanye) ) {
                cell = new ListProfessionals(bundle.getString(tmp + 4),
                        "工号", bundle.getString(tmp + 1));
                listInfos.add(cell);
            }
        }


    }
    //=========================================================
}
