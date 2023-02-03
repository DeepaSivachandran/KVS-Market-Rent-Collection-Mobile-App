package com.example.collectionreport;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.softland.palmtecandro.palmtecandro;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PalmtecPrinter {

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
