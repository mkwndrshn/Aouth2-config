package product.info.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}
	
	/*public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/grantAllUser/**");
    }*/

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/oauth/token").permitAll();

		http.anonymous().disable()
		.authorizeRequests()
		.antMatchers("/allUsers/**").permitAll()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/user/**").access("hasRole('USER')")
		.and().exceptionHandling()
		.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
