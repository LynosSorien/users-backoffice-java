package com.djorquab.jarvis.technicaltest.model;

import com.djorquab.jarvis.technicaltest.commons.Location;
import com.djorquab.jarvis.technicaltest.commons.Name;
import com.djorquab.jarvis.technicaltest.commons.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements Serializable {
    @Id
    private ObjectId _id;

    @Indexed(unique = true)
    private String username;
    private String password;

    @Indexed(unique = true)
    private String email;
    private String gender;

    private Location location;
    private Name name;
    private Picture picture;

    private String phone;
    private String cell;

    private String roles;

    // This is just used to view passwords for technical test (as passwords are encoded before entering to database).
    private String decodedPassword;
}
