package com.people.loveme.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.people.loveme.R;
import com.people.loveme.bean.BaseBean;
import com.people.loveme.utils.NetUtil;
import com.people.loveme.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装的 okhttp
 */
public class OkHttpHelper {

    public static final String TAG = "OkHttpHelper";

    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Context context;
    private Handler mHandler;
    private Map<String, Map<String, String>> mMap;


    enum HttpMethodType {
        GET,
        POST,
    }

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
        mMap = new HashMap<>();

    }

    /**
     * 获取 OkHttp 实例
     *
     * @return
     */
    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    /**
     * get 请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        request(request, callback);
    }
    public void get(String url, Map<String,String> params ,BaseCallback callback) {
        Request request = new Request.Builder()
                .get()
                .url(getUrl(params,url))
                .build();
        request(request, callback);
    }

    private String getUrl(Map<String, String> params,String url) {
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }


    /**
     * post 请求方法
     *
     * @param url
     * @param param
     * @param callback
     */
    public void post(Context context, String url, Map<String, String> param, BaseCallback callback) {
        this.context = context;
        Log.e("json",new Gson().toJson(param));
        Request request = buildPostRequest(url, param);
        request(request, callback);
    }

    /**
     * post请求 将请求参数封装为json
     *
     * @param context
     * @param url
     * @param params  json 参数Map
     * @param callback
     */
    Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();

    public void post_json(Context context, String url, Map<String, Object> params, BaseCallback callback) {
        String gson = gson2.toJson(params);
        Log.e("http--json", gson);
        Map<String, String> param = new HashMap<>();
        param.put("json", gson);
        this.context = context;
        Request request = buildPostRequest(url, param);
        request(request, callback);
    }


    /**
     * 除文件外其他参数josn形式传递
     *
     * @param context
     * @param url
     * @param params
     * @param fileParams
     * @param callback
     */
    public void post_json_file(Context context, String url, Map<String, Object> params, Map<String, List<File>> fileParams, BaseCallback callback) {
        this.context = context;
        Request request = buildRequestFiles(url, params, fileParams);
        request(request, callback);
    }

    /**
     * 文件上传 参数以 key value 形式上传
     *
     * @param context
     * @param url
     * @param params
     * @param fileParams
     * @param callback
     */
    public void post_map_file(Context context, String url, Map<String, Object> params, Map<String, List<File>> fileParams, BaseCallback callback) {
        this.context = context;
        Request request = buildRequestMapFiles(url, params, fileParams);
        request(request, callback);
    }


    /**
     * POST 请求 附加 header
     *
     * @param url
     * @param param
     * @param callback
     * @param context
     */
    public void post_header(String url, Map<String, String> param, BaseCallback callback, final Context context) {
        this.context = context;
        this.mMap.put(url, param);
        if (!NetUtil.isNetWork(context)) {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = buildRequest_header(url, param);
        request(request, callback);
    }

    /**
     * POST 请求 附加 header,带文件
     *
     * @param url
     * @param param
     * @param callback
     * @param context
     */
    public void post_header_file(String url, Map<String, String> param, BaseCallback callback, final Context context, File file, String key) {
        this.context = context;
        this.mMap.put("url_map", param);
        if (!NetUtil.isNetWork(context)) {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = buildRequest_header_file(url, param, file, key);
        request(request, callback);
    }

    public void post_header_files(String url, Map<String, String> param, BaseCallback callback, final Context context, List<File> files, String key) {
        this.context = context;
        this.mMap.put("url_map", param);
        if (!NetUtil.isNetWork(context)) {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = buildRequest_header_files(url, param, files, key);
        request(request, callback);
    }

    /**
     * 执行 网络请求 get\post\post_header
     *
     * @param request
     * @param callback
     */
    private void request(final Request request, final BaseCallback callback) {
        callback.onBeforeRequest(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackFailure(callback, request, e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                callbackResponse(callback, response);
                callback.onResponse(response);

                if (response.isSuccessful()) {
                    Log.e("http--url", request.url().toString());
                    String resultStr = response.body().string();
                    Log.e("http--http", resultStr);
                    if (!request.url().toString().equals("https://v.juhe.cn/sms/send?")) {
                        try {
                            final BaseBean base = mGson.fromJson(resultStr, BaseBean.class);
//                            if (base.getCode() != 1) {
//                                mHandler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
////                                            ToastUtil.show(base.getMsg());
//                                            callbackError(callback, response, new Exception());
//                                        } catch (Exception e) {
//                                        }
//                                    }
//                                });
//                                return;
//                            }
                        } catch (com.google.gson.JsonParseException e) {
                            callbackError(callback, response, e);
                        }
                    }

                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, obj);
                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                            e.printStackTrace();
                            callbackError(callback, response, e);
                        }
                    }
                } else {
                    callbackError(callback, response, null);
                }
            }
        });
    }

    /**
     * 构建请求参数
     *
     * @param url
     * @param methodType
     * @param params
     * @return
     */
    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }
        return builder.build();
    }

    private Request buildRequestFiles(String url, Map<String, Object> params, Map<String, List<File>> fileParams) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBody body = builderFormDataFiles(params, fileParams);
        builder.post(body);
        return builder.build();
    }

    private Request buildRequestMapFiles(String url, Map<String, Object> params, Map<String, List<File>> fileParams) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBody body = builderFormDataMapFiles(params, fileParams);
        builder.post(body);
        return builder.build();
    }


    /**
     * 构建请求参数 附加请求头
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildRequest_header(String url, Map<String, String> params) {
        /**
         * 添加的header参数（根据需求改变）
         */
        String apiKey = "";
        String currentBabyId = "";
        String clubid = "";
        Request.Builder builder;
        builder = new Request.Builder().url(url)
                .addHeader("X-Apikey", apiKey)
                .addHeader("X-Baby", currentBabyId)
                .addHeader("X-CLUB", clubid);
        RequestBody body = builderFormData(params);
        builder.post(body);
        return builder.build();
    }

    /**
     * 构建请求参数 附加请求头，带文件
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildRequest_header_file(String url, Map<String, String> params, File file, String key) {
        String apiKey = "";
        String currentBabyId = "";
        String clubid = "";
        Request.Builder builder;
        builder = new Request.Builder().url(url)
                .addHeader("X-Apikey", apiKey)
                .addHeader("X-Baby", currentBabyId)
                .addHeader("X-CLUB", clubid);
        RequestBody body = builderFormData_file(params, file, key);
        builder.post(body);
        return builder.build();
    }

    private Request buildRequest_header_files(String url, Map<String, String> params, List<File> files, String key) {
        String apiKey = "";
        String currentBabyId = "";
        String clubid = "";
        Request.Builder builder;
        builder = new Request.Builder().url(url)
                .addHeader("X-Apikey", apiKey)
                .addHeader("X-Baby", currentBabyId)
                .addHeader("X-CLUB", clubid);
        RequestBody body = builderFormData_files(params, files, key);
        builder.post(body);
        return builder.build();
    }

    /**
     * 组装表单数据
     *
     * @param params
     * @return
     */
    private RequestBody builderFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 组装表单数据带文件
     *
     * @param params
     * @return
     */
    private MultipartBody builderFormData_file(Map<String, String> params, File file, String key) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart(key, filename, body);
        }

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return requestBody.build();
    }

    /**
     * 多文件上传
     *
     * @param params
     * @param files
     * @param key
     * @return
     */
    private MultipartBody builderFormData_files(Map<String, String> params, List<File> files, String key) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null) {
            // MediaType.parse() 里面是上传的文件类型。
            for (int i = 0; i < files.size(); i++) {
                RequestBody body = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                String filename = files.get(i).getName();
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                requestBody.addFormDataPart(key + "[]", filename, body);
            }
        }
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return requestBody.build();
    }


    /**
     * 多文件上传
     *
     * @param params
     * @param fileParams
     * @return
     */
    private MultipartBody builderFormDataFiles(Map<String, Object> params, Map<String, List<File>> fileParams) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileParams != null) {
            for (Map.Entry<String, List<File>> entry : fileParams.entrySet()) {
                List<File> files = entry.getValue();
                // MediaType.parse() 里面是上传的文件类型。
                for (int i = 0; i < files.size(); i++) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                    String filename = files.get(i).getName();
                    // 参数分别为， 请求key ，文件名称 ， RequestBody
                    requestBody.addFormDataPart(entry.getKey(), filename, body);
                }
            }
        }
        if (params != null) {
            Log.e("http--json", new Gson().toJson(params));
            requestBody.addFormDataPart("json", new Gson().toJson(params));
        }
        return requestBody.build();
    }

    private MultipartBody builderFormDataMapFiles(Map<String, Object> params, Map<String, List<File>> fileParams) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileParams != null) {
            for (Map.Entry<String, List<File>> entry : fileParams.entrySet()) {
                List<File> files = entry.getValue();
                // MediaType.parse() 里面是上传的文件类型。
                for (int i = 0; i < files.size(); i++) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                    String filename = files.get(i).getName();
                    // 参数分别为， 请求key ，文件名称 ， RequestBody
                    requestBody.addFormDataPart(entry.getKey(), filename, body);
                }
            }
        }
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(), (String) entry.getValue());
            }
        }
        return requestBody.build();
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(response, obj);
                } catch (Exception e) {
                }
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }

    private void callbackFailure(final BaseCallback callback, final Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
                ToastUtil.show(context.getString(R.string.httperror));
            }
        });
    }

    private void callbackResponse(final BaseCallback callback, final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    private Request buildGetRequest(String url) {
        return buildRequest(url, HttpMethodType.GET, null);
    }

    private Request buildPostRequest(String url, Map<String, String> params) {
        return buildRequest(url, HttpMethodType.POST, params);
    }

    public PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

}
