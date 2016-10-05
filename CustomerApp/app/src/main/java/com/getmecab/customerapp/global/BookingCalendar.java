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
                    String selectedDate = selectedDay + "," + date + "-" + selectedMonth + "-" + year;
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
                selectedDay = "Sun";
                break;
            case 2 :
                selectedDay = "Mon";
                break;
            case 3 :
                selectedDay = "Tue";
                break;
            case 4 :
                selectedDay = "Wed";
                break;
            case 5 :
                selectedDay = "Thu";
                break;
            case 6 :
                selectedDay = "Fri";
                break;
            case 7 :
                selectedDay = "Sat";
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
                selectedMonth = "Jan";
                break;
            case 1 :
                selectedMonth = "Feb";
                break;
            case 2 :
                selectedMonth = "Mar";
                break;
            case 3 :
                selectedMonth = "Apr";
                break;
            case 4 :
                selectedMonth = "May";
                break;
            case 5 :
                selectedMonth = "Jun";
                break;
            case 6 :
                selectedMonth = "Jul";
                break;
            case 7 :
                selectedMonth = "Aug";
                break;
            case 8 :
                selectedMonth = "Sep";
                break;
            case 9 :
                selectedMonth = "Oct";
                break;
            case 10 :
                selectedMonth = "Nov";
                break;
            case 11 :
                selectedMonth = "Dec";
                break;
            default:
                selectedMonth = "";
                break;
        }
        return selectedMonth;
    }
}
