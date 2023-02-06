package com.example.collectionreport;

import static com.example.collectionreport.MainActivity.actual_collection_tv;
import static com.example.collectionreport.MainActivity.expected_collection_tv;
import static com.example.collectionreport.MainActivity.model;
import static com.example.collectionreport.MainActivity.selectedDate;
import static com.example.collectionreport.MainActivity.settings_details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.softland.palmtecandro.palmtecandro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Report_Shift_Activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String line_space = "------------------------------";
    ImageView back_img;
    DecimalFormat dft = new DecimalFormat("0.00");
    String Shift_code,Shift_time,Shift_date;
    TextView tot_amount_tv,title_tv,txt_date,txt_time;
    ArrayList<report_shift_details> report_shift_details = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    Report_Shift_Adapter report_shift_adapter;
    ListView report_listview ;
    String selected_date;
    LinearLayout LL2000,LL500,LL200,LL100,LL50,LL20,LL10,LL5,LLCoins;
    TextView ct_2000_tv,ct_500_tv,ct_200_tv,ct_100_tv,ct_50_tv,ct_20_tv,ct_10_tv,ct_5_tv,ct_coins_tv;
    TextView total_store_tv,total_collection_tv,date_tv,tv_2000,tv_500,tv_200,tv_100,tv_50,tv_20,tv_10,tv_5,tv_coins;
    ScrollView cr_scrollview;
    boolean close_state = false;
    ProgressBar progress_bar ;
    double Total_amt=0,Bank_Total=0,Cash_Total=0;
    ArrayList<shift_details> shift_details = new ArrayList<>();
    FloatingActionButton consolidated_print;
    SimpleDateFormat md_time = new SimpleDateFormat("HH:mm:ss");
    Calendar calendar;
    String TIME ,CURRENT_DATE = "";

    ArrayList<String> stringArrayList = new ArrayList<>();
    LayoutInflater inflater = null;
    MemberListBaseAdapter memberListBaseAdapter ;

    ArrayList<Collection_report_details> collection_report_details = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_shift_activity);
        back_img = (ImageView) findViewById(R.id.back_img);

        date_tv = (TextView) findViewById(R.id.date_tv);

        total_store_tv  = (TextView) findViewById(R.id.total_store_tv);
        total_collection_tv = (TextView) findViewById(R.id.total_collection_tv);

        title_tv = (TextView) findViewById(R.id.title_tv);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_time = (TextView) findViewById(R.id.txt_time);
        tot_amount_tv =(TextView) findViewById(R.id.tot_amount);
        report_listview =(ListView)  findViewById(R.id.report_listview);
        cr_scrollview =(ScrollView)  findViewById(R.id.cr_scrollview);
        consolidated_print =(FloatingActionButton) findViewById(R.id.consolidated_print);


        LL2000 = (LinearLayout)  findViewById(R.id.LL2000);
        LL500 = (LinearLayout)  findViewById(R.id.LL500);
        LL200 = (LinearLayout)  findViewById(R.id.LL200);
        LL100 = (LinearLayout)  findViewById(R.id.LL100);
        LL50 = (LinearLayout)  findViewById(R.id.LL50);
        LL20 = (LinearLayout)  findViewById(R.id.LL20);
        LL10 = (LinearLayout)  findViewById(R.id.LL10);
        LL5  = (LinearLayout)   findViewById(R.id.LL5);
        LLCoins= (LinearLayout)  findViewById(R.id.LLCoins);

        ct_2000_tv = (TextView) findViewById(R.id.ct_2000_tv);
        ct_500_tv = (TextView) findViewById(R.id.ct_500_tv);
        ct_200_tv = (TextView) findViewById(R.id.ct_200_tv);
        ct_100_tv = (TextView) findViewById(R.id.ct_100_tv);
        ct_50_tv = (TextView) findViewById(R.id.ct_50_tv);
        ct_20_tv = (TextView) findViewById(R.id.ct_20_tv);
        ct_10_tv = (TextView) findViewById(R.id.ct_10_tv);
        ct_5_tv = (TextView) findViewById(R.id.ct_5_tv);
        ct_coins_tv = (TextView) findViewById(R.id.ct_coins_tv);

        tv_2000  = (TextView) findViewById(R.id.tv_2000);
        tv_500  = (TextView) findViewById(R.id.tv_500);
        tv_200  = (TextView) findViewById(R.id.tv_200);
        tv_100  = (TextView) findViewById(R.id.tv_100);
        tv_50  = (TextView) findViewById(R.id.tv_50);
        tv_20  = (TextView) findViewById(R.id.tv_20);
        tv_10  = (TextView) findViewById(R.id.tv_10);
        tv_5  = (TextView) findViewById(R.id.tv_5);
        tv_coins  = (TextView) findViewById(R.id.tv_coins);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report_Shift_Activity.this.finish();
            }
        });

        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        stringArrayList.add("4");
        stringArrayList.add("5");
        stringArrayList.add("6");
        stringArrayList.add("7");
        stringArrayList.add("8");
        stringArrayList.add("9");
        stringArrayList.add("10");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        CURRENT_DATE = mdformat.format(calendar.getTime());

        date_tv.setText(strDate);


        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog();
            }
        });

        progressDialog = new ProgressDialog(Report_Shift_Activity.this);
        progressDialog.setMessage("Printing..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        try{
            Intent i = getIntent();
            Shift_code = i.getStringExtra("shiftcode");
            Shift_time = i.getStringExtra("shifttime");
            Shift_date = i.getStringExtra("shiftdate");

            title_tv.setText(String.valueOf("Shift "+ Shift_code.charAt(8)));
            txt_date.setText(String.valueOf(Shift_date));
            if(!Shift_time.equals("")) {
                txt_time.setText(String.valueOf(Shift_time));
            }else{
                txt_time.setText(String.valueOf("Active"));
                txt_time.setTextColor(getResources().getColor(R.color.green));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //new Load_Shift_Records().execute();

       // Toast.makeText(Report_Shift_Activity.this,String.valueOf(Shift_code),Toast.LENGTH_SHORT).show();

        consolidated_print.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                try {
                    if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                     //   Print_Consolidated_Receipt();
                        Print_Receipt_Market("","","","","","","","",1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        new Async_MonthlyCollection().execute();
       // new Get_Shift_Details().execute();
    }


    public class Async_MonthlyCollection extends
            AsyncTask<String, JSONObject, Boolean> {
        JSONObject jsonObj;
        JSONArray jsonArray ;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                //  if (networkstate) {

                //REQUEST 1 is used to get Daily Collection Details

                jsonObj = objRestAPI.GetReportList(date_tv.getText().toString());
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

        @Override
        protected void onPostExecute(Boolean result) {

            if (jsonObj == null || jsonObj.equals("")) {
                // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                toast("Please check your internet connection");
            } else {
                try {
                    int success = jsonObj.getInt("success");

                    if (success == 0) {
                        collection_report_details.clear();
                        report_listview.setAdapter(null);
                        total_store_tv.setText(String.valueOf(""));
                        total_collection_tv.setText(String.valueOf(" "));

                    } else if (success == 1) {
                        collection_report_details.clear();
                        jsonArray = jsonObj.getJSONArray("reportdetails");

                        if (jsonArray.length() > 0) {
                            collection_report_details.clear();
                            total_store_tv.setText(String.valueOf(jsonArray.length()));
                            String status;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);

                                int due_amount =0;
                                int total_amount = 0;
//                                 due_amount = Integer.parseInt(json.getString("due_amount"));
//                                 total_amount = Integer.parseInt(json.getString("total_amount"));

                                //json.getString("olddue_amount") OLD
                                collection_report_details.add(new Collection_report_details(json.getString("receiptno"),"",json.getString("shopname"),"",json.getString("tenantname"),
                                                               json.getString("totalamount"),json.getString("rentcycle"),false));

                                Log.d(" collection_report_details ===========>", "====================>"+String.valueOf(total_amount-due_amount)+json.toString());
                            }

//                            if(collection_report_details.size()>0) {
//
//                                memberListBaseAdapter = new MemberListBaseAdapter(getApplicationContext(), collection_report_details);
//                                report_listview.setAdapter(memberListBaseAdapter);
//                                memberListBaseAdapter.notifyDataSetChanged();
//                                calculateGrandAmount();
//                            }
                        }else{

                        }
                        memberListBaseAdapter = new MemberListBaseAdapter(getApplicationContext(), collection_report_details);
                        report_listview.setAdapter(memberListBaseAdapter);
                        memberListBaseAdapter.notifyDataSetChanged();
                        calculateGrandAmount();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public void calculateGrandAmount(){

        final DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");
        double totalamt=0,paid_amt = 0;

        for(int i=0;i<collection_report_details.size();i++){
            Double amt = Double.parseDouble(collection_report_details.get(i).getTotal_amount());
            totalamt = totalamt + amt;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            total_collection_tv.setText(String.valueOf(" \u20B9 "+dft.format(totalamt)));
        }

    }


    public class Load_Shift_Records extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Report_Shift_Activity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select_Transact_Cutomer(Shift_code,"null");
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progress_bar.getVisibility()== View.GONE){
                progress_bar.setVisibility(View.VISIBLE);
            }
            report_shift_details.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(progress_bar.getVisibility()== View.VISIBLE){
                progress_bar.setVisibility(View.GONE);
            }

            if (mCur.getCount() > 0) {
               // shift_details.clear();
                String status;

                for (int i = 0; i < mCur.getCount(); i++) {
                    status ="";
                  // char s_name = mCur.getString(0).charAt(8);
                   //shift_details.add(new Report_Activity.shift_details(String.valueOf(s_name),mCur.getString(0),mCur.getString(1),mCur.getString(2)));
                    report_shift_details.add(new report_shift_details(String.valueOf(i),mCur.getString(0),mCur.getString(1),mCur.getString(2),
                                                                       mCur.getString(3),mCur.getString(4),mCur.getString(5),mCur.getString(6),mCur.getString(7),mCur.getInt(8),mCur.getInt(10),mCur.getString(11),mCur.getString(9),mCur.getString(13),mCur.getString(14),mCur.getString(15),mCur.getString(16),mCur.getString(17),mCur.getString(18),mCur.getString(19)));
                    Log.d("TRANSACTION_TABLE_RECORD ======ROW("+i+")=========>",mCur.getString(6));
                    mCur.moveToNext();
                }
            } else {

                Log.d("TRANSACTION_TABLE_RECORD ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
            if(report_shift_details !=null) {
                arrayList.clear();
                for(int i=0;i<report_shift_details.size();i++){
                        if(report_shift_details.get(i).getClose_status() == 1) {
                            arrayList.add(String.valueOf(i));
                        }
                  }
                       if(arrayList != null){

                            if(report_shift_details.size() == arrayList.size()) {
                                if(consolidated_print.getVisibility() == View.GONE) {
                                    consolidated_print.setVisibility(View.VISIBLE);
                                    close_state =true;
                                }
                             }
                       }
                       if(report_shift_details != null){
                           for(int i=0;i<report_shift_details.size();i++){
                               Log.i("REPORT DETAILS=========>", report_shift_details.get(i).toString());
                           }
                       }
                report_shift_adapter = new Report_Shift_Adapter(Report_Shift_Activity.this, report_shift_details);
                report_listview.setAdapter(report_shift_adapter);
                report_shift_adapter.notifyDataSetChanged();
                calculateAreaWiseTotal();
            }
        }

    }

    public void openDatePickerDialog() {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    String day="";
                    if(dayOfMonth < 10){
                        day = "0"+String.valueOf((dayOfMonth));
                    }else {
                        day = String.valueOf((dayOfMonth));
                    }
                    String monthvalue="";
                    if(monthOfYear < 10){
                        monthvalue = "0"+String.valueOf((monthOfYear+1));
                    }else {
                        monthvalue = String.valueOf(monthOfYear+1);
                    }

                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    String selectedDate = day + "-" + monthvalue + "-" + String.valueOf(year);

                    date_tv.setText(selectedDate);


                    ListData();

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private void ListData() {

        new Async_MonthlyCollection().execute();
    }

    public class Load_Shift_Data extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Report_Shift_Activity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select_Transact_Cutomer(Shift_code,selected_date);
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progress_bar.getVisibility()== View.GONE){
                progress_bar.setVisibility(View.VISIBLE);
            }
            report_shift_details.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(progress_bar.getVisibility()== View.VISIBLE){
                progress_bar.setVisibility(View.GONE);
            }

            if (mCur.getCount() > 0) {
                // shift_details.clear();
                String status;

                for (int i = 0; i < mCur.getCount(); i++) {
                    status ="";
                    // char s_name = mCur.getString(0).charAt(8);
                    //shift_details.add(new Report_Activity.shift_details(String.valueOf(s_name),mCur.getString(0),mCur.getString(1),mCur.getString(2)));
                    report_shift_details.add(new report_shift_details(String.valueOf(i),mCur.getString(0),mCur.getString(1),mCur.getString(2),
                            mCur.getString(3),mCur.getString(4),mCur.getString(5),mCur.getString(6),mCur.getString(7),mCur.getInt(8),mCur.getInt(10),mCur.getString(11),mCur.getString(9),mCur.getString(13),mCur.getString(14),mCur.getString(15),mCur.getString(16),mCur.getString(17),mCur.getString(18),mCur.getString(19)));
                    Log.d("TRANSACTION_TABLE_RECORD ======ROW("+i+")=========>",mCur.getString(6));
                    mCur.moveToNext();
                }
            } else {

                Log.d("TRANSACTION_TABLE_RECORD ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
            if(report_shift_details !=null) {
                arrayList.clear();
                for(int i=0;i<report_shift_details.size();i++){
                    if(report_shift_details.get(i).getClose_status() == 1) {
                        arrayList.add(String.valueOf(i));
                    }
                }
                if(arrayList != null){

                    if(report_shift_details.size() == arrayList.size()) {
                        if(consolidated_print.getVisibility() == View.GONE) {
                            consolidated_print.setVisibility(View.VISIBLE);
                            close_state =true;
                        }
                    }
                }
                if(report_shift_details != null){
                    for(int i=0;i<report_shift_details.size();i++){
                        Log.i("REPORT DETAILS=========>", report_shift_details.get(i).toString());
                    }
                }
                report_shift_adapter = new Report_Shift_Adapter(Report_Shift_Activity.this, report_shift_details);
                report_listview.setAdapter(report_shift_adapter);
                report_shift_adapter.notifyDataSetChanged();
                calculateAreaWiseTotal();
            }
        }

    }


    public class Report_Shift_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<report_shift_details> report_shift_details;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Report_Shift_Adapter(Context context,ArrayList<report_shift_details> report_shift_details){
            this.context=context;
            this.report_shift_details =report_shift_details;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return report_shift_details.size();
        }

        @Override
        public Object getItem(int position) {
            return report_shift_details.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            if(getCount() > 0){
                return getCount();
            }else{
                return super.getViewTypeCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.report_customer_card, viewGroup, false);
                viewHolder = new ViewHolder();

                viewHolder.sub_print =(ImageView) view.findViewById(R.id.sub_print);
                viewHolder.main_layout = (LinearLayout) view.findViewById(R.id.main_layout);

                viewHolder.txt_sno =(TextView) view.findViewById(R.id.txt_sno);
                viewHolder.txt_customer_name =(TextView) view.findViewById(R.id.customer_name);
                viewHolder.txt_accno = (TextView) view.findViewById(R.id.acc_no);
                viewHolder.txt_amount = (TextView) view.findViewById(R.id.txt_amount);

                viewHolder.receipt_no = (TextView) view.findViewById(R.id.receipt_no);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            try {
                if(report_shift_details.get(position).getCancel().equals("N")) {
                    if (viewHolder.sub_print.getVisibility() == View.GONE) {
                        viewHolder.sub_print.setVisibility(View.VISIBLE);
                    }
                }
                viewHolder.txt_sno.setText(String.valueOf(position+1));
                viewHolder.receipt_no.setText(String.valueOf("Receipt No : "+report_shift_details.get(position).getReceipt_code()));

               if (!report_shift_details.get(position).getCustomer_name().equals("null") && !report_shift_details.get(position).getCity_name().equals("null")) {
                    viewHolder.txt_customer_name.setText(String.valueOf(report_shift_details.get(position).getCustomer_name()+",  "+report_shift_details.get(position).getCity_name()));
                }else if(!report_shift_details.get(position).getCustomer_name().equals("null")) {
                    viewHolder.txt_customer_name.setText(String.valueOf(report_shift_details.get(position).getCustomer_name()));
                }
                else {
                    if(report_shift_details.get(position).getCustomer_type() == 2){
                        viewHolder.txt_customer_name.setText(String.valueOf("Unknown Customer"));
                    }
                    if(report_shift_details.get(position).getCustomer_type() == 3) {
                        viewHolder.txt_customer_name.setText(String.valueOf("New Customer"));
                    }
                }
                if(!report_shift_details.get(position).getAcno().equals("")) {
                    viewHolder.txt_accno.setText(String.valueOf("A/C #" + report_shift_details.get(position).getAcno()));
                }else{
                    viewHolder.txt_accno.setText(String.valueOf(" - "));
                }
                if(!report_shift_details.get(position).getCollectamt().equals("null")) {
                    viewHolder.txt_amount.setText(String.valueOf("₹ " + report_shift_details.get(position).getCollectamt()));
                }else{
                    viewHolder.txt_amount.setText(String.valueOf("₹ 0.00" ));
                }
                if(report_shift_details.get(position).getCancel().equals("Y")) {
                    viewHolder.txt_amount.setTextColor(getResources().getColor(R.color.red));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            viewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                   /* if(!close_state) {
                        if (report_shift_details.get(position).getCollectdt().equals(MainActivity.strDate) && report_shift_details.get(position).getCancel().equals("N")) {
                           // cancel_payment(position);
                        } else {

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Shift closed",Toast.LENGTH_SHORT).show();
                    }*/
                }
            });
            viewHolder.sub_print.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    try {
                        if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                            Print_Receipt(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Print_Consolidated_Receipt();

                }
            });

            return view;
        }

    }

    public class Get_Company_Details extends
            AsyncTask<String, JSONObject, Boolean> {
        DataBaseAdapter base;
        PendingFragment.Pending_Adapter adapter;
        Cursor mcur;
        int pos;

        public Get_Company_Details(int position) {
            this.pos = position;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {
            base = new DataBaseAdapter(Report_Shift_Activity.this);
            base.open();
            mcur = base.Select_Settings();
            base.close();
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            settings_details.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(mcur != null){
                if(mcur.getCount()>0){
                    settings_details.clear();
                    for(int i = 0; i< mcur.getCount(); i++) {
                        //settings_details.add(new Settings_Details(mcur.getString(1), mcur.getString(2), mcur.getString(3), mcur.getString(4), mcur.getString(5), mcur.getString(6)));
                      //  Log.d("SETTINGS_DATA =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(mcur.getString(2)));
                      //  mcur.moveToNext();
                    }
                }
            }

            if(settings_details.size()>0) {
               // Print_Receipt(pos);
            }else{

            }
        }
    }



    public class MemberListBaseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Collection_report_details> myList;
        DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");

        public MemberListBaseAdapter(Context context, ArrayList<Collection_report_details> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);

            //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Collection_report_details getItem(int position) {
            return  myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            if (getCount() > 0) {
                return getCount();
            } else {
                return super.getViewTypeCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder mHolder;

            View view = null;
            if (convertView == null) {

                view = inflater.inflate(R.layout.collection_report_card, parent, false);
                final  ViewHolder holder = new ViewHolder();
                try {
                    //     holder.total_amount_tv = (EditText) view.findViewById(R.id.total_amount_tv);
                    holder.LL_card = (LinearLayout) view.findViewById(R.id.LL_card);
                    holder.card_view = (CardView) view.findViewById(R.id.card_view);

                    holder.shop_name_tv = (TextView) view.findViewById(R.id.shop_name_tv);
                    holder.tenant_name_tv = (TextView) view.findViewById(R.id.tenant_name_tv);
                    holder.receiptno_tv = (TextView) view.findViewById(R.id.receiptno_tv);
                    holder.amount_tv = (TextView) view.findViewById(R.id.amount_tv);
                    holder.print_icon = (ImageView) view.findViewById(R.id.print_icon);

                } catch (Exception e) {

                    Log.i("Route", e.toString());

                }


                view.setTag(holder);

                holder.shop_name_tv.setText(String.valueOf(" "+collection_report_details.get(position).getShopname()));
                holder.tenant_name_tv.setText(String.valueOf(" "+collection_report_details.get(position).getTenantname()));
                holder.receiptno_tv.setText(String.valueOf("# "+collection_report_details.get(position).getReceiptno()));
                holder.amount_tv.setText(String.valueOf(collection_report_details.get(position).getTotal_amount()));


                holder.print_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Print_Receipt_Market(collection_report_details.get(position).getTotal_amount(),collection_report_details.get(position).getTenantname(),collection_report_details.get(position).getReceiptno(),collection_report_details.get(position).getShopname(),"","","","",0);
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });


            } else {
                // mHolder = (ViewHolder) convertView.getTag();
                view = convertView;
                // ((ViewHolder) view.getTag()).total_amount_tv.setTag(membersDetails.get(position));
            }


//            ViewHolder holder_ = (ViewHolder) view.getTag();
//
//            holder_.total_amount_tv.setTag(getItem(position));

            return view;
        }
    }



    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow,print_icon;
        Button button;
        CardView card_view,Show_more,Track;
        TextView shop_name_tv,tenant_name_tv,receiptno_tv,amount_tv,total_customer_tv;
        TextView customer_name,area_name,acc_no,mob_no;
        LinearLayout LL_card;

        public CardView report_card;
        ImageView sub_print;
        LinearLayout main_layout;
        TextView receipt_no,txt_sno,txt_customer_name,txt_accno,txt_amount;

    }


    private void Print_Receipt_Market(String total_amount,String tenantname,String receiptcode,String shopname,String customer_name,String city,String Old_due_amount,String Due_amount,int customer_type) {
        double  old_due_amount=500,due_amount=1000;
        double total_amt= 0,grand_total = 0;
        String tot_amt_words = "";
        String agent_code,grp_code;

        for (int i=0;i<collection_report_details.size();i++) {
            grand_total = grand_total + Double.parseDouble(collection_report_details.get(i).getTotal_amount());
        }
        palmtecandro.jnidevOpen(115200);

        if(customer_type == 0) {

                Clearbuffer();//Clear unwanted buffer
                //Leavespace();
                Alignment(27,97,1);//Center aligment
                Print("");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    Clearbuffer();//Clear unwanted buffer
                    // Leavespace();
                    Alignment(27, 97, 1);//Center aligment
                    // Print("Thirupuvanam");
                    Print(MainActivity.CITY_NAME);

                    Clearbuffer();//Clear unwanted buffer
                    //Leavespace();
                    Alignment(27, 97, 1);//Center aligment
                    //Print(" KAS JEWELLERY");
                    Print(MainActivity.COMPANY_NAME);

                    Clearbuffer();//Clear unwanted buffer
                    // Leavespace();
                    Alignment(27, 97, 1);//Center aligment
                    // Print("Thirupuvanam");
                    Print(MainActivity.SUBTITLE_1);

                    Clearbuffer();//Clear unwanted buffer
                    // Leavespace();
                    Alignment(27, 97, 1);//Center aligment
                    // Print("Thirupuvanam");
                    Print(MainActivity.SUBTITLE_2);

                }
                Clearbuffer();//Clear unwanted buffer
                Alignment(27,97,1);//Center aligment
                Print(line_space);

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27,97,1);
                Print("RENT RECEIPT");

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);


                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 0);//Left alignment
                // Print("DATE: " + date + " TIME: " + getReminingTime());
                Print("DATE: " + MainActivity.strDate + " TIME: " + getReminingTime());

                Clearbuffer();//Clear unwanted buffer
                //  Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("RECEIPT NO:" + String.valueOf(receiptcode+" SHOP NO:" + shopname));

                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("TENANT NAME: " + tenantname );

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27,97,2);//Right alignment
                Print("AMOUNT: " + total_amount);
                Clearbuffer();//Clear unwanted bufferPrint("");
                // Leavespace();
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);
                    Clearbuffer();//Clear unwanted buffer
                    // Leavespace();
                    Alignment(27, 97, 1);//Center aligment
                    // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
                    Print("(INCLUSIVE OF SERVICE TAX)");
                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27,97,0);//Left alignment
                Print(" * * * * * THANK YOU * * * * * ");

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,0);//Left alignment
            Print("");
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,0);//Left alignment
            Print("");


        }

