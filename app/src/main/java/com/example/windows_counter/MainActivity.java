package com.example.windows_counter;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText et_result;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_0;
    private Button btn_point;
    private Button btn_add;
    private Button btn_minus;
    private Button btn_multiply;
    private Button btn_divide;
    private Button btn_equal;
    private Button btn_clear;
    private String progress,currentSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_result = findViewById(R.id.et_result);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);
        btn_add = findViewById(R.id.btn_add);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);
        btn_point = findViewById(R.id.btn_point);
        btn_equal = findViewById(R.id.btn_equal);
        btn_clear = findViewById(R.id.btn_clear);

        btn_1.setOnClickListener(new clickNum());
        btn_2.setOnClickListener(new clickNum());
        btn_3.setOnClickListener(new clickNum());
        btn_4.setOnClickListener(new clickNum());
        btn_5.setOnClickListener(new clickNum());
        btn_6.setOnClickListener(new clickNum());
        btn_7.setOnClickListener(new clickNum());
        btn_8.setOnClickListener(new clickNum());
        btn_9.setOnClickListener(new clickNum());
        btn_0.setOnClickListener(new clickNum());
        btn_add.setOnClickListener(new clickOther());
        btn_minus.setOnClickListener(new clickOther());
        btn_multiply.setOnClickListener(new clickOther());
        btn_divide.setOnClickListener(new clickOther());
        btn_point.setOnClickListener(new clickOther());
        btn_equal.setOnClickListener(new clickOther());
        btn_clear.setOnClickListener(new clickOther());
    }


    class clickNum implements View.OnClickListener {
        public void onClick(View v) {
            Button num = (Button) v;
            String clickNum = num.getText().toString();
            progress = et_result.getText().toString();
            if(v == btn_0){
                progress = dealRes(progress,clickNum);
            }else {
                progress = dealRes(progress,clickNum);
            }
            et_result.setText(progress);
        }
    }

    class clickOther implements View.OnClickListener {
        public void onClick(View v) {
            Button num = (Button) v;
            String reg = "^(\\d+)|^(\\d+\\+|-|/|\\*\\d+)|^(\\d+\\.\\d+\\+|-|/|\\*\\d+)";
            String symbol = num.getText().toString();
            progress = et_result.getText().toString();

            if(v == btn_clear){
                progress = "0";
                currentSymbol = "";
            } else if(v == btn_equal) {
                String []arr = progress.split("\\+|-|/|\\*");
                if(progress.split("\\+|-|/|\\*").length != 1) {
                    progress = splitStr(progress,currentSymbol);
                }
            } else if ( v == btn_point) {
                progress = !Pattern.matches(reg,progress)  ? progress : dealRes(progress,symbol);
            } else {
                Boolean a = progress.indexOf('+') != -1 || progress.indexOf('*') != -1 || progress.indexOf('/') != -1;
                Boolean b = progress.indexOf('-') != -1;
                Boolean c = progress.lastIndexOf('-') != 0;
                if (a) {
                    progress = splitStr(progress,currentSymbol);
                    currentSymbol = num.getText().toString();
                    progress = progress + currentSymbol;
                } else if (b && c) {
                    progress = splitStr(progress,currentSymbol);
                    currentSymbol = num.getText().toString();
                    progress = progress + currentSymbol;
                } else {
                    currentSymbol = num.getText().toString();
                    progress = progress + currentSymbol;
                }
            }
            et_result.setText(progress);
        }
    }

    public String splitStr (String str , String calSymbol) {
        String []strArray = new String[20];
        strArray = str.split("\\+|-|/|\\*");
        Double res = 0.0;
        switch (calSymbol) {
            case "+" : {
                if (strArray.length > 2) res = (- Double.parseDouble(strArray[1])) + Double.parseDouble(strArray[2]);
                else res = Double.parseDouble(strArray[0]) + Double.parseDouble(strArray[1]);
                currentSymbol = "";
                break;
            }
            case "-" : {
                if (strArray.length > 2) res = (- Double.parseDouble(strArray[1])) - Double.parseDouble(strArray[2]);
                else  res = str.indexOf('-') == 0 ? Double.parseDouble(str) : Double.parseDouble(strArray[0]) - Double.parseDouble(strArray[1]);
                currentSymbol = "";
                break;
            }
            case "*" : {
                if (strArray.length > 2) res = (- Double.parseDouble(strArray[1])) * Double.parseDouble(strArray[2]);
                else res = Double.parseDouble(strArray[0]) * Double.parseDouble(strArray[1]);
                currentSymbol = "";
                break;
            }
            case "/" : {
                if (strArray.length > 2) res = (- Double.parseDouble(strArray[1])) / Double.parseDouble(strArray[2]);
                else res = Double.parseDouble(strArray[0]) / Double.parseDouble(strArray[1]);
                currentSymbol = "";
                break;
            }
        }
        return String.valueOf(res);
    }

    public String dealRes (String str,String clickSymbol){
        String reValue = "";

        if (str.length() == 0) {
            reValue = clickSymbol;
        } else if(str.length() == 1 && Integer.parseInt(str) == 0 && clickSymbol != btn_point.getText().toString()) {
            reValue = clickSymbol ;
        } else {
            reValue = str + clickSymbol;
        }
        return  reValue;
    }
}
