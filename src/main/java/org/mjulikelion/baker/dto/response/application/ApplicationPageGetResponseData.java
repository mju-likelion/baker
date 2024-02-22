package org.mjulikelion.baker.dto.response.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;


@Builder
public class ApplicationPageGetResponseData {
    @JsonProperty
    private List<ApplicationResponse> applications;
    @JsonProperty
    private int totalPage;
    @JsonProperty
    private int currentPage;
}
