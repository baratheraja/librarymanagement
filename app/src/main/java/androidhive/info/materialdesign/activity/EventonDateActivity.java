package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.EventAdapter;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Event;

public class EventonDateActivity extends AppCompatActivity {

    DbOperation dbOperation = new DbOperation();
    List<Event> eventslist = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventon_date);
        Intent intent = getIntent();
        Date datetodisplay = (Date) intent.getExtras().get("date");
        String date2 = datetodisplay.getDay()+"-"+datetodisplay.getMonth()+"-"+datetodisplay.getYear();
        dbOperation.getevents(this);
        List<Event> events = DbOperation.events;
        listView = (ListView) findViewById(R.id.listing);
        String [] date;
        Calendar cal = Calendar.getInstance();
        for (Event event: events) {
            date=event.getDate().split("-");
            cal.set(Integer.parseInt(date[2]),(-1+Integer.parseInt(date[1])),Integer.parseInt(date[0]));
            Date temp = cal.getTime();
            if(datetodisplay.getDay() == temp.getDay() && datetodisplay.getMonth() == temp.getMonth() && datetodisplay.getYear() == temp.getYear()) {
                eventslist.add(event);
            }
        }
        listView.setAdapter(new EventAdapter(this, eventslist));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Event  itemValue    = (Event) listView.getItemAtPosition(position);

                // Show Alert
                //  Toast.makeText(view.getContext(),
                //       "Event:  " + itemValue.getTitle()+"\nAbout:  "+itemValue.getAbout()+"\nDate:  "+itemValue.getDate(), Toast.LENGTH_LONG)
                //      .show();
                Intent intent = new Intent(EventonDateActivity.this,EventdetailActivity.class);
                intent.putExtra("EventDetail",itemValue);
                startActivity(intent);

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventon_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
