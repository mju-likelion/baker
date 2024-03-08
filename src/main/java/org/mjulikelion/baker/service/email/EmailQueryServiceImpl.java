package org.mjulikelion.baker.service.email;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.email.EmailSendResponseData;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.repository.ApplicationRepository;
import org.mjulikelion.baker.util.mail.EmailService;
import org.mjulikelion.baker.util.mail.EmailSubject;
import org.mjulikelion.baker.util.mail.EmailTemplate;
import org.mjulikelion.baker.vo.EmailVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailQueryServiceImpl implements EmailQueryService {
    private final ApplicationRepository applicationRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<ResponseDto<EmailSendResponseData>> sendEmails() {
        EmailSendResponseData emailSendResponseData = new EmailSendResponseData();

        List<Application> applications = this.applicationRepository.findAll();
        applications.forEach(application -> {
            try {
                EmailVO emailVO = new EmailVO(EmailSubject.FIRST_RESULT,
                        application.isPass() ? EmailTemplate.FIRST_APPROVE : EmailTemplate.FIRST_REJECT,
                        application.getEmail(), application.getName());
                this.emailService.sendMail(emailVO);
                emailSendResponseData.addSentApplicationId(application.getEmail());
            } catch (Exception e) {
                log.error("Failed to send email to {}, {}", application.getEmail(), e.getMessage());
                emailSendResponseData.addFailedApplicationId(application.getEmail());
            }
        });
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "OK", emailSendResponseData), HttpStatus.OK);
    }
}
