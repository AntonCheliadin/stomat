package com.stomat.transfer.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
public class PeriodDto {

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date from;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}