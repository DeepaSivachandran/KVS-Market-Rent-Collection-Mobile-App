package com.example.collectionreport;

import static com.example.collectionreport.Dowload_SQLite_DB.ShareToAll_Above_Android7;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.instacart.library.truetime.TrueTimeRx;

public class DataBaseAdapter
{
    private final Context mContext;
    public static SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase database;
    public static final String _ID = "_id";
    public static final String MASTER_table = "master";
    public static final String TRANSACT_table = "transact";

    public static final String MASTER_accountno= "accountno";
    public static final String MASTER_name = "name";

    public static final String MASTER_Phone_no = "Phone_no";
    public static final String MASTER_address1 = "address1";
    public static final String MASTER_address2 = "address2";
    public static final String MASTER_lasttransdt = "lasttransdt";
    public static final String MASTER_actype = "actype";

    public static final String MASTER_amount = "amount";
    public static final String MASTER_multiplicationamt = "multiplicationamt";
    public static final String MASTER_limitamt = "limitamt";
    public static final String MASTER_insamt = "insamt";

    public static final String MASTER_opendt = "opendt";
    public static final String MASTER_duedt = "duedt";
    public static final String MASTER_ag_code = "ag_code";
    public static final String MASTER_ag_name = "ag_name";

    public static final String MASTER_optcl = "optcl";
    public static final String MASTER_minamt = "minamt";
    public static final String MASTER_multiamtlimit = "multiamtlimit";
    public static final String MASTER_period = "period";

    public static final String MASTER_interestamt = "interestamt";
    public static final String MASTER_totalamt= "totalamt";
    public static final String MASTER_penaltyamt = "penaltyamt";
    public static final String MASTER_interestpaid = "interestpaid";

    public static final String MASTER_interestdue = "interestdue";
    public static final String MASTER_loanbaldue= "loanbaldue";
    public static final String MASTER_rate = "rate";
    public static final String MASTER_pendingpenalty= "pendingpenalty";

    public static final String MASTER_colinterest = "colinterest";
    public static final String MASTER_Totinsttno= "Totinsttno";
    public static final String MASTER_Paidinstno= "Paidinstno";
    public static final String MASTER_EnableInst= "EnableInst";

    public static final String MASTER_Doorno = "Doorno";
    public static final String MASTER_StreetName= "StreetName";
    public static final String MASTER_DownlaodFlag= "DownlaodFlag";
    public static final String MASTER_CreatedOn= "CreatedOn";

    public static final String MASTER_Remark = "Remark";
    public static final String MASTER_EmiNumber= "EmiNumber";
    public static final String MASTER_CollectionTypeID= "CollectionTypeID";
    public static final String MASTER_branch_id= "branch_id";


    public static final String MASTER_Branch_code = "Branch_code";
    public static final String MASTER_DDCode= "DDCode";
    public static final String MASTER_InstPending= "InstPending";
    public static final String MASTER_FineAmount= "FineAmount";

    public static final String MASTER_DailyAmount = "DailyAmount";
    public static final String MASTER_NoticeAmount= "NoticeAmount";
    public static final String MASTER_AmountinSus= "AmountinSus";
    public static final String MASTER_IsLoan= "IsLoan";
    public static final String MASTER_city_name= "city_name";
    public static final String MASTER_city_code= "city_code";
    public static final String MASTER_status_code= "status_code";

    public static final String MASTER_balance_amount = "balance_amount";
    public static final String MASTER_maturity_amount = "maturity_amount";

    public static final String MASTER_billno = "BILLNO";
    public static final String MASTER_billdate = "BILLDATE";
    public static final String TRANSACT_sno = "sno";
    public static final String TRANSACT_acno = "acno";
    public static final String TRANSACT_receiptno = "receiptno";
    public static final String TRANSACT_ag_code = "ag_code";
    public static final String TRANSACT_actype = "actype";
    public static final String TRANSACT_modepay = "modepay";
    public static final String TRANSACT_palmtec_id = "palmtec_id";
    public static final String TRANSACT_cancel = "cancel";
    public static final String TRANSACT_tr_time = "tr_time";
    public static final String TRANSACT_chequedt = "chequedt";
    public static final String TRANSACT_chequeno = "chequeno";
    public static final String TRANSACT_collectdt = "collectdt";
    public static final String TRANSACT_collectamt = "collectamt";
    public static final String TRANSACT_remark = "remark";

