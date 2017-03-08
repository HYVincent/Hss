package com.vincent.hss.network;


import android.net.Uri;
import android.util.Log;

import com.vincent.hss.config.Config;
import com.vincent.hss.network.service.ApiService;
import com.vise.log.ViseLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/2/23 10:12
 *
 * @version 1.0
 */

public class RetrofitUtils {

    private static Retrofit retrofit;

    /**
     * 获取一个默认配置BaseUrl地址的
     * @return
     */
    private static Retrofit getRetrofit(){
          return   retrofit = new Retrofit.Builder()
                    .baseUrl(Config.SERVICE_API_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    ViseLog.d("请求地址-->"+request.url());
                                    ViseLog.d(request);
                                    ViseLog.d("本次请求是否为https-->"+request.isHttps());
                                    return chain.proceed(request);
                                }
                            }).build())
                    .build();
    }

    /**
     * 动态配置BaseUrl
     * @param baseUrl
     * @return
     */
    private static   Retrofit getRetrofit (String baseUrl){
            return retrofit = new Retrofit.Builder()
//                    .baseUrl(Config.SERVICE_API_ADDRESS)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    ViseLog.d("请求地址-->"+request.url());
                                    ViseLog.d(request);
                                    ViseLog.d("本次请求是否为https-->"+request.isHttps());
                                    return chain.proceed(request);
                                }
                            }).build())
                    .build();
    }

    public static ApiService getApiService(){
        return getRetrofit().create(ApiService.class);
    }

    public static ApiService getApiService(String baseUrl){
        return getRetrofit(baseUrl).create(ApiService.class);
    }
}
