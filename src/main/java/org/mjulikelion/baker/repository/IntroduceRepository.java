package org.mjulikelion.baker.repository;

import java.util.List;
import org.mjulikelion.baker.model.Introduce;
import org.mjulikelion.baker.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroduceRepository extends JpaRepository<Introduce, String> {
    List<Introduce> findAllByPartOrderBySequence(Part part);

    Long countByPart(Part part);
}
