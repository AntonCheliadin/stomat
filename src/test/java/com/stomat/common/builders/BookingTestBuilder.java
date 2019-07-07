package com.stomat.common.builders;

import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;

import java.time.LocalDateTime;

import static com.stomat.common.UtilsTest.tomorrowAt;

public class BookingTestBuilder {

    public static Reason makeReason(int duration) {
        return new Reason("testReason", duration);
    }

    public static Booking makeBookingTomorrowAt(int startH, int endH, Doctor doctor, int reasonLength) {
        var testPatient = makePatient();
        var testReason = makeReason(reasonLength);
        var bookingStart = tomorrowAt(startH, 0);
        var bookingEnd = tomorrowAt(endH, 0);

        return new Booking(testPatient, doctor, testReason, bookingStart, bookingEnd, "testBooking");
    }

    public static Booking makeBooking(LocalDateTime start, LocalDateTime end, Doctor doctor, int reasonLength) {
        var testPatient = makePatient();
        var testReason = makeReason(reasonLength);

        return new Booking(testPatient, doctor, testReason, start, end, "testBooking");
    }

    public static Booking makeBooking(LocalDateTime start, LocalDateTime end, Doctor doctor, Reason reason) {
        var testPatient = makePatient();

        return new Booking(testPatient, doctor, reason, start, end, "testBooking");
    }

    public static Booking makeBooking(LocalDateTime start, LocalDateTime end, Doctor doctor, Reason reason, Patient patient) {
        return new Booking(patient, doctor, reason, start, end, "testBooking");
    }

    public static Patient makePatient() {
        return new Patient("testFirst", "testLast", "+123456789098");
    }
}
