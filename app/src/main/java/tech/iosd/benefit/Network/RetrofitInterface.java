package tech.iosd.benefit.Network;

/**
 * Created by SAM33R on 25-05-2018.
 */

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.User;
import tech.iosd.benefit.Model.UserForLogin;

public interface RetrofitInterface {

    @POST("auth/signup")
    Observable<Response> register(@Body User user);

    @POST("auth/login")
    Observable<Response> login(@Body UserForLogin userForLogin);

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}