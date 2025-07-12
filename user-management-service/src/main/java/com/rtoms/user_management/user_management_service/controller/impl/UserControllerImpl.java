package com.rtoms.user_management.user_management_service.controller.impl;

import com.rtoms.user_management.user_management_service.controller.UserController;
import com.rtoms.user_management.user_management_service.dto.KeycloakUser;
import com.rtoms.user_management.user_management_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manoj Krushna Mohanta
 * 06-07-2025 23:50
 */

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<?> createUser(KeycloakUser keycloakUser) {
        return ResponseEntity.ok(userService.createUser(keycloakUser));
    }

    @Override
    public ResponseEntity<?> getUserDetails(String username) {
        return ResponseEntity.ok(userService.getUserDetails(username));
    }

    @Override
    public ResponseEntity<?> assignRole(String userId, String roleName) {
        return ResponseEntity.ok(userService.assignRole(userId, roleName));
    }

    @Override
    public ResponseEntity<?> revokeRole(String userId, String roleName) {
        return ResponseEntity.ok(userService.revokeRole(userId, roleName));
    }

    @Override
    public ResponseEntity<?> updateUserStatus(String userId, boolean enabled) {
        return ResponseEntity.ok(userService.updateUserStatus(userId, enabled));
    }

    @Override
    public ResponseEntity<?> updatePassword(String userId, String password) {
        return ResponseEntity.ok(userService.updatePassword(userId, password));
    }
}