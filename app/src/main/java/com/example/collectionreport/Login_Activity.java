package com.example.collectionreport;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

public class Login_Activity extends AppCompatActivity {
    String commaseparatedlist="";
    ArrayList<String> Company_name_li = new ArrayList<>();
    ArrayList<String> date_list = new ArrayList<>();
    Button clean,btn_back,btn1,dot_1,dot_2,dot_3,dot_4,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    TextView company_name_tv,user_name_tv,tv_1,tv_2,tv_3,tv_4;
    View point_1,point_2,point_3,point_4;
    private String codeString = "";
    Context context;
    Calendar calendar,temp_calendar;
    String SCHEDULE_DATE="",TEMP_DATE="",COMPANY_NAME="",SUBTITLE_1 = "",SUBTITLE_2= "",CITY_NAME ="";
    SimpleDateFormat md_format = new SimpleDateFormat("dd-MM-yyyy");
    private static final int MAX_LENGHT = 4;
    public  static  String getdeviceid="";
    public static JSONArray details=null;
    public static JSONArray settings_details=null,company_details = null;
    public static String[] agent_code,db_agent_name, db_passcode, db_login_status,db_device_id;
    ArrayList<String> api_agent_name = new ArrayList<String>();
    public  static ArrayList<String> api_agent_code = new ArrayList<String>();
    ArrayList<String> api_login_status = new ArrayList<String>();
    ArrayList<String> api_device_id = new ArrayList<String>();
    private ArrayList<String> api_agent_passcode = new ArrayList<String>();
    private ArrayList<Login_Details> login_details = new ArrayList<>();

    private ArrayList<Settings_Details> settingsdetails = new ArrayList<>();


