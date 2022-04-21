package io.github.ctlove0523.nsq.http;

import io.github.ctlove0523.nsq.http.lookup.Channels;
import io.github.ctlove0523.nsq.http.lookup.Nodes;
import io.github.ctlove0523.nsq.http.lookup.ProducerInfo;
import io.github.ctlove0523.nsq.http.lookup.Topics;

public interface NsqLookupClient {

    String getVersion();

    boolean ping();

    ProducerInfo getProducer(String topic);

    Topics getTopics();

    Channels getChannels(String topic);

    Nodes listNodes();

    boolean addTopic(String topic);

    boolean deleteTopic(String topic);

    boolean addChannel(String topic, String channel);

    boolean deleteChannel(String topic, String channel);
}
