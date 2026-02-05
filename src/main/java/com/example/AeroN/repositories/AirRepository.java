package com.example.AeroN.repositories;

import com.example.AeroN.enteties.AirEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AirRepository extends JpaRepository<AirEntity, Integer> {
    @Query("SELECT a.icao24 FROM AirEntity a")
    Set<String> findAllIsao24();


    @Query("SELECT a FROM AirEntity a WHERE a.icao24 = :icao")
    AirEntity findByIsao24(@Param("icao") String icao24);


    @Query("""
    SELECT a
    FROM AirEntity a
    WHERE (SELECT MAX(c.lastContact) FROM a.conditions c) > :lt
    """)
    List<AirEntity> findLastOnLastMinut(@Param("lt") LocalDateTime m);
}
