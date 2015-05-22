package com.demo.qa.rest_api_test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.demo.qa.utils.DataReader;
import com.demo.qa.utils.DataWriter;
import com.demo.qa.utils.HTTPReqGen;
import com.demo.qa.utils.RecordHandler;
import com.demo.qa.utils.SheetUtils;
import com.demo.qa.utils.Utils;
import com.jayway.restassured.response.Response;

public class HTTPReqGenTest implements ITest {
    
    private Response response;
    private DataReader myInputData;
    private DataReader myBaselineData;
    private String template;
    
    public String getTestName() {
        return "API Test";
    }
    
    String filePath = "";
    
    XSSFWorkbook wb = null;
    XSSFSheet inputSheet = null;
    XSSFSheet baselineSheet = null;
    XSSFSheet outputSheet = null;
    XSSFSheet comparsionSheet = null;
    XSSFSheet resultSheet = null;
    
    private double totalcase = 0;
    private double failedcase = 0;
    private String startTime = "";
    private String endTime = "";
    
    @BeforeTest
    @Parameters("workBook")
    public void setup(String path) {
        filePath = path;
        try {
            wb = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputSheet = wb.getSheet("Input");
        baselineSheet = wb.getSheet("Baseline");
        
        SheetUtils.removeSheetByName(wb, "Output");
        SheetUtils.removeSheetByName(wb, "Comparison");
        SheetUtils.removeSheetByName(wb, "Result");
        outputSheet = wb.createSheet("Output");
        comparsionSheet = wb.createSheet("Comparison");
        resultSheet = wb.createSheet("Result");
        
        try {
            InputStream is = HTTPReqGenTest.class.getClassLoader()
                                                 .getResourceAsStream("http_request_template.txt");
            template = IOUtils.toString(is, Charset.defaultCharset());
        } catch (Exception e) {
            Assert.fail("Problem fetching data from input file:"
                    + e.getMessage());
        }
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = sf.format(new Date());
    }
    
    @DataProvider(name = "WorkBookData")
    protected Iterator<Object[]> testProvider(ITestContext context) {
        
        List<Object[]> test_IDs = new ArrayList<Object[]>();
        
        myInputData = new DataReader(inputSheet, true, true, 0);
        Map<String, RecordHandler> myInput = myInputData.get_map();
        
        // sort map in order so that test cases ran in a fixed order
        Map<String, RecordHandler> sortmap = Utils.sortmap(myInput);
        
        for (Map.Entry<String, RecordHandler> entry : sortmap.entrySet()) {
            String test_ID = entry.getKey();
            String test_case = entry.getValue().get("TestCase");
            if (!test_ID.equals("") && !test_case.equals("")) {
                test_IDs.add(new Object[] { test_ID, test_case });
            }
            totalcase++;
        }
        
        myBaselineData = new DataReader(baselineSheet, true, true, 0);
        
        return test_IDs.iterator();
    }
    
    /*
    @Test(dataProvider = "WorkBookData", description = "ReqGenTest")
    public void api_test(String ID, String test_case) {
        
        HTTPReqGen myReqGen = new HTTPReqGen();
        
        try {
            // myReqGen.generate_request(template, myInputData.get_record(ID));
            myReqGen.generate_request(myInputData.get_record(ID));
            response = myReqGen.perform_request();
        } catch (Exception e) {
            Assert.fail("Problem using HTTPRequestGenerator to generate response: "
                    + e.getMessage());
        }
        
        String baseline_message = myBaselineData.get_record(ID).get("Response");
        
        try {
            
            baseline_message = java.net.URLDecoder.decode(baseline_message,
                                                          "utf-8");
            
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if (response.statusCode() == 200) {
            
            try {
                
                String responsedecode = java.net.URLDecoder.decode(response.asString(),
                                                                   "utf-8");
                
                DataWriter.writeData(outputSheet, responsedecode, ID, test_case);
                
                // System.out.println("json start aaa"+ response.asString());
                // String a =
                // java.net.URLDecoder.decode(response.asString(),"utf-8");
                // System.out.println("json start bbbb"+ a);
                // System.out.println("json start dddd"+ baseline_message);
                // System.out.println("json start cccc"+
                // (baseline_message.equals(a)));
                
                JSONCompareResult result = JSONCompare.compareJSON(baseline_message,
                                                                   responsedecode,
                                                                   JSONCompareMode.NON_EXTENSIBLE);
                
                // System.out.println("getMessage"+result.getMessage());
                // System.out.println("getfield"+result.getField());
                // System.out.println("getact"+result.getActual().toString());
                // System.out.println("getexp"+result.getExpected().toString());
                // List<FieldComparisonFailure> l = result.getFieldFailures();
                // Iterator it = l.iterator();
                // System.out.println("Iterator");
                // while(it.hasNext()){
                // System.out.println("Iterator");
                // FieldComparisonFailure ff = (FieldComparisonFailure)
                // it.next();
                // System.out.println("getfield"+ff.getField());
                // System.out.println("getActual"+ff.getActual().toString());
                // System.out.println("getExpected"+ff.getExpected().toString());
                // }
                
                if (!result.passed()) {
                    System.out.println("Fail!!!!!!!! result != expect");
                    DataWriter.writeData(comparsionSheet,
                                         result.getMessage(),
                                         ID,
                                         test_case);
                    DataWriter.writeData(resultSheet, "false", ID, test_case);
                    failedcase++;
                } else {
                    System.out.println("Success!!!!!!! result == expect");
                    DataWriter.writeData(resultSheet, "true", ID, test_case);
                }
            } catch (JSONException e) {
                DataWriter.writeData(comparsionSheet,
                                     "Problem to assert Response and baseline messages: "
                                             + e.getMessage(),
                                     ID,
                                     test_case);
                DataWriter.writeData(resultSheet, "error", ID, test_case);
                failedcase++;
                Assert.fail("Problem to assert Response and baseline messages: "
                        + e.getMessage());
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            DataWriter.writeData(outputSheet,
                                 response.statusLine(),
                                 ID,
                                 test_case);
            if (baseline_message.equals(response.statusLine())) {
                DataWriter.writeData(resultSheet, "true", ID, test_case);
            } else {
                DataWriter.writeData(comparsionSheet,
                                     baseline_message+"</n>"+
                                     response.statusLine(),
                                     ID,
                                     test_case);
                DataWriter.writeData(resultSheet, "false", ID, test_case);
                failedcase++;
            }
        }
    }
    */
    
    
    @Test(dataProvider = "WorkBookData", description = "ReqGenTest")
    public void api_test(String ID, String test_case) {
        
        HTTPReqGen myReqGen = new HTTPReqGen();
        
        try {
            // myReqGen.generate_request(template, myInputData.get_record(ID));
            myReqGen.generate_request(myInputData.get_record(ID));
            response = myReqGen.perform_request();
        } catch (Exception e) {
            Assert.fail("Problem using HTTPRequestGenerator to generate response: "
                    + e.getMessage());
        }
        //注释
        String baseline_message = myBaselineData.get_record(ID).get("Response");
            
        
        if (response.statusCode() == 200) {
            
            try {
                
                String responsedecode = java.net.URLDecoder.decode(response.asString(),
                                                                   "utf-8");
              
                int index = responsedecode.indexOf(baseline_message);
                
                if( index == -1){
                    
                    System.out.println("Fail!!!!!!!! result != expect");
                    DataWriter.writeData(resultSheet, "Fail", ID, test_case);
                    failedcase++;
                } else {
                    System.out.println("Success!!!!!!! result == expect");
                    DataWriter.writeData(resultSheet, "Pass", ID, test_case);
                }
            } catch (Exception e) {
                DataWriter.writeData(resultSheet, "Error", ID, test_case);
                failedcase++;
                Assert.fail("Problem to assert Response and baseline messages: "
                        + e.getMessage());
            } 
        } else {
            
            try {
                String responsedecode = java.net.URLDecoder.decode(response.asString(),
                        "utf-8");
                
                DataWriter.writeData(outputSheet,
                                     response.statusLine(),
                                     ID,
                                     test_case);
                if ( responsedecode.indexOf(baseline_message)!=-1 ) {
                    DataWriter.writeData(resultSheet, "Pass", ID, test_case);
                } else {
                    DataWriter.writeData(resultSheet, "Fail", ID, test_case);
                    failedcase++;
                }
                
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        }
    }
    
    @AfterTest
    public void teardown() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = sf.format(new Date());
        DataWriter.writeData(resultSheet,
                             totalcase,
                             failedcase,
                             startTime,
                             endTime);
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
