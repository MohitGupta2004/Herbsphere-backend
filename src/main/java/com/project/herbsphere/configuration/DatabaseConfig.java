package com.project.herbsphere.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        String dbUrl = System.getenv("DATABASE_URL");

        if (dbUrl != null && dbUrl.startsWith("postgres://")) {
            // Convert Render postgres:// URL to jdbc:postgresql:// URL
            dbUrl = dbUrl.replace("postgres://", "jdbc:postgresql://");
            return DataSourceBuilder.create().url(dbUrl).build();
        }

        return DataSourceBuilder.create().build();
    }
}
