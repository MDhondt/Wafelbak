package be.scoutsronse.wafelbak.repository.config;

import be.scoutsronse.wafelbak.repository.db.EventPostingJpaTransactionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.listener.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.dialect.Dialect.DEFAULT_BATCH_SIZE;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource sqlLoggingProxyDataSource() {
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
        System.setProperty("org.slf4j.impl.SimpleLogger.defaultLogLevel", "trace");

        return ProxyDataSourceBuilder
                .create(hikariConnectionPoolingDataSource())
                .name("ProxyDataSource")
                .listener(loggingListener)
                .build();
    }

    private DataSource hikariConnectionPoolingDataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:~/WafelbakDb");
        config.setUsername("sa");
        config.setPassword("sa");
        config.setMaximumPoolSize(200);
        config.setRegisterMbeans(true);

        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource);
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter);
        lcemfb.setPackagesToScan("be.scoutsronse.wafelbak");
        lcemfb.setJpaProperties(additionalProperties());
        return lcemfb;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.jdbc.batch_size", DEFAULT_BATCH_SIZE);
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new EventPostingJpaTransactionManager(emf);
    }
}