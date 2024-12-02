package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.persistance.entity.WorkTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresRepositoryJpa extends CrudRepository<WorkTime, Long> {
    Optional<WorkTime> findByUserIdAndSessionId(String userId, String sessionId);

    @Query("select s from WorkTime s where s.userId=:userId and s.endTime is null")
    Optional<WorkTime> findCurrentSessionByUserId(@Param("userId") String userId);
}
