package org.example;

import com.google.gson.Gson;
import org.apache.catalina.User;
import org.example.model.UserSearch;
import org.example.repository.UserSearchRepo;
import org.example.request.UserSearchRequest;
import org.example.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private Gson gson = new Gson();

    @Autowired
    private UserSearchRepo userSearchRepo;

    @KafkaListener(topics = AppConstants.USER_SEARCH_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserSearchTopic(List<String> messages){
        LOGGER.info(String.format("Message received from" +AppConstants.USER_SEARCH_TOPIC + " -> %s", messages));
        Map<String, List<String>> userNameVsSearchQuery = new HashMap<>();
        for(String msg : messages){
            UserSearchRequest request = gson.fromJson(msg, UserSearchRequest.class);
            UserSearch userSearch = new UserSearch();
            userSearch.setSearchQuery(request.getSearchQuery());
            userSearch.setUserName(request.getUserName());
            userSearch.setTimestamp(request.getTimestamp());
            userSearchRepo.save(userSearch);
        }
    }
}