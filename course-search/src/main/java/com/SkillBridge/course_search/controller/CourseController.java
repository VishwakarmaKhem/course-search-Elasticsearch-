package com.SkillBridge.course_search.controller;

import com.SkillBridge.course_search.dto.SearchFilterDto;
import com.SkillBridge.course_search.dto.response.APIResponse;
import com.SkillBridge.course_search.dto.response.CourseSearchResponse;
import com.SkillBridge.course_search.exception.GeneralSearchingException;
import com.SkillBridge.course_search.service.CourseSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class CourseController {

    private CourseSearchService courseSearchService;

    public CourseController(CourseSearchService courseSearchService) {
        this.courseSearchService = courseSearchService;
    }

    @PostMapping("/courses/getCourses")
    public ResponseEntity<APIResponse> getSearchResponse(@RequestBody(required = false) SearchFilterDto filter, @RequestParam(required = true) int pageSize, @RequestParam(required = true) int pageNumber){
        try{
            APIResponse response = courseSearchService.searchCoursesWithFilter(filter, pageSize, pageNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new GeneralSearchingException("error while searching : "+e+" "+e.getMessage()+" "+e.getCause());
        }
    }

}
