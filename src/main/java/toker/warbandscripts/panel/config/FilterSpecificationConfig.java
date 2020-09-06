package toker.warbandscripts.panel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toker.warbandscripts.panel.util.FilterSpecificationArgumentResolver;

import java.util.List;

@Configuration
public class FilterSpecificationConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new FilterSpecificationArgumentResolver());
    }
}
