package com.ktx.lib.request;

/**
 * 网络引擎回调
 * author: 星辰CentOS
 * on 2019/12/09
 */

public interface HttpCallback {

    public void onSuccess(String result, String type);

    public void onError(Exception e, String type);

}
