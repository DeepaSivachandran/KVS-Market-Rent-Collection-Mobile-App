package com.example.collectionreport;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Report_Activity extends AppCompatActivity {
    ImageView back_img,print_icon;
    LinearLayout hidden_layout;
    CardView report_card;
    ListView report_listview;
    Shift_Adapter shift_adapter;
    String selected_date="";
    ProgressBar progress_bar;
    TextView date_tv;
    ArrayList<shift_details> shift_details = new ArrayList<>();
    ArrayList<String> date_list = new ArrayList<>();
    String commaseparatedlist="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        back_img = (ImageView) findViewById(R.id.back_img);
        hidden_layout = (LinearLayout) findViewById(R.id.hidden_layout);
        report_card = (CardView) findViewById(R.id.report_card);
        print_icon = (ImageView) findViewById(R.id.print_icon);
        report_listview = (ListView) findViewById(R.id.report_listview);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        date_tv  = (TextView) findViewById(R.id.date_tv);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report_Activity.this.finish();
            }
        });

        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Run_Layout_Transmission();
                Intent i = new Intent(Report_Activity.this,Report_Shift_Activity.class);
                startActivity(i);
            }
        });

        print_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Report_Activity.this, "Print", Toast.LENGTH_SHORT).show();
            }
        });

        date_tv.setText(String.valueOf(MainActivity.strDate));

        //new Get_Limit_Date().execute();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());

       // String date_after = formateDateFromstring("yyyy-MM-dd", "dd, MMM yyyy", date_before);
        // String selectedDate =String.format("%02d-%02d-%d","1-8-2022");
       // Log.d("REPORT ACTIVITY ===============>",selectedDate);


        new Load_Shift_DB().execute("'"+MainActivity.strDate+"'");
       // new Load_Shift_DB().execute();

        //new Get_Shift_Details().execute();

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void Run_Layout_Transmission() {
        if (hidden_layout.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(report_card, new AutoTransition());
            } else {

            }
            hidden_layout.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(report_card, new AutoTransition());
            } else {

            }
            hidden_layout.setVisibility(View.VISIBLE);

            //arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
        }
    }


    public class Shift_Adapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<shift_details> shiftdetails;

        private ArrayList<Boolean> booleanList;
        private  boolean show=false;
        private String lastSelectPos;
        public Shift_Adapter(Context context,ArrayList<shift_details> master_details_active){
            this.context=context;
            this.shiftdetails =shift_details;
            this.layoutInflater =LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return shiftdetails.size();
        }

        @Override
        public Object getItem(int position) {
            return shiftdetails.get(position);
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
                view = layoutInflater.inflate(R.layout.report_shift_card, viewGroup, false);
                viewHolder = new ViewHolder();

                viewHolder.txt_shift_title = (TextView) view.findViewById(R.id.txt_shift_title);
                viewHolder.txt_date =(TextView) view.findViewById(R.id.txt_date);
                viewHolder.txt_time = (TextView) view.findViewById(R.id.txt_time);
                viewHolder.report_card =(CardView) view.findViewById(R.id.report_card);
                viewHolder.LL_shift_title  =(LinearLayout) view.findViewById(R.id.LL_shift_title);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.txt_shift_title.setText(String.valueOf("Shift "+shiftdetails.get(position).getShift_name()));
            viewHolder.txt_date.setText(shiftdetails.get(position).getShift_date());
            if(!shiftdetails.get(position).getShift_time().equals("")) {
            //    viewHolder.LL_shift_title.setBackground(R.color.report_header);
                viewHolder.txt_time.setText(shiftdetails.get(position).getShift_time());
            }else{
            //   viewHolder.LL_shift_title.setBackgroundColor(R.color.red);
                viewHolder.txt_time.setText(String.valueOf("Active"));
                viewHolder.txt_time.setTextColor(getResources().getColor(R.color.green));
            }

            viewHolder.report_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Report_Activity.this,Report_Shift_Activity.class);
                    i.putExtra("shiftcode",shiftdetails.get(position).getShift_code());
                    i.putExtra("shifttime",shiftdetails.get(position).getShift_time());
                    i.putExtra("shiftdate",shiftdetails.get(position).getShift_date());
                    startActivity(i);
                }
            });

            return view;
        }

    }


    public class Get_Limit_Date extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Report_Activity.this);
            baseAdapter.open();

            mCur = baseAdapter.Select_Limit(10);
            baseAdapter.close();

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
                    new Load_Shift_DB().execute(commaseparatedlist);
                }
            } else {

                Log.d("SHIFT_TABLE_DATELIST ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class Load_Shift_DB extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj;
        Cursor mCur;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            DataBaseAdapter baseAdapter = new DataBaseAdapter(Report_Activity.this);
            baseAdapter.open();

            mCur = baseAdapter.Select_Distinct(params[0]);
            baseAdapter.close();

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
            if (mCur.getCount() > 0) {
                shift_details.clear();
                String status;

                for (int i = 0; i < mCur.getCount(); i++) {
                    status ="";
                    char s_name = mCur.getString(0).charAt(8);
                    shift_details.add(new shift_details(String.valueOf(s_name),mCur.getString(0),mCur.getString(1),mCur.getString(2)));
                    Log.d("SHIFT_TABLE_DATA ======ROW("+i+")=========>",mCur.getString(2));
                    mCur.moveToNext();
                }
                shift_adapter = new Shift_Adapter(getApplicationContext(), shift_details);
                report_listview.setAdapter(shift_adapter);
                shift_adapter.notifyDataSetChanged();
            } else {
                report_listview.setAdapter(null);
                toast(" Report not found ");
                Log.d("SHIFT_TABLE_DATA_ERROR ===============>","No Data Available");
                // Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            }


        }
    }


    public void toast(String message)
    {
        Toast toast = new Toast(Report_Activity.this);
        View view = LayoutInflater.from(Report_Activity.this).inflate(R.layout.toast_custom, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    public void openDatePickerDialog(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal1.add(Calendar.DAY_OF_YEAR, -10);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    date_tv.setText(selectedDate);
                    selected_date = selectedDate;
                  // Load_Temp();
                    if(!selected_date.equals("")) {

                        new Load_Shift_DB().execute("'"+selected_date+"'");
                    }



                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

       // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
       // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

   void  Load_Temp(){
      /* ArrayList<shift_details> shift_details_temp = new ArrayList<>();
        for(int i=0;i<shift_details.size();i++){
            if(shift_details.get(i).getShift_date().equals(selected_date)){
                shift_details_temp.add(new shift_details(shift_details.get(i).getShift_name(),shift_details.get(i).getShift_code(),shift_details.get(i).getShift_date(),shift_details.get(i).getShift_time()));
            }
        }
        if(shift_details_temp != null) {
            shift_adapter = new Shift_Adapter(getApplicationContext(), shift_details_temp);
            report_listview.setAdapter(shift_adapter);
            shift_adapter.notifyDataSetChanged();
        }*/
    }

    public  class ViewHolder{
        public CardView report_card;
        ImageView arrow;
        Button button;
        CardView cardview,Show_more,Track;
        TextView city_name_tv,total_customer_tv;
        TextView txt_shift_title,txt_date,txt_time;
        LinearLayout LL_shift_title;
    }

    public class shift_details{
        String shift_name,shift_code,shift_date,shift_time;

        public shift_details(String shift_name, String shift_code, String shift_date, String shift_time) {
            this.shift_name = shift_name;
            this.shift_code = shift_code;
            this.shift_date = shift_date;
            this.shift_time = shift_time;
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
    }

}