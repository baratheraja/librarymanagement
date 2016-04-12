package androidhive.info.materialdesign.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.dbconnection.DbOperation;
import androidhive.info.materialdesign.model.Book;

public class BookActivity extends AppCompatActivity implements View.OnClickListener {

    Button cButton;
    Button tButton,dButton;
    static MaterialEditText title,about,author,stock,given;
    Book bookData;
    DbOperation dbOperation;
    static int h,m,yr,mt,dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        stock=(MaterialEditText) findViewById(R.id.txtStock);
        title = (MaterialEditText) findViewById(R.id.txtTitle);
        about = (MaterialEditText) findViewById(R.id.txtAbout);
        author = (MaterialEditText) findViewById(R.id.txtAuthor);
        given = (MaterialEditText) findViewById(R.id.txtGiven);

        cButton = (Button) findViewById(R.id.btnSubmit);
        cButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        title.getText().toString();
        //  bookData = new Book(title.getText().toString(),about.getText().toString(),venue.getText().toString(),
        //        about.getText().toString());

        bookData = new Book(title.getText().toString(),about.getText().toString(),author.getText().toString(),stock.getText().toString(),given.getText().toString());
        dbOperation = new DbOperation();
        String response = dbOperation.create(bookData);
        if ( response.equals("s")) {
            Toast toast = Toast.makeText(this,"Book created",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
