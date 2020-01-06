package com.dan.yousuanshi.http;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.request.RxRequest;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class BaseRequest {

    protected static <T> Disposable request(@NonNull Observable<ResponseBean<T>> observable, @NonNull final RequestListener<T> listener) {
        return RxRequest.create(observable)
                .listener(new RxRequest.RequestListener() {
                    @Override
                    public void onStart() {
                        listener.onStart();
                    }

                    @Override
                    public void onError(ExceptionHandle handle) {
                        listener.onError(handle);
                        listener.onFailed(YouSuanShiApi.Code.ERROR, handle.getMsg());
                    }

                    @Override
                    public void onFinish() {
                        listener.onFinish();
                    }
                })
                .request(new RxRequest.ResultCallback<T>() {
                    @Override
                    public void onSuccess(int code, T data) {
                        listener.onSuccess(code, data);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
//                        if (code == YouSuanShiApi.Code.FAILED_NOT_LOGIN) {
//                            UserUtils.getInstance().logout();
//                        }
                        listener.onFailed(code, msg);
                    }
                });
    }
}
