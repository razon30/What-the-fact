package com.razon.whatthefact.rest;

import android.support.annotation.Keep;

import com.razon.whatthefact.Response;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by HP on 26-Oct-17.
 */
@Keep
public interface FactClient {

    @Headers("Content-Type: application/json")
    @GET("/{number}/{type}")
    Observable<Response> getResponse(@Path("number") String number, @Path("type") String type);

}
