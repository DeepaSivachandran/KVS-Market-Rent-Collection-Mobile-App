package com.example.collectionreport;

import static com.example.collectionreport.MainActivity.RECEIPT_PREFIX;
import static com.example.collectionreport.MainActivity.actual_collection_tv;
import static com.example.collectionreport.MainActivity.date_db;
import static com.example.collectionreport.MainActivity.expected_collection_tv;
import static com.example.collectionreport.MainActivity.selectedDate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.softland.palmtecandro.palmtecandro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Monthly_Fragment extends Fragment implements  MainActivity.Refreshpage{

    View view;
    Context context;
    ListView customer_Listview;
    LayoutInflater inflater = null;
    MemberListBaseAdapter memberListBaseAdapter ;
    ArrayList<Customer_Details> customer_details = new ArrayList<>();
    ArrayList<Customer_Details> customer_details_Temp = new ArrayList<>();
    String TENANT_STATE_CODE = "";
    ImageView total_print_img;
    String line_space = "-------------------------------";
    DecimalFormat dft = new DecimalFormat("0.00");
    String RECEIPT_NUMBER = "";
    int output_receipt_no  = 0,print_receipt_no =0;
    int TOTAL_AMOUNT =0;
    TextView due_amount_tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.monthly_fragment, container, false);
        //    expandableListView_lineSales = (ListView) view.findViewById(R.id.expenListview_Linesales);
        //    progressBar = (ProgressBar) view.findViewById(R.id.linesales_fragment_1);
        //   progress = (ProgressBar) view.findViewById(R.id.linesales_fragment_1);

        customer_Listview = (ListView) view.findViewById(R.id.customer_Listview);

        due_amount_tv = (TextView) view.findViewById(R.id.due_amount_tv);
        total_print_img = (ImageView) view.findViewById(R.id.total_print_img);
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));
//        customer_details.add(new Customer_Details("1","1","1",false));


        new Async_DailyCollection().execute();

        new Async_Get_Receiptno().execute();
