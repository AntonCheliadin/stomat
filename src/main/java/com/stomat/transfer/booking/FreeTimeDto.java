package com.stomat.transfer.booking;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class FreeTimeDto {

    public FreeTimeDto(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime from;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime to;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "FreeTimeDto{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreeTimeDto that = (FreeTimeDto) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
