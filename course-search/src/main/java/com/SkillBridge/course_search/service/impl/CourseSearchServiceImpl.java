package com.SkillBridge.course_search.service.impl;

import com.SkillBridge.course_search.constants.ApiConstants;
import com.SkillBridge.course_search.document.Courses;
import com.SkillBridge.course_search.dto.SearchFilterDto;
import com.SkillBridge.course_search.dto.response.APIResponse;
import com.SkillBridge.course_search.dto.response.CourseSearchResponse;
import com.SkillBridge.course_search.exception.GeneralSearchingException;
import com.SkillBridge.course_search.service.CourseSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseSearchServiceImpl implements CourseSearchService {

    private final RestHighLevelClient esClient;

    private final ObjectMapper objectMapper;

    public CourseSearchServiceImpl(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.esClient = esClient;
    }

    @Override
    public APIResponse searchCoursesWithFilter(SearchFilterDto filterDto, int pageSize, int pageNumber) {
        APIResponse searchResult = new APIResponse();
        try {
            SearchRequest searchRequest = new SearchRequest(ApiConstants.ESINDEX);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (filterDto == null) {
                sourceBuilder.query(QueryBuilders.matchAllQuery());
            } else {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

                if (StringUtils.hasText(filterDto.getId())) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("id.keyword", filterDto.getId()));
                }

                if (StringUtils.hasText(filterDto.getTitle())) {
                    boolQueryBuilder.filter(QueryBuilders.matchQuery("title", filterDto.getTitle()));
                }

                if (StringUtils.hasText(filterDto.getDescription())) {
                    boolQueryBuilder.filter(QueryBuilders.matchQuery("description", filterDto.getDescription()));
                }

                if (StringUtils.hasText(filterDto.getCategory())) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("category.keyword", filterDto.getCategory()));
                }

                if (StringUtils.hasText(filterDto.getType())) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("type.keyword", filterDto.getType()));
                }

                if (filterDto.getPrice() != null && filterDto.getPrice() != 0) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("price", filterDto.getPrice()));
                }

                if (StringUtils.hasText(filterDto.getGradeRange())) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("gradeRange.keyword", filterDto.getGradeRange()));
                }

                if (filterDto.getMinAge() != null && filterDto.getMinAge() > 0 && filterDto.getMinAge() < 150) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("minAge", filterDto.getMinAge()));
                }

                if (filterDto.getMaxAge() != null && filterDto.getMaxAge() > 0 && filterDto.getMaxAge() < 150) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("maxAge", filterDto.getMaxAge()));
                }

                if (filterDto.getNextSessionDate() != null) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("nextSessionDate", filterDto.getNextSessionDate()));
                }

                if(!Objects.isNull(filterDto.getCreatedAtFilter())){
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("createdAt")
                            .gte(filterDto.getCreatedAtFilter().getFromDate())
                            .lte(filterDto.getCreatedAtFilter().getToDate()));
                }

                if (filterDto.getCreatedBy()!=null){
                    boolQueryBuilder.filter(QueryBuilders.termQuery("createdBy", filterDto.getCreatedBy()));
                }

                sourceBuilder.query(boolQueryBuilder);
            }
            sourceBuilder.from((pageNumber-1)*pageSize);
            sourceBuilder.size(pageSize);
            searchRequest.source(sourceBuilder);
            SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            List<Courses> coursesList = new ArrayList<>();
            for (SearchHit hit : searchHits){
                String source = hit.getSourceAsString();
                if (source==null) continue;
                JsonNode sourceNode = objectMapper.readTree(source);
                Courses course = objectMapper.convertValue(sourceNode, Courses.class);
                coursesList.add(course);
            }
            searchResult.setData(buildSearchResponse(coursesList));
            searchResult.setTotalDocuments(Objects.requireNonNull(response.getHits().getTotalHits()).value);

            return searchResult;
        } catch (Exception e) {
            throw new GeneralSearchingException("Error while searching courses : "+e.getMessage());
        }
    }


    private List<CourseSearchResponse> buildSearchResponse(List<Courses> searchResponse){
        List<CourseSearchResponse> courseSearchResponse = new ArrayList<>();
        if (!searchResponse.isEmpty()){
            for (Courses course: searchResponse){

                courseSearchResponse.add(CourseSearchResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .description(course.getDescription())
                        .category(course.getCategory())
                        .minAge(course.getMinAge())
                        .maxAge(course.getMaxAge())
                        .gradeRange(course.getGradeRange())
                        .nextSessionData(course.getNextSessionDate())
                        .createdAt(course.getCreatedAt())
                        .createdBy(course.getCreatedBy())
                        .build());

            }
        }
        return courseSearchResponse;
    }
}
