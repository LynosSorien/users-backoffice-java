package com.djorquab.jarvis.technicaltest.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    private String street;
    private String city;
    private String state;
    private String postcode;
}
