package com.stomat.domain.booking;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.domain.user.UserAccount;
import com.stomat.transfer.views.Views;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Anton Chelyadin.
 * @since 29.12.18.
 */
@Entity
public class Patient {

    public Patient() {
    }

    public Patient(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.BookingsView.class)
    private Long id;

    @NotBlank
    @NotNull
    @JsonView(Views.BookingsView.class)
    private String firstName;

    @NotBlank
    @NotNull
    @JsonView(Views.BookingsView.class)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^(\\+)?[0-9]{7,12}")
    @JsonView(Views.BookingsView.class)
    private String phone;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
