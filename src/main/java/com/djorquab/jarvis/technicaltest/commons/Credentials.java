package com.djorquab.jarvis.technicaltest.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials implements Serializable {
    private String username;
    private String password;
}