//
//        if(customer_type == 1) {
//
//            for (int i=0;i<collection_report_details.size();i++) {
//
//                Clearbuffer();//Clear unwanted buffer
//                //Leavespace();
//                Alignment(27,97,1);//Center aligment
//                Print("");
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//                    Clearbuffer();//Clear unwanted buffer
//                    // Leavespace();
//                    Alignment(27, 97, 1);//Center aligment
//                    // Print("Thirupuvanam");
//                    Print(MainActivity.CITY_NAME);
//
//                    Clearbuffer();//Clear unwanted buffer
//                    //Leavespace();
//                    Alignment(27, 97, 1);//Center aligment
//                    //Print(" KAS JEWELLERY");
//                    Print(MainActivity.COMPANY_NAME);
//
//                    Clearbuffer();//Clear unwanted buffer
//                    // Leavespace();
//                    Alignment(27, 97, 1);//Center aligment
//                    // Print("Thirupuvanam");
//                    Print(MainActivity.SUBTITLE_1);
//
//                    Clearbuffer();//Clear unwanted buffer
//                    // Leavespace();
//                    Alignment(27, 97, 1);//Center aligment
//                    // Print("Thirupuvanam");
//                    Print(MainActivity.SUBTITLE_2);
//
//                }
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27,97,1);//Center aligment
//                Print(line_space);
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27,97,1);
//                Print("RENT RECEIPT");
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                Print(line_space);
//
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                // Print("DATE: " + date + " TIME: " + getReminingTime());
//                Print("DATE: " + MainActivity.strDate + " TIME: " + getReminingTime());
//
////                Clearbuffer();//Clear unwanted buffer
////                Alignment(27, 97, 0);//Left alignment
////                Print("");
//
//                Clearbuffer();//Clear unwanted buffer
//              //  Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("RECEIPT NO:" + String.valueOf(collection_report_details.get(i).getReceiptno()+" SHOP NO:" + collection_report_details.get(i).getShopname()));
//
//                Clearbuffer();//Clear unwanted buffer
//                //   Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("TENANT NAME: " + collection_report_details.get(i).getTenantname());
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27,97,2);//Right alignment
//                Print("TOTAL AMOUNT: " + collection_report_details.get(i).getTotal_amount());
//                Clearbuffer();//Clear unwanted bufferPrint("");
//               // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                Print(line_space);
////
////                    Clearbuffer();//Clear unwanted buffer
////                    // Leavespace();
////                    Alignment(27, 97, 1);//Center aligment
////                    // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
////                    Print("(INCLUSIVE OF SERVICE TAX)");
////                    // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
//
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27,97,0);//Left alignment
//                Print(" * * * * * THANK YOU * * * * * ");
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Print("");
//                Clearbuffer();//Clear unwanted buffer
//                Print("");
//            }
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27, 97, 0);//Left alignment
//            Print("OVERALL TOTAL : "+String.valueOf(dft.format(grand_total)));
//
//
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27,97,0);//Left alignment
//            Print("");
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27,97,0);//Left alignment
//            Print("");
//
//        }


        if(customer_type == 1) {

            Clearbuffer();//Clear unwanted buffer
            //Leavespace();
            Alignment(27,97,1);//Center aligment
            Print("");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 1);//Center aligment
                // Print("Thirupuvanam");
                Print(MainActivity.CITY_NAME);

                Clearbuffer();//Clear unwanted buffer
                //Leavespace();
                Alignment(27, 97, 1);//Center aligment
                //Print(" KAS JEWELLERY");
                Print(MainActivity.COMPANY_NAME);

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 1);//Center aligment
                // Print("Thirupuvanam");
                Print(MainActivity.SUBTITLE_1);

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 1);//Center aligment
                // Print("Thirupuvanam");
                Print(MainActivity.SUBTITLE_2);

            }
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,1);//Center aligment
            Print(line_space);

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27,97,1);
            Print("COLLECTION REPORT");

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(date_tv.getText().toString());

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,1);
            Print("REC.NO      SHOP.NO       AMT");

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(line_space);

            for (int i=0;i<collection_report_details.size();i++) {


                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print( String.valueOf(collection_report_details.get(i).getReceiptno()+"   " + collection_report_details.get(i).getShopname()+" "+collection_report_details.get(i).getTenantname()));

                Clearbuffer();//Clear unwanted buffer
               // Leavespace();
                Alignment(27,97,2);//Right alignment
                Print("" + collection_report_details.get(i).getTotal_amount());

            }
            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(line_space);

            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,2);//Right alignment
            Print("TOTAL AMOUNT: "+String.valueOf(dft.format(grand_total)));

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Print("");
            Clearbuffer();//Clear unwanted buffer
            Print("");
            Clearbuffer();//Clear unwanted buffer
            Print("");

        }

