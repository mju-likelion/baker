package org.mjulikelion.baker.service.introduce;

import static org.mjulikelion.baker.errorcode.ErrorCode.APPLICATION_INTRODUCE_NOT_FOUND_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.APPLICATION_NOT_FOUND_ERROR;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.dto.response.introduce.IntroduceGetResponseData;
import org.mjulikelion.baker.exception.ApplicationIntroduceNotFoundException;
import org.mjulikelion.baker.exception.ApplicationNotFoundException;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.model.ApplicationIntroduce;
import org.mjulikelion.baker.model.Introduce;
import org.mjulikelion.baker.repository.ApplicationIntroduceRepository;
import org.mjulikelion.baker.repository.ApplicationRepository;
import org.mjulikelion.baker.vo.IntroduceDetailVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class IntroduceQueryServiceImpl implements IntroduceQueryService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationIntroduceRepository applicationIntroduceRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto<IntroduceGetResponseData>> getStudentIntroduce(UUID applicationId) {
        Application application = this.findApplicationById(applicationId);
        List<ApplicationIntroduce> applicationIntroduceList = this.findApplicationIntroduces(application.getId());
        List<IntroduceDetailVO> introduceDetailVOList = this.buildIntroduceDetailVOList(applicationIntroduceList);

        IntroduceGetResponseData introduceGetResponseData = IntroduceGetResponseData.builder()
                .introduceDetailVOList(introduceDetailVOList)
                .studentId(application.getStudentId())
                .name(application.getName())
                .part(application.getPart())
                .build();

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "OK", introduceGetResponseData));
    }

    private Application findApplicationById(UUID applicationId) {
        return this.applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException(APPLICATION_NOT_FOUND_ERROR));
    }

    private List<ApplicationIntroduce> findApplicationIntroduces(UUID applicationId) {
        List<ApplicationIntroduce> applicationIntroduceList = this.applicationIntroduceRepository.findByApplicationId(
                applicationId);
        if (applicationIntroduceList.isEmpty()) {
            throw new ApplicationIntroduceNotFoundException(APPLICATION_INTRODUCE_NOT_FOUND_ERROR);
        }
        return applicationIntroduceList;
    }

    private List<IntroduceDetailVO> buildIntroduceDetailVOList(List<ApplicationIntroduce> applicationIntroduceList) {
        Map<Introduce, String> introduceMap = applicationIntroduceList.stream()
                .sorted(Comparator.comparingInt(a -> a.getIntroduce().getSequence()))
                .collect(Collectors.toMap(
                        ApplicationIntroduce::getIntroduce,
                        ApplicationIntroduce::getContent,
                        (a, b) -> b,
                        LinkedHashMap::new
                ));

        return introduceMap.keySet().stream()
                .map(introduce -> IntroduceDetailVO.builder()
                        .sequence(introduce.getSequence())
                        .title(introduce.getTitle())
                        .content(introduceMap.get(introduce))
                        .maxLength(introduce.getMaxLength())
                        .build())
                .collect(Collectors.toList());
    }
}

