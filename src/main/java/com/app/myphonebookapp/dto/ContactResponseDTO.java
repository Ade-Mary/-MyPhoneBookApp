package com.app.myphonebookapp.dto;



import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String contactImage;
    private String physicalAddress;
    private String group;
    private boolean isFavorite;
}
