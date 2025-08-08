package com.SkillBridge.course_search.Config;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Builder
@Configuration
public class ESConfig {

    @Value("${esdomain}")
    private String esDomain;

    @Value("${esport}")
    private int esPort;

    @Bean
    public RestHighLevelClient client(){
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(esDomain, esPort, "http"))
        );
    }
}
