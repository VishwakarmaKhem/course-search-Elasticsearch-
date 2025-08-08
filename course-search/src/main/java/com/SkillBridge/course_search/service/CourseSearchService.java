package com.SkillBridge.course_search.service;
import com.SkillBridge.course_search.dto.SearchFilterDto;
import com.SkillBridge.course_search.dto.response.APIResponse;

public interface CourseSearchService {
    APIResponse searchCoursesWithFilter(SearchFilterDto filter, int pageSize, int pageNumber);
}
