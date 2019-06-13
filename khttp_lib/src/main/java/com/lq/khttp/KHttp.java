package com.lq.khttp;

import android.content.Context;
import android.text.TextUtils;

import com.lq.khttp.api.ApiService;
import com.lq.khttp.provider.RetrofitManager;
import com.lq.khttp.transformer.HttpSchedulersTransformer;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


/**
 * description:
 *
 * @author mick
 * @date 2019/6/10
 */
@SuppressWarnings({"unused"})
public class KHttp {
    private static String baseUrl = "";
    private static Context sContext;


    public static void init(Context context, String baseUrl) {
        KHttp.sContext = context;
        KHttp.baseUrl = baseUrl;
    }


    private static Retrofit generateRetrofit() {
        return RetrofitManager.getInstance().getBuilder().build();
    }


    public <T> T createApi(Class<T> service) {
        return generateRetrofit().create(service);
    }


    public static Context getContext() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("必须先调用KHttp.init初始化");
        }

        return sContext;
    }

    public static String getBaseUrl() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new ExceptionInInitializerError("必须先调用KHttp.init初始化");
        }
        return baseUrl == null ? "" : baseUrl;
    }

    /**
     * 简单参数的post请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static Observable<ResponseBody> post(String url, Map<String, Object> paramsMap) {
        return generateRetrofit().create(ApiService.class)
                .post(url, paramsMap)
                .compose(new HttpSchedulersTransformer<ResponseBody>());
    }

    /**
     * 简单参数的get请求
     *
     * @param url
     * @param queryMap
     * @return
     */
    public static Observable<ResponseBody> get(String url, Map<String, Object> queryMap) {
        return generateRetrofit().create(ApiService.class)
                .get(url, queryMap)
                .compose(new HttpSchedulersTransformer<ResponseBody>());
    }

    /**
     * 带文件的post请求
     *
     * @param url
     * @param map
     * @return
     */
    public static Observable<ResponseBody> postWithFile(String url, Map<String, RequestBody> map) {
        return generateRetrofit().create(ApiService.class)
                .uploadFiles(url, map);
    }

    /**
     * 文件下载
     *
     * @param url
     * @return
     */
    public static Observable<ResponseBody> down(String url) {
        return generateRetrofit().create(ApiService.class)
                .downloadFile(url);
    }

    /**
     * 文件上传
     *
     * @param url
     * @param map
     * @return
     */
    public static Observable<ResponseBody> upload(String url, Map<String, RequestBody> map) {
        return generateRetrofit().create(ApiService.class)
                .uploadFiles(url, map);
    }
}
