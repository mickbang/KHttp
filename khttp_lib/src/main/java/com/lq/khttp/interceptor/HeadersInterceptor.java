/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lq.khttp.interceptor;


import com.lq.khttp.logs.HttpLog;
import com.lq.khttp.model.HttpHeaders;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 配置公共头部
 *
 * @author xuexiang
 * @since 2018/6/20 上午1:19
 */
public class HeadersInterceptor implements Interceptor {

    private HttpHeaders headers;

    public HeadersInterceptor(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers.headersMap.isEmpty()) return chain.proceed(builder.build());
        try {
            for (Map.Entry<String, String> entry : headers.headersMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e) {
            HttpLog.e(e);
        }
        return chain.proceed(builder.build());

    }
}
