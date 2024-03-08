package org.mjulikelion.baker.vo;

import lombok.Getter;
import org.mjulikelion.baker.util.mail.EmailSubject;
import org.mjulikelion.baker.util.mail.EmailTemplate;

@Getter
public class EmailVO {
    private final EmailSubject emailSubject;
    private final EmailTemplate emailTemplate;
    private final String to;
    private final String name;

    public EmailVO(EmailSubject emailSubject, EmailTemplate emailTemplate, String to, String name) {
        this.emailSubject = emailSubject;
        this.emailTemplate = emailTemplate;
        this.to = to;
        this.name = name;
    }
}
