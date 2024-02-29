package org.mjulikelion.baker.service.application;

import static org.mjulikelion.baker.errorcode.ErrorCode.APPLICATION_ALREADY_PASS_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.APPLICATION_ALREADY_REJECT_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.APPLICATION_NOT_FOUND_ERROR;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.dto.request.application.ApplicationApproveRequestDto;
import org.mjulikelion.baker.dto.request.application.ApplicationRejectRequestDto;
import org.mjulikelion.baker.dto.request.application.ApplicationRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.errorcode.ErrorCode;
import org.mjulikelion.baker.exception.ApplicationNotFoundException;
import org.mjulikelion.baker.exception.InvalidDataException;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.repository.ApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationCommandServiceImpl implements ApplicationCommandService {
    private final ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<ResponseDto<Void>> approveApplication(ApplicationApproveRequestDto requestDto) {
        return processApplication(requestDto, true, APPLICATION_ALREADY_PASS_ERROR);
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> rejectApplication(ApplicationRejectRequestDto requestDto) {
        return processApplication(requestDto, false, APPLICATION_ALREADY_REJECT_ERROR);
    }

    private ResponseEntity<ResponseDto<Void>> processApplication(ApplicationRequestDto applicationRequestDto,
                                                                 boolean approve,
                                                                 ErrorCode errorCode) {
        UUID applicationId = UUID.fromString(applicationRequestDto.getApplicationId());
        Application application = this.applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException(APPLICATION_NOT_FOUND_ERROR));

        if ((approve && application.isPass()) || (!approve && !application.isPass())) {
            throw new InvalidDataException(errorCode);
        }

        application.setPass(approve);
        this.applicationRepository.save(application);

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "OK"));
    }
}
