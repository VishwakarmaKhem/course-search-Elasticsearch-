package com.SkillBridge.course_search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateFilterModel {
    private Long fromDate;
    private Long toDate;
}
