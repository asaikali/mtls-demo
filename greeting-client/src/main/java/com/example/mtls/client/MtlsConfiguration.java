package com.example.mtls.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mtls")
public class MtlsConfiguration {

    private KeyStore keystore;
    private TrustStore truststore;

    public static class KeyStore {
        private String password;
        private String value;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TrustStore {
        private String password;
        private String value;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public KeyStore getKeystore() {
        return keystore;
    }

    public void setKeystore(KeyStore keystore) {
        this.keystore = keystore;
    }

    public TrustStore getTruststore() {
        return truststore;
    }

    public void setTruststore(TrustStore truststore) {
        this.truststore = truststore;
    }
}
