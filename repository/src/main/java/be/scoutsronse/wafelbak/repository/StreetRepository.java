package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Long> {
}