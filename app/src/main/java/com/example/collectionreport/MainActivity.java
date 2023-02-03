package com.example.collectionreport;

import static com.example.collectionreport.Dowload_SQLite_DB.ShareToAll_Above_Android7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.softland.palmtecandro.palmtecandro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   public static ArrayList<Settings_Details> settings_details = new ArrayList<>();
    String commaseparatedlist="";
    ArrayList<String> date_list = new ArrayList<>();
    ArrayList<String> delete_limit = new ArrayList<>();

    ArrayList<String> SCHEDULE_DATE = new ArrayList<>();
    ArrayList<String> TEMP_DATE = new ArrayList<>();
    public static String manufacturer="",brand="",product="",model="";

    public static TabLayout tabLayout;
    ViewPager viewPager;
    LayoutInflater inflater = null;
    ArrayList<master_details> search_array = new ArrayList<>();
    String line_space = "-------------------------------";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    Dialog entry_dialog,closed_dialog;
    Spinner customer_type;
    Spinner mode_of_payment;
    ImageView search_clear,search_enter;
    Button btnSave;
    ArrayAdapter arrayAdapter;
    LinearLayout LL_UNK,LL_NEW_NAME,LL_NEW_PHONE,LL_CITY;
    EditText search_edittext,txt_cheque_date,txt_cheque_no,amount,remarks,description,new_name_tv,phoneno_tv,city_tv;;
    ArrayList<String> customer_name_arraylist = new ArrayList<>();
    ArrayList<String> customer_code_arraylist = new ArrayList<>();
    LinearLayout cheque_layout;
    ArrayAdapter payment_arrayAdapter;
    ArrayList<Sync_details> sync_details = new ArrayList<>();
    TextView company_name_tv,agent_name_tv,completed_customer_tv,unk_name_tv,customer_type_txt,receipt_number,mode_of_payment_txt,paid_amt,payable_amt,balance_amt,txt_cus_name,txt_acc_no,txt_ac_type,txt_address_line1,txt_address_line2,txt_cityname,txt_mobno,txt_mode;
    ArrayList<String> payment_arraylist = new ArrayList<>();
    ArrayList<String> payment_code_arraylist = new ArrayList<>();
    DecimalFormat money_sdf = new DecimalFormat ("0.00");
    String payment_name="",payment_code="0",Maturity_amount="0",PRVS_check="0",Amount="0",Remarks="0",Cheque_no="0",Cheque_date="";
    String current_time ,customer_code,Description,Phoneno="" ,Name="",City="";
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    DownloadManager manager;
    CardView collection_card, report_card;
    private String codeString = "";
    public static String strTime="",date_db="",strDate = "",RECEIPT_PREFIX="", selectedDate = "",AG_CODE=null;
    TextView title_tv,date_tv,total_customer_tv,active_customer_tv,pending_customer_tv,inactive_customer_tv;
    FloatingActionButton fab, fab1, fab2, fab3,fab4,fab5;
    ImageView close_img;
    DecimalFormat dft = new DecimalFormat("0.00");
    int  Grand_Total=0,Amount_2000=0,Amount_500=0,Amount_200=0,Amount_100=0,Amount_50=0,Amount_20=0,Amount_10=0,Amount_5=0,Amount_coin=0,Coin_count=0;
    int ct_2000,ct_500,ct_200,ct_100,ct_50,ct_20,ct_10,ct_5;
    double Total_colectamt=0,Bank_colectamt=0,Total_collection=0;
    EditText pin_code,edit_2000,edit_500,edit_200,edit_100,edit_50,edit_20,edit_10,edit_5,edit_coin_count;
    TextView wrong_pin_tv,tv_2000,tv_500,tv_200,tv_100,tv_50,tv_20,tv_10,tv_5,grand_total,total_collection_label,total_cash_label,bank_colectamt_label,diff_amount,tv_coin_amount;
    Context context;
    ListView customer_Listview;
    public  ArrayList<master_details> temp_customer = new ArrayList<>();
    Button btnConfirm,btncash_close;
    Dialog verify_popup,denomination_popup;
    String[] agent_name, passcode, login_status;
    public  static  ArrayList<String> customer_name = new ArrayList<String>();
    private boolean networkstate;

    public  static  String PASSCODE="",AGENTCODE="",AGENTNAME="",DEVICE_ID="",COMPANY_NAME="",CITY_NAME ="",SUBTITLE_1="",SUBTITLE_2="";
    public static JSONArray details = null,city_details;
    public static  ArrayList<master_details> master_details = new ArrayList<>();
    public static  ArrayList<master_details> master_details_active = new ArrayList<>();
    public static  ArrayList<master_details> master_details_pending = new ArrayList<>();
    public static  ArrayList<master_details> master_details_inactive = new ArrayList<>();
    public static  ArrayList<master_details> master_details_completed = new ArrayList<>();
    ArrayList<report_shift_details> report_shift_details = new ArrayList<>();
    ArrayList<report_shift_details> arrayList = new ArrayList<>();
    ArrayList<report_shift_details> arrayList_bank = new ArrayList<>();

    public static  ArrayList<Integer> response = new ArrayList<>();

    public static  ArrayList<City_details> city_name = new ArrayList<>();

    private static final int MAX_LENGHT = 4;
    public boolean isFABOpen = false;

    String ShiftCode_df;
    public static  String generate_code="";
    DataBaseAdapter baseAdapter;
    Calendar calendar;
    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat mdformat1 = new SimpleDateFormat("ddMMyyyy");

    SimpleDateFormat mdformat_db = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mdtime = new SimpleDateFormat("hh:mm aa", Locale.US);

    SimpleDateFormat mdformat_receipt = new SimpleDateFormat("yyMM");

    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");

    ImageView menu_img;

    Customer_Adapter adapter;

    MemberListBaseAdapter memberListBaseAdapter ;
    ArrayList<String> stringArrayList = new ArrayList<>();

    Main_Adapter main_adapter;

    public  String USER_CODE = "",USER_NAME = "";
    public static  TextView expected_collection_tv,actual_collection_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        networkstate = isNetworkAvailable();

        collection_card = (CardView) findViewById(R.id.collection_cv);
        report_card = (CardView) findViewById(R.id.report_cv);
        date_tv = (TextView) findViewById(R.id.date_tv);
      //  close_img = (ImageView) findViewById(R.id.close_img);
        title_tv = (TextView) findViewById(R.id.title_tv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        fab5 = (FloatingActionButton) findViewById(R.id.fab5);

        customer_Listview = (ListView) findViewById(R.id.customer_Listview);
        //search_edittext  = (EditText) findViewById(R.id.main_search_edittext);

        actual_collection_tv = (TextView) findViewById(R.id.actual_collection_tv);
        expected_collection_tv = (TextView) findViewById(R.id.expected_collection_tv);

        verify_popup = new Dialog(context);
        verify_popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verify_popup.setContentView(R.layout.confirm_pin_popup);
        pin_code = verify_popup.findViewById(R.id.pin_code);
        btnConfirm = verify_popup.findViewById(R.id.btnConfirm);

        wrong_pin_tv = verify_popup.findViewById(R.id.wrong_pin_tv);

        tabLayout=(TabLayout) findViewById(R.id.tabs);
        viewPager=(ViewPager)findViewById(R.id.viewpage_container);

        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        menu_img =(ImageView) findViewById(R.id.menu_img);

        tabLayout=(TabLayout) findViewById(R.id.tabs);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


                switch(tab.getPosition()){
                    case 0:
                       //  Toast.makeText(getApplicationContext(),"Daily",Toast.LENGTH_SHORT).show();
                        Daily_Fragment daily_fragment = (Daily_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                        daily_fragment.refreshpage();
                        break;
                    case 1:
                        Monthly_Fragment monthly_fragment = (Monthly_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
                        monthly_fragment.refreshpage();
                       //   Toast.makeText(getApplicationContext(),"Monthly",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
                // viewPager.setSelected(true);
            }
        });

        main_adapter = new Main_Adapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(main_adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.setTheme(R.style.popupNew);
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                         public boolean onMenuItemClick(MenuItem item) {

                                                             switch(item.getItemId()) {
                                                                 case R.id.recovery_report_mnu:
                                                                     //add the function to perform here
                                                                     Intent i = new Intent(MainActivity.this,Report_Shift_Activity.class);
                                                                     startActivity(i);
                                                                     return(true);
                                                                 case R.id.logout_mnu:
                                                                     //add the function to perform here
                                                                     LogoutDialog();
                                                                     return(true);
                                                             }
                                                             return false;
                                                         }
                                                     }
                );
                popupMenu.inflate(R.menu.menu_main);
                popupMenu.show();
            }
        });

        pin_code.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                if(wrong_pin_tv.getVisibility() == View.VISIBLE){
                    wrong_pin_tv.setVisibility(View.GONE);
                }
             }
         });
        search_clear = (ImageView) findViewById(R.id.search_clear);
        search_enter = (ImageView) findViewById(R.id.search_enter);

        denomination_popup = new Dialog(context);
        denomination_popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        denomination_popup.setContentView(R.layout.denomination_layout);
        denomination_popup.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        edit_2000 = (EditText) denomination_popup.findViewById(R.id.edit_2000);
        edit_500 = (EditText) denomination_popup.findViewById(R.id.edit_500);
        edit_200 = (EditText) denomination_popup.findViewById(R.id.edit_200);
        edit_100 = (EditText) denomination_popup.findViewById(R.id.edit_100);
        edit_50 = (EditText) denomination_popup.findViewById(R.id.edit_50);
        edit_20 = (EditText) denomination_popup.findViewById(R.id.edit_20);
        edit_10 = (EditText) denomination_popup.findViewById(R.id.edit_10);
        edit_5 = (EditText) denomination_popup.findViewById(R.id.edit_5);

        tv_2000 =  (TextView) denomination_popup.findViewById(R.id.tv_2000);
        tv_500 =  (TextView) denomination_popup.findViewById(R.id.tv_500);
        tv_200 =  (TextView) denomination_popup.findViewById(R.id.tv_200);
        tv_100 =  (TextView) denomination_popup.findViewById(R.id.tv_100);
        tv_50 =  (TextView) denomination_popup.findViewById(R.id.tv_50);
        tv_20 =  (TextView) denomination_popup.findViewById(R.id.tv_20);
        tv_10 =  (TextView) denomination_popup.findViewById(R.id.tv_10);
        tv_5 =  (TextView) denomination_popup.findViewById(R.id.tv_5);

        edit_coin_count = (EditText) denomination_popup.findViewById(R.id.edit_coin_count);
        tv_coin_amount = (TextView) denomination_popup.findViewById(R.id.tv_coin_amount);

        grand_total  =  (TextView) denomination_popup.findViewById(R.id.grand_total);
        total_cash_label   =  (TextView) denomination_popup.findViewById(R.id.total_cash_label);
        bank_colectamt_label   =  (TextView) denomination_popup.findViewById(R.id.bank_colectamt_label);

        total_collection_label  =  (TextView) denomination_popup.findViewById(R.id.total_collection_label);

        diff_amount  =  (TextView) denomination_popup.findViewById(R.id.diff_amount);
        btncash_close = (Button)  denomination_popup.findViewById(R.id.btncash_close);

        total_customer_tv = (TextView) findViewById(R.id.total_customer_tv);
        active_customer_tv = (TextView) findViewById(R.id.active_customer_tv);
        pending_customer_tv = (TextView) findViewById(R.id.pending_customer_tv);
        inactive_customer_tv = (TextView) findViewById(R.id.inactive_customer_tv);
        completed_customer_tv =  (TextView) findViewById(R.id.completed_customer_tv);

        company_name_tv =  (TextView) findViewById(R.id.company_name_tv);
        agent_name_tv =  (TextView) findViewById(R.id.agent_name_tv);

        try {

            manufacturer = Build.MANUFACTURER;
            brand = Build.BRAND;
            product = Build.PRODUCT;
            model = Build.MODEL;

            Log.d("DEVICE MANUFACTURER ===============>", " " + manufacturer);
            Log.d("DEVICE BRAND ===============>", " " + brand);
            Log.d("DEVICE PRODUCT ===============>", " " + product);
            Log.d("DEVICE MODEL ===============>", " " + model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        edit_2000.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()>0) {
                    int total = Calculate_Currency(2000, Integer.parseInt(editable.toString()));
                    ct_2000 = Integer.parseInt(editable.toString());
                    Amount_2000 = total;
                    tv_2000.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_2000 = 0;
                    tv_2000.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_500.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(500, Integer.parseInt(editable.toString()));
                    ct_500 = Integer.parseInt(editable.toString());
                    Amount_500 = total;
                    tv_500.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_500 = 0;
                    tv_500.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_200.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(200, Integer.parseInt(editable.toString()));
                    ct_200 = Integer.parseInt(editable.toString());
                    Amount_200 = total;
                    tv_200.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_200 = 0;
                    tv_200.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_100.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(100, Integer.parseInt(editable.toString()));
                    ct_100 = Integer.parseInt(editable.toString());
                    Amount_100 = total;
                    tv_100.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_100 = 0;
                    tv_100.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_50.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(50, Integer.parseInt(editable.toString()));
                    ct_50 = Integer.parseInt(editable.toString());
                    Amount_50 = total;
                    tv_50.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_50 = 0;
                    tv_50.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_20.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(20, Integer.parseInt(editable.toString()));
                    ct_20 = Integer.parseInt(editable.toString());
                    Amount_20 = total;
                    tv_20.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_20 = 0;
                    tv_20.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(10, Integer.parseInt(editable.toString()));
                    ct_10 = Integer.parseInt(editable.toString());
                    Amount_10 = total;
                    tv_10.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_10 = 0;
                    tv_10.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });
        edit_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total = Calculate_Currency(5, Integer.parseInt(editable.toString()));
                    ct_5 = Integer.parseInt(editable.toString());
                    Amount_5 = total;
                    tv_5.setText(String.valueOf(total));
                    Grand_Total();
                }else{
                    Amount_5 = 0;
                    tv_5.setText(String.valueOf(0));
                    Grand_Total();
                }

            }
        });

        edit_coin_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total =  Calculate_Currency(1, Integer.parseInt(editable.toString()));
                    Coin_count = Integer.parseInt(editable.toString());
                    Amount_coin = total;
                    tv_coin_amount.setText(String.valueOf(Amount_coin));
                    Grand_Total();
                }else{
                    Coin_count  = 0;
                    Amount_coin = 0;
                    tv_coin_amount.setText(String.valueOf(0));
                    Grand_Total();
                }
            }
        });

       /* tv_coin_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
                    int total =  Integer.parseInt(editable.toString());
                    Amount_coin = total;
                    Grand_Total();
                }else{
                    Amount_coin = 0;
                    Grand_Total();
                }
            }
        });*/

        fab.setBackgroundColor(Color.parseColor("#f21628"));
        fab1.setBackgroundColor(Color.parseColor("#d14354"));
        fab2.setBackgroundColor(Color.parseColor("#d14354"));
        fab3.setBackgroundColor(Color.parseColor("#d14354"));
        fab4.setBackgroundColor(Color.parseColor("#d14354"));
        fab5.setBackgroundColor(Color.parseColor("#d14354"));


        try{
            Intent i = getIntent();
            PASSCODE = i.getStringExtra("PASSCODE");
            AGENTCODE = i.getStringExtra("AGENTCODE");

            USER_CODE  = i.getStringExtra("AGENTCODE");
            USER_NAME = i.getStringExtra("AGENTNAME");

            AGENTNAME = i.getStringExtra("AGENTNAME");
            DEVICE_ID = i.getStringExtra("DEVICE_ID");
            COMPANY_NAME = i.getStringExtra("COMPANY_NAME");

            SUBTITLE_1 = i.getStringExtra("SUBTITLE_1");
            SUBTITLE_2 = i.getStringExtra("SUBTITLE_2");

            CITY_NAME  = i.getStringExtra("CITY_NAME");

            company_name_tv.setText(COMPANY_NAME);
            agent_name_tv.setText(AGENTNAME);

        } catch (Exception e) {
            e.printStackTrace();
        }


        collection_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }

                Intent i = new Intent(MainActivity.this, Collection_Activity.class);
                startActivity(i);

            }
        });
        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }

                Intent i = new Intent(MainActivity.this, Report_Activity.class);
                startActivity(i);
            }
        });

