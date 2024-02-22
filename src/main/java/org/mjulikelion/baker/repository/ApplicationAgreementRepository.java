package org.mjulikelion.baker.repository;

import java.util.UUID;
import org.mjulikelion.baker.model.ApplicationAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAgreementRepository extends JpaRepository<ApplicationAgreement, UUID> {
}
