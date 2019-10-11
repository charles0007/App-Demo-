package pdp.hycode.pdp;

/**
 * Created by HyCode on 12/22/2017.
 */


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Typeface;
        import android.location.Criteria;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.crashlytics.android.Crashlytics;
        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.InterstitialAd;
        import com.google.android.gms.ads.MobileAds;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

        import java.util.Arrays;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;

        import static android.location.LocationManager.NETWORK_PROVIDER;

public class VideosTabfragment extends Fragment {
    ListView usersList;
    double max_distance = 10000;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers, totalUsersGroup;
    ProgressDialog pd;
    //    ListView list;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    //ArrayList<HashMap<String, String>> al;
    static ArrayList<HashMap<String, String>> dataList;
    private static final String TAG = CityTabFragment.class.getSimpleName();

    static String prevMess;
    View rootView;

    DatabaseReference nRootRef;
    DatabaseReference dblobschat;
    DatabaseReference nUsers;
    //    DatabaseReference nChat = dblobschat.child("artisan-chat").child("user-chat");
    DatabaseReference nChat;
//    DatabaseReference ngroupChat = dblobschat.child("artisan-chat");


    SessionManagement sessionManagement;
    String address, city, state, knownName, country, postalCode;
    protected Context context;

    TextView gplast_mess_date, gpnum_users, gplast_mess, gpname, num_users;
    String userLatitude, userLongitude;
    ConstraintLayout groupLayout;
    int count2;
    boolean gpsAlert = false;
    Location locLastKnown;
    protected LocationManager locationManager;
    private AdView adView;
    String towers;
    boolean isNetworkEnabled;
    //    Firebase  oldMess;
    // String grpUsername = "", grpstatus = "", grpmessage = "", grpchatId = "", grpdate = "", grpotherUser = "", grpgpsLat = "", grpgpsLog = "";
    String image = "", TodayDate;
    DatabaseReference lastMess;
    //    ImageView thumb_image;
    int dataNum = 0;
    String appName = "";
    private InterstitialAd mInterstitialAd;
    private static boolean isStop;
    HashMap<String, String> mapList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        sessionManagement = new SessionManagement(getActivity());
        sessionManagement.set("ListPage", "CityTabFragment");
        myOnClickListener = new MyOnClickListener(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_recycler);
        appName = getString(R.string.app_name);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception ex) {
        }

//        nRootRef = FirebaseDatabase.getInstance().getReference();
//        dblobschat = nRootRef.child("lobschat");
//        nUsers = dblobschat.child("users");
//
//        nChat = dblobschat.child("artisan-chat");

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ERASMD.TTF");

//        userLatitude = sessionManagement.get("GpsLat");
//        userLongitude = sessionManagement.get("GpsLog");

        num_users = (TextView) rootView.findViewById(R.id.num_users);
        MobileAds.initialize(getActivity(), "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);
//        try {
//            if (!sessionManagement.get("Ads").equalsIgnoreCase("UnSet")) {
//                AdRequest adRequest = new AdRequest.Builder().build();
//
//                adView.loadAd(adRequest);
//                mInterstitialAd.loadAd(adRequest);
//            } else {
//                adView.setVisibility(View.INVISIBLE);
//            }
//        } catch (Exception ex) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mInterstitialAd.loadAd(adRequest);
//            adView.loadAd(adRequest);
//            adView.setVisibility(View.VISIBLE);
//        }
//
//

        dataList = new ArrayList<HashMap<String, String>>();
//        dataGroupList = new ArrayList<HashMap<String, String>>();
        totalUsers = 0;
        count2 = 0;
//        gpname = (TextView) rootView.findViewById(R.id.gp_name);
//        thumb_image = (ImageView) rootView.findViewById(R.id.list_image);

        registerForContextMenu(recyclerView);


        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
//        pd.show();
        FloatingActionButton fabUser = (FloatingActionButton) rootView.findViewById(R.id.fabUser);


        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManagement.set("Page", "City");
                startActivity(new Intent(getActivity(), UsersList.class)
                        .putExtra("ArtisanPage", "City")
                        .putExtra("Page", "City")
                        .putExtra("Artisan", "City"));
            }
        });

//        firebasenUsers();

//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Load the next interstitial.
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//
//        });

        String  description= "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim..";
        String city = "";
        int image = R.drawable.pdp;
        String token = "";
        String sex = "Male";
        String Email = "";
        String desc = "just a test";
        String type = "";

        mapList = new HashMap<String, String>();
