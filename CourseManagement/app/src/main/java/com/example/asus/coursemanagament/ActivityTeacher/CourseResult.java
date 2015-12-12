package com.example.asus.coursemanagament.ActivityTeacher;

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

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TeachingOfficeSummaryTable;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.Tb_course;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseResult extends AppCompatActivity {
    String tableName = new String ();
    String courseTB =  new String();
    private List<Tb_course> l = new ArrayList<Tb_course>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_course>>() {}.getType();
    private Bundle bundle;

    private EditText search;
    private List<ListTotalCourse> listInfos= new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    TotalCoureseListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        //获取数据，eg：网络工程专业：。。。。
        courseTB = intent.getStringExtra("courseTB");
        tableName = courseTB+"开课表";//之后删除开课表三个字
        Log.i(tableName, "!!!!!!tablename");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);
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



        adapter = new TotalCoureseListAdapter(CourseResult.this, listInfos);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    //======================================================
    // 初始化listView数据===========================================
    private void initList(){

        //测试用例
        final  List<Tb_course> l2 = new ArrayList<Tb_course>();
        Tb_course t1 = new Tb_course("2013级","软件工程专业","144","软工实践",
                "实践选修", "2分","48h","24h","24h","1-8周","张东","");
        l2.add(t1);
        Tb_course t2 = new Tb_course("2013级","软件工程专业","144","UML建模",
                "专业选修", "2分","48h","24h","24h","1-8周","郭洪","");
        l2.add(t2);
        Log.i(gson.toJson(l2), "!!!!!!!");

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type",""+1);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            l = gson.fromJson(gson.toJson(l2), type);
                            l = gson.fromJson(response, type);
                            bundle = new queryDB().queryDB(CourseResult.this, tableName, l);
                            int rows = bundle.getInt("rows");
                            int cols = bundle.getInt("cols");
                            int i;
                            String tmp;
                            ListTotalCourse cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                cell = new ListTotalCourse(bundle.getString(tmp + 3));
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

                            Toast.makeText(CourseResult.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

//                            l = gson.fromJson(gson.toJson(l2), type);
//
//                            bundle = new queryDB().queryDB(CourseResult.this, tableName, l);
//                            int rows = bundle.getInt("rows");
//                            int cols = bundle.getInt("cols");
//                            int i;
//                            String tmp;
//                            ListTotalCourse cell;
//                            for (i = 0; i < rows; i++) {
//                                tmp = "cell" + i;
//                                cell = new ListTotalCourse(bundle.getString(tmp + 3));
//                                listInfos.add(cell);
//
//                            }
//                            initView();

                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = getIntent();
//        //获取数据
//        String zhuanye = intent.getStringExtra("zhuanye");
//        Bundle bundle = new queryDB().queryDB(this, zhuanye+"课程表");
//        int rows = bundle.getInt("rows");
//        int cols = bundle.getInt("cols");
//        int i;
//        String tmp;
//        ListTotalCourse cell;
//        for (i = 0; i < rows; i++) {
//            tmp = "cell" + i;
//            cell = new ListTotalCourse(bundle.getString(tmp + 4));
//            listInfos.add(cell);
//        }
    }
    //=========================================================
}
