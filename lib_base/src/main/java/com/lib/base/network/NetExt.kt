package com.lib.base.network

import android.net.ParseException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.lib.base.mvvm.vm.BaseViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 数据解析扩展函数
 * @CreateDate: 2023/08/02 17:35
 */

/**
 * ViewModel中扩展函数，封装协程请求网络
 */
fun BaseViewModel.initiateRequest(
    loadState: MutableLiveData<UIState>,
    block: suspend () -> Unit,
    key: String? = null
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            handleException(it, loadState, key)
        }
    }
}

fun BaseViewModel.initiateRequest(
    loadState: MutableLiveData<UIState>,
    block: suspend () -> Unit
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            handleException(it, loadState)
        }
    }
}

/**
 * 获取到请求的结果后二次封装解析，转换，key用于区分不同请求，建议唯一
 */
fun <T> BaseResponse<T>.dataConvert(loadState: MutableLiveData<UIState>, key: String? = null): T {
    return when (errorCode) {
        0 -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.value = UIState(UIStateType.EMPTY, code = errorCode, msg = errorMsg, key)
                } else {
                    loadState.value = UIState(UIStateType.SUCCESS, code = errorCode, msg = errorMsg, key)
                }
            } else {
                loadState.value = UIState(UIStateType.SUCCESS, code = errorCode, msg = errorMsg, key)
            }
            data
        }
        else -> {
            loadState.postValue(UIState(UIStateType.ERROR, code = errorCode, msg = errorMsg, key))
            data
        }
    }
}

/**
 * 拦截处理网络层的异常错误
 */
private fun handleException(
    e: Throwable?,
    loadState: MutableLiveData<UIState>,
    key: String? = null
) {
    e?.let {
        when (it) {
            is HttpException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = it.message(), key)
            }
            is ConnectException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = "网络连接异常，请检查网络", key)
            }
            is TimeoutException, is SocketTimeoutException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = "网络连接超时，请稍后重试", key)
            }
            is UnknownHostException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = "网络异常，请检查网络", key)
            }
            is JSONException, is ParseException, is JsonParseException, is MalformedJsonException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = "数据解析失败", key)
            }
            is IOException -> {
                loadState.value =
                    UIState(UIStateType.NETWORK_ERROR, code = 500, msg = "网络连接异常!", key)
            }
            else -> {
                loadState.value = UIState(
                    UIStateType.NETWORK_ERROR,
                    code = 500,
                    msg = (it.message.toString()),
                    key
                )
            }
        }
    }
}