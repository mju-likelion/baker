package org.mjulikelion.baker.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ROLE_USER("USER"), ROLE_ADMIN("ADMIN");
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
