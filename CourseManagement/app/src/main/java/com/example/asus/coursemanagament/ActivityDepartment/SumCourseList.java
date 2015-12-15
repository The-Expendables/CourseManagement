package com.example.asus.coursemanagament.ActivityDepartment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course;
import com.example.asus.coursemanagament.Tb.Tb_department;
import com.example.asus.coursemanagament.Tb.Tb_teacherBaoCourse;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.example.asus.coursemanagament.Util.TotalCoureseListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumCourseList extends AppCompatActivity {

    private int num = 0;
    String tableName = new String();
    String zhuanye = new String();
    private List<Tb_course> l = new ArrayList<Tb_course>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_course>>() {
    }.getType();
    private Bundle bundle;

    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Button btn_export;
    TotalCoureseListAdapter adapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //获取数据，eg：网络工程专业开课表。
        zhuanye = intent.getStringExtra("zhuanye");
        tableName = zhuanye;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_course_list__xxx);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();


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
    class MyOnClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();

        }
    }

    //============================================================
//控件绑定，事件监听===========================================
    private void initView() {
        search = (EditText) findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());
        iv_left = (ImageView) findViewById(R.id.left);
        iv_left.setOnClickListener(new MyOnClickListner());

        btn_export = (Button) findViewById(R.id.btn_export);
        //导出事件


        adapter = new TotalCoureseListAdapter(SumCourseList.this, listInfos);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {

        progress = new ProgressDialog(SumCourseList.this);
        progress.setMessage("加载中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);
        progress.show();
        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type", "" + 1);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            l = gson.fromJson(response, type);
                            progress.cancel();
                            Intent intent = getIntent();
                            //获取数据，eg：网络工程专业：局域网解析。。。。
                            String zhuanye = intent.getStringExtra("zhuanye");
                            bundle = new queryDB().queryDB(SumCourseList.this, tableName, l);
                            int rows = bundle.getInt("rows");
                            int i, j;
                            String tmp;
                            ListTotalCourse cell;
                            String kk;

                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                Tb_course t = (Tb_course) l.get(i);
                                kk = t.getT_name();
                                if (!kk.equals("")) {
                                    num++;
                                    for (int k = 0; k < kk.length(); k++) {
                                        if (kk.charAt(k) == ';') {
                                            num++;
                                        }
                                    }
                                }
                                cell = new ListTotalCourse(bundle.getString(tmp + 3) + ':' + num);
                                listInfos.add(cell);
                                num = 0;
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
                            Toast.makeText(SumCourseList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //=====================================
}
