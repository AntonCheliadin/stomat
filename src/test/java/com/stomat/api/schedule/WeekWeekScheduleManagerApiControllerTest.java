package com.stomat.api.schedule;

import com.stomat.common.integration.MockMvcTestPrototype;
import com.stomat.common.integration.user.MockDbUser;
import com.stomat.domain.profile.Doctor;
import com.stomat.domain.schedule.WeekSchedule;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.profile.DoctorRepository;
import com.stomat.repository.schedule.WeekScheduleRepository;
import com.stomat.repository.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WeekWeekScheduleManagerApiControllerTest extends MockMvcTestPrototype {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeekScheduleRepository weekScheduleRepository;

    private Doctor doctor;

    @Before
    public void setUpEntities() {
        Doctor doctor = new Doctor("first", "fathers", "last", "weekScheduleTestDoctor@email.com");
        this.doctor = doctorRepository.save(doctor);
    }

    @Test
    public void unauthorizedTest() throws Exception {
        mockMvc.
                perform(MockMvcRequestBuilders.get("/api/manage/schedule/week")
                        .param("doctor", doctor.getId().toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void accessDeniedTest() throws Exception {
        mockMvc.
                perform(MockMvcRequestBuilders.get("/api/manage/schedule/week")
                        .param("doctor", doctor.getId().toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @MockDbUser(email = "getEmptyWeekScheduleTestUser@email.com", roles = "MANAGER")
    public void getEmptyWeekScheduleTest() throws Exception {
        //given: logged in user is manager of doctor
        UserAccount userAccount = userRepository.findByEmail("getEmptyWeekScheduleTestUser@email.com").orElseThrow();
        doctor.setManagers(Set.of(userAccount));
        doctorRepository.save(doctor);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/manage/schedule/week")
                        .param("doctor", doctor.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals("[]", mvcResult.getResponse()
                .getContentAsString(), false);
    }

    @Test
    @MockDbUser(email = "getWeekScheduleTestUser@email.com", roles = "DOCTOR")
    public void getNotEmptyWeekScheduleTest() throws Exception {
        //given:
        // - logged in user is manager of doctor
        // - doctor has week schedule
        UserAccount userAccount = userRepository.findByEmail("getWeekScheduleTestUser@email.com").orElseThrow();
        WeekSchedule weekSchedule = weekScheduleRepository.save(new WeekSchedule(
                doctor,
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(16, 0)
        ));
        doctor.setWeekSchedules(Set.of(weekSchedule));
        doctor.setManagers(Set.of(userAccount));
        doctorRepository.save(doctor);

        //when: get week schedule by doctor
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/manage/schedule/week")
                        .param("doctor", doctor.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        //then: response is a list of one week schedule in json
        JSONAssert.assertEquals("[{\"id\":" + weekSchedule.getId() + ",\"dayOfWeek\":1,\"timeFrom\":\"10:00:00\",\"timeTo\":\"16:00:00\"}]", mvcResult.getResponse()
                .getContentAsString(), false);
    }
}
