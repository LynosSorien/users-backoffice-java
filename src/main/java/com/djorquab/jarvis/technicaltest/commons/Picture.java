package com.djorquab.jarvis.technicaltest.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {
    private String large;
    private String medium;
    private String thumbnail;
}
