package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional
public interface StreetRepository extends JpaRepository<Street, Long> {

    @Query("select s from Street s where s.id = :#{#id.value()}")
    Optional<Street> findById(@Param("id") StreetId id);

    @Query("select s from Street s where s.id in :#{#ids.![value()]}")
    Collection<Street> findByIds(@Param("ids") Collection<StreetId> ids);
}