//        close_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isFABOpen){
//                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
//                    closeFABMenu();
//                }
//                goback();
//            }
//        });
        calendar = Calendar.getInstance();
        //calendar.add(Calendar.DATE, -13);

        strDate = mdformat.format(calendar.getTime());

        date_db = mdformat_db.format(calendar.getTime());

        RECEIPT_PREFIX = mdformat_receipt.format(calendar.getTime());

        Log.d("RECEIPT PREFIX =======================>",RECEIPT_PREFIX);
       // strDate = "17-10-2022";


        ShiftCode_df = mdformat1.format(calendar.getTime());
        // ShiftCode_df = "30092022";
        generate_code = ShiftCode_df+"A";
        baseAdapter = new DataBaseAdapter(MainActivity.this);
        selectedDate = mdformat.format(calendar.getTime());


        date_tv.setText(String.valueOf(strDate));

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //strDate ="";
                // openDatePickerDialog(v);
            }
        });


//        search_edittext.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                if(isFABOpen){
//                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
//                    closeFABMenu();
//                }
//
//                return false;
//            }
//        });

//        search_enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(search_edittext != null) {
//                    if (search_edittext.getText().toString().length()>0) {
//                        //toast("clicked");
//                        if(search_clear.getVisibility() == View.GONE) {
//                            search_clear.setVisibility(View.VISIBLE);
//                        }
//                        if(customer_Listview.getVisibility() == View.GONE) {
//                            customer_Listview.setVisibility(View.VISIBLE);
//                        }
//                      //  if(search_edittext.getText().toString().length()>=3) {
//                            new Search_Customer().execute(search_edittext.getText().toString().trim().toLowerCase());
//                      /*  }else{
//                            toast("Enter atleast three character");
//                        }*/
//                    }else{
//                        if(search_clear.getVisibility() == View.VISIBLE) {
//                            search_clear.setVisibility(View.GONE);
//                        }
//                        if(customer_Listview.getVisibility() == View.VISIBLE) {
//                            customer_Listview.setVisibility(View.GONE);
//                        }
//                    }
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//                }
//
//            }
//        });

