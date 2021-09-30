package com.app.animesoul.payload.response;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class ValidationError {
    final int code = 400;
    final String message = ":) Bad Request: Parameter validation failed.";
    List<Map<String, String>> errors = new ArrayList<>();

    public void addError(Map<String, String> error) {
        errors.add(error);
    }
}
