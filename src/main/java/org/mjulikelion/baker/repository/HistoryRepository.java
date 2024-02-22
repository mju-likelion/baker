package org.mjulikelion.baker.repository;

import java.util.UUID;
import org.mjulikelion.baker.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, UUID> {
}
