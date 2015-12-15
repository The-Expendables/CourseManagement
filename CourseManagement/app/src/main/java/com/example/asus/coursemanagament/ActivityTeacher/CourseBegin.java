package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListCurriculums;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course_mes;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.CurriculumsListAdapter;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseBegin extends AppCompatActivity {

    private String tableName = new String("发布表");
    private List<Tb_course_mes> l = new ArrayList<Tb_course_mes>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_course_mes>>() {}.getType();
    private Bundle bundle;
    private String Data1;
    private int[] data_set = new int[3];
    private EditText search;
    private List<ListCurriculums> listCurriculumses = new ArrayList<ListCurriculums>(); //存放Item
    private ListView listView;
    CurriculumsListAdapter adapter;
    private Calendar cal;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_begin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        cal = Calendar.getInstance();
        //获取年月日时分秒
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(CourseBegin.this, CourseChoose.class);
            TextView info = (TextView) view.findViewById(R.id.ItemName);
            //传送给下一个UI 专业名称（开课表表名）
            String infoo = info.getText().toString();
            intent.putExtra("courseTB", infoo);
            int p1, p2;
            for (p1 = 0; p1 < Data1.length() && Data1.charAt(p1) != '.'; p1++) ;
            for(p2=p1+1;p2<Data1.length()&&Data1.charAt(p2)!='.';p2++);
            data_set[0]=Integer.parseInt(Data1.substring(0,p1));
            data_set[1]=Integer.parseInt(Data1.substring(p1+1,p2));
            data_set[2]=Integer.parseInt(Data1.substring(p2+1));
//            //测试数据=======修改报课截止时间可以的时候注释掉
//            data_set[0]=2016;
//            data_set[1]=11;
//            data_set[2]=15;
            //=========================================
            if(year>data_set[0]){
                showDialog_end();
            } else{
                if(year<data_set[0]){
                    startActivity(intent);
                } else{
                    if ((month+1)>data_set[1]){
                        showDialog_end();
                    } else{
                        if ((month+1)<data_set[1]){
                            startActivity(intent);
                        } else{
                            if(day>data_set[2]){
                                showDialog_end();
                            }else{
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
//            Toast.makeText(CourseBegin.this,data_set[0]+"  "+data_set[1]+"  "+data_set[2],Toast.LENGTH_SHORT).show();
        }
    }

    //超过截止时间不能报课提示框===============================
    private void showDialog_end(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("报课时间已经截止，无法继续报课！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
    //=================================================

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

        adapter = new CurriculumsListAdapter(CourseBegin.this, listCurriculumses);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {


        //连接服务器=================================================================
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
                            //获取返回数据
                            l = gson.fromJson(response, type);
                            bundle = new queryDB().queryDB(CourseBegin.this, tableName, l);
                            int i;
                            String tmp;
                            int rows = bundle.getInt("rows");
                            ListCurriculums cell;
                            for (i = 0; i < rows; i++) {
                                tmp = "cell" + i;

                                cell = new ListCurriculums(bundle.getString(tmp + 0), bundle.getString(tmp + 1),
                                        "截止日期:", bundle.getString(tmp + 2));
                                Data1 = bundle.getString(tmp + 2);
                                listCurriculumses.add(cell);

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
                            Toast.makeText(CourseBegin.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

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