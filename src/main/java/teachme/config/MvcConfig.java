package teachme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/learn").setViewName("learn");
        registry.addViewController("/learnAll").setViewName("learnAll");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/manage").setViewName("manage");

    }

}
