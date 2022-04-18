package io.github.ctlove0523.nsq.http.nsq;

import io.github.ctlove0523.nsq.http.NsqClient;
import io.github.ctlove0523.nsq.http.client.HttpClient;
import io.github.ctlove0523.nsq.http.client.HttpResponse;
import io.github.ctlove0523.nsq.http.lookup.NsqVersion;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HttpNsqClient implements NsqClient {
    private HttpClient httpClient;
    private List<String> endpoints;

    public HttpNsqClient() {
        this.httpClient = new HttpClient();
        this.endpoints = Collections.singletonList("http://localhost:4151");
    }

    @Override
    public boolean ping() {
        String url = getEndpoint() + "/ping";
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        HttpResponse<String> response = httpClient.execute(request, String.class);
        return response.successResponse() && response.getResult().equals("OK");
    }

    @Override
    public NsqInfo showInfo() {
        String url = getEndpoint() + "/info";
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        HttpResponse<NsqInfo> response = httpClient.execute(request, NsqInfo.class);
        if (response.successResponse()) {
            return response.getResult();
        }

        return null;
    }

    @Override
    public boolean pub(String topic, byte[] message) {
        String url = getEndpoint() + "/pub?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(message))
                .build();
        HttpResponse<String> response = httpClient.execute(request, String.class);
        return response.successResponse() && response.getResult().equals("OK");
    }

    @Override
    public boolean pub(String topic, long defer, byte[] message) {
        String url = getEndpoint() + "/pub?topic=" + topic + "&defer=" + defer;
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(message))
                .build();
        HttpResponse<String> response = httpClient.execute(request, String.class);
        return response.successResponse() && response.getResult().equals("OK");
    }

    @Override
    public boolean multiplePub(String topic, List<String> messages) {
        String url = getEndpoint() + "/mpub?topic=" + topic;


        Request request = new Builder().url(url)
                .post(RequestBody.create(String.join("\n",messages).getBytes(StandardCharsets.UTF_8)))
                .build();
        HttpResponse<String> response = httpClient.execute(request, String.class);
        return response.successResponse() && response.getResult().equals("OK");
    }

    @Override
    public boolean createTopic(String topic) {
        String url = getEndpoint() + "/topic/create?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean deleteTopic(String topic) {
        String url = getEndpoint() + "/topic/delete?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean emptyTopic(String topic) {
        String url = getEndpoint() + "/topic/empty?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean pauseTopic(String topic) {
        String url = getEndpoint() + "/topic/pause?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean unpauseTopic(String topic) {
        String url = getEndpoint() + "/topic/unpause?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean createChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/create?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean deleteChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/create?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean emptyChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/empty?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean pauseChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/pause?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean unpauseChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/unpause?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    private String getEndpoint() {
        return endpoints.get(Math.abs(new Random().nextInt() % endpoints.size()));
    }
}
