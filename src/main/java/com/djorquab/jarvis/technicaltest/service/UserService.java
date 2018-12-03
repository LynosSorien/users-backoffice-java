package com.djorquab.jarvis.technicaltest.service;

import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.dto.UserEditor;

import java.util.List;

public interface UserService {
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

    void deleteByUsername(String username);
    void deleteByEmail(String email);

    void loadUsers(int numberOfUsers, boolean cleanDatabase);

    List<UserDTO> findUsersByUsernameLike(String username);
    List<UserDTO> findUsersByEmailLike(String email);

    List<UserDTO> findAll();

    UserEditor dtoToEditor(UserDTO dto);

    void updateUser(UserEditor userEditor);
}
