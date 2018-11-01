package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.domain.entity.UndocumentedSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UndocumentedSaleRepository extends JpaRepository<UndocumentedSale, Long> {

    Optional<UndocumentedSale> findByYear(Short year);
}