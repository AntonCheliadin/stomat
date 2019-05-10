package com.stomat.transfer.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

import static com.stomat.constants.DateFormat.API_DATE;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
public class PeriodDto {

    @NotNull
    private Long doctor;

    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }
}
