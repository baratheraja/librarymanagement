package androidhive.info.materialdesign.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Event;


public class EventsFragment extends Fragment implements View.OnClickListener {

    Button cButton;
    Button tButton,dButton;
    static MaterialEditText title,about,venue,date,time;
    Event eventData;
    DbOperation dbOperation;
    private View view;
    static int h,m,yr,mt,dy;
    public EventsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friends, container, false);

        date=(MaterialEditText) view.findViewById(R.id.txtIsbn);
        title = (MaterialEditText) view.findViewById(R.id.txtTitle);
        about = (MaterialEditText) view.findViewById(R.id.txtAbout);
        venue = (MaterialEditText) view.findViewById(R.id.txtVenue);
        time = (MaterialEditText) view.findViewById(R.id.tvtime);

        cButton = (Button) view.findViewById(R.id.btnSubmit);
        cButton.setOnClickListener(this);
       // tButton = (Button) view.findViewById(R.id.button2);
        //dButton = (Button) view.findViewById(R.id.button1);

        //Listener for time
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });


        //listener for date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }

        });
        return view;
    }

    @Override
    public void onClick(View v) {
        title.getText().toString();
      //  eventData = new Event(title.getText().toString(),about.getText().toString(),venue.getText().toString(),
        //        date.getText().toString());
        eventData = new Event(title.getText().toString(),about.getText().toString(),venue.getText().toString(),
                date.getText().toString(),Config.club,time.getText().toString());
                dbOperation = new DbOperation();
        String response = dbOperation.create(eventData);
        if ( response.equals("s")) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),Config.club,Toast.LENGTH_SHORT);
            toast.show();
        }
    }






    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        int hour,minute;

        public TimePickerFragment() {

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            if(hour ==-1 || minute == -1) {
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                hour+=5;
                minute+=30;
                hour+=(minute)/60;
                minute%=60;
            }

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
           h=hourOfDay;
            m=minute;
            time.setText(h+" : "+m);
        }
    }






    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        int year,month,day;
        public DatePickerFragment() {

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            yr=year;
            mt=month+1;
            dy=day;
                date.setText(dy+"-"+mt+"-"+yr);
        }
    }
  }
