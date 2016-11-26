package be.scoutsronse.wafelbak.application;

import be.scoutsronse.wafelbak.mvp.config.MVPConfig;
import be.scoutsronse.wafelbak.repository.config.DatabaseConfig;
import be.scoutsronse.wafelbak.repository.config.RepositoryConfig;
import be.scoutsronse.wafelbak.tech.config.TechConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MVPConfig.class,
        RepositoryConfig.class,
        DatabaseConfig.class,
        TechConfig.class})
public class WafelbakConfig {
}