//        Clearbuffer();//Clear unwanted buffer
//        Leavespace();
//        Alignment(27,97,1);//Center aligment
//        Print(" * * * * * THANK YOU * * * * * ");
//
//        Clearbuffer();//Clear unwanted buffer
//        Leavespace();
//        Print("");
//        Clearbuffer();//Clear unwanted buffer
//        Print("");
//        Clearbuffer();//Clear unwanted buffer
//        Print("");

        palmtecandro.jnidevClose();
    }


    private String getReminingTime() {
        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Print_Receipt(int pos) {

        //progressDialog.show(); // Display Progress Dialog

        double  prvs_amount=0,paid_amount=0;
        double total_amt=0;
        String tot_amt_words = "";
        String date ="",time="",receipt_no="",cust_id="",grp_code="",cust_name="",phone_no="",city="",agent_code="",remark="";
        date = report_shift_details.get(pos).getCollectdt();
        time = report_shift_details.get(pos).getTr_time();
       // receipt_no = String.valueOf(report_shift_details.get(pos).getReceipt_code() +" "+report_shift_details.get(pos).getCollectdt());
        receipt_no = String.valueOf(report_shift_details.get(pos).getReceipt_code());
        cust_id = report_shift_details.get(pos).getAcno();
        grp_code = report_shift_details.get(pos).getActype();
        cust_name = report_shift_details.get(pos).getCustomer_name();
        phone_no = report_shift_details.get(pos).getPhone_no();
        city = report_shift_details.get(pos).getCity_name();
        remark = report_shift_details.get(pos).getRemark();

        agent_code = MainActivity.AGENTCODE;

        try {
            if(report_shift_details.get(pos).getCustomer_type() ==1) {
                if (!report_shift_details.get(pos).getPrvs_amount().equals("")) {
                    prvs_amount = Double.parseDouble(report_shift_details.get(pos).getPrvs_amount());
                }
                if (!report_shift_details.get(pos).getCollectamt().equals("")) {
                    paid_amount = Double.parseDouble(report_shift_details.get(pos).getCollectamt());
                }
                total_amt = prvs_amount + paid_amount;
            }

            if(report_shift_details.get(pos).getCustomer_type() ==2 || report_shift_details.get(pos).getCustomer_type() ==3) {
                total_amt  = Double.parseDouble(report_shift_details.get(pos).getCollectamt());
            }

            tot_amt_words = String.valueOf(convertIntoWords(total_amt,"en","US"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // if(!date.equals("") && !time.equals("") && !cust_id.equals("") && ) {
        palmtecandro.jnidevOpen(115200);



        if(settings_details.size()>0) {
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,1);//Center aligment
            Print("");
            Clearbuffer();//Clear unwanted buffer
            //Leavespace();
            Alignment(27,97,1);//Center aligment
            //Print(" KAS JEWELLERY");
            Print( " "+settings_details.get(0).getCompany_name());

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            // Print("Thirupuvanam");
            Print(settings_details.get(0).getCity_name());

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            // Print("7530024247");
            Print(settings_details.get(0).getPhone_no());
            // Print(line_space);
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
        }
        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,1);//Center aligment
        Print(line_space);

        Clearbuffer();//Clear unwanted buffer
       // Leavespace();
        Alignment(27,97,1);
        Print("COLLECTION RECEIPT");

        Clearbuffer();//Clear unwanted buffer
       // Leavespace();
        Alignment(27, 97, 1);//Center aligment
        Print(line_space);

            if (!date.equals("") && !time.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("DATE : " + date + "  TIME : " + time.substring(0, 5));
            }

            // Print(" ");
            //  Alignment(27,97,2);//Right alignment
            // Print(" TIME : 09:30");
            if (!receipt_no.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                //  Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("RECEIPT NO   : " + receipt_no);
            }

            if(report_shift_details.get(pos).getCustomer_type() ==1) {

            if (!cust_id.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER ID  : " + cust_id);
            }
            if (!grp_code.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("GRP CODE     : " + grp_code);

            }

            if (!cust_name.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER NAME: " + cust_name);
            }
            if (!phone_no.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("PHONE NO     : " + phone_no);
            }
            if (!city.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                //  Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CITY         : " + city);
            }

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(line_space);

            if (prvs_amount != 0) {
                Clearbuffer();//Clear unwanted buffer
              //  Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("PRVS.AMOUNT  : " + dft.format(prvs_amount));
            }
            if (paid_amount != 0) {
                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print("PAID.AMOUNT  : " + dft.format(paid_amount));

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 1);//Center alignment
                Print("( "+String.valueOf(tot_amt_words.charAt(0)).toUpperCase()+String.valueOf(tot_amt_words.substring(1,tot_amt_words.length()))+" only)");

            }
                Clearbuffer();//Clear unwanted bufferPrint("");
                //Leavespace();
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);

            if (total_amt != 0) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("TOTAL        : " + dft.format(total_amt));
            }

                 Clearbuffer();//Clear unwanted buffer
               //  Leavespace();
                 Alignment(27, 97, 1);//Center aligment
                 Print(line_space);

        }


        if(report_shift_details.get(pos).getCustomer_type() ==2 ){
            Clearbuffer();//Clear unwanted buffer
            Print("");
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,1);//Center aligment
            Print(line_space);
            if(total_amt != 0) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("TOTAL        : "+dft.format(total_amt));
            }

            if(!remark.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("UNKNOWN CUSTOMER  : "+remark);

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 1);//Center alignment
                Print("( "+String.valueOf(tot_amt_words.charAt(0)).toUpperCase()+String.valueOf(tot_amt_words.substring(1,tot_amt_words.length()))+" only)");

            }


            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27,97,1);//Center aligment
            Print(line_space);

        }
