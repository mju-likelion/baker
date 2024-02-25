package org.mjulikelion.baker.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.request.auth.AuthLoginRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.service.auth.AuthQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private final AuthQueryService authQueryService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDTO,
                                                   HttpServletResponse response) {
        return this.authQueryService.login(authLoginRequestDTO, response);
    }
}
