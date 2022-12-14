package pepsico.entry.domain.hcm;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@ComponentScan
@EntityScan("pepsico.entry.services.hcm")
@EnableJpaRepositories("pepsico.entry.services.hcm")
@PropertySource("classpath:db-config.properties")
public class HCMConfiguration {
    protected Logger logger;

    public HCMConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Creates an in-memory "hcm" database populated with test data for fast
     * testing
     */
    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        // Create an in-memory H2 relational database containing some demo
        // hcms.

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        DataSource dataSource = builder
                .setType(EmbeddedDatabaseType.H2) // HSQL or DERBY
                .addScript("classpath:testdb/hcm-schema.sql")
                //.addScript("classpath:testdb/hcm-data.sql")
                .build();

        logger.info("dataSource = " + dataSource);

        return dataSource;
    }

    @Bean
    public JdbcTemplate createJdbcTeamplate() {

        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());

        return template;
    }
}
