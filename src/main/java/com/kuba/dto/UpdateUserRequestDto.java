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
@Schema(description = "Update User Request DTO")
public class UpdateUserRequestDto {
    @Schema(description = "User name", example = "Jane Doe")
    private String name;

    @Schema(description = "User password (optional)", example = "newpassword123")
    private String password;
}
