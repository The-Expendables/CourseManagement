package com.example.asus.coursemanagament.ActivityDepartment;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
    private String before;
    private String after;

    private EditText search;
    private List<ListTotalCourse> listInfos = new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Button btn_export;
    TotalCoureseListAdapter adapter;
    private ProgressDialog progress;
    private ProgressDialog progress2;
    private int re = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //获取数据，eg：网络工程专业开课表。
        zhuanye = intent.getStringExtra("zhuanye");
        tableName = zhuanye;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_course_list__xxx);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //手动刷新
        ImageView reflash = (ImageView)findViewById(R.id.reflash_sumCourseList);
        reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress2 = new ProgressDialog(SumCourseList.this);
                progress2.setMessage("刷新中...");
                progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress2.setCancelable(true);
                progress2.show();
                re = 1;
                listInfos.clear();
                initList();

            }
        });

        initList();


    }

    //listview 点击事件========================================
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            showDialog1(position);
        }
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
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //======================================================
    // 初始化listView数据===========================================
    private void initList() {
        if(re != 1 ){
            progress = new ProgressDialog(SumCourseList.this);
            progress.setMessage("加载中...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(true);
            progress.show();
        }


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
                            if(re == 1)
                                progress2.cancel();
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
                                if (kk != null&&!kk.equals("")  ) {
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
                            progress2.cancel();
                            Toast.makeText(SumCourseList.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();


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
    private void showDialog1(int i) {   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        final View view = inflater.inflate(R.layout.activity_course_info1, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        int j = 0;
        final Tb_course tb_course = l.get(i);
        TextView grade = (TextView) view.findViewById(R.id.grade2);
        grade.setText(tb_course.getGrade());
        TextView major = (TextView) view.findViewById(R.id.major2);
        major.setText(tb_course.getMajor());
        TextView number = (TextView) view.findViewById(R.id.number2);
        number.setText(tb_course.getP_cnt());
        builder.setTitle(tb_course.getC_name());
        TextView type = (TextView) view.findViewById(R.id.type2);
        type.setText(tb_course.getType());
        TextView credit = (TextView) view.findViewById(R.id.credit2);
        credit.setText(tb_course.getCredit());
        TextView time = (TextView) view.findViewById(R.id.time2);
        time.setText(tb_course.getTimes());
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time2);
        exp_time.setText(tb_course.getExp_times());
        TextView com_time = (TextView) view.findViewById(R.id.com_time2);
        com_time.setText(tb_course.getPra_times());
        TextView be_weeks = (TextView) view.findViewById(R.id.be_weeks2);
        be_weeks.setText(tb_course.getBe_weeks());
        EditText teachers = (EditText) view.findViewById(R.id.teachers2);
        teachers.setText(tb_course.getT_name());

        before = tb_course.getT_name();//获得编辑前的教师字符串

        TextView note = (TextView) view.findViewById(R.id.note2);
        note.setText(tb_course.getRemark());
        builder.setView(view);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //发送数据到服务器========

                Tb_teacherBaoCourse tt;
                List<Tb_teacherBaoCourse> ll = new ArrayList<Tb_teacherBaoCourse>();
                String after1;
                String after2;


                EditText teachers2 = (EditText) view.findViewById(R.id.teachers2);
                EditText be_week2 = (EditText)view.findViewById(R.id.be_weeks2);
                EditText remark2 = (EditText) view.findViewById(R.id.note2);
                after = teachers2.getText().toString();//获得编辑后的教师字符串
                after1 = be_week2.getText().toString();
                after2 = remark2.getText().toString();
                Log.i("info", "<<>>before" + before);
                Log.i("info", "<<>>after" + after);

                String tmp1 = new String("");
                List<String> lt = new ArrayList<String>();
                String tmp2 = new String("");
                List<String> lt2 = new ArrayList<String>();

                for (int i = 0; i < before.length(); i++) {
                    if (before.charAt(i) != ';') {
                        tmp1 += before.charAt(i);
                        Log.i("tmp1----", tmp1);

                    }
                    if(before.charAt(i) == ';'||i == before.length() - 1){
                        lt.add(tmp1);
//                        Log.i("lt!!!!", lt.get(i));
                        tmp1 = "";
                    }
                }//获取更改前的每个教师名字



                for (int i = 0; i < after.length(); i++) {
                    if (after.charAt(i) != ';'||i == before.length()-1) {
                        tmp2 += after.charAt(i);

                    }
                    if(after.charAt(i) == ';'||i == after.length() - 1){
                        lt2.add(tmp2);
                        tmp2 = "";
                    }
                }//获取更改后的每个教师名字


                for (int i = 0; i < lt.size(); i++) {
                    for (int k = 0; k < lt2.size(); k++) {
                        if (lt.get(i).equals(lt2.get(k)))
                            break;
                        if ((k == lt2.size() - 1) && !lt.get(i).equals(lt2.get(k))) {
                            tt = new Tb_teacherBaoCourse(zhuanye, tb_course.getC_name(), tb_course.getGrade(),
                                    "", lt.get(i), "", "");
                            Log.i("lt-----", lt.get(i));

                            ll.add(tt);
                        }
                    }
                }
                String ll_gson = gson.toJson(ll);
                Tb_course tt0 = new Tb_course("",tb_course.getGrade(), "", "", tb_course.getC_name(),
                        "", "", "", "", "", after1, after,
                        after2);
                String tt0_gson = gson.toJson(tt0);//发送更新后的信息到该开课表
                Log.i("tt0!!!",tt0.getT_name());
                Log.i("info","<<>>tt0_gson"+tt0_gson);
                Log.i("info","<<>>l1_gson"+ll_gson);

                //连接服务器不能删==================================================================
                Map<String, String> params = new HashMap<String, String>();
                params.put("table_name", zhuanye);
                params.put("info_json", tt0_gson);
                params.put("delete_json", ll_gson);
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/CourseUpdate", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SumCourseList.this, response, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {

                            e.printStackTrace();
                            Log.i("Login", "服务器访问失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("info", e.toString());
                }
                //=====================================


            }
        });
        AlertDialog dialog = builder.create();  //创建一个dialog
        dialog.show();          //显示对话框
    }
}
