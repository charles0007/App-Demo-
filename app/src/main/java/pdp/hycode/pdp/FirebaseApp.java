package pdp.hycode.pdp;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import pdp.hycode.pdp.firebase_notification.FCM;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


public interface FirebaseApp {

    @POST("send")
    Call<ResponseBody> send(
            @HeaderMap Map<String, String> headers,
            @Body FCM message
            );

}
