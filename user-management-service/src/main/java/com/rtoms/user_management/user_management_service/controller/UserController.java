package com.rtoms.user_management.user_management_service.controller;

import com.rtoms.user_management.user_management_service.dto.KeycloakUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Manoj Krushna Mohanta
 * 06-07-2025 23:44
 */

@RequestMapping("/v1/users")
public interface UserController {

    @PostMapping("/create")
    ResponseEntity<?> createUser(@RequestBody KeycloakUser keycloakUser);

    @GetMapping("/{username}")
    ResponseEntity<?> getUserDetails(@PathVariable("username") String username);

    @PutMapping("/{userId}/role/{roleName}")
    ResponseEntity<?> assignRole(@PathVariable("userId") String userId, @PathVariable("roleName") String roleName);

    @DeleteMapping("/{userId}/role/{roleName}")
    ResponseEntity<?> revokeRole(@PathVariable("userId") String userId, @PathVariable("roleName") String roleName);

    @PutMapping("/{userId}/status")
    ResponseEntity<?> updateUserStatus(@PathVariable("userId") String userId, @RequestParam("enabled") boolean enabled);

    @PutMapping("/{userId}/updatePassword")
    ResponseEntity<?> updatePassword(@PathVariable("userId") String userId, @RequestParam("password") String password);
}