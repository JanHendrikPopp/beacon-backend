package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.model.Stats;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.rest.response.UserNotFoundErrorResponse;
import com.baecon.rockpapersissersapp.service.StatsService;
import com.baecon.rockpapersissersapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class StatsControllerTest extends RestBaseTest {

    private static final String TEST_USERNAME = "username";
    private static final long TEST_USERID = 1L;
    private static final long TEST_INVALID_USERID = 2L;

    private JacksonTester<Stats> json;
    private JacksonTester<UserNotFoundErrorResponse> userNotFoundJson;

    @MockBean
    private UserService userService;

    @MockBean
    private StatsService statsService;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        User user = generateDummyUser(TEST_USERID, TEST_USERNAME);
        given(this.userService.loadUser(TEST_USERID)).willReturn(user);
        given(this.userService.loadUser(TEST_INVALID_USERID)).willReturn(null);

        Stats stats = new Stats();
        given(this.statsService.loadStats(user)).willReturn(stats);
    }

    @Test
    public void testGameResultPlayerNotFound() throws IOException {
        String url = "/api/1/stats/" + String.valueOf(TEST_INVALID_USERID);
        ResponseEntity<String> response = getRequest(url);
        UserNotFoundErrorResponse errorResponse = userNotFoundJson.parseObject(response.getBody());
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getUserId() == TEST_INVALID_USERID);
    }

    @Test
    public void testGetStats() throws IOException {
        String url = "/api/1/stats/" + String.valueOf(TEST_USERID);
        ResponseEntity<String> response = getRequest(url);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(response.getBody() != null);
    }


}
