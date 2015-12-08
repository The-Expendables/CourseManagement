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
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.ExcelUtil;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;
import jxl.read.biff.BiffException;

public class TotalCourseInfo extends Activity {

    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Button btn_export;
    //    private
    TotalCoureseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_course_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initList();
        initView();

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
                    public void onFilePicked(String currentPath) {

                        List list = new ArrayList();

                        String tablename = new String();
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

                        //从服务器拉取数据
                        Map<String, String> params = new HashMap<String, String>();
                        try {
                            HttpUtil.doPost(GlobalVariables.URL + "/login", params, new HttpCallbackListener() {
                                @Override
                                public void onFinish(final String response) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<Tb_course> courseList=parse
                                        }
                                    });
                                }
                                @Override
                                public void onError(Exception e) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("info", e.toString());
                        }

                        //每一行数据
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
                        */

                        ExcelUtil.writeExcel(currentPath, list);
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
    private void initList() {
        Intent intent = getIntent();
        //获取数据
        String zhuanye = intent.getStringExtra("zhuanye");
        Bundle bundle = new queryDB().queryDB(this, zhuanye + "课程表");
        int rows = bundle.getInt("rows");
        int cols = bundle.getInt("cols");
        int i;
        String tmp;
        ListTotalCourse cell;
        for (i = 0; i < rows; i++) {
            tmp = "cell" + i;
            cell = new ListTotalCourse(bundle.getString(tmp + 4));
            listInfos.add(cell);
        }

    }
    //=========================================================
}
