package org.mjulikelion.baker.service.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.mjulikelion.baker.dto.request.auth.AuthLoginRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthQueryService {
    ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDTO,
                                            HttpServletResponse response);
}
