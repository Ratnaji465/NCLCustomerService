package com.ncl.nclcustomerservice.uploadfiles;

import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface RestApiService {

    @Multipart
    @POST("SendMyFileToS3")
    Single<ResponseBody> onFileUpload(/*@Part("email") RequestBody mEmail,*/ @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("InsertSitesDetails")
    Call<Object> onPostData(@FieldMap Map<String, String> fieldMap);

    @FormUrlEncoded
    @POST("InsertCCESiteData")
    Call<Object> onPostInsertCCESiteData(@FieldMap Map<String, String> fieldMap);

    @FormUrlEncoded
    @POST("InsertCCESiteData")
    Single<Object> onPostInsertCCESiteDataOffLine(@FieldMap Map<String, String> fieldMap);

    @FormUrlEncoded
    @POST("InsertFarmerDetails")
    Call<Object> onPostInsertFarmerDetails(@FieldMap Map<String, String> fieldMap);

    @POST
    Single<ApiResponseController> callPost(@Url String path, @Body ApiRequestController requestController);



}
