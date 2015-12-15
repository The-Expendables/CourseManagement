package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_teacherBaoCourse;
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

/**
 * Created by wwk on 2015/11/13.
 */
public class TeachingOfficeSummaryTable extends Activity {
    String tableName = new String ("教师报课信息表");

    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_teacherBaoCourse>>() {}.getType();
    private String courseTB = new String();
    List<Tb_teacherBaoCourse> l = new ArrayList<Tb_teacherBaoCourse>();
    private ProgressDialog progress;
    private ProgressDialog progress2;
    private int re = 0;

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
        TextView isCourse = (TextView)findViewById(R.id.isCourse);
        isCourse.setText(tableName);
        ImageView reflash = (ImageView)findViewById(R.id.reflash);
        reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(TeachingOfficeSummaryTable.this);
                progress.setMessage("刷新中...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(true);
                progress.show();
                re = 1;
                listInfos.clear();
                initList();

            }
        });
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
                //获取数据,传送专业名称->实现该专业课表显示
                String zhuanye = intent2.getStringExtra("zhuanye");
                Intent intent = new Intent(TeachingOfficeSummaryTable.this, TotalCourseInfo.class);
                intent.putExtra("zhuanye",zhuanye);
                startActivity(intent);
            }
            else{
                //教师个人报课信息，传送教师的工号
                Intent intent = new Intent(TeachingOfficeSummaryTable.this,TeacherCourseInfo.class);
                TextView info = (TextView)view.findViewById(R.id.jobNumber);
                TextView info2 = (TextView)view.findViewById(R.id.teacherName);
                String infoo = info.getText().toString();
                String infoo2 = info2.getText().toString() ;
                intent.putExtra("gonghao",infoo);
                intent.putExtra("t_name",infoo2);
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
        if(re != 1 ){
            progress2 = new ProgressDialog(TeachingOfficeSummaryTable.this);
            progress2.setMessage("加载中...");
            progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress2.setCancelable(true);
            progress2.show();
        }

        //连接服务器不能删==================================================================
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name", tableName);
        params.put("type",""+2);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/sendList", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("info",response.length()+"------------");
                            Log.i("info","-------"+response);

                            l = gson.fromJson(response, type);
                            progress2.cancel();
                            if(re == 1)
                                progress.cancel();
                            Intent intent = getIntent();
                            //获取数据,即专业名称，eg：软件工程专业
                            String zhuanye = intent.getStringExtra("zhuanye");
                            //汇总表和教师信息
                            Bundle bundle = new queryDB().queryDB(TeachingOfficeSummaryTable.this, tableName, l);

                            int rows = bundle.getInt("rows");
                            int cols = bundle.getInt("cols");
                            int i;
                            String tmp;
                            ListProfessionals cell;
                                cell = new ListProfessionals("汇", "总", "表");
                                listInfos.add(cell);
                                for (i = 0; i < rows; i++) {
                                    tmp = "cell" + i;
                                    if (bundle.getString(tmp + 0).equals(zhuanye)) {
                                        cell = new ListProfessionals(bundle.getString(tmp + 4),
                                                "工号:", bundle.getString(tmp + 5));
                                        listInfos.add(cell);

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
                            progress2.cancel();
                            progress.cancel();
                            Toast.makeText(TeachingOfficeSummaryTable.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //=========================================================
    }

    //=========================================================
}
