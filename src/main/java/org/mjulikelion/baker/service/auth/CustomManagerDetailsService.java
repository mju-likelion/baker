package org.mjulikelion.baker.service.auth;

import static org.mjulikelion.baker.errorcode.ErrorCode.AUTHENTICATION_ERROR;

import lombok.RequiredArgsConstructor;
import org.mjulikelion.baker.exception.AuthenticationException;
import org.mjulikelion.baker.model.Manager;
import org.mjulikelion.baker.repository.ManagerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomManagerDetailsService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String managerId) throws UsernameNotFoundException {
        return this.managerRepository.findById(managerId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR, "해당하는 관리자를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Manager manager) {
        return Manager.builder()
                .id(manager.getId())
                .password(manager.getPassword())
                .roles(manager.getRoles())
                .build();
    }
}