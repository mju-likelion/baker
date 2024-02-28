package org.mjulikelion.baker.service.application;

import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.application.ApplicationPageGetResponseData;
import org.springframework.http.ResponseEntity;


public interface ApplicationQueryService {

    ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getApplications(String part, int pageNum);

    ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getPassedApplications(String part, int pageNum);
}
