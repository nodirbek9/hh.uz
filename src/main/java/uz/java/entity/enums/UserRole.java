package uz.java.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    JOB_SEEKER, EMPLOYER, ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
