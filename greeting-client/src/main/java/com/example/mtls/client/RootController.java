package com.example.mtls.client;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

@RestController
public class RootController {

    private final RestTemplate restTemplate;

    @Autowired
    public RootController(MtlsConfiguration configuration) throws Exception {

        HttpClient httpClient = MtlsHttpClientBuilder.build(configuration);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restTemplate =  new RestTemplate(requestFactory);
    }

    @GetMapping("/")
    public String homePage()
    {
        String result = this.restTemplate.getForObject("https://localhost:8443/greet/adib", String.class);
        return result;
    }
}
