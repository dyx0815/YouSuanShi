package com.dan.yousuanshi.http;

import android.util.Log;

import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class PublicHeadersInterceptor implements Interceptor {
    private static String TOKEN = "";
    public static void updateToken(String token) {
        PublicHeadersInterceptor.TOKEN = token;
    }
    public static String getToken() {
        return TOKEN;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .header(Constant.PUBLIC_REQUEST_HEADER, TOKEN)
                .build();
        return chain.proceed(request);
    }
}
