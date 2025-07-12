package com.rtoms.user_management.user_management_service.service.impl;

import com.rtoms.user_management.user_management_service.dto.KeycloakUser;
import com.rtoms.user_management.user_management_service.service.UserService;
import com.rtoms.user_management.user_management_service.util.CustomRestClient;
import com.rtoms.user_management.user_management_service.util.TokenProvider;
import com.rtoms.user_management.user_management_service.util.UserServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

;

/**
 * @author Manoj Krushna Mohanta
 * 07-07-2025 00:01
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${keycloak.base-url}")
    private String baseUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomRestClient customRestClient;

    private HttpHeaders getHeaders() {
        return customRestClient.buildRequest(null).getHeaders();
    }

    @Override
    public Map<String, Object> createUser(KeycloakUser keycloakUser) {
        String url = baseUrl + "/admin/realms/" + realm + "/users";
        Map<String, Object> userDetails;
        try {
            HttpEntity<KeycloakUser> request = new HttpEntity<>(keycloakUser, getHeaders());

            // Create user in Keycloak
            restTemplate.postForEntity(url, request, Void.class);

            String userId = findUserId(keycloakUser.getUsername());

            // Update password and assign role
            updatePassword(userId, keycloakUser.getCredentials().get(0).getValue());
            assignRole(userId, "CUSTOMER");

            // Fetch full user details
            userDetails = getUserDetails(keycloakUser.getUsername());
            userDetails.put("message", "✅ User created successfully!!");
        } catch (RestClientException ex) {
            logger.error("Error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to create user", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
        return userDetails;
    }

    @Override
    public String findUserId(String username) {
        String url = baseUrl + "/admin/realms/" + realm + "/users?username=" + username;
        try {
            ResponseEntity<List> response = customRestClient.get(url, List.class);
            if (response.getBody() != null && !response.getBody().isEmpty()) {
                Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
                return user.get("id").toString();
            }
            return StringUtils.EMPTY;
        } catch (RestClientException ex) {
            logger.error("Error finding user ID: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to find user ID", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }

    @Override
    public String updatePassword(String userId, String password) {
        String url = baseUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";
        Map<String, Object> payload = Map.of(
                "type", "password",
                "value", password,
                "temporary", false
        );
        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, getHeaders());
            restTemplate.put(url, request);

            return "✅ Password updated successfully for user ID: " + userId;
        } catch (RestClientException ex) {
            logger.error("Error updating password: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to update password", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }

    @Override
    public String assignRole(String userId, String roleName) {
        String roleUrl = baseUrl + "/admin/realms/" + realm + "/roles/" + roleName;
        String mappingUrl = baseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

        try {
            // Step 1: Fetch full role representation
            ResponseEntity<Map> roleResponse = restTemplate.exchange(
                    roleUrl, HttpMethod.GET, new HttpEntity<>(getHeaders()), Map.class
            );
            Map<String, Object> role = roleResponse.getBody();

            if (role == null || role.get("id") == null) {
                throw new UserServiceException("❌ Role '" + roleName + "' not found in realm '" + realm + "'");
            }

            // Step 2: Check if role is already assigned
            ResponseEntity<List> assignedRolesResponse = restTemplate.exchange(
                    mappingUrl, HttpMethod.GET, new HttpEntity<>(getHeaders()), List.class
            );
            List<Map<String, Object>> assignedRoles = assignedRolesResponse.getBody();

            boolean alreadyAssigned = assignedRoles != null && assignedRoles.stream()
                    .anyMatch(r -> roleName.equals(r.get("name")));

            if (alreadyAssigned) {
                throw new UserServiceException("⚠️ Role '" + roleName + "' is already assigned to user '" + userId + "'");
            }

            // Step 3: Assign the role
            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(role), getHeaders());
            restTemplate.postForEntity(mappingUrl, request, Void.class);

            logger.info("✅ Role '{}' assigned successfully to user '{}'", roleName, userId);
            return "✅ Role '" + roleName + "' assigned successfully to user '" + userId + "'";
        } catch (RestClientException ex) {
            logger.error("Error assigning role: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to assign role", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }

    @Override
    public Map<String, Object> getUserDetails(String username) {
        String url = baseUrl + "/admin/realms/" + realm + "/users?username=" + username;
        try {
            HttpEntity<Void> request = new HttpEntity<>(getHeaders());
            ResponseEntity<List> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, List.class
            );
            List<Map<String, Object>> users = response.getBody();

            if (users == null || users.isEmpty()) {
                throw new UserServiceException("User '" + username + "' not found in realm '" + realm + "'");
            }

            Map<String, Object> user = users.get(0);
            String userId = (String) user.get("id");

            // Fetch realm roles
            String roleUrl = baseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
            ResponseEntity<List> roleResponse = customRestClient.get(roleUrl, List.class);
            List<Map<String, Object>> roles = roleResponse.getBody();

            // Add roles to user map
            user.put("roles", roles != null ? roles : List.of());
            return user;
        } catch (RestClientException ex) {
            logger.error("Error getting user details: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to get user details", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }

    @Override
    public String revokeRole(String userId, String roleName) {
        String roleUrl = baseUrl + "/admin/realms/" + realm + "/roles/" + roleName;
        String mappingUrl = baseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

        try {
            // Step 1: Fetch full role representation
            ResponseEntity<Map> roleResponse = customRestClient.get(roleUrl, Map.class);
            Map<String, Object> role = roleResponse.getBody();

            if (role == null || role.get("id") == null) {
                throw new UserServiceException("❌ Role '" + roleName + "' not found in realm '" + realm + "'");
            }

            // Step 2: Check if role is assigned to user
            ResponseEntity<List> assignedRolesResponse = customRestClient.get(mappingUrl, List.class);
            List<Map<String, Object>> assignedRoles = assignedRolesResponse.getBody();

            boolean isAssigned = assignedRoles != null && assignedRoles.stream()
                    .anyMatch(r -> roleName.equals(r.get("name")));

            if (!isAssigned) {
                throw new UserServiceException("⚠️ Role '" + roleName + "' is not assigned to user '" + userId + "'");
            }

            // Step 3: Revoke the role
            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(role), getHeaders());
            restTemplate.exchange(mappingUrl, HttpMethod.DELETE, request, Void.class);

            logger.info("✅ Role '{}' revoked successfully from user '{}'", roleName, userId);
            return "✅ Role '" + roleName + "' revoked successfully from user '" + userId + "'";

        } catch (RestClientException ex) {
            logger.error("Error revoking role: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to revoke role", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }

    @Override
    public String updateUserStatus(String userId, boolean enabled) {
        String url = baseUrl + "/admin/realms/" + realm + "/users/" + userId;

        try {
            // Step 1: Fetch current user representation
            ResponseEntity<Map> userResponse = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Map.class
            );
            Map<String, Object> user = userResponse.getBody();

            if (user == null || user.get("id") == null) {
                throw new UserServiceException("❌ User with ID '" + userId + "' not found in realm '" + realm + "'");
            }

            // Step 2: Update the 'enabled' field
            user.put("enabled", enabled);

            // Step 3: Send the updated user object back to Keycloak
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, getHeaders());
            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

            String status = enabled ? "enabled" : "disabled";
            logger.info("✅ User '{}' status updated successfully to '{}'", userId, status);
            return "✅ User '" + userId + "' status updated successfully to '" + status + "'";

        } catch (RestClientException ex) {
            logger.error("Error updating user status: {}", ex.getMessage(), ex);
            throw new UserServiceException("Failed to update user status", ex);
        } catch (UserServiceException ex) {
            logger.error("UserServiceException: {}", ex.getMessage(), ex);
            throw ex;
        } catch ( Exception ex) {
            logger.error("Unexpected error creating user: {}", ex.getMessage(), ex);
            throw new UserServiceException("Unexpected error creating user", ex);
        }
    }
}
