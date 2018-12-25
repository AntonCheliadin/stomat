package com.stomat.transfer.user;

import com.stomat.transfer.Create;
import com.stomat.transfer.Update;
import com.stomat.validation.common.FieldsValueMatchConstraint;
import com.stomat.validation.user.UniqueUserEmailConstraint;

import javax.validation.constraints.*;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
@FieldsValueMatchConstraint.List({
        @FieldsValueMatchConstraint(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!",//todo: i18n
                groups = {Create.class}
        )
})
public class UserAccountDto {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    private String firstName;

    @NotBlank(groups = {Create.class, Update.class})
    private String lastName;

    @UniqueUserEmailConstraint(groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    private String email;

    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class}, min = 6, max = 20)
    private String password;

    @NotBlank(groups = {Create.class})
    private String verifyPassword;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
