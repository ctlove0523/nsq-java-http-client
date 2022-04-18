package io.github.ctlove0523.nsq.http;

import io.github.ctlove0523.nsq.http.nsq.NsqInfo;

import java.util.List;

public interface NsqClient {
    boolean ping();

    NsqInfo showInfo();

    boolean pub(String topic, byte[] message);

    boolean pub(String topic, long defer, byte[] message);

    boolean multiplePub(String topic, List<String> messages);

    boolean createTopic(String topic);

    boolean deleteTopic(String topic);

    boolean emptyTopic(String topic);

    boolean pauseTopic(String topic);

    boolean unpauseTopic(String topic);

    boolean createChannel(String topic, String channel);

    boolean deleteChannel(String topic, String channel);

    boolean emptyChannel(String topic, String channel);

    boolean pauseChannel(String topic, String channel);

    boolean unpauseChannel(String topic, String channel);
}
