package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.persistance.entity.WorkTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresRepositoryJpa extends CrudRepository<WorkTime, Long> {
    Optional<WorkTime> findByUserIdAndSessionId(String userId, String sessionId);
}