//        if(report_shift_details.get(pos).getCustomer_type() ==2 || report_shift_details.get(pos).getCustomer_type() ==3){
//            Clearbuffer();//Clear unwanted buffer
//            Print("");
//            Clearbuffer();//Clear unwanted buffer
//            Alignment(27,97,1);//Center aligment
//            Print(line_space);
//            if(total_amt != 0) {
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("TOTAL        : "+dft.format(total_amt));
//            }
//
//            if(!remark.equals("")) {
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("UNKNOWN CUSTOMER  : "+remark);
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27, 97, 1);//Center alignment
//                Print("( "+String.valueOf(tot_amt_words.charAt(0)).toUpperCase()+String.valueOf(tot_amt_words.substring(1,tot_amt_words.length()))+" only)");
//
//            }
//
//            Clearbuffer();//Clear unwanted buffer
//            //  Leavespace();
//            Alignment(27,97,1);//Center aligment
//            Print(line_space);
//
//        }

        if( report_shift_details.get(pos).getCustomer_type() ==3){
            if(!report_shift_details.get(pos).getName().equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER NAME: "+report_shift_details.get(pos).getName());
            }

            if(!report_shift_details.get(pos).getNew_mob_no().equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("PHONE NO     : "+report_shift_details.get(pos).getNew_mob_no());
            }

            if(!report_shift_details.get(pos).getCity().equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CITY         : "+report_shift_details.get(pos).getCity());
            }

            if(!report_shift_details.get(pos).getRemark().equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("NEW CUSTOMER : "+report_shift_details.get(pos).getRemark());
            }

            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,1);//Center aligment
            Print(line_space);

            if(total_amt != 0) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("TOTAL        : "+dft.format(total_amt));

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 1);//Center alignment
                Print("("+String.valueOf(tot_amt_words.charAt(0)).toUpperCase()+String.valueOf(tot_amt_words.substring(1,tot_amt_words.length()))+" only)");
            }
            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27,97,1);//Center aligment
            Print(line_space);

        }

        if(!agent_code.equals("")) {
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("AGENT NAME   : "+agent_code);
        }

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,1);//Center aligment
        Print(" * * * * * THANK YOU * * * * * ");

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Print("");
        Clearbuffer();//Clear unwanted buffer
        Print("");
        Clearbuffer();//Clear unwanted buffer
        Print("");

        palmtecandro.jnidevClose();

       // progressDialog.dismiss();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Print_Consolidated_Receipt() {
        //progressDialog.show();

        calendar = Calendar.getInstance();
        TIME = md_time.format(calendar.getTime());

        String device_id ="";
        try{
            device_id = MainActivity.DEVICE_ID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        palmtecandro.jnidevOpen(115200);

        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,1);//Center aligment
        PrintBold("");
        if(settings_details.size()>0) {
            Clearbuffer();//Clear unwanted buffer
            Alignment(27, 97, 1);//Center aligment
           // PrintBold("KAS JEWELLERY");
            Print(" "+settings_details.get(0).getCompany_name());
            Clearbuffer();//Clear unwanted buffer
            Alignment(27, 97, 1);//Center aligment
            //Print("Thirupuvanam");
            Print(settings_details.get(0).getCity_name());
            Clearbuffer();//Clear unwanted buffer
            Alignment(27, 97, 1);//Center aligment
          //  Print("7530024247");
            Print(settings_details.get(0).getPhone_no());
            // Print(line_space);
        }
        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,1);//Center aligment
        Print(line_space);


        Clearbuffer();//Clear unwanted buffer
      //  Leavespace();
        Alignment(27,97,1);//Center aligment
        Print("DATE WISE ");
        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,1);//Center aligment
        Print("COLLECTION REPORT");

        Clearbuffer();//Clear unwanted buffer
      //  Leavespace();
        Alignment(27,97,1);//Center aligment
        Print(line_space);

        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,0);//Left alignment
        Print("DATE : "+MainActivity.strDate+"  TIME : "+TIME.substring(0, 5));

        // Print(" ");
        //  Alignment(27,97,2);//Right alignment
        // Print(" TIME : 09:30");
        if(!device_id.equals("")) {
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("DEVICE ID  : "+device_id);
        }


            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("COLL.ID   : " +MainActivity.AGENTCODE);

        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,1);//Center aligment
        Print(line_space);


        for(int i=0;i<report_shift_details.size();i++) {


            if(report_shift_details.get(i).getCustomer_type() == 1) {
//OLD
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27, 97, 0);//Left alignment
//                Print("RECEIPT NO     :"+report_shift_details.get(i).getReceipt_code());
//
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27, 97, 0);//Left alignment
//                Print("CUS.ID-GRP.CODE:" + report_shift_details.get(i).getAcno() + "-" + report_shift_details.get(i).getActype());
//
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27, 97, 0);//Left alignment
//                Print("CUSTOMER NAME  :" + report_shift_details.get(i).getCustomer_name());
//
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27,97,2);//Right alignment
//                Print("AMT : "+dft.format(Double.parseDouble(report_shift_details.get(i).getCollectamt())));
//
//                Clearbuffer();//Clear unwanted buffer
//                Alignment(27, 97, 1);//Center aligment
//                Print(line_space);
                //NEW

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print("RECEIPT NO :"+report_shift_details.get(i).getReceipt_code()+"     Rs "+dft.format(Double.parseDouble(report_shift_details.get(i).getCollectamt())));

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print(report_shift_details.get(i).getAcno() + "-" + report_shift_details.get(i).getActype());

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
               // Print(report_shift_details.get(i).getCustomer_name()+"          "+dft.format(Double.parseDouble(report_shift_details.get(i).getCollectamt())));
                Print(report_shift_details.get(i).getCustomer_name());


                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);
            }
            if(report_shift_details.get(i).getCustomer_type() == 2) {
                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                //OLD
               // Print("RECEIPT NO    :"+report_shift_details.get(i).getReceipt_code()+ " "+ report_shift_details.get(i).getCollectdt());
                //Print("RECEIPT NO        :"+report_shift_details.get(i).getReceipt_code());
                Print("RECEIPT NO :"+report_shift_details.get(i).getReceipt_code()+"     Rs "+dft.format(Double.parseDouble(report_shift_details.get(i).getCollectamt())));

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print("UNKNOWN CUSTOMER   ");

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print(report_shift_details.get(i).getRemark());

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);
            }

            if( report_shift_details.get(i).getCustomer_type() == 3) {
                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                //OLD
               // Print("RECEIPT NO    :"+report_shift_details.get(i).getReceipt_code()+ " "+ report_shift_details.get(i).getCollectdt());
                //Print("RECEIPT NO        :"+report_shift_details.get(i).getReceipt_code());
                Print("RECEIPT NO :"+report_shift_details.get(i).getReceipt_code()+"     Rs "+dft.format(Double.parseDouble(report_shift_details.get(i).getCollectamt())));

                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print("NEW CUSTOMER       ");

                if(report_shift_details.get(i).getName() != null) {
                    Clearbuffer();//Clear unwanted buffer
                    Alignment(27, 97, 0);//Left alignment
                    Print(report_shift_details.get(i).getName()+",");

                    Clearbuffer();//Clear unwanted buffer
                    Alignment(27, 97, 0);//Left alignment
                    Print(report_shift_details.get(i).getCity());
                }


                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 1);//Center aligment
                Print(line_space);

            }

        }

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("NO.OF.RECEIPT   : "+String.valueOf(report_shift_details.size()));

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("BANK COLLECTION : "+dft.format(Bank_Total));
        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("DENOMINATION     ");

        if(!shift_details.get(0).getCt_2000().equals("0") && !shift_details.get(0).getCt_2000().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,0);//Left alignment
            Print("2000  * "+shift_details.get(0).getCt_2000()+" = "+ String.valueOf(2000 * Integer.parseInt(shift_details.get(0).getCt_2000())));


        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_500().equals("0") && !shift_details.get(0).getCt_500().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("500   * "+shift_details.get(0).getCt_500()+" = "+ String.valueOf(500 * Integer.parseInt(shift_details.get(0).getCt_500())));
        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_200().equals("0") && !shift_details.get(0).getCt_200().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("200   * "+shift_details.get(0).getCt_200()+" = "+ String.valueOf(200 * Integer.parseInt(shift_details.get(0).getCt_200())));
        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_100().equals("0") && !shift_details.get(0).getCt_100().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("100   * "+shift_details.get(0).getCt_100()+" = "+ String.valueOf(100 * Integer.parseInt(shift_details.get(0).getCt_100())));

        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_50().equals("0") && !shift_details.get(0).getCt_50().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("50    * "+shift_details.get(0).getCt_50()+" = "+ String.valueOf(50 * Integer.parseInt(shift_details.get(0).getCt_50())));
        }else {
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_20().equals("0") && !shift_details.get(0).getCt_20().equals("")){

            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("20    * "+shift_details.get(0).getCt_20()+" = "+ String.valueOf(20 * Integer.parseInt(shift_details.get(0).getCt_50())));
        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCt_10().equals("0") && !shift_details.get(0).getCt_10().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("10    * "+shift_details.get(0).getCt_10()+" = "+ String.valueOf(10 * Integer.parseInt(shift_details.get(0).getCt_10())));
        }else{
            Clearbuffer();//Clear unwanted buffer
        }
        if(!shift_details.get(0).getCt_5().equals("0") && !shift_details.get(0).getCt_5().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("5     * "+shift_details.get(0).getCt_5()+" = "+ String.valueOf(5 * Integer.parseInt(shift_details.get(0).getCt_5())));

        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        if(!shift_details.get(0).getCoins().equals("0") && !shift_details.get(0).getCoins().equals("")){
            Clearbuffer();//Clear unwanted buffer
            Alignment(27,97,0);//Left alignment
            Print("Coins   "+shift_details.get(0).getCoins());
        }else{
            Clearbuffer();//Clear unwanted buffer
        }

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("CASH COLLECTION : "+dft.format(Cash_Total));

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("TOTAL AMOUNT    : "+dft.format(Total_amt));

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Alignment(27,97,0);//Left alignment
        Print("COLL.AGENT SIGN : ");


        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Print("");

        Clearbuffer();//Clear unwanted buffer
        Alignment(27,97,1);//Center aligment
        //Leavespace();
        Print(" * * * * * THANK YOU * * * * * ");

        Clearbuffer();//Clear unwanted buffer
        Leavespace();
        Print("");
        Clearbuffer();//Clear unwanted buffer
        Print("");
        Clearbuffer();//Clear unwanted buffer
        Print("");

        toast("Print ");
        palmtecandro.jnidevClose();

       // progressDialog.dismiss();
    }

    public void cancel_payment(int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Shift_Activity.this, R.style.AlertDialogStyle);
        builder.setMessage("Do you want to cancel ?")
                .setTitle("Cancel Payment")
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DataBaseAdapter base_Adapter = new DataBaseAdapter(Report_Shift_Activity.this);
                        base_Adapter.open();
                        String success  = base_Adapter.Update_Transact(Integer.parseInt(report_shift_details.get(pos).getReceipt_code()),"cancel");
                        base_Adapter.close();
                      if(success.equals("success")){
                            Log.d("TRANSACT_TABLE_DATA =======RECEIPT_NO(" + String.valueOf(report_shift_details.get(pos).getReceipt_code()) + ")========>", "cancelled");
                            toast("Payment cancelled");

                          Intent i = new Intent(Report_Shift_Activity.this,Report_Shift_Activity.class);
                          i.putExtra("shiftcode",Shift_code);
                          i.putExtra("shifttime",Shift_time);
                          i.putExtra("shiftdate",Shift_date);
                          Report_Shift_Activity.this.finish();
                          startActivity(i);
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void calculateAreaWiseTotal(){

        final DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");
        double totalamt=0;

        for(int i=0;i<report_shift_details.size();i++){
            if(report_shift_details.get(i).getCancel().equals("N")) {
                Double amt = Double.parseDouble(report_shift_details.get(i).getCollectamt());
                totalamt = totalamt + amt;

                Total_amt = Total_amt + amt;
                if(report_shift_details.get(i).getModepay().equals("BANK TRANSFER")) {
                    Double bank_amt = Double.parseDouble(report_shift_details.get(i).getCollectamt());
                    Bank_Total =Bank_Total + bank_amt;
                }
                if(report_shift_details.get(i).getModepay().equals("CASH")) {
                    Double cash_amt = Double.parseDouble(report_shift_details.get(i).getCollectamt());
                    Cash_Total =Cash_Total + cash_amt;
                }
            }
        }
        if(totalamt <= 0)
            tot_amount_tv.setText(String.valueOf("₹ 0.00"));
        else
            tot_amount_tv.setText(String.valueOf("₹ "+dft.format(totalamt)));
    }

    public class Get_Shift_Details extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Report_Shift_Activity.this);
            baseAdapter.open();
            mCur = baseAdapter.Last_Shift(Shift_code);
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(mCur != null) {
                if (mCur.getCount() > 0) {
                    shift_details.clear();
                    String status;
                    for (int i = 0; i < mCur.getCount(); i++) {
                        shift_details.add(new shift_details(mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(4), mCur.getString(5), mCur.getString(6), mCur.getString(7), mCur.getString(8), mCur.getString(9), mCur.getString(10)
                                , mCur.getString(11), mCur.getString(12), mCur.getString(13), mCur.getString(14), mCur.getString(15), mCur.getString(16), mCur.getString(17), mCur.getString(18), mCur.getString(19), mCur.getString(20), mCur.getString(21), mCur.getString(22), mCur.getString(23)));
                        Log.d("SHIFT_TABLE_DATA ======ROW(" + i + ")=========>", mCur.getString(2)+"===="+mCur.getString(3));
                        mCur.moveToNext();
                    }
                    // cr_listview.setAdapter();
                } else {
                    Log.d("SHIFT_TABLE_DATA_ERROR ===============>", "No Data Available");
                    // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }

            if(shift_details.size() >0){
                if(!shift_details.get(0).getCt_2000().equals("0") && !shift_details.get(0).getCt_2000().equals("")){
                    LL2000.setVisibility(View.VISIBLE);
                    ct_2000_tv.setText(String.valueOf(shift_details.get(0).getCt_2000()));
                    tv_2000.setText(String.valueOf(shift_details.get(0).getCr_2000()));
                }else{
                   LL2000.setVisibility(View.GONE);
                }

               if(!shift_details.get(0).getCt_500().equals("0") && !shift_details.get(0).getCt_500().equals("")){
                    LL500.setVisibility(View.VISIBLE);
                    ct_500_tv.setText(String.valueOf(shift_details.get(0).getCt_500()));
                    tv_500.setText(String.valueOf(shift_details.get(0).getCr_500()));
                }else{
                    LL500.setVisibility(View.GONE);
                }

                if(!shift_details.get(0).getCt_200().equals("0") && !shift_details.get(0).getCt_200().equals("")){
                    LL200.setVisibility(View.VISIBLE);
                    ct_200_tv.setText(String.valueOf(shift_details.get(0).getCt_200()));
                    tv_200.setText(String.valueOf(shift_details.get(0).getCr_200()));
                }else{
                    LL200.setVisibility(View.GONE);
                }

               if(!shift_details.get(0).getCt_100().equals("0") && !shift_details.get(0).getCt_100().equals("")){
                   LL100.setVisibility(View.VISIBLE);
                    ct_100_tv.setText(String.valueOf(shift_details.get(0).getCt_100()));
                    tv_100.setText(String.valueOf(shift_details.get(0).getCr_100()));
                }else{
                    LL100.setVisibility(View.GONE);
                }

                if(!shift_details.get(0).getCt_50().equals("0") && !shift_details.get(0).getCt_50().equals("")){
                    LL50.setVisibility(View.VISIBLE);
                    ct_50_tv.setText(String.valueOf(shift_details.get(0).getCt_50()));
                    tv_50.setText(String.valueOf(shift_details.get(0).getCr_50()));
                }else{
                    LL50.setVisibility(View.GONE);
                }

                if(!shift_details.get(0).getCt_20().equals("0") && !shift_details.get(0).getCt_20().equals("")){
                    LL20.setVisibility(View.VISIBLE);
                    ct_20_tv.setText(String.valueOf(shift_details.get(0).getCt_20()));
                    tv_20.setText(String.valueOf(shift_details.get(0).getCr_20()));
                }else{
                    LL20.setVisibility(View.GONE);
                }

                if(!shift_details.get(0).getCt_10().equals("0") && !shift_details.get(0).getCt_10().equals("")){
                    LL10.setVisibility(View.VISIBLE);
                    ct_10_tv.setText(String.valueOf(shift_details.get(0).getCt_10()));
                    tv_10.setText(String.valueOf(shift_details.get(0).getCr_10()));
                }else{
                    LL10.setVisibility(View.GONE);
                }
                if(!shift_details.get(0).getCt_5().equals("0") && !shift_details.get(0).getCt_5().equals("")){
                    LL5.setVisibility(View.VISIBLE);
                    ct_5_tv.setText(String.valueOf(shift_details.get(0).getCt_5()));
                    tv_5.setText(String.valueOf(shift_details.get(0).getCr_5()));
                }else{
                    LL5.setVisibility(View.GONE);
                }

                if(!shift_details.get(0).getCoins().equals("0") && !shift_details.get(0).getCoins().equals("")){
                    LLCoins.setVisibility(View.VISIBLE);
                    ct_coins_tv.setText(String.valueOf(shift_details.get(0).getCoins()));
                    tv_coins.setText(String.valueOf(shift_details.get(0).getCoins()));
                }else{
                    LLCoins.setVisibility(View.GONE);
                }

            }


        }
    }
    public void toast(String message)
    {
        Toast toast = new Toast(Report_Shift_Activity.this);
        View view = LayoutInflater.from(Report_Shift_Activity.this).inflate(R.layout.toast_custom, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }



    private static void Alignment(int param1, int param2, int param3) {
        int iLen = 0;

        iLen=3;
        final int[] dataArr = new int[iLen];
        dataArr[0] = param1;
        dataArr[1] = param2;

        dataArr[2] = param3;

        palmtecandro.jnidevDataWrite(dataArr, iLen);

    }
    private static void Print(String input) {
        int iLen = 0;
        int iCount = 0;
        int iPos = 0;

        byte[] _data = input.getBytes();
        iLen = _data.length;
        iLen += 4;
        final int[] dataArr = new int[iLen];
        dataArr[0] = (byte) 0x1B;
        dataArr[1] = (byte) 0x21;
        dataArr[2] = (byte) 0x00;
        iCount = 3;
        for (; iCount < iLen - 1; iCount++, iPos++) {
            dataArr[iCount] = _data[iPos];
        }
        dataArr[iCount] = (byte) 0x0A;

        palmtecandro.jnidevDataWrite(dataArr, iLen);
    }

    private static void Print_title(String input) {
        int iLen = 0;
        int iCount = 0;
        int iPos = 0;

        byte[] _data = input.getBytes();
        iLen = _data.length;
        iLen += 4;
        final int[] dataArr = new int[iLen];
        dataArr[0] = (byte) 0x1B;
        dataArr[1] = (byte) 0x21;
        dataArr[2] = (byte) 0x00;
        iCount = 3;
        for (; iCount < iLen - 1; iCount++, iPos++) {
            dataArr[iCount] = _data[iPos];
        }
        dataArr[iCount] = (byte) 0x0A;

        palmtecandro.jnidevDataWrite(dataArr, iLen);
    }
    private static void PrintBold(String input) {
        int iLen = 0;
        int iCount = 0;
        int iPos = 0;

        byte[] _data = input.getBytes();
        iLen = _data.length;
        iLen += 4;
        final int[] dataArr = new int[iLen];
        dataArr[0] = (byte) 0x1B;
        dataArr[1] = (byte) 0x21;
        dataArr[2] = (byte) 0x10;
        iCount = 3;
        for (; iCount < iLen - 1; iCount++, iPos++) {
            dataArr[iCount] = _data[iPos];
        }
        dataArr[iCount] = (byte) 0x0A;

        palmtecandro.jnidevDataWrite(dataArr, iLen);
    }

    private static void PrintUnderline(String input) {
        int iLen = 0;
        int iCount = 0;
        int iPos = 0;

        byte[] _data = input.getBytes();
        iLen = _data.length;
        iLen += 4;
        final int[] dataArr = new int[iLen];
        dataArr[0] = (byte) 0x1B;
        dataArr[1] = (byte) 0x21;
        dataArr[2] = (byte) 0x80;
        iCount = 3;
        for (; iCount < iLen - 1; iCount++, iPos++) {
            dataArr[iCount] = _data[iPos];
        }
        dataArr[iCount] = (byte) 0x0A;

        palmtecandro.jnidevDataWrite(dataArr, iLen);
    }
    private static void Clearbuffer() {
        int iLen = 0;

        iLen = 2;
        final int[] dataArr = new int[iLen];
        dataArr[0] = 27;
        dataArr[1] = 64;
        palmtecandro.jnidevDataWrite(dataArr, iLen);

    }
    private static void Leavespace() {
        int iLen = 0;

        iLen = 1;
        final int[] dataArr = new int[iLen];
        dataArr[0] = (byte) 0x0A;
       /* dataArr[1] = (byte) 0x64;
        dataArr[2] = (byte) 0x1;*/
        palmtecandro.jnidevDataWrite(dataArr, iLen);

    }

    public String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }


    public class shift_details{
        String shift_name,shift_code,shift_date,shift_time,cash_collectamt,cr_2000,ct_2000,cr_500,ct_500,cr_200,ct_200,cr_100,ct_100,cr_50,ct_50,cr_20,ct_20,cr_10,ct_10,cr_5,ct_5,coins,total_cash;

        public shift_details(String shift_name, String shift_code, String shift_date, String shift_time, String cash_collectamt, String cr_2000, String ct_2000, String cr_500, String ct_500, String cr_200, String ct_200, String cr_100, String ct_100, String cr_50, String ct_50, String cr_20, String ct_20, String cr_10, String ct_10, String cr_5, String ct_5, String coins, String total_cash) {
            this.shift_name = shift_name;
            this.shift_code = shift_code;
            this.shift_date = shift_date;
            this.shift_time = shift_time;
            this.cash_collectamt = cash_collectamt;
            this.cr_2000 = cr_2000;
            this.ct_2000 = ct_2000;
            this.cr_500 = cr_500;
            this.ct_500 = ct_500;
            this.cr_200 = cr_200;
            this.ct_200 = ct_200;
            this.cr_100 = cr_100;
            this.ct_100 = ct_100;
            this.cr_50 = cr_50;
            this.ct_50 = ct_50;
            this.cr_20 = cr_20;
            this.ct_20 = ct_20;
            this.cr_10 = cr_10;
            this.ct_10 = ct_10;
            this.cr_5 = cr_5;
            this.ct_5 = ct_5;
            this.coins = coins;
            this.total_cash = total_cash;
        }

        public String getShift_name() {
            return shift_name;
        }

        public void setShift_name(String shift_name) {
            this.shift_name = shift_name;
        }

        public String getShift_code() {
            return shift_code;
        }

        public void setShift_code(String shift_code) {
            this.shift_code = shift_code;
        }

        public String getShift_date() {
            return shift_date;
        }

        public void setShift_date(String shift_date) {
            this.shift_date = shift_date;
        }

        public String getShift_time() {
            return shift_time;
        }

        public void setShift_time(String shift_time) {
            this.shift_time = shift_time;
        }

        public String getCash_collectamt() {
            return cash_collectamt;
        }

        public void setCash_collectamt(String cash_collectamt) {
            this.cash_collectamt = cash_collectamt;
        }

        public String getCr_2000() {
            return cr_2000;
        }

        public void setCr_2000(String cr_2000) {
            this.cr_2000 = cr_2000;
        }

        public String getCt_2000() {
            return ct_2000;
        }

        public void setCt_2000(String ct_2000) {
            this.ct_2000 = ct_2000;
        }

        public String getCr_500() {
            return cr_500;
        }

        public void setCr_500(String cr_500) {
            this.cr_500 = cr_500;
        }

        public String getCt_500() {
            return ct_500;
        }

        public void setCt_500(String ct_500) {
            this.ct_500 = ct_500;
        }

        public String getCr_200() {
            return cr_200;
        }

        public void setCr_200(String cr_200) {
            this.cr_200 = cr_200;
        }

        public String getCt_200() {
            return ct_200;
        }

        public void setCt_200(String ct_200) {
            this.ct_200 = ct_200;
        }

        public String getCr_100() {
            return cr_100;
        }

        public void setCr_100(String cr_100) {
            this.cr_100 = cr_100;
        }

        public String getCt_100() {
            return ct_100;
        }

        public void setCt_100(String ct_100) {
            this.ct_100 = ct_100;
        }

        public String getCr_50() {
            return cr_50;
        }

        public void setCr_50(String cr_50) {
            this.cr_50 = cr_50;
        }

        public String getCt_50() {
            return ct_50;
        }

        public void setCt_50(String ct_50) {
            this.ct_50 = ct_50;
        }

        public String getCr_20() {
            return cr_20;
        }

        public void setCr_20(String cr_20) {
            this.cr_20 = cr_20;
        }

        public String getCt_20() {
            return ct_20;
        }

        public void setCt_20(String ct_20) {
            this.ct_20 = ct_20;
        }

        public String getCr_10() {
            return cr_10;
        }

        public void setCr_10(String cr_10) {
            this.cr_10 = cr_10;
        }

        public String getCt_10() {
            return ct_10;
        }

        public void setCt_10(String ct_10) {
            this.ct_10 = ct_10;
        }

        public String getCr_5() {
            return cr_5;
        }

        public void setCr_5(String cr_5) {
            this.cr_5 = cr_5;
        }

        public String getCt_5() {
            return ct_5;
        }

        public void setCt_5(String ct_5) {
            this.ct_5 = ct_5;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public String getTotal_cash() {
            return total_cash;
        }

        public void setTotal_cash(String total_cash) {
            this.total_cash = total_cash;
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
}
