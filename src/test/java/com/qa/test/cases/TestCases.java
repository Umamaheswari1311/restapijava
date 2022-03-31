package com.qa.test.cases;

import com.qa.client.RestClient;
import com.qa.test.base.TestBase;
import com.qa.utils.ExcelReader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import  io.restassured.response.Response;

public class TestCases extends TestBase {
    ExcelReader reader=new ExcelReader();
    private String sTestCaseName;
    private int iTestCaseRow;
    RestClient client = new RestClient();
    Response response;
    public static final String FILEPATH = "D:\\Workspace\\RestApiFramework\\src\\main\\resources\\"+GetSheetName+".xlsx";
    @SuppressWarnings("rawtypes")
    @Test(dataProvider="GET")
    public void getResponseTest(String run,String url,String jsonElement,String value) {
        if (run.equals("YES")) {

            response=client.get(url);
            //Validate status code
            Assert.assertEquals(client.getResponseCode(response), Response_Status_Code_200);

            // Validate if Response Body Contains a specific String
            Assert.assertTrue(client.getResponseBody(response).contains("firstName"));

            // Validate if the specific JSON element is equal to expected value
            Assert.assertTrue(client.getValueBy(response,jsonElement).equalsIgnoreCase(value));

            //Validate Response Time
            System.out.println("ResponseTime:" + client.getResponseTime());

        }
    }
    @SuppressWarnings("rawtypes")
    @Test(dataProvider="POST")
    public void postResponseTest(String run,String url,String email,String f_name,String l_name,String Gender) {
        if (run.equals("YES")){
          //Create new data using Post Method
        int code= client.getResponseCode(client.post(url,email,f_name,l_name,Gender));
        Assert.assertEquals(code,Response_Status_Code_201);

        //Check post record is present in database
            response=client.get(url);
            //Validate status code
            Assert.assertEquals(client.getResponseCode(response), Response_Status_Code_200);
        }
    }

    @SuppressWarnings("rawtypes")
    @Test(dataProvider="DELETE")
    public void DeleteResponseTest(String run,String url,String email,String f_name,String l_name,String Gender) {
        if (run.equals("YES")){
             response=client.delete(url);
            //Validate status code
            Assert.assertEquals(client.getResponseCode(response), Response_Status_Code_200);
            //Negative validation -Delete data should not present
            response=client.get(url);
            //Validate status code
            Assert.assertEquals(client.getResponseCode(response), Response_Status_Code_404);

        }
    }

    @DataProvider(name = "GET")


    public Object[][] getData() throws Exception{

        // Setting up the Test Data Excel file

        ExcelReader.setExcelFile(FILEPATH,"GET");

        sTestCaseName = this.toString();

        // From above method we get long test case name including package and class name etc.

        // The below method will refine your test case name, exactly the name use have used

        sTestCaseName = ExcelReader.getTestCaseName(this.toString());

        // Fetching the Test Case row number from the Test Data Sheet

        // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet

        iTestCaseRow = ExcelReader.getRowContains(sTestCaseName,0);

        Object[][] testObjArray = ExcelReader.getTableArray(FILEPATH,"GET",iTestCaseRow);

        return (testObjArray);

    }



    @DataProvider(name = "POST")
    public Object[][] getPostData() throws Exception{

        ExcelReader.setExcelFile(FILEPATH,"POST");
        sTestCaseName = this.toString();
        sTestCaseName = ExcelReader.getTestCaseName(this.toString());
        iTestCaseRow = ExcelReader.getRowContains(sTestCaseName,0);
        Object[][] testObjArray = ExcelReader.getTableArray(FILEPATH,"DELETE",iTestCaseRow);
        return (testObjArray);

    }
    @DataProvider(name = "DELETE")
    public Object[][] getDeleteData() throws Exception{

        ExcelReader.setExcelFile(FILEPATH,"POST");
        sTestCaseName = this.toString();
        sTestCaseName = ExcelReader.getTestCaseName(this.toString());
        iTestCaseRow = ExcelReader.getRowContains(sTestCaseName,0);
        Object[][] testObjArray = ExcelReader.getTableArray(FILEPATH,"GET",iTestCaseRow);
        return (testObjArray);

    }

}
