package com.qwerty.mircoservices.userservice.security;


import com.qwerty.mircoservices.userservice.domain.Role;
import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.service.securityServices.TokenBlackListService;
import com.qwerty.mircoservices.userservice.service.serviceImpl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.apache.log4j.Logger;


@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private Logger logger = Logger.getLogger(AuthorizationConfig.class);

    private int accessTokenValiditySeconds = 10000;
    private int refreshTokenValiditySeconds = 30000;
    private AuthenticationManager authenticationManager;
    private TokenBlackListService blackListService;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailService();
    }


    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setTokenBlackListService(TokenBlackListService blackListService) {
        this.blackListService = blackListService;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer
                // we're allowing access to the token only for clients with 'ROLE_TRUSTED_CLIENT' authority
                .tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()

                .withClient("trusted-app")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .authorities(Role.ROLE_TRUSTED_CLIENT.toString())
                .scopes("read", "write")
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(10)
                .refreshTokenValiditySeconds(30000)
                .secret("secret")
                .and()
                .withClient("register-app")
                .authorizedGrantTypes("client_credentials")
                .authorities(Role.ROLE_REGISTER.toString())
                .scopes("registerUser")
                .accessTokenValiditySeconds(10)
                .refreshTokenValiditySeconds(10)
                .resourceIds(resourceId)
                .secret("secret");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Value("${security.signing-key}")
    private String signingKey;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(signingKey);
        converter.setSigningKey(signingKey);
        return converter;
    }
   /* @Bean
    @Qualifier("jwtAccessTokenConverter")
    protected JwtAccessTokenConverter jwtTokenEnhancer() throws Exception{

        JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("mypublic.pem");
        String publicKey = null;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }*/

  /*  @Bean
    public AuthorizationServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setAccessTokenValiditySeconds(-1);

        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
*/
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        MyTokenService tokenService = new MyTokenService(blackListService);
        tokenService.setTokenStore(tokenStore());
        tokenService.setSupportRefreshToken(true);
        tokenService.setTokenEnhancer(accessTokenConverter());
        return tokenService;
    }


    static class MyTokenService extends DefaultTokenServices {
        Logger logger = Logger.getLogger(MyTokenService.class);

        private TokenBlackListService blackListService;

        public MyTokenService(TokenBlackListService blackListService) {
            this.blackListService = blackListService;
        }

        @Override
        public OAuth2AccessToken readAccessToken(String accessToken) {
            return super.readAccessToken(accessToken);
        }



        @Override
        public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
            OAuth2AccessToken token = super.createAccessToken(authentication);
            User user = (User) authentication.getPrincipal();
            String jti = (String) token.getAdditionalInformation().get("jti");

            blackListService.addToEnabledList(
                    user.getId(),
                    jti,
                    token.getExpiration().getTime() );
            return token;
        }

        @Override
        public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) throws AuthenticationException {
            logger.info("refresh token:" + refreshTokenValue);
            String jti = tokenRequest.getRequestParameters().get("jti");
            try {
                if ( jti != null )
                    if ( blackListService.isBlackListed(jti) ) return null;


                OAuth2AccessToken token = super.refreshAccessToken(refreshTokenValue, tokenRequest);
                blackListService.addToBlackList(jti);
                return token;
            } catch (TokenBlackListService.TokenNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
