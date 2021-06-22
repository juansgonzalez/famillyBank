package com.juanseb.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static UserDetails userDetails;
	//private static MyUserDetails userDetails;
	
	/**
	 * Require login to access internal pages and configure login form.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.csrf().disable()

				// Register our CustomRequestCache, that saves unauthorized access attempts, so
				// the user is redirected after login.
				.requestCache().requestCache(new CustomRequestCache())

				// Restrict access to our application.
				.and().authorizeRequests()

				// Allow all Vaadin internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Allow all requests by logged in users.
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin()
                        .loginPage(LOGIN_URL).permitAll()
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
				        .failureUrl(LOGIN_FAILURE_URL)

				// Configure logout
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// autenticar con usuario en la base de datos
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /*
	public static boolean isAdmin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
    	if (principal instanceof UserDetails) {
    		userDetails = ((UserDetails)principal);
    	
	    	for(GrantedAuthority granted : userDetails.getAuthorities()) { 
	    		if (granted.getAuthority().equals("ROLE_ADMIN"))
	    			return true;
	    	}
    	}
    	
    	return false;
	}
	*/
	
	public static UserDetails getUserDetails() {
		return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				// Client-side JS
				"/VAADIN/**",

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest",
				"/sw.js",
				"/offline.html",

				// icons and images
				"/icons/**",
				"/images/**",
				"/styles/**",

				// (development mode) H2 debugging console
				"/h2-console/**");
	}
}
