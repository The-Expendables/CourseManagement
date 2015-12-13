package com.example.asus.coursemanagament.ActivityTeachingOffice.UserManage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.qqtheme.framework.helper.Common;
import cn.qqtheme.framework.picker.FilePicker;
import jxl.read.biff.BiffException;

import com.example.asus.coursemanagament.R;
import com.example.asus.coursemanagament.UiCustomViews.ExcelUtil;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BatchImport extends AppCompatActivity {

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
                List list = null;
                try {
                    list = ExcelUtil.readExcel(currentPath);
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(BatchImport.this,data.toString(),Toast.LENGTH_SHORT).show();
                /*TextView tvw_show_excel = (TextView) findViewById(R.id.tvw_show_excel);
                String result = "";
                assert list != null;
                for (int i = 0; i < list.size(); i++) {
                    String[] str = (String[]) list.get(i);
                    for (String aStr : str) {
                        result += aStr;
                        result += ";";
                    }
                    result += "\n";
                }
                tvw_show_excel.setText(result);*/

            }
        });
        picker.showAtBottom();
    }
    //=================================
}
