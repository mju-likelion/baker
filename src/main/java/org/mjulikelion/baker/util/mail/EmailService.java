package org.mjulikelion.baker.util.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.baker.vo.EmailVO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendMail(EmailVO emailVO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailVO.getTo());
            mimeMessageHelper.setFrom("mju@likelion.org");
            mimeMessageHelper.setSubject(emailVO.getEmailSubject().getSubject());
            mimeMessageHelper.setText(
                    setContext(emailVO.getName(), emailVO.getEmailTemplate().getMessage()), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String setContext(String name, String type) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process(type, context);
    }
}
