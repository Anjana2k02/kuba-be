package com.kuba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create User Request DTO")
public class CreateUserRequestDto {
    @Schema(description = "User email", example = "john@example.com")
    private String email;

    @Schema(description = "User name", example = "John Doe")
    private String name;

    @Schema(description = "User password", example = "password123")
    private String password;
}
