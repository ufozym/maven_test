package com.demo.qa.utils;

import java.text.DecimalFormat;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataWriter {
    
    protected static final Logger logger = LoggerFactory.getLogger(DataReader.class);
    
    // private Boolean byColumnName = false;
    // private Boolean byRowKey = false;
    // private List<String> headers = new ArrayList<String>();
    //
    // private Integer size = 0;
    
    public DataWriter() {
    }
    
    // write resultSheet
    public static void writeData(XSSFSheet xSheet,
                                 String string,
                                 String iD,
                                 String test_case) {
        
        XSSFSheet sh = xSheet;
        String shname = sh.getSheetName();
        int rowNum = sh.getLastRowNum();
        System.out.println("rowNum" + rowNum);
        if ("Result".equals(shname)) {
            
            if (rowNum <= 0) {
                // write sheethead start
                XSSFRow headRow = sh.createRow(0);
                // write Head-ID
                XSSFCell headcell_id = headRow.createCell(0);
                headcell_id.setCellValue("ID");
                // write Head-TestCase
                XSSFCell headcell_tc = headRow.createCell(1);
                headcell_tc.setCellValue("TesCase");
                // write Head-Result
                XSSFCell headcell_re = headRow.createCell(2);
                headcell_re.setCellValue("Result");
                // write sheethead end
            }
            
            // write data
            XSSFRow dataRow = sh.createRow(rowNum + 1);
            dataRow.createCell(0).setCellValue(iD);
            dataRow.createCell(1).setCellValue(test_case);
            dataRow.createCell(2).setCellValue(string);
        } else if ("Output".equals(shname)) {
            if (rowNum <= 0) {
                // write sheethead start
                XSSFRow headRow = sh.createRow(0);
                // write Head-ID
                XSSFCell headcell_id = headRow.createCell(0);
                headcell_id.setCellValue("ID");
                // write Head-TestCase
                XSSFCell headcell_tc = headRow.createCell(1);
                headcell_tc.setCellValue("TesCase");
                // write Head-Response
                XSSFCell headcell_re = headRow.createCell(2);
                headcell_re.setCellValue("Response");
                // write sheethead end
            }
            // int rowNum = sh.getLastRowNum();
            
            // write data
            XSSFRow dataRow = sh.createRow(rowNum + 1);
            dataRow.createCell(0).setCellValue(iD);
            dataRow.createCell(1).setCellValue(test_case);
            dataRow.createCell(2).setCellValue(string);
        }else if ("Comparison".equals(shname)){
            if (rowNum <= 0) {
                // write sheethead start
                XSSFRow headRow = sh.createRow(0);
                // write Head-ID
                XSSFCell headcell_id = headRow.createCell(0);
                headcell_id.setCellValue("ID");
                // write Head-TestCase
                XSSFCell headcell_tc = headRow.createCell(1);
                headcell_tc.setCellValue("TesCase");
                // write Head-Assert
                XSSFCell headcell_re = headRow.createCell(2);
                headcell_re.setCellValue("Comparsion Result");
                // write Head-Failure field:Value
                // XSSFCell headcell_fv = headRow.createCell(2);
                // headcell_fv.setCellValue("Failure field:Value");
                // write sheethead end
            }
            
            // write data
            XSSFRow dataRow = sh.createRow(rowNum + 1);
            dataRow.createCell(0).setCellValue(iD);
            dataRow.createCell(1).setCellValue(test_case);
            dataRow.createCell(2).setCellValue(string);
        }
        
    }
    
//    public static void writeData(XSSFSheet comparsionSheet,
//                                 String string,String iD,
//                                 String test_case) {
//        
//        XSSFSheet sh = comparsionSheet;
//        int rowNum = sh.getLastRowNum();
//        if (rowNum <= 0) {
//            // write sheethead start
//            XSSFRow headRow = sh.createRow(0);
//            // write Head-ID
//            XSSFCell headcell_id = headRow.createCell(0);
//            headcell_id.setCellValue("ID");
//            // write Head-TestCase
//            XSSFCell headcell_tc = headRow.createCell(1);
//            headcell_tc.setCellValue("TesCase");
//            // write Head-Assert
//            XSSFCell headcell_re = headRow.createCell(2);
//            headcell_re.setCellValue("Comparsion Result");
//            // write Head-Failure field:Value
//            // XSSFCell headcell_fv = headRow.createCell(2);
//            // headcell_fv.setCellValue("Failure field:Value");
//            // write sheethead end
//        }
//        
//        // write data
//        XSSFRow dataRow = sh.createRow(rowNum + 1);
//        dataRow.createCell(0).setCellValue(iD);
//        dataRow.createCell(1).setCellValue(test_case);
//        dataRow.createCell(2).setCellValue(string);
//        
//    }
    
    public static void writeData(XSSFSheet resultSheet,
                                 double totalcase,
                                 double failedcase,
                                 String startTime,
                                 String endTime) {
        
        XSSFSheet sh = resultSheet;
        double pp = (totalcase - failedcase) / totalcase;
        double ppp = (double) ((Math.round(pp * 1000) / 1000.0) * 100);
        int rowNum = sh.getLastRowNum();
        
        XSSFRow dataRow = sh.createRow(rowNum + 2);
        dataRow.createCell(0).setCellValue("");
        dataRow.createCell(1).setCellValue("Pass Percentage: ");
        dataRow.createCell(2).setCellValue(ppp + "%");
        XSSFRow dataRow2 = sh.createRow(rowNum + 3);
        dataRow2.createCell(0).setCellValue("");
        dataRow2.createCell(1).setCellValue("Start Time :");
        dataRow2.createCell(2).setCellValue(startTime);
        XSSFRow dataRow3 = sh.createRow(rowNum + 4);
        dataRow3.createCell(0).setCellValue("");
        dataRow3.createCell(1).setCellValue("End Time :");
        dataRow3.createCell(2).setCellValue(endTime);
        
        // DecimalFormat df = new DecimalFormat("0.00");
        //
        // String pppp = df.format(pp);
        
        // System.out.println("math"+ppp);
        
        // System.out.println("decimal"+pppp);
        
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
    }
    
//    public static void writeData(XSSFSheet comparsionSheet,
//                                 String string,
//                                 String string2,
//                                 String iD,
//                                 String test_case) {
//        
//        XSSFSheet sh = comparsionSheet;
//        int rowNum = sh.getLastRowNum();
//        if (rowNum <= 0) {
//            // write sheethead start
//            XSSFRow headRow = sh.createRow(0);
//            // write Head-ID
//            XSSFCell headcell_id = headRow.createCell(0);
//            headcell_id.setCellValue("ID");
//            // write Head-TestCase
//            XSSFCell headcell_tc = headRow.createCell(1);
//            headcell_tc.setCellValue("TesCase");
//            // write Head-Assert
//            XSSFCell headcell_re = headRow.createCell(2);
//            headcell_re.setCellValue("Comparsion Result");
//            // write Head-Failure field:Value
//            // XSSFCell headcell_fv = headRow.createCell(2);
//            // headcell_fv.setCellValue("Failure field:Value");
//            // write sheethead end
//        }
//        
//        // write data
//        XSSFRow dataRow = sh.createRow(rowNum + 1);
//        dataRow.createCell(0).setCellValue(iD);
//        dataRow.createCell(1).setCellValue(test_case);
//        dataRow.createCell(2).setCellValue(result.getMessage());
//        
//    }
    
}
