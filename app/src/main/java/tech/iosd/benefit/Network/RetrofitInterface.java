package tech.iosd.benefit.Network;

/**
 * Created by SAM33R on 25-05-2018.
 */

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

import tech.iosd.benefit.Model.Measurements;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.ResponseForMeasurementsUpdate;
import tech.iosd.benefit.Model.ResponseForMesurementsHistory;
import tech.iosd.benefit.Model.ResponseForUpdate;
import tech.iosd.benefit.Model.User;
import tech.iosd.benefit.Model.UserDetails;
import tech.iosd.benefit.Model.UserForLogin;
import tech.iosd.benefit.Model.UserGoogleLogin;
import tech.iosd.benefit.Model.UserProfileUpdate;

public interface RetrofitInterface {

    @POST("auth/signup")
    Observable<Response> register(@Body User user);

    @POST("auth/login")
    Observable<Response> login(@Body UserForLogin userForLogin);

    @POST("auth/login/google")
    Observable<Response> loginGoogle(@Body UserGoogleLogin userGoogleLogin);

    @POST("profile/update")
    Observable<ResponseForUpdate> update(@Header("authorization") String token,@Body UserProfileUpdate userProfileUpdate);

    @POST("profile/measurements")
    Observable<ResponseForMeasurementsUpdate> updateMeasurements(@Header("authorization") String token, @Body Measurements measurements );

    @GET("profile")
    Observable<UserDetails> getProfile(@Header("Authorization") String token);

    @GET("profile/measurements/history")
    Observable<ResponseForMesurementsHistory> getMeasurementsHistory(@Header("Authorization") String token);

    /*@GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);*/

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}