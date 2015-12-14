package com.example.asus.coursemanagament.ActivityLogin;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.coursemanagament.ActivityDepartment.TaskList;
import com.example.asus.coursemanagament.ActivityTeacher.CourseDeclare;
import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.TaskManage;
import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.SQLite_operation.DBOpenHelper;
import com.example.asus.coursemanagament.SQLite_operation.SQLOperateImpl;
import com.example.asus.coursemanagament.SQLite_operation.Tb_teachingoffice;
import com.example.asus.coursemanagament.UiCustomViews.GlobalVariables;
import com.example.asus.coursemanagament.UiCustomViews.HttpCallbackListener;
import com.example.asus.coursemanagament.UiCustomViews.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class  Login extends Activity {


    private Button btn_login;   //登录按钮
    private EditText edtt_userName; //用户名输入框
    private EditText edtt_password; //密码输入框
    private RadioGroup rdgp;    //单选按钮组
    private int number = 1; //判断角色身份
    private TextView tvw_forget; //忘记密码？
    private DBOpenHelper dbOpenHelper = new DBOpenHelper(Login.this);
    String username = new String();
    String zhuanye = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();


        //初始化表格数据==========================================================================
//        SQLOperateImpl test = new SQLOperateImpl(Login.this);    //增
//        Tb_teacher th = new Tb_teacher("23456","23456","计算机专业","李四","男","1970年12月","134892746@qq.com","15629124497");
//        test.add_teacher(th);
//        SQLOperateImpl test2 = new SQLOperateImpl(Login.this);    //增
//        Tb_teacher th2 = new Tb_teacher("23457","23457","计算机专业","王五","男","1980年2月","348192746@qq.com","18547156624");
//        test2.add_teacher(th2);
//        SQLOperateImpl test3 = new SQLOperateImpl(Login.this);    //增
//        Tb_teacher th3 = new Tb_teacher("23458","23458","计算机专业","赵七","男","1974年7月","489322746@qq.com","15624784416");
//        test3.add_teacher(th3);
//        SQLOperateImpl test4 = new SQLOperateImpl(Login.this);    //增
//        Tb_teacher th4 = new Tb_teacher("23459","23459","计算机专业","钱八","男","1985年11月","925471746@qq.com","18865247894");
//        test4.add_teacher(th4);
//        SQLOperateImpl test5 = new SQLOperateImpl(Login.this);    //增
//        Tb_teacher th5 = new Tb_teacher("23455","23455","计算机专业","陈九","女","1974年6月","845124780@qq.com","15024152632");
//        test5.add_teacher(th5);
//        SQLOperateImpl test0 = new SQLOperateImpl(Login.this);    //增
//        Tb_department th0 = new Tb_department("34567","34567","计算机专业","18659545514","陈楠楠");
//        test0.add_department(th0);
//        SQLOperateImpl test1 = new SQLOperateImpl(Login.this);    //增
//        Tb_teachingoffice th1 = new Tb_teachingoffice("12345","12345","18659545514","张三");
//        test1.add_teachingoffice(th1);
//
//        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("工号","12345");
//        values.put("课程名称","毕业实习");
//        values.put("年级","2013");
//        values.put("起讫周序", "1-15");
//        values.put("任课教师", "王一");
//        db.insert("教师报课信息表", null, values);
//        values.clear();
//        values.put("工号", "12346");
//        values.put("课程名称","电子商务技术");
//        values.put("年级","2013");
//        values.put("起讫周序", "3-5");
//        values.put("任课教师", "王二");
//        db.insert("教师报课信息表", null, values);
//        values.clear();
//        values.put("工号", "12347");
//        values.put("课程名称","高级人工智能");
//        values.put("年级","2013");
//        values.put("起讫周序", "6-18");
//        values.put("任课教师", "王五");
//        db.insert("教师报课信息表", null, values);
//        values.clear();
//
//        values.put("表名", "计算机专业");
//        values.put("学期","201302");
//        values.put("教师报课截止时间","20130107");
//        values.put("系负责人审核截止时间","20130125");
//        db.insert("发布表", null, values);
//        values.clear();
//        values.put("表名", "数学专业");
//        values.put("学期","201501");
//        values.put("教师报课截止时间","20150107");
//        values.put("系负责人审核截止时间","20150115");
//        db.insert("发布表", null, values);
//        values.clear();
        //=================================================================================

    }

    //====================================================================
    //忘记密码？ 事件监听============================================
    class ForgetClickListener implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            final AlertDialog.Builder Dia = new AlertDialog.Builder(Login.this);
            SQLOperateImpl tel = new SQLOperateImpl(Login.this);
            Tb_teachingoffice tele = tel.findById_teachingoffice("12345");
            Dia.setTitle("忘记密码？");
            Dia.setMessage("请联系教学办负责人!" + "\n" + "联系电话:18753621145");
            Dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            });
            Dia.create().show();
        }
    }
