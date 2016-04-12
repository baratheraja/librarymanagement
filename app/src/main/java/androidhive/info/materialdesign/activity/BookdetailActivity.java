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
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Book;

/**
 * Created by baratheraja on 3/9/15.
 */
public class BookdetailActivity extends AppCompatActivity {
    TextView bookname, title, author, about, available;
    static boolean wait = true;
    Button button;
   // static  int year, month, day,hour =0, minute=0,second =0;
    static Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("EventDetail");
        bookname = (TextView) findViewById(R.id.bookname);
        title = (TextView) findViewById(R.id.book_title);
        author = (TextView) findViewById(R.id.book_author);
        about = (TextView) findViewById(R.id.book_about);
        available = (TextView) findViewById(R.id.book_available);
        final SessionManager sessionManager = new SessionManager(this);
        if(book.getTitle()!=null)
            title.setText(book.getTitle());
        author.setText(book.getAbout());
        if(book.getStock()!=null && book.getBlocked()!=null && book.getGiven()!=null) {
            Integer av;
            av = Integer.parseInt(book.getStock()) - Integer.parseInt(book.getGiven()) -
                    Integer.parseInt(book.getBlocked());
            available.setText(av.toString());
        }

        about.setText(book.getAbout());

        button = (Button)findViewById(R.id.block);
        //String[] datelist= book.getDate().split("-");
       // day = Integer.parseInt(datelist[0]);
       // month = Integer.parseInt(datelist[1]);
       // year = Integer.parseInt(datelist[2]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          DialogFragment newFragment = new TimePickerFragment();
        //        newFragment.show(getSupportFragmentManager(), "timePicker");
                DbOperation db = new DbOperation();
                db.blockBook(BookdetailActivity.this,book.getId(),sessionManager.getUserDetails().get(
                        SessionManager.KEY_ID
                ));

            }

        });

    }

    @Override
    public void onStop(){
        super.onStop();
    }
/*
    private static void startAlarm(Context context) {
        Calendar calendar =  Calendar.getInstance();
        calendar.set(year, month-1, day, hour, minute, second);
        long when = calendar.getTimeInMillis();
        AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(context,"Setting reminder for "+day+"-"+month+"-"+year+" "+hour+":"+minute+":"+second,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAbout());
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
            // Use the current available as the default values for the picker
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
    */
}
