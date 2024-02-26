package org.mjulikelion.baker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class IntroduceDetailVO {
    @JsonProperty
    private final int sequence;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String content;
}
