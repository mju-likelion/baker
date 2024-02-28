package org.mjulikelion.baker.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.introduce.IntroduceGetResponseData;
import org.mjulikelion.baker.service.introduce.IntroduceQueryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class IntroduceController {
    private final IntroduceQueryService introduceQueryService;

    @GetMapping("/introduces")
    @Cacheable(value = "applicationByApplicationId", key = "#applicationId")
    public ResponseEntity<ResponseDto<IntroduceGetResponseData>> getStudentIntroduce(
            @RequestParam(value = "applicationId") UUID applicationId
    ) {
        return this.introduceQueryService.getStudentIntroduce(applicationId);
    }
}
