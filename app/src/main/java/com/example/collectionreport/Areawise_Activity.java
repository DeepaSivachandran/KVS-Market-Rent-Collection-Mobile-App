package com.example.collectionreport;

import static com.example.collectionreport.MainActivity.master_details;
import static com.example.collectionreport.MainActivity.master_details_active;
import static com.example.collectionreport.MainActivity.master_details_inactive;
import static com.example.collectionreport.MainActivity.master_details_pending;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Areawise_Activity extends AppCompatActivity {
    ListView customer_listview;
    Customerlist_Adapter customerlist_adapter;
    public static TabLayout tabLayout;
    ViewPager viewPager;
    MyAdapter myAdapter;
    ImageView back_img;
    TextView title_city_tv,tot_customer_tv,active_customer_tv,pending_customer_tv,inactive_customer_tv,completed_customer_tv;;
    String City_name;

    public static ArrayList<master_details> customer_details = new ArrayList<>();
    public static ArrayList<master_details> customer_active_details = new ArrayList<>();
    public static ArrayList<master_details> customer_pending_details = new ArrayList<>();
    public static ArrayList<master_details> customer_inactive_details = new ArrayList<>();
    public static ArrayList<master_details> customer_completed_details = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areawise_activity);

        tabLayout = (TabLayout) findViewById(R.id.tabs_area);
        viewPager=(ViewPager)findViewById(R.id.viewpage_container);
        back_img = (ImageView) findViewById(R.id.back_img);
        title_city_tv =(TextView) findViewById(R.id.title_city_tv);

        tot_customer_tv = (TextView) findViewById(R.id.total_customer_tv);
        active_customer_tv  = (TextView) findViewById(R.id.active_customer_tv);
        pending_customer_tv  = (TextView) findViewById(R.id.pending_customer_tv);
        inactive_customer_tv  = (TextView) findViewById(R.id.inactive_customer_tv);

        completed_customer_tv = (TextView) findViewById(R.id.completed_customer_tv);

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Areawise_Activity.this.finish();
            }
        });

        try{
            Intent i = getIntent();
            City_name = i.getStringExtra("cityname");
            title_city_tv.setText(String.valueOf(City_name));
        } catch (Exception e) {
            e.printStackTrace();
        }

         new Async_Data().execute();
        //Toast.makeText(getApplicationContext(),City_name,Toast.LENGTH_SHORT).show();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            //SharedPreferences sharedPreferences1 = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            // int val = sharedPreferences1.getInt("Selected_tab",0);

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()){
                    case 0:
                        // Toast.makeText(getApplicationContext(),"sales",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Toast.makeText(getApplicationContext(),"order",Toast.LENGTH_SHORT).show();
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

        myAdapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount(),City_name);
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }



    public class Customerlist_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<String> arr;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Customerlist_Adapter(Context context,ArrayList<String> arr){
            this.context=context;
            this.arr=arr;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
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
                view = layoutInflater.inflate(R.layout.customer_card_list, viewGroup, false);
                viewHolder = new ViewHolder();

                viewHolder.customer_name = (TextView) view.findViewById(R.id.customer_name);
                viewHolder.area_name =(TextView) view.findViewById(R.id.area_name);
                viewHolder.acc_no = (TextView) view.findViewById(R.id.acc_no);
                viewHolder.mob_no = (TextView) view.findViewById(R.id.mob_no);

                viewHolder.customer_name.setText(arr.get(position));
                viewHolder.area_name.setText("");
                viewHolder.acc_no.setText("");
                viewHolder.mob_no.setText("");

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();


            }

            return view;
        }

    }

    public class Async_Data extends
            AsyncTask<String, JSONObject,Boolean> {


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block

            for(int i=0;i<master_details.size();i++){
                if(master_details.get(i).getCity_name().equals(City_name)) {
                    customer_details.add(new master_details(master_details.get(i).getAccountno(), master_details.get(i).getName(), master_details.get(i).getPhoneno(), master_details.get(i).getAddress1(), master_details.get(i).getAddress2(), master_details.get(i).getLasttransdt(),
                            master_details.get(i).getActype(), master_details.get(i).getAmount(), master_details.get(i).getMultiplicationamt(), master_details.get(i).getLimitamt(), master_details.get(i).getInsamt(), master_details.get(i).getDuedt(), master_details.get(i).getAg_code(), master_details.get(i).getAg_name(), master_details.get(i).getOptcl(),
                            master_details.get(i).getMinamt(), master_details.get(i).getMultiamtlimit(), master_details.get(i).getPeriod(), master_details.get(i).getInterestamt(), master_details.get(i).getTotalamt(), master_details.get(i).getPenaltyamt(), master_details.get(i).getInterestpaid(), master_details.get(i).getInterestdue(),
                            master_details.get(i).getLoanbaldue(), master_details.get(i).getRate(), master_details.get(i).getPendingpenalty(), master_details.get(i).getColinterest(), master_details.get(i).getTotinsttno(), master_details.get(i).getPaidinstno(), master_details.get(i).getEnableInst(), master_details.get(i).getDoorno(), master_details.get(i).getStreetName(),
                            master_details.get(i).getDownlaodFlag(), master_details.get(i).getCreatedOn(), master_details.get(i).getRemark(), master_details.get(i).getEmiNumber(), master_details.get(i).getCollectionTypeID(), master_details.get(i).getBranch_id(), master_details.get(i).getBranch_code(), master_details.get(i).getDDCode(), master_details.get(i).getInstPending(), master_details.get(i).getFineAmount(),
                            master_details.get(i).getDailyAmount(), master_details.get(i).getNoticeAmount(), master_details.get(i).getAmountinSus(), master_details.get(i).getIsLoan(), master_details.get(i).getCity_name(), master_details.get(i).getCity_code(), master_details.get(i).getStatus_code(),master_details.get(i).getBalance_amount(),master_details.get(i).getMaturity_amount(),master_details.get(i).getBILLNO(),master_details.get(i).getBILLDATE()));


                    if (master_details.get(i).getStatus_code().equals("1") && master_details.get(i).getBILLDATE().equals("null") && master_details.get(i).getBILLNO().equals("null")) {
                        customer_active_details.add(new master_details(master_details.get(i).getAccountno(), master_details.get(i).getName(), master_details.get(i).getPhoneno(), master_details.get(i).getAddress1(), master_details.get(i).getAddress2(), master_details.get(i).getLasttransdt(),
                                master_details.get(i).getActype(), master_details.get(i).getAmount(), master_details.get(i).getMultiplicationamt(), master_details.get(i).getLimitamt(), master_details.get(i).getInsamt(), master_details.get(i).getDuedt(), master_details.get(i).getAg_code(), master_details.get(i).getAg_name(), master_details.get(i).getOptcl(),
                                master_details.get(i).getMinamt(), master_details.get(i).getMultiamtlimit(), master_details.get(i).getPeriod(), master_details.get(i).getInterestamt(), master_details.get(i).getTotalamt(), master_details.get(i).getPenaltyamt(), master_details.get(i).getInterestpaid(), master_details.get(i).getInterestdue(),
                                master_details.get(i).getLoanbaldue(), master_details.get(i).getRate(), master_details.get(i).getPendingpenalty(), master_details.get(i).getColinterest(), master_details.get(i).getTotinsttno(), master_details.get(i).getPaidinstno(), master_details.get(i).getEnableInst(), master_details.get(i).getDoorno(), master_details.get(i).getStreetName(),
                                master_details.get(i).getDownlaodFlag(), master_details.get(i).getCreatedOn(), master_details.get(i).getRemark(), master_details.get(i).getEmiNumber(), master_details.get(i).getCollectionTypeID(), master_details.get(i).getBranch_id(), master_details.get(i).getBranch_code(), master_details.get(i).getDDCode(), master_details.get(i).getInstPending(), master_details.get(i).getFineAmount(),
                                master_details.get(i).getDailyAmount(), master_details.get(i).getNoticeAmount(), master_details.get(i).getAmountinSus(), master_details.get(i).getIsLoan(), master_details.get(i).getCity_name(), master_details.get(i).getCity_code(), master_details.get(i).getStatus_code(),master_details.get(i).getBalance_amount(),master_details.get(i).getMaturity_amount(),master_details.get(i).getBILLNO(),master_details.get(i).getBILLDATE()));
                        Log.d("CITYWISE ACTIVE CUSTOMER ===========>", String.valueOf(master_details.get(i).getName()+"===="+master_details.get(i).getAg_code()+"===="+master_details.get(i).getBalance_amount()));
                    } else if (master_details.get(i).getStatus_code().equals("2") && master_details.get(i).getBILLDATE().equals("null") && master_details.get(i).getBILLNO().equals("null")) {
                        customer_pending_details.add(new master_details(master_details.get(i).getAccountno(), master_details.get(i).getName(), master_details.get(i).getPhoneno(), master_details.get(i).getAddress1(), master_details.get(i).getAddress2(), master_details.get(i).getLasttransdt(),
                                master_details.get(i).getActype(), master_details.get(i).getAmount(), master_details.get(i).getMultiplicationamt(), master_details.get(i).getLimitamt(), master_details.get(i).getInsamt(), master_details.get(i).getDuedt(), master_details.get(i).getAg_code(), master_details.get(i).getAg_name(), master_details.get(i).getOptcl(),
                                master_details.get(i).getMinamt(), master_details.get(i).getMultiamtlimit(), master_details.get(i).getPeriod(), master_details.get(i).getInterestamt(), master_details.get(i).getTotalamt(), master_details.get(i).getPenaltyamt(), master_details.get(i).getInterestpaid(), master_details.get(i).getInterestdue(),
                                master_details.get(i).getLoanbaldue(), master_details.get(i).getRate(), master_details.get(i).getPendingpenalty(), master_details.get(i).getColinterest(), master_details.get(i).getTotinsttno(), master_details.get(i).getPaidinstno(), master_details.get(i).getEnableInst(), master_details.get(i).getDoorno(), master_details.get(i).getStreetName(),
                                master_details.get(i).getDownlaodFlag(), master_details.get(i).getCreatedOn(), master_details.get(i).getRemark(), master_details.get(i).getEmiNumber(), master_details.get(i).getCollectionTypeID(), master_details.get(i).getBranch_id(), master_details.get(i).getBranch_code(), master_details.get(i).getDDCode(), master_details.get(i).getInstPending(), master_details.get(i).getFineAmount(),
                                master_details.get(i).getDailyAmount(), master_details.get(i).getNoticeAmount(), master_details.get(i).getAmountinSus(), master_details.get(i).getIsLoan(), master_details.get(i).getCity_name(), master_details.get(i).getCity_code(), master_details.get(i).getStatus_code(),master_details.get(i).getBalance_amount(),master_details.get(i).getMaturity_amount(),master_details.get(i).getBILLNO(),master_details.get(i).getBILLDATE()));
                        Log.d("CITYWISE PENDING  CUSTOMER ===========>", String.valueOf(master_details.get(i).getName()+"===="+master_details.get(i).getAg_code()+"===="+master_details.get(i).getBalance_amount()));
                    } else {
                        if( master_details.get(i).getBILLDATE().equals("null") && master_details.get(i).getBILLNO().equals("null")) {

                            customer_inactive_details.add(new master_details(master_details.get(i).getAccountno(), master_details.get(i).getName(), master_details.get(i).getPhoneno(), master_details.get(i).getAddress1(), master_details.get(i).getAddress2(), master_details.get(i).getLasttransdt(),
                                    master_details.get(i).getActype(), master_details.get(i).getAmount(), master_details.get(i).getMultiplicationamt(), master_details.get(i).getLimitamt(), master_details.get(i).getInsamt(), master_details.get(i).getDuedt(), master_details.get(i).getAg_code(), master_details.get(i).getAg_name(), master_details.get(i).getOptcl(),
                                    master_details.get(i).getMinamt(), master_details.get(i).getMultiamtlimit(), master_details.get(i).getPeriod(), master_details.get(i).getInterestamt(), master_details.get(i).getTotalamt(), master_details.get(i).getPenaltyamt(), master_details.get(i).getInterestpaid(), master_details.get(i).getInterestdue(),
                                    master_details.get(i).getLoanbaldue(), master_details.get(i).getRate(), master_details.get(i).getPendingpenalty(), master_details.get(i).getColinterest(), master_details.get(i).getTotinsttno(), master_details.get(i).getPaidinstno(), master_details.get(i).getEnableInst(), master_details.get(i).getDoorno(), master_details.get(i).getStreetName(),
                                    master_details.get(i).getDownlaodFlag(), master_details.get(i).getCreatedOn(), master_details.get(i).getRemark(), master_details.get(i).getEmiNumber(), master_details.get(i).getCollectionTypeID(), master_details.get(i).getBranch_id(), master_details.get(i).getBranch_code(), master_details.get(i).getDDCode(), master_details.get(i).getInstPending(), master_details.get(i).getFineAmount(),
                                    master_details.get(i).getDailyAmount(), master_details.get(i).getNoticeAmount(), master_details.get(i).getAmountinSus(), master_details.get(i).getIsLoan(), master_details.get(i).getCity_name(), master_details.get(i).getCity_code(), master_details.get(i).getStatus_code(), master_details.get(i).getBalance_amount(), master_details.get(i).getMaturity_amount(), master_details.get(i).getBILLNO(), master_details.get(i).getBILLDATE()));
                            Log.d("CITYWISE INACTIVE CUSTOMER  ===========>", String.valueOf(master_details.get(i).getName() + "====" + master_details.get(i).getAg_code() + "====" + master_details.get(i).getBalance_amount()));
                        }
                    }
                    if( !master_details.get(i).getBILLDATE().equals("null") && !master_details.get(i).getBILLNO().equals("null")) {
                        customer_completed_details.add(new master_details(master_details.get(i).getAccountno(), master_details.get(i).getName(), master_details.get(i).getPhoneno(), master_details.get(i).getAddress1(), master_details.get(i).getAddress2(), master_details.get(i).getLasttransdt(),
                                master_details.get(i).getActype(), master_details.get(i).getAmount(), master_details.get(i).getMultiplicationamt(), master_details.get(i).getLimitamt(), master_details.get(i).getInsamt(), master_details.get(i).getDuedt(), master_details.get(i).getAg_code(), master_details.get(i).getAg_name(), master_details.get(i).getOptcl(),
                                master_details.get(i).getMinamt(), master_details.get(i).getMultiamtlimit(), master_details.get(i).getPeriod(), master_details.get(i).getInterestamt(), master_details.get(i).getTotalamt(), master_details.get(i).getPenaltyamt(), master_details.get(i).getInterestpaid(), master_details.get(i).getInterestdue(),
                                master_details.get(i).getLoanbaldue(), master_details.get(i).getRate(), master_details.get(i).getPendingpenalty(), master_details.get(i).getColinterest(), master_details.get(i).getTotinsttno(), master_details.get(i).getPaidinstno(), master_details.get(i).getEnableInst(), master_details.get(i).getDoorno(), master_details.get(i).getStreetName(),
                                master_details.get(i).getDownlaodFlag(), master_details.get(i).getCreatedOn(), master_details.get(i).getRemark(), master_details.get(i).getEmiNumber(), master_details.get(i).getCollectionTypeID(), master_details.get(i).getBranch_id(), master_details.get(i).getBranch_code(), master_details.get(i).getDDCode(), master_details.get(i).getInstPending(), master_details.get(i).getFineAmount(),
                                master_details.get(i).getDailyAmount(), master_details.get(i).getNoticeAmount(), master_details.get(i).getAmountinSus(), master_details.get(i).getIsLoan(), master_details.get(i).getCity_name(), master_details.get(i).getCity_code(), master_details.get(i).getStatus_code(), master_details.get(i).getBalance_amount(), master_details.get(i).getMaturity_amount(), master_details.get(i).getBILLNO(), master_details.get(i).getBILLDATE()));
                    }
                }

            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customer_details.clear();
            customer_active_details.clear();
            customer_pending_details.clear();
            customer_inactive_details.clear();
            customer_completed_details.clear();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try{
                tot_customer_tv.setText(String.valueOf(customer_details.size()));
                active_customer_tv.setText(String.valueOf(customer_active_details.size()));
                pending_customer_tv.setText(String.valueOf(customer_pending_details.size()));
                inactive_customer_tv.setText(String.valueOf(customer_inactive_details.size()));
                completed_customer_tv.setText(String.valueOf(customer_completed_details.size()));

               // Toast.makeText(getApplicationContext(),String.valueOf(customer_completed_details.size()),Toast.LENGTH_SHORT).show();

                // customer_completed_details
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public  class ViewHolder{
        public CardView city_card;
        ImageView arrow;
        Button button;
        CardView cardview,Show_more,Track;
        TextView customer_name,area_name,acc_no,mob_no;

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Areawise_Activity.this.finish();
    }
}