    public static final String TRANSACT_shift_code = "shift_code";
    public static final String TRANSACT_shift_date = "shift_date";
    public static final String TRANSACT_close_time = "close_time";
    public static final String TRANSACT_close_status = "close_status";
    public static final String TRANSACT_sync_status = "sync_status";

    public static final String TRANSACT_customer_type = "customer_type";

    public static final String TRANSACT_receiptcode = "receipt_code";

    public static final String TRANSACT_name= "name";
    public static final String TRANSACT_phoneno = "phoneno";
    public static final String TRANSACT_city = "city";
    public static final String TRANSACT_customerno= "customerno";



    DecimalFormat dft = new DecimalFormat("0.00");
    public String statusvar = "Active";
    public String getdate="";
    public  static  String SELECT_QUERY="";

    //Common function for database
    public DataBaseAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataBaseAdapter open() throws SQLException {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();

        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public String insert(String login_pk, String agent, String code, String password, String isadmin, String login_status, String branch_id, String remit_user, String device_id, String passcode,String receiptno_max,String customerno_max) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DataBaseHelper.LOGIN_PK, login_pk);
        contentValue.put(DataBaseHelper.CODE, code);
        contentValue.put(DataBaseHelper.AGENT, agent);
        contentValue.put(DataBaseHelper.PASSWORD, password);
        contentValue.put(DataBaseHelper.ISADMIN, isadmin);
        contentValue.put(DataBaseHelper.LOGIN_STATUS, login_status);
        contentValue.put(DataBaseHelper.BRANCH_ID, branch_id);
        contentValue.put(DataBaseHelper.REMIT_USER, remit_user);
        contentValue.put(DataBaseHelper.DEVICE_ID, device_id);
        contentValue.put(DataBaseHelper.PASSCODE, passcode);
        contentValue.put(DataBaseHelper.RECEIPTNO_MAX, receiptno_max);
        contentValue.put(DataBaseHelper.CUSTOMERNO_MAX,customerno_max);



        mDb.insert(DataBaseHelper.TABLE_NAME, null, contentValue);

