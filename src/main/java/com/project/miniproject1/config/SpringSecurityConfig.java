package com.project.miniproject1.config;

import com.project.miniproject1.security.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
    JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //Security Filter chain after many filters authorize user for end Point Permission
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeHttpRequests(authorize -> {
                    //authorize.requestMatchers(HttpMethod.POST, "/api/employee").permitAll();
                    //authorize.requestMatchers(HttpMethod.GET,"/api/employee").permitAll();
                    //authorize.requestMatchers(HttpMethod.POST,"/api/users").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//.httpBasic(Customizer.withDefaults());
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails john= User.builder().username("john")
//                .password(passwordEncoder().encode("password")).roles("USER").build();
//        UserDetails admin= User.builder().username("admin")
//                .password(passwordEncoder().encode("admin")).roles("ADMIN","USER").build();
//        return new InMemoryUserDetailsManager(john,admin);
//    }
//    @Bean
//   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return  authenticationConfiguration.getAuthenticationManager();
//    }
//}
