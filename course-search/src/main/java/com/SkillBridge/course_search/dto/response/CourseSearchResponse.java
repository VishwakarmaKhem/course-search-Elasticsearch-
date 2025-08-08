package com.SkillBridge.course_search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSearchResponse {
    private String id;
    private String title;
    private String category;
    private String description;
    private String type;
    private String gradeRange;
    private Integer minAge;
    private Integer maxAge;
    private Double price;
    private LocalDateTime nextSessionData;
    private Long createdAt;
    private Long createdBy;
}
