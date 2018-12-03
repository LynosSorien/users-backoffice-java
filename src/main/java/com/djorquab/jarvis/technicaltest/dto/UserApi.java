package com.djorquab.jarvis.technicaltest.dto;

import com.djorquab.jarvis.technicaltest.commons.Credentials;
import com.djorquab.jarvis.technicaltest.commons.Location;
import com.djorquab.jarvis.technicaltest.commons.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.djorquab.jarvis.technicaltest.commons.Name;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApi implements Serializable {
    private String gender;
    private Name name;
    private Picture picture;
    private String phone;
    private String cell;
    private Credentials login;
    private Location location;
    private String email;
}
