package si.uni_lj.fe.tnuv.vaja6;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import si.uni_lj.fe.tnuv.vaja6.adapters.RestaurantListAdapter;
import si.uni_lj.fe.tnuv.vaja6.model.RestaurantModel;


public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {

    String result = "";

    String url = "https://run.mocky.io/v3/32dd2f9a-c814-4433-b45c-3898bf64225e";
    RecyclerView recyclerView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Seznam Restavracij");

        List<RestaurantModel> restaurantModelList =  getRestaurantData();

        //url = getResources().getString(R.string.urlNaslov);

        initRecycleView(restaurantModelList);

        new jsonTask().execute();

    }

    class jsonTask extends AsyncTask<Void,Void,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL myurl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection)myurl.openConnection();

                InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null){
                    builder.append(line);

                }
                result = builder.toString();

                Log.e("Json", builder.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.e("jsonobject",object.getString("name"));
                    //restaurantName, restaurantAddress replaces textView
                    textView.setText(object.getString("name"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initRecycleView(List<RestaurantModel> restaurantModelList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantModelList, this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


  /*     PrenosPodatkov pp = new PrenosPodatkov(url, this);

        new Thread(){
            @Override
            public void run() {
                String rezultat = pp.prenesiPodatke();
                runOnUiThread(() -> prikaziPodatke(rezultat));
                //prikaziPodatke(rezultat);
            }
        }.start();
*/

    }

 /*   private void prikaziPodatke(String podatki){
        ArrayList<HashMap<String, String>> seznamKontaktov = new ContactsJsonParser().parseToArrayList(podatki);

        SimpleAdapter adapter = new SimpleAdapter(this, seznamKontaktov, R.layout.list_item, new String[]{"name","email","mobile"}, new int[]{R.id.name,R.id.email,R.id.mobile});
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(adapter);
        //Toast.makeText(this, podatki, Toast.LENGTH_LONG).show();
    }
*/
    private List<RestaurantModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while (( n = reader.read(buffer)) != -1){
                writer.write(buffer, 0,n);
            }
        }catch (Exception e){

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels = gson.fromJson(jsonStr, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);

        return restList;
    }

    @Override
    public void onItemCLick(RestaurantModel restaurantModel) {

        Intent intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);


    }
}