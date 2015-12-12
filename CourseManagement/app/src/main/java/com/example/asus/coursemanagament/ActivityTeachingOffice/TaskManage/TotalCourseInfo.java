package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.DBOpenHelper;
import com.example.asus.coursemanagament.SQLite_operation.SQLOperate;
import com.example.asus.coursemanagament.SQLite_operation.SQLOperateImpl;
import com.example.asus.coursemanagament.SQLite_operation.Tb_course;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teacherBaoCourse;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.ExcelUtil;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;
import jxl.read.biff.BiffException;

public class TotalCourseInfo extends Activity {

    String tableName = new String ();
    String zhuanye =new String();
    private List<Tb_course> l = new ArrayList<Tb_course>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Tb_course>>() {}.getType();
    private Bundle bundle;

    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Button btn_export;
    //    private
    TotalCoureseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        //获取数据，eg：网络工程专业：局域网解析。。。。
        zhuanye = intent.getStringExtra("zhuanye");
        tableName = zhuanye+"开课表";//之后删除开课表三个字

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_course_info);
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
        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker picker = new FilePicker(TotalCourseInfo.this);
                picker.setShowHideDir(false);
                picker.setInitPath(Common.getRootPath(TotalCourseInfo.this) + "Download/");
                //picker.setAllowExtensions(new String[]{".apk"});
                picker.setMode(FilePicker.Mode.File);
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(final String currentPath) {

                        final List<String[]> list = new ArrayList<String[]>();

                        String tablename = "";
                        //这里需要intent传表名
                        tablename = "计算机专业课程表";
                        //表名
                        String[] str2 = new String[12];
                        for (int i = 0; i < 12; i++) str2[i] = "";
                        str2[0] = tablename;
                        list.add(str2);

                        //列名
                        String str[] = new String[12];
                        str[0] = "年级";
                        str[1] = "专业";
                        str[2] = "专业人数";
                        str[3] = "课程名称";
                        str[4] = "选修类型";
                        str[5] = "学分";
                        str[6] = "学时";
                        str[7] = "实验";
                        str[8] = "上机";
                        str[9] = "起讫周序";
                        str[10] = "任课教师";
                        str[11] = "备注";
                        list.add(str);

                        //列名
                        String[] str3 = new String[12];
                        for (int i = 0; i < 12; i++) str3[i] = "";
                        str3[7] = str3[8] = "学时";
                        list.add(str3);

                        //从服务器拉取数据=====================================================================
                        Map<String, String> params = new HashMap<String, String>();
                        try {
                            HttpUtil.doPost(GlobalVariables.URL + "/getCourseTable", params, new HttpCallbackListener() {
                                @Override
                                public void onFinish(final String response) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Gson gson=new Gson();
                                            List<Tb_course> courseList=
                                                    gson.fromJson(response,new TypeToken<List<Tb_course>>(){}.getType());
                                            for(Tb_course tb_course:courseList){
                                                String str4[]=new String[12];
                                                str4[0] = tb_course.getGrade();
                                                str4[1] = tb_course.getMajor();
                                                str4[2] = tb_course.getP_cnt();
                                                str4[3] = tb_course.getC_name();
                                                str4[4] = tb_course.getType();
                                                str4[5] = tb_course.getCredit();
                                                str4[6] = tb_course.getTimes();
                                                str4[7] = tb_course.getExp_times();
                                                str4[8] = tb_course.getPra_times();
                                                str4[9] = tb_course.getBe_weeks();
                                                str4[10] = tb_course.getT_name();
                                                str4[11] = tb_course.getRemark();
                                                list.add(str4);
                                            }
                                            ExcelUtil.writeExcel(currentPath, list);
                                        }
                                    });
                                }
                                @Override
                                public void onError(Exception e) {
                                    Log.i("TotalCourseInfo",e.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(TotalCourseInfo.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("info", e.toString());
                        }
                        //=================================

                        //访问本地数据库================================================================
                        /*
                        DBOpenHelper dbhelper = new DBOpenHelper(TotalCourseInfo.this);
                        SQLiteDatabase db = dbhelper.getReadableDatabase();
                        ContentValues values = new ContentValues();
                        Cursor cursor = db.query(tablename, null, null, null, null, null, null);
                        if (cursor.moveToFirst())

                        {
                            do {
                                String[] str4 = new String[12];
                                str4[0] = cursor.getString(cursor.getColumnIndex("年级"));
                                str4[1] = cursor.getString(cursor.getColumnIndex("专业"));
                                str4[2] = cursor.getString(cursor.getColumnIndex("专业人数"));
                                str4[3] = cursor.getString(cursor.getColumnIndex("课程名称"));
                                str4[4] = cursor.getString(cursor.getColumnIndex("选修类型"));
                                str4[5] = cursor.getString(cursor.getColumnIndex("学分"));
                                str4[6] = cursor.getString(cursor.getColumnIndex("学时"));
                                str4[7] = cursor.getString(cursor.getColumnIndex("实验学时"));
                                str4[8] = cursor.getString(cursor.getColumnIndex("上机学时"));
                                str4[9] = cursor.getString(cursor.getColumnIndex("起讫周序"));
                                str4[10] = cursor.getString(cursor.getColumnIndex("任课教师"));
                                str4[11] = cursor.getString(cursor.getColumnIndex("备注"));
                                Log.i("info", str[0]);
                                list.add(str4);
                            } while (cursor.moveToNext());
                        }
<<<<<<< HEAD
//                        SQLOperateImpl test2 = new SQLOperateImpl(TotalCourseInfo.this);
//                        Tb_course tb_course=new Tb_course();
//                        String[] str4=new String[12];
//                        for(int i=1;i<=64;i++){
//                            tb_course=test2.findById_course(""+i);
//                            if(tb_course==null) Log.i("info","fuckkkk!");
//                            str4[0]=tb_course.getGrade();
//                            str4[1]=tb_course.getMajor();
//                            str4[2]=tb_course.getP_cnt();
//                            str4[3]=tb_course.getC_name();
//                            str4[4]=tb_course.getType();
//                            str4[5]=tb_course.getCredit();
//                            str4[6]=tb_course.getTimes();
//                            str4[7]=tb_course.getExp_times();
//                            str4[8]=tb_course.getPra_times();
//                            str4[9]=tb_course.getBe_weeks();
//                            str4[10]=tb_course.getT_name();
//                            str4[11]=tb_course.getRemark();
//                            list.add(str4);
//                        }

                        Log.i("info", "fuckyou!after");

//                        String result="";
//                        for (int i = 0; i < list.size(); i++) {
//                            str = (String[]) list.get(i);
//                            for (int j = 0; j < str.length; j++) {
//                                result += str[j];
//                                result += ";";
//                            }
//                            result += "\n";
//                        }
//                        Toast.makeText(TotalCourseInfo.this,"fuckyou!",Toast.LENGTH_SHORT);
//                        List list=null;

                        Log.i(bundle.getString("cell01"),"!!!!!!!!in ceshi");

                        ExcelUtil.writeExcel(currentPath,list);
=======
                        */
                        //================================================
                    }
                });
                picker.showAtBottom();
            }
        });

        adapter = new TotalCoureseListAdapter(TotalCourseInfo.this, listInfos);
        listView = (ListView) findViewById(R.id.list);
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
                            Intent intent = getIntent();
                            //获取数据，eg：网络工程专业：局域网解析。。。。
                            String zhuanye = intent.getStringExtra("zhuanye");
                            bundle = new queryDB().queryDB(TotalCourseInfo.this, tableName, l);
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
                            Toast.makeText(TotalCourseInfo.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();

//                            l = gson.fromJson(gson.toJson(l2), type);
//                            Intent intent = getIntent();
//                            //获取数据，eg：网络工程专业：局域网解析。。。。
//                            String zhuanye = intent.getStringExtra("zhuanye");
//                            Bundle bundle = new queryDB().queryDB(TotalCourseInfo.this, tableName, l);
//                            int rows = bundle.getInt("rows");
//                            int cols = bundle.getInt("cols");
//                            int i;
//                            String tmp;
//                            ListTotalCourse cell;
//                            for (i = 0; i < rows; i++) {
//                                tmp = "cell" + i;
//                                cell = new ListTotalCourse(bundle.getString(tmp + 3));
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
