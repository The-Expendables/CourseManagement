package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListProfessionals;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_department;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacher;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.ProfessionalsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherList extends AppCompatActivity {
    String tableName = new String ("教师信息表");
    private List<Tb_teacher> l = new ArrayList<Tb_teacher>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_teacher>>() {}.getType();
    private Bundle bundle;

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

        //测试用例
//        final  List<Tb_teacher> l2 = new ArrayList<Tb_teacher>();
//        Tb_teacher t1 = new Tb_teacher("11344", "123456", "李华","网络工程专业","男","1990.01.11",
//                "342342@qq.com", "18923234444");
//        l2.add(t1);
//        Tb_teacher t2 = new Tb_teacher("11355", "123456", "林航","软件工程专业","男","1991.02.22",
//                "333342@qq.com", "18923232222");
//        l2.add(t2);
//        Log.i(gson.toJson(l2), "!!!!!!!");

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type",""+3);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            l = gson.fromJson(gson.toJson(l2), type);
                            l = gson.fromJson(response, type);
                            bundle = new queryDB().queryDB(TeacherList.this, tableName, l);

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
                            Toast.makeText(TeacherList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
//                            l = gson.fromJson(gson.toJson(l2), type);
//                            bundle = new queryDB().queryDB(TeacherList.this, tableName, l);
//
//                            int rows = bundle.getInt("rows");
//                            int cols = bundle.getInt("cols");
//                            int i;
//                            String tmp;
//                            ListProfessionals cell;
//                            for (i = 0; i < rows; i++) {
//                                tmp = "cell" + i;
//                                cell = new ListProfessionals(bundle.getString(tmp + 2),
//                                        "工号:", bundle.getString(tmp + 0));
//                                listInfos.add(cell);
//                            }
//                            initView();
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
