package org.mjulikelion.baker.dto.request.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ApplicationRequestDto {
    @NotBlank(message = "지원서 ID가 누락되었습니다")
    private String applicationId;
}
