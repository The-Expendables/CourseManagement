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
    public static List<String[]> readExcel(String currentpath)throws BiffException,IOException{
        Workbook workbook=null;
        try{
            workbook=Workbook.getWorkbook(new File(currentpath));
        }catch(Exception e){
            e.printStackTrace();
        }
        if (workbook == null) throw new AssertionError();
        Sheet sheet = workbook.getSheet(0);
        //列数
        int colcount = sheet.getColumns();
        //行号
        int rowcount = sheet.getRows();

        List<String[]> list=new ArrayList<String[]>();
        Cell cell=null;
        for(int i=0;i<rowcount;i++){
            String[] str = new String[colcount];
            for(int j=0;j<colcount;j++){
                cell=sheet.getCell(j, i);
                str[j]=cell.getContents();
                str[j]=str[j].replaceAll("\\s","");
            }
            list.add(str);
        }
        workbook.close();
        return list;
    }

    public static void writeExcel(String currentpath,List list){
        try{
            Log.i("info","In Writ Excel");
            WritableWorkbook wwb;
            OutputStream os=new FileOutputStream(currentpath);
            wwb=Workbook.createWorkbook(os);
            WritableSheet sheet=wwb.createSheet("汇总表",0);
            sheet.mergeCells(0,0,11,0);
            for(int j=0;j<12;j++){
                if(j==7||j==8) continue;
                sheet.mergeCells(j,1,j,2);
            }
            Label label;
            for(int i=0;i<list.size();i++){
                String[] str=(String[])list.get(i);
                for(int j=0;j<str.length;j++){
                    WritableCellFormat wc=new WritableCellFormat();
                    //设置居中
                    wc.setAlignment(Alignment.CENTRE);
                    //设置边框线
                    wc.setBorder(Border.ALL, BorderLineStyle.THIN);
                    label = new Label(j,i,str[j],wc);
//                label = new Label(i,0,title[i]);
                    sheet.addCell(label);
                }
            }
//            WritableCellFormat wc=new WritableCellFormat();
//            wc.setAlignment(Alignment.CENTRE);
//            //设置边框线
//            wc.setBorder(Border.ALL, BorderLineStyle.THIN);
//            label = new Label(0,0,"fuckyou!",wc);
//            sheet.addCell(label);
            wwb.write();
            wwb.close();
        }catch(Exception e){
            Log.i("error","---writeExcel wrong---");
            e.printStackTrace();
        }
    }

}
