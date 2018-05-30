package com.example.mtls.client;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

public class MtlsHttpClientBuilder {

    public static HttpClient build(MtlsConfiguration config) {
        SSLConnectionSocketFactory csf = buildSocketFactory(config);
        return HttpClients.custom()
                          .setSSLSocketFactory(csf)
                          .build();
    }

    private static SSLConnectionSocketFactory buildSocketFactory(MtlsConfiguration config) {
        SSLContext sslContext = buildSSLContext(config);
        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }

    private static SSLContext buildSSLContext(MtlsConfiguration config) {
        KeyStore keyStore = loadJksKeyStore(config.getKeystore().getValue(), config.getKeystore().getPassword());
        KeyStore trustStore = loadJksKeyStore(config.getTruststore().getValue(), config.getTruststore().getPassword());

        return buildSSLContext(keyStore, trustStore,config.getKeystore().getPassword().toCharArray());
    }

    private static SSLContext buildSSLContext(KeyStore keyStore, KeyStore trustStore, char[] keyPassword) {
        try {
            return SSLContexts.custom()
                              .loadTrustMaterial(trustStore, TrustSelfSignedStrategy.INSTANCE)
                              .loadKeyMaterial(keyStore, keyPassword)
                              .build();
        } catch (NoSuchAlgorithmException
                | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new RuntimeException("Cloud not setup SSLContext", e);
        }
    }

    private static KeyStore loadJksKeyStore(String based64Value, String password) {
        byte[] jksFile = Base64.getMimeDecoder()
                               .decode(based64Value);

        return loadJksKeyStore(new ByteArrayInputStream(jksFile), password);
    }

    private static KeyStore loadJksKeyStore(InputStream inputStream, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] passwordAsCharArray = password == null ? null : password.toCharArray();
            keyStore.load(inputStream, passwordAsCharArray);
            return keyStore;
        } catch (NoSuchAlgorithmException
                | CertificateException
                | KeyStoreException
                | IOException e) {
            throw new RuntimeException("Unable to load JKS KeyStore", e);
        }
    }
}
