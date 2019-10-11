package pdp.hycode.pdp;

/**
 * Created by HyCode on 12/22/2017.
 */


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    protected Context context;


    SessionManagement sessionManagement;
    //    String address,city,state,knownName,country,postalCode;
//    Geocoder geocoder;
//    sendRegTask mAuthTask;
    DatabaseReference nusers;
    DatabaseReference newuser;
    DatabaseReference dbToken;
    DatabaseReference nRootRef;
    DatabaseReference dblobschat;

    static boolean calledAlready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try{
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }catch (Exception ex){}
//
//         nRootRef= FirebaseDatabase.getInstance().getReference();
//         dblobschat=nRootRef.child("lobschat");
//
//        sessionManagement=new SessionManagement(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("People's Democratic Party");
        setSupportActionBar(toolbar);

//        mAuthTask = new sendRegTask();
//        mAuthTask.execute((Void) null);
//        try {
//             nusers = dblobschat.child("users");
//             newuser = nusers.child(sessionManagement.get(sessionManagement.KEY_NAME));
//             dbToken = newuser.child("Token");
//            dbToken.setValue(sessionManagement.get("Token"));
//        }catch(Exception ex){
//
//        }

        // Display icon in the toolbar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.logof);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ERASMD.TTF");
//        TextView tabPdp=(TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
//        tabPdp.setText("PDP");
//        tabPdp.setTypeface(typeface);
//        tabPdp.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.pdp,0,0,0);
//        TextView tabImages=(TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
//        tabImages.setText("Images");
//        tabImages.setTypeface(typeface);
//        tabImages.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_camera_alt_white_18dp,0,0,0);
//        TextView tabVideos=(TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
//        tabVideos.setText("Videos");
//        tabVideos.setTypeface(typeface);
//        tabVideos.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_camera_alt_white_18dp,0,0,0);
//        TextView tabNews=(TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
//        tabNews.setText("Other Newsss");
//        tabNews.setTypeface(typeface);
//        tabNews.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_markunread_white_18dp,0,0,0);
//        tabLayout.getTabAt(0).setCustomView(tabPdp);
//        tabLayout.getTabAt(1).setCustomView(tabImages);
//        tabLayout.getTabAt(2).setCustomView(tabVideos);
//        tabLayout.getTabAt(3).setCustomView(tabNews);


//tabLayout.addTab(createTab("PDP",R.drawable.pdp));
//        tabLayout.addTab(createTab("Images",R.drawable.pdp));
//        tabLayout.addTab(createTab("Videos",R.drawable.pdp));
//        tabLayout.addTab(createTab("Other News",R.drawable.pdp));

//        ((ViewGroup.MarginLayoutParams) (ImageView) tabLayout.getTabAt(0).setIcon(R.drawable.pdp).setCustomView(R.layout.tab_layout).getCustomView().findViewById(android.R.id.icon).getLayoutParams()).bottomMargin = 0;

        /**
         * on swiping the viewpager make respective tab selected
         * */
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                // on changing the page
//                // make respected tab selected
////                viewPager.setCurrentItem(position);
//
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //    private TabLayout.Tab createTab(String text, int icon){
//
//        TabLayout.Tab tab = tabLayout.newTab().setText(text).setIcon(icon).setCustomView(R.layout.tab_layout);
//
//        if(tab.getCustomView()!=null) {
////            ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.tab_icon);
//            ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) imageView.getLayoutParams());
//            lp.bottomMargin = 0;
//            imageView.requestLayout();
//        }
//        return tab;
//
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (R.id.nav_state == id) {
            final ViewPagerAdapterState state_adapter = new ViewPagerAdapterState(getSupportFragmentManager(), this);
            viewPager.setAdapter(state_adapter);


        } else if (R.id.nav_city == id) {

            final ViewPagerAdapterCity city_adapter = new ViewPagerAdapterCity(getSupportFragmentManager(), this);
            viewPager.setAdapter(city_adapter);

        } else {
            final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
            viewPager.setAdapter(adapter);

        }
        tabLayout.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}