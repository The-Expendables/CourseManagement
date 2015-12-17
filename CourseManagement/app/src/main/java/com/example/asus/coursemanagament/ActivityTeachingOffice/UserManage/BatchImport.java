package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;
import jxl.read.biff.BiffException;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.Tb.Tb_teacher;
import com.example.asus.coursemanagament.Util.ExcelUtil;
import com.example.asus.coursemanagament.Util.GlobalVariables;
import com.example.asus.coursemanagament.Util.HttpCallbackListener;
import com.example.asus.coursemanagament.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchImport extends AppCompatActivity {

    private String teachers_json;
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_import);

        //返回监听事件============================================
        ImageView ivw_back = (ImageView) findViewById(R.id.ivw_back);
        ivw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //============================================


    }
    //确认导入按钮============================================
    public void onBtn_ok(View view){
        Map<String, String> params = new HashMap<String, String>();
        params.put("table_name","教师信息表");
        params.put("info_json", teachers_json);
        try {
            HttpUtil.doPost(GlobalVariables.URL + "/GetTeachersInfo", params, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BatchImport.this,response,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BatchImport.this,TeacherList.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BatchImport.this, "服务器访问失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("info", e.toString());
        }
    }
    //============================================

    //选择文件监听事件====================================
    public void onBtn_select(View view) {
        FilePicker picker = new FilePicker(this);
        picker.setShowHideDir(false);
        picker.setInitPath(Common.getRootPath(this) + "Download/");
        //picker.setAllowExtensions(new String[]{".apk"});
        picker.setMode(FilePicker.Mode.File);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
//                Toast.makeText(BatchImport.this,currentPath,Toast.LENGTH_SHORT).show();
                List list = null;
                try {
                    list = ExcelUtil.readExcel(currentPath);
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                }

                //显示文件名===================================================================
                TextView tvw_show_excel = (TextView) findViewById(R.id.tvw_show_excel);
                int p;
                for (p = currentPath.length() - 1; p >= 0 && currentPath.charAt(p) != '/'; p--) ;
                tvw_show_excel.setText(currentPath.substring(p + 1));
                //======================

                //打包数据
                List<Tb_teacher> teachers=new ArrayList<Tb_teacher>();
                for (int i = 1; i < list.size(); i++) {
                    String[] strings = (String[]) list.get(i);
                    Tb_teacher tb_teacher=new Tb_teacher();

                    tb_teacher.setId(strings[0]);
                    tb_teacher.setPassword(strings[1]);
                    tb_teacher.setName(strings[2]);
                    tb_teacher.setDepartment(strings[3]);
                    tb_teacher.setSex(strings[4]);
                    tb_teacher.setBirth(strings[5]);
                    tb_teacher.setEmail(strings[6]);
                    tb_teacher.setPhone(strings[7]);

                    teachers.add(tb_teacher);

                }
                teachers_json=gson.toJson(teachers);

            }
        });
        picker.showAtBottom();
    }
    //=================================
}
