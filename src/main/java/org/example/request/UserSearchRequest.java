package org.example.request;

public class UserSearchRequest {
    private String userName;
    private String searchQuery;
    private Long timestamp;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String toString() {
        return "UserSearchRequest{" +
                "userName='" + userName + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
