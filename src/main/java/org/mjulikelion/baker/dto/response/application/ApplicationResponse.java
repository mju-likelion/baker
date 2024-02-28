package org.mjulikelion.baker.dto.response.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Builder;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.model.Part;

@Builder
public class ApplicationResponse {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String studentId;
    @JsonProperty
    private String phoneNumber;
    @JsonProperty
    private Part part;
    @JsonProperty
    private String major;
    @JsonProperty
    private Date createdAt;
    @JsonProperty
    private String link;
    @JsonProperty
    private boolean isPass;


    public static ApplicationResponse makeApplicationResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId().toString())
                .name(application.getName())
                .studentId(application.getStudentId())
                .phoneNumber(application.getPhoneNumber())
                .part(application.getPart())
                .major(application.getMajor().getName())
                .createdAt(application.getCreatedAt())
                .link(application.getLink())
                .isPass(application.isPass())
                .build();
    }
}
