package com.ktx.lib.request;

import android.content.Context;

import com.ktx.lib.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络引擎工具类
 * author: 星辰CentOS
 * on 2019/12/09
 */

public class OkHttpUtils {

    private static HttpEngine mHttpEngine = new OkHttpEngine();

    private static final int GET_TYPE = 1;
    private static final int POST_TYPE = 2;
    private static final int PUT_TYPE = 3;
    private static final int DELETE_TYPE = 4;
    private static final int DOWNLOAD_TYPE = 5;
    private static final int UPDATE_TYPE = 6;

    //默认post请求方式
    private int mType = POST_TYPE;

    private String mUrl;
    private String sType;
    private boolean isCache = false;
    private Map<String, Object> mParams;
    private static Map<String, Object> mCommonParams;

    private Context mContext;

    private String mBaseUrl = Constant.BASE_HTTP;

    public OkHttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public static OkHttpUtils with(Context context) {
        return new OkHttpUtils(context);
    }

    public OkHttpUtils base(String base) {
        mBaseUrl = base;
        return this;
    }

    public OkHttpUtils url(String url) {
        mUrl = mBaseUrl + url;
        return this;
    }

    public OkHttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public OkHttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public OkHttpUtils put() {
        mType = PUT_TYPE;
        return this;
    }

    public OkHttpUtils delete() {
        mType = DELETE_TYPE;
        return this;
    }

    public OkHttpUtils downLoad() {
        mType = DOWNLOAD_TYPE;
        return this;
    }

    public OkHttpUtils update() {
        mType = UPDATE_TYPE;
        return this;
    }

    public OkHttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public OkHttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    public OkHttpUtils type(String type) {
        sType = type;
        return this;
    }

    public OkHttpUtils isCache(boolean cache) {
        isCache = cache;
        return this;
    }

    public void initParams(Map<String, Object> commonParams) {
        if (commonParams != null && !commonParams.isEmpty()) mCommonParams.putAll(commonParams);
    }

    public void call(HttpCallback callback) {

        if (mCommonParams != null && !mCommonParams.isEmpty()) {
            mParams.putAll(mCommonParams);
        }

        switch (mType) {
            case GET_TYPE:
                get(mUrl, mParams, sType, isCache, callback);
                break;
            case POST_TYPE:
                post(mUrl, mParams, sType, isCache, callback);
                break;
            case PUT_TYPE:
                put(mUrl, mParams, sType, isCache, callback);
                break;
            case DELETE_TYPE:
                delete(mUrl, mParams, sType, isCache, callback);
                break;
            case DOWNLOAD_TYPE:
                downLoad(mUrl, mParams, sType, isCache, callback);
                break;
            case UPDATE_TYPE:
                update(mUrl, mParams, sType, isCache, callback);
                break;
        }
    }

    //可以在Application中自定义网络引擎
    public static void init(HttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    //可以在Application中自定义网络引擎
    public static void init(HttpEngine httpEngine, Map<String, Object> commonParams) {
        mHttpEngine = httpEngine;
        mCommonParams = new HashMap<>();
        mCommonParams.putAll(commonParams);
    }

    //每次请求可以携带自定义网络引擎
    public void inject(HttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    private void get(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.get(url, params, type, isCache, callback);
    }

    private void post(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.post(url, params, type, isCache, callback);
    }

    private void put(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.put(url, params, type, isCache, callback);
    }

    private void delete(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.delete(url, params, type, isCache, callback);
    }

    private void downLoad(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.downLoad(url, params, type, isCache, callback);
    }

    private void update(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        mHttpEngine.update(url, params, type, isCache, callback);
    }


    protected static String appendUrl(String url, Map<String, Object> params) {
        StringBuilder urlParams = new StringBuilder("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return url + urlParams.substring(0, urlParams.toString().length() - 1);
    }
}
