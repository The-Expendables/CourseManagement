package com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_course;
import com.example.asus.coursemanagament.Tb.queryDB;
import com.example.asus.coursemanagament.Util.ExcelUtil;
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

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;

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
    private ProgressDialog progress;
    TotalCoureseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        //获取数据，eg：网络工程专业：局域网解析。。。。
        zhuanye = intent.getStringExtra("zhuanye");
        tableName = zhuanye;//之后删除开课表三个字

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

    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView t = (TextView)view.findViewById(R.id.courseName);
            String tt = t.getText().toString();
            showDialog3(tt);
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

                        for(Tb_course tb_course:l){
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
                        Toast.makeText(TotalCourseInfo.this,"导出成功，请查看excel表格",Toast.LENGTH_SHORT).show();
                    }
                });
                picker.showAtBottom();
            }
        });

        adapter = new TotalCoureseListAdapter(TotalCourseInfo.this, listInfos);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList(){
        progress = new ProgressDialog(TotalCourseInfo.this);
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
                            for(i = 0;i < rows ; i++)
                                for(int j = 0;j <cols; j++)
                                {
                                    Log.i(bundle.getString("cell"+i+j),"-----------------");
                                }

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
                            Toast.makeText(TotalCourseInfo.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();



                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        }
    //=========================================================

    //================报课结果弹出框============================================
    private void showDialog3(String i){   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        View view  = inflater.inflate(R.layout.activity_course_info, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        int rows = bundle.getInt("rows");
        int j ;
        for (j = 0; j < rows; j++) {
            Tb_course tb=l.get(j);
            if (tb.getC_name().equals(i)) {
                break;
            }
        }
        Tb_course tb_course=(Tb_course)l.get(j);
        TextView grade = (TextView) view.findViewById(R.id.grade);
        grade.setText(tb_course.getGrade());
        TextView major = (TextView) view.findViewById(R.id.major);
        major.setText(tb_course.getMajor());
        TextView number = (TextView) view.findViewById(R.id.number);
        number.setText(tb_course.getP_cnt());
        builder.setTitle(tb_course.getC_name());
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(tb_course.getType());
        TextView credit = (TextView) view.findViewById(R.id.credit);
        credit.setText(tb_course.getCredit());
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText(tb_course.getTimes());
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time);
        exp_time.setText(tb_course.getExp_times());
        TextView com_time = (TextView) view.findViewById(R.id.com_time);
        com_time.setText(tb_course.getPra_times());
        TextView be_weeks = (TextView) view.findViewById(R.id.be_weeks);
        be_weeks.setText(tb_course.getBe_weeks());
        TextView teachers = (TextView)view.findViewById(R.id.teachers);
        teachers.setText(tb_course.getT_name());
        TextView note = (TextView)view.findViewById(R.id.note);
        note.setText(tb_course.getRemark());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
}
