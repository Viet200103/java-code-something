package com.javacodesomething.spring.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public static class Auth {

        private String tokenSecret;

        private long tokenExpirationTime;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationTime() {
            return tokenExpirationTime;
        }

        public void setTokenExpirationTime(long tokenExpirationTime) {
            this.tokenExpirationTime = tokenExpirationTime;
        }
    }

    public static class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();


        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
}
