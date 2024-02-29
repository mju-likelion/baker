package org.mjulikelion.baker.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.request.application.ApplicationApproveRequestDto;
import org.mjulikelion.baker.dto.request.application.ApplicationRejectRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.application.ApplicationPageGetResponseData;
import org.mjulikelion.baker.service.application.ApplicationCommandService;
import org.mjulikelion.baker.service.application.ApplicationQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ApplicationController {
    private final ApplicationQueryService applicationQueryService;
    private final ApplicationCommandService applicationCommandService;

    @GetMapping("/applications")
    public ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getApplications(
            @RequestParam(value = "part") String part,
            @RequestParam(value = "pageNum") int pageNum) {
        return applicationQueryService.getApplications(part, --pageNum);// pageNum은 1부터 시작하니 0부터 시작하는 페이지로 변환
    }

    @GetMapping("/applications/passed")
    public ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getPassedApplications(
            @RequestParam(value = "part") String part,
            @RequestParam(value = "pageNum") int pageNum) {
        return applicationQueryService.getPassedApplications(part, --pageNum);// pageNum은 1부터 시작하니 0부터 시작하는 페이지로 변환
    }

    @PatchMapping("/application/approve")
    public ResponseEntity<ResponseDto<Void>> approveApplication(
            @RequestBody() @Valid ApplicationApproveRequestDto applicationApproveRequestDto
    ) {
        return applicationCommandService.approveApplication(applicationApproveRequestDto);
    }

    @PatchMapping("/application/reject")
    public ResponseEntity<ResponseDto<Void>> rejectApplication(
            @RequestBody() @Valid ApplicationRejectRequestDto applicationRejectRequestDto
    ) {
        return applicationCommandService.rejectApplication(applicationRejectRequestDto);
    }
}
