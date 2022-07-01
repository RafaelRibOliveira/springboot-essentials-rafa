package academy.rafael.springboot.config;


import academy.rafael.springboot.service.DevUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DevUserDetailsService devUserDetailsService;


    /**
     * BasicAuthenticationFilter
     * UserNamePasswordAuthenticationFilter
     * DefaultLoginPageGenerationFilter
     * DefaultLogoutPageGenerationFilter
     * filterSecurityInterceptor
     * Authentication -> Authorization
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
   //             csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               .authorizeRequests()
               .antMatchers("/usuarios/admin**").hasRole("ADMIN")
               .antMatchers("/usuarios/**").hasRole("USER")
               .antMatchers("/actuator/**").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .and()
               .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("academy"));

        auth.inMemoryAuthentication()
                .withUser("william2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER","ADMIN")
                .and()
                .withUser("Rafa2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER");

    auth.userDetailsService(devUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

}

