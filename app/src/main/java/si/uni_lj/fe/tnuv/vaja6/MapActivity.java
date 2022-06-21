package si.uni_lj.fe.tnuv.vaja6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MapActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener, OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    private static final int Request_code = 101;

    private GoogleMap mMap;
    ArrayList<LatLng> arrayList = new ArrayList<>();

    LatLng azur = new LatLng(46.045690747117476, 14.473345684657135);
    LatLng spar = new LatLng(46.03958620504355, 14.476320117832323);
    LatLng mc = new LatLng(46.0759634013417, 14.484136854069007);
    LatLng hood = new LatLng(46.0389707888777, 14.47582012708236);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.maps);
        supportMapFragment.getMapAsync(this);

        arrayList.add(azur);
        arrayList.add(spar);
        arrayList.add(mc);
        arrayList.add(hood);


        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lokacije");

        List<RestaurantModel> restaurantModelList = getRestaurantData();

        //url = getResources().getString(R.string.urlNaslov);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < arrayList.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Restavracija"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }

        /*LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).
                                    title("Tukaj!");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(options);*/
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Request_code);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
        }
        String jsonStr = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels = gson.fromJson(jsonStr, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);
        return restList;
    }

    @Override
    public void onItemCLick(RestaurantModel restaurantModel) {
        Intent intent = new Intent(MapActivity.this, RestaurantMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);
    }
}
