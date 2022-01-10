package com.camp.project2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/retrofit/get") //get은 필요한 데이터를 body에 담지 않고 쿼리스트링을 통해 전달.
    Call<ResponseBody> getFunc(@Query("data") String data);

    @FormUrlEncoded
    @POST("/signup") //메세지를 body에 담아서 전달. POST로 데이터를 전송할때에는 Body영역 데이터 타입을 Header Content-Type에 명시를 해줘야 합니다.
    //Call<ResponseBody> postFunc(@Field("data")JSONObject data);
    Call<ResponseBody> signUp(@Field("username") String name, @Field("userid") String id, @Field("userpwd") String pwd);

    @FormUrlEncoded
    @POST("/login") //메세지를 body에 담아서 전달. POST로 데이터를 전송할때에는 Body영역 데이터 타입을 Header Content-Type에 명시를 해줘야 합니다.
    Call<ResponseBody> logIn(@Field("userid") String userid, @Field("userpwd") String userpwd );

    @FormUrlEncoded
    @PUT("/retrofit/put/{id}") //
    Call<ResponseBody> putFunc(@Path("id") String id, @Field("data") String data);

    @DELETE("/retrofit/delete/{id}")
    Call<ResponseBody> deleteFunc(@Path("id") String id);
}

