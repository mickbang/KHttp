package com.lq.khttp.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * description:
 *  请求基类
 *
 *  包含各种常用的配置
 *
 * @author mick
 * @date 2019/6/13
 */
public abstract class BaseRequest<R extends BaseRequest> {

    /**
     * 进行网络请求
     *
     * @return 网络请求的响应
     */
    protected abstract Observable<ResponseBody> generateRequest();
}
