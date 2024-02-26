package org.mjulikelion.baker.repository;

import java.util.List;
import java.util.UUID;
import org.mjulikelion.baker.model.ApplicationIntroduce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationIntroduceRepository extends JpaRepository<ApplicationIntroduce, UUID> {
    List<ApplicationIntroduce> findByApplicationId(UUID applicationId);
}
