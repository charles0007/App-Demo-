package pdp.hycode.pdp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * Created by HyCode on 12/28/2017.
 */

public class SetAllData extends AppCompatActivity  {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();

    ProgressDialog pd;
    ListView list;
    JSONObject jsonObject;
    ArrayList<HashMap<String, String>> dataList, dataGroupList;

    DatabaseReference nRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbpdpchat = nRootRef.child("pdpapp");
    DatabaseReference nUsers = dbpdpchat.child("users");
    DatabaseReference nChat = dbpdpchat.child("user-chat");
    DatabaseReference ngroupChat = dbpdpchat.child("group-chat");
    SessionManagement sessionManagement;
    String address, city, state, knownName, country, postalCode;
    Geocoder geocoder;
    List<Address> addresses;
    Location locLastKnown;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    //    boolean isNetworkEnabled;
    String towers;
    boolean loadPage1 = false, loadPage2 = false, loadPage3 = false;
    String userLatitude, userLongitude;
    Firebase reference1;
    String mainkey;
    DatabaseReference checkUser;
    DatabaseReference dbLocName;
    DatabaseReference dbGpsLat;
    DatabaseReference dbGpsLog;
    DatabaseReference dbAddress;
    DatabaseReference dbCity;
    DatabaseReference dbState;
    DatabaseReference dbCountry;
    DatabaseReference dbPostal;
    DatabaseReference dbStatus;
    DatabaseReference dbSex;
    DatabaseReference dbAds;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_data);

        sessionManagement = new SessionManagement(this);

        userLatitude = sessionManagement.get("GpsLat");
        userLongitude = sessionManagement.get("GpsLog");
        mainkey = sessionManagement.getUserDetails().get("User");
        checkUser = nUsers.child(mainkey);
        dbLocName = checkUser.child("LocName");
        dbGpsLat = checkUser.child("GpsLat");
        dbGpsLog = checkUser.child("GpsLog");
        dbAddress = checkUser.child("Address");
        dbCity = checkUser.child("City");
        dbState = checkUser.child("State");
        dbCountry = checkUser.child("Country");
        dbPostal = checkUser.child("Postal");
        dbStatus = checkUser.child("Status");
        dbSex = checkUser.child("Sex");
        dbAds = checkUser.child("Ads");
        Firebase.setAndroidContext(this);


        try {
            dbpdpchat.child("ServerKey").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    sessionManagement.set("ServerKey", dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    sessionManagement.set("ServerKey", "");
                }
            });
        } catch (Exception ex) {
            sessionManagement.set("ServerKey", "");
        }


//        locationManager.requestLocationUpdates(towers, 1, 1, this);
        reference1 = new Firebase("https://pdpapp.firebaseio.com/pdpapp/users/" + sessionManagement.getUserDetails().get("User"));

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//            Toast.makeText(SetAllData.this,postSnapshot.getKey()+" : "+postSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                    if (postSnapshot.getKey().equals("Type")) {
                        sessionManagement.set("Type", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Username")) {
                        sessionManagement.set("MyUsername", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Image")) {
                        sessionManagement.set("Image", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Description")) {
                        sessionManagement.set("Description", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Business")) {
                        sessionManagement.set("Description", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("SubStatus")) {
                        sessionManagement.set("SubStatus", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("SubDate")) {
                        sessionManagement.set("SubDate", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Activation")) {
                        sessionManagement.set("Activation", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("SubDaysLeft")) {
                        sessionManagement.set("SubDaysLeft", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("TodayDate")) {
                        sessionManagement.set("TodayDate", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Sex")) {
                        sessionManagement.set("Sex", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("Ads")) {
                        sessionManagement.set("Ads", postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("City") && postSnapshot.getValue().toString() != "" && !postSnapshot.getValue().toString().isEmpty()) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/City" + postSnapshot.getValue().toString());
                    } else if (postSnapshot.getKey().equals("State") && postSnapshot.getValue().toString() != "" && !postSnapshot.getValue().toString().isEmpty()) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/State" + postSnapshot.getValue().toString());
                    }
                }

//                if (sessionManagement.get("GpsLat") != null && !sessionManagement.get("GpsLat").isEmpty() &&
//                        sessionManagement.get("City") != null && !sessionManagement.get("City").isEmpty() &&
//                        sessionManagement.get("State") != null && !sessionManagement.get("State").isEmpty() &&
                if (sessionManagement.get("Type") != null && !sessionManagement.get("Type").isEmpty() &&
                        sessionManagement.get("MyUsername") != null && !sessionManagement.get("MyUsername").isEmpty()) {

                    finish();
                    loadPage1 = true;
                    // if (!loadPage2 && !loadPage3) {
                    startActivity(new Intent(SetAllData.this, MainActivity.class));
                    //}
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
//
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        locLastKnown = locationManager.getLastKnownLocation(towers);


        sessionManagement.set("Artisan", "Local");
        sessionManagement.set("ArtisanPage", "Local");

        pd = new ProgressDialog(this);
        pd.setMessage("Gathering data...");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);

    }
    @Override
    public void onResume() {
        super.onResume();

        String mainkey = sessionManagement.getUserDetails().get("User");
        DatabaseReference checkUser = nUsers.child(mainkey);
        DatabaseReference dbStatus = checkUser.child("Status");
//
//
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }





}
