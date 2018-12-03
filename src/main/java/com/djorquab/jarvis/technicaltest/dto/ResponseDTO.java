package com.djorquab.jarvis.technicaltest.dto;

import com.djorquab.jarvis.technicaltest.commons.MessageLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<E> implements Serializable {
    @Builder.Default
    private MessageLevel messageLevel = MessageLevel.NONE;
    private String message;
    private transient E payload;
}
