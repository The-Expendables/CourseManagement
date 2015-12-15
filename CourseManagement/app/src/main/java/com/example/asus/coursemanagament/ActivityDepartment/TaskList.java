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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityLogin.Login;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course_mes;
import com.example.asus.coursemanagament.Tb.Tb_department;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.CurriculumsListAdapter;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.example.asus.coursemanagament.Util.SlidingMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskList extends AppCompatActivity {

    String tableName2 = new String("系负责人信息表");
    private List<Tb_department> l2 = new ArrayList<Tb_department>();
    private Type type2 = new TypeToken<List<Tb_department>>() {
    }.getType();
    String zhuanye = new String();
    private Bundle bundle2;


    String tableName = new String("发布表");
    private List<Tb_course_mes> l = new ArrayList<Tb_course_mes>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_course_mes>>() {
    }.getType();
    private Bundle bundle;

    private SlidingMenu mLeftMenu_department;
    private Button btn_task2;
    private Button btn_password2;
    private Button btn_exit2;
    private EditText search;
    private List<ListCurriculums> listCurriculumses = new ArrayList<ListCurriculums>(); //存放Item
    private ListView listView;
    CurriculumsListAdapter adapter;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
//        initView();
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
                Intent intent = new Intent(TaskList.this, Login.class);
                startActivity(intent);
            }
        });
        //===========================================================

    }

    //侧滑菜单===============================
    public void toggleMenu(View view) {
        mLeftMenu_department.toggle();
    }

    //=========================================
    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(TaskList.this, SumCourseList.class);
            TextView info = (TextView) view.findViewById(R.id.ItemName);
            String infoo = info.getText().toString();
            intent.putExtra("zhuanye", infoo);
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
    private void initView() {
        search = (EditText) findViewById(R.id.searchbox);//绑定过滤搜索框
        search.addTextChangedListener(new MyTextWatcher());

        adapter = new CurriculumsListAdapter(TaskList.this, listCurriculumses);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {

        progress = new ProgressDialog(TaskList.this);
        progress.setMessage("加载中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);
        progress.show();

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", "" + tableName2);
        params.put("type", "" + 4);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //查询系负责人表获得其工号对应的专业
                            l2 = gson.fromJson(response, type2);
                            progress.cancel();
                            Intent intent = getIntent();
                            //获取工号，判定教师 所属系即专业
                            String gonghao = intent.getStringExtra("gonghao");
                            Log.i("info", "!!!!!!!!!!!" + gonghao);
                            bundle2 = new queryDB().queryDB(TaskList.this, tableName2, l2);
                            int rows2 = bundle2.getInt("rows");
                            int i;
                            String tmp;

                            for (i = 0; i < rows2; i++) {
                                tmp = "cell" + i;
                                if (bundle2.getString(tmp + 0).equals(gonghao)) {
                                    zhuanye = bundle2.getString(tmp + 4);
                                    break;
                                }
                            }

                            //查询发布表
                            initHttp();
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
                            Toast.makeText(TaskList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //=========================================================


    }//=========================================================

    private void initHttp() {//连接服务器，用用户的工号查询对应专业的 开课表
        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type", "" + 5);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            l = gson.fromJson(response, type);
                            bundle = new queryDB().queryDB(TaskList.this, tableName, l);
                            Log.i(bundle.getString("cell00"), "!!!!!bundle num");
                            int rows = bundle.getInt("rows");
                            int i;
                            String tmp;
                            ListCurriculums cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;
                                Log.i("info!!!", bundle.getString(tmp + 0));
                                if (bundle.getString(tmp + 0).substring(0, 2).equals(zhuanye) ||
                                        bundle.getString(tmp + 0).substring(0, 3).equals(zhuanye) ||
                                        bundle.getString(tmp + 0).substring(0, 4).equals(zhuanye)) {
                                    cell = new ListCurriculums(bundle.getString(tmp + 0), bundle.getString(tmp + 1),
                                            "截止日期:", bundle.getString(tmp + 2));
                                    listCurriculumses.add(cell);
                                }

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
                            Toast.makeText(TaskList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }//=========================================================

    }
    //==================================================================

}
