package com.demo.qa.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.demo.qa.constant.QConstant;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SheetUtils {
    
    // XSSFWorkbook wb = null;
    
    public static void removeSheetByName(XSSFWorkbook xssfwb, String name) {
        
        try {
             xssfwb.removeSheetAt(xssfwb.getSheetIndex(name));
            FileInputStream fis = new FileInputStream(QConstant.FILEPATH);   
            FileOutputStream fileOut = new FileOutputStream(QConstant.FILEPATH);
            xssfwb.write(fileOut);
            fileOut.flush();
            fis.close(); 
            fileOut.close();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("xssf remove sheet error !!!!!!!!!!!! sheet's name is :"
                    + name);
        }
        
    }
    
//    public void fileWrite(String targetFile, XSSFWorkbook wb) throws Exception {
//        FileOutputStream fileOut = new FileOutputStream(targetFile);
//        wb.write(fileOut);
//        fileOut.flush();
//        fileOut.close();
//    }
    
    public static void main(String[] args) {
        
        XSSFWorkbook wb = null;
         try {
              wb = new XSSFWorkbook(new FileInputStream(QConstant.FILEPATH));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        removeSheetByName(wb, "Input");
        
    }
    
}
