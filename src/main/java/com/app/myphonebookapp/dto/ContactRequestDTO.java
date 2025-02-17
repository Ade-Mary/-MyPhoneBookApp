package com.app.myphonebookapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
public class ContactRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    private String contactImage;
    private String physicalAddress;
    private String group;
    private boolean isFavorite;
}
