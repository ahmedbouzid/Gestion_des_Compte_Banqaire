package org.banque.sec;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("banque").password("{noop}admin").roles("admin");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/operations","consulterCompte" ,"saveOperation").authenticated()
		.and().
			formLogin().loginPage("/login")
		.and()
		.logout()
        .logoutSuccessUrl("/login?logout=true")
        .invalidateHttpSession(true)
        .permitAll();
	}
	
	
}