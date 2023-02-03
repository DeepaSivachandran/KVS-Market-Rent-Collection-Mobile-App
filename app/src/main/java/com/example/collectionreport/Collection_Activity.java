package com.example.collectionreport;

import static com.example.collectionreport.Areawise_Activity.customer_active_details;
import static com.example.collectionreport.Login_Activity.agent_code;
import static com.example.collectionreport.MainActivity.AG_CODE;
import static com.example.collectionreport.MainActivity.city_name;
import static com.example.collectionreport.MainActivity.generate_code;
import static com.example.collectionreport.MainActivity.master_details;
import static com.example.collectionreport.MainActivity.master_details_active;
import static com.example.collectionreport.MainActivity.master_details_completed;
import static com.example.collectionreport.MainActivity.master_details_inactive;
import static com.example.collectionreport.MainActivity.master_details_pending;
import static com.example.collectionreport.MainActivity.model;
import static com.example.collectionreport.MainActivity.settings_details;
import static com.example.collectionreport.MainActivity.strDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.softland.palmtecandro.palmtecandro;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Collection_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String line_space = "-------------------------------";
    DecimalFormat dft = new DecimalFormat("0.00");
    public static  ArrayList<Integer> response = new ArrayList<>();
    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
    private boolean networkstate;
    public static TabLayout tabLayout;
    ViewPager viewPager;
    Citylist_Adapter citylist_adapter;
    ListView city_Listview;
    ArrayAdapter payment_arrayAdapter;
    ImageView back_img;
    TextView mode_of_payment_txt,completed_customer_tv,unk_name_tv,customer_type_txt,receipt_number,title_tv,tot_city_tv ,tot_customer_tv,active_customer_tv,pending_customer_tv,inactive_customer_tv;
    LinearLayout LL_UNK,LL_NEW_NAME,LL_NEW_PHONE,LL_CITY;
    String ShiftCode_df,payment_name="",payment_code="0";
    String Phoneno="" ,Name="",City="",Amount,customer_code,Description;
    DataBaseAdapter baseAdapter;
    ProgressBar progress_bar;
    Dialog entry_dialog,closed_dialog;
    Spinner customer_type;
    Spinner mode_of_payment;
    ArrayList<String> payment_arraylist = new ArrayList<>();
    ArrayList<String> payment_code_arraylist = new ArrayList<>();
    EditText amount,description,new_name_tv,phoneno_tv,city_tv;
    Button btnSave;
    ArrayAdapter arrayAdapter;
    FloatingActionButton add;
    ArrayList<String> customer_name_arraylist = new ArrayList<>();
    ArrayList<String> customer_code_arraylist = new ArrayList<>();
    public static String strTime="";
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdtime = new SimpleDateFormat("hh:mm aa", Locale.US);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String current_time ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_activity);

        back_img = (ImageView) findViewById(R.id.back_img);

        city_Listview = (ListView) findViewById(R.id.city_Listview);

        tot_customer_tv = (TextView) findViewById(R.id.total_customer_tv);
        active_customer_tv  = (TextView) findViewById(R.id.active_customer_tv);
        pending_customer_tv  = (TextView) findViewById(R.id.pending_customer_tv);
        inactive_customer_tv  = (TextView) findViewById(R.id.inactive_customer_tv);

        completed_customer_tv = (TextView) findViewById(R.id.completed_customer_tv);

        add = (FloatingActionButton) findViewById(R.id.add);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        title_tv = (TextView) findViewById(R.id.title_tv);
        tot_city_tv = (TextView) findViewById(R.id.tot_city_tv);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collection_Activity.this.finish();
            }
        });
        networkstate = isNetworkAvailable();
        try{
            tot_customer_tv.setText(String.valueOf(master_details.size()));
            active_customer_tv.setText(String.valueOf(master_details_active.size()));
            pending_customer_tv.setText(String.valueOf(master_details_pending.size()));
            inactive_customer_tv.setText(String.valueOf(master_details_inactive.size()));
            completed_customer_tv.setText(String.valueOf(master_details_completed.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseAdapter = new DataBaseAdapter(Collection_Activity.this);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("ddMMyyyy ");
        ShiftCode_df = mdformat.format(calendar.getTime());
      //  title_tv.setText(ShiftCode_df);
        tot_city_tv.setText(String.valueOf(city_name.size()));
        new Async_Data().execute();
        //Check_Shift();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(master_details != null) {
                    new Async_Get_Receipt(master_details.get(0).getAg_code(), 0).execute();
                   // Toast.makeText(getApplicationContext(), " open ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), " unable to open ", Toast.LENGTH_SHORT).show();
                }
            }
        });

}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){
            case R.id. mode_of_payment_unk:
                payment_code =String.valueOf(payment_code_arraylist.get(i));
                payment_name =String.valueOf(payment_arraylist.get(i));
                mode_of_payment_txt.setError(null);
                break;
        }
        if (adapterView.getId() == R.id.customer_type) {
            customer_type_txt.setError(null);
            amount.setError(null);
            //Toast.makeText(getApplicationContext(), String.valueOf(customer_code_arraylist.get(i)), Toast.LENGTH_SHORT).show();
            customer_code = String.valueOf(customer_code_arraylist.get(i));

            if(String.valueOf(customer_code_arraylist.get(i)).equals("2")){
                LL_UNK.setVisibility(View.VISIBLE);
                LL_NEW_NAME.setVisibility(View.GONE);
                LL_NEW_PHONE.setVisibility(View.GONE);
                LL_CITY.setVisibility(View.GONE);
            }
            if(String.valueOf(customer_code_arraylist.get(i)).equals("3")) {

                LL_UNK.setVisibility(View.GONE);
                LL_NEW_NAME.setVisibility(View.VISIBLE);
                LL_NEW_PHONE.setVisibility(View.VISIBLE);
                LL_CITY.setVisibility(View.VISIBLE);
            }
            }

        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class Async_Data extends
            AsyncTask<String, JSONObject,Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progress_bar.getVisibility() == View.GONE) {
                progress_bar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(progress_bar.getVisibility() == View.VISIBLE) {
                progress_bar.setVisibility(View.GONE);
            }

            citylist_adapter = new Citylist_Adapter(getApplicationContext(),city_name);
            city_Listview.setAdapter(citylist_adapter);
            citylist_adapter.notifyDataSetChanged();
        }
    }

    public class Citylist_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<City_details> city_list;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Citylist_Adapter(Context context,ArrayList<City_details> city_li){
            this.context=context;
            this.city_list=city_li;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return city_list.size();
        }

        @Override
        public Object getItem(int position) {
            return city_list.get(position);
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
                view = layoutInflater.inflate(R.layout.citywise_list_card, viewGroup, false);
                viewHolder = new ViewHolder();

                viewHolder.city_name_tv = (TextView) view.findViewById(R.id.city_name_tv);
                viewHolder.total_customer_tv =(TextView) view.findViewById(R.id.total_customer_tv);
                viewHolder.city_card = (CardView)view.findViewById(R.id.city_card);
                viewHolder.city_name_tv.setText(city_list.get(position).getCity_name());
                viewHolder.total_customer_tv.setText(city_list.get(position).getTotal_customer());

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.city_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Collection_Activity.this,Areawise_Activity.class);
                    i.putExtra("cityname",city_list.get(position).getCity_name());
                    startActivity(i);
                }
            });
            return view;
        }

    }

    public class Async_Get_Receipt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor cursor;
        DataBaseAdapter base_Adapter;
        String ag_code;
        int pos;

        public Async_Get_Receipt(String agcode,int position) {
            this.ag_code =agcode;
            this.pos = position;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
             base_Adapter = new DataBaseAdapter(Collection_Activity.this);
             base_Adapter.open();
             cursor = base_Adapter.Get_Receipt(ag_code);

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // customer_name.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            base_Adapter.close();
            String receiptcode = "",receiptno="",collectdt="",customerno_max="";
            //  passcode = new String[0];
            //  login_status = new String[0];
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                    for(int i=0;i<cursor.getCount();i++){
                        receiptcode = cursor.getString(0);
                        receiptno  = cursor.getString(1);
                        collectdt  = cursor.getString(2);
                        customerno_max = cursor.getString(3);
                        //   cursor.moveToNext();
                    }
                    Log.d("TRANSACTION TABLE RECEIPT NO ===============>", String.valueOf(receiptno+"======="+receiptcode));
                } else {

                    Log.d("TRANSACTION TABLE RECEIPT NO ===============>", " RECEIPT COLUMN EMPTY ");
                    // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                }

            }
            if(!receiptcode.equals("")){
                openEntryPopup(ag_code,receiptno,receiptcode,collectdt,customerno_max);
            }else{
                Toast.makeText(getApplicationContext(), " unable to open payment dailog ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openEntryPopup(String ag_code,String receiptno,String receiptcode,String collectdt,String customerno) {
        entry_dialog = new Dialog(Collection_Activity.this);
        entry_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        entry_dialog.setContentView(R.layout.customer_unknown_popup);
        entry_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ImageView closepopup = (ImageView) entry_dialog.findViewById(R.id.closepopup);
        customer_type = (Spinner)entry_dialog.findViewById(R.id.customer_type);

        amount = (EditText) entry_dialog.findViewById(R.id.amount);
        description = (EditText) entry_dialog.findViewById(R.id.remarks);
        btnSave = (Button)entry_dialog.findViewById(R.id.btnSave);
        receipt_number = (TextView) entry_dialog.findViewById(R.id.receipt_number);
        customer_type_txt = (TextView) entry_dialog.findViewById(R.id.customer_type_txt);
        customer_type.setOnItemSelectedListener(this);

        mode_of_payment = (Spinner)entry_dialog.findViewById(R.id.mode_of_payment_unk);
        mode_of_payment_txt = (TextView) entry_dialog.findViewById(R.id.mode_of_payment_txt);
        mode_of_payment.setOnItemSelectedListener(this);

        LL_UNK =  (LinearLayout) entry_dialog.findViewById(R.id.LL_UNK);
        LL_NEW_NAME =  (LinearLayout) entry_dialog.findViewById(R.id.LL_NEW_NAME);
        LL_NEW_PHONE =  (LinearLayout) entry_dialog.findViewById(R.id.LL_NEW_PHONE);
        LL_CITY  =  (LinearLayout) entry_dialog.findViewById(R.id.LL_CITY);

        unk_name_tv = (TextView) entry_dialog.findViewById(R.id.unk_name_tv);

        new_name_tv  = (EditText) entry_dialog.findViewById(R.id.new_name_tv);
        phoneno_tv  = (EditText) entry_dialog.findViewById(R.id.phoneno_tv);
        city_tv= (EditText) entry_dialog.findViewById(R.id.city_tv);

        unk_name_tv.setText(String.valueOf("Unknown Customer "+customerno));

        customer_name_arraylist.clear();
      //  customer_name_arraylist.add("Select Customer Type");
        customer_name_arraylist.add("Unknown Customer");
        customer_name_arraylist.add("New Customer");

        customer_code_arraylist.clear();
       // customer_code_arraylist.add("0");
        customer_code_arraylist.add("2");
        customer_code_arraylist.add("3");

        // mode_of_payment.setSelection(0);

        payment_arraylist.clear();
        //payment_arraylist.add("Select Payment Mode");
        payment_arraylist.add("Cash");
        // payment_arraylist.add("Cheque");
        payment_arraylist.add("Bank Transfer");

        payment_code_arraylist.clear();
        // payment_code_arraylist.add("0");
        payment_code_arraylist.add("1");
        //  payment_code_arraylist.add("2");
        payment_code_arraylist.add("3");

        // mode_of_payment.setSelection(0);

        // receipt_number.setText(String.valueOf(receiptcode+" "+collectdt));
        receipt_number.setText(String.valueOf(receiptcode));
        Collection_Activity.this.setTheme(R.style.DialogTheme);
        payment_arrayAdapter  = new ArrayAdapter(Collection_Activity.this, android.R.layout.simple_spinner_item, payment_arraylist) {
            @SuppressLint("ResourceAsColor")
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
               /* if (position == 0) {
                    textview.setEnabled(false);
                    textview.setTextColor(R.color.black);
                } else {
                    textview.setTextColor(R.color.new_gray);
                }*/
                textview.setTextColor(R.color.new_gray);

                return view;
            }

            @Override
            public boolean isEnabled(int position) {
               /* if (position == 0) {
                    return false;
                } else {
                    return true;
                }*/
                return true;
            }

        };
        payment_arrayAdapter.setDropDownViewResource(R.layout.drop_dwon_list);
        mode_of_payment.setAdapter(payment_arrayAdapter);

        receipt_number.setText(String.valueOf(receiptcode));
        //context.setTheme(R.style.DialogTheme);
        arrayAdapter  = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, customer_name_arraylist) {
            @SuppressLint("ResourceAsColor")
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                textview.setTextColor(R.color.new_gray);

                return view;
            }

            @Override
            public boolean isEnabled(int position) {

                return true;
            }

        };
         arrayAdapter.setDropDownViewResource(R.layout.drop_dwon_list);
         customer_type.setAdapter(arrayAdapter);

        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_dialog.dismiss();
            }
        });

        if (payment_code.equals("0")) {
            mode_of_payment_txt.setError("Select payment mode");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // strTime = mdtime.format(calendar.getTime());
                current_time = sdf.format(new Date());
                //Insert_Transation_DB("","","","","","",
                //        "","","","","","","","");
                if(amount!=null) {
                    Amount = amount.getText().toString();
                }
                if(Amount.equals("0") || Amount.equals("")){
                    amount.setError("Enter the amount");
                }
                if(description != null){
                    Description = description.getText().toString();
                }
                if(customer_code.equals("0")){
                    customer_type_txt.setError("Select customer type");
                }
                 if(phoneno_tv != null){
                     Phoneno = phoneno_tv.getText().toString();
                 }
                 if(new_name_tv != null){
                     Name =  new_name_tv.getText().toString();
                 }
                 if(city_tv != null){
                     City = city_tv.getText().toString();
                 }

                if(Name.equals("")){
                    new_name_tv.setError("Enter the name");
                }
                if(City.equals("")){
                    city_tv.setError("Enter the city");
                }
                if(Phoneno.equals("")){
                    phoneno_tv.setError("Enter the phone number");
                }
                if(customer_code.equals("2")) {
                    if(payment_code.equals("1")) {
                        if (!Amount.equals("0") && !Amount.equals("")) {
                            //  Toast.makeText(getApplicationContext(), "Payment done", Toast.LENGTH_SHORT).show();
                            Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 2, Name, Phoneno, City, customerno);
                        } else {

                        }
                    }

                    if(payment_code.equals("3")) {
                        if (!Amount.equals("0") && !Amount.equals("")) {
                            //  Toast.makeText(getApplicationContext(), "Payment done", Toast.LENGTH_SHORT).show();
                            Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 2, Name, Phoneno, City, customerno);
                        } else {

                        }
                    }
                }

                if(customer_code.equals("3")) {
                    if(payment_code.equals("1")) {
                        if (!Amount.equals("0") && !Amount.equals("") && !Name.equals("") && !Phoneno.equals("") && !City.equals("")) {
                            // Toast.makeText(getApplicationContext(), " payment done", Toast.LENGTH_SHORT).show();
                            Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 3, Name, Phoneno, City, String.valueOf(0));
                        }
                    }
                    if(payment_code.equals("3")) {
                        if (!Amount.equals("0") && !Amount.equals("") && !Name.equals("") && !Phoneno.equals("") && !City.equals("")) {
                            // Toast.makeText(getApplicationContext(), " payment done", Toast.LENGTH_SHORT).show();
                            Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 3, Name, Phoneno, City, String.valueOf(0));
                        }
                    }
                }
            }
        });
        entry_dialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Insert_Transation_DB(String sno, String acno, String receiptno, String receiptcode, String ag_code, String actype, String modepay, String palmtec_id,
                                      String cancel, String tr_time, String chequedt, String chequeno, String collectdt, String collectamt, String remark, String shift_code, int customer_type, String name, String phoneno, String city, String customerno) {
        int sno_temp,receipt_max;
        baseAdapter.open();

        Cursor cursor = baseAdapter.Select_Transact();
        if (cursor != null) {

            if (cursor.getCount() > 0) {
                sno_temp = cursor.getCount();
                cursor.moveToLast();
                //receipt_max = Integer.parseInt(cursor.getString(3))+1;

                String success = baseAdapter.insert_transact(String.valueOf(sno_temp+1),acno, receiptno,Integer.parseInt(receiptcode),ag_code,actype,modepay, palmtec_id,
                        cancel,tr_time,chequedt,chequeno,collectdt,collectamt,remark,generate_code,strDate,"",0,0,customer_type,name,phoneno,city,Integer.parseInt(customerno));

                if(success.equals("success")){
                    toast("Payment done");
                    Log.d("TRANSACT_TABLE_INSERTION===============>",String.valueOf("completed"));

                    Log.d("COLLECTION ACTIVITY=======(MODE OF PAYMENT)========>",modepay);
                    if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                        Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, remark, collectamt, customer_type);
                    }
                    if(isNetworkAvailable()) {
                        new Sync_Services().execute();
                    }

                }else {
                    Log.d("TRANSACT_TABLE_INSERTION===============>","failed");
                }
                Log.d("TRANSACT_MAX()_VALUE===============>", String.valueOf(sno_temp));
            }
            if (cursor.getCount()== 0) {
                sno_temp = cursor.getCount();

                String success = baseAdapter.insert_transact(String.valueOf(1),acno,receiptno,Integer.parseInt(receiptcode),ag_code,actype,modepay, palmtec_id,
                        cancel,tr_time,chequedt,chequeno,collectdt,collectamt,remark,generate_code,strDate,"",0,0,customer_type,name,phoneno,city,Integer.parseInt(customerno));

                if(success.equals("success")){
                    toast("Payment done");
                    Log.d("TRANSACT_TABLE_INSERTION===============>","completed");

                    Log.d("COLLECTION ACTIVITY=======(MODE OF PAYMENT)========>",modepay);

                    if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                        Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, remark, collectamt, customer_type);
                    }
                    if(isNetworkAvailable()) {
                        new Sync_Services().execute();
                    }
                }else
                {
                    Log.d("TRANSACT_TABLE_INSERTION===============>","failed");
                }
                Log.d("TRANSACT_MAX()_VALUE===============>", String.valueOf(sno_temp));
            }
        } else {
            Log.d("CURSOR NULL===============>","unable to get");

        }
        entry_dialog.dismiss();

        baseAdapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Print_Receipt(String date, String time, String receiptcode, String customer_id, String grp_code, String customer_name, String phoneno, String city, String remarks, String paidamount, int customer_type) {
        double  prvs_amount=0,paid_amount=0;
        double total_amt=0;
        String agent_code,tot_amt_words="";
        //   String date ="",time="",receipt_no="",cust_id="",grp_code="",cust_name="",phone_no="",city="",agent_code="",remark="";
        // date = date;
        // time = time;
        // receipt_no = String.valueOf(report_shift_details.get(pos).getReceipt_code() +" "+report_shift_details.get(pos).getCollectdt());
        // receipt_no = receiptno;
        //cust_id = customer_id;
        grp_code = grp_code;
        // cust_name = customer_name;
        // phone_no = phoneno;
        city = city;
        // remark = report_shift_details.get(pos).getRemark();
        total_amt = Double.parseDouble(paidamount);
        agent_code = MainActivity.AGENTCODE;
        tot_amt_words = String.valueOf(convertIntoWords(total_amt,"en","US"));
        // if(!date.equals("") && !time.equals("") && !cust_id.equals("") && ) {
        palmtecandro.jnidevOpen(115200);

        if(settings_details.size()>0) {
            Clearbuffer();//Clear unwanted buffer
            //Leavespace();
            Alignment(27,97,1);//Center aligment
            //Print(" KAS JEWELLERY");
            Print(" "+settings_details.get(0).getCompany_name());

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
        if (!receiptcode.equals("")) {

            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("RECEIPT NO   : " + receiptcode);
        }


        if(customer_type ==2 ){
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

            if(!remarks.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("UNKNOWN CUSTOMER : "+remarks);

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

        if( customer_type ==3){
            if(!customer_name.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER NAME: "+customer_name);
            }

            if(!phoneno.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("PHONE NO     : "+phoneno);
            }

            if(!city.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CITY         : "+city);
            }

            if(!remarks.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("NEW CUSTOMER : "+remarks);
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

    public void toast(String message)
    {
        Toast toast = new Toast(Collection_Activity.this);
        View view = LayoutInflater.from(Collection_Activity.this).inflate(R.layout.toast_custom, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow;
        Button button;
        CardView cardview,Show_more,Track;
        TextView city_name_tv,total_customer_tv;
        TextView customer_name,area_name,acc_no,mob_no;
    }

    public class Sync_Services extends
            AsyncTask<String, JSONObject, Boolean> {
        JSONObject jsonObj;
        String update;
        int success =0;
        RestAPI objRestAPI = new RestAPI();
        DataBaseAdapter base_Adapter;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {
            base_Adapter = new DataBaseAdapter(getApplicationContext());
            base_Adapter.open();
            mCur = base_Adapter.Select_Transact();

            if (mCur != null) {
                if (mCur.getCount() > 0) {
                    for (int i = 0; i < mCur.getCount(); i++) {
                        try {
                            String update;
                            //   sync_details.add( new Sync_details(String.valueOf(mCur.getString(2)),String.valueOf( mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), "",String.valueOf( mCur.getString(9)),"14-10-2022", String.valueOf(mCur.getString(11)), "14-10-2022", String.valueOf(mCur.getString(13)),String.valueOf( mCur.getString(14)), String.valueOf(mCur.getString(20)),mCur.getInt(21)));
                            if (mCur.getInt(19) == 0) {
                                // SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                                //  success = objRestAPI.Async_DB_Services("9",	"40055"	,"ZEBRA"	,"W400"	,"CASH",	"N"	,"08:45"	,"11-10-2022"	,"778978987",	"11-10-2022",	"200",	"11-10-2022",	"13",1).getInt("success");

                                String receipt_no = String.valueOf(mCur.getString(20));
                                String code = String.valueOf(mCur.getString(12));
                                String receipt_code = String.valueOf(receipt_no+" "+code);

                                String acno = String.valueOf(mCur.getString(2));
                                String receiptno = String.valueOf(mCur.getString(3));
                                String agcode= String.valueOf(mCur.getString(4));
                                String actype = String.valueOf(mCur.getString(5));
                                String modepay = String.valueOf(mCur.getString(6));
                                String cancel = String.valueOf(mCur.getString(8));
                                String tr_time = String.valueOf(mCur.getString(9));
                                String chequedt = String.valueOf(mCur.getString(10));
                                String chequeno = String.valueOf(mCur.getString(11));
                                String collectdt = String.valueOf(mCur.getString(12));
                                String collectamt = String.valueOf(mCur.getString(13));
                                String remark = String.valueOf(mCur.getString(14));
                                String receiptcode = String.valueOf(mCur.getString(3));


                                Date date = inputFormat.parse(collectdt);
                                String outputDateStr = outputFormat.format(date);
                                if(acno.equals("")){
                                    acno ="null";
                                }
                                if(actype.equals("")){
                                    actype = "null";
                                }
                                if(modepay.equals("")){
                                    modepay ="null";
                                }
                                if(cancel.equals("")){
                                    cancel = "null";
                                }
                                if(chequedt.equals("")){
                                    chequedt ="null";
                                }
                                if(chequeno.equals("")){
                                    chequeno ="null";
                                }
                                if(collectdt.equals("")){
                                    collectdt ="null";
                                }
                                if(remark.equals("")){
                                    remark = "null";
                                }

                                if( !acno.equals("") && !receiptno.equals("") && !agcode.equals("") && !actype.equals("") && !modepay.equals("") && !cancel.equals("")
                                        && !tr_time.equals("") && !chequedt.equals("") && !chequeno.equals("") && !collectdt.equals("") && !collectamt.equals("") && !remark.equals("") && !receiptcode.equals("")){

                                    // success = objRestAPI.Async_DB_Services("9",	"40055"	,"ZEBRA"	,"W400"	,"CASH",	"N"	,"08:45"	,"11-10-2022"	,"778978987",	"11-10-2022",	"200",	"11-10-2022",	"13",1).getInt("success");
                                  // if(objRestAPI.Async_DB_Services(String.valueOf(mCur.getString(2)),String.valueOf( mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), cancel,String.valueOf( mCur.getString(9)),"", "", outputDateStr, String.valueOf(mCur.getString(13)),String.valueOf( mCur.getString(14)), String.valueOf(mCur.getString(20)),mCur.getInt(21),mCur.getString(22),mCur.getString(23),mCur.getString(24),mCur.getString(25)) != null) {

                                       success = objRestAPI.Async_DB_Services(String.valueOf(mCur.getString(2)), String.valueOf(mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), cancel, String.valueOf(mCur.getString(9)), "", "", outputDateStr, String.valueOf(mCur.getString(13)), String.valueOf(mCur.getString(14)), String.valueOf(mCur.getString(20)), mCur.getInt(21), mCur.getString(22), mCur.getString(23), mCur.getString(24), mCur.getString(25)).getInt("success");
                                       Log.d("INSERTED =======ROW(" + String.valueOf(i) + ")========>", String.valueOf("UPDATED   " + mCur.getString(12)) + "   " + outputDateStr);

                                  // }else{
                                  //     Log.d("COLLECTION ACTIVITY  ===============>","API RETURNS NULL JSON OBJECT");
                                 //  }
                                   }else{

                                    //  Toast.makeText(MainActivity.this, "returns null", Toast.LENGTH_SHORT).show();
                                    Log.d("NULL VALUE RETURN =======ROW(" + String.valueOf(i) + ")========>", "null");
                                }
                                //   int success = objRestAPI.Async_DB_Services("9",	"40055"	,"ZEBRA"	,"W400"	,"CASH",	"N"	,"08:45"	,"11-10-2022"	,"778978987",	"11-10-2022",	"200",	"11-10-2022",	"13",1).getInt("success");


                                // int success = objRestAPI. Async_DB_Services(String.valueOf(mCur.getInt(2)),String.valueOf( mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), "",String.valueOf( mCur.getInt(9)), String.valueOf(mCur.getString(10)), String.valueOf(mCur.getString(11)), String.valueOf(mCur.getString(12)), String.valueOf(mCur.getInt(13)),String.valueOf( mCur.getString(14)), receipt_code,mCur.getInt(21)).getInt("success");

                                if (success == 1) {
                                    update = baseAdapter.Update_Transact(mCur.getInt(3),"sync");
                                    if (update.equals("success")) {
                                        Log.d("TRANSACT_TABLE_DATA =======ROW(" + String.valueOf(i) + ")========>", "sync_state changed to 1");
                                    }
                                }
                                //int success = objRestAPI.Async_DB_Services("121212","434","DONKEY","D200","CASH","","05:00","02-08-2022","574574574","02-08-2022","650","jk","80453").getInt("success");
                                response.add(success);
                                Log.d("API_SYNC_RESPONSE =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(success));
                            } else {
                                Log.d("API_SYNC_RESPONSE =======ROW(" + String.valueOf(i) + ")========>", "Row already updated");
                            }
                        } catch (Exception  e) {
                            e.printStackTrace();
                        }
                        Log.d("TRANSACTION ======= ROW(" + String.valueOf(i) + ") ========>", mCur.getString(2)+"="+ mCur.getString(3)+"="+ mCur.getString(4)+"="+ mCur.getString(5)+"="+ mCur.getString(6)+"="+mCur.getString(9)+"="+mCur.getString(10)+"="+mCur.getString(11)+"="+mCur.getString(12)+"="+mCur.getString(13)+"="+mCur.getString(14)+"="+String.valueOf(mCur.getString(20)+" "+mCur.getString(12))+"="+mCur.getInt(21));
                        mCur.moveToNext();
                    }
                } else {

                    //Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                }

             /*   if(sync_details != null) {
                     for (int i = 0; i < sync_details.size(); i++) {
                    try {
                        success = objRestAPI.Async_DB_Services(sync_details.get(i).getAcno(), sync_details.get(i).getReceiptno(),sync_details.get(i).getAg_code(), sync_details.get(i).getActype(), sync_details.get(i).getModepay(), sync_details.get(i).getCancel(), sync_details.get(i).getTr_time(), "","", "", sync_details.get(i).getCollectamt(), sync_details.get(i).getRemark(), sync_details.get(i).getReceipt_code(), sync_details.get(i).getCustomer_type()).getInt("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if (success == 1) {
                        update = baseAdapter.Update_Transact(mCur.getInt(3), "sync");
                        if (update.equals("success")) {
                            Log.d("TRANSACT_TABLE_DATA =======ROW(" + String.valueOf(i) + ")========>", "sync_state changed to 1");
                        }
                    }
                    //int success = objRestAPI.Async_DB_Services("121212","434","DONKEY","D200","CASH","","05:00","02-08-2022","574574574","02-08-2022","650","jk","80453").getInt("success");
                    response.add(success);
                    Log.d("API_SYNC_RESPONSE(STRING) =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(success));
                     }
                }*/

                Log.d("CURSOR_DATA===============>", String.valueOf(mCur.getCount()));
            }

            base_Adapter.close();
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String update="";
            ArrayList<Integer> check = new ArrayList<>();
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).equals(1)) {
                        check.add(1);
                    }
                }

                if (response.size() == check.size()) {
                    // new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    //.setTitleText("Android")
                    //       .setContentText("Synchronized successfully");
                    // Toast.makeText(getApplicationContext(), "Every Data Synchronized", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(MainActivity.this, "Synchronized successfully", Toast.LENGTH_SHORT).show();
                    toast("Synchronized successfully");
                }
            }

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

    public String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
