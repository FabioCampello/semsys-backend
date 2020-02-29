package com.williamdsw.semsys.config;

import java.util.Arrays;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.williamdsw.semsys.security.JWTUtil;
import com.williamdsw.semsys.security.filters.JWTAuthenticationFilter;
import com.williamdsw.semsys.security.filters.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	// FIELDS
	
	private static final String[] PUBLIC_MATCHERS = 
	{
		"/h2-console/**",
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = 
	{
		"/v1/public/**"
	};
	
	public static final String[] PUBLIC_MATCHERS_POST = 
	{
		"/v1/public/**"
	};
	
	private static final String[] SWAGGER_MATCHERS_POST = 
	{
		"/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
		"/configuration/**", "/swagger-ui.html", "/webjars/**"
	};
	
	@Autowired private Environment enviroment;
	@Autowired private UserDetailsService userDetailsService;
	@Autowired private JWTUtil jwtUtil;
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public void configure (WebSecurity web) throws Exception 
	{
		web.ignoring ().antMatchers (SWAGGER_MATCHERS_POST);
	}
	
	@Override
	protected void configure (HttpSecurity http) throws Exception 
	{
		if (Arrays.asList (enviroment.getActiveProfiles ()).contains ("test"))
		{
			http.headers ().frameOptions ().disable ();
		}
		
		// Config
		http.cors ().and ().csrf ().disable ();
		http.authorizeRequests ()
			.antMatchers (HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll ()
			.antMatchers (HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll ()
			.antMatchers (PUBLIC_MATCHERS).permitAll ()
			.anyRequest ().authenticated ();
		
		http.addFilter(new JWTAuthenticationFilter (this.authenticationManager (), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter (this.authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService (userDetailsService).passwordEncoder (this.bCryptPasswordEncoder ());
	}
	
	// BEANS
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource ()
	{
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ();
		CorsConfiguration configuration = new CorsConfiguration ().applyPermitDefaultValues ();
		configuration.setAllowedMethods (Arrays.asList ("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		source.registerCorsConfiguration ("/**", configuration);
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder ()
	{
		return new BCryptPasswordEncoder ();
	}
}