package io.github.ctlove0523.nsq.http.lookup;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Topics {
    private List<String> topics = new ArrayList<>();
}
