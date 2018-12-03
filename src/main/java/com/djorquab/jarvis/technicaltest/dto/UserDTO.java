package com.djorquab.jarvis.technicaltest.dto;

import com.djorquab.jarvis.technicaltest.commons.Location;
import com.djorquab.jarvis.technicaltest.commons.Name;
import com.djorquab.jarvis.technicaltest.commons.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private String username;
    private String gender;
    private String password;
    private String email;
    private String roles;

    private Location location;
    private Name name;
    private Picture picture;
    private String phone;
    private String cell;

    private String decodedPassword;

    private String fullname;
}
