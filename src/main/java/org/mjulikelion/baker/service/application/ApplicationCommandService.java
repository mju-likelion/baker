package org.mjulikelion.baker.service.application;

import org.mjulikelion.baker.dto.request.application.ApplicationApproveRequestDto;
import org.mjulikelion.baker.dto.request.application.ApplicationRejectRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface ApplicationCommandService {
    ResponseEntity<ResponseDto<Void>> approveApplication(
            ApplicationApproveRequestDto applicationApproveRequestDto);

    ResponseEntity<ResponseDto<Void>> rejectApplication(ApplicationRejectRequestDto applicationRejectRequestDto);
}
