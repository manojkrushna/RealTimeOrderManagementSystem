package com.rtoms.user_management.user_management_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Manoj Krushna Mohanta
 * 07-07-2025 00:17
 */

@Component
public class CustomRestClient {

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private  TokenProvider tokenProvider;

    public HttpEntity<?> buildRequest(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenProvider.getAdminToken());
        return new HttpEntity<>(body, headers);
    }

    public <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                buildRequest(body),
                responseType
        );
    }

    public <T> ResponseEntity<T> delete(String url, Object body, Class<T> responseType) {
        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                buildRequest(body),
                responseType
        );
    }

    public <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                buildRequest(body),
                responseType
        );
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        HttpEntity<?> request = buildRequest(null);
        return restTemplate.exchange(url, HttpMethod.GET, request, responseType);
    }
}
