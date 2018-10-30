package be.scoutsronse.wafelbak.config;

import be.scoutsronse.wafelbak.tech.config.TechConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("be.scoutsronse.wafelbak")
@Import({RepositoryConfig.class,
        DatabaseConfig.class,
        TechConfig.class})
public class WafelbakConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/wafelbak");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}