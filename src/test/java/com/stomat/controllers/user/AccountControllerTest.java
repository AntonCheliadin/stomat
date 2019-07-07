package com.stomat.controllers.user;

import com.stomat.common.integration.MockMvcTestPrototype;
import com.stomat.common.integration.user.MockDbUser;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends MockMvcTestPrototype {

    @Test
    public void redirectToLoginOnUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @MockDbUser()
    public void authorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(status().isOk());
    }
}
