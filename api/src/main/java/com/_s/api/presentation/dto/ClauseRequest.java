package com._s.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClauseRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String mainText;

    private String[] paragraphs;
}
