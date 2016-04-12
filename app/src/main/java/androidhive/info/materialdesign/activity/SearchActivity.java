package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidhive.info.materialdesign.R;

public class SearchActivity extends AppCompatActivity {

    EditText text;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        text= (EditText) findViewById(R.id.editText3);
        b= (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SearchActivity.this, BookActivity.class);
                i.putExtra("key",text.getText() );
               // startActivity(i);
            }
        });
    }
}
