package androidhive.info.materialdesign.activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.model.Event;

/**
 * Created by baratheraja on 3/9/15.
 */
public class EventdetailActivity extends AppCompatActivity {
    TextView eventname,club,detail,date,time,venue;
    static boolean wait = true;
    Button button;
    static  int year, month, day,hour =0, minute=0,second =0;
    static Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("EventDetail");
        eventname = (TextView) findViewById(R.id.eventname);
        club = (TextView) findViewById(R.id.club);
        detail = (TextView) findViewById(R.id.details);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time1);
        venue = (TextView) findViewById(R.id.place);
        eventname.setText(event.getTitle());
        if(event.getClub()!=null)
            club.setText(event.getClub());
        detail.setText(event.getAbout());
        if(event.getTime()!=null)
            time.setText(event.getTime());
        date.setText(event.getDate());
        venue.setText(event.getVenue());
        button = (Button)findViewById(R.id.reminder);
        String[] datelist= event.getDate().split("-");
        day = Integer.parseInt(datelist[0]);
        month = Integer.parseInt(datelist[1]);
        year = Integer.parseInt(datelist[2]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }

        });

    }

    @Override
    public void onStop(){
        super.onStop();
    }

    private static void startAlarm(Context context) {
        Calendar calendar =  Calendar.getInstance();
        calendar.set(year, month-1, day, hour, minute, second);
        long when = calendar.getTimeInMillis();
        AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(context,"Setting reminder for "+day+"-"+month+"-"+year+" "+hour+":"+minute+":"+second,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("title",event.getTitle());
        intent.putExtra("detail",event.getAbout());
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, intent, 0);
        // I choose 3s after the launch of my application
        alarms.set(AlarmManager.RTC_WAKEUP, when, operation);
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        int hr,min;

        public TimePickerFragment() {
            hr=hour;
            min=minute;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hr, min,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minut) {
            hour=hourOfDay;
            minute=minut;
            wait = false;
            startAlarm(getActivity());
        }
    }
}
