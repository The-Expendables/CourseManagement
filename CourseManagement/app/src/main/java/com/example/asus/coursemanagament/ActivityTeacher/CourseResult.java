package com.example.asus.coursemanagament.ActivityTeacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTotalCourse;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.queryDB;
import com.example.asus.coursemanagament.UiCustomViews.TotalCoureseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CourseResult extends AppCompatActivity {

    private EditText search;
    private List<ListTotalCourse> listInfos= new ArrayList<ListTotalCourse>(); //存放Item
    private ListView listView;
    private ImageView iv_left;
    private Bundle bundle;
    TotalCoureseListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);
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
        Intent intent = getIntent();
        //获取数据
        String zhuanye = intent.getStringExtra("zhuanye");
        Bundle bundle = new queryDB().queryDB(this, zhuanye+"课程表");
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

    //================报课结果弹出框============================================
     private void showDialog1(int i){   //显示课程信息对话框
        LayoutInflater inflater = LayoutInflater.from(this);    //引入自定义布局
        View view  = inflater.inflate(R.layout.activity_course_info, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int j=0;
        TextView grade = (TextView) view.findViewById(R.id.grade);
        grade.setText(bundle.getString("cell"+i+j)); j++;
         TextView major = (TextView) view.findViewById(R.id.major);
         major.setText(bundle.getString("cell"+i+j)); j++;
        TextView number = (TextView) view.findViewById(R.id.number);
        number.setText(bundle.getString("cell"+i+j)); j++;
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(bundle.getString("cell" + i + j)); j++;
         builder.setTitle(bundle.getString("cell" +i+j)); j++;//弹出框标题为课程名称
        TextView credit = (TextView) view.findViewById(R.id.credit);
        credit.setText(bundle.getString("cell" + i + j)); j++;
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText(bundle.getString("cell" + i + j)); j++;
        TextView exp_time = (TextView) view.findViewById(R.id.exp_time);
        exp_time.setText(bundle.getString("cell" + i + j)); j++;
        TextView com_time = (TextView) view.findViewById(R.id.com_time);
        com_time.setText(bundle.getString("cell" + i + j)); j++;
        TextView be_weeks = (TextView) view.findViewById(R.id.be_weeks);
        be_weeks.setText(bundle.getString("cell" + i + j)); j++;
        TextView teachers = (TextView)view.findViewById(R.id.teachers);
        teachers.setText(bundle.getString("cell" + i + j)); j++;
        TextView note = (TextView)view.findViewById(R.id.note);
         note.setText(bundle.getString("cell" +i+j));

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
