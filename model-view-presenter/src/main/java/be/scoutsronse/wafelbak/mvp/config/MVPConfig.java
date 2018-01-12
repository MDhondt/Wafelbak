package be.scoutsronse.wafelbak.mvp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@ComponentScan("be.scoutsronse.wafelbak.mvp")
public class MVPConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/wafelbak");
        Locale locale = new Locale("nl", "BE");
        String title = messageSource.getMessage("STREET_OVERVIEW_TITLE", null, locale);
        System.out.println(title);
        return messageSource;
    }
}