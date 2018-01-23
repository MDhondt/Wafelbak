package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}