package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
    private DatePickerDialog datePickerDialog;
    private int hour, minute;

    private EditText eventNameET;
    private Button dateButton, timeButton;

    private LocalDate date;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        date = LocalDate.now();
        time = LocalTime.now();
        hour = time.getHour();
        minute = time.getMinute();

        dateButton.setText("Date: " + formatDate(date));
        timeButton.setText("Time: " + formatTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        dateButton = findViewById(R.id.eventDateButton);

        timeButton = findViewById(R.id.eventTimeButton);
    }

    public void openDatePicker(View view)
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                dateButton.setText("Date: "+formatDate(year, month, day));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    public void openTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText("Time: "+formatTime(hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    private String formatDate(int year, int month, int day) {
        switch (month){
            case 1: return String.valueOf(day)+" JAN "+String.valueOf(year);
            case 2: return String.valueOf(day)+" FEB "+String.valueOf(year);
            case 3: return String.valueOf(day)+" MAR "+String.valueOf(year);
            case 4: return String.valueOf(day)+" APR "+String.valueOf(year);
            case 5: return String.valueOf(day)+" MAY "+String.valueOf(year);
            case 6: return String.valueOf(day)+" JUN "+String.valueOf(year);
            case 7: return String.valueOf(day)+" JUL "+String.valueOf(year);
            case 8: return String.valueOf(day)+" AUG "+String.valueOf(year);
            case 9: return String.valueOf(day)+" SEP "+String.valueOf(year);
            case 10: return String.valueOf(day)+" OCT "+String.valueOf(year);
            case 11: return String.valueOf(day)+" NOV "+String.valueOf(year);
            case 12: return String.valueOf(day)+" DEC "+String.valueOf(year);
            default: return String.valueOf(day)+" Error "+String.valueOf(year);
        }
    }

    private String formatTime(LocalTime time) {
        return String.format(Locale.getDefault(), "%02d:%02d",time.getHour(), time.getMinute());
    }

    private String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d",hour, minute);
    }
}