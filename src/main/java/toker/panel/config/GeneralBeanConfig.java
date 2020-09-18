package toker.panel.config;

import org.openid4java.consumer.ConsumerManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.openid.NullAxFetchListFactory;
import org.springframework.security.openid.OpenID4JavaConsumer;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.MultipartConfigElement;
import java.net.URI;

@Configuration
public class GeneralBeanConfig {

    @Bean
    public OpenIDConsumer openIDConsumer() {

        ConsumerManager manager = new ConsumerManager();
        manager.setMaxAssocAttempts(0);
        return new OpenID4JavaConsumer(manager, new NullAxFetchListFactory());
    }

    @Bean
    @Qualifier("steam-api")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.interceptors((request, body, execution) -> {
            HttpRequest newRequest = new HttpRequestWrapper(request) {
                @Override
                public URI getURI() {
                    return UriComponentsBuilder.fromHttpRequest(request)
                            .queryParam("key", "827E49C8DF4885520BD4559A0BC83F23")
                            .build().toUri();
                }
            };
            return execution.execute(newRequest, body);
        }).build();
    }

    @Bean
    public MultipartConfigElement multipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(1280));
        factory.setMaxRequestSize(DataSize.ofMegabytes(1280));
        return factory.createMultipartConfig();
    }
}
