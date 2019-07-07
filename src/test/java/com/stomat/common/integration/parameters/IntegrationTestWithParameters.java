package com.stomat.common.integration.parameters;

import junitparams.JUnitParamsRunner;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

/**
 * Workaround for integration test using JUnitParams.
 * https://www.javacodegeeks.com/2015/08/parameterized-integration-tests-with-spring-junit-rules.html
 */
@RunWith(JUnitParamsRunner.class)
public class IntegrationTestWithParameters {

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
