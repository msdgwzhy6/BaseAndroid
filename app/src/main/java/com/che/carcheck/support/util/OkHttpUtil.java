package com.che.carcheck.support.util;

import android.os.Environment;

import com.che.carcheck.support.config.BaseApplication;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yutianran on 16/2/24.
 */
public class OkHttpUtil {

    private OkHttpClient okHttpClient;

    public static int CACHE_SIZE = 200 * 1024 * 1024;
    public static File ROOT_CACHE_DIR; //根缓存路径
    public static File IMAGE_CACHE_DIR; //图片缓存路径
    public static File HTTP_CACHE_DIR;//网络缓存路径
    public static File LOG_CACHE_DIR;//Log日志缓存路径
    public static File CRASH_CACHE_DIR;//Crash日志缓存路径

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ROOT_CACHE_DIR = new File(Environment.getExternalStorageDirectory(), "carMall");
        } else {
            ROOT_CACHE_DIR = new File(BaseApplication.getContext().getCacheDir(), "carMall");
        }
        IMAGE_CACHE_DIR = new File(ROOT_CACHE_DIR, "ImageCache");
        HTTP_CACHE_DIR = new File(ROOT_CACHE_DIR, "HttpCache");
        LOG_CACHE_DIR = new File(ROOT_CACHE_DIR, "LogCache");
        CRASH_CACHE_DIR = new File(ROOT_CACHE_DIR, "CrashCache");

        if (!IMAGE_CACHE_DIR.exists()) {
            IMAGE_CACHE_DIR.mkdirs();
        }
        if (!HTTP_CACHE_DIR.exists()) {
            HTTP_CACHE_DIR.mkdirs();
        }
        if (!LOG_CACHE_DIR.exists()) {
            LOG_CACHE_DIR.mkdirs();
        }
        if (!CRASH_CACHE_DIR.exists()) {
            CRASH_CACHE_DIR.mkdirs();
        }
    }

    /*网络回调*/
    public interface HttpCallBack {
        void onSuccss(String json);

        void onFailure(String error);
    }

    //静态内部类的单例模式
    public static OkHttpUtil getInstance() {
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        private static final OkHttpUtil mInstance = new OkHttpUtil();
    }

    /*OkHttp的基本配置*/
    private OkHttpUtil() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        okHttpClient.setCache(new Cache(HTTP_CACHE_DIR, CACHE_SIZE));
        okHttpClient.setRetryOnConnectionFailure(true);
        //……
    }

    /*添加网络请求,无参数*/
    public void addRequest(String url, int tag, final HttpCallBack callBack) {
        try {
            LogUtil.print("添加网络请求：url=" + url);
            final Request request = new Request.Builder().url(url).tag(tag).build();
            doRequest(request, callBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*添加网络请求，有参数*/
    public void addRequest(String url, int tag, Map<String, String> params, final HttpCallBack callBack) {
        try {
            LogUtil.print("添加网络请求：url=" + url);
            final Request request = new Request.Builder().url(url).tag(tag).post(buildParams(params)).build();
            doRequest(request, callBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*具体执行网络请求的方法*/
    private void doRequest(Request request, final HttpCallBack callBack) {
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                final String responseJson = response.body().string();
                LogUtil.print("网络请求正确：" + responseJson);
                BaseApplication.getMainHandler().post(new Runnable() {
                    public void run() {
                        callBack.onSuccss(responseJson);
                    }
                });
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                ToastUtil.showToastSafe("网络异常，请检查网络");
                final String res = arg1.getMessage();
                LogUtil.print("网络请求错误：" + res);
                BaseApplication.getMainHandler().post(new Runnable() {
                    public void run() {
                        callBack.onFailure(res);
                    }
                });

            }
        });
    }


    /*移除网络请求*/
    public void removeRequest(int tag) {
        okHttpClient.cancel(tag);
    }

    /*包装网络请求参数*/
    private RequestBody buildParams(Map<String, String> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        LogUtil.print("参数：params=" + params);
        return requestBody;
    }


    /*同步的网络请求*/
    public String syncRequest(int tag, String url) {
        try {
            LogUtil.print("添加网络请求：url=" + url);
            final Request request = new Request.Builder().url(url).tag(tag).build();
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                LogUtil.print(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /*同步的网络请求*/
    public String syncRequest(int tag, String url, Map<String, String> params) {
        try {
            LogUtil.print("添加网络请求：url=" + url);
            final Request request = new Request.Builder().url(url).tag(tag).post(buildParams(params)).build();
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                LogUtil.print(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
