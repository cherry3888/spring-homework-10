package ru.cherry.springhomework;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class exeptions {

    @Data
    @AllArgsConstructor
    public static class ErrorMessage {
        private int statusCode;
        private Date timestamp;
        private String message;
        private String description;
    }
}
