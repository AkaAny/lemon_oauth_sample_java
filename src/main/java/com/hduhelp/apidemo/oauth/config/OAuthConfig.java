package com.hduhelp.apidemo.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "oauth")
public class OAuthConfig {
    private String host;

    private String clientID;

    private String clientSecret;

    private String callbackUrl;

    private String callbackRedirectUrl;

    public String getHost(){
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackRedirectUrl() {
        return callbackRedirectUrl;
    }

    public void setCallbackRedirectUrl(String callbackRedirectUrl) {
        this.callbackRedirectUrl = callbackRedirectUrl;
    }
}
