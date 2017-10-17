package product.info.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import product.info.model.User;
import product.info.service.UserService;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	UserService userService;

	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Map<String, Object> additionalInfo = new HashMap<String, Object>();

		String authorities = "";
		for (GrantedAuthority role : authentication.getAuthorities()) {
			authorities = "" + role;
		}

		User user = userService.findByUserName(authentication.getName());

		String userName = user.getFirstName() + " " + user.getLastName();

		additionalInfo.put("user", userName);
		additionalInfo.put("authorities", authorities.substring(5));

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

		return accessToken;
	}
}
