package com.example.collectionreport.sunmi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SunMiPrinter {

    //Printer Details
    public String printerIP;
    public String printerPort;
    public String printerHeaderText="";
    public String printerFooterText;
    Bitmap bitmap;
    Drawable drawable;
    Context mContext;
    String line_space ="----------------------------";
    public boolean SendDataToPrinter(String gettransactiono, String getvoucherno) {
        boolean billPrinted = false;

        printText(26, true, 1, true, true, printerHeaderText);
        printText(24, false, 1, true, true, "Hello ");
        printText(24, false, 1, true, false, line_space);
 /*       printText(24, true, 0, false, false, Utilities.getStringWithPadding("Bill : " + mCur.getString(1), 12, false, false));
        printText(24, true, 0, true, false, simple.format(result) + " " + mCur.getString(7));
        printText(24, false, 1, true, false, line_space);
        printText(24, true, 0, true, false, "Item Name   Qty   Rate   Amount");
        printText(24, false, 1, true, false, line_space);*/


        billPrinted = true;

        return billPrinted;
    }

    public void printText(int fontSize, boolean isBold, int alignMode, boolean isNeedLF, boolean isNeedLS, String textToPrint) {
        try {
            /*
            isbold - true, isnotbold - false
            leftalign - 0, rightalign - 2, centeralign - 1
             */
            SunmiPrintHelper.getInstance().setAlign(alignMode);
            SunmiPrintHelper.getInstance().printText(textToPrint, fontSize, isBold, false);
            if (isNeedLF)
                SunmiPrintHelper.getInstance().printLineWrap();

        } catch (Exception ex) {

        }

    }

    public void printT2MiniText(int fontSize, boolean isBold,int alignMode, boolean isNeedLF, boolean isNeedLS, String textToPrint){
        try{
            //if (!BluetoothUtil.isBlueToothPrinter) {
                SunmiPrintHelper.getInstance().setAlign(alignMode);
                SunmiPrintHelper.getInstance().printText(textToPrint, fontSize, isBold, false);
                if (isNeedLF)
                    SunmiPrintHelper.getInstance().printLineWrap();
            //}else{
//                Toast toast = Toast.makeText(mContext, "unable to print", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
                return;
           // }

        }
        catch (Exception ex){

        }
    }
}
