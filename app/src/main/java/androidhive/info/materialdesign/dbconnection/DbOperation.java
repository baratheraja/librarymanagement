package androidhive.info.materialdesign.dbconnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import androidhive.info.materialdesign.model.Book;

/**
 * Created by baratheraja on 22/7/15.
 */
public class DbOperation {
    private ProgressDialog pdia;
    private Context context;
    ObjectMapper mapper = new ObjectMapper();
    public static List<Book> books = new ArrayList<>();
    private void seteventhelper(String response){
        try {
            books = mapper.readValue(response, new TypeReference<List<Book>>() { });
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
            if(result.equals("success")) Toast.makeText(context,"Successfully Blocked",Toast.LENGTH_LONG).show();
            else if(result.equals("blocked")) Toast.makeText(context,"Already Blocked",Toast.LENGTH_LONG).show();
//            else Toast.makeText(context,"Error in Blocking",Toast.LENGTH_LONG).show();
        }
    }

    private class GetBook extends AsyncTask<String, Void, String> {
        List<NameValuePair> nameValuePairs;
        private GetBook() {
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
            //Log.d("getting books",result+"XYZ");
        }
    }


    public String create(Book book) {
        //create post params
        List<NameValuePair> nameValuePairs =new ArrayList<>(4);
        nameValuePairs.add(new BasicNameValuePair("title", book.getTitle()));
        nameValuePairs.add(new BasicNameValuePair("about", book.getAbout()));
        nameValuePairs.add(new BasicNameValuePair("author", book.getAuthor()));
        nameValuePairs.add(new BasicNameValuePair("stock", book.getStock()));
        nameValuePairs.add(new BasicNameValuePair("given", book.getGiven()));

        //sending post request
        try {
           PostEvent createEvent = new PostEvent(nameValuePairs);
            //createEvent.execute(new String[] {"http://192.168.43.126/inceg/create.php"});
            createEvent.execute(new String[]{"http://192.168.43.126/inceg/create.php"});
            return "s";
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
            return "f";
        }
    }

    public void getBooks(Context context,Boolean isAdmin) {

        this.context = context;
        GetBook getBook = new GetBook();
        try {
           // String resp= getBook.execute(new String[] {"http://192.168.43.126/inceg/show.php"}).get();
            String resp;
            if(isAdmin) {
                resp = getBook.execute(new String[]{"http://192.168.43.126/inceg/showall.php"}).get();
            }else{
                resp= getBook.execute(new String[] {"http://192.168.43.126/inceg/show.php"}).get();
            }
            seteventhelper(resp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public boolean blockBook(Context context,String id,String userid) {
        this.context = context;d2 c
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id",id));
        nameValuePairs.add(new BasicNameValuePair("userid",userid));
        try {
            PostEvent createEvent = new PostEvent(nameValuePairs);
            //createEvent.execute(new String[] {"http://192.168.43.126/inceg/delete.php"});
            createEvent.execute(new String[] {"http://192.168.43.126/inceg/block.php"});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean deleteBooks(List<String> ids) {
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        for(String id: ids) {
            nameValuePairs.add(new BasicNameValuePair("id[]",id));
        }
        try {
            PostEvent createEvent = new PostEvent(nameValuePairs);
            //createEvent.execute(new String[] {"http://192.168.43.126/inceg/delete.php"});
            createEvent.execute(new String[] {"http://192.168.43.126/inceg/delete.php"});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
