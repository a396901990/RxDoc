package com.example.deanguo.myapplication.network;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by deanguo on 11/25/16.
 */
public interface SignUpService {
//
    @GET("check_phone")
    Observable<JSONObject> checkPhone(@Query("account") String account);

    @POST("api/account/check")
    @FormUrlEncoded
    Observable<SEResponse<JSONObject>> checkPhone(@Field("channel") String channel, @Field("account") String account);
}
