package teachme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/dist/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("gorbash")
                        .password("krowa1234")
                        .roles("USER")
                        .build();

        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("lukaszc")
                        .password("lukaszc")
                        .roles("USER")
                        .build();


        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("lukaszw")
                        .password("lukaszw")
                        .roles("USER")
                        .build();

        UserDetails user4 =
                User.withDefaultPasswordEncoder()
                        .username("olas")
                        .password("olas")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user, user2, user3, user4);
    }
}
