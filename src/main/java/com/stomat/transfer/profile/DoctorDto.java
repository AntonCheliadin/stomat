package com.stomat.transfer.profile;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public class DoctorDto {

    @Nullable
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String fathersName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
