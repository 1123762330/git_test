package com.xnpool.account.util;


import com.xnpool.account.model.HttpResuletCheakTokenModel;
import com.xnpool.account.model.HttpResultJsonModel;
import com.xnpool.account.model.HttpResultTokenModel;
import com.xnpool.account.model.HttpResultUpdateModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Slf4j
@Service
public class HttpTokenUtil {

    private static Base64.Encoder encoder = Base64.getEncoder();

    public static String OauthServerRul;


    @Value("${oauthServer.Url}")
    public void setOauthServerRul(String oauthServerRul) {
        OauthServerRul = oauthServerRul;
    }

    /**
     * app应用
     */
    private static String OauthClientId;

    @Value("${oauthServer.client-id}")
    public void setAppOauthClientId(String appClientId) {
        OauthClientId = appClientId;
    }

    private static String OauhtClientSecret;

    @Value("${oauthServer.client-secret}")
    public void setAppOauhtClientSecret(String appClientSecret) {
        OauhtClientSecret = appClientSecret;
    }


    public static String getBace64Code(String cleintId, String clientSecret) throws UnsupportedEncodingException {
        //对应用bace64编码，设置请求头
        String headvalue = cleintId + ":" + clientSecret;
        byte[] headvalueBytes = headvalue.getBytes("GBK");
        String encodedValue = encoder.encodeToString(headvalueBytes);
        //添加字段
        String FullEncode = "Basic " + encodedValue;
        return FullEncode;
    }


    /**
     * app验证token
     */
    public static HttpResultTokenModel getToken(String username, String password) throws UnsupportedEncodingException {
        HttpResultTokenModel httpResultTokenModel = new HttpResultTokenModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);

        //对 发送请求 进行封装
        String head = getBace64Code(OauthClientId, OauhtClientSecret);
        String grant_type = "password";

        Call<HttpResultTokenModel> call = request.getOauthToken(head, grant_type, username, password);

        try {
            httpResultTokenModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }

        return httpResultTokenModel;
    }


    /**
     * 校验open是否存在
     *
     * @param type
     * @param openId
     */
    public static HttpResultJsonModel getSocial(String type, String openId) {
        HttpResultJsonModel httpResultJsonModel = new HttpResultJsonModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);

        Call<HttpResultJsonModel> call = request.getSocial(type, openId);
        try {
            httpResultJsonModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return httpResultJsonModel;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpResuletCheakTokenModel tokenVerify(String token) throws UnsupportedEncodingException {
        HttpResuletCheakTokenModel httpResuletCheakTokenModel = new HttpResuletCheakTokenModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);

        //对 发送请求 进行封装
        String head = getBace64Code(OauthClientId, OauhtClientSecret);
        Call<HttpResuletCheakTokenModel> call = request.tokenVerify(head, token);
        try {
            httpResuletCheakTokenModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return httpResuletCheakTokenModel;


    }


    /**
     * 用户注册
     *
     * @param username
     * @param phone
     * @param password
     */
    public static HttpResultJsonModel getSign(String username, String phone, String password) {
        HttpResultJsonModel httpResultJsonModel = new HttpResultJsonModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);

        //对 发送请求 进行封装
        Call<HttpResultJsonModel> call = request.getSign(username, phone, password);
        try {
            httpResultJsonModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return httpResultJsonModel;
    }

    /**
     * 用户修改
     */
    public static HttpResultUpdateModel getUpdateUserPhone(String username, String phone) {
        HttpResultUpdateModel httpResultUpdateModel = new HttpResultUpdateModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);
        //对 发送请求 进行封装
        Call<HttpResultUpdateModel> call = request.getUpdateUserPhone(username, phone);
        try {
            httpResultUpdateModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return httpResultUpdateModel;
    }

    public static HttpResultUpdateModel getUpdateUserEmail(String username, String email) {
        HttpResultUpdateModel object = new HttpResultUpdateModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);
        //对 发送请求 进行封装
        Call<HttpResultUpdateModel> call = request.getUpdateUserEmail(username, email);
        try {
            object = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return object;
    }


    public static HttpResultUpdateModel getUpdateUserPwd(String username, String pwd) {
        HttpResultUpdateModel httpResultUpdateModel = new HttpResultUpdateModel();
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl(OauthServerRul)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiletResult request = retrofit.create(HttpServiletResult.class);
        //对 发送请求 进行封装
        Call<HttpResultUpdateModel> call = request.getUpdateUserPwd(username, pwd);
        try {
            httpResultUpdateModel = call.execute().body();
        } catch (IOException e) {
            System.out.println("认证连接失败");
            e.printStackTrace();
        }
        return httpResultUpdateModel;
    }


}
