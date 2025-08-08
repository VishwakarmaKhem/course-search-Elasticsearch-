package com.SkillBridge.course_search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    private Object data;
    private Long totalDocuments;
}
