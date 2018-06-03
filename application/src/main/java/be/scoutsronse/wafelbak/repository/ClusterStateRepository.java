package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClusterStateRepository extends JpaRepository<ClusterState, Long> {
}