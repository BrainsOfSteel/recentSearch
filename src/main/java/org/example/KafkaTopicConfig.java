package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.utils.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic userSearchTopic(){
        return TopicBuilder.name(AppConstants.USER_SEARCH_TOPIC)
                .build();
    }
}