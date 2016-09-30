package com.getmecab.customerapp.global;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.getmecab.customerapp.R;

import java.util.Calendar;

public class BookingCalendar extends AppCompatActivity {

    CalendarView calendarView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        if (calendarView != null) {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView calendarView, int year, int month, int date) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, date);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    String selectedDay = dayName(dayOfWeek);
                    String selectedMonth = monthName(month);
                    String selectedDate = selectedDay + " " + selectedMonth + " " + date + " ," + year;
                    Log.d("selectedDate ", selectedDate);
                    Intent intent = new Intent();
                    intent.putExtra("sentData", selectedDate);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    private String dayName(int dayOfWeek) {
        String selectedDay;
        switch (dayOfWeek) {
            case 1 :
                selectedDay = "Sunday";
                break;
            case 2 :
                selectedDay = "Monday";
                break;
            case 3 :
                selectedDay = "Tuesday";
                break;
            case 4 :
                selectedDay = "Wednesday";
                break;
            case 5 :
                selectedDay = "Thursday";
                break;
            case 6 :
                selectedDay = "Friday";
                break;
            case 7 :
                selectedDay = "Saturday";
                break;
            default:
                selectedDay = "";
                break;
        }
        return selectedDay;
    }

    private String monthName(int month) {
        String selectedMonth;
        switch (month) {
            case 0 :
                selectedMonth = "January";
                break;
            case 1 :
                selectedMonth = "February";
                break;
            case 2 :
                selectedMonth = "March";
                break;
            case 3 :
                selectedMonth = "April";
                break;
            case 4 :
                selectedMonth = "May";
                break;
            case 5 :
                selectedMonth = "June";
                break;
            case 6 :
                selectedMonth = "July";
                break;
            case 7 :
                selectedMonth = "August";
                break;
            case 8 :
                selectedMonth = "September";
                break;
            case 9 :
                selectedMonth = "October";
                break;
            case 10 :
                selectedMonth = "November";
                break;
            case 11 :
                selectedMonth = "December";
                break;
            default:
                selectedMonth = "";
                break;
        }
        return selectedMonth;
    }
}
