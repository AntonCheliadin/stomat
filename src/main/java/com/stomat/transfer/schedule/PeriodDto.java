package com.stomat.transfer.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.stomat.constants.DateFormat.API_DAY;

/**
 * @author Anton Chelyadin.
 * @since 03.01.19.
 */
public class PeriodDto {

    @NotNull
    @DateTimeFormat(pattern = API_DAY)
    private Date from;

    @NotNull
    @DateTimeFormat(pattern = API_DAY)
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
