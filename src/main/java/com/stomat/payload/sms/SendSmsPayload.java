package com.stomat.payload.sms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SendSmsPayload {

    @Pattern(regexp = "^\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}$")
    private String recipient;

    @NotBlank
    private String message;

    public String getPhone() {
        return recipient.replaceAll("[ \\(\\)\\-]", "");
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
