package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.EventAdapter;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Book;

public class AdminActivity extends AppCompatActivity {
    SessionManager session;
    ListView listView;
    DbOperation dbOperation = new DbOperation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        session = new SessionManager(this);
        if(!session.isLoggedIn()){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else if(!session.isAdmin()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        listView = (ListView) findViewById(R.id.listadmin);

        dbOperation.getBooks(this,true);

        List<Book> eventslist = DbOperation.books;
        listView.setAdapter(new EventAdapter(this, eventslist));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Book itemValue = (Book) listView.getItemAtPosition(position);
                Intent intent = new Intent(AdminActivity.this, BookdetailActivity.class);
                intent.putExtra("EventDetail", itemValue);
                startActivity(intent);

            }

        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout_admin) {
            session= new SessionManager(this);
            session.logoutUser();
            return true;
        }

        if(id == R.id.action_create) {
            Intent intent = new Intent(this,BookActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_delete){
            int cntChoice =  listView.getCount();
            List<String> ids = new ArrayList<>();
            Book book;
            for(int i = 0; i < cntChoice; i++) {
                book = DbOperation.books.get(i);
                if(book.isChecked()) {
                    ids.add(book.getId());
                }
            }
            dbOperation.deleteBooks(ids);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}