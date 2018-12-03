package com.djorquab.jarvis.technicaltest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEditor implements Serializable {
    private String username;
    private String email;
    private String gender;
    private String password;
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private String cell;
    private String street;
    private String city;
    private String state;
    private String postcode;
    private String imageLargeUrl;
    private String imageMediumUrl;
    private String imageThumbnailUrl;
}