//        userName = "Uche Secondus";

        city = "";
        image = R.drawable.pdp_uche_secondus;
        token = "";
        sex = "Male";
        Email = "";
        desc = "National Chairman";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "0");

        dataList.add(0, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Elder Yemi Akinwonmi";

        city = "";
        image = R.drawable.pdp_yemi_akinwonmi;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Chairman(South)";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "1");

        dataList.add(1, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Gamawa Garba";
        city = "";
        image = R.drawable.pdp_babayo_garba_gamawa;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Chair(North";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "2");

        dataList.add(2, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Ibrahim Tsauri";

        city = "";
        image = R.drawable.pdp_ibrahim_tsauri;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "3");

        dataList.add(3, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Agbo Emmanuel";

        city = "";
        image = R.drawable.pdp_agbo_emmanuel;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "4");

        dataList.add(4, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Kola Ologbodiyon";
        city = "";
        image = R.drawable.pdp_kola_ologbodiyon;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Publicity Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "5");

        dataList.add(5, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Diran Odeyemi";
        city = "";
        image = R.drawable.pdp_diran_odeyemi;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Publicity Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "6");

        dataList.add(6, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Mariya Waziri";
        city = "";
        image = R.drawable.pdp_mariya_waziri;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Women Leader";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "7");

        dataList.add(7, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Umoru Hadizat";
        city = "";
        image = R.drawable.pdp_umoru_hadizat;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Women Leader";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "8");

        dataList.add(8, mapList);

        mapList = new HashMap<String, String>();
//        userName = "Abdullahi Maibasara";
        city = "";
        image = R.drawable.pdp_abdullah_maibasira;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Financial Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "9");

        dataList.add(9, mapList);

        mapList = new HashMap<String, String>();
//        userName = " Irona Gerald";

        city = "";
        image = R.drawable.pdp_irona_gerald;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Financial Secretary";
        type = "";

        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "10");

        dataList.add(10, mapList);
        mapList = new HashMap<String, String>();
//        userName = "Hassan Yakubu";

        city = "";
        image = R.drawable.pdp;
        token = "";
        sex = "Male";
        Email = "";
        desc = "Deputy Organising Mobilization and Secretary";
        type = "";
        mapList.put("Description", description);
        mapList.put("Sex", sex);
        mapList.put("message", desc);
        mapList.put("otherUser", "");
        mapList.put("date", "");
        mapList.put("distKm", city);
        mapList.put("read", "");
        mapList.put("UserToken", token);
        mapList.put("Image", image+"");
        mapList.put("chatId", "11");

        dataList.add(11, mapList);



        if (dataList.size() < 1) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new VideosTabAdapter(getActivity(), dataList);
            recyclerView.setAdapter(adapter);
        }
        return rootView;

    }

    private void firebasenUsers() {


        final String mainUser = sessionManagement.getUserDetails().get("User");
        lastMess = nChat.child(mainUser).child("lastMess");
        try {
            lastMess.child("City").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(final DataSnapshot dataSnapshotParent, String s) {

//                    Map map = dataSnapshot.getValue(Map.class);

                    final String chatIdmessage = dataSnapshotParent.getValue().toString();
                    final String chatId = chatIdmessage.split("::")[0];
                    final String page = chatIdmessage.split("::")[1];

                    if (page.equalsIgnoreCase("City")) {

                        nUsers.child(chatId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, String> mapList = new HashMap<String, String>();
                                String userName = dataSnapshotParent.getKey();

                                String city = "";
                                String image = "";
                                String token = "";
                                String sex = "";
                                String Email = "";
                                String desc = "";
                                String type = "";
                                for (DataSnapshot postSnap : dataSnapshot.getChildren()) {

                                    if (postSnap.getKey().equals("Image")) {
                                        image = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Sex")) {
                                        sex = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Token")) {
                                        token = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Email")) {
                                        Email = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("City")) {
                                        city = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Business")) {
                                        desc = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Username")) {
                                        userName = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Type")) {
                                        type = postSnap.getValue().toString();
                                    }
                                }

                                if (!userName.isEmpty() && page.equalsIgnoreCase("City") && type.equalsIgnoreCase("Artisan")) {

                                    if (city.equalsIgnoreCase(sessionManagement.get("City"))) {


                                        int siz = 0;
                                        int doesntExist = 0;
                                        mapList.put("Username", userName);
                                        mapList.put("Sex", sex);
                                        mapList.put("message", desc);
                                        mapList.put("otherUser", "");
                                        mapList.put("date", "");
                                        mapList.put("distKm", city);
                                        mapList.put("read", "");
                                        mapList.put("UserToken", token);
                                        mapList.put("Image", image);
                                        mapList.put("chatId", chatId);
                                        for (HashMap<String, String> dat : dataList) {

                                            if (dat.containsValue(userName)) {

                                                dataList.set(siz, mapList);

                                            } else {
                                                doesntExist = doesntExist + 1;
                                            }
                                            if (doesntExist == dataList.size()) {
                                                dataList.add(totalUsers, mapList);
                                                totalUsers = totalUsers + 1;
                                            }
                                            siz = siz + 1;
                                        }
                                        if (dataList.size() < 1) {
                                            dataList.add(totalUsers, mapList);
                                            totalUsers = totalUsers + 1;
                                        }


                                    }
                                }
                                if (dataList.size() < 1) {
                                    recyclerView.setVisibility(View.GONE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    adapter = new VideosTabAdapter(getActivity(), dataList);
                                    recyclerView.setAdapter(adapter);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }

                @Override
                public void onChildChanged(final DataSnapshot dataSnapshotParent, String s) {

//                    Map map = dataSnapshot.getValue(Map.class);

                    final String chatIdmessage = dataSnapshotParent.getValue().toString();
                    final String chatId = chatIdmessage.split("::")[0];
                    final String page = chatIdmessage.split("::")[1];

                    if (page.equalsIgnoreCase("City")) {

                        nUsers.child(chatId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, String> mapList = new HashMap<String, String>();
                                String userName = dataSnapshotParent.getKey();

                                String city = "";
                                String image = "";
                                String token = "";
                                String sex = "";
                                String Email = "";
                                String desc = "";
                                String type = "";
                                for (DataSnapshot postSnap : dataSnapshot.getChildren()) {

                                    if (postSnap.getKey().equals("Image")) {
                                        image = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Sex")) {
                                        sex = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Token")) {
                                        token = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Email")) {
                                        Email = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("City")) {
                                        city = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Business")) {
                                        desc = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Username")) {
                                        userName = postSnap.getValue().toString();
                                    }
                                    if (postSnap.getKey().equals("Type")) {
                                        type = postSnap.getValue().toString();
                                    }
                                }

                                if (!userName.isEmpty() && page.equalsIgnoreCase("City") && city != "" && type.equalsIgnoreCase("Artisan")) {

                                    if (city.equalsIgnoreCase(sessionManagement.get("City"))) {

                                        int index = 0;
                                        for (HashMap<String, String> dat : dataList) {

                                            if (dat.containsValue(chatId)) {
                                                mapList.put("Username", userName);
                                                mapList.put("Sex", sex);
                                                mapList.put("message", desc);
                                                mapList.put("otherUser", "");
                                                mapList.put("distKm", city);
                                                mapList.put("date", "");
                                                mapList.put("read", "");
                                                mapList.put("UserToken", token);
                                                mapList.put("Image", image);
                                                mapList.put("chatId", chatId);
                                                mapList.put("Email", Email);
                                                dataList.set(index, mapList);
                                                break;
                                            }
                                            index = index + 1;
                                        }
                                    }
                                }
                                if (dataList.size() < 1) {
                                    recyclerView.setVisibility(View.GONE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    adapter = new VideosTabAdapter(getActivity(), dataList);
                                    recyclerView.setAdapter(adapter);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    HashMap<String, String> mapList = new HashMap<String, String>();
//                    Map map = dataSnapshot.getValue(Map.class);
                    String userName = dataSnapshot.getKey();
                    String chatIdmessage = dataSnapshot.getValue().toString();
                    String page = chatIdmessage.split("::")[1];

                    if (page.equalsIgnoreCase("City")) {

                        int index = 0;
                        for (HashMap<String, String> dat : dataList) {

                            if (dat.containsValue(chatIdmessage.split("::")[0])) {
                                //                            mapNew.put("Username", userName);
                                dataList.remove(index);

                                break;
                            }
                            index = index + 1;
                        }
                    }
                    if (dataList.size() < 1) {
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new VideosTabAdapter(getActivity(), dataList);
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });

        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        if (sessionManagement.get("Type").equalsIgnoreCase("Artisan")) {
//            inflater.inflate(R.menu.artisan_tabfragment_menu, menu);
//        } else {
//            inflater.inflate(R.menu.city_tabfragment_menu, menu);
//        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), Settings.class));
                return true;
            case R.id.my_product:
                startActivity(new Intent(getActivity(), MyProducts.class));
                return true;
            case R.id.action_exit:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Exit " + appName + "?");
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        String mainkey = sessionManagement.getUserDetails().get("User");
//                                        DatabaseReference checkUser = nUsers.child(mainkey);
//                                        DatabaseReference dbStatus = checkUser.child("Status");
//                                        dbStatus.setValue("offline");
                                        System.exit(0);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            case R.id.action_refresh:
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                }
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Methods met = new Methods(getActivity());
                    met.showGPSDisabledAlertToUser();
                } else {
                    pd.setMessage("Refreshing Location...");
                    pd.show();
                    UpdateLocation updateLocation = new UpdateLocation(getActivity(), "City");
                    updateLocation.refresh("City");
                    pd.dismiss();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
//        sessionManagement = new SessionManagement(getActivity());
//        String mainkey = sessionManagement.getUserDetails().get("User");
//        DatabaseReference checkUser = nUsers.child(mainkey);
//        DatabaseReference dbStatus = checkUser.child("Status");
//        dbStatus.setValue("online");

        if (mInterstitialAd.isLoaded() && isStop) {
            mInterstitialAd.show();
        }
        isStop=false;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
    }

    /**
     * MENU
     */


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list) {
            MenuItem mnu1 = menu.add(0, 0, 0, "View Profile");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == 0) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            long selectid = info.id; //_id from database in this case
            int selectpos = info.position; //position in the adapter
            HashMap<String, String> value;
            String chatId, username, usernameUc;
            switch (item.getItemId()) {
                case 0:
                    value = dataList.get(selectpos);
                    chatId = value.get("chatId");
                    username = value.get("Username");
                    usernameUc = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
//                Toast.makeText(UsersList.this,"User: "+usernameUc, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), UsersInfo.class);
                    intent.putExtra("UserInfoId", chatId);
                    intent.putExtra("UserInfoName", usernameUc);
                    intent.putExtra("UserInfoUsername", username);
                    startActivity(intent);

                    return true;
                case 1:
                    Toast.makeText(getContext(), "2Just now", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        return false;
    }


    private static class MyOnClickListener implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private final Context context;
        private SessionManagement sessionManagement;
        Intent intent;
        View viw;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
//            removeItem(v);
            ChatArtisan(v);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            viw = v;
            if (v.getId() == R.id.mainLayout) {
                MenuItem mnu1 = menu.add(0, 0, 0, "Block");
                mnu1.setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getGroupId() == 0) {
                View view = viw;
                int selectedItemPosition = recyclerView.getChildPosition(view);
                RecyclerView.ViewHolder viewHolder
                        = recyclerView.findViewHolderForPosition(selectedItemPosition);
                sessionManagement = new SessionManagement(context);

                TextView chatIdtx = (TextView) view.findViewById(R.id.chat_id);
                TextView user_name = (TextView) view.findViewById(R.id.user_name);

                HashMap<String, String> value = null;
                String chatId = chatIdtx.getText().toString(), username = user_name.getText().toString(), usernameUc;
                switch (item.getItemId()) {
                    case 0:
//                        value = dataList.get(selectedItemPosition);
//                        chatId = value.get("chatId");
//                        username = value.get("Username");
                        usernameUc = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
                        Toast.makeText(context, usernameUc + "Block", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;//super.onContextItemSelected(item);
                }
            }
            return false;
        }

        private void ChatArtisan(View view) {


            int selectedItemPosition = recyclerView.getChildPosition(view);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            sessionManagement = new SessionManagement(context);

            TextView chatId = (TextView) view.findViewById(R.id.chat_id);
            TextView description = (TextView) view.findViewById(R.id.description);

            TextView img = (TextView) view.findViewById(R.id.img_txt);
            TextView email = (TextView) view.findViewById(R.id.email_txt);
            String chatWit = "";
            TextView distKm = (TextView) view.findViewById(R.id.distance);
            chatWit = chatId.getText().toString();
//Toast.makeText(view.getContext(),"HERE"+chatWit,Toast.LENGTH_LONG).show();
            TextView token = (TextView) view.findViewById(R.id.token);
            HashMap<String,String> mapList=new HashMap<>();

            mapList.put("Image", img.getText().toString());
            mapList.put("chatId", chatWit);
            mapList.put("Description", "......Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc");
                    dataList.set(Integer.parseInt(chatWit),mapList);
            adapter = new VideosTabAdapter((Activity) view.getContext(), dataList);
//            recyclerView = (RecyclerView) view.findViewById(R.id.list_recycler);
            recyclerView.setAdapter(adapter);

        }



    }

}