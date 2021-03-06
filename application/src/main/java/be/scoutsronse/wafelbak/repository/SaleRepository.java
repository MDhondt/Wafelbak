package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SaleRepository extends JpaRepository<Sale, Long> {
}