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
import android.location.Address;
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


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    private static final int Request_code = 101;

    private GoogleMap mMap;
    ArrayList<LatLng> arrayList = new ArrayList<>();
    ArrayList<String> arrayListS = new ArrayList<>();

    LatLng azur = new LatLng(46.045690747117476, 14.473345684657135);
    LatLng spar = new LatLng(46.03958620504355, 14.476320117832323);
    LatLng mc = new LatLng(46.0759634013417, 14.484136854069007);
    LatLng hood = new LatLng(46.0389707888777, 14.47582012708236);
    String azurS = new String("Azur");
    String sparS = new String("Spar");
    String mcS = new String("McDonalds Šiška");
    String hoodS = new String("Hood Burger");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        supportMapFragment.getMapAsync(this);

        arrayList.add(azur);
        arrayList.add(spar);
        arrayList.add(mc);
        arrayList.add(hood);
        arrayListS.add(azurS);
        arrayListS.add(sparS);
        arrayListS.add(mcS);
        arrayListS.add(hoodS);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lokacije");

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < arrayList.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Restavracija "+arrayListS.get(i)));

        }


        float zoomLevel = 11.0f; //This goes up to 21
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(azur, zoomLevel));

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
    }
}
