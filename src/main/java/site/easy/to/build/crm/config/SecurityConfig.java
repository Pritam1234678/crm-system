package site.easy.to.build.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig {

    private final CrmUserDetails crmUserDetails;

    private final CustomerUserDetails customerUserDetails;

    private final Environment environment;

    @Autowired
    public SecurityConfig(CrmUserDetails crmUserDetails, CustomerUserDetails customerUserDetails, Environment environment) {
        this.crmUserDetails = crmUserDetails;
        this.customerUserDetails = customerUserDetails;
        this.environment = environment;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        httpSessionCsrfTokenRepository.setParameterName("csrf");

        http.csrf((csrf) -> csrf
                .csrfTokenRepository(httpSessionCsrfTokenRepository)
        );

        http.
                authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/set-employee-password/**").permitAll()
                        .requestMatchers("/change-password/**").permitAll()
                        .requestMatchers("/font-awesome/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/save").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/**/manager/**")).hasRole("MANAGER")
                        .requestMatchers("/employee/**").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login")
                        .permitAll()
                ).userDetailsService(crmUserDetails)
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler(accessDeniedHandler());
                });


        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {


        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        httpSessionCsrfTokenRepository.setParameterName("csrf");

        http.csrf((csrf) -> csrf
                .csrfTokenRepository(httpSessionCsrfTokenRepository)
        );

        http.securityMatcher("/customer-login/**").
                authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/set-password/**").permitAll()
                        .requestMatchers("/font-awesome/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/**/manager/**")).hasRole("MANAGER")
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/customer-login")
                        .loginProcessingUrl("/customer-login")
                        .failureUrl("/customer-login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()).userDetailsService(customerUserDetails)
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/customer-login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
