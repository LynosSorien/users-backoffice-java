package com.djorquab.jarvis.technicaltest.web;

import com.djorquab.jarvis.technicaltest.commons.MessageLevel;
import com.djorquab.jarvis.technicaltest.dto.ResponseDTO;
import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.dto.UserEditor;
import com.djorquab.jarvis.technicaltest.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserManagementController {
    @Autowired
    private UserService service;

    @GetMapping("/user/by")
    @ApiOperation("Gets a User by the username or email")
    public ResponseEntity<ResponseDTO<UserDTO>> userByUsernameOrEmail(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email
    ) {
        UserDTO user = null;
        if (username != null && !"".equals(username)) {
            user = service.getUserByUsername(username);
        } else if (email != null && !"".equals(email)) {
            user = service.getUserByEmail(email);
        }
        if (user == null) {
            return ResponseEntity.ok(ResponseDTO.<UserDTO>builder().message("User not found").messageLevel(MessageLevel.ERROR).build());
        }
        return ResponseEntity.ok(ResponseDTO.<UserDTO>builder().payload(user).build());
    }

    @GetMapping("/users/by")
    @ApiOperation("Find users by email or username like.")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> usersByEmailOrUsername(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email
    ) {
        List<UserDTO> users = null;
        log.info("Find users by username {} or email {}", username, email);
        if (username != null && !"".equals(username)) {
            users = service.findUsersByUsernameLike(username);
        } else if (email != null && !"".equals(email)) {
            users = service.findUsersByEmailLike(email);
        } else {
            users = service.findAll();
        }
        return ResponseEntity.ok(ResponseDTO.<List<UserDTO>>builder().payload(users).build());
    }

    @GetMapping("/users")
    @ApiOperation("Find all users in Database")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> findAllUsers() {
        return ResponseEntity.ok(ResponseDTO.<List<UserDTO>>builder().payload(service.findAll()).build());
    }

    @PostMapping("/users/fill")
    @ApiOperation("Fill users from random generator API. Inserts 100 new users and remove all previous users (except admin account).")
    public ResponseEntity<ResponseDTO> fillUsers() {
        service.loadUsers(100, true);
        return ResponseEntity.ok(ResponseDTO.builder().build());
    }

    @PostMapping("/user/edit")
    public ResponseEntity<ResponseDTO<String>> editUser(@RequestBody UserEditor user) {
        log.info("Edit user: {}", user);
        service.updateUser(user);
        return ResponseEntity.ok(ResponseDTO.<String>builder().payload("Request processed correctly").build());
    }

    @DeleteMapping("/users/by")
    @ApiOperation("Delete user by username or email.")
    public ResponseEntity<ResponseDTO> deleteByUsernameOrEmail(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email
    ) {
        log.info("Delete user by username: {} or email: {}", username, email);
        try {
            if (username != null && !"".equals(username)) {
                service.deleteByUsername(username);
            } else if (email != null && !"".equals(email)) {
                service.deleteByEmail(email);
            }
        } catch (Exception e){
            log.error("Error occurred while trying to delete user {}:{}. Error {}", username, email, e);
            return ResponseEntity.ok(ResponseDTO.builder().messageLevel(MessageLevel.ERROR).message("The user can't be deleted, try again later.").build());
        }
        return ResponseEntity.ok(ResponseDTO.builder().messageLevel(MessageLevel.INFO).message("The user has been deleted.").build());
    }
}
