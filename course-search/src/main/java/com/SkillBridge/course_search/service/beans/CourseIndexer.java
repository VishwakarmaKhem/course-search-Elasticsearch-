package com.SkillBridge.course_search.service.beans;

import com.SkillBridge.course_search.document.Courses;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CourseIndexer {
    @Bean
    public ApplicationRunner indexCourses(@Value("classpath:sample-courses.json") Resource resource
            , ElasticsearchOperations operations
            , ObjectMapper mapper){
        return args -> {
            Courses[] courses = mapper.readValue(resource.getInputStream(), Courses[].class);
            Arrays.stream(courses).forEach(course -> operations.save(courses));
            System.out.println("Indexes " + courses.length + " courses.");
        };
    }
}
