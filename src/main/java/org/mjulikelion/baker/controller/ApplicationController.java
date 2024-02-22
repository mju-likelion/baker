package org.mjulikelion.baker.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.baker.Service.ApplicationService;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.application.ApplicationPageGetResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping()
    public ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getApplications(
            @RequestParam(value = "part") String part,
            @RequestParam(value = "pageNum") int pageNum) {
        return applicationService.getApplications(part, --pageNum);// pageNum은 1부터 시작하니 0부터 시작하는 페이지로 변환
    }
}
