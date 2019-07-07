package com.stomat.services.booking;

import com.stomat.common.builders.ScheduleTestBuilder;
import com.stomat.common.integration.parameters.IntegrationTestWithParameters;
import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.booking.ReasonRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.transfer.booking.BookingDto;
import junit.framework.TestCase;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static com.stomat.common.UtilsTest.tomorrowAt;

@SpringBootTest
@Transactional
public class BookingServiceTest extends IntegrationTestWithParameters {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ReasonRepository reasonRepository;

    private Doctor doctor;
    private Reason reason;

    @Before
    public void setUpEntities() {
        var doctor = new Doctor("first", "fathers", "last", "bookingServiceTestDoctor@email.com");
        this.doctor = doctorRepository.save(doctor);

        var reason = new Reason("testReason", 60);
        this.reason = reasonRepository.save(reason);
    }

    private Object[] parametersToTestHasFreeTimeByScheduleWeek() {
        return new Object[]{
                new Object[]{8, 0, 9, 0, false},
                new Object[]{9, 0, 10, 0, true},
                new Object[]{10, 0, 11, 0, false},
        };
    }

    @Test
    @Parameters(method = "parametersToTestHasFreeTimeByScheduleWeek")
    public void hasFreeTimeByScheduleWeek(int startH, int startM, int endH, int endM, boolean expectedResult) {
        //given week schedule on tomorrow [09:00 - 10:00]
        ScheduleTestBuilder.addWeekScheduleToDoctor(tomorrowAt(9, 0).getDayOfWeek(),
                LocalTime.of(9, 0), LocalTime.of(10, 0), doctor);

        doctorRepository.save(doctor);

        //when create booking tomorrow at [startH:startM] - [endH:endM]
        BookingDto bookingDto = new BookingDto() {{
            setDoctor(doctor.getId());
            setReason(reason.getId());
            setStartDate(tomorrowAt(startH, startM));
            setEndDate(tomorrowAt(endH, endM));
            setFirstName("testFirst");
            setLastName("testFirst");
            setPhoneNumber("+123456789098");
        }};

        boolean hasFreeTime = bookingService.hasFreeTime(bookingDto, doctor, reason);

        //then
        TestCase.assertEquals(expectedResult, hasFreeTime);
    }
}