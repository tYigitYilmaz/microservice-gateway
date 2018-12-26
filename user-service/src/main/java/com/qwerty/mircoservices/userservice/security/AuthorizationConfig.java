package com.qwerty.mircoservices.userservice.security;


import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.service.serviceImpl.TokenBlackListService;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private Logger logger = Logger.getLogger(AuthorizationConfig.class);

    private SecretKeyProvider keyProvider;
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
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Autowired
    public void setSecretKeyProvider(SecretKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }
    public SecretKeyProvider getSecretKeyProvider() {
        return keyProvider;
    }

    @Autowired
    public void setTokenBlackListService(TokenBlackListService blackListService) {
        this.blackListService = blackListService;
    }
    public TokenBlackListService getTokenBlackListService() {
        return blackListService;
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
        clients.inMemory()
                .withClient("normal-app")
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .and()
                .withClient("trusted-app")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .authorities("ROLE_TRUSTED_CLIENT")
                .scopes("read", "write")
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .secret("secret")
                .and()
                .withClient("register-app")
                .authorizedGrantTypes("client_credentials")
                .authorities("ROLE_REGISTER")
                .scopes("read")
                .resourceIds(resourceId)
                .secret("secret")
                .and()
                .withClient("my-client-with-registered-redirect")
                .authorizedGrantTypes("authorization_code")
                .authorities("ROLE_CLIENT")
                .scopes("read", "trust")
                .resourceIds("oauth2-resource")
                .redirectUris("http://anywhere?key=value");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        try {
            converter.setSigningKey(keyProvider.getKey());
        } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | CertificateException e) {
            e.printStackTrace();
        }

        return converter;
    }



   /* @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        try {
            converter.setSigningKey(keyProvider.getKey());
        } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | CertificateException e) {
            e.printStackTrace();
        }
        return converter;
    }*/
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