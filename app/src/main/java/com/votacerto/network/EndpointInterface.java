package com.votacerto.network;

import com.votacerto.model.Analysis;
import com.votacerto.model.Candidate;
import com.votacerto.model.Tweet;
import com.votacerto.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface EndpointInterface {
    @FormUrlEncoded
    @POST("/user/auth")
    Observable<User> auth(@Field("fb_token") String fbToken);

    @GET("/tweet")
    Observable<List<Tweet>> getTweets(@Query("access_token") String token);

    @FormUrlEncoded
    @POST("/analysis")
    Observable<Analysis> sendAnalysis(@Query("access_token") String token,
                                      @Field("tweet_id") Integer tweedId,
                                      @Field("sentiment") String sentiment);

    @GET("/politician/me")
    Observable<List<Candidate>> getCandidates(@Query("access_token") String token);

    @GET("/analysis/politician/{id}")
    Observable<List<Analysis>> getAnalysisByPolitician(@Path("id") Integer id, @Query("access_token") String token);
}
