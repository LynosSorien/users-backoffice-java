package com.djorquab.jarvis.technicaltest.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name implements Serializable {
    private String title;
    private String first;
    private String last;
}