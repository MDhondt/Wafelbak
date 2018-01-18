package be.scoutsronse.wafelbak.repository.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("be.scoutsronse.wafelbak.repository")
@EnableTransactionManagement
public class RepositoryConfig {
}