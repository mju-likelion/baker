package org.mjulikelion.baker.Service;

import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.application.ApplicationPageGetResponseData;
import org.springframework.http.ResponseEntity;


public interface ApplicationService {

    ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getApplications(String part, int pageNum);
}
