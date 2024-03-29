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

import okhttp3.Response;

/**
 * 基础请求拦截器
 *
 * @author xuexiang
 * @since 2018/6/19 下午11:41
 */
public abstract class BaseRequestInterceptor extends BaseInterceptor {

    @Override
    protected Response onAfterRequest(Response response, Chain chain, String bodyString) {
        return null;
    }
}
