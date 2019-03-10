package com.stomat.api.user;

import com.stomat.UtilsTest;
import com.stomat.api.user.auth.MockMvcTestPrototype;
import com.stomat.api.user.auth.WithMockCustomUser;
import com.stomat.repository.user.UserRepository;
import com.stomat.services.user.UserService;
import com.stomat.transfer.user.UserAccountDto;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAccountApiControllerTest extends MockMvcTestPrototype {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    UserRepository mockUserRepository;

    @Before
    public void setUpMockRepo() {
        userService = new UserService(mockUserRepository, passwordEncoder, null);
    }

    @Test
    @WithMockCustomUser()
    public void getUserDataTest() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email@email.com\",\"doctor\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void unauthorizedErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser()
    public void updateUserDataTest() throws Exception {
        UserAccountDto userDto = new UserAccountDto() {{
            setFirstName("firstUpdated");
            setLastName("lastUpdated");
            setEmail("emailUpdated@email.com");
        }};

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

        String expected = "{\"id\":1,\"firstName\":\"firstUpdated\",\"lastName\":\"lastUpdated\",\"email\":\"emailUpdated@email.com\",\"doctor\":null}";

        JSONAssert.assertEquals(expected, mvcResult.getResponse()
                .getContentAsString(), false);
    }
}
