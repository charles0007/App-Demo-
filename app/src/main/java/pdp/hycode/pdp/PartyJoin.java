package pdp.hycode.pdp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.regex.Pattern;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class PartyJoin extends AppCompatActivity {
    boolean exist = false;
    Button btnJoin;
    public static String userPresent;
    public static int useridPresent;
    AutoCompleteTextView login_email;
    EditText login_pass;
    SessionManagement session;
    static String rlogin_email, ruser, rlogin_pass;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;
    boolean loginSuccess = false;


    boolean boolVal = true;
    View mRegFormView;
    public static boolean welcomeScreenIsShown = true;

    int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    ProgressDialog pd;
    TextView regLogin;
    //    String androidID;
    static String mainKey;

    String TodayDate;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    private String appName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        appName=getString(R.string.app_name);

        setContentView(R.layout.party_join);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        login_email = (AutoCompleteTextView) findViewById(R.id.login_email);
        login_pass = (EditText) findViewById(R.id.login_pass);
        regLogin = (TextView) findViewById(R.id.regLogin);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/poppins/Poppins-Light.otf");
        btnJoin.setTypeface(typeface);
        login_pass.setTypeface(typeface);
        login_email.setTypeface(typeface);
        regLogin.setTypeface(typeface);
//        addAdapterToViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("    PDP");
        // toolbar.setColsetBackground(new ColorDrawable(Color.parseColor("#0000ff")));
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.pdp);
        getSupportActionBar().setTitle(R.string.app_name);

        getSupportActionBar().setDisplayUseLogoEnabled(true);


        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rs = 0;
                rlogin_email = login_email.getText().toString();
                rlogin_pass = login_pass.getText().toString();
//
//                login_email.setError(null);
//                login_pass.setError(null);
//
//                if (TextUtils.isEmpty(rlogin_email)) {
//                    login_email.setError(getString(R.string.error_field_required));
//                } else if (TextUtils.isEmpty(rlogin_pass)) {
//                    login_pass.setError(getString(R.string.error_field_required));
//                } else if (rlogin_pass.length() < 6) {
//                    login_pass.setError(getString(R.string.minimum_password));
//                } else {

//                    pd = new ProgressDialog(Join.this);
//                    pd.setMessage("Loading...");
//                    pd.show();
//                    pd.setCancelable(false);
//                    pd.setCanceledOnTouchOutside(false);
                startActivity(new Intent(PartyJoin.this, MainActivity.class));
//                    mAuthTask = new UserLoginTask(rlogin_email, rlogin_pass);
//                    mAuthTask.execute((Void) null);

//                }

            }
        });

    }



}
