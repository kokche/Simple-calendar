package com.example.calendarfroyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.text.DateFormat.getDateInstance;

public class MainActivity extends AppCompatActivity {

    private  TextView valuesYellow,valuesGreen,valuesRed;
    private String mDate,dateMap;
    private MaterialCalendarView calendarView;
    private FloatingActionButton fab;
    private SharedPreferences mySharedPreferences;
    private String currentColor;
    Calendar myCalendar;
    Set<String> set,myYellowDate,myRedDate,myGreenDate;
    int max_date;


    private OnDateSelectedListener onDateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            mDate = date.getYear()+"/"+date.getMonth()+"/"+date.getDay();
            currentColor = mySharedPreferences.getString(mDate, color.BlUE.getCode());
            //calendarView.setSelectionColor(Color.parseColor(currentColor));
        }
    };
    private View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Set<String> YellowDate = new HashSet<>();
            Set<String> redDate = new HashSet<>();
            Set<String> greenDate = new HashSet<>();

             currentColor = mySharedPreferences.getString(mDate, color.BlUE.getCode());

            int currentRedValues = 0;
            int currentYellowValues = 0;
            int currentGreenValues = 0;


            if (currentColor.equals(color.RED.getCode())) {
                currentColor = color.YELLOW.getCode();
                Toast.makeText(MainActivity.this, "I think you can do better", Toast.LENGTH_SHORT).show();
            } else if (currentColor.equals(color.YELLOW.getCode())) {
                currentColor = color.GREEN.getCode();
                Toast.makeText(MainActivity.this, "Very GOOD!!!\nStay cool!)", Toast.LENGTH_SHORT).show();
            } else {
                currentColor = color.RED.getCode();
                Toast.makeText(MainActivity.this, "It was a bad day(((", Toast.LENGTH_SHORT).show();
            }
            mySharedPreferences.edit().putString(mDate, currentColor).apply();
            set = mySharedPreferences.getAll().keySet();

            for (int i = 0; i< max_date;++i){
                dateMap = calendarView.getCurrentDate().getYear()+"/"+ calendarView.getCurrentDate().getMonth()+"/";
                currentColor = mySharedPreferences.getString(dateMap + i, color.BlUE.getCode());
                if (currentColor.equals(color.RED.getCode())) {
                    currentRedValues += 1;
                    redDate.add(dateMap+i);
                } else if (currentColor.equals(color.YELLOW.getCode())) {
                    currentYellowValues += 1;
                    YellowDate.add(dateMap+i);
                } else if (currentColor.equals(color.GREEN.getCode())) {
                    currentGreenValues += 1;
                    greenDate.add(dateMap+i);
                }
            }

            myYellowDate = YellowDate;
            myRedDate = redDate;
            myGreenDate = greenDate;
            AddDecorators(myYellowDate,myRedDate,myGreenDate);

            valuesGreen.setText(String.valueOf(currentGreenValues));
            valuesRed.setText(String.valueOf(currentRedValues));
            valuesYellow.setText(String.valueOf(currentYellowValues));

        }
    };

    private void AddDecorators(Set<String> myYellowDate,Set<String> myRedDate,Set<String> myGreenDate)
    {

        calendarView.addDecorators(new EventDecorator(myYellowDate,this,color.YELLOW.getCode()));
        calendarView.addDecorators(new EventDecorator(myRedDate,this,color.RED.getCode()));
        calendarView.addDecorators(new EventDecorator(myGreenDate,this,color.GREEN.getCode()));

    }
    enum color
    {
        RED("#BB2022",0), YELLOW("#FFF000",1), GREEN("#00FF30",2),BlUE("#0000FF",3);
        private String code;
        private int number;
        color(String code, int number){
            this.code = code;
            this.number = number;
        }
        public String getCode(){ return code;}
        public int getNumber(){return  number;}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         myCalendar = (Calendar) Calendar.getInstance().clone();


        fab = findViewById(R.id.floatingActionButton);
        calendarView = findViewById(R.id.calendarView1);
        valuesGreen = findViewById(R.id.values_green);
        valuesRed = findViewById(R.id.values_red);
        valuesYellow = findViewById(R.id.values_yellow);
        mySharedPreferences = getSharedPreferences(Tools.COLOR_ON_DATE, Context.MODE_PRIVATE);

        myCalendar.set(calendarView.getCurrentDate().getYear(), calendarView.getCurrentDate().getMonth()+1, 1);
        max_date = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
       // mySharedPreferences.edit().clear().apply();
        valuesGreen.setText(mySharedPreferences.getString("valuesGreen","0"));
        valuesRed.setText(mySharedPreferences.getString("valuesRed","0"));
        valuesYellow.setText(mySharedPreferences.getString("valuesYellow","0"));


        UpdateDecorators();

        mDate = calendarView.getCurrentDate().getYear() + "/"+calendarView.getCurrentDate().getMonth() + "/" +calendarView.getCurrentDate().getDay();

        this.setTitle(Calendar.getInstance().getTime().toString().split(" ")[1]);
        fab.setOnClickListener(fabOnClickListener);
        initializeCalendar();

    }

    private void UpdateDecorators() {

        Set<String> YellowDate = new HashSet<>();
        Set<String> redDate = new HashSet<>();
        Set<String> greenDate = new HashSet<>();

        int currentRedValues = 0;
        int currentYellowValues = 0;
        int currentGreenValues = 0;
        for (int i = 0; i< max_date;++i){
            dateMap = calendarView.getCurrentDate().getYear()+"/"+ calendarView.getCurrentDate().getMonth()+"/";
            currentColor = mySharedPreferences.getString(dateMap + i, color.BlUE.getCode());
            if (currentColor.equals(color.RED.getCode())) {
                currentRedValues += 1;
                redDate.add(dateMap+i);
            } else if (currentColor.equals(color.YELLOW.getCode())) {
                currentYellowValues += 1;
                YellowDate.add(dateMap+i);
            } else if (currentColor.equals(color.GREEN.getCode())) {
                currentGreenValues += 1;
                greenDate.add(dateMap+i);
            }
        }

        myYellowDate = YellowDate;
        myRedDate = redDate;
        myGreenDate = greenDate;
        AddDecorators(myYellowDate,myRedDate,myGreenDate);

        valuesGreen.setText(String.valueOf(currentGreenValues));
        valuesRed.setText(String.valueOf(currentRedValues));
        valuesYellow.setText(String.valueOf(currentYellowValues));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.remove_decoder) {
            mySharedPreferences.edit().remove(mDate).apply();
            calendarView.removeDecorators();
            UpdateDecorators();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeCalendar() {


        calendarView.setOnDateChangedListener(onDateSelectedListener);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                myCalendar.set(date.getYear(), date.getMonth(), 1);
                max_date = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;

                int currentRedValues = 0;
                int currentYellowValues = 0;
                int currentGreenValues = 0;

                Set<String> YellowDate = new HashSet<>();
                Set<String> redDate = new HashSet<>();
                Set<String> greenDate = new HashSet<>();

                for (int i = 0; i< max_date;++i){
                    dateMap = calendarView.getCurrentDate().getYear()+"/"+ calendarView.getCurrentDate().getMonth()+"/";
                    currentColor = mySharedPreferences.getString(dateMap + i, color.BlUE.getCode());
                    if (currentColor.equals(color.RED.getCode())) {
                        currentRedValues += 1;
                        redDate.add(dateMap+i);
                    } else if (currentColor.equals(color.YELLOW.getCode())) {
                        currentYellowValues += 1;
                        YellowDate.add(dateMap+i);
                    } else if (currentColor.equals(color.GREEN.getCode())) {
                        currentGreenValues += 1;
                        greenDate.add(dateMap+i);
                    }
                }

                myYellowDate = YellowDate;
                myRedDate = redDate;
                myGreenDate = greenDate;
                AddDecorators(myYellowDate,myRedDate,myGreenDate);

                valuesGreen.setText(String.valueOf(currentGreenValues));
                valuesRed.setText(String.valueOf(currentRedValues));
                valuesYellow.setText(String.valueOf(currentYellowValues));
            }
        });
        //calendarView.addDecorators(new EventDecorator(mySharedPreferences,this.getApplicationContext()));

    }

}
