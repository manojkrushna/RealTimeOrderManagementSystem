package com.rtoms.product.product_service.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 23:04
 */

class SecurityConfigTest {

    @Test
    void testJwtAuthenticationConverterBean() {
        SecurityConfig config = new SecurityConfig();
        JwtAuthenticationConverter converter = config.jwtAuthenticationConverter();
        assertNotNull(converter);
    }

    @Test
    void testFilterChainBean() throws Exception {
        SecurityConfig config = new SecurityConfig();
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        // Just verify that the method does not throw and returns a SecurityFilterChain
        SecurityFilterChain chain = config.filterChain(http);
        assertNotNull(chain);
    }


    @Test
    void testJwtGrantedAuthoritiesConverterWithRoles() {
        SecurityConfig config = new SecurityConfig();
        JwtAuthenticationConverter converter = config.jwtAuthenticationConverter();

        Map<String, Object> claims = new HashMap<>();
        claims.put("realm_access", Map.of("roles", List.of("ADMIN", "USER")));

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaim("realm_access")).thenReturn(claims.get("realm_access"));

        Collection<? extends GrantedAuthority> authorities =
                converter.convert(jwt).getAuthorities();

        assertNotNull(authorities);
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testJwtGrantedAuthoritiesConverterWithoutRoles() {
        SecurityConfig config = new SecurityConfig();
        JwtAuthenticationConverter converter = config.jwtAuthenticationConverter();

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaim("realm_access")).thenReturn(null);

        Collection<? extends GrantedAuthority> authorities =
                converter.convert(jwt).getAuthorities();

        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }
}
