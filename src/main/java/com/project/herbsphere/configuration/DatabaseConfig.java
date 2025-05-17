package com.project.herbsphere.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${JDBC_DATABASE_URL:#{null}}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Handle standard JDBC URL or convert postgresql:// format
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            try {
                // Parse the URL properly
                URI dbUri = new URI(databaseUrl);

                // Get username and password from the URI userInfo
                String userInfo = dbUri.getUserInfo();
                String username = null;
                String password = null;

                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":");
                    username = parts[0];
                    password = parts[1];
                }

                // Create the proper JDBC URL
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost();
                if (dbUri.getPort() > 0) {
                    jdbcUrl += ":" + dbUri.getPort();
                }
                jdbcUrl += dbUri.getPath();

                // Build and return the DataSource
                return DataSourceBuilder.create()
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .build();
            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid JDBC_DATABASE_URL", e);
            }
        }

        // Fall back to default Spring Boot datasource initialization
        return DataSourceBuilder.create().build();
    }
}