//        if(customer_details.size()>0) {
//
//            memberListBaseAdapter = new MemberListBaseAdapter(context, customer_details);
//            customer_Listview.setAdapter(memberListBaseAdapter);
//            memberListBaseAdapter.notifyDataSetChanged();
//
//        }

        total_print_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
                builder.setMessage("Are you sure you want to print ?")
                        .setTitle(R.string.app_name)
                        .setIcon(R.mipmap.icon)
                        .setCancelable(false)
                        //.setIcon(R.mipmap.admin)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                call();
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
        });
        return  view;
    }

    private void call() {
        try{
            new Async_Get_Receiptno().execute();
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (customer_details.size() > 0) {
                customer_details_Temp.clear();
                for (int i = 0; i < customer_details.size(); i++) {

                    if (customer_details.get(i).isSelected()) {
                        customer_details_Temp.add(customer_details.get(i));
                        Log.d("MONTHLY REPORT  SELECTED LIST  ===========>", "TENANT NAME  :  " + customer_details.get(i).getTenantname() + "SHOP NAME : " + customer_details.get(i).getShopname());
                    }
                }
            }

            if (customer_details_Temp.size() > 0) {
                if (customer_details_Temp.size() == 1) {
//                        try {
//                            new Async_Get_Receiptno().execute();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }finally {
                    Print_Receipt_Market(selectedDate, " ", " ", "", "", "", "", "", 0);
                    //    }
                }

                if (customer_details_Temp.size() > 1) {
//                        try {
//                            new Async_Get_Receiptno().execute();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }finally {
                    Print_Receipt_Market(selectedDate, " ", "  ", "", "", "", "", "", 1);
                    //   }
                }

            } else {
                toast("Please select atleast one customer");
            }
        }
        // Print_Receipt_Market("26-01-2023","12:30 PM","2023010001","","Deepa Sivachandran","","1000","1000",1);

}

    @Override
    public void refreshpage() {
        // MainActivity.progressBar.setVisibility(View.VISIBLE);
        //progress.setVisibility(View.VISIBLE);
        // selectedDate=MainActivity.tv_date_range.getText().toString();
        // new Asynclinesaleslist().execute(selectedDate);
        FragmentTransaction ft =  getFragmentManager().beginTransaction();
        ft.detach(Monthly_Fragment.this).attach(Monthly_Fragment.this).commit();
        new Async_Get_Receiptno().execute();
        new Async_DailyCollection().execute();
    }

    @Override
    public void onResume() {
        new Async_Get_Receiptno().execute();
        super.onResume();
    }

    @Override
    public void onStart() {
        new Async_Get_Receiptno().execute();
        super.onStart();
    }

    public class Async_DailyCollection extends
            AsyncTask<String, JSONObject, Boolean> {
        JSONObject jsonObj;
        JSONArray jsonArray ;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                  if (isNetworkAvailable()) {
                //REQUEST 1 is used to get Daily Collection Details
                      jsonObj = objRestAPI.GetDashBoard("2");
                  }
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
            due_amount_tv.setText(String.valueOf(" \u20B9 0"));
            if (jsonObj == null || jsonObj.equals("")) {
                // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                toast("Please check your internet connection");
            } else {
                try {
                    int success = jsonObj.getInt("success");


                    if (success == 0) {
                        customer_details.clear();

                    } else if (success == 1) {
                        customer_details.clear();
                        jsonArray = jsonObj.getJSONArray("daily_collection_details");

                        if (jsonArray.length() > 0) {
                            customer_details.clear();
                            String status;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                customer_details.add(new Customer_Details(json.getString("shopcode"),json.getString("shopname"),json.getString("tenantcode"),json.getString("tenantname"),
                                        json.getString("rent"),json.getString("olddue_amount"),json.getString("due_amount"),json.getString("total_amount"),
                                        json.getString("statuscode"),json.getString("statusname"),json.getString("shopstatus"),json.getString("rentcycle"),false));

                                Log.d("MASTER_DATA_API CITY_LIST ===========>", json.toString());
                            }

                            if(customer_details.size()>0) {

                                memberListBaseAdapter = new MemberListBaseAdapter(context, customer_details);
                                customer_Listview.setAdapter(memberListBaseAdapter);
                                memberListBaseAdapter.notifyDataSetChanged();
                                calculateGrandAmount();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }

    }

//    public class MemberListBaseAdapter extends BaseAdapter {
//
//        Context context;
//        ArrayList<Customer_Details> myList;
//        DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");
//
//        public MemberListBaseAdapter(Context context, ArrayList<Customer_Details> myList) {
//            this.myList = myList;
//            this.context = context;
//            inflater = LayoutInflater.from(context);
//
//            //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() {
//            return myList.size();
//        }
//
//        @Override
//        public Customer_Details getItem(int position) {
//            return  myList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            if (getCount() > 0) {
//                return getCount();
//            } else {
//                return super.getViewTypeCount();
//            }
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//
//        @SuppressLint("InflateParams")
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            ViewHolder mHolder;
//
//            View view = null;
//            if (convertView == null) {
//
//                view = inflater.inflate(R.layout.main_card, parent, false);
//                final ViewHolder holder = new ViewHolder();
//                try {
//                    //     holder.total_amount_tv = (EditText) view.findViewById(R.id.total_amount_tv);
//                    holder.LL_card = (LinearLayout) view.findViewById(R.id.LL_card);
//                    holder.card_view = (CardView) view.findViewById(R.id.card_view);
//
//                    holder.shop_no_tv =  (TextView) view.findViewById(R.id.shop_no_tv);
//                    holder.tenant_name_tv =  (TextView) view.findViewById(R.id.tenant_name_tv);
//
//                    holder.olddue_amount_tv =  (TextView) view.findViewById(R.id.olddue_amount_tv);
//                    holder.due_amount_tv =  (TextView) view.findViewById(R.id.due_amount_tv);
//                    holder.total_amount_tv  =  (TextView) view.findViewById(R.id.total_amount_tv);
//
//                    holder.shop_no_tv.setText(String.valueOf(myList.get(position).getShopname()));
//                    holder.tenant_name_tv.setText(String.valueOf(myList.get(position).getTenantname()));
//
//                    holder.due_amount_tv.setText(String.valueOf("\u20B9 "+myList.get(position).getDue_amount()));
//                    holder.olddue_amount_tv.setText(String.valueOf("\u20B9  "+myList.get(position).getOlddue_amount()));
//                    holder.total_amount_tv.setText(String.valueOf("\u20B9  "+myList.get(position).getTotal_amount()));
//
////                    if(!myList.get(position).getDue_amount().equals("") && myList.get(position).getDue_amount() != null && !myList.get(position).getDue_amount().equals("null")){
////                        int due_amount = Integer.parseInt(myList.get(position).getDue_amount());
////                        int total_amount = Integer.parseInt(myList.get(position).getTotal_amount());
////
////                        holder.olddue_amount_tv.setText(String.valueOf(total_amount - due_amount));
////
////                    }
//
//
//                } catch (Exception e) {
//                    Log.i("Route", e.toString());
//
//                }
//
//
//                view.setTag(holder);
//
//
//                //  holder.total_amount_tv.setText(String.valueOf(membersDetails.get(position).getGivenamount()));
//
//
////                holder.total_amount_tv.addTextChangedListener(new TextWatcher() {
////                    @Override
////                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////                    }
////
////                    @Override
////                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////                    }
////
////                    @Override
////                    public void afterTextChanged(Editable editable) {
////
////                        if(editable != null){
////                            if(editable.length()>0) {
////
////                                Log.d("RECOVERY ACTIVITY ====("+String.valueOf(position+1)+")======(MEMBER :" + membersDetails.get(position).getMembername() + " )=====(TOTAL AMOUNT CHANGED)======>",String.valueOf(editable));
////
//////                                if(Integer.parseInt(String.valueOf(editable))>Integer.parseInt(membersDetails.get(position).getOverall_loan_amount())){
//////                                     mHolder.total_amount_tv.setText("");
//////                                     Toast.makeText(context,"Recovery amount exceed loan amount to "+membersDetails.get(position).getMembername(),Toast.LENGTH_SHORT).show();
//////                                }else {
////                                //  membersDetails.get(position).setEmiamount(String.valueOf(editable));
////
////
////                                membersDetails.get(position).setGivenamount(String.valueOf(editable));
////                                //  myList.get(position).setGivenamount(String.valueOf(editable));
//////                                }
////
////
////
////                                // memberListBaseAdapter.notifyDataSetChanged();
////                                calculateTotalDueAmount();
////                            }
////                        }
////                    }
////                });
//
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
////                        mHolder.LL_recovery.setBackgroundResource(R.color.card_select);
////                        mHolder.total_amount_tv.setBackgroundResource(R.drawable.amount_input_bg);
//
//                        // Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_SHORT).show();
//
//
//                        if(!customer_details.get(position).isSelected()) {
//
//                            holder.LL_card.setBackgroundResource(R.color.select_green);
//                            customer_details.get(position).setSelected(true);
//
//                        }else{
//
//                            holder.LL_card.setBackgroundResource(R.color.white);
//                            customer_details.get(position).setSelected(false);
//                        }
//
//                    }
//                });
//
//
//            } else {
//                // mHolder = (ViewHolder) convertView.getTag();
//                view = convertView;
//                // ((ViewHolder) view.getTag()).total_amount_tv.setTag(membersDetails.get(position));
//            }
//
//
////            ViewHolder holder_ = (ViewHolder) view.getTag();
////
////            holder_.total_amount_tv.setTag(getItem(position));
//
//            return view;
//        }
//    }


    public class MemberListBaseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Customer_Details> myList;
        DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");

        public MemberListBaseAdapter(Context context, ArrayList<Customer_Details> myList) {
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
        public Customer_Details getItem(int position) {
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

                    holder.shop_no_tv =  (TextView) view.findViewById(R.id.shop_no_tv);
                    holder.tenant_name_tv =  (TextView) view.findViewById(R.id.tenant_name_tv);

                    holder.paid_tv =  (TextView) view.findViewById(R.id.paid_tv);

                    holder.olddue_amount_tv =  (TextView) view.findViewById(R.id.olddue_amount_tv);
                    holder.due_amount_tv =  (TextView) view.findViewById(R.id.due_amount_tv);

                    holder.total_amount_tv  =  (EditText) view.findViewById(R.id.total_amount_tv);

                    holder.shop_no_tv.setText(String.valueOf(myList.get(position).getShopname()));
                    holder.tenant_name_tv.setText(String.valueOf(myList.get(position).getTenantname()));

                    holder.due_amount_tv.setText(String.valueOf(myList.get(position).getDue_amount()));
                    holder.olddue_amount_tv.setText(String.valueOf("\u20B9  "+myList.get(position).getOlddue_amount()));
                    holder.total_amount_tv.setText(String.valueOf("\u20B9  "+myList.get(position).getTotal_amount()));

//                    if(!myList.get(position).getDue_amount().equals("") && myList.get(position).getDue_amount() != null && !myList.get(position).getDue_amount().equals("null")){
//                        int due_amount = Integer.parseInt(myList.get(position).getDue_amount());
//                        int total_amount = Integer.parseInt(myList.get(position).getTotal_amount());
//
//                        holder.olddue_amount_tv.setText(String.valueOf(total_amount - due_amount));
//
//                    }


                } catch (Exception e) {
                    Log.i("Route", e.toString());

                }


                view.setTag(holder);



                holder.due_amount_tv.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        if(editable != null){
                            if(editable.length()>0) {

                                Log.d("RECOVERY ACTIVITY ====("+String.valueOf(position+1)+")======(MEMBER :" + customer_details.get(position).getTenantname() + " )=====(TOTAL AMOUNT CHANGED)======>",String.valueOf(editable));

//                                if(Integer.parseInt(String.valueOf(editable))>Integer.parseInt(membersDetails.get(position).getOverall_loan_amount())){
//                                     mHolder.total_amount_tv.setText("");
//                                     Toast.makeText(context,"Recovery amount exceed loan amount to "+membersDetails.get(position).getMembername(),Toast.LENGTH_SHORT).show();
//                                }else {
                                //  membersDetails.get(position).setEmiamount(String.valueOf(editable));

                                customer_details.get(position).setDue_amount(String.valueOf(editable));

                                customer_details.get(position).setTotal_amount(String.valueOf(editable));
                                //  myList.get(position).setGivenamount(String.valueOf(editable));
//                                }

                                // memberListBaseAdapter.notifyDataSetChanged();
                                calculateTotalDueAmount();

                            }
                        }
                    }
                });

                if(customer_details.get(position).getStatuscode() != null && !customer_details.get(position).getStatuscode().equals("null")){

                    if(customer_details.get(position).getStatuscode().equals("10")){
                        if(holder.paid_tv.getVisibility() == View.GONE) {
                            holder.paid_tv.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(holder.paid_tv.getVisibility() == View.VISIBLE) {
                            holder.paid_tv.setVisibility(View.GONE);
                        }
                    }
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        mHolder.LL_recovery.setBackgroundResource(R.color.card_select);
//                        mHolder.total_amount_tv.setBackgroundResource(R.drawable.amount_input_bg);

                        // Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_SHORT).show();

                        if(customer_details.get(position).getStatuscode() != null && !customer_details.get(position).getStatuscode().equals("null")){


                        }else{


                            if(TENANT_STATE_CODE.equals("") || TENANT_STATE_CODE.equals(customer_details.get(position).getTenantcode())) {


                                if (!customer_details.get(position).isSelected()) {

                                    holder.due_amount_tv.setEnabled(true);
                                    holder.due_amount_tv.setFocusable(true);
                                    holder.due_amount_tv.setFocusableInTouchMode(true);

                                    holder.due_amount_tv.setBackgroundResource(R.drawable.amount_input_bg);
                                    holder.LL_card.setBackgroundResource(R.color.select_green);
                                    customer_details.get(position).setSelected(true);

                                } else {

                                    holder.due_amount_tv.setEnabled(false);
                                    holder.due_amount_tv.setFocusable(false);
                                    holder.due_amount_tv.setFocusableInTouchMode(false);

                                    holder.due_amount_tv.setBackgroundResource(R.color.white);
                                    holder.LL_card.setBackgroundResource(R.color.white);

                                    customer_details.get(position).setSelected(false);
                                }
                            }else{
                                toast("Please select same tenant");
                            }

                            calculateTotalDueAmount();
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




//    private void Print_Receipt_Market(String date,String time,String receiptcode,String customer_id,String customer_name,String city,String Old_due_amount,String Due_amount,int customer_type) {
//        double  old_due_amount=500,due_amount=1000;
//        double total_amt= 0,grand_total = 0;
//        String tot_amt_words = "";
//        String agent_code,grp_code;
//
//        palmtecandro.jnidevOpen(115200);
//        if(customer_type == 0) {
//            grand_total =0;
//
//            total_amt =0;
//            // total_amt = old_due_amount + due_amount;
//
//
//
//            Clearbuffer();//Clear unwanted buffer
//            //Leavespace();
//            Alignment(27,97,1);//Center aligment
//            Print("");
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                // Print("Thirupuvanam");
//                Print(MainActivity.CITY_NAME);
//
//                Clearbuffer();//Clear unwanted buffer
//                //Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                //Print(" KAS JEWELLERY");
//                Print(MainActivity.COMPANY_NAME);
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                // Print("Thirupuvanam");
//                Print(MainActivity.SUBTITLE_1);
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                // Print("Thirupuvanam");
//                Print(MainActivity.SUBTITLE_2);
//
//            }
//
//            Clearbuffer();//Clear unwanted buffer
//            Alignment(27,97,1);//Center aligment
//            Print(line_space);
//
//            Clearbuffer();//Clear unwanted buffer
//            // Leavespace();
//            Alignment(27,97,1);
//            Print("RENT RECEIPT");
//
//            Clearbuffer();//Clear unwanted buffer
//            // Leavespace();
//            Alignment(27, 97, 1);//Center aligment
//            Print(line_space);
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//              //  Print("DATE: " + date + " TIME: " + time);
//                Print("DATE: " + MainActivity.strDate + " TIME: " + getReminingTime());
//
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27, 97, 0);//Left alignment
//            Print("RECEIPT NO   : " + String.valueOf(print_receipt_no+1));
//
//            Clearbuffer();//Clear unwanted buffer
//            //   Leavespace();
//            Alignment(27, 97, 0);//Left alignment
//            Print("SHOP NO      : " + customer_details_Temp.get(0).getShopname());
//
//            Clearbuffer();//Clear unwanted buffer
//            //   Leavespace();
//            Alignment(27, 97, 0);//Left alignment
//
//            Print("TENANT NAME  : " + customer_details_Temp.get(0).getTenantname());
////
////                if (customer_details_Temp.get(0).getOlddue_amount() != null && !customer_details_Temp.get(0).getOlddue_amount().equals("null") ) {
////                    Clearbuffer();//Clear unwanted buffer
////                    //  Leavespace();
////                    Alignment(27, 97, 0);//Left alignment
////
////                    Print("OLD DUE      : " + dft.format(Double.parseDouble(customer_details_Temp.get(0).getOlddue_amount())));
////                }
////            if (customer_details_Temp.get(0).getDue_amount() != null && !customer_details_Temp.get(0).getDue_amount().equals("null")) {
////                Clearbuffer();//Clear unwanted buffer
////                Alignment(27, 97, 0);//Left alignment
////                Print("CURRENT DUE  : " + dft.format(Double.parseDouble(customer_details_Temp.get(0).getDue_amount())));
////
////            }
//            Clearbuffer();//Clear unwanted bufferPrint("");
//            //Leavespace();
//            Alignment(27, 97, 1);//Center aligment
//            Print(line_space);
//
//            if (customer_details_Temp.get(0).getDue_amount() != null) {
//                Clearbuffer();//Clear unwanted buffer
//                //Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("TOTAL        : " + dft.format(Double.parseDouble(customer_details_Temp.get(0).getDue_amount())));
//
//                Clearbuffer();//Clear unwanted buffer
//                // Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
//                Print("(INCLUSIVE OF SERVICE TAX)");
//                // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
//
//            }
//
//            Clearbuffer();//Clear unwanted buffer
//            //  Leavespace();
//            Alignment(27, 97, 1);//Center aligment
//            Print(line_space);
//
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27,97,1);//Center aligment
//            Print(" * * * * * THANK YOU * * * * * ");
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Print("");
//            Clearbuffer();//Clear unwanted buffer
//            Print("");
//            Clearbuffer();//Clear unwanted buffer
//            Print("");
//        }
//
//        if(customer_type == 1) {
//            grand_total =0;
//            for (int i=0;i<customer_details_Temp.size();i++) {
//
//                print_receipt_no =  print_receipt_no +1;
//                String tot_amount_words ="";
//                grand_total = grand_total +Double.parseDouble(customer_details_Temp.get(i).getDue_amount());
//
//                total_amt =0;
//
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
//                if (!date.equals("") && !time.equals("")) {
//                    Clearbuffer();//Clear unwanted buffer
//                    Leavespace();
//                    Alignment(27, 97, 0);//Left alignment
//                  //  Print("DATE: " + date + " TIME: " + time);
//                    Print("DATE: " + MainActivity.strDate + " TIME: " + getReminingTime());
//                }
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("RECEIPT NO  : " + String.valueOf(print_receipt_no));
//                //   }
//                Clearbuffer();//Clear unwanted buffer
//                //   Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("SHOP NO     : " + customer_details_Temp.get(i).getShopname());
//                Clearbuffer();//Clear unwanted buffer
//                //   Leavespace();
//                Alignment(27, 97, 0);//Left alignment
//                Print("TENANT NAME : " + customer_details_Temp.get(i).getTenantname());
//
////                if (customer_details_Temp.get(i).getOlddue_amount() != null && !customer_details_Temp.get(i).getOlddue_amount().equals("null") ) {
////                    Clearbuffer();//Clear unwanted buffer
////                    Leavespace();
////                    Alignment(27, 97, 0);//Left alignment
////                    Print("OLD DUE     : " + dft.format(Double.parseDouble(customer_details_Temp.get(i).getOlddue_amount())));
////                }
////
////                if (customer_details_Temp.get(i).getDue_amount() != null && !customer_details_Temp.get(i).getDue_amount().equals("null")) {
////                    Clearbuffer();//Clear unwanted buffer
////                    Alignment(27, 97, 0);//Left alignment
////                    Print("CURRENT DUE : " + dft.format(Double.parseDouble(customer_details_Temp.get(i).getDue_amount())));
////
////                }
//                Clearbuffer();//Clear unwanted bufferPrint("");
//                //Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                Print(line_space);
//
//                if (customer_details_Temp.get(i).getTotal_amount() != null) {
//                    Clearbuffer();//Clear unwanted buffer
//                    //Leavespace();
//                    Alignment(27, 97, 0);//Left alignment
//                    Print("TOTAL       : " + dft.format(Double.parseDouble(customer_details_Temp.get(i).getDue_amount())));
//
//                    Clearbuffer();//Clear unwanted buffer
//                    // Leavespace();
//                    Alignment(27, 97, 1);//Center aligment
//                    // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
//                    Print("(INCLUSIVE OF SERVICE TAX)");
//                    // Print("(" + String.valueOf(tot_amount_words.charAt(0)).toUpperCase() + String.valueOf(tot_amount_words.substring(1, tot_amount_words.length())) + " only)");
//
//                }
//
//                Clearbuffer();//Clear unwanted buffer
//                //  Leavespace();
//                Alignment(27, 97, 1);//Center aligment
//                Print(line_space);
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Alignment(27,97,1);//Center aligment
//                Print(" * * * * * THANK YOU * * * * * ");
//
//                Clearbuffer();//Clear unwanted buffer
//                Leavespace();
//                Print("");
//                Clearbuffer();//Clear unwanted buffer
//                Print("");
//                Clearbuffer();//Clear unwanted buffer
//                Print("");
//            }
//            Print("OVERALL AMOUNT : "+dft.format(grand_total));
//            Clearbuffer();//Clear unwanted buffer
//            Leavespace();
//            Alignment(27,97,0);//Left alignment
//
//
//        }
//
////        Clearbuffer();//Clear unwanted buffer
////        Leavespace();
////        Alignment(27,97,1);//Center aligment
////        Print(" * * * * * THANK YOU * * * * * ");
////
////        Clearbuffer();//Clear unwanted buffer
////        Leavespace();
////        Print("");
////        Clearbuffer();//Clear unwanted buffer
////        Print("");
////        Clearbuffer();//Clear unwanted buffer
////        Print("");
//
//        palmtecandro.jnidevClose();
//
//
////        new  Async_Save_Transaction().execute();
////
////        new Async_DailyCollection().execute();
//        Save_Transaction();
//
//    }
//

    private void Print_Receipt_Market(String date,String time,String receiptcode,String customer_id,String customer_name,String city,String Old_due_amount,String Due_amount,int customer_type) {
        double  old_due_amount=500,due_amount=1000;
        double total_amt= 0,grand_total = 0;
        String tot_amt_words = "";
        String agent_code,grp_code;

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
            Print("RECEIPT NO:" + String.valueOf(print_receipt_no+1)+" SHOP NO:" + customer_details_Temp.get(0).getShopname());

            Clearbuffer();//Clear unwanted buffer
            //   Leavespace();
            Alignment(27, 97, 0);//Left alignment
            Print("TENANT NAME: " + customer_details_Temp.get(0).getTenantname());

            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,2);//Right alignment
            Print("AMOUNT: "  + String.valueOf(dft.format(Double.parseDouble(customer_details_Temp.get(0).getTotal_amount()))));
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

        if(customer_type == 1) {
            grand_total =0;
            for (int i=0;i<customer_details_Temp.size();i++) {

                print_receipt_no =  print_receipt_no +1;
                String tot_amount_words ="";
                grand_total = grand_total +Double.parseDouble(customer_details_Temp.get(i).getDue_amount());

                total_amt =0;

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
                Print("RECEIPT NO:" + String.valueOf(print_receipt_no)+" SHOP NO:" + customer_details_Temp.get(i).getShopname());

                Clearbuffer();//Clear unwanted buffer
                //   Leavespace();
                Alignment(27, 97, 0);//Left alignment
                Print("TENANT NAME: " + customer_details_Temp.get(i).getTenantname());

                Clearbuffer();//Clear unwanted buffer
                Leavespace();
                Alignment(27,97,2);//Right alignment
                Print("AMOUNT: "  + String.valueOf(dft.format(Double.parseDouble(customer_details_Temp.get(i).getTotal_amount()))));
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
            }

            Print("OVERALL AMOUNT : "+String.valueOf(dft.format(grand_total)));
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,0);//Left alignment
            Print("");
            Clearbuffer();//Clear unwanted buffer
            Leavespace();
            Alignment(27,97,0);//Left alignment
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


//        new  Async_Save_Transaction().execute();
//
       new Async_DailyCollection().execute();
        Save_Transaction();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Save_Transaction() {

        if(customer_details_Temp.size()>0){


                for (int i =0 ;i<customer_details_Temp.size();i++)
                {
                    output_receipt_no = output_receipt_no +1;
                    // new Async_Get_Receiptno().execute();

                    Log.d("SAVE   RECEIPT NO : =================>", String.valueOf(output_receipt_no));
                    new Async_Save_Transaction().execute(customer_details_Temp.get(i).getTenantcode(),customer_details_Temp.get(i).getTenantname(),
                            customer_details_Temp.get(i).getTotal_amount(),customer_details_Temp.get(i).getStatuscode(),String.valueOf(output_receipt_no),String.valueOf(output_receipt_no),
                            customer_details_Temp.get(i).getShopcode(),customer_details_Temp.get(i).getShopname(),customer_details_Temp.get(i).getRentcycle(),customer_details_Temp.get(i).getShopstatus(),customer_details_Temp.get(i).getRent(),String.valueOf(date_db+" 00:00:00"));

                }



            call_refresh();
        }

    }

    private void call_refresh() {
        new Async_DailyCollection().execute();
    }

    public void calculateTotalDueAmount(){

        ArrayList<Customer_Details> temp = new ArrayList<>();
        final DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");
        int totalamt=0;

        for(int i=0;i<customer_details.size();i++){
            if(customer_details.get(i).isSelected()) {
                Integer amt = Integer.parseInt(customer_details.get(i).getDue_amount());
                totalamt = totalamt + amt;
                temp.add(customer_details.get(i));
            }
        }

        if(temp.size() ==1){
            if(TENANT_STATE_CODE.equals("")){
                TENANT_STATE_CODE = temp.get(0).getTenantcode();
                Log.d("TENaNT STATE CODE ==================>", temp.get(0).getTenantcode());
            }
        }
        if(temp.size() ==0){
            TENANT_STATE_CODE ="";
        }
        // TENANT_STATE_CODE =

        TOTAL_AMOUNT = totalamt;
        due_amount_tv.setText(String.valueOf(" \u20B9 "+TOTAL_AMOUNT));
    }


    public void calculateGrandAmount(){

        final DecimalFormat dft = new DecimalFormat("##,##,##,##,###.00");
        int totalamt=0,paid_amt = 0;

        for(int i=0;i<customer_details.size();i++){
            Integer amt = Integer.parseInt(customer_details.get(i).getDue_amount());
            totalamt = totalamt + amt;
        }

        for (int i=0;i<customer_details.size();i++){
            if(customer_details.get(i).getStatuscode() != null && !customer_details.get(i).getStatuscode().equals("null")){

                if(customer_details.get(i).getStatuscode().equals("10")){

                    Integer amt = Integer.parseInt(customer_details.get(i).getDue_amount());
                    paid_amt = paid_amt + amt;
                }else{

                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            expected_collection_tv.setText(String.valueOf(" \u20B9 "+totalamt));
            actual_collection_tv.setText(String.valueOf(" \u20B9 "+paid_amt));
        }

    }


    private String getReminingTime() {
        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }

//    public class Async_Get_Receiptno extends
//            AsyncTask<String, JSONObject, Boolean> {
//        JSONObject jsonObj;
//        JSONArray receiptcode_details_jsonarray;
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            //Try Block
//            try {
//                //Make webservice connection and call APi
//
//                RestAPI objRestAPI = new RestAPI();
//                //  if (networkstate) {
//                jsonObj = objRestAPI.GetReceiptno();
//                //  }
//            }
//            //Catch Block UserAuth true
//            catch (Exception e) {
//                Log.d("AsyncLoggerService", "Message");
//                Log.d("AsyncLoggerService", e.getMessage());
//
//            }
//            return true;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @SuppressLint("NewApi")
//        @Override
//        protected void onPostExecute(Boolean result) {
//
//            if (jsonObj == null || jsonObj.equals("")) {
//                // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                toast("Please check your internet connection");
//            } else {
//                String check_receipt_prefix ="";
//                String generate_code = "";
//                int num = 0;
//                generate_code  = String.format("%04d", num); // var is "001"
//
//                try {
//                    int success = jsonObj.getInt("success");
//                    if (success == 0) {
//
//                        receiptcode_details_jsonarray = jsonObj.getJSONArray("receiptcode_details");
//                        if(receiptcode_details_jsonarray.length() ==0) {
//                            output_receipt_no = Integer.parseInt(RECEIPT_PREFIX + generate_code);
//                            print_receipt_no = Integer.parseInt(RECEIPT_PREFIX + generate_code);
//                        }
//
//
//                    } else if (success == 1) {
//
//                        Log.d("API_GET RECEIPT RESPONSE =============ROW()==========>", "GET RECEIPT NO SUCCESSFULLY ");
//
//                        receiptcode_details_jsonarray = jsonObj.getJSONArray("receiptcode_details");
//
//                        if(receiptcode_details_jsonarray.length()>0){
//
//                            JSONObject jsonObject = receiptcode_details_jsonarray.getJSONObject(0);
//                            RECEIPT_NUMBER =jsonObject.getString("receiptno");
//
//                            Log.d("API_GET RECEIPT NUMBER =============ROW()==========>", jsonObject.getString("receiptcode"));
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//
//                if(!RECEIPT_NUMBER.equals("")){
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Log.d("MONTYL REPORT GERERNATE   RECEIPT NO===========>",generate_code+" GENERATED RECEIPT PREFIX  : "+RECEIPT_PREFIX);
//
//                    }
//                    if(RECEIPT_NUMBER.length()>=8){
//
//                        check_receipt_prefix = RECEIPT_NUMBER.substring(0,4);
//                        Log.d("API RECEIPT NUMBER PREFIX ===========>",generate_code+" API  RECEIPT PREFIX  : "+check_receipt_prefix);
//                    }
//
//                    if(RECEIPT_NUMBER.equals("0")){
//
//                        output_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
//                        print_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
//                    }
//
//                    if(!check_receipt_prefix.equals("")) {
//
//                        if (check_receipt_prefix.equals(RECEIPT_PREFIX)) {
//                            output_receipt_no = Integer.parseInt(RECEIPT_NUMBER);
//                            print_receipt_no = Integer.parseInt(RECEIPT_NUMBER);
//                        }
//
//                        if (!check_receipt_prefix.equals(RECEIPT_PREFIX)) {
//
//                            output_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
//                            print_receipt_no  = Integer.parseInt(RECEIPT_PREFIX+generate_code);
//                        }
//                    }
//
//
//                    Log.d("MONTHLY GENERATED RECEIPT NUMBER =======(OUTPUT)====>","   RECEIPT NO  : "+output_receipt_no);
//                }
//            }
//        }
//    }
//



    public class Async_Save_Transaction extends
            AsyncTask<String, JSONObject, Boolean> {
        JSONObject jsonObj;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                //  if (networkstate) {

                jsonObj = objRestAPI.GetSaveTransaction(params[0],params[1],params[2],params[2],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11]);
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



                    } else if (success == 1) {

                        Log.d("API_SAVE_RESPONSE =============ROW()==========>", "Saved Successfully");
                        // toast(" ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }

    }



    public class Async_Get_Receiptno extends
            AsyncTask<String, JSONObject, Boolean> {
        JSONObject jsonObj;
        JSONArray receiptcode_details_jsonarray;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                //  if (networkstate) {
                jsonObj = objRestAPI.GetReceiptno();
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
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(Boolean result) {

            if (jsonObj == null || jsonObj.equals("")) {
                // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                toast("Please check your internet connection");
            } else {
                String check_receipt_prefix ="";
                String generate_code = "";
                int num = 0;
                generate_code  = String.format("%04d", num); // var is "001"

                try {

                    int success = jsonObj.getInt("success");
                    if (success == 0) {
                        receiptcode_details_jsonarray = jsonObj.getJSONArray("receiptcode_details");
                        if(receiptcode_details_jsonarray.length() ==0) {
                            output_receipt_no = Integer.parseInt(RECEIPT_PREFIX + generate_code);
                            print_receipt_no = Integer.parseInt(RECEIPT_PREFIX + generate_code);
                        }
                    } else if (success == 1) {

                        Log.d("API_GET RECEIPT RESPONSE =============ROW()==========>", "GET RECEIPT NO SUCCESSFULLY ");
                        //toast(" ");

                        receiptcode_details_jsonarray = jsonObj.getJSONArray("receiptcode_details");

                        if(receiptcode_details_jsonarray.length()>0){

                            JSONObject jsonObject = receiptcode_details_jsonarray.getJSONObject(0);
                            RECEIPT_NUMBER =jsonObject.getString("receiptno");

                            Log.d("API_GET RECEIPT NUMBER =============ROW()==========>", jsonObject.getString("receiptcode"));

                        }else{

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                if(!RECEIPT_NUMBER.equals("")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Log.d("DALIY REPORT GERERNATE   RECEIPT NO===========>",generate_code+" GENERATED RECEIPT PREFIX  : "+RECEIPT_PREFIX);

                    }
                    if(RECEIPT_NUMBER.length()>=8){

                        check_receipt_prefix = RECEIPT_NUMBER.substring(0,4);
                        Log.d("API RECEIPT NUMBER PREFIX ===========>",generate_code+" API  RECEIPT PREFIX  : "+check_receipt_prefix);
                    }

                    if(RECEIPT_NUMBER.equals("0")){

                        output_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
                        print_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
                    }

                    if(!check_receipt_prefix.equals("")) {

                        if (check_receipt_prefix.equals(RECEIPT_PREFIX)) {
                            output_receipt_no = Integer.parseInt(RECEIPT_NUMBER);
                            print_receipt_no = Integer.parseInt(RECEIPT_NUMBER);
                        }

                        if (!check_receipt_prefix.equals(RECEIPT_PREFIX)) {
                            output_receipt_no = Integer.parseInt(RECEIPT_PREFIX+generate_code);
                            print_receipt_no  = Integer.parseInt(RECEIPT_PREFIX+generate_code);
                        }

                    }

                    Log.d("GENERATED RECEIPT NUMBER =======(OUTPUT)====>","   RECEIPT NO  : "+output_receipt_no);
                }
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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


    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow;
        Button button;
        CardView card_view,Show_more,Track;
        TextView city_name_tv,total_customer_tv;
        TextView tenant_name_tv,olddue_amount_tv,due_amount_tv,total_amount_tv,shop_no_tv,paid_tv;
        LinearLayout LL_card;

    }
}

