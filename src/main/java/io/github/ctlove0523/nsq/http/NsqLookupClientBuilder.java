package io.github.ctlove0523.nsq.http;

import io.github.ctlove0523.nsq.http.lookup.HttpNsqLookupClient;
import lombok.Getter;

import java.util.List;

@Getter
public class NsqLookupClientBuilder {
    private List<String> endpoints;

    public NsqLookupClientBuilder withEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public NsqLookupClient build() {
        return new HttpNsqLookupClient(this);
    }
}
