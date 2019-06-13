package com.lq.khttp.provider;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.lq.khttp.KHttp;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * description:
 * 提供retrofit的build,并进行一些初始化的设置
 *
 * @author mick
 * @date 2019/6/10
 */
public class RetrofitManager {
    private static RetrofitManager sInstance = null;
    private Retrofit.Builder mBuilder;

    private RetrofitManager() {
        if (TextUtils.isEmpty(KHttp.baseUrl)) {
            throw new ExceptionInInitializerError("必须先调用KHttp.init初始化");
        }

        generateBuilder();
    }

    private void generateBuilder() {
        mBuilder = new Retrofit.Builder();
        mBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()
                ))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClientManager.getInstance().getHttpClientBuilder().build())
                .baseUrl(KHttp.baseUrl);
    }

    public static RetrofitManager getInstance() {
        RetrofitManager instance = sInstance;
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = RetrofitManager.sInstance;
                if (instance == null) {
                    instance = RetrofitManager.sInstance = new RetrofitManager();
                }
            }
        }
        return instance;
    }


    public Retrofit.Builder getBuilder() {
        return mBuilder;
    }

    public void setBuilder(Retrofit.Builder builder) {
        mBuilder = builder;
    }


}
