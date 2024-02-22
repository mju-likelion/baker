package org.mjulikelion.baker.repository;

import java.util.UUID;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.model.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    Long countAllByPart(Part part);

    Page<Application> findAllByPart(Part part, Pageable pageable);
}
