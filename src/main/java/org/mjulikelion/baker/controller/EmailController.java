package org.mjulikelion.baker.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.email.EmailSendResponseData;
import org.mjulikelion.baker.service.email.EmailQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class EmailController {
    private final EmailQueryService emailQueryService;

    @PostMapping("/send-email")
    public ResponseEntity<ResponseDto<EmailSendResponseData>> sendEmails() {
        return this.emailQueryService.sendEmails();
    }
}
