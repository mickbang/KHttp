package com.lq.khttp.request;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * description:
 *
 * @author mick
 * @date 2019/6/13
 */
public class GetRequest extends BaseRequest<GetRequest> {
    Map<String,Object> mQueryMap = new HashMap<>();

    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return mApiManager.get(getUrl(), mQueryMap);
    }

    /**
     * 设置get参数
     * @param queryMap
     * @return
     */
    public GetRequest setQueryMap(Map<String, Object> queryMap) {
        mQueryMap = queryMap;
        return this;
    }
}
