package org.mjulikelion.baker.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplate {
    FIRST_APPROVE("first-approve"),
    FIRST_REJECT("first-reject");

    private final String message;
}
