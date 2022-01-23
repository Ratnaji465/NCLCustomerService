package com.ncl.nclcustomerservice.network;


import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by sys on 12/6/2017.
 */

public interface APIInterface {
    @POST
    Call<ApiResponseController> callPost(@Url String path, @Body ApiRequestController requestController);

    @GET
    Call<Object> callGet(@Body Object o);

    @Multipart
    @POST
    Call<ResponseBody> uploadFile(@Url String path,
                                  @Part MultipartBody.Part file);


    @Multipart
    @POST
    Call<ResponseBody> uploadFiles(@Url String path,
                                   @Part MultipartBody.Part[] file,
                                   @Part("requestparameters") Object object);
  @Multipart
    @POST
    Call<ResponseBody> uploadPaymentCollection(@Url String path,
                                   @Part MultipartBody.Part[] file,
                                   @Part("requestParams") Object object);

    @Multipart
    @POST
    Call<ApiResponseController> uploadProfile(@Url String path,
                                              @Part MultipartBody.Part[] file,
                                              @Part("requestparameters") Object object);


}
