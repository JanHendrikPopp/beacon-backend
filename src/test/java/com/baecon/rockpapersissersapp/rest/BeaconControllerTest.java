package com.baecon.rockpapersissersapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class BeaconControllerTest extends RestBaseTest {

    private static final String CHECK_URL = "/api/1/isvalidbeacon/";
    private static final String CORRECT_BEACON_ID = "4LKv";
    private static final String INCORRECT_BEACON_ID = "incorrect";

    private JacksonTester<Boolean> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testCorrectId() throws IOException {
        String url = CHECK_URL + CORRECT_BEACON_ID;
        ResponseEntity<String> response = getRequest(url);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(json.parseObject(response.getBody()).equals(true));
    }

    @Test
    public void testIncorrectId() throws IOException {
        String url = CHECK_URL + INCORRECT_BEACON_ID;
        ResponseEntity<String> response = getRequest(url);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(json.parseObject(response.getBody()).equals(false));
    }

}
