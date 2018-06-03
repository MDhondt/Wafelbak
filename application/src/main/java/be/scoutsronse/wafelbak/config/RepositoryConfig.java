package be.scoutsronse.wafelbak.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("be.scoutsronse.wafelbak.repository")
@EnableTransactionManagement
@EnableJpaRepositories("be.scoutsronse.wafelbak.repository")
public class RepositoryConfig {
}