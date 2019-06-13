package com.lq.khttp.subsciber;

import com.lq.khttp.exception.ApiException;
import com.lq.khttp.exception.ApiExceptionHandler;
import com.lq.khttp.logs.HttpLog;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * description:
 *
 * @author mick
 * @date 2019/6/12
 */
public abstract class BaseSubsciber<T> extends DisposableSubscriber<T> {


    public BaseSubsciber() {
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Throwable e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        HttpLog.e("--> Subscriber is onError");
        try {
            if (e instanceof ApiException) {
                HttpLog.e("--> e instanceof ApiException, message:" + e.getMessage());
                onError((ApiException) e);
            } else {
                HttpLog.e("--> e !instanceof ApiException, message:" + e.getMessage());
                onError(ApiExceptionHandler.handleException(e));
            }
        } catch (Throwable throwable) {  //防止onError中执行又报错导致rx.exceptions.OnErrorFailedException: Error occurred when trying to propagate error to Observer.onError问题
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 出错
     *
     * @param e 出错信息
     */
    protected abstract void onError(ApiException e);

    /**
     * 安全版的{@link #onNext},自动做了try-catch
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

}
