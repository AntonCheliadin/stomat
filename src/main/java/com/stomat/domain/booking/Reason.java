package com.stomat.domain.booking;

import com.fasterxml.jackson.annotation.JsonView;
import com.stomat.config.locale.Translator;
import com.stomat.transfer.views.Views;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Reason {

    public Reason() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.BookingsView.class)
    private Long id;

    @NotBlank
    private String nameCode;

    @Min(1)
    private Integer duration;

    private boolean defaults;

    public String getName() {
        return Translator.localize(nameCode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public boolean isDefaults() {
        return defaults;
    }

    public void setDefaults(boolean defaults) {
        this.defaults = defaults;
    }
}
