package com.stomat.validation.user;

import com.stomat.api.user.auth.integration.MockDbUser;
import com.stomat.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Anton Chelyadin.
 * @since 25.12.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("email", "testRegistration@email.com")
                .param("password", "password")
                .param("verifyPassword", "password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testFieldsValueMatchConstraint() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("email", "testRegistration@email.com")
                .param("password", "password")
                .param("verifyPassword", "passwordINVALID"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrorCode("userAccountDto", "verifyPassword", "MatchConstraint"))
                .andExpect(view().name("user/registration"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-user-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUniqueUserEmailConstraint() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("email", "testEmail1@test.com")
                .param("password", "password")
                .param("verifyPassword", "password"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrorCode("userAccountDto", "email", "UniqueUserEmailConstraint"))
                .andExpect(view().name("user/registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFieldsValueMatchConstraintConstraint() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("email", "testEmail3@test.com")
                .param("password", "password")
                .param("verifyPassword", "passwordINVALID"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrorCode("userAccountDto", "verifyPassword", "MatchConstraint"))
                .andExpect(view().name("user/registration"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-user-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("testEmail1@test.com").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void incorrectLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("testEmail@test.com").password("incorrect"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/login*"));
    }
}
