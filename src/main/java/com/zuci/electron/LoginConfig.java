package com.zuci.electron;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.zuci.electron.session.CustomSecurityFilter;

@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware{
	
	// private ApplicationContext applicationContext;

		/*
		 * @Auto public void setApplicationContext(ApplicationContext
		 * applicationContext) { this.applicationContext = applicationContext; }
		 */
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	CustomLogoutHandler customLogouthandler;
	
	//@Autowired
	//CustomSecurityFilter customSecurityFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * http.authorizeRequests() .antMatchers("/admin").hasRole("ADMIN")
		 * .antMatchers("/user").hasAnyRole("USER","ADMIN")
		 * .antMatchers("/home").authenticated()
		 * .antMatchers("/","/error","/favicon.ico","/forgetpwd","/applogin").permitAll(
		 * ) .and().formLogin().loginPage("/applogin") .permitAll().and() .csrf()
		 * .disable();
		 */
		 
		
		
		/*
		 * http.authorizeRequests().antMatchers("/resources/**","/login").permitAll()
		 * .anyRequest().authenticated()
		 * .and().formLogin().loginPage("/login").defaultSuccessUrl("/home")
		 * .permitAll().and() .csrf() .disable();;
		 */
				// .usernameParameter("emailId").passwordParameter("password") .and()
				
		 
		
		
		http.authorizeRequests()
		.antMatchers("/login","/forgetpwd","/recreatepwd","/processrecreatepwd","/js/**","/notifications","/notifications/**").permitAll()
		.antMatchers("/admin/*").hasAnyRole("ADMIN")
		.antMatchers("/**").hasAnyRole("ADMIN", "USER").and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true").permitAll()
		.and().logout().logoutSuccessUrl("/login?logout=true")
		.invalidateHttpSession(true).permitAll().and().csrf().disable();
		//.and().logout().addLogoutHandler(customLogouthandler).logoutSuccessUrl("/login?logout=true").deleteCookies("SESSION")
		//.addFilterBefore(customSecurityFilter, WebAsyncManagerIntegrationFilter.class);
		//.sessionManagement().maximumSessions(1);
		
		 
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
