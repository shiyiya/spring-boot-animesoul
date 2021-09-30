package com.app.animesoul.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

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
        return new ApiResponse<>(HttpStatus.OK.value(), data, "OK");
    }

    public static <T> ApiResponse<T> x(Integer code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public static <T> ApiResponse<T> x(Integer code) {
        return new ApiResponse<>(code, null, null);
    }

    public static <T> ApiResponse<T> notfound() {
        return new ApiResponse<T>(HttpStatus.NOT_FOUND.value(), null, "Not Found");
    }
}
