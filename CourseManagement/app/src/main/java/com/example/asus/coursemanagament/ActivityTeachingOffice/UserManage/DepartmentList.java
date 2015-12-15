package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListProfessionals;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_department;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.example.asus.coursemanagament.Util.ProfessionalsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentList extends Activity {
    String tableName = new String("系负责人信息表");
    private List<Tb_department> l = new ArrayList<Tb_department>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_department>>() {
    }.getType();
    private Bundle bundle;

    private EditText search;
    private List<ListProfessionals> listInfos = new ArrayList<ListProfessionals>(); //存放Item
    private ListView listView;
    ProfessionalsListAdapter adapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        initList();

        //返回监听事件============================================
        ImageView ivw_1 = (ImageView) findViewById(R.id.ivw_1);
        ivw_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //============================================

        //添加教师信息按钮监听事件=================================
        ImageView ivw_2 = (ImageView) findViewById(R.id.ivw_2);
        ivw_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepartmentList.this, DepartmentAdd.class);
                startActivity(intent);
            }
        });
        //=================================

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
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(DepartmentList.this, DepartmentInfo.class);
            startActivity(intent);
        }
    }
    //========================================================

    //控件绑定，事件监听===========================================
    private void initView() {
        search = (EditText) findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());

        adapter = new ProfessionalsListAdapter(DepartmentList.this, listInfos);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {

        progress = new ProgressDialog(DepartmentList.this);
        progress.setMessage("加载中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);
        progress.show();


        //连接服务器==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type", "" + 4);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            l = gson.fromJson(gson.toJson(l2), type);
                            l = gson.fromJson(response, type);
                            progress.cancel();
                            bundle = new queryDB().queryDB(DepartmentList.this, tableName, l);
                            int rows = bundle.getInt("rows");
                            int cols = bundle.getInt("cols");
                            int i;
                            String tmp;
                            ListProfessionals cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                cell = new ListProfessionals(bundle.getString(tmp + 2),
                                        "工号:", bundle.getString(tmp + 0));
                                listInfos.add(cell);
                            }
                            initView();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.i("in onError", "!!!!!!");
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.cancel();
                            Toast.makeText(DepartmentList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //=========================================================
}


