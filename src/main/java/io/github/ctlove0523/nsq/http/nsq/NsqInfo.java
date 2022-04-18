package io.github.ctlove0523.nsq.http.nsq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NsqInfo {
    private String version;
    private String broadcast_address;
    private String hostname;
    private int http_port;
    private int tcp_port;
    private long start_time;
}
