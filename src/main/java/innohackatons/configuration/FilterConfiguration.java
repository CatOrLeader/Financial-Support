package innohackatons.configuration;

import innohackatons.filter.SecretTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class FilterConfiguration {
    private final ApplicationConfiguration configuration;

    @Bean
    public FilterRegistrationBean<SecretTokenFilter> secretTokenFilter() {
        FilterRegistrationBean<SecretTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new SecretTokenFilter(configuration));
        registrationBean.addUrlPatterns("/user/*", "/deposit/*", "/piggy-bank/*", "/report/*", "/transaction/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
