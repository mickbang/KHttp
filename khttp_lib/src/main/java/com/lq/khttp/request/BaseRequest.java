package com.lq.khttp.request;

import com.lq.khttp.KHttp;
import com.lq.khttp.api.ApiService;
import com.lq.khttp.provider.RetrofitManager;
import com.lq.khttp.transformer.HttpSchedulersTransformer;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * description:
 * 请求基类
 * <p>
 * 包含各种常用的配置
 *
 * @author mick
 * @date 2019/6/13
 */
public abstract class BaseRequest<R extends BaseRequest> {
    protected String mBaseUrl;
    protected String mUrl = "";

    protected Retrofit mRetrofit;
    protected OkHttpClient mHttpClient;
    protected ApiService mApiManager;

    public BaseRequest(String url) {
        mUrl = url;
        mBaseUrl = KHttp.getBaseUrl();
    }

    /**
     * 进行网络请求
     *
     * @return 网络请求的响应
     */
    protected abstract Observable<ResponseBody> generateRequest();


    protected R build() {
        mRetrofit = RetrofitManager.getInstance().getBuilder().build();
        mApiManager = mRetrofit.create(ApiService.class);
        return (R) this;
    }


    public String getUrl() {
        return mUrl == null ? "" : mUrl;
    }

    public  Observable<ResponseBody> excute(){
        return build().generateRequest()
                .compose(new HttpSchedulersTransformer<ResponseBody>());


//                        .compose()

    }
}
