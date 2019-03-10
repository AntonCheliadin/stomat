package com.stomat.api.user.auth;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public abstract class MockMvcTestPrototype {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
}
