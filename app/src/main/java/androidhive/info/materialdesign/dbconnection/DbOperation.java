package androidhive.info.materialdesign.dbconnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import androidhive.info.materialdesign.model.Event;

/**
 * Created by baratheraja on 22/7/15.
 */
public class DbOperation {
    private ProgressDialog pdia;
    private Context context;
    ObjectMapper mapper = new ObjectMapper();
    public static List<Event> events = new ArrayList<>();
    private void seteventhelper(String response){
        try {
            events = mapper.readValue(response, new TypeReference<List<Event>>() { });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class PostEvent extends AsyncTask<String, Void, String> {
        List<NameValuePair> nameValuePairs;
        private PostEvent(List<NameValuePair> nameValuePairs) {
            this.nameValuePairs = nameValuePairs;
        }


        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    HttpResponse execute = client.execute(httpPost);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
                Log.d("abc", result + "XYZ");
        }
    }

    private class GetEvent extends AsyncTask<String, Void, String> {
        List<NameValuePair> nameValuePairs;
        private GetEvent() {
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(context);
            pdia.setMessage("Loading...");
            pdia.show();
        }
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            pdia.dismiss();
            //Log.d("getting events",result+"XYZ");
        }
    }


    public String create(Event event) {
        //create post params
        List<NameValuePair> nameValuePairs =new ArrayList<>(4);
        nameValuePairs.add(new BasicNameValuePair("title",event.getTitle()));
        nameValuePairs.add(new BasicNameValuePair("about",event.getAbout()));
        nameValuePairs.add(new BasicNameValuePair("venue",event.getVenue()));
        nameValuePairs.add(new BasicNameValuePair("date",event.getDate()));
        nameValuePairs.add(new BasicNameValuePair("club",event.getClub()));
        nameValuePairs.add(new BasicNameValuePair("time",event.getTime()));

        //sending post request
        try {
           PostEvent createEvent = new PostEvent(nameValuePairs);
            //createEvent.execute(new String[] {"http://10.0.3.2/inceg/create.php"});
            createEvent.execute(new String[] {"http://192.168.43.15/inceg/create.php"});
            return "s";
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
            return "f";
        }
    }

    public void getevents(Context context) {

        this.context = context;
        GetEvent getEvent = new GetEvent();
        try {
           // String resp= getEvent.execute(new String[] {"http://10.0.3.2/inceg/show.php"}).get();
            String resp= getEvent.execute(new String[] {"http://192.168.43.15/inceg/show.php"}).get();

            seteventhelper(resp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteEvents(List<String> ids) {
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        for(String id: ids) {
            nameValuePairs.add(new BasicNameValuePair("id[]",id));
        }
        try {
            PostEvent createEvent = new PostEvent(nameValuePairs);
            //createEvent.execute(new String[] {"http://10.0.3.2/inceg/delete.php"});
            createEvent.execute(new String[] {"http://192.168.43.15/inceg/delete.php"});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
