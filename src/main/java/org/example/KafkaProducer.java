package org.example;

import com.google.gson.Gson;
import org.example.request.UserSearchRequest;
import org.example.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private Gson gson = new Gson();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserSearchRequest(UserSearchRequest request){
        LOGGER.info("user search request : "+request);
        kafkaTemplate.send(AppConstants.USER_SEARCH_TOPIC, gson.toJson(request));
    }
}