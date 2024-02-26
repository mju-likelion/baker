package org.mjulikelion.baker.dto.response.introduce;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import org.mjulikelion.baker.model.Part;
import org.mjulikelion.baker.vo.IntroduceDetailVO;

@Builder
public class IntroduceGetResponseData {
    @JsonProperty
    private final Part part;
    @JsonProperty
    private final List<IntroduceDetailVO> introduceDetailVOList;
}
