package com.rtoms.user_management.user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manoj Krushna Mohanta
 * 06-07-2025 23:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    private String type = "password";
    private String value;
    private boolean temporary = false;
}