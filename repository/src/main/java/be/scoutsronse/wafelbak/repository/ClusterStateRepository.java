package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterStateRepository extends JpaRepository<ClusterState, Long> {
}