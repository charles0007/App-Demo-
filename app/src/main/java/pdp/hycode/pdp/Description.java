package pdp.hycode.pdp;


/**
 * Created by HyCode on 5/16/2017.
 */

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.Typeface;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.DividerItemDecoration;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
//import android.support.v7.widget.CircleImageView;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.target.SimpleTarget;
        import com.bumptech.glide.request.transition.Transition;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.ui.storage.images.FirebaseImageLoader;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.MobileAds;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserInfo;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Objects;
        import java.util.UUID;

        import de.hdodenhof.circleimageview.CircleImageView;

public class Description extends AppCompatActivity {

    private Button sendEmail, remove, signOut;
    private ImageView btn_select_image;
    private EditText oldEmail, newEmail, password, newPassword;
    private TextView profile_name, profile_desc,noUsersText,profile_email;
    //    private TextView  profile_email, act_type;
    private ProgressDialog progressBar;

    private FirebaseAuth auth;
    SessionManagement session;
    StorageReference storageReference;
    String image = "";
    String phone = "";
    //    CircleImageView profile_img, old_profile_img;
    static FirebaseUser userDetails = null;
    DatabaseReference nRootRef;
    DatabaseReference dblobschat;
    DatabaseReference nUsers;
    DatabaseReference nProducts;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;
    //    ArrayAdapter adapter;
    private Uri filePath;
    //    Firebase reference1;
    Intent intent;
    String description,descriptionId;
    private AdView adView;
    String sex,email_txt,image_txt;
    //    static boolean calledAlready=false;
    ViewPager imageviewpager;
    static ArrayList<HashMap<String, String>> dataList;
    InnerImageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    int totalUsers=0;
    Button chatBtn;
    TextView distance;
    String descriptionToken,title;
    ArrayList<Drawable> GalImages;
    ArrayList<String> ImageArr;
    ArrayList<String> DescArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.description);
        session = new SessionManagement(Description.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent=getIntent();
        description=  intent.getStringExtra("Description");
        descriptionId=  intent.getStringExtra("DescriptionId");
        title=  intent.getStringExtra("Title");
        image_txt=  intent.getStringExtra("image_txt");

        descriptionToken=  intent.getStringExtra("DescriptionToken");
        String titleUc = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();

        toolbar.setTitle("  " + titleUc);
        // toolbar.setBackground(new ColorDrawable(Color.parseColor("#0000ff")));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       TextView txtDescription = (TextView) findViewById(R.id.description);
        ViewPager imageviewpager = (ViewPager) findViewById(R.id.imageviewpager);
//        ImageView imageviewpager=(ImageView)findViewById(R.id.imageviewpager);
        txtDescription.setText(description);
        GalImages=new ArrayList<>();
        ImageArr=new ArrayList<>();
        DescArr=new ArrayList<>();



//        MobileAds.initialize(this, "ca-app-pub-5527620381143347~2850647637");
//        adView = (AdView) findViewById(R.id.adView);
//
//        try{
//            if(!session.get("Ads").equalsIgnoreCase("UnSet") ){
//                AdRequest adRequest = new AdRequest.Builder().build();
//
//                adView.loadAd(adRequest);
//            }else{adView.setVisibility(View.INVISIBLE);}
//        }catch (Exception ex){
//            AdRequest adRequest = new AdRequest.Builder().build();
//
//            adView.loadAd(adRequest);
//            adView.setVisibility(View.VISIBLE);
//        }





//        act_type = (TextView) findViewById(R.id.act_type);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ERASMD.TTF");
        txtDescription.setTypeface(typeface);

        try{
        if(!image_txt.equals("") || image_txt !=null) {
//            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(image_txt);
            GlideApp.with(Description.this).asBitmap()
                    .placeholder(R.drawable.default_img)
                    .load(Integer.parseInt(image_txt))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            // do something with the bitmap
                            // set it to an ImageView

//Convert bitmap to drawable
                            Drawable drawable = new BitmapDrawable(Description.this.getResources(), bitmap);
                            GalImages.add(0,drawable);
                            DescArr.add(0,titleUc);
                            ImageArr.add(0,image_txt);
                            adapter = new InnerImageAdapter(Description.this,GalImages,DescArr,ImageArr);
                            imageviewpager.setAdapter(adapter);
//                                new InnerImageAdapter(activity,GalImages).notifyDataSetChanged();
                        }
                    });
        }
    }catch (Exception ex){}



    }


    public void signOut() {
        auth.signOut();
        session.logoutUser();
    }

