package net.engineeringDigestt.JournalApp.config;

import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.SSLContext;

@Configuration
public class MongoConfig {

    @Value("${app.mongodb.ssl-enabled:true}")
    private boolean sslEnabled;

    @Bean
    public MongoClientSettingsBuilderCustomizer mongoTlsCustomizer() {
        return clientSettingsBuilder -> clientSettingsBuilder.applyToSslSettings(sslSettingsBuilder -> {
            sslSettingsBuilder.enabled(sslEnabled);
            if (sslEnabled) {
                sslSettingsBuilder.context(buildTls12Context());
            }
        });
    }

    private SSLContext buildTls12Context() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            return sslContext;
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create TLS context for MongoDB", exception);
        }
    }
}
