package org.mjulikelion.baker.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLoginRequestDto {
    @NotBlank
    private String managerId;
    @NotBlank
    private String password;
}
