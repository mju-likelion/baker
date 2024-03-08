package org.mjulikelion.baker.dto.response.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedList;
import java.util.List;

public class EmailSendResponseData {
    @JsonProperty
    List<String> sentApplicationIds;
    @JsonProperty
    List<String> failedApplicationIds;

    public EmailSendResponseData() {
        this.sentApplicationIds = new LinkedList<>();
        this.failedApplicationIds = new LinkedList<>();
    }

    public void addSentApplicationId(String applicationId) {
        this.sentApplicationIds.add(applicationId);
    }

    public void addFailedApplicationId(String applicationId) {
        this.failedApplicationIds.add(applicationId);
    }
}
