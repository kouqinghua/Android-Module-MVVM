package com.ktx.lib.request;

import java.util.Map;

/**
 * 网络请求引擎规范
 * author: 星辰CentOS
 * on 2019/12/09
 */

public interface HttpEngine {

    void get(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);

    void post(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);

    void put(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);

    void delete(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);

    void downLoad(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);

    void update(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback);
}