//public void firebaseDesc(){
//
//    nUsers.child(UserInfoId).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//
//            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                if (postSnapshot.getKey().equals("Username")) {
//                    profile_name.setText(postSnapshot.getValue().toString());
//                    chatBtn.setText("Chat "+postSnapshot.getValue().toString());
//                }else if (postSnapshot.getKey().equals("Business")) {
//                    profile_desc.setText(postSnapshot.getValue().toString());
//                    if (profile_desc.getText().toString().isEmpty() ||
//                            profile_desc.getText().toString() == "") {
//                        profile_desc.setVisibility(View.GONE);
//                    } else {
//                        profile_desc.setVisibility(View.VISIBLE);
//                    }
//                } else if (postSnapshot.getKey().equals("Email")) {
//                    profile_email.setText(postSnapshot.getValue().toString());
//                }
//            }
////                progressBar.dismiss();
//        }
//
//        @Override
//        public void onCancelled(DatabaseError firebaseError) {
//
//        }
//    });
//
//    nProducts.child(UserInfoId).addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                HashMap<String, String> mapList = new HashMap<String, String>();
//                String Key = postSnapshot.getKey();
//                mapList.put("Key", Key);
//                for (DataSnapshot productSnapshot : postSnapshot.getChildren()) {
//                    if (productSnapshot.getKey().equals("Description")) {
//                        mapList.put("Description", productSnapshot.getValue().toString());
//                    } else if (productSnapshot.getKey().equals("Rating")) {
//                        mapList.put("Rating", productSnapshot.getValue().toString());
//
//                    } else if (productSnapshot.getKey().equals("Category")) {
//                        mapList.put("Category", productSnapshot.getValue().toString());
//                    } else if (productSnapshot.getKey().equals("Status")) {
//                        mapList.put("Status", productSnapshot.getValue().toString());
//                    } else if (productSnapshot.getKey().equals("Image")) {
//                        mapList.put("Image", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image2")) {
//                        mapList.put("Image2", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image3")) {
//                        mapList.put("Image3", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image4")) {
//                        mapList.put("Image4", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image5")) {
//                        mapList.put("Image5", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image6")) {
//                        mapList.put("Image6", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image7")) {
//                        mapList.put("Image7", productSnapshot.getValue().toString());
//                    }else if (productSnapshot.getKey().equals("Image8")) {
//                        mapList.put("Image8", productSnapshot.getValue().toString());
//                    }
//                }
//                int siz = 0;
//                int doesntExist = 0;
//                if(mapList.size()>=5) {
//
//                    for (HashMap<String, String> dat : dataList) {
//
//                        if (dat.containsValue(Key)) {
//
//                            dataList.set(siz, mapList);
//
//                        } else {
//                            doesntExist = doesntExist + 1;
//                        }
//                        if (doesntExist == dataList.size()) {
//                            dataList.add(totalUsers, mapList);
//                            totalUsers = totalUsers + 1;
//                        }
//                        siz = siz + 1;
//                    }
//                    if (dataList.size() < 1 && mapList.size() > 1) {
//                        dataList.add(totalUsers, mapList);
//                        totalUsers = totalUsers + 1;
//                    }
//                    if (dataList.size() < 1) {
//                        noUsersText.setVisibility(View.VISIBLE);
//
//                        recyclerView.setVisibility(View.GONE);
//                    } else {
//                        noUsersText.setVisibility(View.GONE);
//                        recyclerView.setVisibility(View.VISIBLE);
//
//                        adapter = new ProductInfoRecyclerAdapter(UsersInfo.this, dataList);
////                            recyclerView.addItemDecoration(new DividerItemDecoration(UsersInfo.this, new LinearLayoutManager(UsersInfo.this).getOrientation()));
//                        recyclerView.setAdapter(adapter);
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError firebaseError) {
//
//        }
//    });
//
//    chatBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            String chatWit="";
//
//            chatWit=UserInfoId;
//
//
//            Intent intent=new Intent(UsersInfo.this, FragmentChat.class);
//            intent.putExtra("usernameId", session.getUserDetails().get("User"));
//            intent.putExtra("username", session.get("MyUsername"));
//            intent.putExtra("chatWithId", chatWit);
//            intent.putExtra("UserToken", intent.getStringExtra("UserToken"));
//            intent.putExtra("chatWithSex", sex);
//            intent.putExtra("chatWithImage", image_txt);
//            intent.putExtra("chatWithEmail", email_txt);
//            intent.putExtra("Page", session.get("Page"));
//            intent.putExtra("ArtisanPage", session.get("ArtisanPage"));
//            intent.putExtra("chatWith", UserInfoName);
//
//            startActivity(intent);
//        }
//    });
//
//
//}

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class InnerImageAdapter extends PagerAdapter {
        Context context;
        private ArrayList<Drawable> GalImages;
        boolean donotfy=false;
        ArrayList<String> Image,DescUc;
        InnerImageAdapter(Context _con, ArrayList<Drawable> GalImages,ArrayList<String> DescUc,ArrayList<String> Image) {
            this.context = _con;
            this.GalImages=GalImages;
            donotfy=true;
            this.Image=Image;
            this.DescUc=DescUc;
        }

        @Override
        public int getCount() {

            return GalImages.size();

        }

        @Override

        public boolean isViewFromObject(View view, Object object) {

            return view == ((ImageView) object);

        }


        @Override

        public Object instantiateItem(ViewGroup container, int position) {
            donotfy=true;
            ImageView imageView = new ImageView(context);
            int pad = context.getResources().getDimensionPixelOffset(R.dimen.into_img);
//            imageView.setPadding(pad, pad, pad, pad);
            imageView.setMaxHeight(pad);
            imageView.setMaxWidth(pad);

            imageView.setImageDrawable(GalImages.get(position));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ((ViewPager) container).addView(imageView, 0);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Image.get(position).isEmpty()){
                        Toast.makeText(Description.this,"Image not available",Toast.LENGTH_LONG).show();
                    }else {
                        new ImageClicked(Description.this, v, DescUc.get(position), Image.get(position), null, "default");
                    }
                }
            });

            return imageView;

        }

        @Override

        public void destroyItem(ViewGroup container, int position, Object object) {
            donotfy=true;
            ((ViewPager) container).removeView((ImageView) object);

        }
    }


}