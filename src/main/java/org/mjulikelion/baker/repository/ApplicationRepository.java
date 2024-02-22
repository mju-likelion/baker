package org.mjulikelion.baker.repository;

import java.util.UUID;
import org.mjulikelion.baker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByStudentId(String userId);
}
