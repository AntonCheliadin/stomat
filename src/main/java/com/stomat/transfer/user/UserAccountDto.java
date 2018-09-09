package com.stomat.transfer.user;

import com.stomat.transfer.Create;
import com.stomat.transfer.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */
public class UserAccountDto {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    private String name;

    @NotBlank(groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    private String email;

    @NotBlank(groups = {Create.class})
    private String password;

    @NotBlank(groups = {Create.class})
    private String repeatPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
