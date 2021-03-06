package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    @Query(value = "SELECT * FROM team " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<TeamEntity> findAll();

    @Query(value = "SELECT * FROM team " +
            "WHERE external_id = :external_id " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<TeamEntity> findOneByExternalId(@Param("external_id") Long externalId);
}
