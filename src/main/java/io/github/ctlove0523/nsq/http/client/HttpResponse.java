package io.github.ctlove0523.nsq.http.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponse<T> {
    private boolean executeSuccess;

    private int httpStatus;

    private T result;

    public boolean successResponse() {
        return executeSuccess && httpStatus >= 200 && httpStatus < 300;
    }
}
