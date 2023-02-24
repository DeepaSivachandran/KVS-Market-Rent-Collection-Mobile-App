package com.example.collectionreport;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RestAPI {

    public  static  String urlString = "http://kvsmarket.trio-s.com/webservice/";
 //  public  static  String urlString= "http://172.16.1.250:8007/CollectBuddy_Market/";

 //   public  static  String neturl ="http://172.16.1.250:8007/";
   public  static  String neturl = "http://kvsmarket.trio-s.com";

  //UPdate noew 20-02-2023

    public String GetJSONResponse(String paraURL, String paraData) {
        BufferedReader reader = null;
        StringBuffer response = null;
        try {
            URL url = new URL(paraURL);
            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            if (!paraData.equals("")) {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(paraData);
                wr.flush();
            }
            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            response = new StringBuffer();
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                response.append(line);
                Log.i("GET_ResponseException=========>", line.toString());
            }
            // Log.i("GET_Response ======>",response.toString());
        }
        catch (Exception ex) {
            Log.i("GET_ResponseException", ex.toString());
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return response.toString();
    }

    public JSONObject GetDashBoard(String request) throws Exception {
        String url = urlString+"dashboard.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode(request, "UTF-8");
        // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("DashboardException", ex.toString());
        }

        return myResponse;
    }


    public JSONObject GetReportList(String date) throws Exception {
        String url = urlString+"collection_report_list.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("paradate", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
        // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("DashboardException", ex.toString());
        }

        return myResponse;
    }
    public JSONObject GetLoginDetails(String deviceid) throws Exception {
        String url = urlString+"login.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("paradeviceid", "UTF-8") + "=" + URLEncoder.encode(deviceid, "UTF-8");
       // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }


    public JSONObject Async_DB_Services(String acno, String receiptno, String ag_code, String actype, String modepay, String cancel, String tr_time,String chequedt,String chequeno,String collectdt,String collectamt,String remark,String receipt_code,int customer_type,String customer_name,String phone_no,String city,String customer_no) throws UnsupportedEncodingException {
        String url = urlString+"sync_services.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("paracode", "UTF-8") + "=" + URLEncoder.encode(ag_code, "UTF-8");
        data +="&" + URLEncoder.encode("paraacno", "UTF-8") + "=" + URLEncoder.encode(acno, "UTF-8");
        data +="&" + URLEncoder.encode("parareceiptno", "UTF-8") + "=" + URLEncoder.encode(receiptno, "UTF-8");
        data +="&" + URLEncoder.encode("paraactype", "UTF-8") + "=" + URLEncoder.encode(actype, "UTF-8");
        data +="&" + URLEncoder.encode("paramodepay", "UTF-8") + "=" + URLEncoder.encode(modepay, "UTF-8");
        data +="&" + URLEncoder.encode("paracancel", "UTF-8") + "=" + URLEncoder.encode(cancel, "UTF-8");
        data +="&" + URLEncoder.encode("paratr_time", "UTF-8") + "=" + URLEncoder.encode(tr_time, "UTF-8");
        data +="&" + URLEncoder.encode("parachequedt", "UTF-8") + "=" + URLEncoder.encode(chequedt, "UTF-8");
        data +="&" + URLEncoder.encode("parachequeno", "UTF-8") + "=" + URLEncoder.encode(chequeno, "UTF-8");
        data +="&" + URLEncoder.encode("paracollectdt", "UTF-8") + "=" + URLEncoder.encode(collectdt, "UTF-8");
        data +="&" + URLEncoder.encode("paracollectamt", "UTF-8") + "=" + URLEncoder.encode(collectamt, "UTF-8");
        data +="&" + URLEncoder.encode("pararemark", "UTF-8") + "=" + URLEncoder.encode(remark, "UTF-8");
        data +="&" + URLEncoder.encode("parareceiptcode", "UTF-8") + "=" + URLEncoder.encode(receipt_code, "UTF-8");
        data +="&" + URLEncoder.encode("paracustomer_type", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(customer_type), "UTF-8");

        data +="&" + URLEncoder.encode("paracustomer_name", "UTF-8") + "=" + URLEncoder.encode(customer_name, "UTF-8");
        data +="&" + URLEncoder.encode("paraphone_no", "UTF-8") + "=" + URLEncoder.encode(phone_no, "UTF-8");
        data +="&" + URLEncoder.encode("paracity", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8");
        data +="&" + URLEncoder.encode("paracustomer_no", "UTF-8") + "=" + URLEncoder.encode(customer_no, "UTF-8");
        // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            Log.i("PARAMETERS TO SYNC", data);
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }

    public JSONObject GetMasterDetails(String AGENTCODE,String Start_date,String End_date,String End_year ) throws UnsupportedEncodingException {
        String url = urlString+"collectbuddy.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("paracode", "UTF-8") + "=" + URLEncoder.encode(AGENTCODE, "UTF-8");
               data +="&" + URLEncoder.encode("parastartdate", "UTF-8") + "=" + URLEncoder.encode(Start_date, "UTF-8");
               data +="&" + URLEncoder.encode("paraenddate", "UTF-8") + "=" + URLEncoder.encode(End_date, "UTF-8");
               data +="&" + URLEncoder.encode("paraendyear", "UTF-8") + "=" + URLEncoder.encode(End_year, "UTF-8");
        // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }


    public JSONObject GetSaveTransaction(String tenantcode,String tenantname,String totalamount,String statuscode,String receiptno,String  receiptcode,String shopcode ,String shopname,
                                         String rentcycle,String shopstatus,String rent,String collectiondate) throws UnsupportedEncodingException {
        String url = urlString+"save_transaction.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("tenantcode", "UTF-8") + "=" + URLEncoder.encode(tenantcode, "UTF-8");
        data +="&" + URLEncoder.encode("tenantname", "UTF-8") + "=" + URLEncoder.encode(tenantname, "UTF-8");
        data +="&" + URLEncoder.encode("totalamount", "UTF-8") + "=" + URLEncoder.encode(totalamount, "UTF-8");
        data +="&" + URLEncoder.encode("statuscode", "UTF-8") + "=" + URLEncoder.encode(statuscode, "UTF-8");
        data +="&" + URLEncoder.encode("receiptno", "UTF-8") + "=" + URLEncoder.encode(receiptno, "UTF-8");

        data +="&" + URLEncoder.encode("receiptcode", "UTF-8") + "=" + URLEncoder.encode(receiptcode, "UTF-8");

        data +="&" + URLEncoder.encode("shopcode", "UTF-8") + "=" + URLEncoder.encode(shopcode, "UTF-8");
        data +="&" + URLEncoder.encode("shopname", "UTF-8") + "=" + URLEncoder.encode(shopname, "UTF-8");
        data +="&" + URLEncoder.encode("rentcycle", "UTF-8") + "=" + URLEncoder.encode(rentcycle, "UTF-8");
        data +="&" + URLEncoder.encode("shopstatus", "UTF-8") + "=" + URLEncoder.encode(shopstatus, "UTF-8");


        data +="&" + URLEncoder.encode("rent", "UTF-8") + "=" + URLEncoder.encode(rent, "UTF-8");
        data +="&" + URLEncoder.encode("collectiondate", "UTF-8") + "=" + URLEncoder.encode(collectiondate, "UTF-8");

        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }


    public JSONObject GetReceiptno() throws UnsupportedEncodingException {
        String url = urlString+"transaction_max.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("parapasscode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");


        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }

}
