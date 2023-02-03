package com.example.collectionreport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Confirm_pin_activity extends AppCompatActivity {
    Context context;
    private String codeString = "";
    View point_1,point_2,point_3,point_4;
    TextView user_name_tv,tv_1,tv_2,tv_3,tv_4;
    private static final int MAX_LENGHT = 4;
    public  static  String PASSCODE="";
    Button clean,btn_back,btn1,dot_1,dot_2,dot_3,dot_4,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_pin_layout);
         context = this;

        btn0 =(Button) findViewById(R.id.btn0);
        btn1 =(Button) findViewById(R.id.btn1);
        btn2 =(Button) findViewById(R.id.btn2);
        btn3 =(Button) findViewById(R.id.btn3);
        btn4 =(Button) findViewById(R.id.btn4);
        btn5 =(Button) findViewById(R.id.btn5);
        btn6 =(Button) findViewById(R.id.btn6);
        btn7 =(Button) findViewById(R.id.btn7);
        btn8 =(Button) findViewById(R.id.btn8);
        btn9 =(Button) findViewById(R.id.btn9);

        clean = (Button) findViewById(R.id.clean);
        btn_back = (Button) findViewById(R.id.btn_back);


        tv_1 =(TextView) findViewById(R.id.tv_1);
        tv_2 =(TextView) findViewById(R.id.tv_2);
        tv_3 =(TextView) findViewById(R.id.tv_3);
        tv_4 =(TextView) findViewById(R.id.tv_4);

        point_1 =(View) findViewById(R.id.point_1);
        point_2 =(View) findViewById(R.id.point_2);
        point_3 =(View) findViewById(R.id.point_3);
        point_4 =(View) findViewById(R.id.point_4);


        try {
            Intent i = getIntent();
            PASSCODE = i.getStringExtra("PASSCODE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeString.length() > 0) {

                    codeString = removeLastChar(codeString);
                    setDotImagesState();
                }
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeString.length() > 0) {

                    codeString = removeAllChar(codeString);
                    setDotImagesState();
                }
            }
        });


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(0);
                // Toast.makeText(MainActivity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    call();
                    getbtnStringCode(0);
                }
                setDotImagesState();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getbtnStringCode(1);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    call();
                    getbtnStringCode(1);
                }
                setDotImagesState();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(2);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    call();
                    getbtnStringCode(2);
                }
                setDotImagesState();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(3);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(3);
                    call();
                }
                setDotImagesState();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(4);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(4);
                    call();
                }
                setDotImagesState();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(5);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                    call();
                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(5);
                }
                setDotImagesState();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(6);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(6);
                    call();
                }
                setDotImagesState();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(7);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(7);
                    call();
                }
                setDotImagesState();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(8);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(8);
                    call();
                }
                setDotImagesState();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(9);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(9);
                    call();
                }
                setDotImagesState();
            }
        });
    }

    private void setDotImagesState() {
      /*  for (int i = 0; i < codeString.length(); i++) {

            dots.get(i).setBackgroundResource(R.drawable.paswd_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setBackgroundResource(R.drawable.paswd_disable);
            }
        }*/
        if(codeString.length() == 0){
            //  tv_1.setText("");
            //  tv_2.setText("");
            //  tv_3.setText("");
            //  tv_4.setText("");
            point_1.setVisibility(View.GONE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 1){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            //  tv_2.setText("");
            //  tv_3.setText("");
            //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 2){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            //  tv_2.setText(String.valueOf(codeString.charAt(1)));
            // tv_3.setText("");
            // tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 3){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            // tv_2.setText(String.valueOf(codeString.charAt(1)));
            // tv_3.setText(String.valueOf(codeString.charAt(2)));
            //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 4){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            // tv_2.setText(String.valueOf(codeString.charAt(1)));
            //  tv_3.setText(String.valueOf(codeString.charAt(2)));
            //  tv_4.setText(String.valueOf(codeString.charAt(3)));
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.VISIBLE);
        }
    }

    private String removeAllChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-codeString.length() );
    }
    //Remove only last character
    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1 );
    }


    private void getbtnStringCode(int val) {
        switch (val) {
            case 0:
                codeString += "0";
                break;
            case 1:
                codeString += "1";
                break;
            case 2:
                codeString += "2";
                break;
            case 3:
                codeString += "3";
                break;
            case 4:
                codeString += "4";
                break;
            case 5:
                codeString += "5";
                break;
            case 6:
                codeString += "6";
                break;
            case 7:
                codeString += "7";
                break;
            case 8:
                codeString += "8";
                break;
            case 9:
                codeString += "9";
                break;
            default:
                break;
        }


    }

    private void call() {
        //tv_1.setText("");
        // tv_2.setText("");
        // tv_3.setText("");
        //  tv_4.setText("");

        point_1.setVisibility(View.GONE);
        point_2.setVisibility(View.GONE);
        point_3.setVisibility(View.GONE);
        point_4.setVisibility(View.GONE);
        if(codeString != null) {
            // Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
        }
    }
    public class AsyncLogin extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,jsonObject_token;
        int flag;

        @Override
        protected Boolean doInBackground(String... params) {
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(codeString.equals(PASSCODE)) {
                Toast.makeText(Confirm_pin_activity.this,"Verified" , Toast.LENGTH_SHORT).show();
                   Confirm_pin_activity.this.finish();
            }

        }


    }
}
