package com.lq.khttp.request;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * description:
 *
 * @author mick
 * @date 2019/6/13
 */
public class BaseBodyRequest<R extends BaseRequest> extends BaseRequest {
    private Map<String, Object> mParamsMap;
    protected String mJson;                                     //上传的Json
    protected byte[] mBytes;                                       //上传的字节数据
    protected Object mObject;                                   //上传的对象
    protected RequestBody mRequestBody;                         //自定义的请求体



    public BaseBodyRequest(String url) {
        super(url);

    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (mParamsMap != null) {
            mApiManager.post(getUrl(), mParamsMap);
        }else if (mJson !=null){
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJson);
            return mApiManager.postJson(getUrl(), body);
        }else if (mBytes !=null){
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), mBytes);
            return mApiManager.postBody(getUrl(), body);
        }else if (mObject!=null){
            return mApiManager.postBody(getUrl(), mObject);
        }else if (mRequestBody !=null){
            return mApiManager.postBody(getUrl(), mRequestBody);
        }


        return null;
    }


    public R setJson(String json) {
        mJson = json;
        return (R) this;
    }

    public R setBytes(byte[] bytes) {
        mBytes = bytes;
        return (R) this;
    }

    public R setObject(Object object) {
        mObject = object;
        return (R) this;
    }

    public R setRequestBody(RequestBody requestBody) {
        mRequestBody = requestBody;
        return (R) this;
    }

    public R setParamsMap(Map<String, Object> paramsMap) {
        mParamsMap = paramsMap;
        return (R) this;
    }
}
