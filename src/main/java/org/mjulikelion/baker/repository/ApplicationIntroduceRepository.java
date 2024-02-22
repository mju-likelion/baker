package org.mjulikelion.baker.repository;

import java.util.UUID;
import org.mjulikelion.baker.model.ApplicationIntroduce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationIntroduceRepository extends JpaRepository<ApplicationIntroduce, UUID> {
}
