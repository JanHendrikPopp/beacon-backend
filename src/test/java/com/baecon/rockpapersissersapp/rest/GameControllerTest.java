package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.rest.response.GameNotFoundErrorResponse;
import com.baecon.rockpapersissersapp.rest.response.MissingParameterErrorResponse;
import com.baecon.rockpapersissersapp.rest.response.UserNotFoundErrorResponse;
import com.baecon.rockpapersissersapp.service.GameService;
import com.baecon.rockpapersissersapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class GameControllerTest extends RestBaseTest {

    private static final String MOVE_URL = "/api/1/move";
    private static final String TEST_USERNAME = "username";
    private static final long TEST_USERID = 1L;
    private static final long TEST_USERID_SND = 6L;
    private static final long TEST_INVALID_USERID = 2L;
    private static final long TEST_BEACONID = 3L;
    private static final long TEST_GAMEID = 4L;
    private static final long TEST_INVALID_GAMEID = 5L;

    private JacksonTester<Game> json;
    private JacksonTester<MissingParameterErrorResponse> misingParameterJson;
    private JacksonTester<GameNotFoundErrorResponse> gameNotFoundJson;
    private JacksonTester<UserNotFoundErrorResponse> userNotFoundJson;

    @MockBean
    private UserService userService;

    @MockBean
    private GameService gameService;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        User user = generateDummyUser(TEST_USERID, TEST_USERNAME);
        Game game = new Game();
        game.setFirstFigure(Figure.PAPER);
        game.setFirstUser(user);
        given(this.userService.loadUser(TEST_USERID)).willReturn(user);
        given(this.userService.loadUser(TEST_INVALID_USERID)).willReturn(null);
        given(this.gameService.makeMove(user, Figure.PAPER)).willReturn(game);

        User user2 = generateDummyUser(TEST_USERID_SND, "Testname");
        Game completeGame = new Game();
        completeGame.setId(TEST_GAMEID);
        completeGame.setFirstUser(user);
        completeGame.setFirstFigure(Figure.PAPER);
        completeGame.setSecondUser(user2);
        completeGame.setSecondFigure(Figure.ROCK);
        given(this.gameService.loadGame(TEST_GAMEID)).willReturn(completeGame);
        given(this.gameService.loadGame(TEST_INVALID_GAMEID)).willReturn(null);
    }

    @Test
    public void testMakeMove() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("playerId", String.valueOf(TEST_USERID));
        parameters.add("beaconId", String.valueOf(TEST_BEACONID));
        parameters.add("option", "PAPER");

        ResponseEntity<String> response = postRequest(parameters, MOVE_URL);
        Game game = json.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(game.getFirstUser().getId().equals(1L));
        assertTrue(game.getFirstFigure().equals(Figure.PAPER));
    }

    @Test
    public void testUserNotFound() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("playerId", String.valueOf(TEST_INVALID_USERID));
        parameters.add("beaconId", String.valueOf(TEST_BEACONID));
        parameters.add("option", "PAPER");
        ResponseEntity<String> response = postRequest(parameters, MOVE_URL);
        UserNotFoundErrorResponse errorResponse = userNotFoundJson.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getUserId() == TEST_INVALID_USERID);
    }

    @Test
    public void testMissingBeaconId() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("playerId", String.valueOf(TEST_USERID));
        parameters.add("option", "PAPER");
        ResponseEntity<String> response = postRequest(parameters, MOVE_URL);
        MissingParameterErrorResponse errorResponse = misingParameterJson.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getMissingParameter().equals("beaconId"));
    }

    @Test
    public void testMissingPlayerId() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("beaconId", String.valueOf(TEST_BEACONID));
        parameters.add("option", "PAPER");
        ResponseEntity<String> response = postRequest(parameters, MOVE_URL);
        MissingParameterErrorResponse errorResponse = misingParameterJson.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getMissingParameter().equals("playerId"));
    }

    @Test
    public void testMissingOption() throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("playerId", String.valueOf(TEST_USERID));
        parameters.add("beaconId", String.valueOf(TEST_BEACONID));
        ResponseEntity<String> response = postRequest(parameters, MOVE_URL);
        MissingParameterErrorResponse errorResponse = misingParameterJson.parseObject(response.getBody());

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getMissingParameter().equals("option"));
    }

    @Test
    public void testGameResultGameNotFound() throws IOException {
        String url = "/api/1/game/" + String.valueOf(TEST_GAMEID) + "/" + String.valueOf(TEST_INVALID_USERID);
        ResponseEntity<String> response = getRequest(url);
        UserNotFoundErrorResponse errorResponse = userNotFoundJson.parseObject(response.getBody());
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getUserId() == TEST_INVALID_USERID);
    }

    @Test
    public void testGameResultPlayerNotFound() throws IOException {
        String url = "/api/1/game/" + String.valueOf(TEST_INVALID_GAMEID) + "/" + String.valueOf(TEST_USERID);
        ResponseEntity<String> response = getRequest(url);
        GameNotFoundErrorResponse errorResponse = gameNotFoundJson.parseObject(response.getBody());
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertTrue(errorResponse.getGameId() == TEST_INVALID_GAMEID);
    }

    @Test
    public void testGameResult() throws IOException {
        String url = "/api/1/game/" + String.valueOf(TEST_GAMEID) + "/" + String.valueOf(TEST_USERID);
        ResponseEntity<String> response = getRequest(url);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(response.getBody().equals("\"WIN\""));
    }


}
