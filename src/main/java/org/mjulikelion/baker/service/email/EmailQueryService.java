package org.mjulikelion.baker.service.email;

import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.email.EmailSendResponseData;
import org.springframework.http.ResponseEntity;

public interface EmailQueryService {
    ResponseEntity<ResponseDto<EmailSendResponseData>> sendEmails();
}
