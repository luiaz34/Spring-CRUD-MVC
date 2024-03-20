package khaing.thymeleaf.caching;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ComponentScan("khaing.thymeleaf.service")
public class CacheConfig {
    
}