        return "success";
    }

    public String insert_settings(String sno,String company_name,String city_name, String phone_no, String delete_cycle, String limit_date,String schedule_date,String temp_date) {
        ContentValues contentValue = new ContentValues();

        contentValue.put("sno", sno);
        contentValue.put("company_name", company_name);
        contentValue.put("city_name", city_name);
        contentValue.put("phone_no", phone_no);
        contentValue.put("delete_cycle", delete_cycle);
        contentValue.put("limit_date", limit_date);

        contentValue.put("schedule_date", schedule_date);
       // contentValue.put("temp_date", temp_date);

        mDb.insert("settings", null, contentValue);

        return "success";
    }

    public String insert_master(String accountno, String name, String phoneno, String address1, String address2,
                                String lasttransdt, String actype, String amount, String multiplicationamt,
                                String limitamt,String insamt,String duedt,String ag_code,String ag_name,
                                String optcl,String minamt,String multiamtlimit,String period,String interestamt,String totalamt,
                                String penaltyamt,String interestpaid,String interestdue,String loanbaldue,String rate,String pendingpenalty,
                                String colinterest,String Totinsttno,String Paidinstno,String EnableInst,String Doorno,String StreetName,
                                String DownlaodFlag,String CreatedOn,String Remark,String EmiNumber,String CollectionTypeID,String branch_id,
                                String Branch_code,String DDCode,String InstPending,String FineAmount,String DailyAmount,String NoticeAmount,
                                String AmountinSus,String IsLoan,String city_code,String city_name,String status_code,String balance_amount,String maturity_amount,String billno,String billdate) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(MASTER_accountno, accountno);
        contentValue.put(MASTER_name, name);
        contentValue.put(MASTER_Phone_no, phoneno);

        contentValue.put(MASTER_address1, address1);
        contentValue.put(MASTER_address2, address2);
        contentValue.put(MASTER_lasttransdt, lasttransdt);
        contentValue.put(MASTER_actype, actype);

        contentValue.put(MASTER_amount, amount);
        contentValue.put(MASTER_multiplicationamt, multiplicationamt);
        contentValue.put(MASTER_limitamt, limitamt);
        contentValue.put(MASTER_insamt, insamt);
        contentValue.put(MASTER_duedt, duedt);

        contentValue.put(MASTER_ag_code, ag_code);
        contentValue.put(MASTER_ag_name, ag_name);
        contentValue.put(MASTER_optcl, optcl);
        contentValue.put(MASTER_minamt, minamt);
        contentValue.put(MASTER_multiamtlimit, multiamtlimit);

        contentValue.put(MASTER_period, period);
        contentValue.put(MASTER_interestamt, interestamt);
        contentValue.put(MASTER_totalamt, totalamt);
        contentValue.put(MASTER_penaltyamt, penaltyamt);
        contentValue.put(MASTER_interestpaid, interestpaid);

        contentValue.put(MASTER_interestdue, interestdue);
        contentValue.put(MASTER_loanbaldue, loanbaldue);
        contentValue.put(MASTER_rate, rate);
        contentValue.put(MASTER_pendingpenalty, pendingpenalty);

        contentValue.put(MASTER_colinterest, colinterest);
        contentValue.put(MASTER_Totinsttno, Totinsttno);
        contentValue.put(MASTER_Paidinstno, Paidinstno);
        contentValue.put(MASTER_EnableInst, EnableInst);
        contentValue.put(MASTER_Doorno, Doorno);

        contentValue.put(MASTER_StreetName, StreetName);
        contentValue.put(MASTER_DownlaodFlag, DownlaodFlag);
        contentValue.put(MASTER_CreatedOn, CreatedOn);
        contentValue.put(MASTER_Remark, Remark);
        contentValue.put(MASTER_EmiNumber, EmiNumber);

        contentValue.put(MASTER_CollectionTypeID, CollectionTypeID);
        contentValue.put(MASTER_branch_id, branch_id);
        contentValue.put(MASTER_Branch_code, Branch_code);
        contentValue.put(MASTER_DDCode, DDCode);
        contentValue.put(MASTER_InstPending, InstPending);

        contentValue.put(MASTER_FineAmount, FineAmount);
        contentValue.put(MASTER_DailyAmount, DailyAmount);
        contentValue.put(MASTER_NoticeAmount, NoticeAmount);
        contentValue.put(MASTER_AmountinSus, AmountinSus);
        contentValue.put(MASTER_IsLoan, IsLoan);

        contentValue.put(MASTER_city_name, city_name);
        contentValue.put(MASTER_city_code, city_code);
        contentValue.put(MASTER_status_code, status_code);

        contentValue.put(MASTER_balance_amount, balance_amount);
        if(maturity_amount != null) {
            contentValue.put(MASTER_maturity_amount, maturity_amount);
        }else{
            contentValue.put(MASTER_maturity_amount, "null");
        }
        if(billno != null){
            contentValue.put(MASTER_billno, billno);
        }else{
            contentValue.put(MASTER_billno, "null");
        }
       if(billdate != null){
           contentValue.put(MASTER_billdate, billdate);
        }else{
           contentValue.put(MASTER_billdate, "null");
       }
        mDb.insert(DataBaseHelper.TABLE_MASTER, null, contentValue);

        return "success";
    }

    public String insert_transact(String sno, String acno,String receiptno, int receipt_code, String ag_code, String actype, String modepay, String palmtec_id,
                                  String cancel, String tr_time,String chequedt,String chequeno,String collectdt,String collectamt,String remark,String shift_code,String shift_date ,String close_time,int close_status,int sync_status,int customer_type,String name,String phoneno,String city,int customerno) {
        Cursor cursor;
        String receiptno_max="";
       /* cursor = mDb.rawQuery("SELECT coalesce(max("+TRANSACT_receiptno+")+1,0) as receiptno FROM "+TRANSACT_table, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                receiptno_max = cursor.getString(0);
            }
        }*/
        ContentValues contentValue = new ContentValues();
        contentValue.put(TRANSACT_sno, sno);
        contentValue.put(TRANSACT_acno, acno);
        contentValue.put(TRANSACT_receiptno, receiptno);
        contentValue.put(TRANSACT_receiptcode,receipt_code);

        contentValue.put(TRANSACT_ag_code, ag_code);
        contentValue.put(TRANSACT_actype , actype);
        contentValue.put(TRANSACT_modepay,modepay);
        contentValue.put(TRANSACT_palmtec_id, palmtec_id);
        contentValue.put(TRANSACT_cancel, cancel);
        contentValue.put(TRANSACT_tr_time, tr_time);
        contentValue.put(TRANSACT_chequedt, chequedt);
        contentValue.put(TRANSACT_chequeno, chequeno);
        contentValue.put(TRANSACT_collectdt, collectdt);
        contentValue.put(TRANSACT_collectamt, collectamt);
        contentValue.put(TRANSACT_remark, remark);

        contentValue.put(TRANSACT_shift_code, shift_code);

        contentValue.put(TRANSACT_shift_date, shift_date);
        contentValue.put(TRANSACT_close_time, close_time);

        contentValue.put(TRANSACT_close_status, close_status);
        contentValue.put(TRANSACT_sync_status, sync_status);
        contentValue.put(TRANSACT_customer_type, customer_type);

        contentValue.put(TRANSACT_name, name);
        contentValue.put(TRANSACT_phoneno, phoneno);
        contentValue.put(TRANSACT_city, city);
        contentValue.put(TRANSACT_customerno,customerno);


        mDb.insert(TRANSACT_table, null, contentValue);
        Log.d("TRANSACT_TABLE ========DATA=======>",String.valueOf(receiptno_max));
        return "success";
    }

    public  String insert_shift(String s_name,String s_code,String date,String time,double cash_collectamt,int cr_2000,int ct_2000,int cr_500,int ct_500,
                                int cr_200,int ct_200,int cr_100,int ct_100, int cr_50,int ct_50,int cr_20,int ct_20,int cr_10,int ct_10,int cr_5,int ct_5,
                                int coins,int total_cash){
        ContentValues contentValue = new ContentValues();
        contentValue.put("shift_name", s_name);
        contentValue.put("shift_code", s_code);
        contentValue.put("shift_date", date);
        contentValue.put("shift_time", time);

        contentValue.put("cash_collectamt", cash_collectamt);
        contentValue.put("cr_2000", cr_2000);
        contentValue.put("ct_2000", ct_2000);
        contentValue.put("cr_500", cr_500);
        contentValue.put("ct_500", ct_500);
        contentValue.put("cr_200", cr_200);
        contentValue.put("ct_200", ct_200);
        contentValue.put("cr_100", cr_100);
        contentValue.put("ct_100", ct_100);
        contentValue.put("cr_50", cr_50);
        contentValue.put("ct_50", ct_50);
        contentValue.put("cr_20", cr_20);
        contentValue.put("ct_20", ct_20);
        contentValue.put("cr_10", cr_10);
        contentValue.put("ct_10", ct_10);
        contentValue.put("cr_5", cr_5);
        contentValue.put("ct_5", ct_5);
        contentValue.put("coins", coins);
        contentValue.put("total_cash", total_cash);


        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);
        mDb.insert("shift", null, contentValue);
        //cursor = database.rawQuery("select * from login" ,null);

        return "success";
    }

    public Cursor fetch() {
        String[] columns = new String[] { DataBaseHelper._ID, DataBaseHelper.SUBJECT, DataBaseHelper.DESC };
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.SUBJECT, name);
        contentValues.put(DataBaseHelper.DESC, desc);
        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._ID + " = " + _id, null);
        return i;
    }

    public  Cursor Select(String device_id){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS,DataBaseHelper.CODE,DataBaseHelper.DEVICE_ID};
        SELECT_QUERY ="SELECT * FROM login" ;
       // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
         database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
        cursor = database.query(DataBaseHelper.TABLE_NAME, columns, DataBaseHelper.DEVICE_ID+"=?", new String[] { device_id }, null, null, null);

        //cursor = database.rawQuery("select * from login" ,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        /*if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                    String name= cursor.getString(cursor.getColumnIndex(DataBaseHelper.AGENT));
                    String passcode = cursor.getString(cursor.getColumnIndex(DataBaseHelper.PASSCODE));

                }while(cursor.moveToNext());
            }
        }*/

        return cursor;
    }

    public  Cursor Select_Master(){
        Cursor cursor;
        SELECT_QUERY ="SELECT * FROM login" ;
        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
       // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
        cursor = mDb.rawQuery("SELECT * FROM "+MASTER_table, null);
        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor != null) {

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        }
        /*if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                    String name= cursor.getString(cursor.getColumnIndex(DataBaseHelper.AGENT));
                    String passcode = cursor.getString(cursor.getColumnIndex(DataBaseHelper.PASSCODE));

                }while(cursor.moveToNext());
            }
        }*/
        return cursor;
    }
    public  Cursor Select_Login(){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};
        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
        cursor = mDb.rawQuery("SELECT * FROM login", null);
        //cursor = database.rawQuery("select * from login" ,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public  Cursor Select_City(){
        Cursor cursor= null;
        try {
           // cursor = mDb.rawQuery("SELECT * FROM city", null);
            cursor = mDb.rawQuery("   SELECT _id,coalesce(sno,0) as sno ,city_name,total_customer FROM city", null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public  Cursor Select_Settings(){
        Cursor cursor= null;
        try {
            // cursor = mDb.rawQuery("SELECT * FROM city", null);
            cursor = mDb.rawQuery("SELECT * FROM settings", null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    public  String Update_Settings(String req){
        Cursor cursor = null;

        if(req.equals("null")) {

            cursor = mDb.rawQuery("UPDATE settings SET temp_date =(SELECT (MAX(settings.temp_date)+1) as temp_date FROM settings) WHERE settings.sno = 1", null);
        }
        if(req.equals("reset")) {
            cursor = mDb.rawQuery("UPDATE settings SET temp_date ='1' WHERE settings.sno = 1", null);
        }
        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        }
        return "success";
    }
    public  String insert_city(String city_name, String total_customer){
        ContentValues contentValue = new ContentValues();
        if(city_name != null) {
            contentValue.put("city_name", city_name);
        }else{
            contentValue.put("city_name", "null");
        }
        if(total_customer != null) {
            contentValue.put("total_customer", total_customer);
        }else{
            contentValue.put("total_customer", "0");
        }
        mDb.insert("city", null, contentValue);

        return "success";
    }

    public  Cursor Select_Transact(){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
       // cursor = mDb.rawQuery("SELECT * FROM "+TRANSACT_table, null);
        cursor = mDb.rawQuery("SELECT _id,sno,coalesce(acno,'null') as acno,receiptno,coalesce(ag_code,'null') as ag_code,coalesce(actype,'null') as actype,coalesce(modepay,'null') as modepay,palmtec_id,coalesce(cancel,'null') as cancel,coalesce(tr_time,'null') as tr_time,coalesce(chequedt,'null') as chequedt,coalesce(chequeno,'null') as chequeno,coalesce(collectdt,'null') as collectdt,coalesce(collectamt,'null') as collectamt,coalesce(remark,'null') as remark,shift_code,shift_date,coalesce(close_time,'null') as close_time,close_status,sync_status,coalesce(receipt_code,'null') as receipt_code,coalesce(customer_type,0) as customer_type,coalesce(name,'null') as customer_name,coalesce(phoneno,'null') as phoneno,coalesce(city,'null') as city,coalesce(customerno,'null') as customerno  FROM transact", null);
        //cursor = database.rawQuery("select * from login" ,null);
       // cursor = mDb.rawQuery("SELECT _id,sno,acno,receiptno,ag_code,actype,modepay,palmtec_id,cancel,tr_time ,chequedt,chequeno,collectdt,collectamt,remark,shift_code,shift_date,close_time,close_status,sync_status,receipt_code,coalesce(customer_type,0) as customer_type  FROM transact",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  Cursor Get_Receipt(String ag_code){
        Cursor cursor=null;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
      //  cursor = mDb.rawQuery("SELECT (SELECT (CASE WHEN COUNT(a.sno)>0 THEN ((SELECT l.login_pk FROM login as l where l.code='"+ag_code+"') || substr('0000'||(coalesce(MAX(a.receiptno)+1,0)), -4, 4)) ELSE ((SELECT l.login_pk FROM login as l where l.code='"+ag_code+"') || substr('0000'||1, -4, 4)) END)) as receipt_code  FROM transact as a", null);
        cursor = mDb.rawQuery("SELECT (SELECT (CASE WHEN COUNT(a.sno)>0 THEN ((SELECT l.login_pk FROM login as l where l.code='"+ag_code+"') || substr('0000'||(coalesce(MAX(a.receiptno)+1,0)), -4, 4)) ELSE ((SELECT l.login_pk FROM login as l where l.code='"+ag_code+"') || substr('0000'|| (SELECT receiptno_max+1 FROM login), -4, 4)) END)) as receipt_code,(SELECT (CASE WHEN COUNT(a.sno)>0 THEN(coalesce((SELECT MAX(a.receiptno)+1),0)) ELSE coalesce((SELECT receiptno_max+1 FROM login),0) END)) as receipt_no, strftime('%d-%m-%Y','now') as receiptdt,(SELECT (CASE WHEN COUNT(a.sno)>0 THEN (SELECT coalesce(MAX(a.customerno)+1,0)) ELSE (SELECT l.customerno_max+1 FROM login as l where l.code='"+ag_code+"') END)) as customerno_max FROM transact as a", null);

        //cursor = database.rawQuery("select * from login" ,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  Cursor Select_Shift(String s_code){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);

       // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);
        if(s_code.equals("null")){
            cursor = mDb.rawQuery("SELECT * FROM shift", null);
        }else {
            cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = '" + s_code + "'", null);
        }
        //cursor = database.rawQuery("select * from login" ,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  String Cash_Close(String close_time){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);

        // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);

            cursor = mDb.rawQuery("UPDATE transact SET  close_time = '"+close_time+"',close_status = 1 where close_status = 0", null);

        //cursor = database.rawQuery("select * from login" ,null);
         if(cursor !=null) {
              if (cursor.getCount() > 0) {
                  cursor.moveToFirst();
               }
           }
        return "success";
    }

    public  String Delete_transaction(String datelist){
        Cursor cursor;

        cursor = mDb.rawQuery("DELETE FROM transact  WHERE collectdt NOT IN("+datelist+") AND sync_status = 1", null);

        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        }
        return "success";
    }

    public  String Update_Transact(int receipt_no,String request){
        Cursor cursor=null;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);

        // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);
         if(request.equals("sync")) {
             cursor = mDb.rawQuery("UPDATE transact SET  sync_status = 1 where receiptno = " + receipt_no, null);
         }
        if(request.equals("cancel")) {
            cursor = mDb.rawQuery("UPDATE transact SET  cancel = 'Y' where receipt_code = '" + String.valueOf(receipt_no)+"'", null);
        }

        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor != null) {

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        }
        return "success";
    }
    public  Cursor Select_Distinct(String datelist){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);

        // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);

       // cursor = mDb.rawQuery("SELECT DISTINCT(v.shift_code),v.collectdt,v.close_time FROM transact as v  ORDER BY collectdt DESC", null);
        cursor = mDb.rawQuery("SELECT DISTINCT(v.shift_code),v.collectdt,v.close_time FROM transact as v  WHERE v.collectdt IN("+datelist+") ORDER BY v.collectdt DESC", null);
        //cursor = database.rawQuery("select * from login" ,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  Cursor Select_Transact_Cutomer(String shift_code,String check_date){
        Cursor cursor =null;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
       // cursor = mDb.rawQuery("SELECT * FROM "+ TRANSACT_table +" WHERE shift_code ='"+shift_code+"'", null);
        if(check_date.equals("null")) {
            cursor = mDb.rawQuery("SELECT v.acno,v.actype,v.modepay,v.tr_time,v.collectdt,v.collectamt," +
                    "coalesce((SELECT c.name FROM master as c WHERE c.accountno = v.acno),'null') as customer_name," +
                    "coalesce((SELECT c.city_name FROM master as c WHERE c.accountno = v.acno),'null') as city_name,v.customer_type,v.receipt_code,v.close_status,v.cancel,(v.receipt_code || ' ' || v.collectdt) as  receipt,(SELECT c.Phone_no FROM master as c WHERE c.accountno = v.acno) as Phone_no,(SELECT c.amount FROM master as c WHERE c.accountno = v.acno) as prvs_amount,(SELECT c.ag_code FROM master as c WHERE c.accountno = v.acno) as agent_code,v.remark,v.name,v.phoneno,v.city FROM transact  as v WHERE v.shift_code ='" + shift_code + "'", null);
        }
        //cursor = database.rawQuery("select * from login" ,null);
         if(!check_date.equals("null") && !check_date.equals("")) {

          cursor = mDb.rawQuery("SELECT v.acno,v.actype,v.modepay,v.tr_time,v.collectdt,v.collectamt," +
                  "coalesce((SELECT c.name FROM master as c WHERE c.accountno = v.acno),'null') as customer_name," +
                  "coalesce((SELECT c.city_name FROM master as c WHERE c.accountno = v.acno),'null') as city_name,v.customer_type,v.receipt_code,v.close_status,v.cancel,(v.receipt_code || ' ' || v.collectdt) as  receipt,(SELECT c.Phone_no FROM master as c WHERE c.accountno = v.acno) as Phone_no,(SELECT c.amount FROM master as c WHERE c.accountno = v.acno) as prvs_amount,(SELECT c.ag_code FROM master as c WHERE c.accountno = v.acno) as agent_code,v.remark,v.name,v.phoneno,v.city FROM transact  as v WHERE v.collectdt='" + check_date + "' ", null);
         }
         if(cursor !=null) {
             if (cursor.getCount() > 0) {
                 cursor.moveToFirst();
             }
         }
        return cursor;
    }

    public  Cursor Select_Limit(int limit ){
        Cursor cursor =null;

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);
        // cursor = mDb.rawQuery("SELECT * FROM "+ TRANSACT_table +" WHERE shift_code ='"+shift_code+"'", null);
        if(limit != 0) {
            cursor = mDb.rawQuery("SELECT v.collectdt as collectdt FROM transact as v GROUP BY  v.collectdt ORDER BY v.collectdt DESC LIMIT "+limit,null);
        }else{
            cursor = mDb.rawQuery("",null);
        }
        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        }
        return cursor;
    }
    public  Cursor Last_Shift(String req){
        Cursor cursor;
        String[] columns = {DataBaseHelper.AGENT,DataBaseHelper.PASSCODE,DataBaseHelper.LOGIN_STATUS};

        // SELECT_QUERY ="SELECT agent FROM login WHERE deviceid =" + device_id;
        // database = mDbHelper.getReadableDatabase();
        // cursor = database.rawQuery(SELECT_QUERY,null);

        // cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code = "+ s_code, null);
        if(req !=null){
            cursor = mDb.rawQuery("SELECT * FROM shift WHERE shift_code='"+req+"'", null);
        }else {
            cursor = mDb.rawQuery("SELECT * FROM shift", null);
        }
        //cursor = database.rawQuery("select * from login" ,null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
            }
        }
        return cursor;
    }

    public  Cursor Search_Name(String name){

        Cursor cursor;
        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(name) LIKE '%"+name+"%'", null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }else{
                cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(Phone_no) LIKE '"+name+"%'", null);
                if(cursor !=null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }else{
                        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(accountno) LIKE '"+name+"%'", null);
                        if(cursor !=null) {
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                            }
                        }
                    }
                }
            }

        }
        return cursor;
    }

    public  Cursor Active_Search(String name,String cityname){

        Cursor cursor;
        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(name) LIKE '%"+name+"%' AND status_code='1' AND city_name='"+cityname+"'", null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }else{
                cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(Phone_no) LIKE '"+name+"%' AND status_code='1' AND city_name='"+cityname+"'", null);
                if(cursor !=null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }else{
                        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(accountno) LIKE '"+name+"%' AND status_code='1' AND city_name='"+cityname+"'", null);
                        if(cursor !=null) {
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                            }
                        }
                    }
                }
            }

        }
        return cursor;
    }

    public  Cursor Pending_Search(String name,String cityname){

        Cursor cursor;
        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(name) LIKE '%"+name+"%' AND status_code='2'AND city_name='"+cityname+"'", null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }else{
                cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(Phone_no) LIKE '"+name+"%' AND status_code='2' AND city_name='"+cityname+"'", null);
                if(cursor !=null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }else{
                        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(accountno) LIKE '"+name+"%' AND status_code='2' AND city_name='"+cityname+"'", null);
                        if(cursor !=null) {
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                            }
                        }
                    }
                }
            }

        }
        return cursor;
    }

    public  Cursor Inactive_Search(String name,String cityname){

        Cursor cursor;
        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(name) LIKE '%"+name+"%' AND status_code='0' AND city_name='"+cityname+"'", null);
        if(cursor !=null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }else{
                cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(Phone_no) LIKE '"+name+"%' AND status_code='0' AND city_name='"+cityname+"'", null);
                if(cursor !=null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }else{
                        cursor = mDb.rawQuery("SELECT * FROM master WHERE lower(accountno) LIKE '"+name+"%' AND status_code='0' AND city_name='"+cityname+"'", null);
                        if(cursor !=null) {
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                            }
                        }
                    }
                }
            }

        }
        return cursor;
    }
    public int delete() {
        int result=0;
        long taskCount = DatabaseUtils.queryNumEntries(mDb, DataBaseHelper.TABLE_NAME);
        if(taskCount !=0) {
            int success = mDb.delete(DataBaseHelper.TABLE_NAME, "1", null);
            if(success>0){
                result = 1;
            }
            mDb.execSQL("VACUUM");
        }else{
            result=2;
            mDb.execSQL("VACUUM");
        }

        //database.execSQL("DELETE FROM login");
        return  result;
    }


    public int delete_settings() {
        int result=0;
        long taskCount = DatabaseUtils.queryNumEntries(mDb, "settings");
        if(taskCount !=0) {
            int success = mDb.delete("settings", "1", null);
            if(success>0){
                result = 1;
            }
            mDb.execSQL("VACUUM");
        }else{
            result=2;
            mDb.execSQL("VACUUM");
        }

        //database.execSQL("DELETE FROM login");
        return  result;
    }

    public int delete_master() {
        int result=0;
        long taskCount = DatabaseUtils.queryNumEntries(mDb,MASTER_table);
        if(taskCount !=0) {
            int success = mDb.delete(MASTER_table, "1", null);
            if(success>0){
                result = 1;
            }
            mDb.execSQL("VACUUM");
        }else{
            result=2;
            mDb.execSQL("VACUUM");
        }

        //database.execSQL("DELETE FROM login");
        return  result;
    }

    public int delete_city() {
        int result=0;
        long taskCount = DatabaseUtils.queryNumEntries(mDb,"city");
        if(taskCount !=0) {
            int success = mDb.delete("city", "1", null);
            if(success>0){
                result = 1;
            }
            mDb.execSQL("VACUUM");
        }else{
            result=2;
            mDb.execSQL("VACUUM");
        }

        //database.execSQL("DELETE FROM login");
        return  result;
    }

    public  String Download_DB(){

       /* String DatabaseName = "dbcollectionreport.db";
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/data/" + mContext.getPackageName() + "/databases/";
      //  String currentDBPath = "/data/"+ "com.example.collectionreport" +"/databases/"+DataBaseHelper.DB_NAME ;
        String backupDBPath = "dbcollectionreport.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(mContext, "Your Database is Exported !!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }*/
      // ShareToAll_Above_Android7(,);

        return "";
    }


}
