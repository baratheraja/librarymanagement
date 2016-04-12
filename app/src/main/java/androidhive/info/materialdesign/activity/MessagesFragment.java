package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Book;


public class MessagesFragment extends Fragment {
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    DbOperation dbOperation = new DbOperation();
    List<Date> dates = new ArrayList<>();
    CaldroidFragment caldroidFragment;
    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_messages, container, false);

        dbOperation.getBooks(getActivity(),false);

        List<Book> eventslist = DbOperation.books;

        String [] date;
        Calendar cal = Calendar.getInstance();
       /* for(Book book : eventslist) {
            date= book.getDate().split("-");
            cal.set(Integer.parseInt(date[2]),(-1+Integer.parseInt(date[1])),Integer.parseInt(date[0]));
            dates.add(cal.getTime());
        }*/

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        for(Date dateinlist: dates ) {
            caldroidFragment.setBackgroundResourceForDate(R.color.green,
                    dateinlist);
        }
        android.support.v4.app.FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                boolean flag=false;
                for(Date dateinlist : dates) {

                    if(  date.getDay() == dateinlist.getDay() && date.getMonth() == dateinlist.getMonth() && date.getYear() == dateinlist.getYear()){
                                flag =true;
                    }
                }
                if(flag)
                {
                    Intent intent = new Intent(getActivity(),EventonDateActivity.class);
                    intent.putExtra("about",date);
                    startActivity(intent);
                }
                else
                Toast.makeText(getActivity(), "No books on " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getActivity(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getActivity(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getActivity(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        caldroidFragment.setCaldroidListener(listener);

        return view;
    }


}
