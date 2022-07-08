package nbu.onlinetradingsytem.config;

import nbu.onlinetradingsytem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/cart").authenticated()
                .antMatchers(HttpMethod.GET, "/statistics").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/editUser").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/editUser").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/store").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/store").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/deleteUser").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/suppliers").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/editSupplier").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/editProduct").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/editSupplier").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/editProduct").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/addProduct").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/addProduct").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/deleteSupplier").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/deleteProduct").hasAnyAuthority("ADMIN", "MODERATOR")
//                .antMatchers("/register",
//                        "/login",
//                        "/users",
//                        "/"
//                        "/registration**",
//                        "/js/**",
//                        "/css/**",
//                        "/img/**",
//                      "/webjars/**").permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?id=error")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll().and().cors().and().
                csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
}