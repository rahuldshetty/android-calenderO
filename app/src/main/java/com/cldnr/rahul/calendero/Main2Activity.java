package com.cldnr.rahul.calendero;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Main2Activity extends AppCompatActivity {


    SQLiteDatabase myDatabase;

    Calendar myCalendar = Calendar.getInstance();

    SharedPreferences sharedpreferences;

    Toolbar toolbar;
    EditText title,desc,time,time2;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        title = findViewById(R.id.titletext);
        desc=findViewById(R.id.desctext);
        time=findViewById(R.id.timetext);
        time2=findViewById(R.id.timetext2);

        add=findViewById(R.id.button);


        myDatabase = openOrCreateDatabase("dbstorage",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS programTable(title VARCHAR(15),description VARCHAR(50),time varchar(5),date varchar(10));");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit=title.getText().toString();
                String des=desc.getText().toString();
                String tim=time2.getText().toString();
                String dat=time.getText().toString();

                if(!TextUtils.isEmpty(tit) && !TextUtils.isEmpty(des) && !TextUtils.isEmpty(tim) && !TextUtils.isEmpty(dat) ) {

                    myDatabase.execSQL("INSERT INTO programTable VALUES('"+ tit+"','"+des+"','"+tim+"','"+dat+"');" );

                    Toast.makeText(Main2Activity.this, "Event added...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Main2Activity.this, "Fill the details...", Toast.LENGTH_LONG).show();
                }
                Intent back=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int a, int b) {
                        time2.setText(a + ":" + b);
                    }
                },hour,minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        time.setText(sdf.format(myCalendar.getTime()));
    }


}
