package com.example.laptopduniya.api;

import com.example.laptopduniya.models.StudentVerify;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    @Multipart
    @POST("student/")
    Call<StudentVerify> createStudent(@Part MultipartBody.Part id_pic,
                                   @Part("email") RequestBody email,
                                   @Part("id_number") RequestBody id_number);

    @GET("student/{email}")
    Call<StudentVerify> getStudent(@Path("email") String email);
}
