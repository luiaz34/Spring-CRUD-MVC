package khaing.thymeleaf.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class EmployeeSecurity {

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
                return new JdbcUserDetailsManager(dataSource);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(configurer -> configurer
                                                .requestMatchers("/css/loginRegisterForm.css", "/js/script.js")
                                                .permitAll()
                                                .requestMatchers("/api/checkAuthenticated").permitAll()
                                                .requestMatchers("/api/update").hasAnyRole("MANAGER", "ADMIN")
                                                .requestMatchers("/api/delete").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/api/showLogin")
                                                .loginProcessingUrl("/authenticateTheUser")
                                                .permitAll()
                                                .defaultSuccessUrl("/api/home", true))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false)
                                                //.expiredUrl("/api/showLogin"))   
                                                .expiredUrl("/logout"))                      
                                        
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/api/showLogin")
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                                                .permitAll())

                                .build();
        }

        @Bean
        public SessionRegistry sessionRegistry() {
                SessionRegistry sessionRegistry = new SessionRegistryImpl();
                return sessionRegistry;
        }

        @Bean
        public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
        }
}

