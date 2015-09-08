package androidhive.info.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.dbconnection.UserLoginDao;


public class LoginActivity extends ActionBarActivity {

    SessionManager session;
    private EditText usernameField,passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        session= new SessionManager(this);
        if(session.isLoggedIn()){
            if(session.isAdmin()){
                Intent intent = new Intent(this,AdminActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
        usernameField = (EditText) findViewById(R.id.editText);
        passwordField = (EditText) findViewById(R.id.editText2);
        loginButton = (Button)  findViewById(R.id.button);
        Toast.makeText(this, "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                new SigninActivity(v.getContext()).execute(username, password);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void handleResponse(UserLoginDao userLoginDao){
        if(userLoginDao.getError()) {
            Toast.makeText(this, userLoginDao.getError_msg(), Toast.LENGTH_LONG).show();
        }
        else {
            session.createLoginSession(userLoginDao.getUid(),
                    userLoginDao.getUser().get("user"), userLoginDao.getUser().get("email"),userLoginDao.getUser().get("admin"));
            if(userLoginDao.getUser().get("admin").equals("1")) {
                Toast.makeText(this, "Admin logged in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,AdminActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, userLoginDao.getUser().get("name") + " logged in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                Config.club = userLoginDao.getUser().get("name");
                startActivity(intent);
            }
        }
    }

    public class SigninActivity  extends AsyncTask<String,Void,String> {
        private Context context;

        //flag 0 means get and 1 means post.(By default it is get.)
        public SigninActivity(Context context) {
            this.context = context;
        }

        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... arg0) {

            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];

                String link="http://192.168.43.15/inceg/login.php";
                //String link="http://stackoverflow.com";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result){
            ObjectMapper mapper = new ObjectMapper();
            try {
                UserLoginDao userLoginDao= mapper.readValue(result,UserLoginDao.class);
                System.out.println(userLoginDao);
                handleResponse(userLoginDao);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
