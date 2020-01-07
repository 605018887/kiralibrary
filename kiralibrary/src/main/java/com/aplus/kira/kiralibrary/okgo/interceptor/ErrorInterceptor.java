package com.aplus.kira.kiralibrary.okgo.interceptor;


/**
 * 异常拦截器，用在拦截token异常，要重新登录之类的
 */
public interface ErrorInterceptor {

    /**
     * 拦截异常，
     *
     * @param tr 异常
     * @return 如果返回true，则表示异常被拦截住，不会走{@link MyCallback#onFailure(String, * Throwable)} 或者 {@link OkObserver#onFailure(String, Throwable)}
     */
    boolean interceptException(Throwable tr);
}
