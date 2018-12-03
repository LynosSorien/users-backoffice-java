package com.djorquab.jarvis.technicaltest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApiResponse implements Serializable {
    private List<UserApi> results;
}