//        search_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                search_edittext.setText(null);
//                if(search_clear.getVisibility() == View.VISIBLE){
//                    search_clear.setVisibility(View.GONE);
//                }
//                if(customer_Listview.getVisibility() == View.VISIBLE) {
//                    customer_Listview.setVisibility(View.GONE);
//                }
//                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    // fab.setBackgroundResource(R.drawable.ic_baseline_clear_24);
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_clear_24));
                    showFABMenu();
                } else {
                    // fab.setBackgroundResource(R.drawable.ic_baseline_add_24);
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Download", Toast.LENGTH_SHORT).show();

              //  new Download_DB().execute();
                File dbpath = context.getDatabasePath(DataBaseHelper.DB_NAME);
                ShareToAll_Above_Android7(MainActivity.this,getApplicationContext(),DataBaseHelper.DB_PATH+DataBaseHelper.DB_NAME,dbpath);

                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean network = isNetworkAvailable();
                if(network) {
                    Sync_transact();
                }else{
                   // Toast.makeText(MainActivity.this, "please check internet connection", Toast.LENGTH_SHORT).show();
                    toast("please check internet connection");
                }
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                  //  Customer_sync_Alert();
                    //new NEW_SYNC_MASTER_DB().execute();
                }else{
                    toast("Please check your internet connection");
                }
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /* Intent i = new Intent(MainActivity.this,Confirm_pin_activity.class);
                i.putExtra("PASSCODE",PASSCODE);
                startActivity(i);*/
                Popup_window();
                /*Toast.makeText(MainActivity.this, "Cash Close", Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance();
                strTime = mdtime.format(cal.getTime());
                Check_Shift();
                Cash_Close();*/
                //  new Async_master().execute();
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(master_details != null) {
                    new Async_New_Customer_Receipt(master_details.get(0).getAg_code(), 0).execute();
                    // Toast.makeText(getApplicationContext(), " open ", Toast.LENGTH_SHORT).show();
                }else{
                    //  Toast.makeText(getApplicationContext(), " unable to open ", Toast.LENGTH_SHORT).show();
                    toast("Unable to open");
                }
                if(isFABOpen){
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
                    closeFABMenu();
                }
            }
        });


        //Check_Shift();
        if(!AGENTNAME.equals("")){
            toast("Welcome "+ AGENTNAME +" !");
        }
        current_time = sdf.format(new Date());


        //new Delete_Timer().execute();

