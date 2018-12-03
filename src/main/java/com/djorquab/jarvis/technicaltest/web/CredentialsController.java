package com.djorquab.jarvis.technicaltest.web;

import com.djorquab.jarvis.technicaltest.commons.Credentials;
import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController("/api")
public class CredentialsController {
    @Autowired
    private UserService userService;

    @GetMapping("/credentials")
    public ResponseEntity<List<Credentials>> credentials() {
        List<UserDTO> users = userService.findAll();
        List<Credentials> credentials = new LinkedList<>();
        for (UserDTO user : users) {
            credentials.add(Credentials.builder().username(user.getUsername()).password(user.getDecodedPassword()).build());
        }
        return ResponseEntity.ok(credentials);
    }
}
