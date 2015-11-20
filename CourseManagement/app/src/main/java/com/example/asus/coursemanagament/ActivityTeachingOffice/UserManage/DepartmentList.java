package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListProfessionals;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.ProfessionalsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DepartmentList extends Activity {

    private EditText search;
    private List<ListProfessionals> listInfos= new ArrayList<ListProfessionals>(); //存放Item
    private ListView listView;
    ProfessionalsListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window_FEATURE_NO_TITLE);
        setContentView(R.layout.activity_department_list);

        initList();
        initView();
        //返回监听事件============================================
        ImageView ivw_1=(ImageView)findViewById(R.id.ivw_1);
        ivw_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //============================================

        //添加教师信息按钮监听事件=================================
        ImageView ivw_2=(ImageView)findViewById(R.id.ivw_2);
        ivw_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(DepartmentList.this,DepartmentAdd.class);
                startActivity(intent);
            }
        });
        //=================================

        //listview====================================

        //================================

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
            Intent intent = new Intent(DepartmentList.this,DepartmentInfo.class);
            startActivity(intent);
        }
    }
    //========================================================

    //控件绑定，事件监听===========================================
    private void initView(){
        search = (EditText)findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());

        adapter = new ProfessionalsListAdapter(DepartmentList.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        Bundle bundle = new queryDB().queryDB(this, "系负责人信息表");
        int rows = bundle.getInt("rows");
        int cols = bundle.getInt("cols");
        int i;
        String tmp;
        ListProfessionals cell;
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            cell = new ListProfessionals(bundle.getString(tmp + 5),
                    "工号", bundle.getString(tmp + 1));
            listInfos.add(cell);
        }

    }
    //=========================================================
}


