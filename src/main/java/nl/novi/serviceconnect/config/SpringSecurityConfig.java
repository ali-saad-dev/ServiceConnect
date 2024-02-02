package nl.novi.serviceconnect.config;

import nl.novi.serviceconnect.filter.JwtRequestFilter;
import nl.novi.serviceconnect.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                 //.requestMatchers("/**").permitAll()

                                //serviceCategory
                                .requestMatchers(HttpMethod.POST,"/serviceCategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/serviceCategory/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/serviceCategory/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/serviceCategory/**").hasRole("ADMIN")

                                //service
                                .requestMatchers(HttpMethod.POST,"/services").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/services/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.PUT,"/services/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.DELETE,"/services/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/uploadFileOrImageToService/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/getFileOrImageOfService/**").hasRole("ADMIN")

                                //servicesRequest
                                .requestMatchers(HttpMethod.POST,"/servicesRequest").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/servicesRequest/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.PUT,"/servicesRequest/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/servicesRequest/**").hasRole("ADMIN")

                                //transaction
                                .requestMatchers(HttpMethod.POST,"/transaction").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/transaction/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/transaction/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/transaction/**").hasRole("ADMIN")

                                //Query's
                                .requestMatchers(HttpMethod.GET,"/querys/**").hasRole("ADMIN")

                                //Users
                                .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/users/{username}/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/users/register").permitAll()

                                //UserAuthenticateInfo
                                .requestMatchers("/authenticated").authenticated()
                                .requestMatchers("/authenticate").permitAll()
                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
