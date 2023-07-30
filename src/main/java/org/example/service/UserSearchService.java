package org.example.service;

import org.example.KafkaProducer;
import org.example.model.CachedSearch;
import org.example.model.UserSearch;
import org.example.repository.UserSearchRepo;
import org.example.request.UserSearchRequest;
import org.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSearchService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserSearchRepo userSearchRepo;


    public void searchQuery(UserSearchRequest request){
        String key = Util.REDIS_KEY_PREFIX + request.getUserName();
        CachedSearch cachedSearch = new CachedSearch(request.getSearchQuery(), request.getTimestamp());
        redisService.pushString(key, cachedSearch);
        kafkaProducer.sendUserSearchRequest(request);
    }

    public List<CachedSearch> getRecentSearch(String userId){
        return redisService.getRecentSearches(Util.REDIS_KEY_PREFIX + userId);
    }

    public void cachePrepopulate(String userId) {
        String key = Util.REDIS_KEY_PREFIX + userId;
        List<CachedSearch> cachedSearches = redisService.getRecentSearches(key);
        if(cachedSearches == null || cachedSearches.size() < 10){
            long oldestTimestamp = cachedSearches != null && cachedSearches.size() > 0 ?
                    cachedSearches.get(cachedSearches.size()-1).getTimestamp(): System.currentTimeMillis();
            List<UserSearch> userSearches = userSearchRepo.findRecentSearchesByUserName(userId, oldestTimestamp, 10- cachedSearches.size());
            if(userSearches.size() > 0){
                List<CachedSearch> values= new ArrayList<>();
                for(UserSearch u : userSearches){
                    CachedSearch cachedSearch = new CachedSearch(u.getSearchQuery(), u.getTimestamp());
                    values.add(cachedSearch);
                }
                redisService.pushListStrings(key, values);
            }
        }
    }
}
