package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.model.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestBaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    protected ResponseEntity<String> postRequest(MultiValueMap<String, String> parameters, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        return restTemplate.postForEntity(url, request , String.class );
    }

    protected ResponseEntity<String> getRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return restTemplate.getForEntity(url, String.class );
    }

    protected User generateDummyUser(long id, String name) {
        User user = new User();
        user.setName(name);
        user.setId(id);
        return user;
    }

}
