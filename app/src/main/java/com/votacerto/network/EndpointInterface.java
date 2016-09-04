package com.votacerto.network;

import com.votacerto.model.Tweet;
import com.votacerto.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface EndpointInterface {
    @FormUrlEncoded
    @POST("/user/auth")
    Observable<User> auth(@Field("fb_token") String fbToken);

    @GET("/tweet")
    Observable<List<Tweet>> getTweets(@Query("access_token") String token);
}
