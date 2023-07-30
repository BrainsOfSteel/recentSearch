package org.example.response;

import java.util.List;

public class UserRecentResponse {
    private List<String> userResponse;

    public List<String> getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(List<String> userResponse) {
        this.userResponse = userResponse;
    }

    @Override
    public String toString() {
        return "UserRecentResponse{" +
                "userResponse=" + userResponse +
                '}';
    }
}
