package io.github.ctlove0523.nsq.http.client;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HttpClient {
    private OkHttpClient client;

    public HttpClient() {
        this.client = new OkHttpClient.Builder()
                .build();
    }

    public <T> HttpResponse<T> execute(Request request,Class<T> typeOfT) {
        Objects.requireNonNull(request, "request");

        HttpResponse<T> httpResponse = new HttpResponse<>();
        try (Response response = client.newCall(request).execute();
             ResponseBody responseBody = response.body()) {
            int code = response.code();
            httpResponse.setHttpStatus(code);
            httpResponse.setExecuteSuccess(true);

            if (code >= 200 && code < 300 && responseBody != null) {
                String bodyString = responseBody.string();
                T result = new Gson().fromJson(bodyString, typeOfT);
                httpResponse.setResult(result);
            }
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpResponse.setExecuteSuccess(false);
        return httpResponse;
    }

    public <T> CompletableFuture<HttpResponse<T>> asyncExecute(Request request, Class<T> typeOfT) {
        Objects.requireNonNull(request, "request");
        CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                HttpResponse<T> httpResponse = new HttpResponse<>();
                httpResponse.setExecuteSuccess(false);
                future.complete(httpResponse);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                HttpResponse<T> httpResponse = new HttpResponse<>();
                httpResponse.setExecuteSuccess(true);
                httpResponse.setHttpStatus(response.code());

                try (ResponseBody responseBody = response.body()) {
                    if (response.code() >= 200 && response.code() < 300 && responseBody != null) {
                        String bodyString = responseBody.string();
                        T result = new Gson().fromJson(bodyString, typeOfT);
                        httpResponse.setResult(result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    httpResponse.setExecuteSuccess(false);
                }

                future.complete(httpResponse);
            }
        });

        return future;
    }

    public static RequestBody emptyRequestBody() {
        return RequestBody.create(new byte[0]);
    }
}
