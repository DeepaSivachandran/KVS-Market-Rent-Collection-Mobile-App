package com.example.collectionreport;

import static com.example.collectionreport.Areawise_Activity.customer_inactive_details;
import static com.example.collectionreport.MainActivity.customer_name;
import static com.example.collectionreport.MainActivity.generate_code;
import static com.example.collectionreport.Areawise_Activity.customer_active_details;
import static com.example.collectionreport.MainActivity.model;
import static com.example.collectionreport.MainActivity.settings_details;
import static com.example.collectionreport.MainActivity.strDate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.softland.palmtecandro.palmtecandro;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActiveFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
    public static  ArrayList<Integer> response = new ArrayList<>();
    String line_space = "-------------------------------";
    DecimalFormat dft = new DecimalFormat("0.00");
    Context context;
    Cursor cursor;
    DataBaseAdapter baseAdapter;
    ImageView search_clear,search_enter;
    View view ;
    Activelist_Adapter activelist_adapter;
    ListView active_Listview;
    EditText search_edittext,txt_cheque_date,txt_cheque_no,amount,remarks;
    Spinner mode_of_payment;
    Dialog entry_dialog,closed_dialog;
    Button btnSave;
    LinearLayout cheque_layout;
    ArrayAdapter payment_arrayAdapter;
    TextView receipt_number,mode_of_payment_txt,paid_amt,payable_amt,balance_amt,txt_cus_name,txt_acc_no,txt_ac_type,txt_address_line1,txt_address_line2,txt_cityname,txt_mobno,txt_mode;
    ArrayList<String> payment_arraylist = new ArrayList<>();
    ArrayList<String> payment_code_arraylist = new ArrayList<>();
    String payment_name="",payment_code="0",Maturity_amount="0",PRVS_check="0",Amount="0",Remarks="0",Cheque_no="0",Cheque_date="";

    DecimalFormat money_sdf = new DecimalFormat ("0.00");
    public static String strTime="";
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdtime = new SimpleDateFormat("hh:mm aa", Locale.US);

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String current_time ,City_name="";
    private boolean networkstate =false;

    public  ArrayList<master_details> temp_customer_active = new ArrayList<>();

    public ActiveFragment(Context c,String City_name) {
        this.context =c;
        this.City_name = City_name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

         // context=getActivity();
          view=inflater.inflate(R.layout.active_fragment, container, false);


         active_Listview  = (ListView) view.findViewById(R.id.active_Listview);
         search_edittext  = (EditText) view.findViewById(R.id.search_edittext);

         search_clear = (ImageView) view.findViewById(R.id.search_clear);
         search_enter = (ImageView) view.findViewById(R.id.search_enter);
        // activelist_adapter = new Activelist_Adapter(context,new ArrayList<String>(Arrays.asList("Manikandan","Veera")));

         networkstate = isNetworkAvailable();

         if(networkstate){

         }

         activelist_adapter = new Activelist_Adapter(context, customer_active_details);
         active_Listview.setAdapter(activelist_adapter);
        // runLayoutAnimation(active_Listview);
         baseAdapter = new DataBaseAdapter(context);
         current_time = sdf.format(new Date());

        /*search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Activelist_Adapter adapter;
                active_Listview.setAdapter(null);
                temp_customer_active.clear();
                for(int i=0;i<customer_active_details.size();i++){
                    if(customer_active_details.get(i).getAccountno().contains(editable.toString()) ||customer_active_details.get(i).getName().contains(editable.toString()) ||customer_active_details.get(i).getPhoneno().contains(editable.toString())){
                        temp_customer_active.add(customer_active_details.get(i));
                    }

                }
                if(temp_customer_active !=null) {
                    active_Listview.setAdapter(null);
                    adapter = new Activelist_Adapter(context, temp_customer_active);
                    active_Listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    active_Listview.setAdapter(null);
                    adapter = new Activelist_Adapter(context, customer_active_details);
                    active_Listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });*/

        search_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_edittext != null) {
                    if (search_edittext.getText().toString().length()>0) {
                        if(search_clear.getVisibility() == View.GONE) {
                            search_clear.setVisibility(View.VISIBLE);
                        }
                        if(!City_name.equals("")) {
                            new Search_Customer().execute(search_edittext.getText().toString().trim(), City_name);
                        }
                    }else{
                        if(search_clear.getVisibility() == View.VISIBLE) {
                            search_clear.setVisibility(View.GONE);
                        }
                    }
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_clear.getVisibility() == View.VISIBLE){
                    search_clear.setVisibility(View.GONE);
                }
                search_edittext.setText(null);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                activelist_adapter = new Activelist_Adapter(context, customer_active_details);
                active_Listview.setAdapter(activelist_adapter);
            }
        });
        return view;
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class Activelist_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<master_details> customer_active_details;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Activelist_Adapter(Context context,ArrayList<master_details> customer_active_details){
            this.context=context;
            this.customer_active_details = customer_active_details;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return customer_active_details.size();
        }

        @Override
        public Object getItem(int position) {
            return customer_active_details.get(position);
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
            master_details master_details = customer_active_details.get(position);
         //   if (view == null) {
                view = layoutInflater.inflate(R.layout.customer_card_list, viewGroup, false);
                viewHolder = new ViewHolder();

                viewHolder.customer_name = (TextView) view.findViewById(R.id.customer_name);
                viewHolder.area_name =(TextView) view.findViewById(R.id.area_name);
                viewHolder.acc_no = (TextView) view.findViewById(R.id.acc_no);
                viewHolder.mob_no = (TextView) view.findViewById(R.id.mob_no);
                viewHolder.customer_cv = (CardView)view.findViewById(R.id.customer_cv);

                if(!customer_active_details.get(position).getName().equals("null")) {
                    viewHolder.customer_name.setText(customer_active_details.get(position).getName());
                }
                if(!customer_active_details.get(position).getAccountno().equals("null")) {
                    viewHolder.acc_no.setText(String.valueOf("A/C #"+customer_active_details.get(position).getAccountno()));
                }
                if(!customer_active_details.get(position).getPhoneno().equals("null"))
                {
                    viewHolder.mob_no.setText(customer_active_details.get(position).getPhoneno());
                }
                if(!customer_active_details.get(position).getCity_name().equals("null"))
                {
                    viewHolder.area_name.setText(customer_active_details.get(position).getCity_name());
                }
                //viewHolder.area_name.setText("");
               // viewHolder.acc_no.setText("");
              //  viewHolder.mob_no.setText("");

                view.setTag(master_details);
           /* } else {
                viewHolder = (ViewHolder) view.getTag();
            }*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  Toast.makeText(getActivity().getApplicationContext(),customer_active_details.get(position).getAg_code(),Toast.LENGTH_SHORT).show();
                    master_details master_details = (master_details) view.getTag();

                  if(master_details.getBILLDATE().equals("null") && master_details.getBILLNO().equals("null")) {

                      new Async_Get_Receipt(master_details, position).execute();

                  }else{
                      toast("Closed customer");
                      Closed_Customer_Popup(master_details.getBILLNO(),master_details.getBILLDATE());
                  }
                  //  activelist_adapter = new Activelist_Adapter(context, customer_active_details);
                  //  active_Listview.setAdapter(activelist_adapter);
                }
            });


            viewHolder.mob_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        //Creating intents for making a call
                        if(!viewHolder.mob_no.getText().toString().equals("")) {
                            Toast.makeText(context, "Making Call", Toast.LENGTH_SHORT).show();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:"+viewHolder.mob_no.getText().toString().trim()));
                            context.startActivity(callIntent);
                        }else{
                            Toast.makeText(context, "unable to call", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                }
            });
            return view;
        }

    }



    public void openEntryPopup(master_details master_details,String receiptno,String receiptcode,String collectdt) {
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
                openDatePickerDialog(view);
            }
        });

        if(!master_details.getName().equals("null")) {
            txt_cus_name.setText(String.valueOf(master_details.getName()));
        }

        if(!master_details.getAccountno().equals("null")) {
            txt_acc_no.setText(String.valueOf(master_details.getAccountno()));
        }

        if(!master_details.getActype().equals("null")) {
            txt_ac_type.setText(String.valueOf(master_details.getActype()));
        }

        if(!master_details.getAddress1().equals("null")) {
            txt_address_line1.setText(String.valueOf(master_details.getAddress1()));
        }
        if(!master_details.getAddress2().equals("null")) {
            txt_address_line2.setText(String.valueOf(master_details.getAddress2()));
        }
        if(!master_details.getPhoneno().equals("null")){
            txt_mobno.setText(String.valueOf(master_details.getPhoneno()));
        }
        txt_cityname.setText(" - ");
        txt_mode.setText("CASH");
        if(!master_details.getCity_name().equals("null")) {
            txt_cityname.setText(String.valueOf(master_details.getCity_name()));
        }
        if(!master_details.getActype().equals("null") ){
            payable_amt.setText(String.valueOf("₹ "+money_sdf.format(Double.parseDouble(splitString(master_details.getActype())))));
            amount.setText(String.valueOf(money_sdf.format(Double.parseDouble(splitString(master_details.getActype())))));
            Log.d("TRYING TO SPLIT NUMBER FROM STRING ===(ACTIVE)====(payable_amt)=============>",splitString(master_details.getActype()));
        }
        if(!master_details.getAmount().equals("null")){
            paid_amt.setText(String.valueOf("₹ "+master_details.getAmount()));
        }
        if( master_details.getAmount() !=null && !master_details.getAmount().equals("null") && !master_details.getActype().equals("null") ) {
           // set_balance_amt(String.valueOf(customer_active_details.get(pos).getAmount()), String.valueOf(customer_active_details.get(pos).getActype().substring(1)));

            set_balance_amt(String.valueOf(master_details.getBalance_amount()), String.valueOf(splitString(master_details.getActype())));

        }else{
           // Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }
        if(master_details.getMaturity_amount() != null &&!master_details.getMaturity_amount().equals("null") && !master_details.getMaturity_amount().equals("")){
            Maturity_amount = master_details.getMaturity_amount();

        }
        if(master_details.getAmount() !=null && !master_details.getAmount().equals("null") && !master_details.getAmount().equals("0")){
            PRVS_check = master_details.getAmount();
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
                    if(!Maturity_amount.equals("0")) {
                        if (!Maturity_amount.equals("")) {
                            if (Double.parseDouble(editable.toString()) > Double.parseDouble(Maturity_amount)) {
                                //   Toast.makeText(context, "Exceed maximum amount", Toast.LENGTH_SHORT).show();
                                toast("Exceed maximum amount");
                                amount.setText("");
                                Amount = "0";
                            }
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
                            // Toast.makeText(context, "Exceed maximum limit", Toast.LENGTH_SHORT).show();
                            toast("Exceed maximum limit");
                            Amount = "0";
                        }
                    }

                    if (payment_code.equals("1")) {
                        if (!Amount.equals("0") && !Amount.equals("")) {
                            // Toast.makeText(context, " Cash payment done", Toast.LENGTH_SHORT).show();
                            //Insert_Transation_DB("","","","","","",
                            //     "","","","","","","","");
                            //toast("Payment done");
                            Insert_Transation_DB("", master_details.getAccountno(), receiptno, receiptcode, master_details.getAg_code(), master_details.getActype(), "CASH",
                                    "", "N", current_time, "", "", strDate, Amount, Remarks, "", master_details.getName(), master_details.getPhoneno(), master_details.getCity_name(), master_details.getAmount());
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
                            // toast("Payment done");
                            //Insert_Transation_DB("","","","","","",
                            //     "","","","","","","","");
                            Insert_Transation_DB("", master_details.getAccountno(), receiptno, receiptcode, master_details.getAg_code(), master_details.getActype(), "BANK TRANSFER",
                                    "", "N", current_time, "", "", strDate, Amount, Remarks, "", master_details.getName(), master_details.getPhoneno(), master_details.getCity_name(), master_details.getAmount());
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
                    toast("Payment done");
                    Log.d("TRANSACT_TABLE_INSERTION===============>",String.valueOf("completed"));
                    try {
                        if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                            Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, paid_amt, collectamt, 1);
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
                        cancel,tr_time,chequedt,chequeno,collectdt,collectamt,remark,generate_code,strDate,"",0,0,1,"","","",0);

                if(success.equals("success")){
                    toast("Payment done");
                    Log.d("TRANSACT_TABLE_INSERTION===============>","completed");
                    try {
                        if(!model.equals("") && model.equals("SIL_PALMTECANDRO_4G")) {
                            Print_Receipt(collectdt, tr_time, receiptcode, acno, actype, name, phoneno, city, paid_amt, collectamt, 1);
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


    public class Search_Customer extends
            AsyncTask<String, JSONObject, Boolean> {
        DataBaseAdapter base;
        Activelist_Adapter adapter;
        Cursor mcur;
        ArrayList<master_details> search_array = new ArrayList<>();

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            //  try {

            mcur = base.Active_Search(params[0],params[1]);

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            search_array.clear();
            base = new DataBaseAdapter(context);
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
                                mcur.getString(43), mcur.getString(44), mcur.getString(45), mcur.getString(46), mcur.getString(48), mcur.getString(49), mcur.getString(50), mcur.getString(51), mcur.getString(52),mcur.getString(53),mcur.getString(54)));
                        Log.d("MASTER_DATA =======ROW(" + String.valueOf(i) + ")========>", String.valueOf(mcur.getString(2)));
                        mcur.moveToNext();
                    }
                }
            }

            if(search_array !=null) {
                active_Listview.setAdapter(null);
                adapter = new Activelist_Adapter(context, search_array);
                active_Listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{

            }
            base.close();
        }
    }
    public void openDatePickerDialog(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        //cal1.add(Calendar.DAY_OF_YEAR, -1);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {

                    String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    txt_cheque_date.setText(selectedDate);

                    Cheque_date = selectedDate;

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

       // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
       // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow;
        Button button;
        CardView customer_cv,Show_more,Track;
        TextView city_name_tv,total_customer_tv;
        TextView customer_name,area_name,acc_no,mob_no;

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
            base_Adapter = new DataBaseAdapter(context);
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
                                      Log.d("INSERTED =======ROW(" + String.valueOf(i) + ")========>", String.valueOf("UPDATED   "+mCur.getString(12))+"   "+outputDateStr);

                                //  }else{
                                //      Log.d("ACTIVE FRAGMENT ===============>","API RETURNS NULL JSON OBJECT");
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

                    // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                }


                Log.d("CURSOR_DATA===============>", String.valueOf(mCur.getCount()));
            }

            base_Adapter.close();
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            response.clear();
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

    public class Async_Get_Receipt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor cursor;
        master_details master_details;
        int pos;

        public Async_Get_Receipt(master_details master_details,int position) {
            this.master_details =master_details;
            this.pos = position;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(context);
            baseAdapter.open();
            cursor = baseAdapter.Get_Receipt(master_details.getAg_code());

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
                openEntryPopup(master_details,receiptno,receiptcode,collectdt);
            }else{
                Toast.makeText(context, " unable to open payment dailog ", Toast.LENGTH_SHORT).show();
            }
        }
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


    public void Closed_Customer_Popup(String receiptno,String receiptdate) {
        TextView receipt_no_tv,receipt_date_tv ;
        Button btnOk ;
        closed_dialog = new Dialog(context);
        closed_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        closed_dialog.setContentView(R.layout.closed_customer_popup);
        closed_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        receipt_no_tv = (TextView) closed_dialog.findViewById(R.id.receipt_no);
        receipt_date_tv = (TextView) closed_dialog.findViewById(R.id.receipt_date);
        btnOk = (Button) closed_dialog.findViewById(R.id.btnOk);
        try {
            if(!receiptdate.equals("null")) {
                JSONObject jsonObject = new JSONObject(receiptdate);
                String temp = jsonObject.getString("date");
                receipt_date_tv.setText(String.valueOf(temp.substring(0,10)));
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void Print_Receipt(String date,String time,String receiptcode,String customer_id,String grp_code,String customer_name,String phoneno,String city,String prvsamount,String paidamount,int customer_type) {
        double  prvs_amount=0,paid_amount=0;
        double total_amt=0;
        String agent_code;
        String tot_amt_words = "";
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

        if(settings_details.size()>0) {
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


        /*if(report_shift_details.get(pos).getCustomer_type() ==2 || report_shift_details.get(pos).getCustomer_type() ==3){
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
                Print("DESCRIPTION  : "+remark);
            }

            Clearbuffer();//Clear unwanted buffer
            //  Leavespace();
            Alignment(27,97,1);//Center aligment
            Print(line_space);

        }*/

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

    public String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
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


}
