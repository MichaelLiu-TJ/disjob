package liuwei.job.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean(name = "healthCheckRestTemplate")
    public RestTemplate getHealthCheckRestTemplate() {
        return new RestTemplate();
    }
}
