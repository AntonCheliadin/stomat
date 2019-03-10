package com.stomat.api.user;

import com.stomat.UtilsTest;
import com.stomat.api.user.auth.MockMvcTestPrototype;
import com.stomat.api.user.auth.WithMockCustomUser;
import com.stomat.transfer.user.UserAccountDto;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAccountApiControllerTest extends MockMvcTestPrototype {

    @Test
    @WithMockCustomUser()
    public void getUserDataTest() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"password\":\"password\",\"email\":\"email@email.com\",\"active\":true,\"activationDate\":null,\"activationCode\":null,\"doctor\":null,\"roles\":[\"USER\"],\"enabled\":true,\"authorities\":[{\"authority\":\"ROLE_USER\"}],\"username\":\"email@email.com\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }

    @Test
    @WithMockCustomUser()
    public void updateUserDataTest() throws Exception {
        UserAccountDto userDto = new UserAccountDto() {{
            setFirstName("firstUpdated");
            setLastName("lastUpdated");
            setEmail("emailUpdated@email.com");
        }};

        MvcResult mvcResult = mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post("/api/account/update")
                                .accept(UtilsTest.APPLICATION_JSON_UTF8).content(UtilsTest.convertObjectToJsonBytes(userDto))
                                .contentType(UtilsTest.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":1,\"firstName\":\"updateFirst\",\"lastName\":\"updatedLast\",\"password\":\"password\",\"email\":\"updatedEmail@email.com\",\"active\":true,\"activationDate\":null,\"activationCode\":null,\"doctor\":null,\"roles\":[\"USER\"],\"enabled\":true,\"authorities\":[{\"authority\":\"ROLE_USER\"}],\"username\":\"email@email.com\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }


}
