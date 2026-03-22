package com.marco.torres.user_service.dto;

import java.time.LocalDateTime;

import com.marco.torres.user_service.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Response {

    private Long id;
    private String email;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
