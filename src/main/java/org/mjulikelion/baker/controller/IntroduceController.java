package org.mjulikelion.baker.controller;

import static org.mjulikelion.baker.constant.RegexPatterns.APPLICATION_STUDENT_ID_PATTERN;

import jakarta.validation.constraints.Pattern;
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
    @Cacheable(value = "applicationByStudentId", key = "#studentId")
    public ResponseEntity<ResponseDto<IntroduceGetResponseData>> getStudentIntroduce(
            @RequestParam(value = "studentId")
            @Pattern(regexp = APPLICATION_STUDENT_ID_PATTERN, message = "학번이 형식에 맞지 않습니다.") String studentId
    ) {
        return this.introduceQueryService.getStudentIntroduce(studentId);
    }
}