    DataBaseHelper dataBaseHelper;
     public  static  String[] PERMISSIONS = {Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
    private boolean networkstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        btn0 =(Button) findViewById(R.id.btn0);
        btn1 =(Button) findViewById(R.id.btn1);
        btn2 =(Button) findViewById(R.id.btn2);
        btn3 =(Button) findViewById(R.id.btn3);
        btn4 =(Button) findViewById(R.id.btn4);
        btn5 =(Button) findViewById(R.id.btn5);
        btn6 =(Button) findViewById(R.id.btn6);
        btn7 =(Button) findViewById(R.id.btn7);
        btn8 =(Button) findViewById(R.id.btn8);
        btn9 =(Button) findViewById(R.id.btn9);

        clean = (Button) findViewById(R.id.clean);
        btn_back = (Button) findViewById(R.id.btn_back);


        tv_1 =(TextView) findViewById(R.id.tv_1);
        tv_2 =(TextView) findViewById(R.id.tv_2);
        tv_3 =(TextView) findViewById(R.id.tv_3);
        tv_4 =(TextView) findViewById(R.id.tv_4);

        point_1 =(View) findViewById(R.id.point_1);
        point_2 =(View) findViewById(R.id.point_2);
        point_3 =(View) findViewById(R.id.point_3);
        point_4 =(View) findViewById(R.id.point_4);

        user_name_tv  =(TextView) findViewById(R.id.user_name_tv);

        company_name_tv  =(TextView) findViewById(R.id.company_name_tv);

        dataBaseHelper = new DataBaseHelper(Login_Activity.this);

        networkstate = isNetworkAvailable();
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // CheckDeviceAuthendication();


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {


        }else{
            //Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


        }else{
            //Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
            }
        }



        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {


        }else{
            //Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET},1);
            }
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {


        }else{
            //Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);
            }
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (codeString.length() > 0) {

                    codeString = removeLastChar(codeString);
                    setDotImagesState();
                }*/
                    new AsyncLogin().execute();

            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeString.length() > 0) {

                    codeString = "";
                    setDotImagesState();
                }
            }
        });


        user_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent i = new Intent(Login_Activity.this,MainActivity.class);
              //  startActivity(i);
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(0);
                // Toast.makeText(MainActivity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
               if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                 setDotImagesState();

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getbtnStringCode(1);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }

                 setDotImagesState();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(2);
               if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                 setDotImagesState();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(3);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(4);

               if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(5);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(6);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(7);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(8);
               if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                setDotImagesState();

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(9);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                 setDotImagesState();

            }
        });


      //  check_tables();

        CheckDeviceAuthendication();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    private void call() {
        //tv_1.setText("");
       // tv_2.setText("");
       // tv_3.setText("");
      //  tv_4.setText("");

        point_1.setVisibility(View.GONE);
        point_2.setVisibility(View.GONE);
        point_3.setVisibility(View.GONE);
        point_4.setVisibility(View.GONE);
        if(codeString != null) {
           // Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
        }
    }


    private void getbtnStringCode(int val) {
        switch (val) {
            case 0:
                codeString += "0";
                break;
            case 1:
                codeString += "1";
                break;
            case 2:
                codeString += "2";
                break;
            case 3:
                codeString += "3";
                break;
            case 4:
                codeString += "4";
                break;
            case 5:
                codeString += "5";
                break;
            case 6:
                codeString += "6";
                break;
            case 7:
                codeString += "7";
                break;
            case 8:
                codeString += "8";
                break;
            case 9:
                codeString += "9";
                break;
            default:
                break;
        }


    }

    public class AsyncLogin extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,jsonObject_token;
        int flag;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

               /* RestAPI objRestAPI = new RestAPI();
                networkstate = isNetworkAvailable();
                if (networkstate == true) {
                    jsonObj = objRestAPI.GetLoginDetails(params[0]);

                }*/
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Boolean result) {
            if(codeString != null) {
                //Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();

               // if(networkstate) {
                    //API Login Validation
                if (codeString.length() == MAX_LENGHT) {

                    if(api_agent_passcode.size()>0) {

                        if(!api_login_status.get(0).equals("1")){
                            toast("Your account is inactive. Please contact admin.");
                        }else{
                            if (codeString.equals(String.valueOf(api_agent_passcode.get(0)))) {
                                Intent i = new Intent(Login_Activity.this, MainActivity.class);
                                Login_Activity.this.finish();
                                i.putExtra("PASSCODE", api_agent_passcode.get(0));
                                i.putExtra("AGENTCODE", api_agent_code.get(0));
                                i.putExtra("AGENTNAME", api_agent_name.get(0));
                                i.putExtra("DEVICE_ID", "");
                                i.putExtra("COMPANY_NAME", COMPANY_NAME);
                                i.putExtra("SUBTITLE_1", SUBTITLE_1);
                                i.putExtra("SUBTITLE_2", SUBTITLE_2);

                                i.putExtra("CITY_NAME",CITY_NAME);
                                startActivity(i);
                                Log.d("CODE STRING =============>", codeString);
                            } else {
                                point_1.setVisibility(View.GONE);
                                point_2.setVisibility(View.GONE);
                                point_3.setVisibility(View.GONE);
                                point_4.setVisibility(View.GONE);

                                codeString = "";

                                //  Toast.makeText(context, "invalid passcode", Toast.LENGTH_SHORT).show();
                                toast("Invalid PIN");
                                Log.d("CODE STRING =============>", codeString);
                            }
                        }
                    }
                }else{
                    toast("Please enter 4 digit PIN");
                    codeString = "";
                    setDotImagesState();
                }


//                }else{
//                }

            }



        }


    }

    private void setDotImagesState() {
      /*  for (int i = 0; i < codeString.length(); i++) {

            dots.get(i).setBackgroundResource(R.drawable.paswd_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setBackgroundResource(R.drawable.paswd_disable);
            }
        }*/
        if(codeString.length() == 0){
          //  tv_1.setText("");
          //  tv_2.setText("");
          //  tv_3.setText("");
          //  tv_4.setText("");
            point_1.setVisibility(View.GONE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 1){
           // tv_1.setText(String.valueOf(codeString.charAt(0)));
          //  tv_2.setText("");
          //  tv_3.setText("");
          //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 2){
           // tv_1.setText(String.valueOf(codeString.charAt(0)));
          //  tv_2.setText(String.valueOf(codeString.charAt(1)));
           // tv_3.setText("");
          // tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 3){
           // tv_1.setText(String.valueOf(codeString.charAt(0)));
           // tv_2.setText(String.valueOf(codeString.charAt(1)));
           // tv_3.setText(String.valueOf(codeString.charAt(2)));
          //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 4){
           // tv_1.setText(String.valueOf(codeString.charAt(0)));
           // tv_2.setText(String.valueOf(codeString.charAt(1)));
          //  tv_3.setText(String.valueOf(codeString.charAt(2)));
          //  tv_4.setText(String.valueOf(codeString.charAt(3)));
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.VISIBLE);
        }
    }

    private String removeAllChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-codeString.length() );
    }
    //Remove only last character
    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1 );
    }

    @SuppressLint("HardwareIds")
    public void CheckDeviceAuthendication(){

        try{
            //getdeviceid =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            getdeviceid = "f22d1103dcce2f11";
            if (getdeviceid.equals(null)) {
              //  getdeviceid = "000000";
            }
            //Toast.makeText(context,"Connection succeeded...!",Toast.LENGTH_SHORT).show();
            Log.d("Device ID : ",getdeviceid);
           // new AsyncChkUser().execute(getdeviceid);
        }
        catch (Exception ex){
            String getfunname = new Object(){}.getClass().getEnclosingMethod().getName();

        }

        if(!getdeviceid.equals("")){
           // Clipboard();
           // new Async_Local_DB().execute();
           // Toast.makeText(context,String.valueOf(getdeviceid),Toast.LENGTH_SHORT).show();
            if(isNetworkAvailable()){
                new AsyncCheck().execute(getdeviceid);
            }else{

            }

        }
    }


    public class Get_Company_Date extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor Settings_Cur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Login_Activity.this);
            baseAdapter.open();
            Settings_Cur = baseAdapter.Select_Settings();
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (Settings_Cur.getCount() > 0) {
                Company_name_li.clear();

                for (int i = 0; i < Settings_Cur.getCount(); i++) {
                    Company_name_li.add(String.valueOf(Settings_Cur.getString(2)));
                    //  SCHEDULE_DATE.add(String.valueOf(Settings_Cur.getString(7)));
                    //   TEMP_DATE.add(String.valueOf(Settings_Cur.getString(8)));

                    Log.d("SETTINGS COMPANY_DATE_LIST ======ROW("+i+")=========>",Settings_Cur.getString(1) +" === "+Settings_Cur.getString(2)+"==="+" === "+Settings_Cur.getString(3)+" === "+Settings_Cur.getString(6)+" ");
                    Settings_Cur.moveToNext();
                }
                if(Company_name_li.size()>=3){
                    COMPANY_NAME = String.valueOf(Company_name_li.get(0));

                  if(!COMPANY_NAME.equals("")){
                      company_name_tv.setText(COMPANY_NAME);
                  }
                }
            } else {

                Log.d("SHIFT_TABLE_DATELIST ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void check_tables() {

        DataBaseAdapter objdatabaseadapter = null;
        objdatabaseadapter = new DataBaseAdapter(Login_Activity.this);
        objdatabaseadapter.open();
        if(dataBaseHelper.getReadableDatabase()!=null) {
          //  new Async_Local_DB().execute();
        }else {
          //  dataBaseHelper.onCreate(null);
            try {

                String success = objdatabaseadapter.insert("","null","null", "null", "null", "null", "null", "null", "null", "null", "null","null");
                if (success.equals("success")) {
                    //GetServerURLList();
                    // edittxtip.setText("");
                    Toast.makeText(Login_Activity.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                   // new Async_Local_DB().execute();
                }

            } catch (Exception e) {
                Toast.makeText(Login_Activity.this, "Error in saving", Toast.LENGTH_SHORT).show();
            } finally {
                if (objdatabaseadapter != null)
                    objdatabaseadapter.close();
            }
        }
    }

    public class Async_Local_DB extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Login_Activity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select(getdeviceid);
            baseAdapter.close();

            //Log.d("CURSOR_DATA===============>", String.valueOf(mCur.getCount()));

            // } catch (Exception e) {

            // }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            db_agent_name = new String[0];
            //  passcode = new String[0];
            //  login_status = new String[0];
            if (mCur.getCount() > 0) {
                db_agent_name = new String[mCur.getCount()];
                db_passcode = new String[mCur.getCount()];
                db_login_status = new String[mCur.getCount()];
                agent_code = new String[mCur.getCount()];
                db_device_id = new String[mCur.getCount()];
                for (int i = 0; i < mCur.getCount(); i++) {
                    db_agent_name[i] = mCur.getString(0);
                    db_passcode[i] = mCur.getString(1);
                    db_login_status[i] = mCur.getString(2);
                    agent_code[i] = mCur.getString(3);
                    db_device_id[i] = mCur.getString(4);
                    Log.d("LOGIN_TABLE_DATA===============>", db_agent_name[i]+ db_passcode[i]+agent_code[i]);
                    mCur.moveToNext();
                }
            } else {

                //Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN_ACTIVITY_ERROR ===============>","No Data Available");
                // toast.setGravity(Gravity.CENTER, 0, 0);
                //toast.show();
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                Clipboard();
            }
            if(db_agent_name != null) {

                if (db_agent_name.length > 0) {
                   // Toast.makeText(Login_Activity.this, String.valueOf(db_agent_name[0]), Toast.LENGTH_SHORT).show();
                    user_name_tv.setText(String.valueOf(db_agent_name[0]));

                }
            }

            if(db_login_status != null) {

                if (db_login_status[0].equals("0")) {
                   // Clipboard();
                    //Toast.makeText(context, "Your account is inactive. Please contact admin.", Toast.LENGTH_SHORT).show();
                    toast("Your account is inactive. Please contact admin.");
                }
            }
           new Get_Company_Date().execute();
        }
    }


    public  void Clipboard(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login_Activity.this);
        builder.setMessage("Please contact system administrator with this [ "+ getdeviceid +" ] access id")
                .setTitle("Access Denied")
                .setCancelable(false)
                .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ClipboardManager myClipboard;
                        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                        ClipData myClip;
                        //String text = "hello world";
                        myClip = ClipData.newPlainText("Device ID", getdeviceid);
                        myClipboard.setPrimaryClip(myClip);
                       // Toast.makeText(Login_Activity.this,"Device ID Copied to ClipBoard",Toast.LENGTH_SHORT).show();
                        toast("Device ID Copied to ClipBoard");
                        //.setIcon(R.mipmap.admin)
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public class AsyncCheck extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,jsonObject_token;
        int flag;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
              //  if (networkstate) {

                    jsonObj = objRestAPI.GetLoginDetails(getdeviceid);
              //  }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Boolean result) {

            if (jsonObj == null || jsonObj.equals("")) {
               // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                toast("Please check your internet connection");
            } else {
                try {
                    int success = jsonObj.getInt("success");
                    if (success == 0) {
                        settingsdetails.clear();
                        api_agent_name.clear();
                        login_details.clear();
                        api_login_status.clear();
                       // Toast.makeText(context, "Invaild Password", Toast.LENGTH_SHORT).show();
                       // toast("Invaild Password");
                        Clipboard();

                    } else if (success == 1) {
                        settingsdetails.clear();
                        api_agent_name.clear();
                        login_details.clear();
                        api_login_status.clear();
                        details = jsonObj.getJSONArray("logindetails");

                        company_details = jsonObj.getJSONArray("companydetails");
                       // agent_code = new String[details.length()];
                        if (details.length() > 0) {
                            JSONObject json = details.getJSONObject(0);

                                  //agent_code[0] = json.getString("code");
                                 api_agent_code.add(json.getString("usercode"));
                                 api_agent_name.add(json.getString("displayname"));
                                 api_agent_passcode.add(json.getString("pin"));
                                 api_login_status.add((json.getString("statuscode")));
                                 user_name_tv.setText(json.getString("displayname"));

                                 login_details.add(new Login_Details("",json.getString("usercode"),
                                                                    json.getString("displayname"), "", "", "",
                                                                    json.getString("pin"), "", "","",""));


                                // Toast.makeText(context, "Welcome " + json.getString("username") + " !", Toast.LENGTH_SHORT).show();
                               // Intent i = new Intent(Login_Activity.this,MainActivity.class);
                              //  i.putExtra("username",json.getString("username"));
                               // i.putExtra("code",json.getString("code"));
                               // startActivity(i);
                                // toast("Welcome " + json.getString("username") +" !");
                               // Log.d("Login_FROM_API===========>",String.valueOf(json.getString("code")+"======"+json.getString("agent")));


                        }else{
                           // Toast.makeText(context, "please check your internet", Toast.LENGTH_SHORT).show();
                            Clipboard();
                        }

                        if(company_details.length()>0){
                            JSONObject json = company_details.getJSONObject(0);
                            String company_arr[] = json.getString("companyname").split(",");
                            if(company_arr.length>=3) {

                              //  company_name_tv.setText(String.valueOf(json.getString("companyname")));
                                company_name_tv.setText(String.valueOf(company_arr[0]));
                                COMPANY_NAME = String.valueOf(company_arr[0]);
                                SUBTITLE_1  = String.valueOf(company_arr[1]);
                                SUBTITLE_2  = String.valueOf(company_arr[2]);
                                CITY_NAME = json.getString("cityname");
                            }


                        }

                        } else if (success == 3) {
                       // Toast.makeText(context, "Your account is inactive. Please contact admin.", Toast.LENGTH_SHORT).show();
                        toast("Your account is inactive. Please contact admin.");
                    } else {
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("Login", e.getMessage());
                    networkstate = isNetworkAvailable();
                    if (networkstate) {
                        // new AsyncLoggerService().execute("Android : " + LOGTAG, getfunname, e.toString());
                    }
                }
            }

        }
    }

   /* public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/

  /* public boolean isNetworkAvailable() {

        int code;
        Boolean result=false;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL siteURL = new URL(RestAPI.neturl);
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.connect();
            code = connection.getResponseCode();
            if (code == 200) {
                result=true;
            }
            connection.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("AsyncSync", e.getMessage());
            result=false;

        }
        return result;
    }*/

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class Login_Details{
        String login_pk,deviceid, agent,code,isadmin,branch_id,remit_user,passcode,login_status,receiptno_max,customerno_max;

        public Login_Details(String login_pk,String code, String agent, String isadmin, String branch_id, String remit_user, String passcode, String deviceid, String login_status,String receiptno_max,String customerno_max) {
            this.login_pk = login_pk;
            this.agent = agent;
            this.code = code;
            this.isadmin = isadmin;
            this.branch_id = branch_id;
            this.remit_user = remit_user;
            this.passcode = passcode;
            this.login_status = login_status;
            this.deviceid = deviceid;
            this.receiptno_max =receiptno_max;
            this.customerno_max = customerno_max;
        }

        public String getCustomerno_max() {
            return customerno_max;
        }

        public void setCustomerno_max(String customerno_max) {
            this.customerno_max = customerno_max;
        }

        public String getReceiptno_max() {
            return receiptno_max;
        }

        public void setReceiptno_max(String receiptno_max) {
            this.receiptno_max = receiptno_max;
        }

        public String getLogin_pk() {
            return login_pk;
        }

        public void setLogin_pk(String login_pk) {
            this.login_pk = login_pk;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIsadmin() {
            return isadmin;
        }

        public void setIsadmin(String isadmin) {
            this.isadmin = isadmin;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getRemit_user() {
            return remit_user;
        }

        public void setRemit_user(String remit_user) {
            this.remit_user = remit_user;
        }

        public String getPasscode() {
            return passcode;
        }

        public void setPasscode(String passcode) {
            this.passcode = passcode;
        }

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }
    }

    public class Settings_Details {
    String sno,company_name,city_name,phone_no,delete_cycle,limit_date;

        public Settings_Details(String sno, String company_name, String city_name, String phone_no, String delete_cycle, String limit_date) {
            this.sno = sno;
            this.company_name = company_name;
            this.city_name = city_name;
            this.phone_no = phone_no;
            this.delete_cycle = delete_cycle;
            this.limit_date = limit_date;
        }

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getDelete_cycle() {
            return delete_cycle;
        }

        public void setDelete_cycle(String delete_cycle) {
            this.delete_cycle = delete_cycle;
        }

        public String getLimit_date() {
            return limit_date;
        }

        public void setLimit_date(String limit_date) {
            this.limit_date = limit_date;
        }
    }

    public void sync_Login(String login_pk,String code,String agent, String isadmin, String branch_id, String remit_user, String passcode, String deviceid,String login_status,String receiptno_max ,String customerno_max,int i) {

        DataBaseAdapter objdatabaseadapter = null;
        objdatabaseadapter = new DataBaseAdapter(Login_Activity.this);
        objdatabaseadapter.open();
        try {
            //String success = objdatabaseadapter.insert("manikandan","1","1111","1","0","0","1","f22d1103dcce2f11","1111");
            int delete_success = objdatabaseadapter.delete();
            Log.d("DELETE STATUS  ===========>", String.valueOf(delete_success));
           if(delete_success == 1 || delete_success == 2) {
                Log.d("SYNC_LOGIN_TABLE_DATA CLEARED   ===========>", "success");
                String success = objdatabaseadapter.insert(login_pk,agent, code, "", isadmin, login_status, branch_id, remit_user, deviceid, passcode,receiptno_max,customerno_max);
                // String success = objdatabaseadapter.insert("null", "null", "null", "null", "null", "null", "null", "null", "null");
                if (success.equals("success")) {

                    // Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_SHORT);
                    Log.d("SYNCED_LOGIN_DB DATA   =====ROW(" + String.valueOf(i) + ")======>", success);
                }
             //  Log.d("SYNCED_LOGIN_DB FINE  ===========>", "test");
            }


        } catch (Exception e) {
            Log.d("SYNCED_LOGIN_DB ERROR   ===========>", "unable to insert");
        } finally {
            if (objdatabaseadapter != null)
                objdatabaseadapter.close();
        }
    }


    public void sync_Settings(String sno,String company_name,String city_name, String phone_no, String delete_cycle, String limit_date,String schedule_date,String temp_date,int i) {

        DataBaseAdapter objdatabaseadapter = null;
        objdatabaseadapter = new DataBaseAdapter(Login_Activity.this);
        objdatabaseadapter.open();
        try {
            //String success = objdatabaseadapter.insert("manikandan","1","1111","1","0","0","1","f22d1103dcce2f11","1111");
            int delete_success = objdatabaseadapter.delete_settings();
            Log.d("DELETE STATUS  ===========>", String.valueOf(delete_success));
            if(delete_success == 1 || delete_success == 2) {
                Log.d("SYNCED_SETTINGS_TABLE CLEARED   ===========>", "success");
                String success = objdatabaseadapter.insert_settings(sno,company_name, city_name, phone_no, delete_cycle, limit_date,schedule_date,temp_date);
                // String success = objdatabaseadapter.insert("null", "null", "null", "null", "null", "null", "null", "null", "null");
                if (success.equals("success")) {

                    // Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_SHORT);
                    Log.d("SYNCED_SETTINGS_TABLE DATA   =====ROW(" + String.valueOf(i) + ")======>", success);
                }
                //  Log.d("SYNCED_LOGIN_DB FINE  ===========>", "test");
            }


        } catch (Exception e) {
            Log.d("SYNCED_LOGIN_DB ERROR   ===========>", "unable to insert");
        } finally {
            if (objdatabaseadapter != null)
                objdatabaseadapter.close();
        }
    }


    public class Get_Limit_Date extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Login_Activity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select_Limit(10);
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (mCur.getCount() > 0) {
                date_list.clear();


                for (int i = 0; i < mCur.getCount(); i++) {
                    date_list.add(String.valueOf(mCur.getString(0)));
                    Log.d("SHIFT_TABLE_DATE_LIST ======ROW("+i+")=========>",mCur.getString(0));
                    mCur.moveToNext();
                }

                StringBuilder str = new StringBuilder("");
                str.append("'");
                // Traversing the ArrayList
                for (String eachstring : date_list) {
                    // Each element in ArrayList is appended
                    // followed by comma
                    str.append(eachstring).append("','");
                }

                // StringBuffer to String conversion
                commaseparatedlist = str.toString();

                // Condition check to remove the last comma
                if (commaseparatedlist.length() > 0) {
                    commaseparatedlist = commaseparatedlist.substring(0, commaseparatedlist.length() - 2);
                }
                // Printing the comma separated string
                // System.out.println(commaseparatedlist);
                Log.d("SHIFT_TABLE COMMA SEPERATED STRING===============>",String.valueOf(commaseparatedlist));
                if(!commaseparatedlist.equals("")){
                    new Async_Delete_Transaction().execute(commaseparatedlist);
                }
            } else {

                Log.d("SHIFT_TABLE_DATELIST ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Async_Delete_Transaction extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        String success;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Login_Activity.this);
            baseAdapter.open();
            success = baseAdapter.Delete_transaction(params[0]);
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(success.equals("success")) {
                Log.d("TRANSACTION TABLE DATA===============>", "DELETED");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void toast(String message)
    {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.check_internet, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
