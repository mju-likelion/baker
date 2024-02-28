package org.mjulikelion.baker.service.application;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.application.ApplicationPageGetResponseData;
import org.mjulikelion.baker.dto.response.application.ApplicationResponse;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.util.application.ApplicationUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationQueryServiceImpl implements ApplicationQueryService {
    private final ApplicationUtil applicationUtil;

    public ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getApplications(String part, int pageNum) {
        Page<Application> applicationPage = this.applicationUtil.getPageApplicationsByPart(part, pageNum);

        ApplicationPageGetResponseData responseData = this.makeApplicationPageGetResponseData(applicationPage, pageNum);

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), responseData));
    }

    @Override
    public ResponseEntity<ResponseDto<ApplicationPageGetResponseData>> getPassedApplications(String part, int pageNum) {
        Page<Application> applicationPage = this.applicationUtil.getPageApplicationsByPassAndPart(true, part, pageNum);

        ApplicationPageGetResponseData responseData = this.makeApplicationPageGetResponseData(applicationPage, pageNum);

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), responseData));
    }

    public ApplicationPageGetResponseData makeApplicationPageGetResponseData(Page<Application> applicationPage,
                                                                             int pageNum) {
        return ApplicationPageGetResponseData.builder()
                .applications(applicationPage.getContent().stream()
                        .map(ApplicationResponse::makeApplicationResponse)
                        .collect(Collectors.toList()))
                .totalPage(applicationPage.getTotalPages())
                .currentPage(pageNum + 1)// pageNum은 1부터 시작하니 0부터 시작하는 페이지로 변환
                .build();
    }
}
