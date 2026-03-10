package com.kuba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Response DTO")
public class UserResponseDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User email", example = "john@example.com")
    private String email;

    @Schema(description = "User name", example = "John Doe")
    private String name;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp")
    private LocalDateTime updatedAt;
}
