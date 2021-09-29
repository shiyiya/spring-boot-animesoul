package com.app.animesoul.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> v(T data) {
        return new ApiResponse<>(200, data, null);
    }

    public static <T> ApiResponse<T> x(Integer code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public static <T> ApiResponse<T> x(Integer code) {
        return new ApiResponse<>(code, null, null);
    }
}
