package com.stomat.api.booking;

import com.stomat.api.MockMvcTestPrototype;
import com.stomat.api.user.auth.integration.MockDbUser;
import com.stomat.domain.booking.Booking;
import com.stomat.domain.booking.Patient;
import com.stomat.domain.booking.Reason;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.booking.BookingRepository;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerBookingApiControllerListTest extends MockMvcTestPrototype {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private Doctor doctor;

    @Before
    public void setUpEntities() {
        Doctor doctor = new Doctor("first", "fathers", "last", "apiBookingDoctor@email.com");
        this.doctor = doctorRepository.save(doctor);
    }

    @Test
    public void whenNoUser_thenUnauthorized() throws Exception {
        mockMvc.
                perform(MockMvcRequestBuilders.get("/api/booking/manager/list"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @MockDbUser()
    public void whenUserEitherManagerNorDoctor_thenForbidden() throws Exception {
        mockMvc.
                perform(MockMvcRequestBuilders.get("/api/booking/manager/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @MockDbUser(roles = "MANAGER")
    public void whenNoParams_thenBadRequest() throws Exception {
        mockMvc.
                perform(MockMvcRequestBuilders.get("/api/booking/manager/list"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @MockDbUser(roles = "MANAGER")
    public void whenUserNotIsManagerOrDoctor_thenForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/booking/manager/list")
                        .param("doctor", doctor.getId().toString())
                        .param("from", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                        .param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isForbidden());
    }

    @Test
    @MockDbUser(email = "apiBookingManagerTest@email.com", roles = "MANAGER")
    public void whenAllParamsValid_thenOk() throws Exception {
        //when
        addUserToManager("apiBookingManagerTest@email.com");

        //then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/booking/manager/list")
                        .param("doctor", doctor.getId().toString())
                        .param("from", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                        .param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isOk());
    }

    @Test
    @MockDbUser(email = "apiBookingListTest@email.com", roles = "MANAGER")
    public void whenBookingExists_thenBookingInResponse() throws Exception {
        //when
        addUserToManager("apiBookingListTest@email.com");
        var booking = makeBooking();

        //then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/booking/manager/list")
                        .param("doctor", doctor.getId().toString())
                        .param("from", booking.getStartDate().format(DateTimeFormatter.ISO_DATE))
                        .param("to", booking.getStartDate().plusDays(1).format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(booking.getId().intValue())));
    }

    private void addUserToManager(String email) {
        UserAccount userAccount = userRepository.findByEmail(email).orElseThrow();
        doctor.setManagers(Set.of(userAccount));
        doctorRepository.save(doctor);
    }

    private Booking makeBooking() {
        var testPatient = new Patient("testFirst", "testLast", "+123456789098");
        var testReason = new Reason("testReason", 60);

        var now = LocalDateTime.now();
        var bookingStart = now.plusDays(1).withHour(9).withMinute(0);
        var bookingEnd = now.plusDays(1).withHour(10).withMinute(0);

        var booking = new Booking(testPatient, doctor, testReason, bookingStart, bookingEnd, "testBooking");

        return bookingRepository.save(booking);
    }

}
