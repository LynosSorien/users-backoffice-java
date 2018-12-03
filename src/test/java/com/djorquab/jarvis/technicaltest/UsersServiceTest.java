package com.djorquab.jarvis.technicaltest;

import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testLoadUsers() {
        userService.loadUsers(100, true);
        List<UserDTO> users = userService.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(101, users.size());
    }

    @Test
    public void testLoadAdminByUsername() {
        UserDTO user = userService.getUserByUsername("admin");
        Assert.assertNotNull(user);
        Assert.assertEquals("admin", user.getUsername());
    }
}
