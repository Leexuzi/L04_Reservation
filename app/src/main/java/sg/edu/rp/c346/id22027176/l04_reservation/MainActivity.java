package sg.edu.rp.c346.id22027176.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button btnBook;
    Button btnReset;
    TextView name;
    TextView number;
    TextView size;
    TextView allInfo;
    CheckBox smoke;
    DatePicker dp;
    TimePicker tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBook = findViewById(R.id.btnBook);
        btnReset = findViewById(R.id.btnReset);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        size = findViewById(R.id.size);
        allInfo = findViewById(R.id.allInfo);
        smoke = findViewById(R.id.smoke);
        dp = findViewById(R.id.slctDate);
        tp = findViewById(R.id.slctTime);
        tp.setIs24HourView(true);

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) { //worked first try. using the picker feels like the Infinity devil's got a hold of me
                if(tp.getHour()<8){
                    tp.setHour(8);
                }
                else if(tp.getHour()>=21){
                    tp.setHour(20);
                    tp.setMinute(59);
                }
            }
        });
        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() { //had to make a new project cause this was only for API26 and above
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                int toDay = c.get(Calendar.DAY_OF_MONTH);
                int thisMonth = c.get(Calendar.MONTH);
                int thisYear = c.get(Calendar.YEAR);
                if(dp.getYear()<thisYear||dp.getMonth()<thisMonth||dp.getDayOfMonth()<toDay){
                    dp.updateDate(thisYear, thisMonth, toDay);
                }
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to find out if there are empty fields, and if so, what they are
                String[] field = {""};
                String[] empty = {"T"};
                if(name.getText().length()==0){
                    field[0] = "Name";
                }
                else if(number.getText().length()==0){
                    field[0] = "Mobile Number";
                }
                else if(size.getText().length()==0) {
                    field[0] = "Group Size";
                }
                else{
                    empty[0] = "F";
                }
                //if no empty fields
                final String[] dateTime = {""}; //i dont understand, but ill just use this anyway
                final String[] date = {""};
                final String[] time = {""};
                if(empty[0].equals("F")) {
                    date[0] = dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear();
                    time[0] = tp.getHour() + " : " + tp.getMinute();
                    dateTime[0] = "Reserved " + date[0] + ", " + time[0];

                    Context context = getApplicationContext();
                    Toast.makeText(context, dateTime[0], Toast.LENGTH_SHORT).show();
                    String[] smoking = {""};
                    if (smoke.isChecked()) {
                        smoking[0] = "Sitting in smoking area";
                    } else {
                        smoking[0] = "Sitting in non-smoking area";
                    }
                    String phName = name.getText().toString();
                    String phNumber = number.getText().toString();
                    String phSize = size.getText().toString();
                    String info = "Name: " + phName + "\nMobile: " + phNumber + "\nGroup size: " + phSize + "\n" + smoking[0] + "\nReservation on: " + date[0] + "\nAt: " + time[0];
                    allInfo.setText(info);
                }
                else {
                    Context con = getApplicationContext();
                    Toast.makeText(con, field[0] + " field not filled in", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp.updateDate(2023, 6, 1);
                tp.setHour(19);
                tp.setMinute(30);
                name.setText("");
                number.setText("");
                size.setText("");
                smoke.setChecked(false);
            }
        });
    }
}