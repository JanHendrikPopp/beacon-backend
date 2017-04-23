package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.rest.response.ErrorResponse;
import com.baecon.rockpapersissersapp.service.UserService;
import com.baecon.rockpapersissersapp.util.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class UserControllerTest extends RestBaseTest {

    private static final String REGISTRATION_URL = "/api/1/registration";
    private static final String TEST_USERNAME = "username";
    private static final long TEST_USERID = 1L;

    private JacksonTester<User> json;
    private JacksonTester<ErrorResponse> errorJson;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        User user = generateDummyUser(TEST_USERID, TEST_USERNAME);
        given(this.userService.
                registerUser(TEST_USERNAME)
        ).willReturn(user);
    }

    @Test
    public void testRegistration() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("name", TEST_USERNAME);

        ResponseEntity<String> response = postRequest(parameters, REGISTRATION_URL);
        User user = json.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(user.getName().equals("username"));
        assertTrue(user.getId().equals(1L));
    }

    @Test
    public void testInvalidRegistration() throws IOException {
        ResponseEntity<String> response = postRequest(new LinkedMultiValueMap<>(), REGISTRATION_URL);
        ErrorResponse errorResponse = errorJson.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getErrorCode().equals(ErrorCodes.MISSING_PARAMETER));
    }



}
