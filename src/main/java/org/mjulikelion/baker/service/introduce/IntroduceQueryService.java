package org.mjulikelion.baker.service.introduce;

import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.introduce.IntroduceGetResponseData;
import org.springframework.http.ResponseEntity;

public interface IntroduceQueryService {
    ResponseEntity<ResponseDto<IntroduceGetResponseData>> getStudentIntroduce(String studentId);
}
