package org.mjulikelion.baker.util.application;

import static org.mjulikelion.baker.errorcode.ErrorCode.INVALID_PAGE_ERROR;

import org.mjulikelion.baker.exception.InvalidDataException;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.model.Part;
import org.mjulikelion.baker.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUtil {
    private final ApplicationRepository applicationRepository;
    @Value("${application.page.size}")
    private int pageSize;
    @Value("${application.order}")
    private String order;

    public ApplicationUtil(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // 페이지네이션 처리된 지원서 데이터를 가져온다.
    public Page<Application> getPageApplicationsByPart(String part, int pageNum) {
        Part partEnum = Part.findBy(part.toUpperCase());
        this.validatePageNumByPart(partEnum, pageNum);
        // 페이지네이션 처리
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Direction.ASC, order));
        // 페이지네이션 처리된 데이터를 가져온다.
        return this.applicationRepository.findAllByPart(partEnum, pageable);
    }

    // 페이지네이션 처리된 지원서 데이터를 가져온다.(합격여부에 따른)
    public Page<Application> getPageApplicationsByPassAndPart(boolean pass, String part, int pageNum) {
        Part partEnum = Part.findBy(part.toUpperCase());
        this.validatePageNumByPartAndPass(pass, partEnum, pageNum);
        // 페이지네이션 처리
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Direction.ASC, order));
        // 페이지네이션 처리된 데이터를 가져온다.
        return this.applicationRepository.findAllByIsPassAndPart(pass, partEnum, pageable);
    }

    // 페이지네이션 처리를 위한 유효성 검사
    private void validatePageNumByPart(Part part, int pageNum) {
        int totalPages = (int) Math.ceil((double) this.applicationRepository.countAllByPart(part) / pageSize);
        if (pageNum < 0 || pageNum >= totalPages) {
            throw new InvalidDataException(INVALID_PAGE_ERROR);
        }
    }

    // 페이지네이션 처리를 위한 유효성 검사(합격여부에 따른)
    private void validatePageNumByPartAndPass(boolean pass, Part part, int pageNum) {
        int totalPages = (int) Math.ceil(
                (double) this.applicationRepository.countAllByIsPassAndPart(pass, part) / pageSize);
        if (pageNum < 0 || pageNum >= totalPages) {
            throw new InvalidDataException(INVALID_PAGE_ERROR);
        }
    }
}
