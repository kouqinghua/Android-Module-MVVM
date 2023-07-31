package com.ktx.lib.request;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.Proxy;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络引擎底层封装
 * author: 星辰CentOS
 * on 2019/12/09
 */

public class OkHttpEngine implements HttpEngine {

    private static OkHttpClient mOkHttpClient;
    private final Handler mHandler;

    public OkHttpEngine() {
        mOkHttpClient = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void get(String url, Map<String, Object> params, final String type, boolean isCache, final HttpCallback callback) {
        final String mUrl = OkHttpUtils.appendUrl(url, params);
        Log.e("REQUEST_GET: ", mUrl);

        hasCache(mUrl, type, isCache, callback);

        final Request request = new Request.Builder()
                .url(mUrl)
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(callback(mUrl, isCache, type, callback));
    }

    @Override
    public void post(String url, Map<String, Object> params, final String type, boolean isCache, final HttpCallback callback) {
        final String mUrl = OkHttpUtils.appendUrl(url, params);
        Log.e("REQUEST_POST: ", mUrl);

        hasCache(mUrl, type, isCache, callback);

        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Callback back = callback(mUrl, isCache, type, callback);
        call.enqueue(back);
//        mOkHttpClient.newCall(request).enqueue(callback(mUrl, isCache, type, callback));
    }

    @Override
    public void put(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        final String mUrl = OkHttpUtils.appendUrl(url, params);
        Log.e("REQUEST_PUT: ", mUrl);

        hasCache(mUrl, type, isCache, callback);

        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Callback back = callback(mUrl, isCache, type, callback);
        call.enqueue(back);
    }

    //判断是否有缓存 并返回缓存数据
    private void hasCache(String mUrl, String type, boolean isCache, HttpCallback callback) {
//        if (isCache) {
//            String caches = XCaches.getCacheResultJson(mUrl);
//            if (!TextUtils.isEmpty(caches)) {
//                callback.onSuccess(caches, type);
//            }
//        }
    }

    @Override
    public void delete(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {
        final String mUrl = OkHttpUtils.appendUrl(url, params);
        Log.e("REQUEST_DELETE: ", mUrl);
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback(mUrl, isCache, type, callback));
    }

    @Override
    public void downLoad(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {

    }

    @Override
    public void update(String url, Map<String, Object> params, String type, boolean isCache, HttpCallback callback) {

    }

    private RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("", "");
        addParams(builder, params);
        return builder.build();
    }

    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof File) {
                    File file = (File) value;
                    String s = guessFileType(file.getAbsolutePath());
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessFileType(s)), file));
                } else if (value instanceof List) {
                    try {
                        List<File> files = (List<File>) value;
                        for (int j = 0; j < files.size(); j++) {
                            File file = files.get(j);
                            builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessFileType(file.getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
        }
    }

    //猜测文件类型
    private String guessFileType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (TextUtils.isEmpty(contentTypeFor)) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private Callback callback(final String url, final boolean isCache, final String type, final HttpCallback callback) {
        return new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e, type);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final String resultJson;
                if (result.startsWith("\uFEFF")) {
                    resultJson = result.replace("\uFEFF", "");
                } else {
                    resultJson = result;
                }
//                if (isCache) {
//                    String cache = XCaches.getCacheResultJson(url);
//                    if (!TextUtils.isEmpty(cache)) {
//                        if (cache.equals(resultJson)) {
//                            return;
//                        }
//                    }
//                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(resultJson, type);

//                        if (isCache) {
//                            XCaches.cacheResultJson(url, resultJson);
//                        }
                    }
                });
            }
        };
    }
}
