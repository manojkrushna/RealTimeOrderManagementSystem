package com.rtoms.user_management.user_management_service.service;

import com.rtoms.user_management.user_management_service.dto.KeycloakUser;

import java.util.Map;

/**
 * @author Manoj Krushna Mohanta
 * 06-07-2025 23:53
 */
public interface UserService {

    Map<String, Object> createUser(KeycloakUser keycloakUser);

    String findUserId(String username);

    String updatePassword(String userId, String password);

    String assignRole(String userId, String roleName);

    Map<String, Object> getUserDetails(String username);

    String revokeRole(String userId, String roleName);

    String updateUserStatus(String userId, boolean enabled);
}
