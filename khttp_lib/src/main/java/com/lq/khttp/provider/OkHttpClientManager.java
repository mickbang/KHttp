package com.lq.khttp.provider;

import com.lq.khttp.KHttp;
import com.lq.khttp.https.DefaultHostnameVerifier;
import com.lq.khttp.interceptor.CacheInterceptor;
import com.lq.khttp.interceptor.HttpLoggingInterceptor;
import com.lq.khttp.logs.HttpLog;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * description:
 * 提供OkHttpClient并进行默认设置
 *
 * @author mick
 * @date 2019/6/10
 */
public class OkHttpClientManager {

    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 60;             //默认的超时时间
    public static final int DEFAULT_RETRY_COUNT = 0;                  //默认重试次数
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 0;         //默认重试叠加时间
    public static final int DEFAULT_RETRY_DELAY = 500;
    //缓存大小 200Mb
    private static final long CACHE_SIZE = 1024 * 1024 * 200;           //缓存大小 200Mb
    public static String HTTP_CACHE_PATH = "httpCache";//http缓存
    private static OkHttpClientManager sInstance = null;
    private OkHttpClient.Builder mHttpClientBuilder;


    private OkHttpClientManager() {
        mHttpClientBuilder = new OkHttpClient.Builder();

        //缓存
        File cacheFile = new File(KHttp.sContext.getCacheDir(), HTTP_CACHE_PATH);
        //创建缓存目录
        createFile(cacheFile);
        Cache cache = new Cache(cacheFile, CACHE_SIZE);

        mHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier())
                .connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //有网络时的拦截器
                .addNetworkInterceptor(CacheInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                //没网络时的拦截器
                .addInterceptor(CacheInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                //日志拦截器
                .addInterceptor(new HttpLoggingInterceptor(HttpLog.DEFAULT_LOG_TAG))
//                //统一添加请求头
//                .addInterceptor(InterceptorUtils.getResponseHeader())
                //缓存目录和大小
                .cache(cache)
                //允许http重定向
                .followRedirects(true)
                //禁止使用代理
                .proxy(Proxy.NO_PROXY)
                // 连接失败后是否重新连接
                .retryOnConnectionFailure(true);
    }

    public static OkHttpClientManager getInstance() {
        OkHttpClientManager instance = sInstance;
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                instance = OkHttpClientManager.sInstance;
                if (instance == null) {
                    instance = OkHttpClientManager.sInstance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }


    public OkHttpClient.Builder getHttpClientBuilder() {
        return mHttpClientBuilder;
    }

    public void setHttpClientBuilder(OkHttpClient.Builder httpClientBuilder) {
        mHttpClientBuilder = httpClientBuilder;
    }

    /**
     * 强行创建文件夹
     *
     * @param file
     */
    public void createFile(File file) {
        if (!file.exists())
            file.mkdirs();
        else if (!file.isDirectory() && file.canWrite()) {
            file.delete();
            file.mkdirs();
        }
    }
}
