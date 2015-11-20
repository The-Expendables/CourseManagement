package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;
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
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.ProfessionalsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeacherList extends AppCompatActivity {

    private EditText search;
    private List<ListProfessionals> listInfos= new ArrayList<ListProfessionals>(); //存放Item
    private ListView listView;
    ProfessionalsListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initList();
        initView();
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
            Intent intent = new Intent(TeacherList.this,TeacherInfo.class);
            startActivity(intent);
        }
    }
    //========================================================

    //控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());



        adapter = new ProfessionalsListAdapter(TeacherList.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){

        Bundle bundle = new queryDB().queryDB(this, "教师信息表");
        int rows = bundle.getInt("rows");
        int cols = bundle.getInt("cols");
        int i;
        String tmp;
        ListProfessionals cell;
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            cell = new ListProfessionals(bundle.getString(tmp + 4),
                    "工号", bundle.getString(tmp + 1));
            listInfos.add(cell);
        }
    }
    //=========================================================
}
