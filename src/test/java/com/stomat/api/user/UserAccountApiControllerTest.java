package com.stomat.api.user;

import com.stomat.api.user.auth.unit.WithMockCustomUser;
import com.stomat.common.UtilsTest;
import com.stomat.common.integration.MockMvcTestPrototype;
import com.stomat.common.integration.user.MockDbUser;
import com.stomat.domain.user.UserAccount;
import com.stomat.repository.user.UserRepository;
import com.stomat.transfer.user.UserAccountDto;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAccountApiControllerTest extends MockMvcTestPrototype {

    @Autowired
    UserRepository userRepository;

    @Test
    @WithMockCustomUser()
    public void getUserDataTest() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email@email.com\",\"doctors\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void unauthorizedErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @MockDbUser(email = "updateUserDataTest@mail.com")
    public void updateUserDataTest() throws Exception {
        //given
        UserAccount userAccount = userRepository.findByEmail("updateUserDataTest@mail.com").orElseThrow();

        //when
        UserAccountDto userDto = new UserAccountDto() {{
            setId(userAccount.getId());
            setFirstName("firstUpdated");
            setLastName("lastUpdated");
            setEmail("emailUpdated@email.com");
        }};

        //then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/account/update")
                        .accept(UtilsTest.APPLICATION_JSON_UTF8).content(UtilsTest.convertObjectToJsonBytes(userDto))
                        .contentType(UtilsTest.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());


        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":" + userAccount.getId() + ",\"firstName\":\"firstUpdated\",\"lastName\":\"lastUpdated\",\"email\":\"updateUserDataTest@mail.com\",\"doctors\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }

    @Test
    @MockDbUser(email = "getWeekScheduleTestUser@email.com")
    public void updateEmailToExistedOneTest() throws Exception {
        //when UserAccountDto is invalid, for example first name is empty
        UserAccountDto userAccountDto = new UserAccountDto() {{
            setFirstName("");
            setLastName("lastName");
            setEmail("updateEmailToExistedOneTest@test.com");
        }};

        //then response has Bad request status
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/account/update")
                        .accept(UtilsTest.APPLICATION_JSON_UTF8)
                        .content(UtilsTest.convertObjectToJsonBytes(userAccountDto))
                        .contentType(UtilsTest.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }
}
