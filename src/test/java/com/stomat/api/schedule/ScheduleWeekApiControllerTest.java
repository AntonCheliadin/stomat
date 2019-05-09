package com.stomat.api.schedule;

import com.stomat.api.user.auth.MockMvcTestPrototype;
import com.stomat.api.user.auth.WithMockCustomUser;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScheduleWeekApiControllerTest extends MockMvcTestPrototype {

    final String TEST_DOCTOR_ID = "12345678901";

    @Test
    @WithMockCustomUser()
    @Sql({"/create-doctor-before.sql"}) //todo: script doesn't run
    public void getEmptyWeekScheduleTest() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/schedule/week")
                        .param("doctor", TEST_DOCTOR_ID))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "[]";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }
}
