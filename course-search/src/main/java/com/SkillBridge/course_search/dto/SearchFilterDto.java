package com.SkillBridge.course_search.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterDto {
    private String id;
    private String title;
    private String description;
    private String category;
    private String type;
    private String gradeRange;
    private Integer minAge;
    private Integer maxAge;
    private Double price;
    @NotNull(message = "date cannot be null")
    private LocalDateTime nextSessionDate;
    private DateFilterModel createdAtFilter;
    private Long createdBy;
}
