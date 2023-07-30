package org.example.repository;

import org.example.model.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSearchRepo extends JpaRepository<UserSearch, Long> {
    @Query(value = "select * from user_search where user_name =?1 and timestamp < ?2 order by timestamp desc limit ?3", nativeQuery = true)
    List<UserSearch> findRecentSearchesByUserName(String userName, long timestamp, int limit);
}
