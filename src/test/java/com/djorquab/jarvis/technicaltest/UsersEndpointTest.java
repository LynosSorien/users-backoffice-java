package com.djorquab.jarvis.technicaltest;

import com.djorquab.jarvis.technicaltest.dto.UserApiResponse;
import com.djorquab.jarvis.technicaltest.manager.PropertyManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersEndpointTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PropertyManager propertyManager;

    @Test
    public void testGet100Users() {
        ResponseEntity<UserApiResponse> response = restTemplate.getForEntity(propertyManager.getUserApi()+100, UserApiResponse.class);
        Assert.assertTrue(response.hasBody());
        Assert.assertEquals(100, response.getBody().getResults().size());
    }
}
