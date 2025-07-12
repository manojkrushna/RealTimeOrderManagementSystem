package com.rtoms.user_management.user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Manoj Krushna Mohanta
 * 06-07-2025 23:41
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUser {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled = true;
    private List<Credential> credentials;
}
