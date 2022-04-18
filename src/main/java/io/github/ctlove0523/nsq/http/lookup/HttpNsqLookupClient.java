package io.github.ctlove0523.nsq.http.lookup;

import io.github.ctlove0523.nsq.http.NsqLookupClient;
import io.github.ctlove0523.nsq.http.NsqLookupClientBuilder;
import io.github.ctlove0523.nsq.http.client.HttpClient;
import io.github.ctlove0523.nsq.http.client.HttpResponse;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class HttpNsqLookupClient implements NsqLookupClient {
    private static final Logger log = LoggerFactory.getLogger(HttpNsqLookupClient.class);
    private final HttpClient httpClient;
    private final List<String> lookupEndpoints;

    public HttpNsqLookupClient(NsqLookupClientBuilder builder) {
        this.httpClient = new HttpClient();
        this.lookupEndpoints = builder.getEndpoints();
    }

    @Override
    public String getVersion() {
        String url = getEndpoint() + "/info";
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        HttpResponse<NsqVersion> httpResponse = httpClient.execute(request, NsqVersion.class);
        if (httpResponse.isExecuteSuccess() && httpResponse.getHttpStatus() == 200) {
            log.info("query nsq lookup info success");
            return httpResponse.getResult().getVersion();
        }

        return null;
    }

    @Override
    public boolean ping() {
        String url = getEndpoint() + "/ping";
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        HttpResponse<String> httpResponse = httpClient.execute(request, String.class);
        return httpResponse.isExecuteSuccess() && httpResponse.getResult().equals("OK");
    }

    @Override
    public ProducerInfo getProducer(String topic) {
        String url = getEndpoint() + "/lookup?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        HttpResponse<ProducerInfo> httpResponse = httpClient.execute(request, ProducerInfo.class);
        if (httpResponse.successResponse()) {
            return httpResponse.getResult();
        }

        return new ProducerInfo();
    }

    @Override
    public Topics getTopics() {
        String url = getEndpoint() + "/topics";
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        HttpResponse<Topics> httpResponse = httpClient.execute(request, Topics.class);
        if (httpResponse.successResponse()) {
            log.info("list nsq lookup topic sucess");
            return httpResponse.getResult();
        }

        return new Topics();
    }

    @Override
    public Channels getChannels(String topic) {
        String url = getEndpoint() + "/channels?topic=" + topic;
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        HttpResponse<Channels> httpResponse = httpClient.execute(request, Channels.class);
        if (httpResponse.successResponse()) {
            log.info("list nsq lookup topic sucess");
            return httpResponse.getResult();
        }

        return new Channels();
    }

    @Override
    public Nodes listNodes() {
        String url = getEndpoint() + "/nodes";
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        HttpResponse<Nodes> httpResponse = httpClient.execute(request, Nodes.class);
        if (httpResponse.successResponse()) {
            log.info("list nsq lookup topic sucess");
            return httpResponse.getResult();
        }

        return new Nodes();
    }

    @Override
    public boolean addTopic(String topic) {
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
    public boolean addChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/create?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    @Override
    public boolean deleteChannel(String topic, String channel) {
        String url = getEndpoint() + String.format("/channel/delete?topic=%s&channel=%s", topic, channel);
        Request request = new Request.Builder().url(url)
                .post(HttpClient.emptyRequestBody())
                .build();

        HttpResponse<String> response = httpClient.execute(request, String.class);

        return response.successResponse();
    }

    private String getEndpoint() {
        return lookupEndpoints.get(Math.abs(new Random().nextInt() % lookupEndpoints.size()));
    }
}