//        adapter = new Customer_Adapter(context, new ArrayList<String>(Arrays.asList("1","2","3","4")));
//        customer_Listview.setAdapter(adapter);

        memberListBaseAdapter = new MemberListBaseAdapter(context, stringArrayList);
        customer_Listview.setAdapter(memberListBaseAdapter);
    }



    public class MemberListBaseAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> myList;
        DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");

        public MemberListBaseAdapter(Context context, ArrayList<String> myList) {
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
        public String getItem(int position) {
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

                view = inflater.inflate(R.layout.main_card, parent, false);
                final ViewHolder holder = new ViewHolder();
                try {
                    //     holder.total_amount_tv = (EditText) view.findViewById(R.id.total_amount_tv);
                    holder.LL_card = (LinearLayout) view.findViewById(R.id.LL_card);
                    holder.card_view = (CardView) view.findViewById(R.id.card_view);

                } catch (Exception e) {
                    Log.i("Route", e.toString());

                }


                view.setTag(holder);


                //  holder.total_amount_tv.setText(String.valueOf(membersDetails.get(position).getGivenamount()));


//                holder.total_amount_tv.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                        if(editable != null){
//                            if(editable.length()>0) {
//
//                                Log.d("RECOVERY ACTIVITY ====("+String.valueOf(position+1)+")======(MEMBER :" + membersDetails.get(position).getMembername() + " )=====(TOTAL AMOUNT CHANGED)======>",String.valueOf(editable));
//
////                                if(Integer.parseInt(String.valueOf(editable))>Integer.parseInt(membersDetails.get(position).getOverall_loan_amount())){
////                                     mHolder.total_amount_tv.setText("");
////                                     Toast.makeText(context,"Recovery amount exceed loan amount to "+membersDetails.get(position).getMembername(),Toast.LENGTH_SHORT).show();
////                                }else {
//                                //  membersDetails.get(position).setEmiamount(String.valueOf(editable));
//
//
//                                membersDetails.get(position).setGivenamount(String.valueOf(editable));
//                                //  myList.get(position).setGivenamount(String.valueOf(editable));
////                                }
//
//
//
//                                // memberListBaseAdapter.notifyDataSetChanged();
//                                calculateTotalDueAmount();
//                            }
//                        }
//                    }
//                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        mHolder.LL_recovery.setBackgroundResource(R.color.card_select);
//                        mHolder.total_amount_tv.setBackgroundResource(R.drawable.amount_input_bg);

                        // Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_SHORT).show();


                        if(stringArrayList.get(position).equals("")) {

                            holder.LL_card.setBackgroundResource(R.color.select_green1);
                            stringArrayList.set(position,String.valueOf(position));

                        }else{

                            holder.LL_card.setBackgroundResource(R.color.white);
                            stringArrayList.set(position,"");
                        }

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
    public void LogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to logout ?")
                .setTitle(R.string.app_name)
                .setIcon(R.mipmap.icon)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                        MainActivity.this.finish();
                        startActivity(intent);
                        //.setIcon(R.mipmap.admin)
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


    private void Grand_Total() {
        double diff_val = 0;
        double cash_tot = 0;
        Grand_Total = Amount_2000+Amount_500+Amount_200+Amount_100+Amount_50+Amount_20+Amount_10+Amount_5+Amount_coin;
        try {
            cash_tot = Double.parseDouble(total_cash_label.getText().toString());
            diff_val = cash_tot - Double.parseDouble(String.valueOf(Grand_Total));
        } catch (Exception e) {
            e.printStackTrace();
        }
        grand_total.setText(String.valueOf(dft.format(Grand_Total)));
        diff_amount.setText(String.valueOf(dft.format(diff_val)));
    }

    public int Calculate_Currency(int amount,int count) {

        int tot_val = amount*count;

        return tot_val;
    }

    public  void Cash_Close(){
         Cursor cursor;
         //Check_Shift();
         baseAdapter.open();
         cursor = baseAdapter.Select_Shift(String.valueOf(generate_code));
         if (cursor != null) {
             if(cursor.getCount()==0){
                 // title_tv.setText(generate_code);
                 char temp_name = generate_code.charAt(generate_code.length()-1);
                 String success =  baseAdapter.insert_shift(String.valueOf(temp_name),generate_code,strDate,strTime,Total_colectamt,Amount_2000,ct_2000,Amount_500,ct_500,Amount_200,ct_200,Amount_100,ct_100,Amount_50,ct_50,Amount_20,ct_20,Amount_10,ct_10,Amount_5,ct_5,Coin_count,Grand_Total);
                 if(success.equals("success")){
                     Bank_colectamt=0;
                     Total_colectamt=0;
                     Amount_2000=0;
                     ct_2000=0;
                     Amount_500=0;
                     ct_500=0;
                     Amount_200=0;
                     ct_200 =0;
                     Amount_100 =0;
                     ct_100 =0;
                     Amount_50 =0;
                     ct_50=0;
                     Amount_20=0;
                     ct_20=0;
                     Amount_10=0;
                     ct_10 =0;
                     Amount_5=0;
                     ct_5=0;
                     Coin_count=0;
                     Grand_Total=0;

                     Log.d("SHIFT_TABLE FIRST_INSERTION ===============>",generate_code+" "+"CASH CLOSED"+" TIME:"+strTime);
                 }
             }
             if(cursor.getCount()>0) {
                 char temp_name = cursor.getString(1).charAt(0);
                 int Value = (int)temp_name + 1;
                 char c = (char)Value;
                 String success =  baseAdapter.insert_shift(String.valueOf(c),generate_code,strDate,strTime,Total_colectamt,Amount_2000,ct_2000,Amount_500,ct_500,Amount_200,ct_200,Amount_100,ct_100,Amount_50,ct_50,Amount_20,ct_20,Amount_10,ct_10,Amount_5,ct_5,Coin_count,Grand_Total);
                 if(success.equals("success")){

                     Bank_colectamt=0;
                     Total_colectamt=0;
                     Amount_2000=0;
                     ct_2000=0;
                     Amount_500=0;
                     ct_500=0;
                     Amount_200=0;
                     ct_200 =0;
                     Amount_100 =0;
                     ct_100 =0;
                     Amount_50 =0;
                     ct_50=0;
                     Amount_20=0;
                     ct_20=0;
                     Amount_10=0;
                     ct_10 =0;
                     Amount_5=0;
                     ct_5=0;
                     Coin_count=0;
                     Grand_Total=0;

                     Log.d("SHIFT_TABLE INSERTION ===============>",generate_code+" "+"CASH CLOSED "+" TIME:"+strTime);
                 }
                 //title_tv.setText(generate_code);
                 // Log.d("SHIFT_TABLE INSERTION ===============>",generate_code);
             }
         }

        new Async_cashclose().execute();

         Set_Shift_code();

     }

    public void Check_Shift() {

        Cursor cursor;
        baseAdapter.open();

        cursor = baseAdapter.Select_Shift(String.valueOf(generate_code));
        if (cursor != null) {
            if(cursor.getCount()==0){
                // title_tv.setText(generate_code);
                Log.d("SHIFT_TABLE SHIFTCODE ===============>",generate_code);
            }
            if(cursor.getCount()>0) {
                char temp_name = cursor.getString(1).charAt(0);
                int Value = (int)temp_name + 1;
                char c = (char)Value;
                generate_code = ShiftCode_df+(c);
                //title_tv.setText(generate_code);
                Log.d("SHIFT_TABLE INSERTABLE_SHIFTCODE ===============>",generate_code);
            }
        }
        baseAdapter.close();
    }

    private void Set_Shift_code() {

        Cursor cursor;
        baseAdapter.open();

        cursor = baseAdapter.Last_Shift(null);
        if (cursor != null) {

            if(cursor.getCount()>0) {
                char temp_name = cursor.getString(1).charAt(0);
                int Value = (int)temp_name + 1;
                char c = (char)Value;

                if(ShiftCode_df.equals(String.valueOf(cursor.getString(2).substring(0,8)))){
                    generate_code = ShiftCode_df+(c);
                    Log.d("GENERATED CODE IMCREAMENTED===============>",generate_code);
                }else{
                    Log.d("GENERATED CODE BY DEFAULT===============>",generate_code);
                }

            }
        }
        baseAdapter.close();
    }


    private void Sync_transact() {

        new  Sync_Services().execute();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.mode_of_payment:
                mode_of_payment_txt.setError(null);
                txt_cheque_no.setError(null);
                txt_cheque_date.setError(null);
                amount.setError(null);
               // Toast.makeText(context, payment_code_arraylist.get(i), Toast.LENGTH_SHORT).show();
                payment_code =String.valueOf(payment_code_arraylist.get(i));
                payment_name =String.valueOf(payment_arraylist.get(i));
               /* if(i==1){
                   if(cheque_layout.getVisibility()==View.GONE){
                       cheque_layout.setVisibility(View.VISIBLE);
                   }
                }else{
                    if(cheque_layout.getVisibility()==View.VISIBLE){
                        cheque_layout.setVisibility(View.GONE);
                    }
                }*/
                break;
            case R.id. mode_of_payment_unk:
                payment_code =String.valueOf(payment_code_arraylist.get(i));
                payment_name =String.valueOf(payment_arraylist.get(i));
                mode_of_payment_txt.setError(null);
                break;
        }
        if (adapterView.getId() == R.id.customer_type) {
            customer_type_txt.setError(null);
            amount.setError(null);
         //   Toast.makeText(getApplicationContext(), String.valueOf(customer_code_arraylist.get(i)), Toast.LENGTH_SHORT).show();
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

    public class Load_Shift_Records extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(MainActivity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select_Transact_Cutomer(generate_code,"null");
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            report_shift_details.clear();
            arrayList.clear();
            arrayList_bank.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (mCur.getCount() > 0) {
                // shift_details.clear();
                String status;

                for (int i = 0; i < mCur.getCount(); i++) {
                    status ="";
                    //  char s_name = mCur.getString(0).charAt(8);
                    // shift_details.add(new Report_Activity.shift_details(String.valueOf(s_name),mCur.getString(0),mCur.getString(1),mCur.getString(2)));
                    report_shift_details.add(new report_shift_details(String.valueOf(i),mCur.getString(0),mCur.getString(1),mCur.getString(2),
                            mCur.getString(3),mCur.getString(4),mCur.getString(5),mCur.getString(6),mCur.getString(7),mCur.getInt(8),mCur.getInt(10),mCur.getString(11),mCur.getString(12),mCur.getString(13),mCur.getString(14),mCur.getString(15),mCur.getString(16),mCur.getString(17),"",""));
                    Log.d("TRANSACTION_TABLE_RECORD ======ROW("+i+")=========>",mCur.getString(6));
                    mCur.moveToNext();
                }
            } else {

                Log.d("TRANSACTION_TABLE_RECORD ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
            if(report_shift_details !=null) {
                arrayList.clear();
                arrayList_bank.clear();

                for(int i=0;i<report_shift_details.size();i++){
                    if(report_shift_details.get(i).getModepay().equals("CASH")) {
                        arrayList.add(report_shift_details.get(i));
                        Log.d("TRANSACTION_TABLE_DENOMINATION =======(CASH)========>",report_shift_details.get(i).getCollectamt());
                    }

                    if(report_shift_details.get(i).getModepay().equals("BANK TRANSFER")) {
                        arrayList_bank.add(report_shift_details.get(i));
                        Log.d("TRANSACTION_TABLE_DENOMINATION =========(BANK TRANSFER)======>",report_shift_details.get(i).getCollectamt());
                    }

                }
               // if(arrayList != null){
                    calculateAreaWiseTotal();
              //  }
                Denomination_popup();
            }
        }

    }

    public void calculateAreaWiseTotal(){
        double totalamt=0;
         if(report_shift_details != null) {
             Total_collection =0;
             for (int i = 0; i < report_shift_details.size(); i++) {
                 if (report_shift_details.get(i).getCancel().equals("N")) {
                     Double amt = Double.parseDouble(report_shift_details.get(i).getCollectamt());
                     Total_collection = Total_collection + amt;
                     Log.d("TRANSACTION_TABLE_TOTAL_COLLECTION ===============>", String.valueOf(Total_collection));
                 }
             }
         }
         if(arrayList != null) {
             Total_colectamt =0;
             for (int i = 0; i < arrayList.size(); i++) {
                 if (arrayList.get(i).getCancel().equals("N")) {
                     Double amt = Double.parseDouble(arrayList.get(i).getCollectamt());
                     Total_colectamt = Total_colectamt + amt;
                     Log.d("TRANSACTION_TABLE_Total_colectamt ===============>", String.valueOf(Total_colectamt));
                 }
             }
         }
         if(arrayList_bank != null) {
             Bank_colectamt =0;
             for (int i = 0; i < arrayList_bank.size(); i++) {
                 if (arrayList_bank.get(i).getCancel().equals("N")) {
                     Double amt = Double.parseDouble(arrayList_bank.get(i).getCollectamt());
                     Bank_colectamt = Bank_colectamt + amt;
                     Log.d("TRANSACTION_TABLE_Bank_colectamt ===============>", String.valueOf(Bank_colectamt));
                 }
             }
         }

        total_cash_label.setText(String.valueOf(" "+dft.format(Total_colectamt)));
        bank_colectamt_label.setText(String.valueOf(" "+dft.format(Bank_colectamt)));
        total_collection_label.setText(String.valueOf(" "+dft.format(Total_collection)));
    }

    private void showFABMenu() {
        isFABOpen = true;

        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
        fab5.animate().translationY(-getResources().getDimension(R.dimen.standard_255));
    }

    private void closeFABMenu() {
        isFABOpen = false;

        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);
    }


    @Override
    public void onBackPressed() {
        if(isFABOpen){
            fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
            closeFABMenu();
        }
        exit();
    }

    public void goback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to logout ?")
                .setTitle("Collect Buddy")
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent logout = new Intent(MainActivity.this, Login_Activity.class);
                        MainActivity.this.finish();
                        startActivity(logout);
                        //.setIcon(R.mipmap.admin)
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

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to exit ?")
                .setTitle("Collect Buddy")
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        MainActivity.this.onResume();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void get_agcode() {
      DataBaseAdapter Adapter = new DataBaseAdapter(MainActivity.this);

        Cursor cursor;
        Adapter.open();

        cursor = Adapter.Select_Login();
        if (cursor != null) {
            if(cursor.getCount()==0){
                // title_tv.setText(generate_code);

                Log.d("LOGIN TABLE EMPTY ===============>","null");
            }
            if(cursor.getCount()>0) {

                 AG_CODE = cursor.getString(2);
                //title_tv.setText(generate_code);
                Log.d("LOGIN TABLE DATA ======(AGENT_CODE)=========>",AG_CODE);
            }
        }
        Adapter.close();
    }

    public void sync_Login() {

        DataBaseAdapter objdatabaseadapter = null;
        objdatabaseadapter = new DataBaseAdapter(getApplicationContext());
        objdatabaseadapter.open();
        try {
            //   String success = objdatabaseadapter.insert("manikandan","1","1111","1","0","0","1","f22d1103dcce2f11","1111");
            // String success = objdatabaseadapter.insert("null", "null", "null", "null", "null", "null", "null", "null", "null");

            String success = objdatabaseadapter.insert("","","manikandan", "1", "1111", "1", "0", "0", "1", "f22d1103dcce2f11", "1111","null");


            if (success.equals("success")) {
                //GetServerURLList();
                // edittxtip.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                // Check_Login_DB();
            }

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error in saving", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } finally {
            if (objdatabaseadapter != null)
                objdatabaseadapter.close();
        }
    }


    public interface Refreshpage {
        void refreshpage();
    }



    public void sync_master_DB(String accountno, String name, String phoneno, String address1, String address2,
                               String lasttransdt, String actype, String amount, String multiplicationamt,
                               String limitamt, String insamt, String duedt, String ag_code, String ag_name,
                               String optcl, String minamt, String multiamtlimit, String period, String interestamt, String totalamt,
                               String penaltyamt, String interestpaid, String interestdue, String loanbaldue, String rate, String pendingpenalty,
                               String colinterest, String Totinsttno, String Paidinstno, String EnableInst, String Doorno, String StreetName,
                               String DownlaodFlag, String CreatedOn, String Remark, String EmiNumber, String CollectionTypeID, String branch_id,
                               String Branch_code, String DDCode, String InstPending, String FineAmount, String DailyAmount, String NoticeAmount,
                               String AmountinSus, String IsLoan,String city_name,String city_code,String status_code,String balance_amount,String maturity_amount,String billno,String billdate,int position) {

        DataBaseAdapter objdatabaseadapter = null;
        objdatabaseadapter = new DataBaseAdapter(getApplicationContext());
        objdatabaseadapter.open();

        try {

            String success = objdatabaseadapter.insert_master(accountno, name, phoneno, address1, address2, lasttransdt, actype, amount,
                    multiplicationamt, limitamt, insamt, duedt, ag_code, ag_name, optcl, minamt, multiamtlimit, period, interestamt, totalamt,
                    penaltyamt, interestpaid, interestdue, loanbaldue, rate, pendingpenalty, colinterest, Totinsttno, Paidinstno, EnableInst, Doorno,
                    StreetName, DownlaodFlag, CreatedOn, Remark, EmiNumber, CollectionTypeID, branch_id, Branch_code,
                    DDCode, InstPending, FineAmount, DailyAmount, NoticeAmount, AmountinSus, IsLoan,city_code,city_name,status_code,balance_amount, maturity_amount,billno,billdate);


            if (success.equals("success")) {
                //GetServerURLList();
                // edittxtip.setText("");
              //  Toast toast = Toast.makeText(getApplicationContext(), "Saved Successfully ", Toast.LENGTH_SHORT);
                // toast.setGravity(Gravity.CENTER, 0, 0);
                // toast.show();
                // Check_Login_DB();
                Log.d("MASTER_SYNC_DETAIL=====Row("+String.valueOf(position)+")=======>","success" +"===STATUS CODE : "+status_code);
            }

        } catch (Exception e) {
            // Toast toast = Toast.makeText(getApplicationContext(), "Error in saving", Toast.LENGTH_SHORT);
            //  toast.setGravity(Gravity.CENTER, 0, 0);
            //  toast.show();
        } finally {
            if (objdatabaseadapter != null)
                objdatabaseadapter.close();
        }
    }

    public class Async_cashclose extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        String  success="";
        DataBaseAdapter base_Adapter1  = new DataBaseAdapter(MainActivity.this);

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            base_Adapter1.open();
            success = base_Adapter1.Cash_Close(strTime);
            base_Adapter1.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // customer_name.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            //  passcode = new String[0];
            //  login_status = new String[0];
            if (success.equals("success")) {
                String status;

                Log.d("TRANSACTION TABLE DATA ===============>","CASH CLOSED");
                denomination_popup.dismiss();
            } else {

                Log.d("TRANSACTION TABLE DATA ===============>","UNABLE TO CLOSE");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public class Search_Customer extends
            AsyncTask<String, JSONObject, Boolean> {
        DataBaseAdapter base;
        Customer_Adapter adapter;
        Cursor mcur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {

            mcur = base.Search_Name(params[0]);

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            search_array.clear();
            base = new DataBaseAdapter(MainActivity.this);
            base.open();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(mcur != null){
                if(mcur.getCount()>0){
                    search_array.clear();
                    for(int i = 0; i< mcur.getCount(); i++) {
                        search_array.add(new master_details(mcur.getString(1), mcur.getString(2), mcur.getString(3), mcur.getString(4), mcur.getString(5), mcur.getString(6),
                                mcur.getString(7), mcur.getString(8), mcur.getString(9), mcur.getString(10), mcur.getString(11), mcur.getString(12), mcur.getString(14), mcur.getString(14), mcur.getString(15),
                                mcur.getString(16), mcur.getString(17), mcur.getString(18), mcur.getString(19), mcur.getString(20), mcur.getString(21), mcur.getString(22), mcur.getString(23),
                                mcur.getString(24), mcur.getString(25), mcur.getString(26), mcur.getString(27), mcur.getString(28), mcur.getString(29), mcur.getString(30), mcur.getString(31), mcur.getString(32),
                                mcur.getString(33), mcur.getString(34), mcur.getString(35), mcur.getString(36), mcur.getString(37), mcur.getString(38), mcur.getString(39), mcur.getString(40), mcur.getString(41), mcur.getString(42),
                                mcur.getString(43), mcur.getString(44), mcur.getString(45), mcur.getString(46), mcur.getString(48), mcur.getString(49), mcur.getString(50), mcur.getString(51), mcur.getString(52), mcur.getString(53), mcur.getString(54)));
                        Log.d("MASTER_DATA =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(mcur.getString(2)));
                        mcur.moveToNext();
                    }
                }
            }

            if(search_array !=null) {
                customer_Listview.setAdapter(null);
                adapter = new Customer_Adapter(context, null);
                customer_Listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{

            }
            base.close();
        }
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

            agent_name = new String[0];
            if (mCur != null) {
                sync_details.clear();
                if (mCur.getCount() > 0) {
                    agent_name = new String[mCur.getCount()];
                    passcode = new String[mCur.getCount()];
                    login_status = new String[mCur.getCount()];
                    for (int i = 0; i < mCur.getCount(); i++) {
                        agent_name[i] = mCur.getString(0);
                        passcode[i] = mCur.getString(1);
                        login_status[i] = mCur.getString(2);
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
                                //  if(objRestAPI.Async_DB_Services(String.valueOf(mCur.getString(2)),String.valueOf( mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), cancel,String.valueOf( mCur.getString(9)),"", "", outputDateStr, String.valueOf(mCur.getString(13)),String.valueOf( mCur.getString(14)), String.valueOf(mCur.getString(20)),mCur.getInt(21),mCur.getString(22),mCur.getString(23),mCur.getString(24),mCur.getString(25)) != null) {

                                      success = objRestAPI.Async_DB_Services(String.valueOf(mCur.getString(2)), String.valueOf(mCur.getString(3)), String.valueOf(mCur.getString(4)), String.valueOf(mCur.getString(5)), String.valueOf(mCur.getString(6)), cancel, String.valueOf(mCur.getString(9)), "", "", outputDateStr, String.valueOf(mCur.getString(13)), String.valueOf(mCur.getString(14)), String.valueOf(mCur.getString(20)), mCur.getInt(21), mCur.getString(22), mCur.getString(23), mCur.getString(24), mCur.getString(25)).getInt("success");
                                      Log.d("INSERTED =======ROW(" + String.valueOf(i) + ")========>", String.valueOf("UPDATED   " + mCur.getString(12)) + "   " + outputDateStr);

                                //  }else{
                                 //     Log.d("MAIN ACTIVITY  ===============>","API RETURNS NULL JSON OBJECT");
                                 // }
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

                            //Sleep will suspend your Thread for 500 miliseconds and resumes afterwards
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

    public class Download_DB extends
            AsyncTask<String, JSONObject, Boolean> {
        DataBaseAdapter base_Adapter;
        String success;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {
            base_Adapter = new DataBaseAdapter(getApplicationContext());
            base_Adapter.open();
            success = base_Adapter.Download_DB();
            base_Adapter.close();
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }


    void Popup_window(){

        ImageView close = (ImageView) verify_popup.findViewById(R.id.closepopup);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin_code.setText("");
                verify_popup.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin="";
                pin = pin_code.getText().toString();
                try {
                    if(sha256(pin).equals(PASSCODE)){
                        pin_code.setText("");
                        verify_popup.dismiss();

                     // Denomination_popup();
                      new Load_Shift_Records().execute();
                    }else{
                        pin_code.setText("");
                        if(wrong_pin_tv.getVisibility() == View.GONE) {
                            wrong_pin_tv.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        verify_popup.show();
    }

    private void Denomination_popup() {
        ImageView close = (ImageView) denomination_popup.findViewById(R.id.closepopup);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denomination_popup.dismiss();
            }
        });

        btncash_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Cash Close", Toast.LENGTH_SHORT).show();
                toast("Cash Closed");
                Calendar cal = Calendar.getInstance();
                strTime = mdtime.format(cal.getTime());
                Check_Shift();
                Cash_Close();
            }
        });
        denomination_popup.show();
    }

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

    public void toast(String message)
    {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public class Customer_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<String> customer_details;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Customer_Adapter(Context context,ArrayList<String> customer_details){
            this.context=context;
            this.customer_details = customer_details;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return customer_details.size();
        }

        @Override
        public Object getItem(int position) {
            return customer_details.get(position);
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
                view = layoutInflater.inflate(R.layout.main_card, viewGroup, false);
                viewHolder = new ViewHolder();

             //   viewHolder.customer_name = (TextView) view.findViewById(R.id.customer_name);
              //  viewHolder.area_name =(TextView) view.findViewById(R.id.area_name)

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }



            return view;
        }
    }
    public class Async_Get_Receipt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor cursor;
        String ag_code;
        int pos;

        public Async_Get_Receipt(String ag_code,int position) {
            this.ag_code =ag_code;
            this.pos = position;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(context);
            baseAdapter.open();
            cursor = baseAdapter.Get_Receipt(ag_code);

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // customer_name.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            baseAdapter.close();
            String receiptcode = "",receiptno="",collectdt="";
            //  passcode = new String[0];
            //  login_status = new String[0];
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                    for(int i=0;i<cursor.getCount();i++){
                        receiptcode = cursor.getString(0);
                        receiptno  = cursor.getString(1);
                        collectdt  = cursor.getString(2);
                        //   cursor.moveToNext();
                    }
                    Log.d("TRANSACTION TABLE RECEIPT NO ===============>", String.valueOf(receiptno+"======="+receiptcode+"====="+collectdt));
                } else {

                    Log.d("TRANSACTION TABLE RECEIPT NO ===============>", " RECEIPT COLUMN EMPTY ");
                    // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                }

            }
            if(!receiptcode.equals("")){
                openEntryPopup(pos,receiptno,receiptcode,collectdt);
            }else{
                Toast.makeText(context, " unable to open payment dailog ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Async_New_Customer_Receipt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor cursor;
        DataBaseAdapter base_Adapter;
        String ag_code;
        int pos;

        public Async_New_Customer_Receipt(String agcode,int position) {
            this.ag_code =agcode;
            this.pos = position;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            base_Adapter = new DataBaseAdapter(MainActivity.this);
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
                NewCustomerEntryPopup(ag_code,receiptno,receiptcode,collectdt,customerno_max);
            }else{
                Toast.makeText(getApplicationContext(), " unable to open payment dailog ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openEntryPopup(int pos,String receiptno,String receiptcode,String collectdt) {
        entry_dialog = new Dialog(context);
        entry_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        entry_dialog.setContentView(R.layout.customer_entry_popup);
        entry_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ImageView closepopup = (ImageView) entry_dialog.findViewById(R.id.closepopup);
        mode_of_payment = (Spinner)entry_dialog.findViewById(R.id.mode_of_payment);
        mode_of_payment_txt = (TextView) entry_dialog.findViewById(R.id.mode_of_payment_txt);
        receipt_number = (TextView) entry_dialog.findViewById(R.id.receipt_number);
        paid_amt = (TextView) entry_dialog.findViewById(R.id.paid_amt);
        payable_amt = (TextView) entry_dialog.findViewById(R.id.payable_amt);
        balance_amt = (TextView) entry_dialog.findViewById(R.id.balance_amt);

        txt_cus_name = (TextView) entry_dialog.findViewById(R.id.txt_cus_name);
        txt_acc_no = (TextView) entry_dialog.findViewById(R.id.txt_acc_no);
        txt_ac_type = (TextView) entry_dialog.findViewById(R.id.txt_ac_type);
        txt_address_line1 = (TextView) entry_dialog.findViewById(R.id.txt_address_line1);
        txt_address_line2 = (TextView) entry_dialog.findViewById(R.id.txt_address_line2);
        txt_cityname = (TextView) entry_dialog.findViewById(R.id.txt_cityname);
        txt_mobno  = (TextView) entry_dialog.findViewById(R.id.txt_mobno);
        txt_mode = (TextView) entry_dialog.findViewById(R.id.txt_mode);

        cheque_layout = (LinearLayout)entry_dialog.findViewById(R.id.cheque_layout);
        txt_cheque_date = (EditText) entry_dialog.findViewById(R.id.txt_cheque_date);
        txt_cheque_no =(EditText) entry_dialog.findViewById(R.id.txt_cheque_no);
        amount = (EditText) entry_dialog.findViewById(R.id.amount);
        remarks = (EditText) entry_dialog.findViewById(R.id.remarks);
        btnSave = (Button)entry_dialog.findViewById(R.id.btnSave);
        mode_of_payment.setOnItemSelectedListener(this);

        Cheque_date="";
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
        context.setTheme(R.style.DialogTheme);
        payment_arrayAdapter  = new ArrayAdapter(context, android.R.layout.simple_spinner_item, payment_arraylist) {
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

        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_dialog.dismiss();
            }
        });
        txt_cheque_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_cheque_date.setError(null);
                Cheque_date="";
                //openDatePickerDialog(view);
            }
        });

        if(!search_array.get(pos).getName().equals("null")) {
            txt_cus_name.setText(String.valueOf(search_array.get(pos).getName()));
        }

        if(!search_array.get(pos).getAccountno().equals("null")) {
            txt_acc_no.setText(String.valueOf(search_array.get(pos).getAccountno()));
        }

        if(!search_array.get(pos).getActype().equals("null")) {
            txt_ac_type.setText(String.valueOf(search_array.get(pos).getActype()));
        }

        if(!search_array.get(pos).getAddress1().equals("null")) {
            txt_address_line1.setText(String.valueOf(search_array.get(pos).getAddress1()));
        }
        if(!search_array.get(pos).getAddress2().equals("null")) {
            txt_address_line2.setText(String.valueOf(search_array.get(pos).getAddress2()));
        }
        if(!search_array.get(pos).getPhoneno().equals("null")){
            txt_mobno.setText(String.valueOf(search_array.get(pos).getPhoneno()));
        }
        txt_cityname.setText(" - ");
        txt_mode.setText("CASH");
        if(!search_array.get(pos).getCity_name().equals("null")) {
            txt_cityname.setText(String.valueOf(search_array.get(pos).getCity_name()));
        }
        if(!search_array.get(pos).getActype().equals("null") ){

            payable_amt.setText(String.valueOf("₹ "+money_sdf.format(Double.parseDouble(splitString(search_array.get(pos).getActype())))));
            amount.setText(String.valueOf(money_sdf.format(Double.parseDouble(splitString(search_array.get(pos).getActype())))));
            Log.d("TRYING TO SPLIT NUMBER FROM STRING ====================>",splitString(search_array.get(pos).getActype()));
        }
        if(!search_array.get(pos).getAmount().equals("null")){
            paid_amt.setText(String.valueOf("₹ "+search_array.get(pos).getAmount()));
        }
        if(!search_array.get(pos).getAmount().equals("null") && !search_array.get(pos).getActype().equals("null") ) {
            // set_balance_amt(String.valueOf(customer_active_details.get(pos).getAmount()), String.valueOf(customer_active_details.get(pos).getActype().substring(1)));

            set_balance_amt(String.valueOf(search_array.get(pos).getBalance_amount()), String.valueOf(splitString(search_array.get(pos).getActype())));
        }else{
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }
        if(search_array.get(pos).getMaturity_amount() != null && !search_array.get(pos).getMaturity_amount().equals("null") && !search_array.get(pos).getMaturity_amount().equals("")){
            Maturity_amount = search_array.get(pos).getMaturity_amount();

        }
        if(!search_array.get(pos).getAmount().equals("null") && !search_array.get(pos).getAmount().equals("0")){
            PRVS_check = search_array.get(pos).getAmount();
        }

        // Amount = amount.getText().toString();

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()>0){
                    if(!Maturity_amount.equals("") && !Maturity_amount.equals("0")) {
                        if (Double.parseDouble(editable.toString()) > Double.parseDouble(Maturity_amount)) {
                            //   Toast.makeText(context, "Exceed maximum amount", Toast.LENGTH_SHORT).show();
                            amount.setText("");
                            Amount = "0";
                        }
                    }
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strTime = mdtime.format(calendar.getTime());

                try {

                    //Insert_Transation_DB("","","","","","",
                    //        "","","","","","","","");
                    if (amount != null) {
                        Amount = amount.getText().toString();
                    }
                    if (txt_cheque_no != null) {
                        Cheque_no = txt_cheque_no.getText().toString();
                    }
                    if (payment_code.equals("0")) {
                        mode_of_payment_txt.setError("Select payment mode");
                    }
                    if (Amount.equals("0") || Amount.equals("")) {
                        amount.setError("Enter the amount");
                    }

                    if (Cheque_date.equals("")) {
                        txt_cheque_date.setError("Select cheque date");
                    }
                    if (Cheque_no.equals("0") || Cheque_no.equals("")) {
                        txt_cheque_no.setError("Enter the cheque number");
                    }
                    if (remarks != null) {
                        Remarks = remarks.getText().toString();
                    }
                    double check = Double.parseDouble(PRVS_check) + Double.parseDouble(Amount);
                 if(!Maturity_amount.equals("0")) {
                     if (check > Double.parseDouble(Maturity_amount)) {
                         //Toast.makeText(context, "Exceed maximum limit", Toast.LENGTH_SHORT).show();
                         toast("Exceed maximum limit");
                         Amount = "0";
                     }
                 }

                    if (payment_code.equals("1")) {
                        if (!Amount.equals("0") && !Amount.equals("")) {
                            // Toast.makeText(context, " Cash payment done", Toast.LENGTH_SHORT).show();
                            //Insert_Transation_DB("","","","","","",
                            //     "","","","","","","","");
                            toast("Payment done");
                            Insert_Transation_DB("", search_array.get(pos).getAccountno(), receiptno, receiptcode, search_array.get(pos).getAg_code(), search_array.get(pos).getActype(), "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Remarks, "", search_array.get(pos).getName(), search_array.get(pos).getPhoneno(), search_array.get(pos).getCity_name(), search_array.get(pos).getAmount());
                        } else {

                        }
                    }

            /*   if(payment_code.equals("2")) {
                   if (!Amount.equals("0") && !Cheque_date.equals("") && !Cheque_no.equals("0") && !Cheque_no.equals("")) {
                       // Toast.makeText(context, "Cheque payment done", Toast.LENGTH_SHORT).show();
                        toast("Payment done");
                        Insert_Transation_DB("",customer_active_details.get(pos).getAccountno(),receiptno,receiptcode,customer_active_details.get(pos).getAg_code(),customer_active_details.get(pos).getActype(),"CHEQUE",
                                "","N",current_time,Cheque_date,Cheque_no,strDate,Amount,Remarks,"");
                    }else{

                    }
                }*/

                    if (payment_code.equals("3")) {
                        if (!Amount.equals("0") && !Amount.equals("")) {
                            // Toast.makeText(context, "UPI payment done", Toast.LENGTH_SHORT).show();
                            toast("Payment done");
                            //Insert_Transation_DB("","","","","","",
                            //     "","","","","","","","");
                            Insert_Transation_DB("", search_array.get(pos).getAccountno(), receiptno, receiptcode, search_array.get(pos).getAg_code(), search_array.get(pos).getActype(), "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Remarks, "", search_array.get(pos).getName(), search_array.get(pos).getPhoneno(), search_array.get(pos).getCity_name(), search_array.get(pos).getAmount());
                        } else {

                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        entry_dialog.show();
    }

    public void NewCustomerEntryPopup(String ag_code,String receiptno,String receiptcode,String collectdt,String customerno) {
        entry_dialog = new Dialog(MainActivity.this);
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

        mode_of_payment_txt = (TextView) entry_dialog.findViewById(R.id.mode_of_payment_txt);
        mode_of_payment  = (Spinner) entry_dialog.findViewById(R.id.mode_of_payment_unk);
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
     //   customer_name_arraylist.add("Select Customer Type");
        customer_name_arraylist.add("Unknown Customer");
        customer_name_arraylist.add("New Customer");

        customer_code_arraylist.clear();
       // customer_code_arraylist.add("0");
        customer_code_arraylist.add("2");
        customer_code_arraylist.add("3");

        // mode_of_payment.setSelection(0);

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
        context.setTheme(R.style.DialogTheme);
        payment_arrayAdapter  = new ArrayAdapter(context, android.R.layout.simple_spinner_item, payment_arraylist) {
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

        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_dialog.dismiss();
            }
        });


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
                if (payment_code.equals("0")) {
                    mode_of_payment_txt.setError("Select payment mode");
                }
                if(customer_code.equals("2")) {
                    if (!Amount.equals("0") && !Amount.equals("") ) {
                        //  Toast.makeText(getApplicationContext(), "Payment done", Toast.LENGTH_SHORT).show();
                        if(payment_code.equals("1")) {
                            New_Customer_Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 2, Name, Phoneno, City, customerno);
                        }
                        if(payment_code.equals("3")) {
                            New_Customer_Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 2, Name, Phoneno, City, customerno);
                        }
                    } else {

                    }
                }

                if(customer_code.equals("3")) {
                    if(!Amount.equals("0") && !Amount.equals("") && !Name.equals("") && !Phoneno.equals("") && !City.equals("")) {

                        // Toast.makeText(getApplicationContext(), " payment done", Toast.LENGTH_SHORT).show();
                        if (payment_code.equals("1")) {
                            New_Customer_Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 3, Name, Phoneno, City, String.valueOf(0));
                        }
                        if (payment_code.equals("3")) {
                            New_Customer_Insert_Transation_DB("", "", receiptno, receiptcode, ag_code, "", "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Description, "", 3, Name, Phoneno, City, String.valueOf(0));
                        }
                    }
                }
            }
        });
        entry_dialog.show();
    }


    public void Closed_Customer_Popup(String receiptno,String receiptdate) {
            TextView receipt_no_tv,receipt_date_tv ;
            Button btnOk ;
        closed_dialog = new Dialog(MainActivity.this);
        closed_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        closed_dialog.setContentView(R.layout.closed_customer_popup);
        closed_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        receipt_no_tv = (TextView) closed_dialog.findViewById(R.id.receipt_no);
        receipt_date_tv = (TextView) closed_dialog.findViewById(R.id.receipt_date);
        btnOk = (Button) closed_dialog.findViewById(R.id.btnOk);
       try {
           if(!receiptdate.equals("null")) {
               JSONObject jsonObject = new JSONObject(receiptdate);
               String temp_date = jsonObject.getString("date");

               DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
               DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
               Date date = null;
               date = inputFormat.parse(temp_date.substring(0,10));

               receipt_date_tv.setText(String.valueOf(outputFormat.format(date)));
           }
           if(!receiptno.equals("null")){
               receipt_no_tv.setText(receiptno);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closed_dialog.dismiss();
            }
        });

        closed_dialog.show();
    }

    private void set_balance_amt(String amt,String payable_amt) {
        double tot=0;
        if(!amt.equals("null") && !payable_amt.equals("null")){
            tot = Double.parseDouble(amt)-Double.parseDouble(payable_amt);
            balance_amt.setText(String.valueOf("₹ "+tot));
        }else{
            balance_amt.setText("-");
        }
        //String  split = ac_type.substring(1);
    }

    private void Insert_Transation_DB(String sno, String acno, String receiptno,String receiptcode, String ag_code, String actype, String modepay, String palmtec_id,
                                      String cancel, String tr_time,String chequedt,String chequeno,String collectdt,String collectamt,String remark,String shift_code,String name,String phoneno,String city,String paid_amt) {
        int sno_temp,receipt_max;
        baseAdapter.open();

        Cursor cursor = baseAdapter.Select_Transact();
        if (cursor != null) {

            if (cursor.getCount() > 0) {
                sno_temp = cursor.getCount();
                cursor.moveToLast();
                //receipt_max = Integer.parseInt(cursor.getString(3))+1;

                String success = baseAdapter.insert_transact(String.valueOf(sno_temp+1),acno, receiptno,Integer.parseInt(receiptcode),ag_code,actype,modepay, palmtec_id,
                        cancel,tr_time,chequedt,chequeno,collectdt,collectamt,remark,generate_code,strDate,"",0,0,1,"","","",0);

                if(success.equals("success")){
                    //Toast.makeText(context, " success ", Toast.LENGTH_SHORT).show();
                    Log.d("TRANSACT_TABLE_INSERTION===============>",String.valueOf("completed"));
                    if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                        Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, paid_amt, collectamt, 1);
                        Log.d("MAIN ACTIVITY =========(AMOUNT IN WORDS)=========>",convertIntoWords(Double.parseDouble(paid_amt),"en","US"));
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
                        cancel,tr_time,chequedt,chequeno,collectdt,collectamt,remark,generate_code,strDate,"",0,0,1,"","","",0);

                if(success.equals("success")){
                    //  Toast.makeText(context, " success ", Toast.LENGTH_SHORT).show();
                    Log.d("TRANSACT_TABLE_INSERTION===============>","completed");
                    if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {

                        Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, paid_amt, collectamt, 1);
                        Log.d("MAIN ACTIVITY =========(AMOUNT IN WORDS)=========>",convertIntoWords(Double.parseDouble(paid_amt),"en","US"));

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


    private void New_Customer_Insert_Transation_DB(String sno, String acno, String receiptno,String receiptcode, String ag_code, String actype, String modepay, String palmtec_id,
                                      String cancel, String tr_time,String chequedt,String chequeno,String collectdt,String collectamt,String remark,String shift_code,int customer_type,String name,String phoneno,String city,String customerno) {
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

                    Log.d("MAIN ACTIVITY=======(MODE OF PAYMENT)========>",modepay);
                    try {
                        if( !model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                            New_Customer_Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, remark, collectamt, customer_type);
                        }
                        } catch (Exception e) {
                        e.printStackTrace();
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
                    Log.d("MAIN ACTIVITY=======(MODE OF PAYMENT)========>",modepay);
                    try {
                        if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                            New_Customer_Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, remark, collectamt, customer_type);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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


    private void Print_Receipt_Market(String date,String time,String receiptcode,String customer_id,String customer_name,String city,String Old_due_amount,String Due_amount,int customer_type) {
        double  old_due_amount=500,due_amount=1000;
        double total_amt= 0;
        String tot_amt_words = "";
        String agent_code,grp_code;

        total_amt = old_due_amount + due_amount;
        //   String date ="",time="",receipt_no="",cust_id="",grp_code="",cust_name="",phone_no="",city="",agent_code="",remark="";
        // date = date;
        // time = time;
        // receipt_no = String.valueOf(report_shift_details.get(pos).getReceipt_code() +" "+report_shift_details.get(pos).getCollectdt());
        // receipt_no = receiptno;
        //cust_id = customer_id;

        // cust_name = customer_name;
        // phone_no = phoneno;
        city = city;
        // remark = report_shift_details.get(pos).getRemark();

        agent_code = MainActivity.AGENTCODE;


        // if(!date.equals("") && !time.equals("") && !cust_id.equals("") && ) {
        palmtecandro.jnidevOpen(115200);

        Clearbuffer();//Clear unwanted buffer
        //Leavespace();
        Alignment(27,97,1);//Center aligment
        Print("");

        if(settings_details.size()>0) {
            Clearbuffer();//Clear unwanted buffer
            //Leavespace();
            Alignment(27,97,1);//Center aligment
            //Print(" KAS JEWELLERY");
            Print("KAS MARKET");

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            // Print("Thirupuvanam");
            Print("Virudhunagar");

            Clearbuffer();//Clear unwanted buffer
            // Leavespace();
            Alignment(27, 97, 1);//Center aligment
            // Print("7530024247");
            Print("1234567890");
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
            Print("DATE : " + date + "  TIME : " + time);
        }


       // if (!receiptcode.equals("")) {

            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("RECEIPT NO   : " + receiptcode);
     //   }

        if(customer_type ==1) {

            if (!customer_name.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER NAME : " + customer_name);
            }

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(line_space);

            if (old_due_amount != 0) {
                Clearbuffer();//Clear unwanted buffer
                //  Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("OLD DUE AMOUNT: " + dft.format(old_due_amount));

            }
            if (due_amount != 0) {
                Clearbuffer();//Clear unwanted buffer
                Alignment(27, 97, 0);//Left alignment
                Print("DUE AMOUNT    : " + dft.format(due_amount));

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27, 97, 1);//Center alignment
                //Print("("+String.valueOf(convertIntoWords(1000.00,"en","US"))+" only)");
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

                Print("("+String.valueOf(convertIntoWords(total_amt,"en","US"))+" only)");
            }

            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27, 97, 1);//Center aligment
            Print(line_space);

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



    private void Print_Receipt(String date,String time,String receiptcode,String customer_id,String grpcode,String customer_name,String phoneno,String city,String prvsamount,String paidamount,int customer_type) {
        double  prvs_amount=0,paid_amount=0;
        double total_amt=0;
        String tot_amt_words = "";
        String agent_code,grp_code;
        //   String date ="",time="",receipt_no="",cust_id="",grp_code="",cust_name="",phone_no="",city="",agent_code="",remark="";
        // date = date;
        // time = time;
        // receipt_no = String.valueOf(report_shift_details.get(pos).getReceipt_code() +" "+report_shift_details.get(pos).getCollectdt());
        // receipt_no = receiptno;
        //cust_id = customer_id;
        grp_code = grpcode;
        // cust_name = customer_name;
        // phone_no = phoneno;
        city = city;
        // remark = report_shift_details.get(pos).getRemark();

        agent_code = MainActivity.AGENTCODE;

        try {
            if(customer_type ==1) {
                if(!prvsamount.equals("")) {
                    prvs_amount = Double.parseDouble(prvsamount);
                }
                if (!paidamount.equals("")) {
                    paid_amount = Double.parseDouble(paidamount);
                }
                total_amt = prvs_amount + paid_amount;
                 tot_amt_words = String.valueOf(convertIntoWords(paid_amount,"en","US"));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // if(!date.equals("") && !time.equals("") && !cust_id.equals("") && ) {
        palmtecandro.jnidevOpen(115200);

        Clearbuffer();//Clear unwanted buffer
        //Leavespace();
        Alignment(27,97,1);//Center aligment
        Print("");

        if(settings_details.size()>0) {
            Clearbuffer();//Clear unwanted buffer
            //Leavespace();
            Alignment(27,97,1);//Center aligment
            //Print(" KAS JEWELLERY");
            Print( settings_details.get(0).getCompany_name());

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

        if(customer_type ==1) {

            if (!customer_id.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER ID  : " + customer_id);
            }
            if (!grp_code.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                // Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("GRP CODE     : " + grp_code);

            }

            if (!customer_name.equals("")) {

                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("CUSTOMER NAME: " + customer_name);
            }
            if (!phoneno.equals("")) {
                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("PHONE NO     : " + phoneno);
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
                Print("("+String.valueOf(tot_amt_words.charAt(0)).toUpperCase()+String.valueOf(tot_amt_words.substring(1,tot_amt_words.length()))+" only)");
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


    private void New_Customer_Print_Receipt(String date,String time,String receiptcode,String customer_id,String grp_code,String customer_name,String phoneno,String city,String remarks,String paidamount,int customer_type) {
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


        if(customer_type ==2){
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
            base = new DataBaseAdapter(MainActivity.this);
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
                        settings_details.add(new Settings_Details(mcur.getString(1), mcur.getString(2), mcur.getString(3), mcur.getString(4), mcur.getString(5), mcur.getString(6)));
                        Log.d("SETTINGS_DATA =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(mcur.getString(2)));
                        mcur.moveToNext();
                    }
                }
            }

            if(settings_details.size()>0) {
                // Print_Receipt(pos);
            }else{

            }
        }
    }
    public class Get_Settings_Date extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor Settings_Cur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(MainActivity.this);
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
                delete_limit.clear();
                SCHEDULE_DATE.clear();
                TEMP_DATE.clear();

                for (int i = 0; i < Settings_Cur.getCount(); i++) {
                    delete_limit.add(String.valueOf(Settings_Cur.getString(6)));
                  //  SCHEDULE_DATE.add(String.valueOf(Settings_Cur.getString(7)));
                 //   TEMP_DATE.add(String.valueOf(Settings_Cur.getString(8)));

                    Log.d("SETTINGS TABLE_DATE_LIST ======ROW("+i+")=========>",Settings_Cur.getString(1) +" === "+Settings_Cur.getString(2)+"==="+" === "+Settings_Cur.getString(3)+" === "+Settings_Cur.getString(6)+" ");
                    Settings_Cur.moveToNext();
                }
                if(delete_limit.size()>0){
                   // if(SCHEDULE_DATE.get(0).equals(TEMP_DATE.get(0))) {
                        new Get_Limit_Date().execute(delete_limit.get(0).toString());
                  //  }
                    Log.d("SETTINGS TABLE DELETE LIMIT===============>",delete_limit.get(0).toString());

                    //company_name_tv.setText(String.valueOf());
                   // agent_name_tv.setText(String.valueOf());
                }
            } else {

                Log.d("SHIFT_TABLE_DATELIST ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class Get_Limit_Date extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur,Settings_Cur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(MainActivity.this);
            baseAdapter.open();
            mCur = baseAdapter.Select_Limit(Integer.parseInt(params[0]));
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
            DataBaseAdapter baseAdapter = new DataBaseAdapter(MainActivity.this);
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

    public class Delete_Timer extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        String success;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(MainActivity.this);
            baseAdapter.open();
            success = baseAdapter.Update_Settings("null");
            baseAdapter.close();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(success.equals("success")){
                Log.d("SETTINGS TABLE UPDATED ===============>","INCREMENTED");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                new Get_Settings_Date().execute();
            }
        }
    }

    public void Customer_sync_Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Do you want to sync customer data ?")
                .setTitle("Sync Process")
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(isNetworkAvailable()) {
                            dialog.dismiss();
                           // new NEW_SYNC_MASTER_DB().execute();

                        //    new Async_master().execute();

                        }else{
                            toast("Please check your internet connection");
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

    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow;
        Button button;
        CardView card_view,Show_more,Track;
        TextView city_name_tv,total_customer_tv;
        TextView customer_name,area_name,acc_no,mob_no;
        LinearLayout LL_card;

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

    public   class Settings_Details {
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(isFABOpen){
            fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_add_24));
            closeFABMenu();
        }

        return super.onTouchEvent(event);
    }

    public String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }
    public  String splitString(String str) {
        StringBuffer alpha = new StringBuffer(), num = new StringBuffer(), special = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)))
                num.append(str.charAt(i));
            else if (Character.isAlphabetic(str.charAt(i)))
                alpha.append(str.charAt(i));
            else
                special.append(str.charAt(i));
        }

        return num.toString();
    }

    }