//===============================================================================

    //单选按钮组 事件监听=============================================
    class RadioClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rdgp_teacher:
                    number = 1;
                    break;
                case R.id.rdgp_department:
                    number = 2;
                    break;
                case R.id.rdgp_office:
                    number = 3;
                    break;
                default:
                    break;
            }
        }
    }

    //============================================================================
//控件绑定，监听器绑定===================================================
    private void initView() {
        edtt_userName = (EditText) findViewById(R.id.edtt_userName); //绑定 用户名输入
        edtt_password = (EditText) findViewById(R.id.edtt_passWord); //绑定 密码输入

        rdgp = (RadioGroup) findViewById(R.id.rdgp);  //绑定 单选按钮组
        rdgp.setOnCheckedChangeListener(new RadioClickListener()); //为 单选按钮组绑定事件监听器

        btn_login = (Button) findViewById(R.id.btn_login); //绑定 登录按钮
        btn_login.setOnClickListener(new LoginClickListener());  //为 登录按钮绑定事件监听器

        tvw_forget = (TextView) findViewById(R.id.tvw_forget);  //绑定 “忘记密码？”
        tvw_forget.setOnClickListener(new ForgetClickListener());    //为"忘记密码"绑定事件监听器

    }


    //登录按钮 事件监听=================================================
    class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            goTo();
            username = edtt_userName.getText().toString();   //获取账号
            String password = edtt_password.getText().toString();  //获取密码

            GlobalVariables.userId=username;
            GlobalVariables.password=password;

            SQLOperateImpl person = new SQLOperateImpl(Login.this);
            if (edtt_userName.length() < 1 || edtt_password.length() < 1) {
                Toast.makeText(Login.this, "账号或密码为空，请重新输入", Toast.LENGTH_SHORT).show();
            } else if (edtt_userName.length() < 5) {
                Toast.makeText(Login.this, "账号格式有误，请重新输入", Toast.LENGTH_SHORT).show();
            } else if (edtt_password.length() < 5) {
                Toast.makeText(Login.this, "密码格式有误，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                //连接服务器不能删==================================================================
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "" + number);
                params.put("username", edtt_userName.getText().toString());
                params.put("password", edtt_password.getText().toString());
                try {
                    HttpUtil.doPost(GlobalVariables.URL + "/login", params, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.equals("true")){

                                        goTo();
                                    }else{
                                        Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                                    }
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
                                    Toast.makeText(Login.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
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
        }
    }
// =====================================================================

    private void goTo(){
        Intent intent=new Intent();
        switch(number){
            case 1:
                intent.putExtra("gonghao",username);
                intent.setClass(Login.this, CourseDeclare.class);
                break;
            case 2:
                intent.putExtra("gonghao",username);
                intent.setClass(Login.this,TaskList.class);
                break;
            case 3:
                intent.setClass(Login.this, TaskManage.class);
                break;
        }
        Login.this.startActivity(intent);
    }

}
