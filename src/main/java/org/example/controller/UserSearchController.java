package org.example.controller;

import org.example.model.CachedSearch;
import org.example.request.UserSearchRequest;
import org.example.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserSearchController {

    @Autowired
    private UserSearchService userSearchService;

    @GetMapping("/searchQuery")
    public void searchQuery(@RequestBody UserSearchRequest request){
        request.setTimestamp(System.currentTimeMillis());
        userSearchService.searchQuery(request);
        ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/getRecentSearch")
    public List<String> getRecentSearch(@RequestParam("userId") String userId){
        List<CachedSearch> userCachedResults = userSearchService.getRecentSearch(userId);
        List<String> values = new ArrayList<>();
        if(userCachedResults == null || userCachedResults.size() == 0){
            return values;
        }
        for(CachedSearch c : userCachedResults){
            values.add(c.getValue());
        }
        return values;
    }

    @GetMapping("/cachePrepopulate")
    public void cachePrepopulate(@RequestParam("userId") String userId){
        userSearchService.cachePrepopulate(userId);
        ResponseEntity.ok("SUCCESS");
    }
}
