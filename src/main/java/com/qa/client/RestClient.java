package com.qa.client;


import  io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import  io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.JsonObject;

public class RestClient {
    private Response response;
String hostUrl="https://gorest.co.in";

    //Get Response
    public Response get(String url)
    {
        RestAssured.baseURI = hostUrl;
        RequestSpecification httpRequest = RestAssured.given();
        // Set HTTP Headers
        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("Authorization", "Bearer L41uyhzxoig9roHjTyRHFvBT48K2GjCMjbVC");
        Response response = httpRequest.get(url);
        System.out.println(response);
        return response;
    }
 //Get status code
    public int getResponseCode(Response response)
    {
      int code=  response.getStatusCode();
return code;
    }
//Get Response body string
    public String getResponseBody(Response response)

    {
        // Get Response Body
        ResponseBody body = response.getBody();
        // Get Response Body as String
        String bodyStringValue = body.asString();

    return bodyStringValue;

    }
//Get particular value from response body
    public String getValueBy(JSONObject jsonResponse,String jpath)

    {
        Object obj= jsonResponse;
        for(String s :jpath.split("/"))
        {
            System.out.println(s);
            if(s!=null)
            if(!(s.contains("[")||s.contains("]")))
                obj=((JSONObject) obj).get(s);
            else if(s.contains("[")||s.contains("]"))
                    obj=((JSONArray)((JSONObject)obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replace("]","")));


        }
        return obj.toString();
    }

    public String getValueBy(Response response,String jsonEle)
    {
        // Get JSON Representation from Response Body
       JsonPath jsonPathEvaluator = response.jsonPath();
        // Get specific element from JSON document
        String value=jsonPathEvaluator.get(jsonEle);
return value;
    }


//Get response Time
    public long getResponseTime()
    {
        long timeTaken;
        timeTaken = response.getTime();
        return timeTaken;
    }
    //************************************************************************************************
    //Post Response
    public Response post(String url,String email,String fName,String lName,String gender)
    {
        RestAssured.baseURI =hostUrl;
       RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("Authorization", "Bearer L41uyhzxoig9roHjTyRHFvBT48K2GjCMjbVC");
       JSONObject jsonObj=new JSONObject();
        jsonObj.put("email",email);
        jsonObj.put("first_name",fName);
        jsonObj.put("last_name",lName);
        jsonObj.put("gender",gender);
        httpRequest.body(jsonObj.toString());
       Response response=httpRequest.post(url);
return response;
}
//*********************************************************************************************************
//Delete Response

public Response delete(String url)
{
    RestAssured.baseURI = hostUrl;
    RequestSpecification httpRequest = RestAssured.given();
    // Set HTTP Headers
    httpRequest.header("Content-Type", "application/json");
    httpRequest.header("Authorization", "Bearer L41uyhzxoig9roHjTyRHFvBT48K2GjCMjbVC");
    Response response = httpRequest.delete(url);
    System.out.println(response);
    return response;
}
}