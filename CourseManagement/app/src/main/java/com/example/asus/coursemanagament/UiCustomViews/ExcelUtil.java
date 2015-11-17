package com.example.asus.coursemanagament.UiCustomViews;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Administrator on 2015/11/17.
 */
public class ExcelUtil {
    public static List readExcel(String currentpath)throws BiffException,IOException{
        Workbook workbook=null;
        try{
            workbook=Workbook.getWorkbook(new File(currentpath));
        }catch(Exception e){
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        //列数
        int colcount = sheet.getColumns();
        //行号
        int rowcount = sheet.getRows();

        List list=new ArrayList();
        Cell cell=null;
        for(int i=0;i<rowcount;i++){
            String[] str = new String[colcount];
            for(int j=0;j<colcount;j++){
                cell=sheet.getCell(j,i);
                str[j]=cell.getContents();
            }
            list.add(str);
        }
        workbook.close();
        return list;
    }

    public static void writeExcel(String currentpath){
        String[] title={"编号","产品价格","产品数量","生产日期","产地","是否生产"};
        try{
            WritableWorkbook wwb;
            OutputStream os=new FileOutputStream(currentpath);
            wwb=Workbook.createWorkbook(os);
            WritableSheet sheet=wwb.createSheet("产品清单",0);
            Label label;
            for(int i=0;i<title.length;i++){
                WritableCellFormat wc=new WritableCellFormat();
                //设置居中
                wc.setAlignment(Alignment.CENTRE);
                //设置边框线
                wc.setBorder(Border.ALL, BorderLineStyle.THIN);
                label = new Label(i,0,title[i],wc);
//                label = new Label(i,0,title[i]);
                sheet.addCell(label);
            }
            wwb.write();
            wwb.close();
        }catch(Exception e){
            Log.i("error","---writeExcel wrong---");
            e.printStackTrace();
        }
    }

}
