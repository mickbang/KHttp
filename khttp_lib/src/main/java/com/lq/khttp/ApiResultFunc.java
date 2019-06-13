package com.lq.khttp;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lq.khttp.model.ApiResult;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * description:
 *
 * @author mick
 * @date 2019/6/13
 */
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    private Type mType;
    private Gson mGson;

    public ApiResultFunc() {
        mGson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        mType = new TypeToken<ApiResult<T>>() {
        }.getType();
    }

    @Override
    public ApiResult<T> apply(ResponseBody responseBody) throws Exception {
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setCode(-1);
        apiResult = parseApiResult(responseBody, apiResult);
        return apiResult;
    }

    @NonNull
    private ApiResult<T> parseApiResult(ResponseBody responseBody, ApiResult<T> apiResult) {
        try {
            final String json = responseBody.string();
            final ApiResult result = parseApiResult(json, apiResult);
            apiResult = result;
        } catch (JSONException e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } finally {
            responseBody.close();
        }
        return apiResult;
    }

    /**
     * 解析请求到的json内容
     *
     * @param json
     * @param apiResult
     * @return
     * @throws JSONException
     */
    private ApiResult parseApiResult(String json, ApiResult apiResult) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        apiResult = mGson.fromJson(json, mType);
        return apiResult;
    }
}
