package androidhive.info.materialdesign.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.EventAdapter;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Book;


public class HomeFragment extends Fragment {

    ListView listView;
    DbOperation dbOperation = new DbOperation();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final FloatingActionButton actionA = (FloatingActionButton) rootView.findViewById(R.id.action_a);
      //  actionA.setImageDrawable(getResources().getDrawable(R.drawable.fb_icon));
        // Inflate the layout for this fragment

        listView = (ListView) rootView.findViewById(R.id.list);

        dbOperation.getBooks(getActivity(),false);

        List<Book> eventslist = DbOperation.books;
        listView.setAdapter(new EventAdapter(getActivity(),eventslist));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Book itemValue    = (Book) listView.getItemAtPosition(position);

                // Show Alert
              //  Toast.makeText(view.getContext(),
                //       "Book:  " + itemValue.getTitle()+"\nAbout:  "+itemValue.getAbout()+"\nDate:  "+itemValue.getDate(), Toast.LENGTH_LONG)
                  //      .show();
                Intent intent = new Intent(getActivity(),BookdetailActivity.class);
                intent.putExtra("EventDetail",itemValue);
                startActivity(intent);

            }

        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
