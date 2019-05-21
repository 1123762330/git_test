package com.xn.bmpos.manager.bmposmanager.util;

import com.xn.bmpos.manager.bmposmanager.domain.model.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpServiletResult {

    @POST("/sso/oauth/token")
    Call<HttpResultTokenModel> getOauthToken(@Header(value = "Authorization") String head,
                                             @Query(value = "grant_type") String grant_type,
                                             @Query(value = "username") String username,
                                             @Query(value = "password") String password);

    @POST("/sso/social")
    Call<HttpResultJsonModel> getSocial(@Query(value = "type") String type, @Query(value = "openId") String openId);

    @GET("/sso/oauth/check_token")
    Call<HttpResuletCheakTokenModel> tokenVerify(@Header("Authorization") String head, @Query("token") String token);

    @POST("/sso/sign")
    Call<HttpResultJsonModel> getSign(@Query(value = "username") String username, @Query(value = "phone") String phone, @Query(value = "password") String password);

    @POST("/sso/updateUser")
    Call<HttpResultJsonModel> getUpdateUser(@Query(value = "userModel") UpdateUserModel userModel);

    @POST("/sso/updateUser")
    Call<HttpResultUpdateModel> getUpdateUserEmail(@Query(value = "username") String username, @Query(value = "email") String email);

    @POST("/sso/updateUser")
    Call<HttpResultUpdateModel> getUpdateUserPhone(@Query(value = "username")String username, @Query(value = "phone")String phone);

    @POST("/sso/updateUser")
    Call<HttpResultUpdateModel> getUpdateUserPwd(@Query(value = "username")String username,@Query(value = "pwd") String pwd);
}
