package cz.vsb.vea.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
      /*  http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/*").authenticated().and().formLogin().permitAll().and().logout().permitAll().invalidateHttpSession(true)
                .and().exceptionHandling().accessDeniedPage("/403");*/

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/*").authenticated().and().formLogin().loginProcessingUrl("/login")
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("login")
                .passwordParameter("password")
                .and().logout().permitAll().invalidateHttpSession(true).logoutSuccessUrl("/login?logout").permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");

    }
}
