package khaing.thymeleaf.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EmployeeSecurity {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/css/loginRegisterForm.css", "/js/script.js").permitAll()
                        .requestMatchers("/api/update").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/delete").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/api/showLogin")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                        .defaultSuccessUrl("/api/list", true))
                .logout(logout -> logout
                        .logoutUrl("/logout") // Custom logout URL to match your form action.
                        .logoutSuccessUrl("/api/showLogin") // Redirect to login page after logout.
                        .invalidateHttpSession(true) // Invalidate session.
                        .clearAuthentication(true) // Clear authentication attributes.
                        .permitAll())
                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/api/roleAccessDenied"));
        return http.build();
    }

}
